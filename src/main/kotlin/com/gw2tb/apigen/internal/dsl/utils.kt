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

import kotlin.time.*

public val Int.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)
public val Long.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)
public val Double.nanoseconds: Duration get() = toDuration(DurationUnit.NANOSECONDS)

public val Int.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)
public val Long.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)
public val Double.microseconds: Duration get() = toDuration(DurationUnit.MICROSECONDS)

public val Int.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)
public val Long.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)
public val Double.milliseconds: Duration get() = toDuration(DurationUnit.MILLISECONDS)

public val Int.seconds: Duration get() = toDuration(DurationUnit.SECONDS)
public val Long.seconds: Duration get() = toDuration(DurationUnit.SECONDS)
public val Double.seconds: Duration get() = toDuration(DurationUnit.SECONDS)

public val Int.minutes: Duration get() = toDuration(DurationUnit.MINUTES)
public val Long.minutes: Duration get() = toDuration(DurationUnit.MINUTES)
public val Double.minutes: Duration get() = toDuration(DurationUnit.MINUTES)

public val Int.hours: Duration get() = toDuration(DurationUnit.HOURS)
public val Long.hours: Duration get() = toDuration(DurationUnit.HOURS)
public val Double.hours: Duration get() = toDuration(DurationUnit.HOURS)

public val Int.days: Duration get() = toDuration(DurationUnit.DAYS)
public val Long.days: Duration get() = toDuration(DurationUnit.DAYS)
public val Double.days: Duration get() = toDuration(DurationUnit.DAYS)