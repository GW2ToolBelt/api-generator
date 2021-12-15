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
@file:Suppress("FunctionName")
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*

/** Alias for [SchemaBoolean]. */
internal val BOOLEAN get() = SchemaPrimitiveReference(SchemaBoolean())

/** Alias for [SchemaDecimal]. */
internal val DECIMAL get() = SchemaPrimitiveReference(SchemaDecimal())

/** Alias for [SchemaInteger]. */
internal val INTEGER get() = SchemaPrimitiveReference(SchemaInteger())

/** Alias for [SchemaString]. */
internal val STRING get() = SchemaPrimitiveReference(SchemaString())

internal interface SchemaTypeReference {

    fun get(register: TypeRegistryScope): SchemaType

}

internal class SchemaPrimitiveReference(
    internal val type: SchemaPrimitive
) : SchemaTypeReference {

    private lateinit var value: SchemaPrimitive

    override fun get(register: TypeRegistryScope): SchemaPrimitive {
        if (!this::value.isInitialized) value = type // TODO register
        return value
    }

}

internal class SchemaArrayReference(private val factory: (register: TypeRegistryScope) -> SchemaArray) : SchemaTypeReference {

    private lateinit var value: SchemaArray

    override fun get(register: TypeRegistryScope): SchemaType {
        if (!this::value.isInitialized) value = factory(register)
        return value
    }

}

internal class SchemaMapReference(private val factory: (register: TypeRegistryScope) -> SchemaMap) : SchemaTypeReference {

    private lateinit var value: SchemaMap

    override fun get(register: TypeRegistryScope): SchemaType {
        if (!this::value.isInitialized) value = factory(register)
        return value
    }

}

internal class SchemaClassReference(
    val name: String,
    private val factory: (register: TypeRegistryScope) -> SchemaClass
) : SchemaTypeReference {

    private lateinit var value: SchemaClass

    override fun get(register: TypeRegistryScope): SchemaType {
        if (!this::value.isInitialized) value = factory(register)
        return value
    }

}

@APIGenDSL
internal interface SchemaClassBuilder<T : APIType> {

    fun array(
        items: SchemaTypeReference,
        nullableItems: Boolean = false
    ): SchemaTypeReference =
        SchemaArrayReference { SchemaArray(items.get(it), nullableItems, null) }

    fun map(
        keys: SchemaPrimitiveReference,
        values: SchemaTypeReference,
        nullableValues: Boolean = false
    ): SchemaTypeReference =
        SchemaMapReference { SchemaMap(keys.get(it), values.get(it), nullableValues, null) }

    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClassReference

    fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClassReference

}

internal interface SchemaConditionalBuilder<T : APIType> : SchemaClassBuilder<T> {

    /**
     * Registers a conditional interpretation using the @receiver as key.
     *
     * The name should be in _TitleCase_.
     *
     * @param type  the type of the interpretation
     */
    operator fun String.invoke(type: SchemaTypeReference, nestProperty: String = this.toLowerCase()): SchemaConditionalInterpretationBuilder

    /** Registers a conditional interpretation using the @receiver's name as key. */
    operator fun SchemaClassReference.unaryPlus(): SchemaConditionalInterpretationBuilder

    /** Marks a deprecated interpretation. */
    val deprecated get() = Modifiers.deprecated

    /** The minimal [V2SchemaVersion] (inclusive) required for the interpretation. */
    fun since(version: V2SchemaVersion): IInterpretationModifier = object : IInterpretationModifier {
        override fun applyTo(interpretation: SchemaConditionalInterpretationBuilder) {
            interpretation.since = version
        }
    }

    /** The maximum [V2SchemaVersion] (exclusive) required for the interpretation. */
    fun until(version: V2SchemaVersion): IInterpretationModifier = object : IInterpretationModifier {
        override fun applyTo(interpretation: SchemaConditionalInterpretationBuilder) {
            interpretation.until = version
        }
    }

}

internal interface SchemaRecordBuilder<T : APIType> : SchemaClassBuilder<T> {

    operator fun String.invoke(
        type: SchemaTypeReference,
        description: String
    ): SchemaRecordPropertyBuilder

    /** Marks a deprecated property. */
    val deprecated get() = Modifiers.deprecated

    /** Marks a localized property. */
    val localized get() = Modifiers.localized

    /** Marks an optional property. */
    val optional get() = Modifiers.optional

    /** Marks an optional property whose presents is mandated by the given `scope`. */
    fun optional(scope: TokenScope): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.optionality = Optionality.MANDATED(scope)
        }
    }

    /** The minimal [V2SchemaVersion] (inclusive) required for the property. */
    fun since(version: V2SchemaVersion): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.since = version
        }
    }

    /** The maximum [V2SchemaVersion] (exclusive) required for the property. */
    fun until(version: V2SchemaVersion): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.until = version
        }
    }

    /** Explicitly specifies the _camelCase_ name for the property. */
    @Suppress("FunctionName")
    fun CamelCase(value: String): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.camelCase = value
        }
    }

    /** Explicitly specifies the serial name for the property. */
    @Suppress("FunctionName")
    fun SerialName(value: String): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.serialName = value
        }
    }

}

internal class SchemaConditionalInterpretationBuilder(
    private val interpretationKey: String,
    private val interpretationNestProperty: String,
    private val type: SchemaTypeReference
) {

    private var isUnused = true

    var isDeprecated = false
        set(value) {
            require(isUnused)
            field = value
        }

    var since: V2SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var until: V2SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    fun build(register: TypeRegistryScope): SchemaConditional.Interpretation {
        isUnused = false

        return SchemaConditional.Interpretation(
            interpretationKey = interpretationKey,
            interpretationNestProperty = interpretationNestProperty,
            type = type.get(register),
            isDeprecated = isDeprecated,
            since = since,
            until = until
        )
    }

}

internal class SchemaRecordPropertyBuilder(
    private val propertyName: String,
    private val type: SchemaTypeReference,
    private val description: String
) {

    init {
        requireTitleCase(propertyName, "propertyName")
    }

    private var isUnused = true

    var isDeprecated = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isDeprecated is `false` by default and should only be set to `true`." }

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
            requireCamelCase(value, "Property::camelCase")

            field = value
        }

    fun build(register: TypeRegistryScope): SchemaRecord.Property {
        isUnused = false

        return SchemaRecord.Property(
            propertyName = propertyName,
            type = type.get(register),
            description = description,
            optionality = optionality ?: Optionality.REQUIRED,
            isDeprecated = isDeprecated,
            isLocalized = isLocalized,
            since = since,
            until = until,
            serialName = serialName ?: propertyName.toLowerCase(),
            camelCaseName = camelCase ?: propertyName.run { "${toCharArray()[0].toLowerCase()}${substring(1)}" }
        )
    }

}