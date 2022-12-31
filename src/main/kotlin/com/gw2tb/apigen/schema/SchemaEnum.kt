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

import com.gw2tb.apigen.model.Name

/**
 * A `SchemaEnum` represents a JSON primitive.
 *
 * Contrary to a [SchemaPrimitive], an enum defines a set of expected [values]
 * for the primitive.
 *
 * ### Mapping
 *
 * SchemaEnums **should not** be mapped to a typical enum language construct
 * (such as Java's enum) since these are usually exhaustive. Instead, a sealed
 * hierarchy with an additional implementation for unrecognized values is more
 * appropriate.
 *
 * @param name          the name of the enum
 * @param type          the type of the enum's values
 * @param values        the enum's values
 * @param description   a description of the enum
 *
 * @since   0.7.0
 */
public data class SchemaEnum(
    public override val name: Name,
    public val type: SchemaPrimitive,
    public val values: Set<Value>,
    public val description: String
) : SchemaTypeDeclaration() {

    /**
     * Always returns `false` since a SchemaEnum wraps a [SchemaPrimitive] with,
     * on its own, is never localized.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean get() = false

    /**
     * A enum value.
     *
     * @param name          the name of the value
     * @param value         the serial representation of the value
     * @param description   a description of the value
     *
     * @since   0.7.0
     */
    public data class Value(
        public val name: Name,
        public val value: String,
        public val description: String
    )

}