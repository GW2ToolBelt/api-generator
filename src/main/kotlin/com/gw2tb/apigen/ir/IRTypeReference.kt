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
package com.gw2tb.apigen.ir

import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.SchemaTypeReference

@LowLevelApiGenApi
public sealed class IRTypeReference : IRTypeUse<SchemaTypeReference>() {

    public abstract val name: QualifiedTypeName

    public data class Alias internal constructor(
        override val name: QualifiedTypeName.Alias,
        internal val alias: IRAlias
    ) : IRTypeReference() {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): SchemaTypeReference {
            val alias = alias.resolve(resolverContext, v2SchemaVersion)
            resolverContext.aliasCollector.collect(name, alias)

            return SchemaTypeReference.Alias(name, alias)
        }

    }

    public data class Declaration internal constructor(
        override val name: QualifiedTypeName.Declaration,
        internal val declaration: IRTypeDeclaration<*>
    ) : IRTypeReference() {

        override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): SchemaTypeReference {
            val declaration = declaration.resolve(resolverContext, v2SchemaVersion)
            resolverContext.referenceCollector.collect(name, declaration)

            return SchemaTypeReference.Declaration(name, declaration)
        }

    }

}