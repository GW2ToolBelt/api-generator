/*
 * Copyright (c) 2019-2020 Leon Linhart
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
package com.github.gw2toolbelt.apigen.model.v2

/**
 * A schema version number for the V2 web API.
 *
 * @since   0.1.0
 */
public enum class V2SchemaVersion(public val version: String?) {
    V2_SCHEMA_CLASSIC(null),
    V2_SCHEMA_2019_02_21T00_00_00_000Z("2019-02-21T00:00:00.000Z"),
    V2_SCHEMA_2019_03_22T00_00_00_000Z("2019-03-22T00:00:00.000Z"),
    V2_SCHEMA_2019_05_16T00_00_00_000Z("2019-05-16T00:00:00.000Z"),
    V2_SCHEMA_2019_05_21T23_00_00_000Z("2019-05-21T23:00:00.000Z"),
    V2_SCHEMA_2019_05_22T00_00_00_000Z("2019-05-22T00:00:00.000Z"),
    V2_SCHEMA_2019_12_19T00_00_00_000Z("2019-12-19T00:00:00.000Z")
}