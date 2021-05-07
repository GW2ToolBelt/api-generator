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

/** A query type. */
public sealed class QueryType {

    internal companion object {

        internal val ByID : QueryType get() = QueryType.ByID.Default

        @Suppress("FunctionName")
        @JvmStatic
        internal fun ByID(
            qpKey: String,
            qpDescription: String,
            qpName: String,
            qpCamelCase: String
        ): ByID =
            QueryType.ByID.Custom(qpKey, qpDescription, qpName, qpCamelCase)

        @Suppress("FunctionName")
        @JvmStatic
        internal fun ByIDs(supportsAll: Boolean): ByIDs =
            if (supportsAll) ByIDs.DefaultAll else ByIDs.Default

        @Suppress("FunctionName")
        @JvmStatic
        internal fun ByIDs(
            supportsAll: Boolean,
            qpKey: String,
            qpDescription: String,
            qpName: String,
            qpCamelCase: String
        ): ByIDs =
            ByIDs.Custom(supportsAll, qpKey, qpDescription, qpName, qpCamelCase)

    }

    /** Queries all available IDs. */
    public object IDs : QueryType() {
        override fun toString(): String = "IDs"
    }

    /** Queries by ID `?id={id}`. */
    public sealed class ByID(
        public val qpKey: String,
        public val qpDescription: String,
        public val qpName: String,
        public val qpCamelCase: String
    ) : QueryType() {
        override fun toString(): String = "ByID"

        // TODO Add description
        internal object Default : ByID(
            QueryParameter.BY_ID_KEY,
            "",
            "ID",
            "id"
        )

        internal class Custom(
            qpKey: String,
            qpDescription: String,
            qpName: String,
            qpCamelCase: String
        ) : ByID(qpKey, qpDescription, qpName, qpCamelCase)

    }

    /** Queries by page `?page={index}&page_size={size}`. */
    public object ByPage : QueryType() {
        override fun toString(): String = "ByPage"
    }

    /** Queries by IDs `?ids={ids}`. */
    public sealed class ByIDs(
        public val qpKey: String,
        public val qpDescription: String,
        public val qpName: String,
        public val qpCamelCase: String
    ) : QueryType() {

        /** Whether or not `?ids=all` is supported. */
        public abstract val supportsAll: Boolean

        override fun toString(): String = "IDs"

        // TODO Add description
        internal abstract class AbstractDefault : ByIDs(
            QueryParameter.BY_IDS_KEY,
            "",
            "IDs",
            "ids"
        )

        internal object Default : AbstractDefault() { override val supportsAll = false }
        internal object DefaultAll : AbstractDefault() { override val supportsAll = true }

        internal class Custom(
            override val supportsAll: Boolean,
            qpKey: String,
            qpDescription: String,
            qpName: String,
            qpCamelCase: String
        ) : ByIDs(qpKey, qpDescription, qpName, qpCamelCase)

    }

}