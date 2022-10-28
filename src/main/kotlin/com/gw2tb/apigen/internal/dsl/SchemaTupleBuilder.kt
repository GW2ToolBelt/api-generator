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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.internal.impl.SchemaVersionedData
import com.gw2tb.apigen.internal.impl.buildVersionedSchemaData
import com.gw2tb.apigen.internal.impl.wrapVersionedSchemaData
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.model.APIType
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.Name

@OptIn(LowLevelApiGenApi::class)
internal class SchemaTupleBuilder<T : IRAPIType>(
    override val name: Name,
    private val description: String,
    override val apiTypeFactory: (SchemaVersionedData<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> T,
    override val typeRegistry: ScopedTypeRegistry<T>?
) : DeferredSchemaClass<T>() {

    private val _elements = mutableListOf<SchemaTupleElementBuilder>()

    private fun buildElements(typeRegistry: ScopedTypeRegistry<T>?): SchemaVersionedData<Set<IRTuple.Element>>? =
        if (_elements.isEmpty()) {
            null
        } else {
            buildVersionedSchemaData {
                V2SchemaVersion.values().forEach { version ->
                    if (_elements.any { it.hasChangedInVersion(typeRegistry, version) }) {
                        add(_elements.map { it.get(typeRegistry, version) }.toSet(), since = version)
                    }
                }
            }
        }

    private lateinit var _value: SchemaVersionedData<IRTypeReference>

    override fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): SchemaVersionedData<out IRTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val elements: SchemaVersionedData<Set<IRTuple.Element>>? = buildElements(typeRegistry?.nestedScope(name))

            val versions = buildVersionedSchemaData<IRTuple> {
                V2SchemaVersion.values()
                    .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || elements?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = IRTuple(
                                name = name,
                                elements = elements?.get(since)?.data ?: emptySet(),
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

    operator fun String.invoke(type: DeferredSchemaType<out IRTypeUse<*>>, description: String): SchemaTupleElementBuilder =
        SchemaTupleElementBuilder(this, type, description).also { _elements += it }

}