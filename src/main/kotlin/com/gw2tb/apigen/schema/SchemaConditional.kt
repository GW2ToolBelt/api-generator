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

/**
 * A `SchemaConditional` represents a JSON object.
 *
 * TODO ...
 *
 * ### Mapping
 *
 * @param name                              the name of the conditional
 * @param disambiguationBy
 * @param disambiguationBySideProperty
 * @param interpretationInNestedProperty
 * @param sharedProperties
 * @param interpretations
 * @param description                       a description of the conditional
 *
 * @since   0.7.0
 */
public data class SchemaConditional(
    public override val name: Name,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
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
     * A conditional interpretation.
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    the serial name of the property which the interpretation is nested in
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether the interpretation is deprecated
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: SchemaTypeUse,
        public val isDeprecated: Boolean
    )

}