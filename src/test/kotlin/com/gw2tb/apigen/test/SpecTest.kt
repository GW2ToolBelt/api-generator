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

            actual?.let(unexpectedEndpoints::remove)
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
        }

        unexpectedEndpoints.forEach { actual ->
            yield(DynamicTest.dynamicTest("SpecTest/$prefix${actual.route}") {
                fail("Unexpected endpoint: ${actual.route}")
            })
        }
    }.iterator()

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

