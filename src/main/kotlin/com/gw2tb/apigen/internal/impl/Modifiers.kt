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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.internal.dsl.IPropertyModifier
import com.gw2tb.apigen.model.Optionality

internal object Modifiers {

    abstract class PropertyModifier : IPropertyModifier
    abstract class SharedModifier : IInterpretationModifier, IPropertyModifier

    val deprecated = object : SharedModifier() {

        override fun applyTo(interpretation: SchemaConditionalInterpretationBuilder) {
            interpretation.isDeprecated = true
        }

        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.isDeprecated = true
        }

    }

    val inline = object : PropertyModifier() {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.isInline = true
        }
    }

    val lenient = object : PropertyModifier() {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.isLenient = true
        }
    }

    val localized = object : PropertyModifier() {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.isLocalized = true
        }
    }

    val optional = object : PropertyModifier() {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.optionality = Optionality.OPTIONAL
        }
    }

}