/*
 * Copyright (c) 2019-2026 Leon Linhart
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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.test

import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.VoidResolverContext
import com.gw2tb.apigen.ir.model.IRAPIQuery
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.ir.model.IRAPIVersion
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.schema.*
import kotlinx.serialization.json.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.fail
import org.junit.jupiter.api.Assertions.*
import kotlin.time.*

abstract class SpecTest<Q : IRAPIQuery, T : IRAPIType, EQ : SpecTest.ExpectedAPIQuery> internal constructor(
    protected val prefix: String,
    private val spec: IRAPIVersion<Q, T>,
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
                    it.path == expectedQuery.route
                        && it.pathParameters.all { p -> expectedQuery.pathParameters.any { p.key == it.key } }
                        && it.queryParameters.all { p -> expectedQuery.queryParameters.any { p.key == it.key } }
                        && expectedQuery.pathParameters.all { p -> it.pathParameters.any { p.key == it.key } }
                        && expectedQuery.queryParameters.all { p -> it.queryParameters.any { p.key == it.key } }
                }
                if (actualQuery != null) actualQueries.remove(actualQuery)

                yield(DynamicTest.dynamicTest("$prefix${expectedQuery.route}") {
                    if (actualQuery == null) fail("Could not find matching actual query for: $expectedQuery")

                    assertEquals(expectedQuery.cache, actualQuery.cache, "Mismatched 'cache' flag for ${actualQuery.path}")
                    assertProperties(expectedQuery, actualQuery)

                    actualQuery.pathParameters.forEach { (_, actualParam) ->
                        val expectedParam = expectedQuery.pathParameters.find { actualParam.key == it.key }!!
                        assertLoweredEquals(expectedParam.type, actualParam.type.resolve(VoidResolverContext))
                    }
                    actualQuery.queryParameters.forEach { (_, actualParam) ->
                        val expectedParam = expectedQuery.queryParameters.find { actualParam.key == it.key }!!
                        assertLoweredEquals(expectedParam.type, actualParam.type.resolve(VoidResolverContext))
                        assertEquals(expectedParam.isOptional, actualParam.isOptional)
                    }
                })

                remove()
            }
        }

        actualQueries.forEach {
            yield(DynamicTest.dynamicTest("$prefix${it.path}") {
                fail("Did not expect query: $it")
            })
        }
    }.iterator()

    fun assertSchema(schema: SchemaTypeDeclaration, data: List<JsonElement>) {
        data.forEachIndexed { index, entry ->
            val errors = testSchema(
                schema = schema,
                actual = entry,
                path = "Sample[$index]"
            )

            if (errors.isNotEmpty()) {
                errors.forEach { error ->
                    System.err.println("${error.path} - ${error.message}")
                    error.cause?.printStackTrace()
                }

                fail("Schema did not match actual")
            }
        }
    }

    abstract fun testType(irType: T): Iterable<DynamicTest>

    @TestFactory
    fun testTypes(): Iterator<DynamicTest> = sequence {
        spec.supportedTypes
            .filter { (loc, _) -> loc.nest == null }
            .forEach { (_, type) -> yieldAll(testType(type)) }
    }.iterator()

    interface ExpectedAPIQuery {
        val route: String
        val isLocalized: Boolean
        val cache: Duration?
        val pathParameters: List<ExpectedPathParameter>
        val queryParameters: List<ExpectedQueryParameter>
    }

    data class ExpectedPathParameter(
        val key: String,
        val type: SchemaTypeUse
    )

    data class ExpectedQueryParameter(
        val key: String,
        val type: SchemaTypeUse,
        val isOptional: Boolean = false
    )

}