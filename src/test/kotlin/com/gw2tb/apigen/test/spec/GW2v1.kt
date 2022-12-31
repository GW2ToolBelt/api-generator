/*
 * Copyright (c) 2019-2022 Leon Linhart
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
package com.gw2tb.apigen.test.spec

import com.gw2tb.apigen.*
import com.gw2tb.apigen.model.APIv1Endpoint.*
import com.gw2tb.apigen.internal.dsl.hours
import com.gw2tb.apigen.internal.spec.GW2v1
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.VoidResolverContext
import com.gw2tb.apigen.ir.model.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*
import kotlin.time.Duration

class GW2v1 : SpecTest<IRAPIQuery.V1, IRAPIType.V1, GW2v1.ExpectedAPIv1Query>(
    "GW2v1",
    GW2v1.build(EnumSet.allOf(APIv1Endpoint::class.java)),
    V1SpecBuilder {
        expectQuery(V1_BUILD)

        expectQuery(
            V1_COLORS,
            isLocalized = true
        )

        expectQuery(
            V1_CONTINENTS,
            isLocalized = true
        )

        expectQuery(
            V1_EVENT_DETAILS,
            isLocalized = true
        )

        expectQuery(V1_FILES)

        expectQuery(
            V1_GUILD_DETAILS,
            queryParameters = listOf(ExpectedQueryParameter("guild_id", STRING))
        )

        expectQuery(
            V1_GUILD_DETAILS,
            queryParameters = listOf(ExpectedQueryParameter("guild_name", STRING))
        )

        expectQuery(
            V1_ITEM_DETAILS,
            isLocalized = true,
            queryParameters = listOf(ExpectedQueryParameter("item_id", INTEGER))
        )

        expectQuery(V1_ITEMS)

        expectQuery(
            V1_MAP_FLOOR,
            isLocalized = true,
            queryParameters = listOf(
                ExpectedQueryParameter("continent_id", INTEGER),
                ExpectedQueryParameter("floor", INTEGER)
            )
        )

        expectQuery(
            V1_MAP_NAMES,
            isLocalized = true
        )

        expectQuery(
            V1_MAPS,
            isLocalized = true
        )

        expectQuery(
            V1_RECIPE_DETAILS,
            queryParameters = listOf(ExpectedQueryParameter("recipe_id", INTEGER))
        )

        expectQuery(V1_RECIPES)

        expectQuery(
            V1_SKIN_DETAILS,
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("skin_id", INTEGER))
        )

        expectQuery(V1_SKINS)

        expectQuery(
            V1_WORLD_NAMES,
            isLocalized = true
        )

        expectQuery(
            V1_WVW_MATCH_DETAILS,
            queryParameters = listOf(ExpectedQueryParameter("match_id", STRING))
        )

        expectQuery(V1_WVW_MATCHES)

        expectQuery(
            V1_WVW_OBJECTIVE_NAMES,
            isLocalized = true
        )
    }
) {

    override fun assertProperties(expected: ExpectedAPIv1Query, actual: IRAPIQuery.V1) {
        val schema = actual.resolve(VoidResolverContext, v2SchemaVersion = null).schema
        assertEquals(expected.isLocalized, schema.isLocalized, "Mismatched 'isLocalized' flag for ${actual.path}")
    }

    override fun testType(irType: IRAPIType.V1): Iterable<DynamicTest> = sequence {
        yield(DynamicTest.dynamicTest("$prefix${irType.name}") {
            val type = irType.resolve(VoidResolverContext, v2SchemaVersion = null)

            val data = TestData[type]
            assertSchema(type.schema, data)
        })
    }.asIterable()

    data class ExpectedAPIv1Query(
        private val endpoint: APIv1Endpoint,
        override val route: String,
        override val isLocalized: Boolean,
        override val cache: Duration?,
        override val pathParameters: List<ExpectedPathParameter>,
        override val queryParameters: List<ExpectedQueryParameter>
    ) : ExpectedAPIQuery

    class V1SpecBuilder {

        private val queries = mutableListOf<ExpectedAPIv1Query>()

        companion object {
            operator fun invoke(block: V1SpecBuilder.() -> Unit): List<ExpectedAPIv1Query> {
                return V1SpecBuilder().also(block).queries
            }
        }

        fun expectQuery(
            endpoint: APIv1Endpoint,
            route: String = endpoint.path,
            isLocalized: Boolean = false,
            cache: Duration? = null,
            pathParameters: List<ExpectedPathParameter> = emptyList(),
            queryParameters: List<ExpectedQueryParameter> = emptyList()
        ) {
            queries += ExpectedAPIv1Query(
                endpoint,
                route,
                isLocalized,
                cache,
                pathParameters,
                queryParameters
            )
        }

    }

}