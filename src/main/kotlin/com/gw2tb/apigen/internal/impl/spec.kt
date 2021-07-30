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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.internal.dsl.SpecBuilderV2
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.ArrayList
import java.util.HashMap
import kotlin.time.*

internal abstract class SpecBuilderImplBase<Q : APIQuery, T : APIType, QB : QueriesBuilder<T>> : SpecBuilder<T> {

    protected val _queries = mutableListOf<QueriesBuilderImplBase<Q, T>>()
    val queries get() = _queries.flatMap { it.finalize() }.toSet()

    protected val _types = mutableMapOf<TypeLocation, MutableList<T>>()
    val types get(): Map<TypeLocation, List<T>> = HashMap(_types.mapValues { (_, v) -> ArrayList(v) })

    abstract fun createType(type: SchemaClass): T

    override fun conditional(
        name: String,
        description: String,
        endpoint: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, ::createType, configure)
        types.forEach { (k, v) -> _types.computeIfAbsent(TypeLocation(endpoint, k)) { mutableListOf() }.addAll(v) }

        return type
    }

    override fun record(
        name: String,
        description: String,
        endpoint: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = recordImpl(name, description, types, ::createType, block)
        types.forEach { (k, v) -> _types.computeIfAbsent(TypeLocation(endpoint, k)) { mutableListOf() }.addAll(v) }

        return type
    }

}

internal class SpecBuilderV1Impl : SpecBuilderImplBase<APIQuery.V1, APIType.V1, QueriesBuilderV1>(), SpecBuilderV1 {

    override fun String.invoke(
        endpointTitleCase: String,
        querySuffix: String,
        summary: String,
        cache: Duration?,
        security: Security?,
        block: QueriesBuilderV1.() -> Unit
    ) {
        QueriesBuilderV1Impl(
            route = this,
            querySuffix = querySuffix,
            endpointTitleCase = endpointTitleCase,
            idTypeKey = null,
            summary = summary,
            cache = cache,
            security = security,
            queryTypes = null
        )
            .also(block)
            .also {
                _queries.add(it)

                /* Register the types registered in the queries context. */
                it.types.forEach { (loc, types) -> _types.computeIfAbsent(loc) { mutableListOf() }.addAll(types) }
            }
    }

    override fun String.invoke(
        endpointTitleCase: String,
        idTypeKey: String,
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration?,
        security: Security?,
        block: QueriesBuilderV1.() -> Unit
    ) {
        QueriesBuilderV1Impl(
            route = this,
            querySuffix = "",
            endpointTitleCase = endpointTitleCase,
            idTypeKey = idTypeKey,
            summary = summary,
            cache = cache,
            security = security,
            queryTypes = queryTypes
        )
            .also(block)
            .also {
                _queries.add(it)

                /* Register the types registered in the queries context. */
                it.types.forEach { (loc, types) -> _types.computeIfAbsent(loc) { mutableListOf() }.addAll(types) }
            }
    }

    override fun createType(type: SchemaClass): APIType.V1 {
        require(type !is SchemaBlueprint)
        return APIType.V1(type)
    }

}

internal class SpecBuilderV2Impl : SpecBuilderImplBase<APIQuery.V2, APIType.V2, QueriesBuilderV2>(), SpecBuilderV2 {

    override fun String.invoke(
        endpointTitleCase: String,
        querySuffix: String,
        summary: String,
        cache: Duration?,
        security: Security?,
        since: V2SchemaVersion,
        until: V2SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        QueriesBuilderV2Impl(
            route = this,
            querySuffix = querySuffix,
            endpointTitleCase = endpointTitleCase,
            idTypeKey = null,
            summary = summary,
            cache = cache,
            security = security,
            queryTypes = null,
            since = since,
            until = until
        )
            .also(block)
            .also {
                _queries.add(it)

                /* Register the types registered in the queries context. */
                it.types.forEach { (loc, types) ->
                    _types.computeIfAbsent(loc) { mutableListOf() }.addAll(types.map { type ->
                        /*
                         * Trim the type's version constraints here using the queries constraints.
                         *
                         * 1. The type's "oldest" `since` constraint should match the queries `since` constraint.
                         * 2. The type's "newest" `until` constraint should match the queries `until` constraint.
                         */
                        val versionedData = type._schema
                        val readjustedVersionedData = versionedData.readjustConstraints(since, until)

                        if (versionedData == readjustedVersionedData) {
                            type
                        } else {
                            APIType.V2(readjustedVersionedData)
                        }
                    })
                }
            }
    }

    override fun String.invoke(
        endpointTitleCase: String,
        idTypeKey: String,
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration?,
        security: Security?,
        since: V2SchemaVersion,
        until: V2SchemaVersion?,
        block: QueriesBuilderV2.() -> Unit
    ) {
        QueriesBuilderV2Impl(
            route = this,
            querySuffix = "",
            endpointTitleCase = endpointTitleCase,
            idTypeKey = idTypeKey,
            summary = summary,
            cache = cache,
            security = security,
            queryTypes = queryTypes,
            since = since,
            until = until
        )
            .also(block)
            .also {
                _queries.add(it)

                /* Register the types registered in the queries context. */
                it.types.forEach { (loc, types) ->
                    _types.computeIfAbsent(loc) { mutableListOf() }.addAll(types.map { type ->
                        /*
                         * Trim the type's version constraints here using the queries constraints.
                         *
                         * 1. The type's "oldest" `since` constraint should match the queries `since` constraint.
                         * 2. The type's "newest" `until` constraint should match the queries `until` constraint.
                         */
                        val versionedData = type._schema
                        val readjustedVersionedData = versionedData.readjustConstraints(since, until)

                        if (versionedData == readjustedVersionedData) {
                            type
                        } else {
                            APIType.V2(readjustedVersionedData)
                        }
                    })
                }
            }
    }

    override fun createType(type: SchemaClass): APIType.V2 = APIType.V2(buildVersionedSchemaData {
        when (type) {
            is SchemaBlueprint -> type.versions.forEach { type, since, until ->
                if (type !is SchemaClass) return@forEach // TODO Should we instead extract the first nested class?
                add(type, since, until)
            }
            else -> add(type)
        }
    })

}