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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.*

@OptIn(LowLevelApiGenApi::class)
internal class SchemaConditionalBuilder<T : IRAPIType>(
    override val name: Name,
    private val description: String,
    private val disambiguationBy: String,
    private val disambiguationBySideProperty: Boolean,
    private val interpretationInNestedProperty: Boolean,
    private val sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)?,
    override val apiTypeFactory: (SchemaVersionedData<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> T,
    override val typeRegistry: ScopedTypeRegistry<T>?
) : DeferredSchemaClass<T>() {

    private val _interpretations = mutableListOf<SchemaConditionalInterpretationBuilder>()

    private fun buildInterpretations(
        conditionalBase: QualifiedTypeName,
        typeRegistry: ScopedTypeRegistry<T>?
    ): SchemaVersionedData<Map<String, IRConditional.Interpretation>> = buildVersionedSchemaData {
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

    private fun buildProperties(typeRegistry: ScopedTypeRegistry<T>?): SchemaVersionedData<Map<String, IRProperty>>? =
        if (sharedConfigure != null)
            SchemaConditionalSharedPropertyBuilder(apiTypeFactory, typeRegistry).also(sharedConfigure).buildProperties(typeRegistry)
        else
            null

    private lateinit var _value: SchemaVersionedData<IRTypeReference>

    override fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): SchemaVersionedData<out IRTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val nestedTypeRegistry = typeRegistry?.nestedScope(name)
            val sharedProperties = buildProperties(nestedTypeRegistry)

            val loc = typeRegistry?.getQualifiedDeclarationName(name) ?: error("TypeRegistry is required")
            val interpretations = buildInterpretations(loc, nestedTypeRegistry)

            val versions = buildVersionedSchemaData<IRConditional> {
                V2SchemaVersion.values()
                    .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || interpretations.hasChangedInVersion(version) || sharedProperties?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = IRConditional(
                                name,
                                description,
                                disambiguationBy,
                                disambiguationBySideProperty,
                                interpretationInNestedProperty,
                                sharedProperties?.get(since)?.data?.values?.toSet() ?: emptySet(),
                                interpretations[since].data.values.toSet(),
                            ),
                            since = since,
                            until = until
                        )
                    }
            }

            val apiType = apiTypeFactory(versions, interpretationHint, isTopLevel)
            typeRegistry.registerDeclaration(name, apiType)

            _value = versions.mapVersionedData { _, data ->
                IRTypeReference.Declaration(
                    name = loc,
                    declaration = data
                )
            }
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
        nestProperty: String = this.lowercase()
    ): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(this, nestProperty, type).also { _interpretations += it }

    /** Registers a conditional interpretation using the @receiver's name as key. */
    operator fun DeferredSchemaClass<*>.unaryPlus(): SchemaConditionalInterpretationBuilder =
        SchemaConditionalInterpretationBuilder(name.toTitleCase(), name.toSnakeCase(), this).also { this@SchemaConditionalBuilder._interpretations += it }

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