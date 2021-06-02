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
@file:Suppress("PropertyName")
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*
import kotlin.time.*

@DslMarker
internal annotation class APIGenDSL

@Suppress("FunctionName")
internal fun <Q : APIQuery, B : APIVersionBuilder<Q, T>, T : APIType> GW2APIVersion(
    builder: () -> B,
    configure: B.() -> Unit
): () -> Pair<Map<TypeLocation, List<T>>, Set<Q>> =
    fun() = builder().also(configure).let { it.types to it.queries }

@APIGenDSL
internal sealed class APIVersionBuilder<Q : APIQuery, T : APIType> {

    protected val _queries = mutableListOf<APIQueryBuilder<Q, T>>()
    val queries get() = _queries.flatMap { it.finalize() }.toSet()

    protected val _types = mutableMapOf<TypeLocation, MutableList<T>>()
    val types get(): Map<TypeLocation, List<T>> = HashMap(_types.mapValues { (_, v) -> ArrayList(v) })

    @APIGenDSL
    fun array(
        items: SchemaType,
        description: String,
        nullableItems: Boolean = false
    ): SchemaType =
        SchemaArray(items, nullableItems, description)

    @APIGenDSL
    fun map(
        keys: SchemaPrimitive,
        values: SchemaType,
        description: String,
        nullableValues: Boolean = false
    ): SchemaType =
        SchemaMap(keys, values, nullableValues, description)

//    @APIGenDSL
//    fun record(
//        name: String,
//        description: String,
//        configure: SchemaRecordBuilder<T>.() -> Unit
//    ): SchemaClass {
//        val types = mutableMapOf<String, MutableList<T>>()
//        val type = recordImpl(name, description, types, ::createType, configure)
//        types.forEach { _types.computeIfAbsent(TypeLocation(null, it.key)) { mutableListOf() }.addAll(it.value) }
//
//        return type
//    }
//
//    @APIGenDSL
//    fun conditional(
//        name: String,
//        description: String,
//        disambiguationBy: String = "type",
//        disambiguationBySideProperty: Boolean = false,
//        interpretationInNestedProperty: Boolean = false,
//        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
//        configure: SchemaConditionalBuilder<T>.() -> Unit
//    ): SchemaClass {
//        val types = mutableMapOf<String, MutableList<T>>()
//        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, ::createType, configure)
//        types.forEach { _types.computeIfAbsent(TypeLocation(null, it.key)) { mutableListOf() }.addAll(it.value) }
//
//        return type
//    }

    abstract fun createType(type: SchemaClass): T

    class V1 : APIVersionBuilder<APIQuery.V1, APIType.V1>() {

        operator fun String.invoke(
            endpoint: String = this,
            configure: APIQueryBuilder.V1.() -> Unit
        ) =
            APIQueryBuilder.V1(::createType, this, endpoint)
                .also(configure)
                .also {
                    _queries.add(it)

                    /* Register the types registered in the queries context. */
                    it.types.forEach { (loc, types) -> _types.computeIfAbsent(loc) { mutableListOf() }.addAll(types) }
                }

        override fun createType(type: SchemaClass): APIType.V1 {
            require(type !is SchemaBlueprint)
            return APIType.V1(type)
        }

    }

    class V2 : APIVersionBuilder<APIQuery.V2, APIType.V2>() {

        operator fun String.invoke(
            endpoint: String = this,
            since: V2SchemaVersion = V2SchemaVersion.V2_SCHEMA_CLASSIC,
            until: V2SchemaVersion? = null,
            configure: APIQueryBuilder.V2.() -> Unit
        ) =
            APIQueryBuilder.V2(::createType, this, endpoint, since, until)
                .also(configure)
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

}

private fun SchemaConditional.Interpretation.copyForVersion(version: V2SchemaVersion): SchemaConditional.Interpretation = copy(
    type = if (type is SchemaBlueprint) {
        type.versions[version].data
    } else {
        type
    }
)

private fun SchemaRecord.Property.copyForVersion(version: V2SchemaVersion): SchemaRecord.Property = copy(
    type = if (type is SchemaBlueprint) {
        type.versions[version].data
    } else {
        type
    }
)

private fun <T : APIType> conditionalImpl(
    name: String,
    description: String,
    disambiguationBy: String,
    disambiguationBySideProperty: Boolean,
    interpretationInNestedProperty: Boolean,
    sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
    types: MutableMap<String?, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaConditionalBuilder<T>.() -> Unit
): SchemaClass {
    val bSharedProps = sharedConfigure?.let { SchemaRecordBuilder<T>(name).also(it) }
    val bInterpretations = SchemaConditionalBuilder<T>().also(configure)

    val sharedProps = bSharedProps?.properties ?: emptyList()
    val interpretations = bInterpretations.interpretations

    bSharedProps?.nestedTypes?.forEach { (k, v) -> types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }.addAll(v) }
    bInterpretations.nestedTypes.forEach { (k, v) ->
        types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }
            .addAll(v.filter { apiType ->
                interpretations.none {
                    it.type is SchemaClass && (apiType.name == it.type.name)
                }
            }
        )
    }

    val propVersions = mutableMapOf<V2SchemaVersion, Map<String, SchemaRecord.Property>?>()
    propVersions[V2SchemaVersion.V2_SCHEMA_CLASSIC] = sharedProps.getForVersion(
        SchemaRecord.Property::since,
        SchemaRecord.Property::until,
        SchemaRecord.Property::serialName,
        V2SchemaVersion.V2_SCHEMA_CLASSIC
    ).mapValues { (_, property) -> property.copyForVersion(V2SchemaVersion.V2_SCHEMA_CLASSIC) }

    val versions = buildVersionedSchemaData<SchemaType> {
        V2SchemaVersion.values()
            .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || sharedProps.any { it.hasChangedInVersion(version) } || interpretations.any { it.hasChangedInVersion(version) } }
            .zipSchemaVersionConstraints()
            .forEach { (since, until) ->
                add(
                    datum = SchemaConditional(
                        name,
                        disambiguationBy,
                        disambiguationBySideProperty,
                        interpretationInNestedProperty,
                        sharedProps.getForVersion(
                            SchemaRecord.Property::since,
                            SchemaRecord.Property::until,
                            SchemaRecord.Property::serialName,
                            since
                        ).mapValues { (_, property) -> property.copyForVersion(since) },
                        interpretations.getForVersion(
                            SchemaConditional.Interpretation::since,
                            SchemaConditional.Interpretation::until,
                            SchemaConditional.Interpretation::interpretationKey,
                            since
                        ).mapValues { (_, intrp) -> intrp.copyForVersion(since) },
                        description
                    ),
                    since = since,
                    until = until
                )
            }
    }

    return (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaConditional
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent(null) { mutableListOf() }.add(apiTypeFactory(it))
    }
}

private fun <T : APIType> recordImpl(
    name: String,
    description: String,
    types: MutableMap<String?, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaRecordBuilder<T>.() -> Unit
): SchemaClass {
    val bProperties = SchemaRecordBuilder<T>(name).also(configure)
    bProperties.nestedTypes.forEach { (k, v) -> types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }.addAll(v) }

    val properties = bProperties.properties
    val versions = buildVersionedSchemaData<SchemaType> {
        V2SchemaVersion.values()
            .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || properties.any { it.hasChangedInVersion(version) } }
            .zipSchemaVersionConstraints()
            .forEach { (since, until) ->
                add(
                    datum = SchemaRecord(
                        name,
                        properties.getForVersion(
                            SchemaRecord.Property::since,
                            SchemaRecord.Property::until,
                            SchemaRecord.Property::serialName,
                            since
                        ).mapValues { (_, property) -> property.copyForVersion(since) },
                        description
                    ),
                    since = since,
                    until = until
                )
            }
    }

    return (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaRecord
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent(null) { mutableListOf() }.add(apiTypeFactory(it))
    }
}

@APIGenDSL
internal interface SchemaBuilder<T : APIType> {

    val nestedTypes: MutableMap<String?, MutableList<T>>

    @APIGenDSL
    fun array(
        items: SchemaType,
        nullableItems: Boolean = false
    ): SchemaType =
        SchemaArray(items, nullableItems, null)

    @APIGenDSL
    fun map(
        keys: SchemaPrimitive,
        values: SchemaType,
        nullableValues: Boolean = false
    ): SchemaType =
        SchemaMap(keys, values, nullableValues, null)

}

internal sealed class APIQueryBuilder<Q : APIQuery, T : APIType>(private val createTypeFun: (SchemaClass) -> T) {

    protected val _types = mutableMapOf<TypeLocation, MutableList<T>>()
    val types get(): Map<TypeLocation, List<T>> = HashMap(_types.mapValues { (_, v) -> ArrayList(v) })

    protected abstract val route: String
    abstract val endpoint: String

    var summary: String = ""

    var cache: Duration? = null
    var security: Set<TokenScope>? = null

    fun security(vararg required: TokenScope) { security = required.toSet() }

    protected val pathParameters: MutableMap<String, PathParameter> = mutableMapOf()
    fun pathParameter(name: String, type: SchemaPrimitive, description: String, key: String = name, camelCase: String = name.firstToLowerCase()) {
        check(":$key" in (route.split('/')))
        check(key !in pathParameters)

        pathParameters[key] = PathParameter(key, type, description, name, camelCase)
    }

    protected val queryParameters: MutableMap<String, QueryParameter> = mutableMapOf()
    fun queryParameter(name: String, type: SchemaPrimitive, description: String, key: String = name.toLowerCase(Locale.ENGLISH), camelCase: String = name.firstToLowerCase(), isOptional: Boolean = false) {
        check(key !in queryParameters)

        queryParameters[key] = QueryParameter(key, type, description, name, camelCase, isOptional)
    }

    protected lateinit var queryTypes: Set<QueryType>
    protected var implicitIDsQuery: Boolean = false
        private set

    fun supportedQueries(vararg types: QueryType, implicitIDsQuery: Boolean = true) {
        check(!this::queryTypes.isInitialized)
        queryTypes = types.toSet()
        this.implicitIDsQuery = implicitIDsQuery
    }

    abstract fun finalize(): Collection<Q>

    @APIGenDSL
    fun record(
        name: String,
        description: String,
        configure: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = recordImpl(name, description, types, createTypeFun, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(endpoint, it.key)) { mutableListOf() }.addAll(it.value) }

        return type
    }

    @APIGenDSL
    fun SchemaBuilder<T>.record(
        name: String,
        description: String,
        configure: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = recordImpl(name, description, types, createTypeFun, configure)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

        return type
    }


    @APIGenDSL
    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, createTypeFun, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(endpoint, it.key)) { mutableListOf() }.addAll(it.value) }

        return type
    }

    @APIGenDSL
    fun SchemaBuilder<T>.conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, createTypeFun, configure)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

        return type
    }

    class V1(
        createTypeFun: (SchemaClass) -> APIType.V1,
        override val route: String,
        override val endpoint: String
    ) : APIQueryBuilder<APIQuery.V1, APIType.V1>(createTypeFun) {

        private lateinit var schema: SchemaType
        fun schema(schema: SchemaType) {
            this.schema = schema
        }

        override fun finalize(): Collection<APIQuery.V1> = listOf(
            APIQuery.V1(
                route = route,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                cache = cache,
                schema = schema
            )
        )

    }

    class V2(
        createTypeFun: (SchemaClass) -> APIType.V2,
        override val route: String,
        override val endpoint: String,
        private val since: V2SchemaVersion,
        private val until: V2SchemaVersion?
    ) : APIQueryBuilder<APIQuery.V2, APIType.V2>(createTypeFun) {

        init {
            require(until == null || since < until)
        }

        var idTypeKey: String = "id"
        var querySuffix: String? = null

        private lateinit var schema: SchemaVersionedData<SchemaType>
        fun schema(schema: SchemaType) {
            this.schema = buildVersionedSchemaData {
                V2SchemaVersion.values()
                    .filter { it == since || ((until == null || it <= until) && schema.hasChangedInVersion(it)) }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) -> add(schema.copyForVersion(since), since, until) }
            }
        }

        fun schema(vararg schemas: Pair<V2SchemaVersion, SchemaType>) {
            /*
             * The method only receives `since` constraints. Thus we need to ensure that no two constraints are equal
             * before we can continue processing.
             */
            require(schemas.all { a -> schemas.all { b -> a == b || a.first != b.first } })
            require(schemas.none { (version, _) -> version < since })
            require(until == null || schemas.none { (version, _) -> version >= until })

            val versions = buildVersionedSchemaData<SchemaType> {
                V2SchemaVersion.values()
                    .sorted()
                    .zipSchemaVersionConstraints()
                    .filter { (since, _) -> schemas.any { it.first <= since } }

                schemas.asIterable()
                    .sortedBy { it.first }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        val schema = since.second
                        val sinceBound = since.first
                        val untilBound = until?.first

                        V2SchemaVersion.values()
                            .filter { it == sinceBound || ((sinceBound < it) && (untilBound == null || it <= untilBound) && schema.hasChangedInVersion(it)) }
                            .zipSchemaVersionConstraints()
                            .forEach { (since, until) -> add(schema.copyForVersion(since), since, until) }
                    }
            }

            val classes = versions.mapDataOrNull { type -> when (type) {
                is SchemaClass -> type
                else -> null
            }}

            val loc = TypeLocation(endpoint, null)
            var types = _types[loc]

            if (types != null) {
                for ((_, schema) in schemas) types.removeIf { it._schema.any { it.data == schema } }
            }

            if (!classes.isEmpty) {
                if (types == null) {
                    types = mutableListOf()
                    _types[loc] = types
                }

                types.add(APIType.V2(classes))
            }

            this.schema = versions
        }

        private fun buildQuery(
            schema: SchemaVersionedData<SchemaType>,
            queryParameters: Map<String, QueryParameter>? = null,
            queryDetails: QueryDetails? = null
        ) = APIQuery.V2(
            route = route,
            endpoint = endpoint,
            summary = summary,
            pathParameters = pathParameters,
            queryParameters = queryParameters ?: this.queryParameters,
            queryDetails = queryDetails,
            querySuffix = querySuffix,
            cache = cache,
            since = since,
            until = until,
            security = security ?: emptySet(),
            _schema = schema
        )

        @OptIn(ExperimentalStdlibApi::class)
        override fun finalize(): Collection<APIQuery.V2> = buildList {
            if (this@V2::queryTypes.isInitialized) {
                val idType: SchemaPrimitive = when (val schema = schema[V2SchemaVersion.V2_SCHEMA_CLASSIC].data) {
                    is SchemaConditional -> schema.sharedProperties[idTypeKey]?.type
                    is SchemaRecord -> schema.properties[idTypeKey]?.type
                    else -> TODO()
                } as? SchemaPrimitive ?: TODO()

                if (implicitIDsQuery) {
                    add(buildQuery(
                        schema = buildVersionedSchemaData {
                            add(SchemaArray(idType, false, description = "the available IDs"), since = since, until = until)
                        },
                        queryParameters = queryParameters,
                        queryDetails = QueryDetails(QueryType.IDs, idType)
                    ))
                }

                queryTypes.forEach { queryType ->
                    val schema: SchemaVersionedData<SchemaType>

                    val queryParameters = mutableMapOf<String, QueryParameter>()
                    queryParameters += this@V2.queryParameters

                    when (queryType) {
                        is QueryType.ByID -> {
                            schema = this@V2.schema
                            QueryParameter(queryType.qpKey, idType, queryType.qpDescription, queryType.qpName, queryType.qpCamelCase, false)
                                .also { queryParameters[it.key] = it }
                        }
                        is QueryType.ByIDs -> {
                            schema = this@V2.schema.mapData { v -> SchemaArray(v, false, "") }
                            QueryParameter(queryType.qpKey, SchemaArray(idType, false, null), queryType.qpDescription, queryType.qpName, queryType.qpCamelCase, false)
                                .also { queryParameters[it.key] = it }
                        }
                        is QueryType.ByPage -> {
                            schema = this@V2.schema.mapData { v -> SchemaArray(v, false, "") }
                            QueryParameter(QueryParameter.PAGE_KEY, INTEGER, "the index of the requested page", "Page", "page", false)
                                .also { queryParameters[it.key] = it }
                            QueryParameter(QueryParameter.PAGE_SIZE_KEY, INTEGER, "the size of the requested page", "PageSize", "pageSize", true)
                                .also { queryParameters[it.key] = it }
                        }
                        else -> error("")
                    }

                    add(buildQuery(
                        schema = schema,
                        queryParameters = queryParameters,
                        queryDetails = QueryDetails(queryType, idType)
                    ))
                }
            } else {
                add(buildQuery(schema))
            }
        }

    }

}