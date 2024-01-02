/*
 * Copyright (c) 2019-2024 Leon Linhart
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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.model.v2.*

internal fun String.firstToLowerCase(): String =
    "${toCharArray()[0].lowercaseChar()}${substring(1)}"

internal fun requireCamelCase(str: String, ctx: String) = require(str[0].isLowerCase()) { "$ctx should be in camelCase: $str" }
internal fun requireTitleCase(str: String, ctx: String) = require(str[0].isUpperCase()) { "$ctx should be in TitleCase: $str" }

internal fun SchemaVersion.containsChangeForBounds(since: SchemaVersion?, until: SchemaVersion?): Boolean =
    (since ?: SchemaVersion.V2_SCHEMA_CLASSIC) == this || until == this

internal fun <T> List<T>.getForVersion(sinceSelector: (T) -> SchemaVersion?, untilSelector: (T) -> SchemaVersion?, version: SchemaVersion): List<T> =
    filter {
        val since = sinceSelector(it)
        if (since != null && version < since) return@filter false

        val until = untilSelector(it)
        until == null || version < until
    }

internal fun <T> Iterable<T>.zipSchemaVersionConstraints(includeUnbound: Boolean = true): Iterable<Pair<T, T?>> = sequence {
    val itr = this@zipSchemaVersionConstraints.iterator()
    var first = itr.next()

    if (!includeUnbound && !itr.hasNext())
        error("Cannot zip single version into bound constraint")

    while (itr.hasNext()) {
        val second = itr.next()
        yield(first to second)

        first = second
    }

    if (includeUnbound)
        yield(first to null)
}.asIterable()