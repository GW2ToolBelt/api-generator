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

import com.gw2tb.apigen.internal.impl.SchemaVersionedData
import com.gw2tb.apigen.internal.impl.buildVersionedSchemaData
import com.gw2tb.apigen.internal.impl.zipSchemaVersionConstraints
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.model.APIType
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.*

@OptIn(LowLevelApiGenApi::class)
internal class SchemaRecordBuilder<T : IRAPIType>(
    override val name: Name,
    private val description: String,
    override val apiTypeFactory: (SchemaVersionedData<out IRTypeDeclaration<*>>, APIType.InterpretationHint?, Boolean) -> T,
    override val typeRegistry: ScopedTypeRegistry<T>?
) : AbstractSchemaRecordBuilder<T>() {

    private lateinit var _value: SchemaVersionedData<IRTypeReference>

    override fun get(
        typeRegistry: ScopedTypeRegistry<*>?,
        interpretationHint: APIType.InterpretationHint?,
        isTopLevel: Boolean
    ): SchemaVersionedData<out IRTypeReference> {
        if (!this::_value.isInitialized) {
            @Suppress("NAME_SHADOWING")
            val typeRegistry = this.typeRegistry

            val properties: SchemaVersionedData<Map<String, IRProperty>>? = buildProperties(typeRegistry?.nestedScope(name))

            val versions = buildVersionedSchemaData<IRRecord> {
                V2SchemaVersion.values()
                    .filter { version -> version == V2SchemaVersion.V2_SCHEMA_CLASSIC || properties?.hasChangedInVersion(version) == true }
                    .zipSchemaVersionConstraints()
                    .forEach { (since, until) ->
                        add(
                            datum = IRRecord(
                                name,
                                description,
                                properties?.get(since)?.data?.values?.toSet() ?: emptySet()
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

}