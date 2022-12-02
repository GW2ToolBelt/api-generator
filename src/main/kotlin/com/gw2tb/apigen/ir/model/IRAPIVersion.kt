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

import com.gw2tb.apigen.ir.AliasCollector
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.ReferenceCollector
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.SchemaTypeDeclaration

/**
 * A low-level representation of a [APIVersion].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRAPIVersion<Q : IRAPIQuery, T : IRAPIType>(
    val languages: Set<Language>,
    val queries: Set<Q>,
    val types: Map<QualifiedTypeName.Declaration, T>
) {

    internal fun resolve(aliasCollector: AliasCollector, v2SchemaVersion: V2SchemaVersion? = null): APIVersion {
        val referencedTypes = mutableMapOf<QualifiedTypeName.Declaration, SchemaTypeDeclaration>()

        val referenceCollector = ReferenceCollector { name, schema ->
            referencedTypes[name] = schema
        }

        val resolverContext = object : ResolverContext {
            override val aliasCollector: AliasCollector get() = aliasCollector
            override val referenceCollector: ReferenceCollector get() = referenceCollector
        }

        val queries = queries.mapNotNull { it.resolve(resolverContext, v2SchemaVersion) }.toSet()

        val types = types.entries.mapNotNull { (qualifiedName, type) ->
            if (qualifiedName in referencedTypes) {
                val apiType = type.resolve(resolverContext, v2SchemaVersion)
                qualifiedName to apiType
            } else {
                null
            }
        }.toMap()

        return APIVersion(
            supportedLanguages = languages,
            supportedQueries = queries,
            supportedTypes = types
        )
    }

}