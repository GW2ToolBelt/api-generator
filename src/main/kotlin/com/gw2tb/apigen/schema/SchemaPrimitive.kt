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
 * A schema representation of a primitive type.
 *
 * ### Localization
 *
 * A primitive does not make any assumptions about the value and, thus, is never
 * considered to be [isLocalized].
 *
 * ### Type Hints
 *
 * Primitives may be enhanced with [TypeHint]s. These hints are used to provide
 * usage information about a type and can be used to generate type-safe wrappers
 * or type-aliases.
 */
public sealed class SchemaPrimitive : SchemaTypeUse() {

    override val isLocalized: Boolean get() = false

    public abstract val typeHint: TypeHint?

    public abstract fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive

    public data class TypeHint(
        val camelCaseName: String
    )

}