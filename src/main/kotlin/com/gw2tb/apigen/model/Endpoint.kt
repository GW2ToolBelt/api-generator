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
 * The [route] may contain `:` prefixed segments. These segments represent the
 * [pathParameters]. A real URL can be constructed by replacing all such
 * segments with the value for the parameter with the segment as key.
 *
 * @param route             the path of the endpoint
 * @param summary           a summary of the endpoint's purpose
 * @param cache             the expect cache duration (as suggested by the Cache-Control header), or [Duration.INFINITE]
 *                          if the resource is expect to never change
 * @param security          the required [TokenScope]s for the endpoint
 * @param isLocalized       whether or not the endpoint is localized
 * @param queryTypes        the [QueryType]s supported by the endpoint
 * @param queryParameters   the required parameters for the endpoint
 * @param pathParameters    the path parameters for the endpoint
 */
public data class Endpoint internal constructor(
    public val route: String,
    public val summary: String,
    public val cache: Duration?,
    public val security: Set<TokenScope>,
    public val isLocalized: Boolean,
    public val queryTypes: Set<QueryType>,
    public val queryParameters: List<QueryParameter>,
    public val pathParameters: List<PathParameter>,
    private val _schema: EnumMap<V2SchemaVersion, SchemaType>
) {

    /** Whether or not the endpoint requires authentication. Use [security] for further information. */
    public val requiresAuthentication: Boolean get() = security.isNotEmpty()

    /** Returns the ID type of the endpoint, or `null` if the endpoint's values do not have a form of IDs. */
    public val idType: SchemaType? by lazy {
        if (QueryType.ByID in queryTypes || queryTypes.any { it is QueryType.ByIDs }) {
            (schema as? SchemaRecord)?.properties?.get("ID")?.type
        } else {
            null
        }
    }

    /** TODO doc */
    public val schema: SchemaType get() = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!

    /** TODO doc */
    public val versions: Set<V2SchemaVersion> get() = _schema.keys.toSet()

    /** TODO doc */
    public operator fun get(version: V2SchemaVersion): Pair<V2SchemaVersion, SchemaType> {
        return _schema.entries.sortedByDescending { it.key }.first { it.key <= version }.toPair()
    }

}