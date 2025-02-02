/*
 * Copyright (c) 2019-2025 Leon Linhart
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

/**
 * A `SchemaMap` represents a JSON object.
 *
 * A SchemaMap provides no guarantees about the semantic meaning of the JSON
 * object's keys. To describe a specific layout, a [SchemaRecord] should be used
 * instead.
 *
 * This abstraction assumes that all values in the map have the same shape
 * (which might not be true in other contexts).
 *
 * ### Mapping
 *
 * SchemaMaps should be mapped to maps or dictionaries. If the target permits,
 * the [type of keys][keys] and the [type of values][values] should be used to
 * provide additional type-safety (e.g. by using them as type arguments).
 *
 * The optional [description] may be used to document the map for some targets.
 * The wording of the description is phrased to complete the sentence:
 *
 * > The map contains ...
 *
 * @param keys              the type of the map's keys
 * @param values            the type of the map's values
 * @param nullableValues    whether the map's values may be `null`
 * @param description       an optional description of the map
 *
 * @since   0.7.0
 */
public data class SchemaMap internal constructor(
    public val keys: SchemaPrimitiveOrAlias,
    public val values: SchemaTypeUse,
    public val nullableValues: Boolean,
    public val description: String?
) : SchemaTypeUse() {

    /**
     * Returns `true` if the [type of the values][values] is localized, or
     * `false` otherwise.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean get() = values.isLocalized

}