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
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import kotlin.time.*

internal abstract class QueriesBuilderImplBase<Q : APIQuery, T : APIType> : QueriesBuilder<T> {

    protected abstract val typeRegistry: TypeRegistryScope

    protected abstract val route: String
    protected abstract val endpointTitleCase: String
    protected abstract val querySuffix: String
    protected abstract val idTypeKey: String?
    protected abstract val summary: String
    protected abstract val cache: Duration?
    protected abstract val security: Security?
    protected abstract val queryTypes: QueryTypes?

    protected val pathParameters: MutableMap<String, PathParameter> = mutableMapOf()
    override fun pathParameter(name: String, type: SchemaPrimitiveReference, description: String, key: String, camelCase: String) {
        check(":$key" in (route.split('/')))
        check(key !in pathParameters)

        pathParameters[key] = PathParameter(key, type.get(typeRegistry), description, name, camelCase)
    }

    protected val queryParameters: MutableMap<String, QueryParameter> = mutableMapOf()
    override fun queryParameter(name: String, type: SchemaPrimitiveReference, description: String, key: String, camelCase: String, isOptional: Boolean) {
        check(key !in queryParameters)

        queryParameters[key] = QueryParameter(key, type.get(typeRegistry), description, name, camelCase, isOptional)
    }

    override fun conditional(
        name: String,
        description: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClassReference = conditionalImpl(
        name,
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        ::createType,
        block
    )

    override fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClassReference = recordImpl(
        name,
        description,
        ::createType,
        block
    )

    abstract fun createType(type: SchemaClass): T

    abstract fun finalize(): Collection<Q>

}

internal class QueriesBuilderV1Impl(
    override val route: String,
    override val querySuffix: String,
    override val endpointTitleCase: String,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    override val typeRegistry: TypeRegistryScope
) : QueriesBuilderImplBase<APIQuery.V1, APIType.V1>(), QueriesBuilderV1 {

    private lateinit var _schema: SchemaType

    override fun schema(schema: SchemaTypeReference) {
        check(!this::_schema.isInitialized)
        _schema = schema.get(typeRegistry)
    }

    override fun createType(type: SchemaClass): APIType.V1 {
        require(type !is SchemaBlueprint)
        return APIType.V1(type)
    }

    override fun finalize(): Collection<APIQuery.V1> = listOf(
        APIQuery.V1(
            route = route,
            endpoint = endpointTitleCase,
            summary = summary,
            pathParameters = pathParameters,
            queryParameters = queryParameters,
            querySuffix = querySuffix,
            cache = cache,
            schema = _schema
        )
    )

}

internal class QueriesBuilderV2Impl(
    override val route: String,
    override val querySuffix: String,
    override val endpointTitleCase: String,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    private val since: V2SchemaVersion,
    private val until: V2SchemaVersion?,
    override val typeRegistry: TypeRegistryScope
) : QueriesBuilderImplBase<APIQuery.V2, APIType.V2>(), QueriesBuilderV2 {

    private lateinit var _schema: SchemaVersionedData<SchemaType>

    override fun schema(schema: SchemaTypeReference) {
        check(!this::_schema.isInitialized)

        val rSchema = schema.get(typeRegistry)
        _schema = buildVersionedSchemaData {
            V2SchemaVersion.values()
                .filter { it == since || it == until || (until != null && it < until && rSchema.hasChangedInVersion(it)) }
                .zipSchemaVersionConstraints(includeUnbound = until == null)
                .forEach { (since, until) -> add(rSchema.copyForVersion(since), since, until) }
        }
    }

    override fun schema(vararg schemas: Pair<V2SchemaVersion, SchemaTypeReference>) {
        check(!this::_schema.isInitialized)

        /*
         * The method only receives `since` constraints. Thus, we need to ensure that no two constraints are equal
         * before we can continue processing.
         */
        require(schemas.all { a -> schemas.all { b -> a == b || a.first != b.first } })
        require(schemas.none { (version, _) -> version < since })
        require(until == null || schemas.none { (version, _) -> version >= until })

        val deferredTypeRegistry = object : TypeRegistryScope() {
            override fun register(name: String, value: APIType) {}
            override fun nestedScope(nestName: String) = typeRegistry.nestedScope(nestName)
        }

        val versions = buildVersionedSchemaData<SchemaType> {
            schemas.asIterable()
                .sortedBy { it.first }
                .zipSchemaVersionConstraints()
                .forEach { (since, until) ->
                    val schema = since.second.get(deferredTypeRegistry)
                    val sinceBound = since.first
                    val untilBound = until?.first

                    V2SchemaVersion.values()
                        .filter { it == sinceBound || it == untilBound || (untilBound != null && it < untilBound && schema.hasChangedInVersion(it)) }
                        .zipSchemaVersionConstraints(includeUnbound = until == null)
                        .forEach { (since, until) -> add(schema.copyForVersion(since), since, until) }
                }
        }

        @Suppress("UNCHECKED_CAST")
        val classes = if (versions.first().data is SchemaClass) APIType.V2(versions as SchemaVersionedData<SchemaClass>) else null
        if (classes != null)
            typeRegistry.register(classes.name, classes)

        _schema = versions
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

    private fun buildQuery(
        schema: SchemaVersionedData<SchemaType>,
        queryParameters: Map<String, QueryParameter>? = null,
        queryDetails: QueryDetails? = null
    ) = APIQuery.V2(
        route = route,
        endpoint = endpointTitleCase,
        summary = summary,
        pathParameters = pathParameters,
        queryParameters = queryParameters ?: this.queryParameters,
        queryDetails = queryDetails,
        querySuffix = querySuffix,
        cache = cache,
        since = since,
        until = until,
        security = security?.scopes ?: emptySet(),
        _schema = schema
    )

    @OptIn(ExperimentalStdlibApi::class)
    override fun finalize(): Collection<APIQuery.V2> = buildList {
        if (queryTypes != null) {
            val idType: SchemaPrimitive = when (val schema = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC].data) {
                is SchemaConditional -> schema.sharedProperties[idTypeKey]?.type
                is SchemaRecord -> schema.properties[idTypeKey]?.type
                else -> error("Cannot extract ID type for key \"$idTypeKey\" from type: ${schema.javaClass}")
            }.let {
                if (it == null) error("Could not find ID member \"$idTypeKey\" for endpoint \"$endpointTitleCase\"")
                it as? SchemaPrimitive ?: error("ID type is not a primitive type: ${it.javaClass}")
            }

            queryTypes.values.forEach { queryType ->
                val schema: SchemaVersionedData<SchemaType>

                val queryParameters = mutableMapOf<String, QueryParameter>()
                queryParameters += this@QueriesBuilderV2Impl.queryParameters

                when (queryType) {
                    is QueryType.IDs -> {
                        schema = buildVersionedSchemaData {
                            add(SchemaArray(idType, false, description = "the available IDs"), since = since, until = until)
                        }
                    }
                    is QueryType.ByID -> {
                        schema = _schema
                        QueryParameter(queryType.qpKey, idType, queryType.qpDescription, queryType.qpName, queryType.qpCamelCase, false)
                            .also { queryParameters[it.key] = it }
                    }
                    is QueryType.ByIDs -> {
                        schema = _schema.mapData { v -> SchemaArray(v, false, "") }
                        QueryParameter(queryType.qpKey, SchemaArray(idType, false, null), queryType.qpDescription, queryType.qpName, queryType.qpCamelCase, false)
                            .also { queryParameters[it.key] = it }
                    }
                    is QueryType.ByPage -> {
                        schema = _schema.mapData { v -> SchemaArray(v, false, "") }
                        QueryParameter(QueryParameter.PAGE_KEY, SchemaInteger(), "the index of the requested page", "Page", "page", false)
                            .also { queryParameters[it.key] = it }
                        QueryParameter(QueryParameter.PAGE_SIZE_KEY, SchemaInteger(), "the size of the requested page", "PageSize", "pageSize", true)
                            .also { queryParameters[it.key] = it }
                    }
                }

                add(buildQuery(
                    schema = schema,
                    queryParameters = queryParameters,
                    queryDetails = QueryDetails(queryType, idType)
                ))
            }
        } else {
            add(buildQuery(_schema))
        }
    }

}