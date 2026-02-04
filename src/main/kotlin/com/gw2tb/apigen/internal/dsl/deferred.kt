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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.SchemaVersionedDataImpl
import com.gw2tb.apigen.internal.impl.wrapVersionedSchemaData
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.schema.model.APIType

/** Alias for [SchemaBitfield] */
internal val BITFIELD: DeferredPrimitiveType.BuiltIn = DeferredPrimitiveType.BuiltIn(IRBitfield)

/** Alias for [SchemaBoolean]. */
internal val BOOLEAN: DeferredPrimitiveType.BuiltIn = DeferredPrimitiveType.BuiltIn(IRBoolean)

/** Alias for [SchemaDecimal]. */
internal val DECIMAL: DeferredPrimitiveType.BuiltIn = DeferredPrimitiveType.BuiltIn(IRDecimal)

/** Alias for [SchemaInteger]. */
internal val INTEGER: DeferredPrimitiveType.BuiltIn = DeferredPrimitiveType.BuiltIn(IRInteger)

/** Alias for [SchemaString]. */
internal val STRING: DeferredPrimitiveType.BuiltIn = DeferredPrimitiveType.BuiltIn(IRString)

internal fun Alias(type: DeferredPrimitiveType.BuiltIn, name: String, description: String = "TODO"): DeferredPrimitiveType.Alias = // TODO desc
    Alias(type, Name.deriveFromTitleCase(name), description)

internal fun Alias(type: DeferredPrimitiveType.BuiltIn, name: Name, description: String = "TODO"): DeferredPrimitiveType.Alias = // TODO desc
    DeferredPrimitiveType.Alias(name, type, description)

internal abstract class DeferredType<out T : IRTypeUse<*>> {

    abstract fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean = false
    ): SchemaVersionedDataImpl<out T>

    fun getFlat(): T =
        get(typeRegistry = null, interpretationHint = null).single().data

}

internal sealed class DeferredPrimitiveType<out T> : DeferredType<T>()
    where T : IRTypeUse<*>, T : IRPrimitiveOrAlias {

    abstract override fun get(typeRegistry: ScopedTypeRegistry<*>?, interpretationHint: APIType.InterpretationHint?, isTopLevel: Boolean): SchemaVersionedDataImpl<out T>

    class Alias(
        private val name: Name,
        private val type: BuiltIn,
        private val description: String
    ) : DeferredPrimitiveType<IRTypeReference.Alias>() {

        override fun get(
            typeRegistry: ScopedTypeRegistry<*>?,
            interpretationHint: APIType.InterpretationHint?,
            isTopLevel: Boolean
        ): SchemaVersionedDataImpl<out IRTypeReference.Alias> {
            val irAlias = IRAlias(
                name = name,
                type = type.getFlat(),
                description = description
            )

            val irReference = IRTypeReference.Alias(
                name = QualifiedTypeName.Alias(name),
                alias = irAlias
            )

            return wrapVersionedSchemaData(irReference)
        }

    }

    data class BuiltIn(
        private val value: IRPrimitive
    ) : DeferredPrimitiveType<IRPrimitive>() {

        override fun get(
            typeRegistry: ScopedTypeRegistry<*>?,
            interpretationHint: APIType.InterpretationHint?,
            isTopLevel: Boolean
        ): SchemaVersionedDataImpl<out IRPrimitive> =
            wrapVersionedSchemaData(value)

    }

}