/*
 * Copyright (c) 2019-2025 Leon Linhart
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
package com.gw2tb.apigen.ir.model

import com.gw2tb.apigen.ir.IRTypeDeclaration
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.schema.model.APIType
import com.gw2tb.apigen.schema.model.APIType.InterpretationHint
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.model.v2.SchemaVersionedData
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.schema.SchemaConditional

/**
 * A low-level representation of an [APIType].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public sealed class IRAPIType {

    /**
     * The type's name
     *
     * @since   0.7.0
     */
    public abstract val name: Name

    /**
     * Additional information about the type if it represents an [interpretation][SchemaConditional.Interpretation].
     *
     * @since   0.7.0
     */
    public abstract val interpretationHint: InterpretationHint?

    internal abstract val isTopLevel: Boolean

    internal abstract fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIType

    /**
     * A low-level representation of an [APIType] for version 1 (`v1`) of the
     * Guild Wars 2 API.
     *
     * @param declaration   the schema definition of the type
     *
     * @since   0.7.0
     */
    public data class V1 internal constructor(
        val declaration: IRTypeDeclaration<*>,
        override val interpretationHint: InterpretationHint?,
        override val isTopLevel: Boolean
    ) : IRAPIType() {

        override val name: Name get() = declaration.name

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIType {
            val schema = declaration.resolve(resolverContext, v2SchemaVersion)

            return APIType(
                name = name,
                schema = schema,
                schemaVersion = v2SchemaVersion,
                interpretationHint = interpretationHint,
                isTopLevel = isTopLevel,
                testSet = 1
            )
        }

    }

    /**
     * A low-level representation of an [APIType] for version 2 (`v2`) of the
     * Guild Wars 2 API.
     *
     * @since   0.7.0
     */
    public data class V2 internal constructor(
        private val _declaration: SchemaVersionedData<IRTypeDeclaration<*>>,
        override val interpretationHint: InterpretationHint?,
        override val isTopLevel: Boolean
    ) : IRAPIType(), SchemaVersionedData<IRTypeDeclaration<*>> by _declaration {

        override val name: Name get() = _declaration.flatten(IRTypeDeclaration<*>::name)

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): APIType {
            val schema = _declaration.getOrThrow(v2SchemaVersion!!).data.resolve(resolverContext, v2SchemaVersion)

            val effectiveVersion = _declaration.significantVersions
                .sorted()
                .findLast { it <= v2SchemaVersion }

            return APIType(
                name = name,
                schema = schema,
                schemaVersion = effectiveVersion,
                interpretationHint = interpretationHint,
                isTopLevel = isTopLevel,
                testSet = 2
            )
        }

    }

}