/*
 * Copyright (c) 2019-2020 Leon Linhart
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
package com.github.gw2toolbelt.apigen.schema

import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.model.v2.*

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
 * @param description   TODO
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
 * @param description       TODO
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
 * @param disambiguationBy  the serial name of the disambiguation-property
 * @param interpretations   the available interpretations
 */
public data class SchemaConditional internal constructor(
    public val disambiguationBy: String,
    public val interpretations: Map<String, SchemaType>
) : SchemaType()

/**
 * A schema for records.
 *
 * @param   properties      the properties of this record
 * @param   description     TODO
 */
public data class SchemaRecord(
    public val properties: Map<String, Property>,
    public val description: String?
) : SchemaType() {

    /**
     * A record property.
     *
     * @param   propertyName    the name of the property in title-case (e.g. "ItemId")
     * @param   type            the schema definition for this property
     * @param   description     TODO
     * @param   isDeprecated    TODO
     * @param   optionality     the [Optionality] of this property
     * @param   since           TODO
     * @param   until           TODO
     * @param   serialName      the serial name of the property
     * @param   camelCaseName   the name of the property in camelCase
     */
    public data class Property internal constructor(
        public val propertyName: String,
        public val type: SchemaType,
        public val description: String?,
        public val isDeprecated: Boolean,
        public val optionality: Optionality,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?,
        public val serialName: String,
        public val camelCaseName: String?
    )

}

public sealed class Optionality {

    public abstract val isOptional: Boolean

    public object OPTIONAL : Optionality() {
        override val isOptional = true
    }

    public class MANDATED(public val scope: TokenScope) : Optionality() {
        override val isOptional = true
    }

    public object REQUIRED : Optionality() {
        override val isOptional = false
    }

}