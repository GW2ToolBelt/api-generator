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

public sealed class SchemaPrimitive : SchemaTypeUse() {

    override val isLocalized: Boolean get() = false

    public abstract val typeHint: TypeHint?

    public abstract fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive

    public data class TypeHint(
        val camelCaseName: String
    )

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

public data class SchemaArray(
    val elements: SchemaTypeUse,
    val nullableElements: Boolean,
    val description: String?
) : SchemaTypeUse() {
    override val isLocalized: Boolean get() = elements.isLocalized
}

public data class SchemaMap(
    val keys: SchemaPrimitive,
    val values: SchemaTypeUse,
    val nullableValues: Boolean,
    val description: String?
) : SchemaTypeUse() {
    override val isLocalized: Boolean get() = values.isLocalized
}

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
 * A record property.
 *
 * @param propertyName  the name of the property in title-case (e.g. "ItemId")
 * @param type          the schema definition for this property
 * @param description   the description of the property. (Should be worded to complete the sentence "This field
 *                      holds {description}.")
 * @param isDeprecated  whether the property is deprecated
 * @param isLocalized
 * @param optionality   the [Optionality] of this property
 * @param since         the minimum [V2SchemaVersion] required for the property
 * @param until         the [V2SchemaVersion] up to which the property existed
 * @param serialName    the serial name of the property
 * @param camelCaseName the name of the property in camelCase
 */
public data class SchemaProperty internal constructor(
    public val propertyName: String,
    public val type: SchemaTypeUse,
    public val description: String,
    public val isDeprecated: Boolean,
    public val isLocalized: Boolean,
    public val optionality: Optionality,
    public val since: V2SchemaVersion?,
    public val until: V2SchemaVersion?,
    public val serialName: String,
    public val camelCaseName: String
)

/** A property's optionality. */
public sealed class Optionality {

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














/*

public sealed class SchemaType {
    public abstract val isLocalized: Boolean
    internal abstract fun equalsSignature(other: SchemaType): Boolean
}

public sealed class SchemaPrimitive : SchemaType(), SchemaTypeUse {
    override val isLocalized: Boolean = false
    public abstract val typeHint: TypeHint?

    internal abstract fun withTypeHint(typeHint: TypeHint?): SchemaPrimitive

    public data class TypeHint(
        val s: String
    )
}

/**
 * A schema for lists.
 *
 * @param items         the schema definition for the elements of this list
 * @param nullableItems whether the items may contain `null`
 * @param description   the description of the type or `null`. (Should be worded to complete the sentence "This field
 *                      holds {description}.")
 */
public data class SchemaArray internal constructor(
    public val items: SchemaType,
    public val nullableItems: Boolean,
    public val description: String?
) : SchemaType() {
    override val isLocalized: Boolean get() = items.isLocalized
    override fun equalsSignature(other: SchemaType) = other is SchemaArray && items.equalsSignature(other.items)
}

/**
 * A schema for maps.
 *
 * @param keys              the schema definition for the keys of this map
 * @param values            the schema definition for the values of this map
 * @param nullableValues    whether the values may contain `null`
 * @param description       the description of the type or `null`. (Should be worded to complete the sentence "This
 *                          field holds {description}.")
 */
public data class SchemaMap internal constructor(
    public val keys: SchemaPrimitive,
    public val values: SchemaType,
    public val nullableValues: Boolean,
    public val description: String?
) : SchemaType() {
    override val isLocalized: Boolean get() = values.isLocalized
    override fun equalsSignature(other: SchemaType) = other is SchemaMap && keys.equalsSignature(other.keys) && values.equalsSignature(other.values)
}

/**
 * A schema for a sealed hierarchy (i.e. algebraic sum type-like construct).
 *
 * The interpretation is chosen based on a "disambiguation-property".
 *
 * @param name                              the name of the conditional in _TitleCase_
 * @param disambiguationBy                  the serial name of the disambiguation-
 *                                          property
 * @param disambiguationBySideProperty      `true` if the disambiguation-property
 *                                          is on the same level as the conditional,
 *                                          or `false` if it is a member
 * @param interpretationInNestedProperty    TODO doc
 * @param sharedProperties                  the properties available in all
 *                                          interpretations of this conditional
 * @param interpretations                   the available interpretations
 * @param description                       the description of the type or `null`. (Should be worded to complete the
 *                                          sentence "This field holds {description}.")
 */
public data class SchemaConditional internal constructor(
    public override val name: String,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Map<String, SchemaRecord.Property>,
    public val interpretations: Map<String, Interpretation>,
    public val description: String
) : SchemaClass() {

    override val isLocalized: Boolean = sharedProperties.any { (_, v) -> v.isLocalized || v.type.isLocalized } || interpretations.any { (_, v) -> v.type.isLocalized }

    init {
        if (!interpretationInNestedProperty) {
            interpretations.forEach { (_, interpretation) ->
                (interpretation.type as SchemaRecord).properties.forEach { (key, _) ->
                    if (key in sharedProperties) error("Property key '$key' in interpretation '${interpretation.interpretationKey}' is also in shared properties of $name")
                }
            }
        }
    }

    override fun equalsSignature(other: SchemaType) = other is SchemaConditional
        && disambiguationBySideProperty == other.disambiguationBySideProperty
        && interpretationInNestedProperty == other.interpretationInNestedProperty
        && sharedProperties.all { (k, v) -> v.equalsSignature(other.sharedProperties[k]!!) }
        && interpretations.all { (k, v) -> v.equalsSignature(other.interpretations[k]!!) }

    /**
     * A conditional interpretation.
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    TODO doc
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether or not the interpretation is deprecated
     * @param since                         the minimum [V2SchemaVersion] required for the interpretation
     * @param until                         the [V2SchemaVersion] up to which the interpretation existed
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: SchemaType,
        public val isDeprecated: Boolean,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?
    ) {

        internal fun equalsSignature(other: Interpretation): Boolean = type.equalsSignature(other.type)

    }

}

/**
 * A schema for records.
 *
 * @param name          the name of the record in _TitleCase_
 * @param properties    the properties of this record
 * @param description   the description of the type or `null`. (Should be worded to complete the sentence "This field
 *                      holds {description}.")
 */
public data class SchemaRecord internal constructor(
    public override val name: String,
    public val properties: Map<String, Property>,
    public val description: String
) : SchemaClass() {

    override val isLocalized: Boolean = properties.any { (_, v) -> v.isLocalized || v.type.isLocalized }

    override fun equalsSignature(other: SchemaType) = other is SchemaRecord
        && properties.all { (k, v) -> v.equalsSignature(other.properties[k]!!) }

    /**
     * A record property.
     *
     * @param propertyName  the name of the property in title-case (e.g. "ItemId")
     * @param type          the schema definition for this property
     * @param description   the description of the property. (Should be worded to complete the sentence "This field
     *                      holds {description}.")
     * @param isDeprecated  whether or not the property is deprecated
     * @param isLocalized
     * @param optionality   the [Optionality] of this property
     * @param since         the minimum [V2SchemaVersion] required for the property
     * @param until         the [V2SchemaVersion] up to which the property existed
     * @param serialName    the serial name of the property
     * @param camelCaseName the name of the property in camelCase
     */
    public data class Property internal constructor(
        public val propertyName: String,
        public val type: SchemaType,
        public val description: String,
        public val isDeprecated: Boolean,
        public val isLocalized: Boolean,
        public val optionality: Optionality,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?,
        public val serialName: String,
        public val camelCaseName: String
    ) {

        internal fun equalsSignature(other: Property): Boolean = type.equalsSignature(other.type)
            && optionality == other.optionality

    }

}
 */