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
import com.github.gw2toolbelt.apigen.schema.*
import java.util.*
import java.util.concurrent.*

@ExperimentalUnsignedTypes
typealias GW2APIEndpointFactory = () -> List<Endpoint>

@ExperimentalUnsignedTypes
@Suppress("FunctionName")
internal fun GW2APIVersion(configure: GW2APIVersionBuilder.() -> Unit): GW2APIEndpointFactory {
    return fun() = GW2APIVersionBuilder().also(configure).endpoints
}

@ExperimentalUnsignedTypes
internal class GW2APIVersionBuilder : SchemaAggregateBuildProvider {

    private val _endpoints = mutableListOf<GW2APIEndpointBuilder>()
    val endpoints get() = _endpoints.map { it.endpoint }

    @ExperimentalUnsignedTypes
    operator fun String.invoke(configure: GW2APIEndpointBuilder.() -> Unit) =
        GW2APIEndpointBuilder().also(configure).also { _endpoints.add(it) }

}

@ExperimentalUnsignedTypes
internal class GW2APIEndpointBuilder {

    val endpoint get() =
        Endpoint(
            summary = summary,
            cache = cache,
            security = security ?: emptySet(),
            isLocalized = isLocalized,
            _schema = schema
        )

    private lateinit var schema: EnumMap<V2SchemaVersion, SchemaType>

    lateinit var summary: String

    var cache: Duration? = null
    var security: Set<TokenScope>? = null

    var isLocalized: Boolean = false

    fun cache(amount: ULong, unit: TimeUnit) { cache = Duration(amount, unit) }
    fun security(vararg required: TokenScope) { security = required.toSet() }

    fun schema(schema: SchemaType) {
        this.schema = EnumMap(V2SchemaVersion::class.java)
        this.schema[V2SchemaVersion.V2_SCHEMA_CLASSIC] = schema
    }

    fun schema(vararg schemas: Pair<V2SchemaVersion, SchemaType>) {
        this.schema = EnumMap(V2SchemaVersion::class.java)
        schemas.forEach { this.schema[it.first] = it.second }
    }

}