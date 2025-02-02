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
package com.gw2tb.apigen.ir

import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.schema.SchemaTuple

/**
 * A low-level representation of a [SchemaTuple].
 *
 * @param name          the name of the tuple
 * @param elements      the elements of the tuple
 * @param description   a description of the tuple
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRTuple internal constructor(
    public override val name: Name,
    public val elements: Set<Element>,
    public val description: String
) : IRTypeDeclaration<SchemaTuple>() {

    override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaTuple {
        val elements = elements.map { it.resolve(resolverContext, v2SchemaVersion) }

        return SchemaTuple(
            name = name,
            elements = elements,
            description = description
        )
    }

    /**
     * A low-level representation of a [SchemaTuple.Element].
     *
     * @param name          the name of the element
     * @param type          the type of the element's value
     * @param description   a description of the element
     * @param isLenient     whether to perform type-coercion on the input
     * @param isLocalized   whether the element's value is localized
     *
     * @since   0.7.0
     */
    public data class Element internal constructor(
        public val name: Name,
        public val type: IRTypeUse<*>,
        public val description: String,
        public val isLenient: Boolean,
        public val isLocalized: Boolean
    ) {

        internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaTuple.Element {
            val type = type.resolve(resolverContext, v2SchemaVersion)

            return SchemaTuple.Element(
                name = name,
                type = type,
                description = description,
                isLenient = isLenient,
                isLocalized = isLocalized
            )
        }

    }

}
