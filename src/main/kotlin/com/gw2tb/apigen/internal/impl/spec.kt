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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*
import kotlin.time.*

internal abstract class SpecBuilderImplBase<E, Q : APIQuery, T : APIType, QB : QueriesBuilder<T>> : SpecBuilder<T> {

    protected val queries = mutableMapOf<E, MutableList<(TypeRegistryScope) -> QueriesBuilderImplBase<Q, T>>>()

    abstract val typeRegistry: TypeRegistryScope?

    abstract fun createType(
        data: SchemaVersionedData<out SchemaTypeDeclaration>,
        interpretationHint: InterpretationHint?,
        isTopLevel: Boolean
    ): T

    override fun String.invoke(type: DeferredPrimitiveType, camelCaseName: String): DeferredPrimitiveType =
        type.withTypeHint(SchemaPrimitive.TypeHint(camelCaseName))

    override fun conditional(
        name: String,
        description: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)?,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = conditionalImpl(
        name,
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        ::createType,
        typeRegistry,
        configure
    )

    override fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = recordImpl(
        name,
        description,
        ::createType,
        typeRegistry,
        block
    )

}

internal class SpecBuilderV1Impl : SpecBuilderImplBase<APIv1Endpoint, APIQuery.V1, APIType.V1, QueriesBuilderV1>(), SpecBuilderV1 {

    private val types = mutableMapOf<TypeLocation, APIType.V1>()

    override val typeRegistry = object : TypeRegistryScope() {

        override fun getLocationFor(name: String): TypeLocation =
            TypeLocation(
                nest = if ("/" in name) name.substringBeforeLast('/') else null,
                name = name.substringAfterLast('/')
            )

        override fun register(name: String, value: APIType): TypeLocation {
            val location = getLocationFor(name)
            types[location] = (value as APIType.V1)

            return location
        }

    }

    fun build(endpoints: Set<APIv1Endpoint>): APIVersion<APIQuery.V1, APIType.V1> {
        val queries = endpoints.flatMap { endpoint -> queries[endpoint]!!.flatMap { it(typeRegistry).finalize() } }.toSet()

        return APIVersion(
            supportedLanguages = EnumSet.of(Language.ENGLISH, Language.FRENCH, Language.GERMAN, Language.SPANISH),
            supportedQueries = queries,
            supportedTypes = types,
            version = "v1"
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
                endpointTitleCase = endpointTitleCase,
                idTypeKey = null,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = null,
                this@SpecBuilderV1Impl::createType,
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
                endpointTitleCase = endpointTitleCase,
                idTypeKey = idTypeKey,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = queryTypes,
                this@SpecBuilderV1Impl::createType,
                typeRegistry = typeRegistry
            ).also(block)
        }
    }

    override fun createType(
        data: SchemaVersionedData<out SchemaTypeDeclaration>,
        interpretationHint: InterpretationHint?,
        isTopLevel: Boolean
    ): APIType.V1 =
        APIType.V1(data.single().data, interpretationHint, isTopLevel)

}

internal class SpecBuilderV2Impl : SpecBuilderImplBase<APIv2Endpoint, APIQuery.V2, APIType.V2, QueriesBuilderV2>(), SpecBuilderV2 {

    private val types = mutableMapOf<TypeLocation, APIType.V2>()

    override val typeRegistry = object : TypeRegistryScope() {

        override fun getLocationFor(name: String): TypeLocation =
            TypeLocation(
                nest = if ("/" in name) name.substringBeforeLast('/') else null,
                name = name.substringAfterLast('/')
            )

        override fun register(name: String, value: APIType): TypeLocation {
            val location = getLocationFor(name)
            types[location] = (value as APIType.V2)

            return location
        }

    }

    fun build(endpoints: Set<APIv2Endpoint>): APIVersion<APIQuery.V2, APIType.V2> {
        val queries = endpoints.flatMap { endpoint -> queries[endpoint]!!.flatMap { it(typeRegistry).finalize() } }.toSet()

        return APIVersion(
            supportedLanguages = EnumSet.allOf(Language::class.java),
            supportedQueries = queries,
            supportedTypes = types,
            version = "v2"
        )
    }

    override fun APIv2Endpoint.invoke(
        endpointTitleCase: String,
        route: String,
        querySuffix: String?,
        summary: String,
        cache: Duration?,
        security: Security?,
        since: V2SchemaVersion,
        until: V2SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV2Impl(
                route = route,
                querySuffix = querySuffix,
                endpointTitleCase = endpointTitleCase,
                idTypeKey = null,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = null,
                since = since,
                until = until,
                this@SpecBuilderV2Impl::createType,
                typeRegistry = typeRegistry,
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
        since: V2SchemaVersion,
        until: V2SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        queries.computeIfAbsent(this) { mutableListOf() }.add { typeRegistry ->
            QueriesBuilderV2Impl(
                route = route,
                querySuffix = null,
                endpointTitleCase = endpointTitleCase,
                idTypeKey = idTypeKey,
                summary = summary,
                cache = cache,
                security = security,
                queryTypes = queryTypes,
                since = since,
                until = until,
                this@SpecBuilderV2Impl::createType,
                typeRegistry = typeRegistry,
            ).also(block)
        }
    }

    override fun createType(
        data: SchemaVersionedData<out SchemaTypeDeclaration>,
        interpretationHint: InterpretationHint?,
        isTopLevel: Boolean
    ): APIType.V2 = APIType.V2(data, interpretationHint, isTopLevel)

}