/*
 * Copyright (c) 2019-2022 Leon Linhart
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

import com.gw2tb.apigen.internal.impl.SchemaVersionedData
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import kotlin.time.*

/**
 * A low-level representation of a [APIQuery].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public sealed class IRAPIQuery {

    public abstract val route: String
    public abstract val endpoint: String
    public abstract val summary: String
    public abstract val pathParameters: Map<String, IRPathParameter>
    public abstract val queryParameters: Map<String, IRQueryParameter>
    public abstract val querySuffix: String?
    public abstract val cache: Duration?

    internal abstract fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIQuery?

    public data class Details(
        val queryType: QueryType,
        val idType: IRPrimitiveIdentifier
    ) {

        internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): QueryDetails {
            val idType = idType.resolve(resolverContext, v2SchemaVersion)

            return QueryDetails(
                queryType = queryType,
                idType = idType
            )
        }

    }

    public data class QueryParameter(
        val key: String,
        val type: IRTypeUse<*>,
        val description: String,
        val name: String,
        val camelCaseName: String,
        val isOptional: Boolean
    )

    public data class V1 internal constructor(
        override val route: String,
        override val endpoint: String,
        override val summary: String,
        override val pathParameters: Map<String, IRPathParameter>,
        override val queryParameters: Map<String, IRQueryParameter>,
        override val querySuffix: String?,
        override val cache: Duration?,
        val type: IRTypeUse<*>
    ) : IRAPIQuery() {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIQuery {
            require(v2SchemaVersion == null)

            val pathParameters = pathParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }
            val queryParameters = queryParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }

            val schema = type.resolve(resolverContext, v2SchemaVersion)

            return APIQuery(
                route = route,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                querySuffix = querySuffix,
                cache = cache,
                queryDetails = null,
                security = emptySet(),
                schema = schema
            )
        }

    }

    public data class V2 internal constructor(
        override val route: String,
        override val endpoint: String,
        override val summary: String,
        override val pathParameters: Map<String, IRPathParameter>,
        override val queryParameters: Map<String, IRQueryParameter>,
        override val querySuffix: String?,
        override val cache: Duration?,
        val queryDetails: Details?,
        val since: V2SchemaVersion?,
        val until: V2SchemaVersion?,
        val security: Set<TokenScope>,
        private val _type: SchemaVersionedData<out IRTypeUse<*>>
    ) : IRAPIQuery(), VersionedData<IRTypeUse<*>> by _type {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIQuery? {
            require(v2SchemaVersion != null)

            if (since != null && since > v2SchemaVersion) return null
            if (until != null && until < v2SchemaVersion) return null

            val pathParameters = pathParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }
            val queryParameters = queryParameters.mapValues { (_, parameter) -> parameter.resolve(resolverContext, v2SchemaVersion) }

            val details = queryDetails?.resolve(resolverContext, v2SchemaVersion)
            val schema = this[v2SchemaVersion].data.resolve(resolverContext, v2SchemaVersion)

            return APIQuery(
                route = route,
                endpoint = endpoint,
                summary = summary,
                pathParameters = pathParameters,
                queryParameters = queryParameters,
                querySuffix = querySuffix,
                cache = cache,
                queryDetails = details,
                security = security,
                schema = schema
            )
        }

    }

}