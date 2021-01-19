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
import org.junit.jupiter.api.Assertions.*
import kotlin.time.*

abstract class SpecTest(private val prefix: String, private val spec: APIVersion, builder: SpecBuilder.() -> Unit) {

    private val expectedEndpoints: List<ExpectedEndpoint>

    init {
        expectedEndpoints = SpecBuilder().apply(builder).endpoints
    }

    @TestFactory
    fun testSpec(): Iterator<DynamicTest> = sequence<DynamicTest> {
        val unexpectedEndpoints = HashSet(spec.endpoints)

        expectedEndpoints.forEach { expected ->
            val name = "SpecTest/$prefix${expected.route}"

            val actual = spec.endpoints.find {
                it.route == expected.route
                    && it.pathParameters.all { actualParam -> expected.pathParameters.any { actualParam.key == it.key } }
                    && it.queryParameters.all { actualParam -> expected.queryParameters.any { actualParam.key == it.key } }
            }

            yield(DynamicTest.dynamicTest(name) {
                assertNotNull(actual, name)

                assertEquals(expected.cache, actual.cache, name)
                assertEquals(expected.security, actual.security, name)
                assertEquals(expected.isLocalized, actual.isLocalized, name)
                assertEquals(expected.queryTypes, actual.queryTypes, name)

                actual.pathParameters.forEach { actualParam ->
                    val expectedParam = expected.pathParameters.find { actualParam.key == it.key }
                    assertNotNull(expectedParam, name)
                    assertEquals(expectedParam.type, actualParam.type, name)
                }
                actual.queryParameters.forEach { actualParam ->
                    val expectedParam = expected.queryParameters.find { actualParam.key == it.key }
                    assertNotNull(expectedParam, name)
                    assertEquals(expectedParam.type, actualParam.type, name)
                }
            })


            if (actual == null) {
                yield(DynamicTest.dynamicTest("EndpointNotFound/$prefix${expected.route}") {
                    fail()
                })
                return@forEach
            }

            actual.let(unexpectedEndpoints::remove)
            if (spec == API_V2_DEFINITION && expected.route == "/CreateSubToken") return@forEach

            actual.versions.forEach { version ->
                val schema = actual[version].second

                yield(DynamicTest.dynamicTest("SchemaTest/$prefix${expected.route}@${version.identifier}") {
                    val data = assertDoesNotThrow("Failed to load test data for ${actual.route}@${version}") {
                        TestData[spec, actual.route, version]
                    }

                    val element = Json.parseToJsonElement(data)

                    (if (actual.queryTypes.isNotEmpty()) SchemaArray(schema, false, null) else schema)
                        .assertSchemaMatches(element)
                })
            }
        }

        unexpectedEndpoints.forEach { actual ->
            yield(DynamicTest.dynamicTest("SpecTest/$prefix${actual.route}") {
                fail("Unexpected endpoint: ${actual.route}")
            })
        }
    }.iterator()

    private fun SchemaType.assertSchemaMatches(element: JsonElement, nullable: Boolean = false, interpretation: String? = null) {
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
                array.forEach { items.assertSchemaMatches(it, nullableItems) }
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

                    property.type.assertSchemaMatches(value, property.optionality.isOptional, intrp)
                }

                /* If the interpretation is not null, the disambiguation happens by side property. */
                val interpretationRecord = interpretations[interpretation ?: assertDoesNotThrow("$this") { record[disambiguationBy]!!.jsonPrimitive.content }]
                assertNotNull(interpretationRecord)

                interpretationRecord.type.assertSchemaMatches(record)
            }
            is SchemaMap -> {
                val map = assertDoesNotThrow<JsonObject> { element.jsonObject }
                if (map.isNotEmpty()) values.assertSchemaMatches(map.values.first(), nullableValues)
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

                    property.type.assertSchemaMatches(value, property.optionality.isOptional, intrp)
                }
            }
        }
    }

    data class ExpectedEndpoint(
        val route: String,
        val cache: Duration?,
        val security: Set<TokenScope>,
        val isLocalized: Boolean,
        val queryTypes: Set<QueryType>,
        val pathParameters: List<Parameter>,
        val queryParameters: List<Parameter>
    ) {

        data class Parameter(
            val key: String,
            val type: SchemaType
        )

    }

    class SpecBuilder {

        private val endpointBuilders = mutableListOf<EndpointSpecBuilder>()

        val endpoints by lazy {
            endpointBuilders.map { builder ->
                ExpectedEndpoint(
                    builder.route,
                    builder.cache,
                    builder.security,
                    builder.isLocalized,
                    builder.queryTypes,
                    builder.pathParameters,
                    builder.queryParameters
                )
            }
        }

        fun expectEndpoint(route: String): EndpointSpecBuilder =
            EndpointSpecBuilder(route)
                .also { endpointBuilders.add(it) }

    }

    class EndpointSpecBuilder(val route: String) {

        var cache: Duration? = null
        var security = emptySet<TokenScope>()
        var isLocalized = false
        var queryTypes = emptySet<QueryType>()
        var pathParameters = emptyList<ExpectedEndpoint.Parameter>()
        var queryParameters = emptyList<ExpectedEndpoint.Parameter>()

        fun cacheTime(value: Duration) = apply {
            cache = value
        }

        fun security(vararg scope: TokenScope) = apply {
            security = setOf(*scope)
        }

        fun localized() = apply {
            isLocalized = true
        }

        fun queryTypes(vararg queryTypes: QueryType) = apply {
            this.queryTypes = setOf(*queryTypes)
        }

        fun pathParameter(name: String, type: SchemaType) = apply {
            pathParameters += ExpectedEndpoint.Parameter(name, type)
        }

        fun queryParameter(name: String, type: SchemaType) = apply {
            queryParameters += ExpectedEndpoint.Parameter(name, type)
        }

    }

}

