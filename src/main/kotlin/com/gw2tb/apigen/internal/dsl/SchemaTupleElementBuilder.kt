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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.SchemaVersionedDataImpl
import com.gw2tb.apigen.internal.impl.requireCamelCase
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.model.Name

internal class SchemaTupleElementBuilder(
    val nameTitleCase: String,
    val type: DeferredSchemaType<out IRTypeUse<*>>,
    val description: String
) {

    var serialName: String? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)

            field = value
        }

    var camelCase: String? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)
            requireCamelCase(value, "Element::camelCase")

            field = value
        }

    var isLenient = false
        set(value) {
            check(isUnused)
            require(value) { "Element::isLenient is `false` by default and should only be set to `true`." }

            field = value
        }

    var isLocalized = false
        set(value) {
            check(isUnused)
            require(value) { "Element::isLocalized is `false` by default and should only be set to `true`." }

            field = value
        }

    private lateinit var _value: SchemaVersionedDataImpl<IRTuple.Element>
    private val isUnused get() = !this::_value.isInitialized

    fun get(typeRegistry: ScopedTypeRegistry<*>?, v2SchemaVersion: SchemaVersion?): IRTuple.Element {
        if (!this::_value.isInitialized) {
            val name = Name.derive(camelCase = camelCase, snakeCase = serialName, titleCase = nameTitleCase)

            _value = type.get(typeRegistry, interpretationHint = null).mapData { type ->
                IRTuple.Element(
                    name = name,
                    type = type,
                    description = description,
                    isLenient = isLenient,
                    isLocalized = isLocalized
                )
            }
        }

        return _value.getOrThrow(v2SchemaVersion ?: SchemaVersion.V2_SCHEMA_CLASSIC).data
    }

    fun hasChangedInVersion(typeRegistry: ScopedTypeRegistry<*>?, version: SchemaVersion): Boolean {
        get(typeRegistry, version)
        return _value.hasChangedInVersion(version)
    }

}