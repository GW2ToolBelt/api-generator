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
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.Name

/**
 * A `SchemaRecord` represents a JSON object.
 *
 * Contrary to a [SchemaMap], a record provides strong guarantees about the
 * object's entries.
 *
 * ### Mapping
 *
 * SchemaRecords should be mapped to a representation that provides strong
 * typing for all [properties] where possible (e.g. using a class in Java).
 *
 * @param name          the name of the record
 * @param properties    the properties of the record
 * @param description   a description of the record
 *
 * @since   0.7.0
 */
@ConsistentCopyVisibility
public data class SchemaRecord internal constructor(
    public override val name: Name,
    public val properties: Map<String, SchemaProperty>,
    public val description: String
) : SchemaTypeDeclaration() {

    /**
     * Returns `true` if at least one of the [properties] is either [localized][SchemaProperty.isLocalized]
     * or its [type][SchemaProperty.type] is [SchemaTypeUse.isLocalized], or `false` otherwise.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean by lazy {
        properties.any { (_, v) -> v.isLocalized || v.type.isLocalized }
    }

}