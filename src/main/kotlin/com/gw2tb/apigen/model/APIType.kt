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
package com.gw2tb.apigen.model

import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*

public sealed class APIType {

    public data class V1(
        val schema: SchemaClass
    ) : APIType()

    public data class V2(
        private val _schema: EnumMap<V2SchemaVersion, SchemaClass>
    ) : APIType() {

        /** Returns the [V2SchemaVersion.V2_SCHEMA_CLASSIC] schema. */
        public val schema: SchemaClass get() = _schema[V2SchemaVersion.V2_SCHEMA_CLASSIC]!!

        /** Returns the schema versions. */
        public val versions: Set<V2SchemaVersion> get() = _schema.keys.toSet()

        /** Returns the appropriate schema for the given [version]. */
        public operator fun get(version: V2SchemaVersion): Pair<V2SchemaVersion, SchemaClass> {
            return _schema.entries.sortedByDescending { it.key }.first { it.key <= version }.toPair()
        }

    }

}