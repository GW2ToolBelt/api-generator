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
@file:Suppress("RedundantVisibilityModifier")
package com.gw2tb.apigen.model

import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*
import kotlin.time.*

/**
 * TODO doc
 *
 * @param route
 * @param summary
 * @param cache
 * @param security
 * @param isLocalized
 * @param queryTypes
 * @param parameters
 * @param pathParameters
 *
 * @since   0.1.0
 */
public data class Endpoint internal constructor(
    public val route: String,
    public val summary: String,
    public val cache: Duration?,
    public val security: Set<TokenScope>,
    public val isLocalized: Boolean,
    public val queryTypes: Set<QueryType>,
    public val parameters: List<Parameter>,
    public val pathParameters: List<PathParameter>,
    private val _schema: EnumMap<V2SchemaVersion, SchemaType>
) {

    public val idType: SchemaType? get() = (schema as? SchemaRecord)?.properties?.get("ID")?.type

    public val schema: SchemaType get() = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!
    public val versions: Set<V2SchemaVersion> get() = _schema.keys.toSet()

    public operator fun get(version: V2SchemaVersion): Pair<V2SchemaVersion, SchemaType> {
        return _schema.entries.sortedByDescending { it.key }.first { it.key <= version }.toPair()
    }

}