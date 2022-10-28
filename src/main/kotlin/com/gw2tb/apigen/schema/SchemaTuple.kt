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
package com.gw2tb.apigen.schema

/**
 * A `SchemaTuple` represents a JSON array.
 *
 * Contrary to a [SchemaArray], a tuple has a fixed number of [elements] (with
 * possibly different shapes) in a fixed order.
 *
 * ### Mapping
 *
 * SchemaTuples should be mapped to a representation that provides strong typing
 * for all [elements] where possible (e.g. using a tuple in Rust).
 *
 * Although, no such information is provided by the API, a [name] is provided
 * for the tuple which should be used for targets which only support nominal
 * tuples (e.g. Java's records). Similarly, names are also provided for the
 * tuple's elements.
 *
 * @param name          the name of the tuple
 * @param elements      the elements of the tuple
 * @param description   a description of the tuple
 *
 * @since   0.7.0
 */
public data class SchemaTuple internal constructor(
    public override val name: Name,
    public val elements: List<Element>,
    public val description: String
) : SchemaTypeDeclaration() {

    /**
     * Returns `true` if at least one of the [elements] is either [localized][Element.isLocalized]
     * or its [type][Element.type] is [SchemaTypeUse.isLocalized], or `false` otherwise.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean by lazy {
        elements.any { it.isLocalized || it.type.isLocalized }
    }

    /**
     * An element of a tuple.
     *
     * ### Type Coercion
     *
     * An element of a [primitive type][SchemaPrimitive] may be marked as [lenient][isLenient].
     * Such elements must be treated with special care as the element's type may
     * not match the JSON type. Instead, the parser should coerce JSON strings
     * into the property's type. If the string is empty, it should be treated as
     * if the value is not set.
     *
     * @param name          the name of the element
     * @param type          the type of the element's value
     * @param description   a description of the element
     * @param isLenient     whether to perform type-coercion on the input
     * @param isLocalized   whether the element's value is localized
     *
     * @since   0.7.0
     */
    public data class Element(
        public val name: Name,
        public val type: SchemaTypeUse,
        public val description: String,
        public val isLenient: Boolean,
        public val isLocalized: Boolean
    )

}