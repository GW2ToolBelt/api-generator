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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*

internal fun String.firstToLowerCase(): String =
    "${toCharArray()[0].toLowerCase()}${substring(1)}"

internal fun requireCamelCase(str: String, ctx: String) = require(str[0].isLowerCase()) { "$ctx should be in camelCase: $str" }
internal fun requireTitleCase(str: String, ctx: String) = require(str[0].isUpperCase()) { "$ctx should be in TitleCase: $str" }

internal fun SchemaConditional.Interpretation.hasChangedInVersion(version: V2SchemaVersion): Boolean =
    since == version || (until != null && until.ordinal == version.ordinal) || type.hasChangedInVersion(version)

internal fun SchemaRecord.Property.hasChangedInVersion(version: V2SchemaVersion): Boolean =
    since == version || (until != null && until.ordinal == version.ordinal) || type.hasChangedInVersion(version)

internal fun SchemaType.hasChangedInVersion(version: V2SchemaVersion): Boolean = when (this) {
    is SchemaBlueprint -> versions.hasChangedInVersion(version)
    is SchemaArray -> items.hasChangedInVersion(version)
    is SchemaConditional -> sharedProperties.any { (_, property) -> property.hasChangedInVersion(version) }
        || interpretations.any { (_, property) -> property.hasChangedInVersion(version) }
    is SchemaMap -> values.hasChangedInVersion(version)
    is SchemaRecord -> properties.any { (_, property) -> property.hasChangedInVersion(version) }
    is SchemaPrimitive -> false
}

internal fun SchemaConditional.Interpretation.copyForVersion(version: V2SchemaVersion): SchemaConditional.Interpretation = copy(
    type = if (type is SchemaBlueprint) {
        type.versions[version].data
    } else {
        type
    }
)

internal fun SchemaRecord.Property.copyForVersion(version: V2SchemaVersion): SchemaRecord.Property = copy(
    type = if (type is SchemaBlueprint) {
        type.versions[version].data
    } else {
        type
    }
)

internal fun SchemaType.copyForVersion(version: V2SchemaVersion): SchemaType = when (this) {
    is SchemaBlueprint -> versions[version].data
    is SchemaArray -> copy(items = items.copyForVersion(version))
    is SchemaConditional -> copy(
        sharedProperties = sharedProperties.filterValues { property ->
            (property.since === null || property.since <= version) && (property.until === null || version < property.until)
        },
        interpretations = interpretations.filterValues { interpretation ->
            (interpretation.since === null || interpretation.since <= version) && (interpretation.until === null || version < interpretation.until)
        }
    )
    is SchemaMap -> copy(values = values.copyForVersion(version))
    is SchemaRecord -> copy(properties = properties.filterValues { property ->
        (property.since === null || property.since <= version) && (property.until === null || version < property.until)
    })
    is SchemaPrimitive -> this
}

internal fun <T> List<T>.getForVersion(sinceSelector: (T) -> V2SchemaVersion?, untilSelector: (T) -> V2SchemaVersion?, keySelector: (T) -> String, version: V2SchemaVersion): Map<String, T> =
    filter {
        val since = sinceSelector(it)
        if (since != null && version < since) return@filter false

        val until = untilSelector(it)
        until == null || version.ordinal < until.ordinal
    }.associateBy { keySelector(it) }

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