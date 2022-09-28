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
@file:Suppress("RedundantVisibilityModifier", "unused")
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*

/** A type usage. */
public sealed class SchemaTypeUse {

    public abstract val isLocalized: Boolean

}

/**
 * A schema representation of a primitive type.
 *
 * ### Localization
 *
 * A primitive does not make any assumptions about the value and, thus, is never
 * considered to be [isLocalized].
 *
 * ### Type Hints
 *
 * Primitives may be enhanced with [TypeHint]s. These hints are used to provide
 * usage information about a type and can be used to generate type-safe wrappers
 * or type-aliases.
 */
public sealed class SchemaPrimitive : SchemaTypeUse() {

    override val isLocalized: Boolean get() = false

    public abstract val typeHint: TypeHint?

    public abstract fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive

    public data class TypeHint(
        val camelCaseName: String
    )

}

/** A schema representing bitfield types. */
public data class SchemaBitfield(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive boolean types. */
public data class SchemaBoolean(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive decimal types. */
public data class SchemaDecimal(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive integer types. */
public data class SchemaInteger(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/** A schema representing primitive string types. */
public data class SchemaString(override val typeHint: TypeHint? = null) : SchemaPrimitive() {
    override fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive = copy(typeHint = typeHint)
}

/**
 * A schema representation of an array/list.
 *
 * ### Localization
 *
 * An array is considered to be localized when its elements are localized.
 */
public data class SchemaArray(
    val elements: SchemaTypeUse,
    val nullableElements: Boolean,
    val description: String?
) : SchemaTypeUse() {
    override val isLocalized: Boolean get() = elements.isLocalized
}

/**
 * A schema representation of a map.
 *
 * ### Localization
 *
 * An array is considered to be localized when its values are localized.
 */
public data class SchemaMap(
    val keys: SchemaPrimitive,
    val values: SchemaTypeUse,
    val nullableValues: Boolean,
    val description: String?
) : SchemaTypeUse() {
    override val isLocalized: Boolean get() = values.isLocalized
}

/**
 * A reference to a schema representation of a complex type (i.e. a [SchemaTypeDeclaration]).
 */
public data class SchemaTypeReference(
    val typeLocation: TypeLocation,
    val version: V2SchemaVersion,
    internal val declaration: SchemaTypeDeclaration
) : SchemaTypeUse() {
    val name: String get() = declaration.name
    override val isLocalized: Boolean get() = declaration.isLocalized
}

/** A type declaration. */
public sealed class SchemaTypeDeclaration {

    public abstract val name: String

    internal abstract val isLocalized: Boolean

}

public data class SchemaConditional(
    public override val name: String,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Map<String, SchemaProperty>,
    public val interpretations: Map<String, Interpretation>,
    public val description: String
) : SchemaTypeDeclaration() {

    override val isLocalized: Boolean by lazy {
        sharedProperties.any { (_, v) -> v.isLocalized || v.type.isLocalized } || interpretations.any { (_, v) -> v.type.isLocalized }
    }

    /**
     * A conditional interpretation.
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    TODO doc
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether the interpretation is deprecated
     * @param since                         the minimum [V2SchemaVersion] required for the interpretation
     * @param until                         the [V2SchemaVersion] up to which the interpretation existed
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: SchemaTypeUse,
        public val isDeprecated: Boolean,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?
    )

}

public data class SchemaRecord(
    public override val name: String,
    public val properties: Map<String, SchemaProperty>,
    public val description: String
) : SchemaTypeDeclaration() {

    override val isLocalized: Boolean by lazy {
        properties.any { (_, v) -> v.isLocalized || v.type.isLocalized }
    }

}

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
 * Usually, there are better alternatives for the information in deprecated
 * properties.
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
)

/**
 * A property's optionality. There are three different _levels_ of optionality:
 *
 * 1. Optional - The property is unconditionally optional,
 * 2. Mandated - The property is required under certain conditions, and
 * 3. Required - The property is required.
 */
public sealed class Optionality {

    /** Returns `true` for [OPTIONAL] and [MANDATED], and `false` otherwise. */
    public abstract val isOptional: Boolean

    /** The property is optional. */
    public object OPTIONAL : Optionality() {
        override val isOptional: Boolean = true
    }

    /** The property is required for a key with the appropriate [scope]. */
    public class MANDATED(public val scope: TokenScope) : Optionality() {
        override val isOptional: Boolean = true
    }

    /** The property is required. */
    public object REQUIRED : Optionality() {
        override val isOptional: Boolean = false
    }

}