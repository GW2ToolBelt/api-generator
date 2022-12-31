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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.schema.model.APIType
import java.util.*
import kotlin.time.*

internal abstract class SpecBuilderImplBase<E, Q : IRAPIQuery, T : IRAPIType, QB : QueriesBuilder<T>> : SpecBuilder<T> {

    protected val queries = mutableMapOf<E, MutableList<(ScopedTypeRegistry<T>) -> QueriesBuilderImplBase<Q, T>>>()

    abstract val typeRegistry: ScopedTypeRegistry<T>?

    abstract fun createType(
        data: SchemaVersionedDataImpl<out IRTypeDeclaration<*>>,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): T

    override fun conditional(
        name: Name,
        description: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)?,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> =
        SchemaConditionalBuilder(
            name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty,
            sharedConfigure, ::createType, typeRegistry
        ).also(block)

    override fun enum(type: DeferredPrimitiveType, name: Name, description: String, block: SchemaEnumBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        SchemaEnumBuilder(type, name, description, ::createType, typeRegistry).also(block)

    override fun record(name: Name, description: String, block: SchemaRecordBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        SchemaRecordBuilder(name, description, ::createType, typeRegistry).also(block)

    override fun tuple(name: Name, description: String, block: SchemaTupleBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        SchemaTupleBuilder(name, description, ::createType, typeRegistry).also(block)

}

internal class SpecBuilderV1Impl : SpecBuilderImplBase<APIv1Endpoint, IRAPIQuery.V1, IRAPIType.V1, QueriesBuilderV1>(), SpecBuilderV1 {

    private val types = mutableMapOf<QualifiedTypeName.Declaration, IRAPIType.V1>()

    override val typeRegistry = ScopedTypeRegistry<IRAPIType.V1>(
        declarationCollector = { name, value -> types[name] = value }
    )

    @OptIn(LowLevelApiGenApi::class)
    fun build(endpoints: Set<APIv1Endpoint>): IRAPIVersion<IRAPIQuery.V1, IRAPIType.V1> {
        val queries = endpoints.flatMap { endpoint -> queries[endpoint]!!.flatMap { it(typeRegistry).collect() } }.toSet()

        return IRAPIVersion(
            supportedLanguages = EnumSet.of(Language.ENGLISH, Language.FRENCH, Language.GERMAN, Language.SPANISH),
            supportedQueries = queries,
            supportedTypes = types
        )
    }

    override fun APIv1Endpoint.invoke(
        endpointTitleCase: String,
        route: String,
        querySuffix: String?,
        summary: String,
        cache: Duration?,
        security: Security?,
        block: QueriesBuilderV1.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV1Impl(
                route = route,
                querySuffix = querySuffix,
                endpoint = this,
                idTypeKey = null,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = null,
                apiTypeFactory = this@SpecBuilderV1Impl::createType,
                typeRegistry = typeRegistry
            ).also(block)
        }
    }

    override fun APIv1Endpoint.invoke(
        endpointTitleCase: String,
        route: String,
        idTypeKey: String,
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration?,
        security: Security?,
        block: QueriesBuilderV1.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV1Impl(
                route = route,
                querySuffix = null,
                endpoint = this,
                idTypeKey = idTypeKey,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = queryTypes,
                apiTypeFactory = this@SpecBuilderV1Impl::createType,
                typeRegistry = typeRegistry
            ).also(block)
        }
    }

    override fun createType(
        data: SchemaVersionedDataImpl<out IRTypeDeclaration<*>>,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): IRAPIType.V1 =
        IRAPIType.V1(data.single().data, interpretationHint, isTopLevel)

}

internal class SpecBuilderV2Impl : SpecBuilderImplBase<APIv2Endpoint, IRAPIQuery.V2, IRAPIType.V2, QueriesBuilderV2>(), SpecBuilderV2 {

    private val types = mutableMapOf<QualifiedTypeName.Declaration, IRAPIType.V2>()

    override val typeRegistry = ScopedTypeRegistry<IRAPIType.V2>(
        declarationCollector = { name, value -> types[name] = value }
    )

    fun build(endpoints: Set<APIv2Endpoint>): IRAPIVersion<IRAPIQuery.V2, IRAPIType.V2> {
        val queries = endpoints.flatMap { endpoint -> queries[endpoint]!!.flatMap { it(typeRegistry).collect() } }.toSet()

        return IRAPIVersion(
            supportedLanguages = EnumSet.allOf(Language::class.java),
            supportedQueries = queries,
            supportedTypes = types
        )
    }

    override fun APIv2Endpoint.invoke(
        endpointTitleCase: String,
        route: String,
        querySuffix: String?,
        summary: String,
        cache: Duration?,
        security: Security?,
        since: SchemaVersion,
        until: SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV2Impl(
                route = route,
                querySuffix = querySuffix,
                endpoint = this,
                idTypeKey = null,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = null,
                since = since,
                until = until,
                apiTypeFactory = this@SpecBuilderV2Impl::createType,
                typeRegistry = typeRegistry
            ).also(block)
        }
    }

    override fun APIv2Endpoint.invoke(
        endpointTitleCase: String,
        route: String,
        idTypeKey: String,
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration?,
        security: Security?,
        since: SchemaVersion,
        until: SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV2Impl(
                route = route,
                querySuffix = null,
                endpoint = this,
                idTypeKey = idTypeKey,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = queryTypes,
                since = since,
                until = until,
                apiTypeFactory = this@SpecBuilderV2Impl::createType,
                typeRegistry = typeRegistry
            ).also(block)
        }
    }

    override fun createType(
        data: SchemaVersionedDataImpl<out IRTypeDeclaration<*>>,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): IRAPIType.V2 =
        IRAPIType.V2(data, interpretationHint, isTopLevel)

}