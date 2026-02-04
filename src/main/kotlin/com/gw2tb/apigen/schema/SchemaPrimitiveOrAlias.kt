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

import com.gw2tb.apigen.ir.*

/**
 * A [SchemaPrimitive] or a [SchemaAlias].
 *
 * @since   0.7.0
 */
public sealed interface SchemaPrimitiveOrAlias

/**
 * A [SchemaPrimitiveIdentifier] or a [SchemaAlias].
 *
 * @since   0.7.0
 */
public sealed interface SchemaPrimitiveIdentifierOrAlias : SchemaPrimitiveOrAlias

/**
 * Lowers a [SchemaPrimitiveOrAlias] to a [SchemaPrimitive].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public tailrec fun SchemaPrimitiveOrAlias.lowered(): SchemaPrimitive {
    if (this is SchemaPrimitive) return this
    return (this as SchemaTypeReference.Alias).alias.type.lowered()
}

/**
 * Lowers a [SchemaPrimitiveIdentifierOrAlias] to a [SchemaPrimitiveIdentifier].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public tailrec fun SchemaPrimitiveIdentifierOrAlias.lowered(): SchemaPrimitiveIdentifier {
    if (this is SchemaPrimitiveIdentifier) return this
    return ((this as SchemaTypeReference.Alias).alias.type as SchemaPrimitiveIdentifierOrAlias).lowered()
}