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
package com.gw2tb.apigen.internal.impl

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.internal.dsl.SchemaConditionalBuilder
import com.gw2tb.apigen.internal.dsl.SchemaRecordBuilder
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.schema.SchemaBlueprint

internal abstract class SchemaClassBuilderBaseImpl<T : APIType>(
    private val apiTypeFactory: (SchemaClass) -> T
) : SchemaClassBuilder<T> {

    override fun conditional(
        name: String,
        description: String,
        disambiguationBy: String,
        disambiguationBySideProperty: Boolean,
        interpretationInNestedProperty: Boolean,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
        configure: SchemaConditionalBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = conditionalImpl(name, description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, types, apiTypeFactory, configure)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

        return type
    }

    override fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClass {
        val types = mutableMapOf<String?, MutableList<T>>()
        val type = recordImpl(name, description, types, apiTypeFactory, block)
        types.forEach { (k, v) -> nestedTypes.computeIfAbsent(k) { mutableListOf() }.addAll(v) }

        return type
    }

}

internal class SchemaConditionalBuilderImpl<T : APIType>(
    apiTypeFactory: (SchemaClass) -> T
) : SchemaClassBuilderBaseImpl<T>(apiTypeFactory), SchemaConditionalBuilder<T> {

    override val nestedTypes: MutableMap<String?, MutableList<T>> = mutableMapOf()

    private val _interpretations = mutableListOf<SchemaConditionalInterpretationBuilder>()
    val interpretations get() = _interpretations.map { it.interpretation }

    override fun String.invoke(type: SchemaType, nestProperty: String): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(this, nestProperty, type).also { _interpretations += it }

    override fun SchemaClass.unaryPlus(): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(name, name.toLowerCase(), this).also { _interpretations += it }

}

internal class SchemaRecordBuilderImpl<T : APIType>(
    apiTypeFactory: (SchemaClass) -> T
) : SchemaClassBuilderBaseImpl<T>(apiTypeFactory), SchemaRecordBuilder<T> {

    override val nestedTypes: MutableMap<String?, MutableList<T>> = mutableMapOf()

    private val _properties = mutableListOf<SchemaRecordPropertyBuilder>()
    val properties get() = _properties.map { it.property }

    override fun String.invoke(type: SchemaType, description: String): SchemaRecordPropertyBuilder =
        SchemaRecordPropertyBuilder(this, type, description).also { _properties += it }

}

internal fun <T : APIType> conditionalImpl(
    name: String,
    description: String,
    disambiguationBy: String,
    disambiguationBySideProperty: Boolean,
    interpretationInNestedProperty: Boolean,
    sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
    types: MutableMap<String?, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaConditionalBuilder<T>.() -> Unit
): SchemaClass {
    val bSharedProps = sharedConfigure?.let { SchemaRecordBuilderImpl(apiTypeFactory).also(it) }
    val bInterpretations = SchemaConditionalBuilderImpl(apiTypeFactory).also(configure)

    val sharedProps = bSharedProps?.properties ?: emptyList()
    val interpretations = bInterpretations.interpretations

    bSharedProps?.nestedTypes?.forEach { (k, v) -> types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }.addAll(v) }
    bInterpretations.nestedTypes.forEach { (k, v) ->
        types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }
            .addAll(v.filter { apiType ->
                interpretations.none {
                    it.type is SchemaClass && (apiType.name == it.type.name)
                }
            }
            )
    }

    val versions = buildVersionedSchemaData<SchemaType> {
        V2SchemaVersion.values()
            .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || sharedProps.any { it.hasChangedInVersion(version) } || interpretations.any { it.hasChangedInVersion(version) } }
            .zipSchemaVersionConstraints()
            .forEach { (since, until) ->
                add(
                    datum = SchemaConditional(
                        name,
                        disambiguationBy,
                        disambiguationBySideProperty,
                        interpretationInNestedProperty,
                        sharedProps.getForVersion(
                            SchemaRecord.Property::since,
                            SchemaRecord.Property::until,
                            SchemaRecord.Property::serialName,
                            since
                        ).mapValues { (_, property) -> property.copyForVersion(since) },
                        interpretations.getForVersion(
                            SchemaConditional.Interpretation::since,
                            SchemaConditional.Interpretation::until,
                            SchemaConditional.Interpretation::interpretationKey,
                            since
                        ).mapValues { (_, intrp) -> intrp.copyForVersion(since) },
                        description
                    ),
                    since = since,
                    until = until
                )
            }
    }

    return (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaConditional
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent(null) { mutableListOf() }.add(apiTypeFactory(it))
    }
}

internal fun <T : APIType> recordImpl(
    name: String,
    description: String,
    types: MutableMap<String?, MutableList<T>>,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaRecordBuilder<T>.() -> Unit
): SchemaClass {
    val bProperties = SchemaRecordBuilderImpl(apiTypeFactory).also(configure)
    bProperties.nestedTypes.forEach { (k, v) -> types.computeIfAbsent("$name${if (k != null && k.isNotEmpty()) "/$k" else ""}") { mutableListOf() }.addAll(v) }

    val properties = bProperties.properties
    val versions = buildVersionedSchemaData<SchemaType> {
        V2SchemaVersion.values()
            .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || properties.any { it.hasChangedInVersion(version) } }
            .zipSchemaVersionConstraints()
            .forEach { (since, until) ->
                add(
                    datum = SchemaRecord(
                        name,
                        properties.getForVersion(
                            SchemaRecord.Property::since,
                            SchemaRecord.Property::until,
                            SchemaRecord.Property::serialName,
                            since
                        ).mapValues { (_, property) -> property.copyForVersion(since) },
                        description
                    ),
                    since = since,
                    until = until
                )
            }
    }

    return (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaRecord
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        types.computeIfAbsent(null) { mutableListOf() }.add(apiTypeFactory(it))
    }
}
