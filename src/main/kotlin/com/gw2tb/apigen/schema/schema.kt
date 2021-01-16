/*
 * Copyright (c) 2019-2021 Leon Linhart
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

public sealed class SchemaType
public sealed class SchemaPrimitive : SchemaType()

/** A schema representing primitive boolean types. */
public object SchemaBoolean : SchemaPrimitive()

/** A schema representing primitive decimal types. */
public object SchemaDecimal : SchemaPrimitive()

/** A schema representing primitive integer types. */
public object SchemaInteger : SchemaPrimitive()

/** A schema representing primitive string types. */
public object SchemaString : SchemaPrimitive()

/**
 * A schema for lists.
 *
 * @param items         the schema definition for the elements of this list
 * @param nullableItems whether or not the items may contain `null`
 * @param description   the description of the type or `null`. (Should be worded to complete the sentence "This field
 *                      holds {description}.")
 */
public data class SchemaArray internal constructor(
    public val items: SchemaType,
    public val nullableItems: Boolean,
    public val description: String?
) : SchemaType()

/**
 * A schema for maps.
 *
 * @param keys              the schema definition for the keys of this map
 * @param values            the schema definition for the values of this map
 * @param nullableValues    whether or not the values may contain `null`
 * @param description       the description of the type or `null`. (Should be worded to complete the sentence "This
 *                          field holds {description}.")
 */
public data class SchemaMap internal constructor(
    public val keys: SchemaPrimitive,
    public val values: SchemaType,
    public val nullableValues: Boolean,
    public val description: String?
) : SchemaType()

/**
 * A schema for a sealed hierarchy (i.e. algebraic sum type-like construct).
 *
 * The interpretation is chosen based on a "disambiguation-property".
 *
 * @param name                          the name of the conditional in _TitleCase_
 * @param disambiguationBy              the serial name of the disambiguation-
 *                                      property
 * @param disambiguationBySideProperty  `true` if the disambiguation-property
 *                                      is on the same level as the conditional,
 *                                      or `false` if it is a member
 * @param sharedProperties              the properties available in all
 *                                      interpretations of this conditional
 * @param interpretations               the available interpretations
 * @param description                   the description of the type or `null`. (Should be worded to complete the
 *                                      sentence "This field holds {description}.")
 */
public data class SchemaConditional internal constructor(
    public val name: String?,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
    public val sharedProperties: Map<String, SchemaRecord.Property>,
    public val interpretations: Map<String, Interpretation>,
    public val description: String
) : SchemaType() {

    /**
     * A conditional interpretation.
     *
     * @param interpretationKey the key used to identify the interpretation
     * @param type              the schema definition for this interpretation
     * @param isDeprecated      whether or not the interpretation is deprecated
     * @param since             the minimum [V2SchemaVersion] required for the interpretation
     * @param until             the [V2SchemaVersion] up to which the interpretation existed
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val type: SchemaType,
        public val isDeprecated: Boolean,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?
    )

}

/**
 * A schema for records.
 *
 * @param name          the name of the record in _TitleCase_
 * @param properties    the properties of this record
 * @param description   the description of the type or `null`. (Should be worded to complete the sentence "This field
 *                      holds {description}.")
 */
public data class SchemaRecord(
    public val name: String?,
    public val properties: Map<String, Property>,
    public val description: String
) : SchemaType() {

    /**
     * A record property.
     *
     * @param propertyName  the name of the property in title-case (e.g. "ItemId")
     * @param type          the schema definition for this property
     * @param description   the description of the property. (Should be worded to complete the sentence "This field
     *                      holds {description}.")
     * @param isDeprecated  whether or not the property is deprecated
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
        public val optionality: Optionality,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?,
        public val serialName: String,
        public val camelCaseName: String
    )

}

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