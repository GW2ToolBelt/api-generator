/*
 * Copyright (c) 2019-2021 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.gw2tb.apigen.test

import com.gw2tb.apigen.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.schema.*
import kotlinx.serialization.json.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.fail
import org.junit.jupiter.api.Assertions.*
import kotlin.time.*

abstract class SpecTest<Q : APIQuery, T : APIType, EQ : SpecTest.ExpectedAPIQuery>(
    protected val prefix: String,
    protected val spec: APIVersion<Q, T>,
    private val expectedQueries: List<EQ>
) {

    abstract fun assertProperties(expected: EQ, actual: Q)

    @TestFactory
    fun testQueries(): Iterator<DynamicTest> = sequence<DynamicTest> {
        val expectedQueries = ArrayList(expectedQueries)
        val actualQueries = HashSet(spec.supportedQueries)

        with(expectedQueries.iterator()) {
            while (hasNext()) {
                val expectedQuery = next()
                val actualQuery = actualQueries.find {
                    it.route == expectedQuery.route
                        && it.pathParameters.all { p -> expectedQuery.pathParameters.any { p.key == it.key } }
                        && it.queryParameters.all { p -> expectedQuery.queryParameters.any { p.key == it.key } }
                        && expectedQuery.pathParameters.all { p -> it.pathParameters.any { p.key == it.key } }
                        && expectedQuery.queryParameters.all { p -> it.queryParameters.any { p.key == it.key } }
                }
                if (actualQuery != null) actualQueries.remove(actualQuery)

                yield(DynamicTest.dynamicTest("$prefix${expectedQuery.route}") {
                    if (actualQuery == null) fail("Could not find matching actual query for: $expectedQuery")

                    assertEquals(expectedQuery.isLocalized, actualQuery.isLocalized, "Mismatched 'isLocalized' flag for ${actualQuery.route}")
                    assertEquals(expectedQuery.cache, actualQuery.cache, "Mismatched 'cache' flag for ${actualQuery.route}")
                    assertProperties(expectedQuery, actualQuery)

                    actualQuery.pathParameters.forEach { (_, actualParam) ->
                        val expectedParam = expectedQuery.pathParameters.find { actualParam.key == it.key }!!
                        assertEquals(expectedParam.type, actualParam.type)
                    }
                    actualQuery.queryParameters.forEach { (_, actualParam) ->
                        val expectedParam = expectedQuery.queryParameters.find { actualParam.key == it.key }!!
                        assertEquals(expectedParam.type, actualParam.type)
                        assertEquals(expectedParam.isOptional, actualParam.isOptional)
                    }
                })

                remove()
            }
        }

        actualQueries.forEach {
            yield(DynamicTest.dynamicTest("$prefix${it.route}") {
                fail("Did not expect query: $it")
            })
        }
    }.iterator()

    fun assertSchema(schema: SchemaType, data: String) {
        val element = Json.parseToJsonElement(data)
        schema.assertMatches(element)
    }

    abstract fun testTypes(queries: Collection<Q>): Iterable<DynamicTest>

    @TestFactory
    fun testTypes(): Iterator<DynamicTest> = sequence {
        spec.supportedQueries.groupBy { it.endpoint }.values.forEach { endpointQueries ->
            yieldAll(testTypes(endpointQueries))
        }
    }.iterator()

    private fun SchemaType.assertMatches(element: JsonElement, nullable: Boolean = false, interpretation: String? = null) {
        fun <T> JsonPrimitive.validate(optional: JsonPrimitive.() -> T, required: JsonPrimitive.() -> T?) =
            assertDoesNotThrow { if (nullable) optional() else required() }

        when (this) {
            is SchemaPrimitive -> {
                val primitive = assertDoesNotThrow("$this") { element.jsonPrimitive }

                when (this) {
                    is SchemaBoolean -> primitive.validate(JsonPrimitive::booleanOrNull, JsonPrimitive::boolean)
                    is SchemaDecimal -> primitive.validate(JsonPrimitive::doubleOrNull, JsonPrimitive::double)
                    is SchemaInteger -> primitive.validate(JsonPrimitive::longOrNull, JsonPrimitive::long)
                    is SchemaString -> primitive.validate(JsonPrimitive::contentOrNull, JsonPrimitive::content)
                    else -> error("Should not happen")
                }
            }
            is SchemaArray -> {
                val array = assertDoesNotThrow<JsonArray> { element.jsonArray }
                array.forEach { items.assertMatches(it, nullableItems) }
            }
            is SchemaConditional -> {
                if (nullable && element is JsonNull) return
                val record = assertDoesNotThrow<JsonObject> { element.jsonObject }

                sharedProperties.forEach { (_, property) ->
                    val value = assertDoesNotThrow<JsonElement?>("$this") { record[property.serialName]!! }
                    if (value === null) {
                        if (property.optionality.isOptional)
                            return@forEach
                        else
                            fail()
                    }

                    val type = property.type
                    val intrp = (if (type is SchemaConditional && type.disambiguationBySideProperty)
                        assertDoesNotThrow<String> { record[type.disambiguationBy]!!.jsonPrimitive.content }
                    else
                        null)

                    property.type.assertMatches(value, property.optionality.isOptional, intrp)
                }

                /* If the interpretation is not null, the disambiguation happens by side property. */
                val interpretationID = interpretation ?: assertDoesNotThrow("$this") { record[disambiguationBy]!!.jsonPrimitive.content }
                val interpretation = interpretations[interpretationID]
                assertNotNull(interpretation)

                interpretation.type.assertMatches(if (interpretationInNestedProperty) record[interpretation.interpretationNestProperty]!! else record)
            }
            is SchemaMap -> {
                val map = assertDoesNotThrow<JsonObject> { element.jsonObject }
                if (map.isNotEmpty()) values.assertMatches(map.values.first(), nullableValues)
            }
            is SchemaRecord -> {
                if (nullable && element is JsonNull) return
                val record = assertDoesNotThrow("$this") { element.jsonObject }

                properties.forEach { (_, property) ->
                    val value = assertDoesNotThrow<JsonElement?> { record[property.serialName] }
                    if (value === null) {
                        if (property.optionality.isOptional)
                            return@forEach
                        else
                            fail("Could not find required property \"${property.serialName}\"")
                    }

                    val type = property.type
                    val intrp = (if (type is SchemaConditional && type.disambiguationBySideProperty)
                        assertDoesNotThrow("$this") { record[type.disambiguationBy]!!.jsonPrimitive.content }
                    else
                        null)

                    property.type.assertMatches(value, property.optionality.isOptional, intrp)
                }
            }
            else -> fail("Unsupported SchemaType reached SpecTest stage: $this")
        }
    }

    interface ExpectedAPIQuery {
        val route: String
        val isLocalized: Boolean
        val cache: Duration?
        val pathParameters: List<ExpectedPathParameter>
        val queryParameters: List<ExpectedQueryParameter>
    }

    data class ExpectedPathParameter(
        val key: String,
        val type: SchemaType
    )

    data class ExpectedQueryParameter(
        val key: String,
        val type: SchemaType,
        val isOptional: Boolean = false
    )

}