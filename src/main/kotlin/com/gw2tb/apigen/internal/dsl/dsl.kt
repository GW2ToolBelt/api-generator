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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.model.QueryType
import com.gw2tb.apigen.model.TokenScope

internal fun defaultQueryTypes(all: Boolean = false): QueryTypes = QueryTypes(buildSet {
    add(QueryType.IDs)
    add(QueryType.ByID)
    add(QueryType.ByIDs(supportsAll = all))
    add(QueryType.ByPage)
})

internal fun queryTypes(first: QueryType, vararg values: QueryType): QueryTypes = QueryTypes(buildSet {
    add(first)
    addAll(values)
})

internal data class QueryTypes(val values: Set<QueryType>)

internal fun security(vararg scopes: TokenScope): Security = Security(setOf(*scopes))

internal data class Security(val scopes: Set<TokenScope>)