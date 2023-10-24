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
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.Name

/**
 * A `SchemaAlias` is a type-alias for a [primitive type][SchemaPrimitive].
 *
 * ### Mapping
 *
 * SchemaAliases should be mapped to either a (weakly or strongly) typed
 * type-alias if the target permits. Otherwise, the aliased primitive type
 * should be used instead.
 *
 * @param name          the name of the alias
 * @param type          the aliased primitive type
 * @param description   a description of the tuple
 *
 * @since   0.7.0
 */
public data class SchemaAlias internal constructor(
    public override val name: Name,
    public val type: SchemaPrimitive,
    public val description: String
) : SchemaTypeDeclaration() {

    /**
     * See [SchemaPrimitive.isLocalized].
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean get() = type.isLocalized

}