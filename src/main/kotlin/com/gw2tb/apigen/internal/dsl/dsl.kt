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

    @APIGenDSL
    fun record(
        name: String,
        description: String,
        configure: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String, MutableList<T>>()
        val type = recordImpl(name, description, types, ::createType, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(null, it.key)) { mutableListOf() }.addAll(it.value) }

        return type
    }

    @APIGenDSL
    fun APIQueryBuilder<Q, T>.record(
        name: String,
        description: String,
        configure: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String, MutableList<T>>()
        val type = recordImpl(name, description, types, ::createType, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(endpoint, it.key)) { mutableListOf() }.addAll(it.value) }

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
        val types = mutableMapOf<String, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, ::createType, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(null, it.key)) { mutableListOf() }.addAll(it.value) }

        return type
    }

    @APIGenDSL
    fun APIQueryBuilder<Q, T>.conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, ::createType, configure)
        types.forEach { _types.computeIfAbsent(TypeLocation(endpoint, it.key)) { mutableListOf() }.addAll(it.value) }

        return type
    }

    abstract fun createType(type: SchemaClass): T

    class V1 : APIVersionBuilder<APIQuery.V1, APIType.V1>() {

        operator fun String.invoke(
            endpoint: String = this,
            configure: APIQueryBuilder.V1.() -> Unit
        ) =
            APIQueryBuilder.V1(::createType, this, endpoint)
                .also(configure)
                .also { _queries.add(it) }

        override fun createType(type: SchemaClass): APIType.V1 {
            require(type !is SchemaBlueprint)
            return APIType.V1(type)
        }

    }

    class V2 : APIVersionBuilder<APIQuery.V2, APIType.V2>() {

        operator fun String.invoke(
            endpoint: String = this,
            since: V2SchemaVersion? = null,
            until: V2SchemaVersion? = null,
            configure: APIQueryBuilder.V2.() -> Unit
        ) =
            APIQueryBuilder.V2(::createType, this, endpoint, since, until, _types)
                .also(configure)
                .also { _queries.add(it) }

        override fun createType(type: SchemaClass): APIType.V2 = when (type) {
            is SchemaBlueprint -> APIType.V2(EnumMap<V2SchemaVersion, SchemaClass>(V2SchemaVersion::class.java).apply {
                type.versions.forEach { (version, type) ->
                    if (type !is SchemaClass) return@forEach
                    // TODO Should we try to extract the first (possibly nested) class here?

                    this[version] = type
                }
            })
            else -> APIType.V2(EnumMap(mapOf(V2SchemaVersion.V2_SCHEMA_CLASSIC to type)))
        }

    }

}

private fun SchemaConditional.Interpretation.copyForVersion(version: V2SchemaVersion): SchemaConditional.Interpretation = copy(
    type = if (type is SchemaBlueprint) {
        V2SchemaVersion.values().sortedDescending().filter { it <= version }.mapNotNull { type.versions[it] }.first()
    } else {
        type
    }
)

private fun SchemaRecord.Property.copyForVersion(version: V2SchemaVersion): SchemaRecord.Property = copy(
    type = if (type is SchemaBlueprint) {
        V2SchemaVersion.values().sortedDescending().filter { it <= version }.mapNotNull { type.versions[it] }.first()
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
    types: MutableMap<String, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaConditionalBuilder<T>.() -> Unit
): SchemaClass {
    val bSharedProps = sharedConfigure?.let { SchemaRecordBuilder<T>(name).also(it) }
    val bInterpretations = SchemaConditionalBuilder<T>().also(configure)

    bSharedProps?.nestedTypes?.forEach { (k, v) -> types.computeIfAbsent("$name${if (k.isNotEmpty()) "/" else ""}$k") { mutableListOf() }.addAll(v) }
    bInterpretations.nestedTypes.forEach { (k, v) -> types.computeIfAbsent("$name${if (k.isNotEmpty()) "/" else ""}$k") { mutableListOf() }.addAll(v) }

    val sharedProps = bSharedProps?.properties ?: emptyList()
    val interpretations = bInterpretations.interpretations

    val propVersions = mutableMapOf<V2SchemaVersion, Map<String, SchemaRecord.Property>?>()
    propVersions[V2SchemaVersion.V2_SCHEMA_CLASSIC] = sharedProps.getForVersion(
        SchemaRecord.Property::since,
        SchemaRecord.Property::until,
        SchemaRecord.Property::serialName,
        V2SchemaVersion.V2_SCHEMA_CLASSIC
    ).mapValues { (_, property) -> property.copyForVersion(V2SchemaVersion.V2_SCHEMA_CLASSIC) }

    val versions = mutableMapOf<V2SchemaVersion, SchemaConditional?>()
    versions[V2SchemaVersion.V2_SCHEMA_CLASSIC] = SchemaConditional(
        name,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        propVersions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!,
        interpretations.getForVersion(
            SchemaConditional.Interpretation::since,
            SchemaConditional.Interpretation::until,
            SchemaConditional.Interpretation::interpretationKey,
            V2SchemaVersion.V2_SCHEMA_CLASSIC
        ).mapValues { (_, intrp) -> intrp.copyForVersion(V2SchemaVersion.V2_SCHEMA_CLASSIC) },
        description
    )

    V2SchemaVersion.values().forEachIndexed { index, version ->
        if (version === V2SchemaVersion.V2_SCHEMA_CLASSIC) return@forEachIndexed

        if (sharedProps.any { it.hasChangedInVersion(version) }) {
            propVersions[version] = sharedProps.getForVersion(
                SchemaRecord.Property::since,
                SchemaRecord.Property::until,
                SchemaRecord.Property::serialName,
                version
            ).mapValues { (_, property) -> property.copyForVersion(version) }
        } else {
            propVersions[version] = propVersions[V2SchemaVersion.values()[index - 1]]
        }

        if (interpretations.any { it.hasChangedInVersion(version) }) {
            versions[version] = SchemaConditional(
                name,
                disambiguationBy,
                disambiguationBySideProperty,
                interpretationInNestedProperty,
                propVersions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!,
                interpretations.getForVersion(
                    SchemaConditional.Interpretation::since,
                    SchemaConditional.Interpretation::until,
                    SchemaConditional.Interpretation::interpretationKey,
                    version
                ).mapValues { (_, intrp) -> intrp.copyForVersion(version) },
                description
            )
        }
    }

    return (if (versions.size == 1) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent("") { mutableListOf() }.add(apiTypeFactory(it))
    }
}

private fun <T : APIType> recordImpl(
    name: String,
    description: String,
    types: MutableMap<String, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaRecordBuilder<T>.() -> Unit
): SchemaClass {
    val bProperties = SchemaRecordBuilder<T>(name).also(configure)
    bProperties.nestedTypes.forEach { (k, v) -> types.computeIfAbsent("$name${if (k.isNotEmpty()) "/" else ""}$k") { mutableListOf() }.addAll(v) }

    val properties = bProperties.properties
    val versions = mutableMapOf<V2SchemaVersion, SchemaType?>()
    versions[V2SchemaVersion.V2_SCHEMA_CLASSIC] = SchemaRecord(
        name,
        properties.getForVersion(
            SchemaRecord.Property::since,
            SchemaRecord.Property::until,
            SchemaRecord.Property::serialName,
            V2SchemaVersion.V2_SCHEMA_CLASSIC
        ).mapValues { (_, property) -> property.copyForVersion(V2SchemaVersion.V2_SCHEMA_CLASSIC) },
        description
    )

    V2SchemaVersion.values().forEachIndexed { _, version ->
        if (version === V2SchemaVersion.V2_SCHEMA_CLASSIC) return@forEachIndexed

        if (properties.any { it.hasChangedInVersion(version) }) {
            versions[version] = SchemaRecord(
                name,
                properties.getForVersion(
                    SchemaRecord.Property::since,
                    SchemaRecord.Property::until,
                    SchemaRecord.Property::serialName,
                    version
                ).mapValues { (_, property) -> property.copyForVersion(version) },
                description
            )
        }
    }

    return (if (versions.size == 1) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!! as SchemaClass
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent("") { mutableListOf() }.add(apiTypeFactory(it))
    }
}

@APIGenDSL
internal interface SchemaBuilder<T : APIType> {

    val nestedTypes: MutableMap<String, MutableList<T>>

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

    protected abstract val route: String
    abstract val endpoint: String

    var summary: String = ""

    var isLocalized: Boolean = false

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

    fun supportedQueries(vararg types: QueryType) {
        check(!this::queryTypes.isInitialized)
        queryTypes = types.toSet()
    }

    protected lateinit var schema: EnumMap<V2SchemaVersion, SchemaType>
    fun schema(schema: SchemaType) {
        this.schema = V2SchemaVersion.values()
            .filter { it === V2SchemaVersion.V2_SCHEMA_CLASSIC || schema.hasChangedInVersion(it) }
            .associateWithTo(EnumMap(V2SchemaVersion::class.java)) { schema.copyForVersion(it) }
    }

    abstract fun finalize(): Collection<Q>

    @APIGenDSL
    fun SchemaBuilder<T>.record(
        name: String,
        description: String,
        configure: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String, MutableList<T>>()
        val type = recordImpl(name, description, types, createTypeFun, configure)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

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
        val types = mutableMapOf<String, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, createTypeFun, configure)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

        return type
    }

    class V1(
        createTypeFun: (SchemaClass) -> APIType.V1,
        override val route: String,
        override val endpoint: String
    ) : APIQueryBuilder<APIQuery.V1, APIType.V1>(createTypeFun) {

        override fun finalize(): Collection<APIQuery.V1> = listOf(
            APIQuery.V1(
                route = route,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                isLocalized = isLocalized,
                cache = cache,
                schema = schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
            )
        )

    }

    class V2(
        createTypeFun: (SchemaClass) -> APIType.V2,
        override val route: String,
        override val endpoint: String,
        private val since: V2SchemaVersion?,
        private val until: V2SchemaVersion?,
        private val _types: MutableMap<TypeLocation, MutableList<APIType.V2>>
    ) : APIQueryBuilder<APIQuery.V2, APIType.V2>(createTypeFun) {

        init {
            require((since == null || until == null) || since < until)
        }

        fun schema(vararg schemas: Pair<V2SchemaVersion, SchemaType>) {
            val loc = TypeLocation(endpoint, null)
            val types = _types[loc]

            if (types != null) {
                for ((_, schema) in schemas) types.removeIf { it.schema == schema }

                types += APIType.V2(EnumMap<V2SchemaVersion, SchemaClass>(V2SchemaVersion::class.java).apply {
                    schemas.forEach { (version, type) ->
                        if (type !is SchemaClass) return@forEach
                        // TODO Should we try to extract the first (possibly nested) class here?

                        this[version] = type
                    }
                })
            }

            this.schema = EnumMap<V2SchemaVersion, SchemaType>(V2SchemaVersion::class.java).also { map ->
                schemas.forEach { map[it.first] = it.second }
            }
        }

        private fun buildQuery(
            schema: EnumMap<V2SchemaVersion, SchemaType>,
            queryParameters: Map<String, QueryParameter>? = null,
            queryDetails: QueryDetails? = null
        ) = APIQuery.V2(
            route = route,
            endpoint = endpoint,
            summary = summary,
            pathParameters = pathParameters,
            queryParameters = queryParameters ?: this.queryParameters,
            queryDetails = queryDetails,
            isLocalized = isLocalized && (queryDetails?.queryType != QueryType.IDs),
            cache = cache,
            since = since,
            until = until,
            security = security ?: emptySet(),
            _schema = schema
        )

        @OptIn(ExperimentalStdlibApi::class)
        override fun finalize(): Collection<APIQuery.V2> = buildList {
            if (this@V2::queryTypes.isInitialized) {
                val idType: SchemaPrimitive = when (val schema = schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]) {
                    is SchemaConditional -> schema.sharedProperties["id"]?.type
                    is SchemaRecord -> schema.properties["id"]?.type
                    else -> TODO()
                } as? SchemaPrimitive ?: TODO()

                add(buildQuery(
                    schema = EnumMap(mapOf(V2SchemaVersion.V2_SCHEMA_CLASSIC to SchemaArray(idType, false, description = "the available IDs"))),
                    queryParameters = queryParameters,
                    queryDetails = QueryDetails(QueryType.IDs, idType)
                ))

                queryTypes.forEach { queryType ->
                    val schema: EnumMap<V2SchemaVersion, SchemaType>

                    val queryParameters = mutableMapOf<String, QueryParameter>()
                    queryParameters += this@V2.queryParameters

                    when (queryType) {
                        is QueryType.ByID -> {
                            schema = this@V2.schema
                            queryParameters["id"] = QueryParameter("id", idType, "", "ID", "id", false)
                        }
                        is QueryType.ByIDs -> {
                            schema = this@V2.schema.mapValues { (_, v) -> SchemaArray(v, false, "") }.toMap(EnumMap(V2SchemaVersion::class.java))
                            queryParameters["ids"] = QueryParameter("ids", SchemaArray(idType, false, null), "", "IDs", "ids", false)
                        }
                        is QueryType.ByPage -> {
                            schema = this@V2.schema.mapValues { (_, v) -> SchemaArray(v, false, "") }.toMap(EnumMap(V2SchemaVersion::class.java))
                            queryParameters["page"] = QueryParameter("page", INTEGER, "", "Page", "page", false)
                            queryParameters["page_size"] = QueryParameter("page_size", INTEGER, "", "PageSize", "pageSize", true)
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