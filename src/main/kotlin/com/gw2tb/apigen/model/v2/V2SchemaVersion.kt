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
package com.gw2tb.apigen.model.v2

/**
 * A schema version number for the V2 web API.
 *
 * @param identifier    the string identifier of the schema version, or `null`
 *                      for the "classic" schema
 */
public enum class V2SchemaVersion(public val identifier: String?) {
    V2_SCHEMA_CLASSIC(null),
    V2_SCHEMA_2019_02_21T00_00_00_000Z("2019-02-21T00:00:00.000Z"),
    V2_SCHEMA_2019_03_22T00_00_00_000Z("2019-03-22T00:00:00.000Z"),
    V2_SCHEMA_2019_05_16T00_00_00_000Z("2019-05-16T00:00:00.000Z"),
    V2_SCHEMA_2019_05_21T23_00_00_000Z("2019-05-21T23:00:00.000Z"),
    V2_SCHEMA_2019_05_22T00_00_00_000Z("2019-05-22T00:00:00.000Z"),
    V2_SCHEMA_2019_12_19T00_00_00_000Z("2019-12-19T00:00:00.000Z"),
    V2_SCHEMA_2020_11_17T00_30_00_000Z("2020-11-17T00:30:00.000Z"),
    V2_SCHEMA_2021_04_06T21_00_00_000Z("2021-04-06T21:00:00.000Z"),
    V2_SCHEMA_2021_07_15T13_00_00_000Z("2021-07-15T13:00:00.000Z")
}