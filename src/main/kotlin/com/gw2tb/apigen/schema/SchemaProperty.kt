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
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.v2.V2SchemaVersion

/**
 * A schema property.
 *
 * ### Names and Description
 *
 * A property has multiple name attributes which differ mostly in casing:
 *
 * 1. [propertyName] - the name of the property in TitleCase (e.g. `ItemID`),
 * 2. [camelCaseName] - the name of the property in camelCase (e.g. `itemID`), and
 * 3. [serialName] - the name of the property in snake_case (e.g. `item_id`).
 *
 * The serial name is also the name of the property that should be used during
 * de-/serialization (hence its name). It exactly matches name of the property
 * in the JSON-encoded API response.
 *
 * Additionally, a property also has a [description]. The description of a
 * property should be worded to fit into the following sentence:
 *
 * > This property holds {description}.
 *
 * (i.e. "This property holds the item's ID")
 *
 *
 * ### Deprecation
 *
 * Properties may be [isDeprecated]. This attribute is purely informational and
 * does not have any semantic meaning. However, it is good practice to consume
 * and to make it visible in generated sources anyway when including the
 * property.
 *
 * Usually, there are more flexible alternatives or replacements to retrieve the
 * data exposed in deprecated properties.
 *
 *
 * ### Inlining
 *
 * A property of a reference type may be marked as [inlined][isInline]. Inlined
 * properties are
 *
 * TODO
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
 *
 * ### Schema versioning
 *
 * Since the schema is versioned in version 2 of the API, some properties may be
 * dependent on the schema version. These dependencies are expressed using the
 * two attributes [since] which specifies the minimal schema version required
 * for the property (inclusive) and [until] which specifies the schema version
 * until which the property is available. (Note that `since` is an inclusive
 * bound while `until` is an exclusive bound: `[since, until)`).
 *
 * Note that it is possible that properties with the same name but different
 * types are available in different schema versions.
 *
 * @param type          the type of the property
 * @param optionality   the [Optionality] of the property
 */
public data class SchemaProperty internal constructor(
    public val propertyName: String,
    public val type: SchemaTypeUse,
    public val description: String,
    public val isDeprecated: Boolean,
    public val isInline: Boolean,
    public val isLenient: Boolean,
    public val isLocalized: Boolean,
    public val optionality: Optionality,
    public val since: V2SchemaVersion?,
    public val until: V2SchemaVersion?,
    public val serialName: String,
    public val camelCaseName: String
) {


}