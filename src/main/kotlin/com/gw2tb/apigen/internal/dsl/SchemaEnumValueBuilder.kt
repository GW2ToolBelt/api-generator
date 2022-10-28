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

import com.gw2tb.apigen.internal.impl.containsChangeForBounds
import com.gw2tb.apigen.internal.impl.requireCamelCase
import com.gw2tb.apigen.internal.impl.requireTitleCase
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.Name

internal class SchemaEnumValueBuilder(
    private val nameTitleCase: String,
    private val value: String,
    private val description: String
) {

    init {
        requireTitleCase(nameTitleCase, "valueName")
    }

    var isDeprecated = false
        set(value) {
            check(isUnused)
            require(value) { "EnumValue::isDeprecated is `false` by default and should only be set to `true`." }

            field = value
        }

    var since: V2SchemaVersion? = null
        set(value) {
            check(isUnused)
            requireNotNull(value)

            field = value
        }

    var until: V2SchemaVersion? = null
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
            requireCamelCase(value, "EnumValue::camelCase")

            field = value
        }

    private lateinit var _value: IREnum.Value
    private val isUnused get() = !this::_value.isInitialized

    fun get(): IREnum.Value {
        if (!this::_value.isInitialized) {
            val name = Name.derive(camelCase = camelCase, snakeCase = serialName, titleCase = nameTitleCase)

            _value = IREnum.Value(
                name = name,
                value = value,
                description = description
            )
        }

        return _value
    }

    fun hasChangedInVersion(version: V2SchemaVersion): Boolean {
        get()
        return version.containsChangeForBounds(since, until)
    }

}