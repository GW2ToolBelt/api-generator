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
import java.util.*

internal fun <T> buildVersionedSchemaData(block: Builder<T>.() -> Unit): SchemaVersionedData<T> =
    SchemaVersionedData(TreeSet<VersionConstrainedData<T>>().apply {
        Builder<T>().apply(block).data.forEach { (constraint, data) ->
            add(VersionConstrainedData(data, constraint.since, constraint.until))
        }
    })

internal class Builder<T> {

    internal val data = mutableMapOf<VersionConstraint, T>()
    private var unboundSince: V2SchemaVersion? = null

    fun add(datum: T, since: V2SchemaVersion = V2SchemaVersion.V2_SCHEMA_CLASSIC, until: V2SchemaVersion? = null) {
        require(until == null || since < until)
//        require(data.none { (other, _) -> (other.until == null || since < other.until) && (until == null || other.since < until) })

        if (unboundSince != null && unboundSince!! < since) {
            val d = data.remove(VersionConstraint(unboundSince!!, null)) ?: error("")
            data[VersionConstraint(unboundSince!!, since)] = d
        }

        if (until == null)
            unboundSince = since

        data[VersionConstraint(since, until)] = datum
    }

}

internal data class VersionConstraint(
    val since: V2SchemaVersion,
    val until: V2SchemaVersion?
)

internal class SchemaVersionedData<T>(
    internal val entries: TreeSet<VersionConstrainedData<T>>
) : VersionedData<T>, Iterable<VersionConstrainedData<T>> by entries {

    init {
        require(entries.isNotEmpty()) { "SchemaVersionedData requires at least one entry" }
    }

    val isConsistent get() =
        (entries.size == 1) && (entries.first().since == V2SchemaVersion.V2_SCHEMA_CLASSIC) && (entries.first().until == null)

    override val versions: List<V2SchemaVersion> get() =
        V2SchemaVersion.values().filter { getOrNull(it) != null }

    override val significantVersions get() =
        V2SchemaVersion.values().filter { hasChangedInVersion(it) && isSupported(it) }

    fun first(): VersionConstrainedData<T> =
        entries.first()

    override operator fun get(version: V2SchemaVersion): VersionConstrainedData<T> =
        getOrNull(version) ?: error("No data for version $version")

    override fun getOrNull(version: V2SchemaVersion): VersionConstrainedData<T>? =
        entries.firstOrNull { (_, since, until) -> since <= version && (until == null || version < until) }

    fun hasChangedInVersion(version: V2SchemaVersion): Boolean =
        entries.any { constraint -> constraint.since == version || constraint.until == version }

    override fun isSupported(version: V2SchemaVersion): Boolean =
        getOrNull(version) != null

    fun readjustConstraints(since: V2SchemaVersion, until: V2SchemaVersion?): SchemaVersionedData<T> {
        if (entries.first().since == since && entries.last().until == until) return this

        return buildVersionedSchemaData {
            val first = entries.first()
            val last = entries.last()

            if (first == last) {
                add(first.data, since, until)
            } else {
                add(first.data, since, first.until)
                entries.forEach { entry ->
                    if (entry == first || entry == last) return@forEach

                    add(entry.data, entry.since, entry.until)
                }
                add(last.data, last.since, until)
            }
        }
    }

    fun forEach(block: (T, V2SchemaVersion, V2SchemaVersion?) -> Unit) {
        entries.forEach { block(it.data, it.since, it.until) }
    }

    override fun <R> flatMapData(transform: (T) -> R): R {
        val itr = entries.iterator()
        val data = transform(itr.next().data)

        while (itr.hasNext()) {
            val nextData = transform(itr.next().data)
            if (data != nextData) error("Cannot flatMap data")
        }

        return data
    }

    fun <R> mapData(transform: (T) -> R): SchemaVersionedData<R> =
        SchemaVersionedData(entries.asSequence().map { it.map(transform) }.toCollection(TreeSet()))

    fun <R> mapDataOrNull(transform: (T) -> R?): SchemaVersionedData<R>? {
        val set = entries.asSequence().mapNotNull { it.mapOrNull(transform) }.toCollection(TreeSet())

        return if (set.isNotEmpty())
            SchemaVersionedData(set)
        else
            null
    }

    private fun <R> VersionConstrainedData<T>.map(transform: (T) -> R) =
        VersionConstrainedData(transform(data), since, until)

    private fun <R> VersionConstrainedData<T>.mapOrNull(transform: (T) -> R?): VersionConstrainedData<R>? =
        transform(data)?.let { VersionConstrainedData(it, since, until) }

}