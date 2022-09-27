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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import kotlin.time.*

@Suppress("FunctionName")
private fun <E, Q : APIQuery, T : APIType, S : SpecBuilderImplBase<E, Q, T, *>> GW2APISpec(builder: S, block: S.() -> Unit): S =
    builder.also(block)

@Suppress("FunctionName")
internal fun GW2APISpecV1(block: SpecBuilderV1.() -> Unit): SpecBuilderV1Impl =
    GW2APISpec(SpecBuilderV1Impl(), block)

@Suppress("FunctionName")
internal fun GW2APISpecV2(block: SpecBuilderV2.() -> Unit): SpecBuilderV2Impl =
    GW2APISpec(SpecBuilderV2Impl(), block)

@APIGenDSL
internal interface SpecBuilder<T : APIType> {

    operator fun String.invoke(type: DeferredPrimitiveType, camelCaseName: String = this): DeferredPrimitiveType

    fun array(
        items: DeferredSchemaType<out SchemaTypeUse>,
        description: String,
        nullableItems: Boolean = false
    ): DeferredSchemaType<SchemaArray> =
        DeferredSchemaType { typeRegistry, isTopLevel -> items.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { SchemaArray(it, nullableItems, description) } }

    fun map(
        keys: DeferredSchemaType<out SchemaPrimitive>,
        values: DeferredSchemaType<out SchemaTypeUse>,
        description: String,
        nullableValues: Boolean = false
    ): DeferredSchemaType<SchemaMap> =
        DeferredSchemaType { typeRegistry, isTopLevel -> values.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { SchemaMap(keys.getFlat(), it, nullableValues, description) } }

    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>

    fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>

}

internal interface SpecBuilderV1 : SpecBuilder<APIType.V1> {

    operator fun APIv1Endpoint.invoke(
        endpointTitleCase: String = endpointName,
        route: String = endpointName,
        querySuffix: String? = null,
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

    operator fun APIv1Endpoint.invoke(
        endpointTitleCase: String = endpointName,
        route: String = endpointName,
        idTypeKey: String = "id",
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

}

internal interface SpecBuilderV2 : SpecBuilder<APIType.V2> {

    operator fun APIv2Endpoint.invoke(
        endpointTitleCase: String = endpointName,
        route: String = endpointName,
        querySuffix: String? = null,
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        since: V2SchemaVersion = V2SchemaVersion.V2_SCHEMA_CLASSIC,
        until: V2SchemaVersion? = null,
        block: QueriesBuilderV2.() -> Unit
    )

    operator fun APIv2Endpoint.invoke(
        endpointTitleCase: String = endpointName,
        route: String = endpointName,
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

internal fun defaultQueryTypes(all: Boolean = false): QueryTypes = QueryTypes(buildSet {
    add(QueryType.IDs)
    add(QueryType.ByID)
    add(QueryType.ByIDs(supportsAll = all))
    add(QueryType.ByPage)
})

internal fun queryTypes(first: QueryType, vararg values: QueryType): QueryTypes = QueryTypes(buildSet {
    add(first)
    addAll(values)
})

internal data class QueryTypes(val values: Set<QueryType>)

internal fun security(vararg scopes: TokenScope): Security = Security(setOf(*scopes))

internal data class Security(val scopes: Set<TokenScope>)