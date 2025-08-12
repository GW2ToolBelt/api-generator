/*
 * Copyright (c) 2019-2025 Leon Linhart
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

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.schema.*

/**
 * An http query parameter.
 *
 * @param key           the name of the parameter (as used in the query)
 * @param type          the type of the parameter
 * @param name          the name of the parameter
 * @param description   the description of the parameter
 * @param isOptional    whether the parameter is optional
 *
 * @since   0.7.0
 */
@ConsistentCopyVisibility
public data class QueryParameter internal constructor(
    val key: String,
    val type: SchemaTypeUse,
    val name: Name,
    val description: String,
    val isOptional: Boolean
) {

    /**
     * Additional [QueryParameter]-related utility properties and functions.
     *
     * @since   0.7.0
     */
    public companion object {

        /**
         * The default key for the "ID" query-parameter for [QueryType.ByID]
         * queries.
         *
         * @since   0.7.0
         */
        public const val BY_ID_KEY: String = "id"

        /**
         * The default key for the "IDs" query-parameter for [QueryType.ByIDs]
         * queries.
         *
         * @since   0.7.0
         */
        public const val BY_IDS_KEY: String = "ids"

        /**
         * The default key for the "page" query-parameter for [QueryType.ByPage]
         * queries.
         *
         * @since   0.7.0
         */
        public const val PAGE_KEY: String = "page"

        /**
         * The default key for the "page-size" query-parameter for [QueryType.ByPage]
         * queries.
         *
         * @since   0.7.0
         */
        public const val PAGE_SIZE_KEY: String = "page_size"

    }

}