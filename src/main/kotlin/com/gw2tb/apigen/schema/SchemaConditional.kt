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
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.Name

/**
 * A `SchemaConditional` represents a JSON object.
 *
 * A conditional is similar to a [SchemaRecord] in that it provides strong
 * guarantees about the object's entries. However, the exact shape may vary.
 * A conditional serves as base and is further specialized in an [Interpretation].
 * The applicable interpretation is identified by a property close to the
 * conditional.
 *
 * ### Mapping
 *
 * SchemaConditionals should ideally be mapped to a sealed hierarchy to provide
 * strong typing for all properties and interpretations where possible.
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
public data class SchemaConditional internal constructor(
    public override val name: Name,
    public val selector: String,
    public val selectorInSideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Map<String, SchemaProperty>,
    public val interpretations: Map<String, Interpretation>,
    public val description: String
) : SchemaTypeDeclaration() {

    /**
     * Returns `true` if at least one of the [shared properties][sharedProperties]
     * is either [localized][SchemaProperty.isLocalized] or its [type][SchemaProperty.type]
     * is [SchemaTypeUse.isLocalized], or `false` otherwise.
     *
     * @since   0.7.0
     */
    override val isLocalized: Boolean by lazy {
        sharedProperties.any { (_, v) -> v.isLocalized || v.type.isLocalized } || interpretations.any { (_, v) -> v.type.isLocalized }
    }

    /**
     * An interpretation of the conditional.
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    the serial name of the property which the interpretation is nested in
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether the interpretation is deprecated
     *
     * @since   0.7.0
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: SchemaTypeUse,
        public val isDeprecated: Boolean
    )

}