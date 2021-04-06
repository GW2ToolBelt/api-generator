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
package com.gw2tb.apigen.model

import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*
import kotlin.time.*

/**
 * An API query.
 *
 * The [route] may contain `:` prefixed segments. These segments represent the
 * [pathParameters]. The real route can be constructed by replacing all such
 * segments with an appropriate value for the parameter.
 *
 * @property route              the query URL
 * @property endpoint           the endpoint this query belongs to
 * @property summary            a short description of the query's purpose
 * @property pathParameters     the path parameters
 * @property queryParameters    the query parameters
 * @property isLocalized        whether or not the query is localized
 * @property cache              TODO
 */
public sealed class APIQuery {

    public abstract val route: String
    public abstract val endpoint: String
    public abstract val summary: String
    public abstract val pathParameters: Map<String, PathParameter>
    public abstract val queryParameters: Map<String, QueryParameter>
    public abstract val isLocalized: Boolean
    public abstract val cache: Duration?

    /**
     * An APIv1 query.
     *
     * @param schema
     */
    public data class V1 internal constructor(
        override val route: String,
        override val endpoint: String,
        override val summary: String,
        override val pathParameters: Map<String, PathParameter>,
        override val queryParameters: Map<String, QueryParameter>,
        override val isLocalized: Boolean,
        override val cache: Duration?,
        val schema: SchemaType
    ) : APIQuery()

    /**
     * An APIv2 query.
     *
     * @param queryDetails
     * @param since
     * @param until
     * @param security
     */
    public data class V2 internal constructor(
        override val route: String,
        override val endpoint: String,
        override val summary: String,
        override val pathParameters: Map<String, PathParameter>,
        override val queryParameters: Map<String, QueryParameter>,
        override val isLocalized: Boolean,
        override val cache: Duration?,
        val queryDetails: QueryDetails?,
        val since: V2SchemaVersion?,
        val until: V2SchemaVersion?,
        val security: Set<TokenScope>,
        private val _schema: EnumMap<V2SchemaVersion, SchemaType>
    ) : APIQuery() {

        /** Returns the [V2SchemaVersion.V2_SCHEMA_CLASSIC] schema. */
        public val schema: SchemaType get() = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!

        /** Returns the schema versions. */
        public val versions: Set<V2SchemaVersion> get() = _schema.keys.toSet()

        /** Returns the appropriate schema for the given [version]. */
        public operator fun get(version: V2SchemaVersion): Pair<V2SchemaVersion, SchemaType> {
            return _schema.entries.sortedByDescending { it.key }.first { it.key <= version }.toPair()
        }

    }

}