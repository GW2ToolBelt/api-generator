/*
 * Copyright (c) 2019-2020 Leon Linhart
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
package com.github.gw2toolbelt.apigen.internal.dsl

import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.model.v2.*
import com.github.gw2toolbelt.apigen.schema.*
import java.util.*
import java.util.concurrent.*

@ExperimentalUnsignedTypes
typealias GW2APIEndpointFactory = () -> Set<Endpoint>

@ExperimentalUnsignedTypes
@Suppress("FunctionName")
internal fun GW2APIVersion(configure: GW2APIVersionBuilder.() -> Unit): GW2APIEndpointFactory {
    return fun() = GW2APIVersionBuilder().also(configure).endpoints
}

@ExperimentalUnsignedTypes
internal class GW2APIVersionBuilder : SchemaAggregateBuildProvider {

    private val _endpoints = mutableListOf<GW2APIEndpointBuilder>()
    val endpoints get() = _endpoints.map { it.endpoint }.toSet()

    @ExperimentalUnsignedTypes
    operator fun String.invoke(configure: GW2APIEndpointBuilder.() -> Unit) =
        GW2APIEndpointBuilder(this).also(configure).also { _endpoints.add(it) }

}

@ExperimentalUnsignedTypes
internal class GW2APIEndpointBuilder(private val route: String) {

    val endpoint get() =
        Endpoint(
            route = route,
            summary = summary,
            cache = cache,
            security = security ?: emptySet(),
            isLocalized = isLocalized,
            queryTypes = if (this::queryTypes.isInitialized) queryTypes else null,
            _schema = schema
        )

    private lateinit var schema: EnumMap<V2SchemaVersion, SchemaType>

    lateinit var summary: String

    var cache: Duration? = null
    var security: Set<TokenScope>? = null

    var isLocalized: Boolean = false

    private lateinit var queryTypes: Set<QueryType>

    fun cache(amount: ULong, unit: TimeUnit) { cache = Duration(amount, unit) }
    fun security(vararg required: TokenScope) { security = required.toSet() }

    fun schema(schema: SchemaType) {
        val tmp = V2SchemaVersion.values().toList().associateWithTo(EnumMap(V2SchemaVersion::class.java)) { version ->
            fun SchemaType.copyOrGet(superIncluded: Boolean = false): SchemaType? {
                return when (this) {
                    is SchemaMap -> {
                        fun SchemaType.hasChangedInVersion(): Boolean = when (this) {
                            is SchemaMap -> properties.any { (_, property) ->
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
                            else -> SchemaMap(copiedProperties, description)
                        }
                    }
                    else -> this
                }
            }

            schema.copyOrGet()
        }

        this.schema = EnumMap<V2SchemaVersion, SchemaType>(V2SchemaVersion::class.java).also { map ->
            tmp.forEach { (key, value) ->
                // TODO the compiler trips here without casts. Try this with NI once 1.3.70 is out
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

}