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
@file:Suppress("FunctionName")
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*

/** Alias for [SchemaBoolean]. */
internal val BOOLEAN: DeferredPrimitiveType = DeferredPrimitiveType(SchemaBoolean())

/** Alias for [SchemaDecimal]. */
internal val DECIMAL: DeferredPrimitiveType = DeferredPrimitiveType(SchemaDecimal())

/** Alias for [SchemaInteger]. */
internal val INTEGER: DeferredPrimitiveType = DeferredPrimitiveType(SchemaInteger())

/** Alias for [SchemaString]. */
internal val STRING: DeferredPrimitiveType = DeferredPrimitiveType(SchemaString())

internal fun <T : SchemaTypeUse> DeferredSchemaType(factory: (TypeRegistryScope?, Boolean) -> SchemaVersionedData<out T>): DeferredSchemaType<T> = object : DeferredSchemaType<T>() {
    override fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean): SchemaVersionedData<out T> = factory(typeRegistry, isTopLevel)
}

internal abstract class DeferredSchemaType<T : SchemaTypeUse> {

    abstract fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean = false): SchemaVersionedData<out T>

    fun getFlat(): T = get(typeRegistry = null, interpretationHint = null).single().data

}

internal data class DeferredPrimitiveType(
    private val value: SchemaPrimitive,
) : DeferredSchemaType<SchemaPrimitive>() {

    override fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean): SchemaVersionedData<out SchemaPrimitive> {
        return wrapVersionedSchemaData(value)
    }

    fun withTypeHint(typeHint: SchemaPrimitive.TypeHint?): DeferredPrimitiveType =
        copy(value = value.withTypeHint(typeHint = typeHint))

}

@APIGenDSL
internal abstract class DeferredSchemaClass<T : APIType> : DeferredSchemaType<SchemaTypeReference>() {

    abstract val name: String

    abstract val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> T

    abstract val typeRegistry: TypeRegistryScope?

    open val nestedTypeRegistry: TypeRegistryScope? get() = typeRegistry?.nestedScope(name)

    fun array(
        items: DeferredSchemaType<out SchemaTypeUse>,
        nullableItems: Boolean = false
    ): DeferredSchemaType<SchemaArray> =
        DeferredSchemaType { typeRegistry, isTopLevel -> items.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { SchemaArray(it, nullableItems, description = null) } }

    fun map(
        keys: DeferredSchemaType<out SchemaPrimitive>,
        values: DeferredSchemaType<out SchemaTypeUse>,
        nullableValues: Boolean = false
    ): DeferredSchemaType<SchemaMap> =
        DeferredSchemaType { typeRegistry, isTopLevel -> values.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { SchemaMap(keys.getFlat(), it, nullableValues, description = null) } }

    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)? = null,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = conditionalImpl(
        name,
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        apiTypeFactory,
        nestedTypeRegistry,
        configure
    )

    fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = recordImpl(
        name,
        description,
        apiTypeFactory,
        nestedTypeRegistry,
        block
    )

}

internal class SchemaConditionalBuilder<T : APIType>(
    override val name: String,
    private val description: String,
    private val disambiguationBy: String,
    private val disambiguationBySideProperty: Boolean,
    private val interpretationInNestedProperty: Boolean,
    private val sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)?,
    override val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> T,
    override val typeRegistry: TypeRegistryScope?
) : DeferredSchemaClass<T>() {

    private val _interpretations = mutableListOf<SchemaConditionalInterpretationBuilder>()

    private fun buildInterpretations(conditionalBase: TypeLocation, typeRegistry: TypeRegistryScope?): SchemaVersionedData<Map<String, SchemaConditional.Interpretation>> = buildVersionedSchemaData {
        V2SchemaVersion.values().forEach { version ->
            val relevantInterpretations = _interpretations.getForVersion(
                SchemaConditionalInterpretationBuilder::since,
                SchemaConditionalInterpretationBuilder::until,
                version
            )

            if (relevantInterpretations.any { it.hasChangedInVersion(typeRegistry, version, conditionalBase) }) {
                add(relevantInterpretations.map {
                    it.get(typeRegistry, version, conditionalBase)
                }.associateBy { it.interpretationKey }, since = version)
            }
        }
    }

    private fun buildProperties(typeRegistry: TypeRegistryScope?): SchemaVersionedData<Map<String, SchemaProperty>>? =
        if (sharedConfigure != null)
            SchemaConditionalSharedPropertyBuilder(apiTypeFactory, typeRegistry).also(sharedConfigure).buildProperties(typeRegistry) // TODO stub scope leaks
        else
            null

    private lateinit var _value: SchemaVersionedData<SchemaTypeReference>

    override fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean): SchemaVersionedData<out SchemaTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val nestedTypeRegistry = typeRegistry?.nestedScope(name)
            val sharedProperties = buildProperties(nestedTypeRegistry)

            val loc = typeRegistry?.getLocationFor(name) ?: error("TypeRegistry is required")
            val interpretations = buildInterpretations(loc, nestedTypeRegistry)

            val versions = buildVersionedSchemaData<SchemaConditional> {
                V2SchemaVersion.values()
                    .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || interpretations.hasChangedInVersion(version) || sharedProperties?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = SchemaConditional(
                                name,
                                disambiguationBy,
                                disambiguationBySideProperty,
                                interpretationInNestedProperty,
                                sharedProperties?.get(since)?.data ?: emptyMap(),
                                interpretations[since].data,
                                description
                            ),
                            since = since,
                            until = until
                        )
                    }
            }

            val apiType = apiTypeFactory(versions, interpretationHint, isTopLevel)
            typeRegistry.register(name, apiType)

            _value = versions.mapVersionedData { version, data -> SchemaTypeReference(loc, version, data) }
        }

        return _value
    }

    /**
     * Registers a conditional interpretation using the @receiver as key.
     *
     * The name should be in _TitleCase_.
     *
     * @param type  the type of the interpretation
     */
    operator fun String.invoke(
        type: DeferredSchemaClass<T>,
        nestProperty: String = this.toLowerCase()
    ): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(this, nestProperty, type).also { _interpretations += it }

    /** Registers a conditional interpretation using the @receiver's name as key. */
    operator fun DeferredSchemaClass<T>.unaryPlus(): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(name, name.toLowerCase(), this).also { this@SchemaConditionalBuilder._interpretations += it }

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

internal abstract class AbstractSchemaRecordBuilder<T : APIType> : DeferredSchemaClass<T>() {

    private val _properties = mutableListOf<SchemaRecordPropertyBuilder>()

    fun buildProperties(typeRegistry: TypeRegistryScope?): SchemaVersionedData<Map<String, SchemaProperty>>? =
        if (_properties.isEmpty()) {
            null
        } else {
            buildVersionedSchemaData {
                V2SchemaVersion.values().forEach { version ->
                    if (_properties.any { it.hasChangedInVersion(typeRegistry, version) }) {
                        val relevantProperties = _properties.getForVersion(
                            SchemaRecordPropertyBuilder::since,
                            SchemaRecordPropertyBuilder::until,
                            version
                        )

                        add(relevantProperties.map { it.get(typeRegistry, version) }.associateBy { it.serialName }, since = version)
                    }
                }
            }
        }

    operator fun String.invoke(type: DeferredSchemaType<out SchemaTypeUse>, description: String): SchemaRecordPropertyBuilder =
        SchemaRecordPropertyBuilder(this, type, description).also { _properties += it }

    /** Marks a deprecated property. */
    val deprecated get() = Modifiers.deprecated

    val lenient get() = Modifiers.lenient

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

internal class SchemaRecordBuilder<T : APIType>(
    override val name: String,
    private val description: String,
    override val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> T,
    override val typeRegistry: TypeRegistryScope?
) : AbstractSchemaRecordBuilder<T>() {

    private lateinit var _value: SchemaVersionedData<SchemaTypeReference>

    override fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean): SchemaVersionedData<SchemaTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val properties: SchemaVersionedData<Map<String, SchemaProperty>>? = buildProperties(typeRegistry?.nestedScope(name))

            val versions = buildVersionedSchemaData<SchemaRecord> {
                V2SchemaVersion.values()
                    .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || properties?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = SchemaRecord(
                                name,
                                properties?.get(since)?.data ?: emptyMap(),
                                description
                            ),
                            since = since,
                            until = until
                        )
                    }
            }

            val apiType = apiTypeFactory(versions, interpretationHint, isTopLevel)
            val loc = typeRegistry?.register(name, apiType) ?: error("TypeRegistry is required")

            _value = versions.mapVersionedData { version, data -> SchemaTypeReference(loc, version, data) }
        }

        return _value
    }

}

internal class SchemaConditionalInterpretationBuilder(
    private val interpretationKey: String,
    private val interpretationNestProperty: String,
    private val type: DeferredSchemaType<out SchemaTypeReference>
) {

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

    private lateinit var _value: SchemaVersionedData<SchemaConditional.Interpretation>
    private val isUnused get() = !this::_value.isInitialized

    fun get(typeRegistry: TypeRegistryScope?, version: V2SchemaVersion?, conditionalBase: TypeLocation): SchemaConditional.Interpretation {
        if (!this::_value.isInitialized) {
            val interpretationHint = InterpretationHint(
                conditionalBase = conditionalBase,
                interpretationKey = interpretationKey,
                interpretationNestProperty = interpretationNestProperty
            )

            _value = type.get(typeRegistry, interpretationHint = interpretationHint).mapData {
                SchemaConditional.Interpretation(
                    interpretationKey = interpretationKey,
                    interpretationNestProperty = interpretationNestProperty,
                    type = it,
                    isDeprecated = isDeprecated,
                    since = since,
                    until = until
                )
            }
        }

        return _value[version ?: V2SchemaVersion.V2_SCHEMA_CLASSIC].data
    }

    fun hasChangedInVersion(typeRegistry: TypeRegistryScope?, version: V2SchemaVersion, conditionalBase: TypeLocation): Boolean {
        get(typeRegistry, version, conditionalBase)
        return version.containsChangeForBounds(since, until) || _value.hasChangedInVersion(version)
    }

}

internal class SchemaConditionalSharedPropertyBuilder<T : APIType>(
    override val apiTypeFactory: (SchemaVersionedData<out SchemaTypeDeclaration>, InterpretationHint?, Boolean) -> T,
    override val typeRegistry: TypeRegistryScope?
) : AbstractSchemaRecordBuilder<T>() {

    override val nestedTypeRegistry: TypeRegistryScope?
        get() = typeRegistry

    override val name: String
        get() = error("Not implemented")

    override fun get(typeRegistry: TypeRegistryScope?, interpretationHint: InterpretationHint?, isTopLevel: Boolean): SchemaVersionedData<out SchemaTypeReference> {
        error("Not implemented")
    }

}

internal class SchemaRecordPropertyBuilder(
    private val propertyName: String,
    val type: DeferredSchemaType<out SchemaTypeUse>,
    private val description: String
) {

    init {
        requireTitleCase(propertyName, "propertyName")
    }

    var isDeprecated = false
        set(value) {
            check(isUnused)
            require(value) { "Property::isDeprecated is `false` by default and should only be set to `true`." }

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

    private lateinit var _value: SchemaVersionedData<SchemaProperty>
    private val isUnused get() = !this::_value.isInitialized

    fun get(typeRegistry: TypeRegistryScope?, version: V2SchemaVersion?): SchemaProperty {
        if (!this::_value.isInitialized) {
            _value = type.get(typeRegistry, interpretationHint = null).mapData {
                SchemaProperty(
                    propertyName = propertyName,
                    type = it,
                    description = description,
                    optionality = optionality ?: Optionality.REQUIRED,
                    isDeprecated = isDeprecated,
                    isLenient = isLenient,
                    isLocalized = isLocalized,
                    since = since,
                    until = until,
                    serialName = serialName ?: propertyName.toLowerCase(),
                    camelCaseName = camelCase ?: propertyName.run { "${toCharArray()[0].toLowerCase()}${substring(1)}" }
                )
            }
        }

        return _value[version ?: V2SchemaVersion.V2_SCHEMA_CLASSIC].data
    }

    fun hasChangedInVersion(typeRegistry: TypeRegistryScope?, version: V2SchemaVersion): Boolean {
        get(typeRegistry, version)
        return version.containsChangeForBounds(since, until) || _value.hasChangedInVersion(version)
    }

}