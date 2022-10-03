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

/** A schema representing bitfield types. */
public data class SchemaBitfield(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive boolean types. */
public data class SchemaBoolean(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive decimal types. */
public data class SchemaDecimal(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive integer types. */
public data class SchemaInteger(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive string types. */
public data class SchemaString(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}