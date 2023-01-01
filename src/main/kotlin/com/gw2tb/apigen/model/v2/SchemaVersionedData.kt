/*
 * Copyright (c) 2019-2023 Leon Linhart
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
 * A representation of data that is changed across multiple schema versions.
 *
 * @since   0.7.0
 */
public interface SchemaVersionedData<out T> {

    /**
     * Returns the list of versions for which data is present.
     *
     * @since   0.7.0
     */
    public val versions: List<SchemaVersion>

    /**
     * Returns the list of versions in which the underlying data is changed.
     *
     * @since   0.7.0
     */
    public val significantVersions: List<SchemaVersion>

    /**
     * Attempts to flatten the data into a single value using the given
     * [transform]. The flattening fails if the transform returns inconsistent
     * results.
     *
     * @return  the flattened data
     *
     * @since   0.7.0
     */
    public fun <R> flatten(transform: (T) -> R): R

    /**
     * Returns the data for the given [version] or `null` if none is available.
     *
     * @param version   the version to look up
     *
     * @return  the data for the given `version` or `null` if none is available
     *
     * @since   0.7.0
     */
    public operator fun get(version: SchemaVersion): SchemaVersionConstrainedData<T>?

    /**
     * Returns the data for the given [version] or throws if none is available.
     *
     * @param version   the version to look up
     *
     * @return  the data for the given `version`
     *
     * @since   0.7.0
     */
    public fun getOrThrow(version: SchemaVersion): SchemaVersionConstrainedData<T>

    /**
     * Returns whether data is available for the given [version].
     *
     * @param version   the version to test
     *
     * @return  `true` if data is available for the given `version` or false otherwise
     *
     * @since   0.7.0
     */
    public fun isSupported(version: SchemaVersion): Boolean

}