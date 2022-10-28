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
package com.gw2tb.apigen.model

import com.gw2tb.apigen.schema.SchemaTypeUse
import kotlin.time.Duration

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
 * @property querySuffix        TODO
 * @property cache              TODO
 * @property schema
 *
 * @since   0.7.0
 */
public data class APIQuery(
    public val route: String,
    public val endpoint: String,
    public val summary: String,
    public val pathParameters: Map<String, PathParameter>,
    public val queryParameters: Map<String, QueryParameter>,
    public val querySuffix: String?,
    public val cache: Duration?,
    val queryDetails: QueryDetails?,
    val security: Set<TokenScope>,
    public val schema: SchemaTypeUse
)