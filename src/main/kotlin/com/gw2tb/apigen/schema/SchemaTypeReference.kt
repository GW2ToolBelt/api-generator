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

import com.gw2tb.apigen.model.QualifiedTypeName

/**
 * A reference to a schema representation of a type.
 *
 * @since   0.7.0
 */
public sealed class SchemaTypeReference : SchemaTypeUse() {

    /**
     * The [qualified name][QualifiedTypeName] of the referenced type.
     *
     * @since   0.7.0
     */
    public abstract val name: QualifiedTypeName

    /**
     * Returns `true` if the referenced type is localized, or `false` otherwise.
     *
     * @since   0.7.0
     */
    abstract override val isLocalized: Boolean

    internal data class Alias constructor(
        override val name: QualifiedTypeName.Alias,
        val alias: SchemaAlias
    ) : SchemaTypeReference(), SchemaPrimitiveOrAlias {

        override val isLocalized: Boolean get() = alias.isLocalized

    }

    internal data class Declaration constructor(
        override val name: QualifiedTypeName.Declaration,
        val declaration: SchemaTypeDeclaration
    ) : SchemaTypeReference() {

        override val isLocalized: Boolean get() = declaration.isLocalized

    }

}