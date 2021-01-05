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
@file:Suppress("RedundantVisibilityModifier")
package com.gw2tb.apigen.model

/** A query type. */
public sealed class QueryType {

    internal companion object {

        @Suppress("FunctionName")
        @JvmStatic
        internal fun ByIDs(supportsAll: Boolean): ByIDs =
            if (supportsAll) ByIDs.All else ByIDs.NotAll

    }

    /** Queries by ID `?id={id}`. */
    public object ByID : QueryType() {
        override fun toString(): String = "ByID"
    }

    /** Queries by page `?page={index}&page_size={size}`. */
    public object ByPage : QueryType() {
        override fun toString(): String = "ByPage"
    }

    /** Queries by IDs `?ids={ids}`. */
    public sealed class ByIDs : QueryType() {

        /** Whether or not `?ids=all` is supported. */
        public abstract val supportsAll: Boolean

        internal object All : ByIDs() { override val supportsAll = true }
        internal object NotAll : ByIDs() { override val supportsAll = false }

        override fun toString(): String = "ByIDs(all = $supportsAll)"

    }

}