/*
 * Copyright (c) 2019-2023 Leon Linhart
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

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.IRAPIQuery
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.ir.model.IRPathParameter
import com.gw2tb.apigen.ir.model.IRQueryParameter
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.schema.model.APIType
import com.gw2tb.apigen.schema.model.QueryParameter
import kotlin.time.*

internal abstract class QueriesBuilderImplBase<Q : IRAPIQuery, T : IRAPIType> : QueriesBuilder<T> {

    protected abstract val typeRegistry: ScopedTypeRegistry<T>

    protected abstract val route: String
    protected abstract val endpoint: APIEndpoint
    protected abstract val querySuffix: String?
    protected abstract val idTypeKey: String?
    protected abstract val summary: String
    protected abstract val cache: Duration?
    protected abstract val security: Security?
    protected abstract val queryTypes: QueryTypes?
    protected abstract val apiTypeFactory: (SchemaVersionedDataImpl<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> T

    protected val pathParameters: MutableMap<String, IRPathParameter> = mutableMapOf()
    override fun pathParameter(name: String, type: DeferredSchemaType<out IRPrimitive>, description: String, key: String, camelCase: String) {
        check(":$key" in (route.split('/')))
        check(key !in pathParameters)

        pathParameters[key] = IRPathParameter(
            key = key,
            type = type.getFlat(),
            name = Name.derive(camelCase = camelCase, titleCase = name),
            description = description
        )
    }

    protected val queryParameters: MutableMap<String, IRQueryParameter> = mutableMapOf()
    override fun queryParameter(name: String, type: DeferredSchemaType<out IRPrimitive>, description: String, key: String, camelCase: String, isOptional: Boolean) {
        check(key !in queryParameters)

        queryParameters[key] = IRQueryParameter(
            key = key,
            type = type.getFlat(),
            name = Name.derive(camelCase = camelCase, titleCase = name),
            description = description,
            isOptional = isOptional
        )
    }

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
            sharedConfigure, apiTypeFactory, typeRegistry
        ).also(block)

    override fun enum(
        type: DeferredPrimitiveType,
        name: Name,
        description: String,
        block: SchemaEnumBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> =
        SchemaEnumBuilder(type, name, description, apiTypeFactory, typeRegistry).also(block)

    override fun record(
        name: Name,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> =
        SchemaRecordBuilder(name, description, apiTypeFactory, typeRegistry).also(block)

    override fun tuple(
        name: Name,
        description: String,
        block: SchemaTupleBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> =
        SchemaTupleBuilder(name, description, apiTypeFactory, typeRegistry).also(block)

    abstract fun collect(): Collection<Q>

}

internal class QueriesBuilderV1Impl(
    override val route: String,
    override val querySuffix: String?,
    override val endpoint: APIEndpoint,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    override val apiTypeFactory: (SchemaVersionedDataImpl<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> IRAPIType.V1,
    override val typeRegistry: ScopedTypeRegistry<IRAPIType.V1>
) : QueriesBuilderImplBase<IRAPIQuery.V1, IRAPIType.V1>(), QueriesBuilderV1 {

    private lateinit var _schema: IRTypeUse<*>

    override fun schema(schema: DeferredSchemaType<out IRTypeUse<*>>) {
        check(!this::_schema.isInitialized)
        _schema = schema.get(typeRegistry, interpretationHint = null, isTopLevel = true).single().data
    }

    override fun collect(): Collection<IRAPIQuery.V1> = listOf(
        IRAPIQuery.V1(
            path = route,
            endpoint = endpoint,
            summary = summary,
            pathParameters = pathParameters,
            queryParameters = queryParameters,
            querySuffix = querySuffix,
            cache = cache,
            type = _schema
        )
    )

}

internal class QueriesBuilderV2Impl(
    override val route: String,
    override val querySuffix: String?,
    override val endpoint: APIEndpoint,
    override val idTypeKey: String?,
    override val summary: String,
    override val cache: Duration?,
    override val security: Security?,
    override val queryTypes: QueryTypes?,
    private val since: SchemaVersion,
    private val until: SchemaVersion?,
    override val apiTypeFactory: (SchemaVersionedDataImpl<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> IRAPIType.V2,
    override val typeRegistry: ScopedTypeRegistry<IRAPIType.V2>
) : QueriesBuilderImplBase<IRAPIQuery.V2, IRAPIType.V2>(), QueriesBuilderV2 {

    private lateinit var _schema: SchemaVersionedDataImpl<out IRTypeUse<*>>

    override fun schema(schema: DeferredSchemaType<out IRTypeUse<*>>) {
        check(!this::_schema.isInitialized)
        _schema = schema.get(typeRegistry, interpretationHint = null, isTopLevel = true)
    }

    override fun schema(vararg schemas: Pair<SchemaVersion, DeferredSchemaType<out IRTypeUse<*>>>) {
        check(!this::_schema.isInitialized)

        /*
         * The method only receives `since` constraints. Thus, we need to ensure that no two constraints are equal
         * before we can continue processing.
         */
        require(schemas.all { a -> schemas.all { b -> a == b || a.first != b.first } })
        require(schemas.none { (version, _) -> version < since })
        require(until == null || schemas.none { (version, _) -> version >= until })

        val deferredTypeRegistry = ScopedTypeRegistry<IRAPIType.V2>(declarationCollector = { _, _ -> })

        val versions = buildVersionedSchemaData<IRTypeUse<*>> {
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

        if (versions.any { it.data is IRTypeReference.Declaration }) {
            val apiType = apiTypeFactory(buildVersionedSchemaData {
                versions.forEach { (schema, since, until) ->
                    if (schema is IRTypeReference.Declaration)
                        add(schema.declaration, since, until)
                }
            }, null, true)

            typeRegistry.registerDeclaration(apiType.name, apiType)
        }

        _schema = versions
    }

    private fun buildQuery(
        schema: SchemaVersionedDataImpl<out IRTypeUse<*>>,
        queryParameters: Map<String, IRQueryParameter>? = null,
        queryDetails: IRAPIQuery.Details? = null
    ) = IRAPIQuery.V2(
        path = route,
        endpoint = endpoint,
        summary = summary,
        pathParameters = pathParameters,
        queryParameters = queryParameters ?: this.queryParameters,
        details = queryDetails,
        querySuffix = querySuffix,
        cache = cache,
        since = since,
        until = until,
        security = security?.scopes ?: emptySet(),
        _type = schema
    )

    override fun collect(): Collection<IRAPIQuery.V2> = buildList {
        if (queryTypes != null) {
            val idType: IRPrimitiveIdentifier = when (val schema = _schema.getOrThrow(SchemaVersion.V2_SCHEMA_CLASSIC).data) {
                is IRTypeReference.Declaration -> when (val declaration = schema.declaration) {
                    is IRConditional -> declaration.sharedProperties.find { it.serialName == idTypeKey }?.type
                    is IRRecord -> declaration.properties.find { it.serialName == idTypeKey }?.type
                    else -> error("Cannot extract ID type for key \"$idTypeKey\" from type: ${schema.javaClass}")
                }
                else -> error("Cannot extract ID type for key \"$idTypeKey\" from type: ${schema.javaClass}")
            }.let {
                if (it == null) error("Could not find ID member \"$idTypeKey\" for endpoint \"$endpoint\"")
                it as? IRPrimitiveIdentifier ?: error("ID type is not a primitive identifier type: ${it.javaClass}")
            }

            queryTypes.values.forEach { queryType ->
                val schema: SchemaVersionedDataImpl<out IRTypeUse<*>>

                val queryParameters = mutableMapOf<String, IRQueryParameter>()
                queryParameters += this@QueriesBuilderV2Impl.queryParameters

                when (queryType) {
                    is QueryType.IDs -> {
                        schema = buildVersionedSchemaData {
                            add(IRArray(idType, false, description = "the available IDs"), since = since, until = until)
                        }
                    }
                    is QueryType.ByID -> {
                        schema = _schema
                        IRQueryParameter(queryType.qpKey, idType, queryType.qpName, queryType.qpDescription, false)
                            .also { queryParameters[it.key] = it }
                    }
                    is QueryType.ByIDs -> {
                        schema = _schema.mapData { v -> IRArray(v, false, "") }
                        IRQueryParameter(queryType.qpKey, IRArray(idType, false, null), queryType.qpName, queryType.qpDescription, false)
                            .also { queryParameters[it.key] = it }
                    }
                    is QueryType.ByPage -> {
                        schema = _schema.mapData { v -> IRArray(v, false, "") }
                        IRQueryParameter(QueryParameter.PAGE_KEY, IRInteger, Name.derive(titleCase = "Page"), "the index of the requested page", false)
                            .also { queryParameters[it.key] = it }
                        IRQueryParameter(QueryParameter.PAGE_SIZE_KEY, IRInteger, Name.derive(titleCase = "PageSize"), "the size of the requested page", true)
                            .also { queryParameters[it.key] = it }
                    }
                }

                add(buildQuery(
                    schema = schema,
                    queryParameters = queryParameters,
                    queryDetails = IRAPIQuery.Details(queryType, idType)
                ))
            }
        } else {
            add(buildQuery(_schema))
        }
    }

}