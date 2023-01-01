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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.SchemaVersionedDataImpl
import com.gw2tb.apigen.internal.impl.containsChangeForBounds
import com.gw2tb.apigen.internal.impl.requireCamelCase
import com.gw2tb.apigen.internal.impl.requireTitleCase
import com.gw2tb.apigen.ir.IRProperty
import com.gw2tb.apigen.ir.IRTypeUse
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.Optionality

internal class SchemaRecordPropertyBuilder(
    private val nameTitleCase: String,
    private val type: DeferredSchemaType<out IRTypeUse<*>>,
    private val description: String
) {

    init {
        requireTitleCase(nameTitleCase, "propertyName")
    }

    var isDeprecated = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isDeprecated is `false` by default and should only be set to `true`." }

            field = value
        }

    var isInline = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isInline is `false` by default and should only be set to `true`." }

            field = value
        }

    var isLenient = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isLenient is `false` by default and should only be set to `true`." }

            field = value
        }

    var isLocalized = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isLocalized is `false` by default and should only be set to `true`." }

            field = value
        }

    var optionality: Optionality? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)

            field = value
        }

    var since: SchemaVersion? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)

            field = value
        }

    var until: SchemaVersion? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)

            field = value
        }

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
            requireCamelCase(value, "Property::camelCase")

            field = value
        }

    private lateinit var _value: SchemaVersionedDataImpl<IRProperty>
    private val isUnused get() = !this::_value.isInitialized

    fun get(typeRegistry: ScopedTypeRegistry<*>?, v2SchemaVersion: SchemaVersion?): IRProperty {
        if (!this::_value.isInitialized) {
            val name = Name.derive(camelCase = camelCase, snakeCase = serialName, titleCase = nameTitleCase)

            _value = type.get(typeRegistry, interpretationHint = null).mapData { type ->
                IRProperty(
                    name = name,
                    type = type,
                    description = description,
                    optionality = optionality ?: Optionality.REQUIRED,
                    isDeprecated = isDeprecated,
                    isInline = isInline,
                    isLenient = isLenient,
                    isLocalized = isLocalized,
                    since = since,
                    until = until,
                    serialName = name.toSnakeCase()
                )
            }
        }

        return _value.getOrThrow(v2SchemaVersion ?: SchemaVersion.V2_SCHEMA_CLASSIC).data
    }

    fun hasChangedInVersion(typeRegistry: ScopedTypeRegistry<*>?, version: SchemaVersion): Boolean {
        get(typeRegistry, version)
        return version.containsChangeForBounds(since, until) || _value.hasChangedInVersion(version)
    }

}