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
    ): SchemaType =
        SchemaRecord(name, SchemaRecordBuilder().also(configure).properties, description)

    @APIGenDSL
    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder.() -> Unit)? = null,
        configure: SchemaConditionalBuilder.() -> Unit
    ): SchemaType =
        SchemaConditional(
            name,
            disambiguationBy,
            disambiguationBySideProperty,
            sharedConfigure?.let(SchemaRecordBuilder()::also)?.properties ?: emptyMap(),
            SchemaConditionalBuilder().also(configure).interpretations,
            description
        )

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
    ): SchemaType =
        SchemaRecord(name, SchemaRecordBuilder().also(configure).properties, description)

    @APIGenDSL
    fun conditional(
        description: String,
        name: String? = null,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder.() -> Unit)? = null,
        configure: SchemaConditionalBuilder.() -> Unit
    ): SchemaType =
        SchemaConditional(
            name,
            disambiguationBy,
            disambiguationBySideProperty,
            sharedConfigure?.let(SchemaRecordBuilder()::also)?.properties ?: emptyMap(),
            SchemaConditionalBuilder().also(configure).interpretations,
            description
        )

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
        val tmp = V2SchemaVersion.values().toList().associateWithTo(EnumMap(V2SchemaVersion::class.java)) { version ->
            fun SchemaType.copyOrGet(superIncluded: Boolean = false): SchemaType? {
                return when (this) {
                    is SchemaConditional -> {
                        fun SchemaType.hasChangedInVersion(): Boolean = when (this) {
                            is SchemaConditional -> {
                                val sharedPropertiesChanged = sharedProperties.any { (_, property) ->
                                    property.since === version || property.until === V2SchemaVersion.values()[version.ordinal - 1] || property.type.hasChangedInVersion()
                                }

                                val interpretationsChanged = interpretations.any { (_, interpretation) ->
                                    interpretation.since === version || interpretation.until === V2SchemaVersion.values()[version.ordinal - 1] || interpretation.type.hasChangedInVersion()
                                }

                                sharedPropertiesChanged || interpretationsChanged
                            }
                            is SchemaRecord -> properties.any { (_, property) ->
                                property.since === version || property.until === V2SchemaVersion.values()[version.ordinal - 1] || property.type.hasChangedInVersion()
                            }
                            else -> false
                        }

                        val includeVersion = version === V2SchemaVersion.V2_SCHEMA_CLASSIC || hasChangedInVersion()
                        val copiedSharedProperties = sharedProperties.mapValues { (_, property) ->
                            val includedSinceVersion = property.since?.let { it === version } ?: false
                            val includedInVersion = ((includedSinceVersion || property.since?.let { version >= it } ?: true)) && (property.until?.let { it < version } ?: true)

                            when {
                                includedInVersion && (includeVersion || includedSinceVersion || superIncluded) -> property.copy(type = property.type.copyOrGet(superIncluded = true)!!)
                                else -> null
                            }
                        }.filterValues { it !== null }.mapValues { it.value!! }

                        val copiedInterpretations = interpretations.mapValues { (_, interpretation) ->
                            val includedSinceVersion = interpretation.since?.let { it === version } ?: false
                            val includedInVersion = ((includedSinceVersion || interpretation.since?.let { version >= it } ?: true)) && (interpretation.until?.let { it < version } ?: true)

                            when {
                                includedInVersion && (includeVersion || includedSinceVersion || superIncluded) -> interpretation.copy(type = interpretation.type.copyOrGet(superIncluded = true)!!)
                                else -> null
                            }
                        }.filterValues { it !== null }.mapValues { it.value!! }

                        return when {
                            !superIncluded && copiedSharedProperties.isEmpty() && copiedInterpretations.isEmpty() -> null
                            else -> copy(sharedProperties = copiedSharedProperties, interpretations = copiedInterpretations)
                        }
                    }
                    is SchemaRecord -> {
                        fun SchemaType.hasChangedInVersion(): Boolean = when (this) {
                            is SchemaRecord -> properties.any { (_, property) ->
                                property.since === version || property.until === V2SchemaVersion.values()[version.ordinal - 1] || property.type.hasChangedInVersion()
                            }
                            else -> false
                        }

                        val includeVersion = version === V2SchemaVersion.V2_SCHEMA_CLASSIC || hasChangedInVersion()
                        val copiedProperties = properties.mapValues { (_, property) ->
                            val includedSinceVersion = property.since?.let { it === version } ?: false
                            val includedInVersion = ((includedSinceVersion || property.since?.let { version >= it } ?: true)) && (property.until?.let { it < version } ?: true)

                            when {
                                includedInVersion && (includeVersion || includedSinceVersion || superIncluded) -> property.copy(type = property.type.copyOrGet(superIncluded = true)!!)
                                else -> null
                            }
                        }.filterValues { it !== null }.mapValues { it.value!! }

                        return when {
                            !superIncluded && copiedProperties.isEmpty() -> null
                            else -> copy(properties = copiedProperties)
                        }
                    }
                    else -> this
                }
            }

            schema.copyOrGet()
        }

        this.schema = EnumMap<V2SchemaVersion, SchemaType>(V2SchemaVersion::class.java).also { map ->
            tmp.forEach { (key, value) ->
                // TODO the compiler trips here without casts. Try this with NI once it is out
                if (value !== null) map[key as V2SchemaVersion] = value as SchemaType
            }
        }
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