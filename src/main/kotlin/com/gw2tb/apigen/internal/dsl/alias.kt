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
@file:Suppress("UnusedReceiverParameter")
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.QueryType
import com.gw2tb.apigen.schema.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/** Marks a [deprecated][SchemaProperty.isDeprecated] property. */
@APIGenDSL internal val AbstractSchemaRecordBuilder<*>.deprecated get() = Modifiers.deprecated

/** Marks an [inline][SchemaProperty.isInline] property. */
@APIGenDSL internal val AbstractSchemaRecordBuilder<*>.inline get() = Modifiers.inline

/** Marks a [lenient][SchemaProperty.isLenient] property. */
@APIGenDSL internal val AbstractSchemaRecordBuilder<*>.lenient get() = Modifiers.lenient

/** Marks a [localized][SchemaProperty.isLocalized] property. */
@APIGenDSL internal val AbstractSchemaRecordBuilder<*>.localized get() = Modifiers.localized

/** Marks an [optional][SchemaProperty.optionality] property. */
@APIGenDSL internal val AbstractSchemaRecordBuilder<*>.optional get() = Modifiers.optional


/** Alias for [QueryType.ByID]. */
internal val BY_ID get() = QueryType.ByID

/** Alias for [QueryType.ByID]. */
internal fun BY_ID(
    qpName: String,
    qpDescription: String,
    qpKey: String = qpName.lowercase(),
    qpCamelCase: String = qpName.firstToLowerCase()
) = QueryType.ByID(
    qpKey = qpKey,
    qpDescription = qpDescription,
    qpName = qpName,
    qpCamelCase = qpCamelCase
)

/** Alias for [QueryType.ByPage]. */
internal val IDS get() = QueryType.IDs

/** Alias for [QueryType.ByPage]. */
internal val BY_PAGE get() = QueryType.ByPage

/** Alias for [QueryType.ByIDs]. */
internal val BY_IDS = QueryType.ByIDs(supportsAll = true)

/** Alias for [QueryType.ByIDs]. */
internal fun BY_IDS(all: Boolean = true) = QueryType.ByIDs(supportsAll = all)

/** Alias for [QueryType.ByIDs]. */
internal fun BY_IDS(
    qpName: String,
    qpDescription: String,
    all: Boolean = true,
    qpKey: String = qpName.lowercase(),
    qpCamelCase: String = qpName.firstToLowerCase()
) = QueryType.ByIDs(
    supportsAll = all,
    qpKey = qpKey,
    qpDescription = qpDescription,
    qpName = qpName,
    qpCamelCase = qpCamelCase
)


internal val Int.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)
internal val Long.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)
internal val Double.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)

internal val Int.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)
internal val Long.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)
internal val Double.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)

internal val Int.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)
internal val Long.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)
internal val Double.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)

internal val Int.seconds: Duration get() = toDuration(DurationUnit.SECONDS)
internal val Long.seconds: Duration get() = toDuration(DurationUnit.SECONDS)
internal val Double.seconds: Duration get() = toDuration(DurationUnit.SECONDS)

internal val Int.minutes: Duration get() = toDuration(DurationUnit.MINUTES)
internal val Long.minutes: Duration get() = toDuration(DurationUnit.MINUTES)
internal val Double.minutes: Duration get() = toDuration(DurationUnit.MINUTES)

internal val Int.hours: Duration get() = toDuration(DurationUnit.HOURS)
internal val Long.hours: Duration get() = toDuration(DurationUnit.HOURS)
internal val Double.hours: Duration get() = toDuration(DurationUnit.HOURS)

internal val Int.days: Duration get() = toDuration(DurationUnit.DAYS)
internal val Long.days: Duration get() = toDuration(DurationUnit.DAYS)
internal val Double.days: Duration get() = toDuration(DurationUnit.DAYS)