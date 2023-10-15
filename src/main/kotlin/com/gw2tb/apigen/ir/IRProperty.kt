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
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.Optionality
import com.gw2tb.apigen.schema.SchemaProperty

/**
 * A low-level representation of a [SchemaProperty].
 *
 * @param name          the name of the property
 * @param type          the type of the property
 * @param isDeprecated  whether the property is deprecated
 * @param isInline      whether the property is referencing an object that is
 *                      embedded into the containing object
 * @param isLenient     whether the property should be parsed leniently
 * @param isLocalized   whether the value is a localized string
 * @param optionality   the optionality of the property
 * @param serialName    the serial name of the property
 * @param description   a description of the property
 * @param since         the lower bound version (inclusive)
 * @param until         the upper bound version (exclusive)
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRProperty internal constructor(
    public val name: Name,
    public val type: IRTypeUse<*>,
    public val isDeprecated: Boolean,
    public val isInline: Boolean,
    public val isLenient: Boolean,
    public val isLocalized: Boolean,
    public val optionality: Optionality,
    public val serialName: String,
    public val description: String,
    public val since: SchemaVersion?,
    public val until: SchemaVersion?
) {

    internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaProperty? {
        if (v2SchemaVersion != null) {
            if (since != null && since > v2SchemaVersion) return null
            if (until != null && until < v2SchemaVersion) return null
        }

        val type = type.resolve(resolverContext)

        return SchemaProperty(
            name = name,
            type = type,
            description = description,
            isDeprecated = isDeprecated,
            isInline = isInline,
            isLenient = isLenient,
            isLocalized = isLocalized,
            optionality = optionality,
            serialName = serialName
        )
    }

}