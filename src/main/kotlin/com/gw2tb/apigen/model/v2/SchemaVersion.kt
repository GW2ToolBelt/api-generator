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
package com.gw2tb.apigen.model.v2

/**
 * A schema version represents a variant of version 2 (`v2`) of the official
 * Guild Wars 2 API.
 *
 * @param identifier    the string identifier of the schema version, or `null`
 *                      for the "classic" schema
 *
 * @since   0.7.0
 */
public enum class SchemaVersion(public val identifier: String?) {
    /**
     * The original variant of version 2 of the official Guild Wars 2 API.
     *
     * As schema versions were only introduced later, this variant does not have
     * a corresponding identifier. Typically, the schema version query parameter
     * should not be set when requesting this "classic" variant.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_CLASSIC(null),
    /**
     * The `2019-02-21T00:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_02_21T00_00_00_000Z("2019-02-21T00:00:00.000Z"),
    /**
     * The `2019-03-22T00:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_03_22T00_00_00_000Z("2019-03-22T00:00:00.000Z"),
    /**
     * The `2019-05-16T00:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_05_16T00_00_00_000Z("2019-05-16T00:00:00.000Z"),
    /**
     * The `2019-05-21T23:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_05_21T23_00_00_000Z("2019-05-21T23:00:00.000Z"),
    /**
     * The `2019-05-22T00:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_05_22T00_00_00_000Z("2019-05-22T00:00:00.000Z"),
    /**
     * The `2019-12-19T00:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2019_12_19T00_00_00_000Z("2019-12-19T00:00:00.000Z"),
    /**
     * The `2020-11-17T00:30:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2020_11_17T00_30_00_000Z("2020-11-17T00:30:00.000Z"),
    /**
     * The `2021-04-06T21:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2021_04_06T21_00_00_000Z("2021-04-06T21:00:00.000Z"),
    /**
     * The `2021-07-15T13:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2021_07_15T13_00_00_000Z("2021-07-15T13:00:00.000Z"),
    /**
     * The `2022-03-09T02:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2022_03_09T02_00_00_000Z("2022-03-09T02:00:00.000Z"),
    /**
     * The `2022-03-23T19:00:00.000Z` version.
     *
     * @since   0.7.0
     */
    V2_SCHEMA_2022_03_23T19_00_00_000Z("2022-03-23T19:00:00.000Z"),
    /**
     * The `2024-07-20T01:00:00.000Z` version.
     *
     * @since   0.8.0
     */
    V2_SCHEMA_2024_07_20T01_00_00_000Z("2024-07-20T01:00:00.000Z")
}