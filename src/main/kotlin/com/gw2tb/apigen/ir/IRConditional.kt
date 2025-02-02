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
import com.gw2tb.apigen.schema.SchemaConditional
import com.gw2tb.apigen.schema.SchemaProperty

/**
 * A low-level representation of a [SchemaConditional].
 *
 * @param name                              the name of the conditional
 * @param selector                          the key of the property which's value is used to select the appropriate
 *                                          interpretation
 * @param selectorInSideProperty            `true` if the selector property resides next to the object, or `false` if it
 *                                          is nested in the object
 * @param interpretationInNestedProperty    `true` if the interpretations are nested in separate properties, or `false`
 *                                          if they are embedded in the object
 * @param sharedProperties                  the properties that are common to all interpretations
 * @param interpretations                   the interpretations of the conditional
 * @param description                       a description of the conditional
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRConditional internal constructor(
    public override val name: Name,
    public val selector: String,
    public val selectorInSideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Set<IRProperty>,
    public val interpretations: Set<Interpretation>,
    public val description: String
) : IRTypeDeclaration<SchemaConditional>() {

    override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaConditional {
        val sharedProperties = sharedProperties.mapNotNull { it.resolve(resolverContext, v2SchemaVersion) }
            .associateBy(SchemaProperty::serialName)

        val interpretations = interpretations.mapNotNull { it.resolve(resolverContext, v2SchemaVersion) }
            .associateBy(SchemaConditional.Interpretation::interpretationKey)

        return SchemaConditional(
            name = name,
            description = description,
            selector = selector,
            selectorInSideProperty = selectorInSideProperty,
            interpretationInNestedProperty = interpretationInNestedProperty,
            sharedProperties = sharedProperties,
            interpretations = interpretations
        )
    }

    /**
     * A low-level representation of an [SchemaConditional.Interpretation].
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    the serial name of the property which the interpretation is nested in
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether the interpretation is deprecated
     * @param since                         the lower bound version (inclusive)
     * @param until                         the upper bound version (exclusive)
     *
     * @since   0.7.0
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: IRTypeUse<*>,
        public val isDeprecated: Boolean,
        public val since: SchemaVersion?,
        public val until: SchemaVersion?
    ) {

        internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaConditional.Interpretation? {
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