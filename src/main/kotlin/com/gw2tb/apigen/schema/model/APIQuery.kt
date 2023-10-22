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
package com.gw2tb.apigen.schema.model

import com.gw2tb.apigen.model.APIEndpoint
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.QueryType
import com.gw2tb.apigen.model.TokenScope
import com.gw2tb.apigen.schema.SchemaPrimitiveIdentifierOrAlias
import com.gw2tb.apigen.schema.SchemaTypeUse
import kotlin.time.Duration

/**
 * An `APIQuery` defines a type of request that can be made against the Guild
 * Wars 2 API.
 *
 * The [path] may differ from the [endpoint's path][APIEndpoint.path] as
 * multiple paths may belong to a single logical endpoint in some cases.
 *
 * The [path] may contain colon-prefixed (`:`) segments. These segments
 * represent the [path parameters][pathParameters]. The real route can be
 * constructed by substituting all such segments with an appropriate value for
 * the respective parameter.
 *
 * @param path              the relative path of the query
 * @param endpoint          the endpoint this query targets
 * @param summary           a short description of the query's purpose (e.g.
 *                          "Returns the current build ID." for
 *                          `/v2/build`).
 * @param pathParameters    the query's [path parameters][PathParameter]
 * @param queryParameters   the query's [query parameters][QueryParameter]
 * @param querySuffix       a suffix that may be used to properly discriminate
 *                          this query, or `null` if none is necessary
 * @param cache             the expected cache time for responses from this
 *                          query
 * @param details           additional details for queries that may be used to
 *                          access indexed resources
 * @param security          the permissions required to access this resource
 * @param schema            the schema returned by the query
 *
 * @since   0.7.0
 */
public data class APIQuery internal constructor(
    public val path: String,
    public val endpoint: APIEndpoint,
    public val summary: String,
    public val pathParameters: Map<String, PathParameter>,
    public val queryParameters: Map<String, QueryParameter>,
    public val querySuffix: String?,
    public val cache: Duration?,
    public val details: Details?,
    public val security: Set<TokenScope>,
    public val schema: SchemaTypeUse
) {

    /**
     * Additional details for queries that may be used to access indexed
     * resources.
     *
     * @param queryType the [type of query][QueryType]
     * @param idType    the ID type of the endpoint
     *
     * @since   0.7.0
     */
    public data class Details internal constructor(
        val queryType: QueryType,
        val idType: SchemaPrimitiveIdentifierOrAlias
    )

}