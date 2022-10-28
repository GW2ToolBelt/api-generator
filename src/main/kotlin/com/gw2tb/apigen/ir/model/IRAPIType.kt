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
package com.gw2tb.apigen.ir.model

import com.gw2tb.apigen.internal.impl.SchemaVersionedData
import com.gw2tb.apigen.ir.IRTypeDeclaration
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.model.APIType
import com.gw2tb.apigen.model.APIType.InterpretationHint
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.model.v2.VersionedData
import com.gw2tb.apigen.schema.Name

/**
 * A low-level representation of a [APIType].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public sealed class IRAPIType {

    public abstract val name: Name

    public abstract val interpretationHint: InterpretationHint?

    internal abstract val isTopLevel: Boolean

    internal abstract fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIType

    public data class V1 internal constructor(
        val declaration: IRTypeDeclaration<*>,
        override val interpretationHint: InterpretationHint?,
        override val isTopLevel: Boolean
    ) : IRAPIType() {

        override val name: Name get() = declaration.name

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIType {
            val schema = declaration.resolve(resolverContext, v2SchemaVersion)

            return APIType(
                name = name,
                schema = schema,
                interpretationHint = interpretationHint,
                isTopLevel = isTopLevel,
                testSet = 1
            )
        }

    }

    public data class V2 internal constructor(
        private val _declaration: SchemaVersionedData<out IRTypeDeclaration<*>>,
        override val interpretationHint: InterpretationHint?,
        override val isTopLevel: Boolean
    ) : IRAPIType(), VersionedData<IRTypeDeclaration<*>> by _declaration {

        override val name: Name get() = _declaration.flatMapData(IRTypeDeclaration<*>::name)

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): APIType {
            val schema = _declaration[v2SchemaVersion!!].data.resolve(resolverContext, v2SchemaVersion)

            return APIType(
                name = name,
                schema = schema,
                interpretationHint = interpretationHint,
                isTopLevel = isTopLevel,
                testSet = 2
            )
        }

    }

}