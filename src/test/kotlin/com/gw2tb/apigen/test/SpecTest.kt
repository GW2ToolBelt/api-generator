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
        schema.assertMatches(path = "", element)
    }

    abstract fun testTypes(queries: Collection<Q>): Iterable<DynamicTest>

    @TestFactory
    fun testTypes(): Iterator<DynamicTest> = sequence {
        spec.supportedQueries.groupBy { it.endpoint }.values.forEach { endpointQueries ->
            yieldAll(testTypes(endpointQueries))
        }
    }.iterator()

    private fun SchemaType.assertMatches(
        path: String,
        actual: JsonElement,
        nullable: Boolean = false,
        interpretationKey: String? = null,
        inheritedSharedProperties: Map<String, SchemaRecord.Property> = emptyMap()
    ) {
        fun <T> JsonPrimitive.validate(optional: JsonPrimitive.() -> T, required: JsonPrimitive.() -> T?) =
            assertDoesNotThrow { if (nullable) optional() else required() }

        when (this) {
            is SchemaPrimitive -> {
                val primitive = assertDoesNotThrow("$this") { actual.jsonPrimitive }

                when (this) {
                    is SchemaBoolean -> primitive.validate(JsonPrimitive::booleanOrNull, JsonPrimitive::boolean)
                    is SchemaDecimal -> primitive.validate(JsonPrimitive::doubleOrNull, JsonPrimitive::double)
                    is SchemaInteger -> primitive.validate(JsonPrimitive::longOrNull, JsonPrimitive::long)
                    is SchemaString -> primitive.validate(JsonPrimitive::contentOrNull, JsonPrimitive::content)
                    else -> error("Should not happen")
                }
            }
            is SchemaArray -> {
                val array = assertDoesNotThrow<JsonArray> { actual.jsonArray }
                array.forEachIndexed { index, it -> items.assertMatches("$path[$index]", it, nullableItems) }
            }
            is SchemaConditional -> {
                if (nullable && actual is JsonNull) return
                val jsonObject = assertDoesNotThrow<JsonObject> { actual.jsonObject }

                if (interpretationInNestedProperty) {
                    val expectedProperties = (inheritedSharedProperties + sharedProperties)

                    val unvisitedExpectedProperties = HashMap(expectedProperties.filter { (_, property) -> !property.optionality.isOptional })
                    val unvisitedActualElements = HashSet(jsonObject.keys)

                    expectedProperties.forEach { (key, property) ->
                        val value = jsonObject[property.serialName] ?: return@forEach
                        unvisitedExpectedProperties.remove(key)
                        unvisitedActualElements.remove(property.serialName)

                        val interpretationKey = property.type.let { propertyType ->
                            if (propertyType !is SchemaConditional || !propertyType.disambiguationBySideProperty) return@let null

                            val disambiguationByElement = jsonObject[propertyType.disambiguationBy] ?: fail("Disambiguator not found: ${propertyType.disambiguationBy} in $path")
                            disambiguationByElement.jsonPrimitive.content
                        }

                        property.type.assertMatches("$path/${property.serialName}", value, property.optionality.isOptional, interpretationKey)
                    }

                    unvisitedExpectedProperties.let {
                        if (it.isNotEmpty())
                            fail("Required properties were not found: ${it.values.map(SchemaRecord.Property::serialName)} in $path")
                    }

                    unvisitedActualElements.let {
                        if (it.isNotEmpty())
                            fail("Unexpected properties: $it in $path")
                    }
                }

                val interpretation = interpretationKey.let {
                    if (it == null) {
                        if (disambiguationBySideProperty) error("Unexpected state")

                        jsonObject[disambiguationBy]?.jsonPrimitive?.content ?: fail("Disambiguator not found: $disambiguationBy in $path")
                    } else
                        it
                }.let { key ->
                    val interpretation = interpretations[key] ?: fail("Could not find interpretation for key: $key in $path")
                    interpretation
                }

                val actualInterpretation = if (interpretationInNestedProperty)
                    jsonObject[interpretation.interpretationNestProperty] ?: fail("Nested interpretation property not found: ${interpretation.interpretationNestProperty} in $path")
                else
                    jsonObject

                interpretation.type.assertMatches(
                    if (interpretationInNestedProperty) "$path/${interpretation.interpretationNestProperty}" else path,
                    actualInterpretation,
                    inheritedSharedProperties = if (interpretationInNestedProperty) emptyMap() else sharedProperties
                )
            }
            is SchemaMap -> {
                val map = assertDoesNotThrow<JsonObject> { actual.jsonObject }
                map.forEach { key, value -> values.assertMatches("$path[$key]", value, nullableValues) }
            }
            is SchemaRecord -> {
                if (nullable && actual is JsonNull) return
                val jsonObject = assertDoesNotThrow<JsonObject> { actual.jsonObject }

                val expectedProperties = (inheritedSharedProperties + properties)

                val unvisitedExpectedProperties = HashMap(expectedProperties.filter { (_, property) -> !property.optionality.isOptional })
                val unvisitedActualElements = HashSet(jsonObject.keys)

                expectedProperties.forEach { (key, property) ->
                    val value = jsonObject[property.serialName] ?: return@forEach
                    unvisitedExpectedProperties.remove(key)
                    unvisitedActualElements.remove(property.serialName)

                    val interpretationKey = property.type.let { propertyType ->
                        if (propertyType !is SchemaConditional || !propertyType.disambiguationBySideProperty) return@let null

                        val disambiguationByElement = jsonObject[propertyType.disambiguationBy] ?: fail("Disambiguator not found: ${propertyType.disambiguationBy} in $path")
                        disambiguationByElement.jsonPrimitive.content
                    }

                    property.type.assertMatches("$path/${property.serialName}", value, property.optionality.isOptional, interpretationKey)
                }

                unvisitedExpectedProperties.let {
                    if (it.isNotEmpty())
                        fail("Required properties were not found: ${it.values.map(SchemaRecord.Property::serialName)} in $path")
                }

                unvisitedActualElements.let {
                    if (it.isNotEmpty())
                        fail("Unexpected properties: $it in $path")
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