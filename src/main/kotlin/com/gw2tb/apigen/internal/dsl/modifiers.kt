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
package com.gw2tb.apigen.internal.dsl

internal operator fun IInterpretationModifier.rangeTo(modifier: IInterpretationModifier): Set<IInterpretationModifier> = setOf(this, modifier)
internal operator fun Set<IInterpretationModifier>.rangeTo(modifier: IInterpretationModifier): Set<IInterpretationModifier> = setOf(modifier, *this.toTypedArray())

internal operator fun IInterpretationModifier.rangeTo(interpretation: SchemaConditionalInterpretationBuilder): SchemaConditionalInterpretationBuilder = interpretation.also { this.applyTo(it) }
internal operator fun Set<IInterpretationModifier>.rangeTo(interpretation: SchemaConditionalInterpretationBuilder): SchemaConditionalInterpretationBuilder = interpretation.also { forEach { mod -> mod.applyTo(it) } }

internal interface IInterpretationModifier {
    fun applyTo(interpretation: SchemaConditionalInterpretationBuilder)
}


internal operator fun IPropertyModifier.rangeTo(modifier: IPropertyModifier): Set<IPropertyModifier> = setOf(this, modifier)
internal operator fun Set<IPropertyModifier>.rangeTo(modifier: IPropertyModifier): Set<IPropertyModifier> = setOf(modifier, *this.toTypedArray())

internal operator fun IPropertyModifier.rangeTo(property: SchemaRecordPropertyBuilder): SchemaRecordPropertyBuilder = property.also { this.applyTo(it) }
internal operator fun Set<IPropertyModifier>.rangeTo(property: SchemaRecordPropertyBuilder): SchemaRecordPropertyBuilder = property.also { forEach { mod -> mod.applyTo(it) } }

internal interface IPropertyModifier {
    fun applyTo(property: SchemaRecordPropertyBuilder)
}


internal operator fun ITupleElementModifier.rangeTo(modifier: ITupleElementModifier): Set<ITupleElementModifier> = setOf(this, modifier)
internal operator fun Set<ITupleElementModifier>.rangeTo(modifier: ITupleElementModifier): Set<ITupleElementModifier> = setOf(modifier, *this.toTypedArray())

internal operator fun ITupleElementModifier.rangeTo(interpretation: SchemaTupleElementBuilder): SchemaTupleElementBuilder = interpretation.also { this.applyTo(it) }
internal operator fun Set<ITupleElementModifier>.rangeTo(interpretation: SchemaTupleElementBuilder): SchemaTupleElementBuilder = interpretation.also { forEach { mod -> mod.applyTo(it) } }

internal interface ITupleElementModifier {
    fun applyTo(element: SchemaTupleElementBuilder)
}