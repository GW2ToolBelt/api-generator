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

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*

/** Alias for [SchemaBoolean]. */
internal val BOOLEAN get() = SchemaBoolean

/** Alias for [SchemaDecimal]. */
internal val DECIMAL get() = SchemaDecimal

/** Alias for [SchemaInteger]. */
internal val INTEGER get() = SchemaInteger

/** Alias for [SchemaString]. */
internal val STRING get() = SchemaString

/** Alias for [QueryType.ByID]. */
internal val BY_ID get() = QueryType.ByID

/** Alias for [QueryType.ByID]. */
internal fun BY_ID(
    qpName: String,
    qpDescription: String,
    qpKey: String = qpName.toLowerCase(Locale.ENGLISH),
    qpCamelCase: String = qpName.firstToLowerCase()
) = QueryType.ByID(
    qpKey = qpKey,
    qpDescription = qpDescription,
    qpName = qpName,
    qpCamelCase = qpCamelCase
)

/** Alias for [QueryType.ByPage]. */
internal val BY_PAGE get() = QueryType.ByPage

/** Alias for [QueryType.ByIDs]. */
internal val BY_IDS = QueryType.ByIDs(supportsAll = true)

/** Alias for [QueryType.ByIDs]. */
internal fun BY_IDS(all: Boolean = true) = QueryType.ByIDs(supportsAll = all)

/** Alias for [QueryType.ByIDs]. */
internal fun BY_IDS(
    qpName: String,
    qpDescription: String,
    all: Boolean = true,
    qpKey: String = qpName.toLowerCase(Locale.ENGLISH),
    qpCamelCase: String = qpName.firstToLowerCase()
) = QueryType.ByIDs(
    supportsAll = all,
    qpKey = qpKey,
    qpDescription = qpDescription,
    qpName = qpName,
    qpCamelCase = qpCamelCase
)

internal fun SchemaConditional.Interpretation.hasChangedInVersion(version: V2SchemaVersion): Boolean =
    since === version || (until !== null && until.ordinal == version.ordinal) || type.hasChangedInVersion(version)

internal fun SchemaRecord.Property.hasChangedInVersion(version: V2SchemaVersion): Boolean =
    since === version || (until !== null && until.ordinal == version.ordinal) || type.hasChangedInVersion(version)

internal fun SchemaType.hasChangedInVersion(version: V2SchemaVersion): Boolean = when (this) {
    is SchemaBlueprint -> versions.hasChangedInVersion(version)
    is SchemaArray -> items.hasChangedInVersion(version)
    is SchemaConditional -> sharedProperties.any { (_, property) -> property.hasChangedInVersion(version) }
        || interpretations.any { (_, property) -> property.hasChangedInVersion(version) }
    is SchemaMap -> values.hasChangedInVersion(version)
    is SchemaRecord -> properties.any { (_, property) -> property.hasChangedInVersion(version) }
    is SchemaPrimitive -> false
}

internal fun SchemaType.copyForVersion(version: V2SchemaVersion): SchemaType = when (this) {
    is SchemaBlueprint -> versions[version].data
    is SchemaArray -> copy(items = items.copyForVersion(version))
    is SchemaConditional -> copy(
        sharedProperties = sharedProperties.filterValues { property ->
            (property.since === null || property.since <= version) && (property.until === null || version < property.until)
        },
        interpretations = interpretations.filterValues { interpretation ->
            (interpretation.since === null || interpretation.since <= version) && (interpretation.until === null || version < interpretation.until)
        }
    )
    is SchemaMap -> copy(values = values.copyForVersion(version))
    is SchemaRecord -> copy(properties = properties.filterValues { property ->
        (property.since === null || property.since <= version) && (property.until === null || version < property.until)
    })
    is SchemaPrimitive -> this
}

internal fun <T> List<T>.getForVersion(sinceSelector: (T) -> V2SchemaVersion?, untilSelector: (T) -> V2SchemaVersion?, keySelector: (T) -> String, version: V2SchemaVersion): Map<String, T> =
    filter {
        val since = sinceSelector(it)
        if (since !== null && version < since) return@filter false

        val until = untilSelector(it)
        until === null || until.ordinal <= version.ordinal
    }.associateBy { keySelector(it) }

internal fun <T> Iterable<T>.zipSchemaVersionConstraints(includeUnbound: Boolean = true): Iterable<Pair<T, T?>> = sequence {
    val itr = this@zipSchemaVersionConstraints.iterator()
    var first = itr.next()

    if (!includeUnbound && !itr.hasNext())
        error("Cannot zip single version into bound constraint")

    while (itr.hasNext()) {
        val second = itr.next()
        yield(first to second)

        first = second
    }

    if (includeUnbound)
        yield(first to null)
}.asIterable()

internal class SchemaConditionalBuilder<T : APIType> : SchemaBuilder<T> {

    override val nestedTypes: MutableMap<String?, MutableList<T>> = mutableMapOf()

    private val _interpretations = mutableListOf<SchemaConditionalInterpretationBuilder>()
    val interpretations get() = _interpretations.map { it.interpretation }

    /**
     * Registers a conditional interpretation using the @receiver as key.
     *
     * The name should be in _TitleCase_.
     *
     * @param type  the type of the interpretation
     */
    @APIGenDSL
    operator fun String.invoke(type: SchemaType, nestProperty: String = this.toLowerCase()): SchemaConditionalInterpretationBuilder {
        return SchemaConditionalInterpretationBuilder(this, nestProperty, type).also { _interpretations += it }
    }

    /** Registers a conditional interpretation using the @receiver's name as key. */
    @APIGenDSL
    operator fun SchemaClass.unaryPlus(): SchemaConditionalInterpretationBuilder {
        return SchemaConditionalInterpretationBuilder(name, name.toLowerCase(), this).also { _interpretations += it }
    }

    /** Marks a deprecated interpretation. */
    val deprecated = Modifiers.deprecated

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

internal class SchemaConditionalInterpretationBuilder(
    private val interpretationKey: String,
    private val interpretationNestProperty: String,
    private val type: SchemaType
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


    val interpretation by lazy {
        isUnused = false

        SchemaConditional.Interpretation(
            interpretationKey = interpretationKey,
            interpretationNestProperty = interpretationNestProperty,
            type = type,
            isDeprecated = isDeprecated,
            since = since,
            until = until
        )
    }

}

internal class SchemaRecordBuilder<T : APIType>(val name: String) : SchemaBuilder<T> {

    override val nestedTypes: MutableMap<String?, MutableList<T>> = mutableMapOf()

    private val _properties = mutableListOf<SchemaRecordPropertyBuilder>()
    val properties get() = _properties.map { it.property }

    /**
     * Registers a new record property with the @receiver as name.
     *
     * The name should be in _TitleCase_. By default, the _camelCase_ variant is generated by converting the first
     * character of the name to lower-case. Similarly, the _serial_name_ variant is generated by converting the entire
     * name to lower-case. For properties that require a custom behavior, the [CamelCase] and [SerialName] modifiers
     * should be used.
     *
     * @param type          the type of the record member
     * @param description   the description of the parameter. (Should be worded to complete the sentence "This field
     *                      holds {description}.")
     */
    @APIGenDSL
    operator fun String.invoke(
        type: SchemaType,
        description: String
    ): SchemaRecordPropertyBuilder {
        return SchemaRecordPropertyBuilder(this, type, description).also { _properties += it }
    }

    /** Marks a deprecated property. */
    val deprecated = Modifiers.deprecated

    /** Marks a localized property. */
    val localized = Modifiers.localized

    /** Marks an optional property. */
    val optional = Modifiers.optional

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

internal class SchemaRecordPropertyBuilder(
    private val propertyName: String,
    private val type: SchemaType,
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

    val property by lazy {
        isUnused = false

        SchemaRecord.Property(
            propertyName = propertyName,
            type = type,
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