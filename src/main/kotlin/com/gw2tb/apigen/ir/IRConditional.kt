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

import com.gw2tb.apigen.model.v2.V2SchemaVersion
import com.gw2tb.apigen.schema.Name
import com.gw2tb.apigen.schema.SchemaConditional
import com.gw2tb.apigen.schema.SchemaProperty

/**
 * A low-level representation of a [SchemaConditional].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRConditional internal constructor(
    public override val name: Name,
    public val description: String,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Set<IRProperty>,
    public val interpretations: Set<Interpretation>
) : IRTypeDeclaration<SchemaConditional>() {

    override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): SchemaConditional {
        val sharedProperties = sharedProperties.mapNotNull { it.resolve(resolverContext, v2SchemaVersion) }
            .associateBy(SchemaProperty::serialName)

        val interpretations = interpretations.mapNotNull { it.resolve(resolverContext, v2SchemaVersion) }
            .associateBy(SchemaConditional.Interpretation::interpretationKey)

        return SchemaConditional(
            name = name,
            description = description,
            disambiguationBy = disambiguationBy,
            disambiguationBySideProperty = disambiguationBySideProperty,
            interpretationInNestedProperty = interpretationInNestedProperty,
            sharedProperties = sharedProperties,
            interpretations = interpretations
        )
    }

    public data class Interpretation(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: IRTypeUse<*>,
        public val isDeprecated: Boolean,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?
    ) {

        internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): SchemaConditional.Interpretation? {
            if (v2SchemaVersion != null) {
                if (since != null && since > v2SchemaVersion) return null
                if (until != null && until < v2SchemaVersion) return null
            }

            val type = type.resolve(resolverContext)

            return SchemaConditional.Interpretation(
                interpretationKey = interpretationKey,
                interpretationNestProperty = interpretationNestProperty,
                type = type,
                isDeprecated = isDeprecated
            )
        }

    }

}