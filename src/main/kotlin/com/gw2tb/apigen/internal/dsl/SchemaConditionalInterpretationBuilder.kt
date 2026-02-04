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
import com.gw2tb.apigen.internal.impl.containsChangeForBounds
import com.gw2tb.apigen.ir.IRConditional
import com.gw2tb.apigen.ir.IRTypeUse
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.schema.model.APIType
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.v2.SchemaVersion

internal class SchemaConditionalInterpretationBuilder(
    private val interpretationKey: String,
    private val interpretationNestProperty: String?,
    private val type: DeferredType<IRTypeUse<*>>
) {

    var isDeprecated = false
        set(value) {
            require(isUnused)
            field = value
        }

    var since: SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var until: SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    private lateinit var _value: SchemaVersionedDataImpl<IRConditional.Interpretation>
    private val isUnused get() = !this::_value.isInitialized

    fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        version: SchemaVersion?,
        conditionalBase: QualifiedTypeName
    ): IRConditional.Interpretation {
        if (!this::_value.isInitialized) {
            val interpretationHint = APIType.InterpretationHint(
                conditionalBase = conditionalBase,
                interpretationKey = interpretationKey,
                interpretationNestProperty = interpretationNestProperty
            )

            _value = type.get(typeRegistry, interpretationHint = interpretationHint).mapData {
                IRConditional.Interpretation(
                    interpretationKey = interpretationKey,
                    interpretationNestProperty = interpretationNestProperty,
                    type = it,
                    isDeprecated = isDeprecated,
                    since = since,
                    until = until
                )
            }
        }

        return _value.getOrThrow(version ?: SchemaVersion.V2_SCHEMA_CLASSIC).data
    }

    fun hasChangedInVersion(
        typeRegistry: ScopedTypeRegistry<*>?,
        version: SchemaVersion,
        conditionalBase: QualifiedTypeName
    ): Boolean {
        get(typeRegistry, version, conditionalBase)
        return version.containsChangeForBounds(since, until) || _value.hasChangedInVersion(version)
    }

}