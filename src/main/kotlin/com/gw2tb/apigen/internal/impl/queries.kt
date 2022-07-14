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

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import kotlin.time.*

internal abstract class QueriesBuilderImplBase<Q : APIQuery, T : APIType> : QueriesBuilder<T> {

    protected abstract val typeRegistry: TypeRegistryScope

    protected abstract val route: String
    protected abstract val endpointTitleCase: String
    protected abstract val querySuffix: String?
    protected abstract val idTypeKey: String?
    protected abstract val summary: String
    protected abstract val cache: Duration?
    protected abstract val security: Security?
    protected abstract val queryTypes: QueryTypes?
    protected abstract val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> T

    protected val pathParameters: MutableMap<String, PathParameter> = mutableMapOf()
    override fun pathParameter(name: String, type: DeferredSchemaType<out SchemaPrimitive>, description: String, key: String, camelCase: String) {
        check(":$key" in (route.split('/')))
        check(key !in pathParameters)

        pathParameters[key] = PathParameter(key, type.getFlat(), description, name, camelCase)
    }

    protected val queryParameters: MutableMap<String, QueryParameter> = mutableMapOf()
    override fun queryParameter(name: String, type: DeferredSchemaType<out SchemaPrimitive>, description: String, key: String, camelCase: String, isOptional: Boolean) {
        check(key !in queryParameters)

        queryParameters[key] = QueryParameter(key, type.getFlat(), description, name, camelCase, isOptional)
    }

    override fun conditional(
        name: String,
        description: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)?,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = conditionalImpl(
        name,
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        apiTypeFactory,
        typeRegistry,
        block
    )

    override fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = recordImpl(
        name,
        description,
        apiTypeFactory,
        typeRegistry,
        block
    )

    abstract fun finalize(): Collection<Q>

}

internal class QueriesBuilderV1Impl(
    override val route: String,
    override val querySuffix: String?,
    override val endpointTitleCase: String,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    override val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> APIType.V1,
    override val typeRegistry: TypeRegistryScope
) : QueriesBuilderImplBase<APIQuery.V1, APIType.V1>(), QueriesBuilderV1 {

    private lateinit var _schema: SchemaTypeUse

    override fun schema(schema: DeferredSchemaType<out SchemaTypeUse>) {
        check(!this::_schema.isInitialized)
        _schema = schema.get(typeRegistry, interpretationHint = null, isTopLevel = true).single().data
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
    override val querySuffix: String?,
    override val endpointTitleCase: String,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    private val since: V2SchemaVersion,
    private val until: V2SchemaVersion?,
    override val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> APIType.V2,
    override val typeRegistry: TypeRegistryScope
) : QueriesBuilderImplBase<APIQuery.V2, APIType.V2>(), QueriesBuilderV2 {

    private lateinit var _schema: SchemaVersionedData<out SchemaTypeUse>

    override fun schema(schema: DeferredSchemaType<out SchemaTypeUse>) {
        check(!this::_schema.isInitialized)
        _schema = schema.get(typeRegistry, interpretationHint = null, isTopLevel = true)
    }

    override fun schema(vararg schemas: Pair<V2SchemaVersion, DeferredSchemaType<out SchemaTypeUse>>) {
        check(!this::_schema.isInitialized)

        /*
         * The method only receives `since` constraints. Thus, we need to ensure that no two constraints are equal
         * before we can continue processing.
         */
        require(schemas.all { a -> schemas.all { b -> a == b || a.first != b.first } })
        require(schemas.none { (version, _) -> version < since })
        require(until == null || schemas.none { (version, _) -> version >= until })

        val deferredTypeRegistry = object : TypeRegistryScope() {
            override fun getLocationFor(name: String): TypeLocation = TypeLocation(nest = null, name)
            override fun register(name: String, value: APIType): TypeLocation = TypeLocation(nest = null, name)
            override fun nestedScope(nestName: String) = typeRegistry.nestedScope(nestName)
        }

        val versions = buildVersionedSchemaData<SchemaTypeUse> {
            schemas.asIterable()
                .sortedBy { it.first }
                .zipSchemaVersionConstraints()
                .forEach { (since, until) ->
                    since.second.get(deferredTypeRegistry, interpretationHint = null, isTopLevel = true).forEach { (schema, schemaSince, schemaUntil) ->
                        // Clamp schema versions against bounds implied by the function call
                        if (since.first >= schemaSince && (until == null || schemaSince < until.first)) {
                            add(
                                datum = schema,
                                since = maxOf(since.first, schemaSince),
                                until = when {
                                    until == null -> schemaUntil
                                    schemaUntil == null -> until.first
                                    else -> minOf(until.first, schemaUntil)
                                }
                            )
                        }
                    }
                }
        }

        if (versions.any { it.data is SchemaTypeReference }) {
            val apiType = apiTypeFactory(buildVersionedSchemaData {
                versions.forEach { (schema, since, until) ->
                    if (schema is SchemaTypeReference)
                        add(schema.declaration, since, until)
                }
            }, null, true)

            typeRegistry.register(apiType.name, apiType)
        }

        _schema = versions
    }

    private fun buildQuery(
        schema: SchemaVersionedData<out SchemaTypeUse>,
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

    override fun finalize(): Collection<APIQuery.V2> = buildList {
        if (queryTypes != null) {
            val idType: SchemaPrimitive = when (val schema = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC].data) {
                is SchemaTypeReference -> when (val declaration = schema.declaration) {
                    is SchemaConditional -> declaration.sharedProperties[idTypeKey]?.type
                    is SchemaRecord -> declaration.properties[idTypeKey]?.type
                }
                else -> error("Cannot extract ID type for key \"$idTypeKey\" from type: ${schema.javaClass}")
            }.let {
                if (it == null) error("Could not find ID member \"$idTypeKey\" for endpoint \"$endpointTitleCase\"")
                it as? SchemaPrimitive ?: error("ID type is not a primitive type: ${it.javaClass}")
            }

            queryTypes.values.forEach { queryType ->
                val schema: SchemaVersionedData<out SchemaTypeUse>

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