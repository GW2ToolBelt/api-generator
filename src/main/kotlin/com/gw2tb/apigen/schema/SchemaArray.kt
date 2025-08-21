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
 * A `SchemaArray` represents a JSON array.
 *
 * A SchemaArray provides no guarantees about the semantic meaning of the JSON
 * array's elements and its size. To describe a specific layout, a [SchemaTuple]
 * should be used instead.
 *
 * This abstraction assumes that all values in the array have the same shape
 * (which might not be true in other contexts).
 *
 * ### Mapping
 *
 * SchemaArrays should be mapped to arrays or lists. If the target permits, the
 * [type of the elements][elements] should be used to provide additional
 * type-safety (e.g. by using it as type argument).
 *
 * The optional [description] may be used to document the array for some
 * targets. The wording of the description is phrased to complete the sentence:
 *
 * > The array contains ...
 *
 * @param elements          the type of the array's elements
 * @param nullableElements  whether the array's elements may be `null`
 * @param description       an optional description of the array
 *
 * @since   0.7.0
 */
@ConsistentCopyVisibility
public data class SchemaArray internal constructor(
    public val elements: SchemaTypeUse,
    public val nullableElements: Boolean,
    public val description: String?
) : SchemaTypeUse() {

    /**
     * Returns `true` if the [type of the elements][elements] is localized, or
     * `false` otherwise.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean get() = elements.isLocalized

}