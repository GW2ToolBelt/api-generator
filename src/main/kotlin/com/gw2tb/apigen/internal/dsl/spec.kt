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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import kotlin.time.*

@Suppress("FunctionName")
private fun <Q : APIQuery, T : APIType, S : SpecBuilderImplBase<Q, T, *>> GW2APISpec(builder: S, block: S.() -> Unit): () -> Pair<Map<TypeLocation, List<T>>, Set<Q>> =
    fun() = builder.also(block).let { it.types to it.queries }

@Suppress("FunctionName")
internal fun GW2APISpecV1(block: SpecBuilderV1.() -> Unit): () -> Pair<Map<TypeLocation, List<APIType.V1>>, Set<APIQuery.V1>> =
    GW2APISpec(SpecBuilderV1Impl(), block)

@Suppress("FunctionName")
internal fun GW2APISpecV2(block: SpecBuilderV2.() -> Unit): () -> Pair<Map<TypeLocation, List<APIType.V2>>, Set<APIQuery.V2>> =
    GW2APISpec(SpecBuilderV2Impl(), block)

@APIGenDSL
internal interface SpecBuilder<T : APIType> {

    fun array(
        items: SchemaType,
        description: String,
        nullableItems: Boolean = false
    ): SchemaType =
        SchemaArray(items, nullableItems, description)

    fun map(
        keys: SchemaPrimitive,
        values: SchemaType,
        description: String,
        nullableValues: Boolean = false
    ): SchemaType =
        SchemaMap(keys, values, nullableValues, description)

    fun conditional(
        name: String,
        description: String,
        endpoint: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass

    fun record(
        name: String,
        description: String,
        endpoint: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass

}

internal interface SpecBuilderV1 : SpecBuilder<APIType.V1> {

    operator fun String.invoke(
        endpointTitleCase: String = this,
        querySuffix: String = "",
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

    operator fun String.invoke(
        endpointTitleCase: String = this,
        idTypeKey: String = "id",
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

}

internal interface SpecBuilderV2 : SpecBuilder<APIType.V2> {

    operator fun String.invoke(
        endpointTitleCase: String = this,
        querySuffix: String = "",
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        since: V2SchemaVersion = V2SchemaVersion.V2_SCHEMA_CLASSIC,
        until: V2SchemaVersion? = null,
        block: QueriesBuilderV2.() -> Unit
    )

    operator fun String.invoke(
        endpointTitleCase: String = this,
        idTypeKey: String = "id",
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration? = null,
        security: Security? = null,
        since: V2SchemaVersion = V2SchemaVersion.V2_SCHEMA_CLASSIC,
        until: V2SchemaVersion? = null,
        block: QueriesBuilderV2.() -> Unit
    )

}

@OptIn(ExperimentalStdlibApi::class)
internal fun defaultQueryTypes(all: Boolean = false): QueryTypes = QueryTypes(buildSet {
    add(QueryType.IDs)
    add(QueryType.ByID)
    add(QueryType.ByIDs(supportsAll = all))
    add(QueryType.ByPage)
})

@OptIn(ExperimentalStdlibApi::class)
internal fun queryTypes(first: QueryType, vararg values: QueryType): QueryTypes = QueryTypes(buildSet {
    add(first)
    addAll(values)
})

internal inline class QueryTypes(val values: Set<QueryType>)

internal fun security(vararg scopes: TokenScope): Security = Security(setOf(*scopes))

internal inline class Security(val scopes: Set<TokenScope>)