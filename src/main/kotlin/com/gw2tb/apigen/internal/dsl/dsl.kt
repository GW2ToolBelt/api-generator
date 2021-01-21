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
@file:Suppress("unused")
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*
import kotlin.time.*

@DslMarker
internal annotation class APIGenDSL

@Suppress("FunctionName")
internal fun GW2APIVersion(configure: GW2APIVersionBuilder.() -> Unit): () -> Set<Endpoint> {
    return fun() = GW2APIVersionBuilder().also(configure).endpoints
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

@APIGenDSL
internal class GW2APIVersionBuilder {

    private val _endpoints = mutableListOf<GW2APIEndpointBuilder>()
    val endpoints get() = _endpoints.map { it.endpoint }.toSet()

    operator fun String.invoke(configure: GW2APIEndpointBuilder.() -> Unit) =
        GW2APIEndpointBuilder(this).also(configure).also { _endpoints.add(it) }

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
        configure: SchemaRecordBuilder.() -> Unit
    ): SchemaType {
        val properties = SchemaRecordBuilder().also(configure).properties
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

        return if (versions.size == 1) {
            versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
        } else {
            SchemaBlueprint(versions)
        }
    }

    @APIGenDSL
    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder.() -> Unit)? = null,
        configure: SchemaConditionalBuilder.() -> Unit
    ): SchemaType {
        val sharedProps = if (sharedConfigure !== null) SchemaRecordBuilder().also(sharedConfigure).properties else emptyList()
        val interpretations = SchemaConditionalBuilder().also(configure).interpretations

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

        return if (versions.size == 1) {
            versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
        } else {
            SchemaBlueprint(versions)
        }
    }

}

@APIGenDSL
internal interface SchemaBuilder {

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

    @APIGenDSL
    fun record(
        description: String,
        name: String? = null,
        configure: SchemaRecordBuilder.() -> Unit
    ): SchemaType {
        val properties = SchemaRecordBuilder().also(configure).properties
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

        return if (versions.size == 1) {
            versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
        } else {
            SchemaBlueprint(versions)
        }
    }

    @APIGenDSL
    fun conditional(
        description: String,
        name: String? = null,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder.() -> Unit)? = null,
        configure: SchemaConditionalBuilder.() -> Unit
    ): SchemaType {
        val sharedProps = if (sharedConfigure !== null) SchemaRecordBuilder().also(sharedConfigure).properties else emptyList()
        val interpretations = SchemaConditionalBuilder().also(configure).interpretations

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

        return if (versions.size == 1) {
            versions[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
        } else {
            SchemaBlueprint(versions)
        }
    }

}

internal class GW2APIEndpointBuilder(private val route: String) {

    val endpoint get() =
        Endpoint(
            route = route,
            summary = summary,
            cache = cache,
            security = security ?: emptySet(),
            isLocalized = isLocalized,
            queryTypes = if (this::queryTypes.isInitialized) queryTypes else emptySet(),
            queryParameters = queryParameters.values.toList(),
            pathParameters = pathParameters.values.toList(),
            _schema = schema
        )

    private lateinit var schema: EnumMap<V2SchemaVersion, SchemaType>

    /** A short summary describing the endpoint. This should be a single, complete sentence. */
    lateinit var summary: String

    var cache: Duration? = null
    var security: Set<TokenScope>? = null

    var isLocalized: Boolean = false

    private lateinit var queryTypes: Set<QueryType>
    private val pathParameters: MutableMap<String, PathParameter> = mutableMapOf()
    private val queryParameters: MutableMap<String, QueryParameter> = mutableMapOf()

    fun security(vararg required: TokenScope) { security = required.toSet() }

    fun schema(schema: SchemaType) {
        this.schema = V2SchemaVersion.values()
            .filter { it === V2SchemaVersion.V2_SCHEMA_CLASSIC || schema.hasChangedInVersion(it) }
            .associateWithTo(EnumMap(V2SchemaVersion::class.java)) { schema.copyForVersion(it) }
    }

    fun schema(vararg schemas: Pair<V2SchemaVersion, SchemaType>) {
        this.schema = EnumMap<V2SchemaVersion, SchemaType>(V2SchemaVersion::class.java).also { map ->
            schemas.forEach { map[it.first] = it.second }
        }
    }

    fun supportedQueries(vararg types: QueryType) {
        check(!this::queryTypes.isInitialized)
        queryTypes = types.toSet()
    }

    fun pathParameter(name: String, type: SchemaPrimitive, description: String, key: String = name, camelCase: String = name.firstToLowerCase()) {
        check(":$key" in (route.split('/')))
        check(key !in pathParameters)

        pathParameters[key] = PathParameter(key, type, description, name, camelCase)
    }

    fun queryParameter(name: String, type: SchemaPrimitive, description: String, key: String = name, camelCase: String = name.firstToLowerCase(), isOptional: Boolean = false) {
        check(key !in queryParameters)

        queryParameters[key] = QueryParameter(key, type, description, name, camelCase, isOptional)
    }

}