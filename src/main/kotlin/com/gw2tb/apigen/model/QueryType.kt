/*
 * Copyright (c) 2019-2026 Leon Linhart
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

import com.gw2tb.apigen.schema.model.QueryParameter

/**
 * A type of query exposed by the Guild Wars 2 API.
 *
 * @since   0.7.0
 */
public sealed class QueryType {

    internal companion object {

        internal val ByID : QueryType get() = QueryType.ByID.Default

        @JvmStatic
        internal fun ByID(qpKey: String, qpName: Name, qpDescription: String): ByID =
            QueryType.ByID.Custom(qpKey, qpName, qpDescription)

        @JvmStatic
        internal fun ByIDs(supportsAll: Boolean): ByIDs =
            if (supportsAll) ByIDs.DefaultAll else ByIDs.Default

        @JvmStatic
        internal fun ByIDs(supportsAll: Boolean, qpKey: String, qpName: Name, qpDescription: String): ByIDs =
            ByIDs.Custom(supportsAll, qpKey, qpName, qpDescription)

    }

    /**
     * The `IDs` type represents queries that are used to retrieve a list of
     * available IDs.
     *
     * @since   0.7.0
     */
    public object IDs : QueryType() {

        /**
         * Returns a string representation of this `QueryType`.
         *
         * @return  a string representation
         *
         * @since   0.7.0
         */
        override fun toString(): String = "IDs"

    }

    /**
     * The `ByID` type represents queries that are used to retrieve an object
     * for a given identifier.
     *
     * This type mandates a query parameter that is used to specify the
     * identifier to look up.
     *
     * @param qpKey         the key for the [QueryParameter]
     * @param qpName        the name for the [QueryParameter]
     * @param qpDescription the description for the [QueryParameter]
     *
     * @since   0.7.0
     */
    public sealed class ByID(
        public val qpKey: String,
        public val qpName: Name,
        public val qpDescription: String
    ) : QueryType() {

        /**
         * Returns a string representation of this `QueryType`.
         *
         * @return  a string representation
         *
         * @since   0.7.0
         */
        override fun toString(): String = "ByID"

        internal object Default : ByID(
            qpKey = QueryParameter.BY_ID_KEY,
            qpName = Name("ID"),
            qpDescription = "the ID of the requested object",
        )

        internal class Custom(qpKey: String, qpName: Name, qpDescription: String) : ByID(qpKey, qpName, qpDescription)

    }

    /**
     * The `ByPage` type represents queries that are used to retrieve objects by
     * pages.
     *
     * This type mandates two query parameters:
     *
     * - The `page` query parameter is used to specify the index of the page to
     *   look up.
     * - The `page_size` query parameter is used to specify the number of
     *   objects that should be treated as one page.
     *
     * @since   0.7.0
     */
    public object ByPage : QueryType() {

        /**
         * Returns a string representation of this `QueryType`.
         *
         * @return  a string representation
         *
         * @since   0.7.0
         */
        override fun toString(): String = "ByPage"

    }

    /**
     * The `ByIDs` type represents queries that are used to retrieve objects by
     * a set of IDs.
     *
     * This type mandates a query parameter that is used to specify the
     * identifiers to look up.
     *
     * @param qpKey         the key for the [QueryParameter]
     * @param qpName        the name for the [QueryParameter]
     * @param qpDescription the description for the [QueryParameter]
     *
     * @since   0.7.0
     */
    public sealed class ByIDs(
        public val qpKey: String,
        public val qpName: Name,
        public val qpDescription: String
    ) : QueryType() {

        /**
         * Indicates whether the query supports retrieving all objects at once.
         *
         * If a query supports querying all objects, the value `all` receives
         * special treatment for the mandated parameter.
         *
         * @since   0.1.0
         */
        public abstract val supportsAll: Boolean

        /**
         * Returns a string representation of this `QueryType`.
         *
         * @return  a string representation
         *
         * @since   0.7.0
         */
        override fun toString(): String = "ByIDs(all=$supportsAll)"

        internal abstract class AbstractDefault : ByIDs(
            qpKey = QueryParameter.BY_IDS_KEY,
            qpName = Name("IDs"),
            qpDescription = "the IDs of the requested objects"
        )

        internal object Default : AbstractDefault() { override val supportsAll = false }
        internal object DefaultAll : AbstractDefault() { override val supportsAll = true }

        internal class Custom(
            override val supportsAll: Boolean,
            qpKey: String,
            qpName: Name,
            qpDescription: String,
        ) : ByIDs(qpKey, qpName, qpDescription)

    }

}