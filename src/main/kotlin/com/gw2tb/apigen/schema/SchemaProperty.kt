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

import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.Optionality

/**
 * A `SchemaProperty` typically represents an entry in a JSON object.
 *
 * ### Deprecation
 *
 * Properties may be [isDeprecated]. This attribute is purely informational and
 * does not have any semantic meaning. However, it is good practice to relay
 * this information (e.g. using the `@Deprecated` annotation in Java).
 *
 * Usually, there are more flexible alternatives or replacements to retrieve the
 * data exposed in deprecated properties.
 *
 *
 * ### Inlining
 *
 * A property of a [reference type][SchemaTypeReference] may be marked as [inlined][isInline].
 * Inlined properties do not represent an entry in a JSON object and instead
 * indicate that the referenced type is embedded in the object.
 *
 * Inlining properties is useful for extracting logical groups that are not
 * strongly grouped in the data.
 *
 *
 * ### Type coercion
 *
 * A property of a primitive type may be marked as [isLenient]. Such properties
 * must be treated with special care as the property's type may not match the
 * JSON type. Instead, the parser should coerce JSON strings into the property's
 * type (e.g. by treating the string `"42"` as the integer `42`). Additionally,
 * when the empty string is found, the element should be treated as if no value
 * were set.
 *
 *
 * ### Localization
 *
 * Usually, the value for a property is expected to be consistent across queries
 * for different languages. However, a property may be marked as [isLocalized].
 * Localized properties are exempt from this rule and should and are expected to
 * return localized results.
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
 *
 * @since   0.7.0
 */
@ConsistentCopyVisibility
public data class SchemaProperty internal constructor(
    public val name: Name,
    public val type: SchemaTypeUse,
    public val isDeprecated: Boolean,
    public val isInline: Boolean,
    public val isLenient: Boolean,
    public val isLocalized: Boolean,
    public val optionality: Optionality,
    public val serialName: String,
    public val description: String
)