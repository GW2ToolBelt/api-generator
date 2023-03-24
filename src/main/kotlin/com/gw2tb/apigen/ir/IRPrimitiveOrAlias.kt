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
package com.gw2tb.apigen.ir

import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.schema.SchemaPrimitiveIdentifierOrAlias
import com.gw2tb.apigen.schema.SchemaPrimitiveOrAlias

/**
 * TODO doc
 *
 * @since   0.7.0
 */
public sealed interface IRPrimitiveOrAlias

/**
 * TODO doc
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public sealed interface IRPrimitiveIdentifierOrAlias : IRPrimitiveOrAlias

/**
 * TODO doc
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public tailrec fun IRPrimitiveOrAlias.lowered(): IRPrimitive {
    if (this is IRPrimitive) return this
    return (this as IRTypeReference.Alias).alias.type.lowered()
}

/**
 * TODO doc
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public tailrec fun IRPrimitiveIdentifierOrAlias.lowered(): IRPrimitiveIdentifier {
    if (this is IRPrimitiveIdentifier) return this
    return ((this as IRTypeReference.Alias).alias.type as IRPrimitiveIdentifierOrAlias).lowered()
}

@OptIn(LowLevelApiGenApi::class)
internal fun IRPrimitiveOrAlias.resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaPrimitiveOrAlias = when (this) {
    is IRPrimitive -> resolve(resolverContext, v2SchemaVersion)
    is IRTypeReference.Alias -> resolve(resolverContext, v2SchemaVersion)
}

@OptIn(LowLevelApiGenApi::class)
internal fun IRPrimitiveIdentifierOrAlias.resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaPrimitiveIdentifierOrAlias = when (this) {
    is IRPrimitiveIdentifier -> resolve(resolverContext, v2SchemaVersion)
    is IRTypeReference.Alias -> resolve(resolverContext, v2SchemaVersion)
}