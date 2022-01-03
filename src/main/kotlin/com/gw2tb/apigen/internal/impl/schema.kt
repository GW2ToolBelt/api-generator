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
    ): SchemaClassReference = conditionalImpl(
        name,
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        apiTypeFactory,
        configure
    )

    override fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): SchemaClassReference = recordImpl(
        name,
        description,
        apiTypeFactory,
        block
    )

}

internal class SchemaConditionalBuilderImpl<T : APIType>(
    apiTypeFactory: (SchemaClass) -> T
) : SchemaClassBuilderBaseImpl<T>(apiTypeFactory), SchemaConditionalBuilder<T> {

    private val _interpretations = mutableListOf<SchemaConditionalInterpretationBuilder>()

    override fun String.invoke(type: SchemaTypeReference, nestProperty: String): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(this, nestProperty, type).also { _interpretations += it }

    override fun SchemaClassReference.unaryPlus(): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(name, name.toLowerCase(), this).also { _interpretations += it }

    fun buildInterpretations(register: TypeRegistryScope): List<SchemaConditional.Interpretation> =
        _interpretations.map { it.build(register) }

}

internal class SchemaRecordBuilderImpl<T : APIType>(
    apiTypeFactory: (SchemaClass) -> T
) : SchemaClassBuilderBaseImpl<T>(apiTypeFactory), SchemaRecordBuilder<T> {

    private val _properties = mutableListOf<SchemaRecordPropertyBuilder>()

    override fun String.invoke(type: SchemaTypeReference, description: String): SchemaRecordPropertyBuilder =
        SchemaRecordPropertyBuilder(this, type, description).also { _properties += it }

    fun buildProperties(register: TypeRegistryScope): List<SchemaRecord.Property> =
        _properties.map { it.build(register) }

}

internal fun <T : APIType> conditionalImpl(
    name: String,
    description: String,
    disambiguationBy: String,
    disambiguationBySideProperty: Boolean,
    interpretationInNestedProperty: Boolean,
    sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)?,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaConditionalBuilder<T>.() -> Unit
): SchemaClassReference = SchemaClassReference(name) { typeRegister ->
    val bSharedProps = sharedConfigure?.let { SchemaRecordBuilderImpl(apiTypeFactory).also(it) }
    val bInterpretations = SchemaConditionalBuilderImpl(apiTypeFactory).also(configure)

    val sharedProps = bSharedProps?.buildProperties(typeRegister.nestedScope(name)) ?: emptyList()
    val interpretations = bInterpretations.buildInterpretations(typeRegister.nestedScope(name))

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

    (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaConditional
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        typeRegister.register(name, apiTypeFactory(it))
    }
}

internal fun <T : APIType> recordImpl(
    name: String,
    description: String,
    apiTypeFactory: (SchemaClass) -> T,
    configure: SchemaRecordBuilder<T>.() -> Unit
): SchemaClassReference = SchemaClassReference(name) { typeRegister ->
    val bProperties = SchemaRecordBuilderImpl(apiTypeFactory).also(configure)
    val properties = bProperties.buildProperties(typeRegister.nestedScope(name))

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

    (if (versions.isConsistent) {
        versions[V2SchemaVersion.V2_SCHEMA_CLASSIC].data as SchemaRecord
    } else {
        SchemaBlueprint(name, versions)
    }).also {
        typeRegister.register(name, apiTypeFactory(it))
    }
}