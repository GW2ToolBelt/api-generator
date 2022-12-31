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

import com.gw2tb.apigen.internal.impl.SchemaVersionedDataImpl
import com.gw2tb.apigen.internal.impl.buildVersionedSchemaData
import com.gw2tb.apigen.internal.impl.getForVersion
import com.gw2tb.apigen.internal.impl.zipSchemaVersionConstraints
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.schema.model.APIType
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.model.Name

internal class SchemaEnumBuilder<T : IRAPIType>(
    private val type: DeferredPrimitiveType,
    override val name: Name,
    private val description: String,
    override val apiTypeFactory: (SchemaVersionedDataImpl<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> T,
    override val typeRegistry: ScopedTypeRegistry<T>?
): DeferredSchemaClass<T>() {

    private val _enumValues = mutableListOf<SchemaEnumValueBuilder>()

    private fun buildEnumValues(): SchemaVersionedDataImpl<Set<IREnum.Value>>? =
        if (_enumValues.isEmpty()) {
            null
        } else {
            buildVersionedSchemaData {
                SchemaVersion.values().forEach { version ->
                    if (_enumValues.any { it.hasChangedInVersion(version) }) {
                        val relevantProperties = _enumValues.getForVersion(
                            SchemaEnumValueBuilder::since,
                            SchemaEnumValueBuilder::until,
                            version
                        )

                        add(relevantProperties.map { it.get() }.toSet(), since = version)
                    }
                }
            }
        }

    private lateinit var _value: SchemaVersionedDataImpl<IRTypeReference>

    override fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): SchemaVersionedDataImpl<IRTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val type = type.get(typeRegistry, interpretationHint = null)
            val enumValues = buildEnumValues()

            val versions = buildVersionedSchemaData<IREnum> {
                SchemaVersion.values()
                    .filter { version -> version == SchemaVersion.V2_SCHEMA_CLASSIC || enumValues?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = IREnum(
                                name = name,
                                type = type.getOrThrow(since).data,
                                values = enumValues?.getOrThrow(since)?.data ?: emptySet(),
                                description = description
                            ),
                            since = since,
                            until = until
                        )
                    }
            }

            val apiType = apiTypeFactory(versions, interpretationHint, isTopLevel)
            val loc = typeRegistry?.registerDeclaration(name, apiType) ?: error("TypeRegistry is required")

            _value = versions.mapVersionedData { _, data ->
                IRTypeReference.Declaration(
                    name = loc as QualifiedTypeName.Declaration,
                    declaration = data
                )
            }
        }

        return _value
    }

    operator fun String.invoke(description: String): SchemaEnumValueBuilder =
        this(this, description)

    operator fun String.invoke(name: String, description: String): SchemaEnumValueBuilder =
        SchemaEnumValueBuilder(name, this, description).also { _enumValues += it }

}