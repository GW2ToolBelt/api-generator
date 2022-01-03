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

import com.gw2tb.apigen.schema.*

/**
 * An http query parameter.
 *
 * @param key           the name of the parameter (as used in the query)
 * @param type          the type of the parameter
 * @param description   the description of the parameter
 * @param name          the name of the parameter in _TitleCase_
 * @param camelCaseName the name of the parameter in _camelCase_
 * @param isOptional    whether or not the parameter is optional
 */
public data class QueryParameter internal constructor(
    val key: String,
    val type: SchemaType,
    val description: String,
    val name: String,
    val camelCaseName: String,
    val isOptional: Boolean
) {

    public companion object {

        /** Key for ID query-parameter for ByID queries. */
        public const val BY_ID_KEY: String = "id"

        /** Key for IDs query-parameter for ByIDs queries. */
        public const val BY_IDS_KEY: String = "ids"

        /** Key for page query-parameter for ByPage queries. */
        public const val PAGE_KEY: String = "page"

        /** Key for page-size query-parameter for ByPage queries. */
        public const val PAGE_SIZE_KEY: String = "page_size"

    }

}