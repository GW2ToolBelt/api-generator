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
package com.gw2tb.apigen.ir.model

import com.gw2tb.apigen.internal.impl.SchemaVersionedDataImpl
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.model.APIQuery
import kotlin.time.*

/**
 * A low-level representation of an [APIQuery].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public sealed class IRAPIQuery {

    /**
     * The relative path of the query.
     *
     * @since   0.7.0
     */
    public abstract val path: String

    /**
     * The [APIEndpoint] this query targets.
     *
     * @since   0.7.0
     */
    public abstract val endpoint: APIEndpoint

    /**
     * A short description of the query's purpose (e.g. "Returns the current
     * build ID." for `/v2/build`).
     *
     * @since   0.7.0
     */
    public abstract val summary: String

    /**
     * The query's [path parameters][IRPathParameter].
     *
     * @since   0.7.0
     */
    public abstract val pathParameters: Map<String, IRPathParameter>

    /**
     * The query's [query parameters][IRQueryParameter].
     *
     * @since   0.7.0
     */
    public abstract val queryParameters: Map<String, IRQueryParameter>

    /**
     * A suffix that may be used to properly discriminate this query, or `null`
     * if none is necessary.
     *
     * @since   0.7.0
     */
    public abstract val querySuffix: String?

    /**
     * The expected cache time for responses from this query.
     *
     * @since   0.7.0
     */
    public abstract val cache: Duration?

    internal abstract fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIQuery?

    /**
     * A low-level representation of a [APIQuery.Details].
     *
     * @param queryType the [type of query][QueryType]
     * @param idType    the ID type of the endpoint
     *
     * @since   0.7.0
     */
    public data class Details internal constructor(
        val queryType: QueryType,
        val idType: IRPrimitiveIdentifier
    ) {

        internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIQuery.Details {
            val idType = idType.resolve(resolverContext, v2SchemaVersion)

            return APIQuery.Details(
                queryType = queryType,
                idType = idType
            )
        }

    }

    /**
     * A low-level representation of an [APIQuery] for version 1 (`v1`) of the
     * Guild Wars 2 API.
     *
     * @param type  the type of responses from this query
     *
     * @since   0.7.0
     */
    public data class V1 internal constructor(
        override val path: String,
        override val endpoint: APIEndpoint,
        override val summary: String,
        override val pathParameters: Map<String, IRPathParameter>,
        override val queryParameters: Map<String, IRQueryParameter>,
        override val querySuffix: String?,
        override val cache: Duration?,
        val type: IRTypeUse<*>
    ) : IRAPIQuery() {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIQuery {
            require(v2SchemaVersion == null)

            val pathParameters = pathParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }
            val queryParameters = queryParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }

            val schema = type.resolve(resolverContext, v2SchemaVersion)

            return APIQuery(
                path = path,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                querySuffix = querySuffix,
                cache = cache,
                details = null,
                security = emptySet(),
                schema = schema
            )
        }

    }

    /**
     * A low-level representation of an [APIQuery] for version 2 (`v2`) of the
     * Guild Wars 2 API.
     *
     * @param details   additional details for queries that may be used to
     *                  access indexed resources
     * @param security  the permissions required to access this resource
     * @param since     the lower bound version (inclusive)
     * @param until     the upper bound version (exclusive)
     *
     * @since   0.7.0
     */
    public data class V2 internal constructor(
        override val path: String,
        override val endpoint: APIEndpoint,
        override val summary: String,
        override val pathParameters: Map<String, IRPathParameter>,
        override val queryParameters: Map<String, IRQueryParameter>,
        override val querySuffix: String?,
        override val cache: Duration?,
        val details: Details?,
        val security: Set<TokenScope>,
        val since: SchemaVersion?,
        val until: SchemaVersion?,
        private val _type: SchemaVersionedDataImpl<out IRTypeUse<*>>
    ) : IRAPIQuery(), SchemaVersionedData<IRTypeUse<*>> by _type {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIQuery? {
            require(v2SchemaVersion != null)

            if (since != null && since > v2SchemaVersion) return null
            if (until != null && until < v2SchemaVersion) return null

            val pathParameters = pathParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }
            val queryParameters = queryParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }

            val details = details?.resolve(resolverContext, v2SchemaVersion)
            val schema = this.getOrThrow(v2SchemaVersion).data.resolve(resolverContext, v2SchemaVersion)

            return APIQuery(
                path = path,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                querySuffix = querySuffix,
                cache = cache,
                details = details,
                security = security,
                schema = schema
            )
        }

    }

}