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

import com.gw2tb.apigen.model.v2.V2SchemaVersion

public data class SchemaConditional(
    public override val name: String,
    public val disambiguationBy: String,
    public val disambiguationBySideProperty: Boolean,
    public val interpretationInNestedProperty: Boolean,
    public val sharedProperties: Map<String, SchemaProperty>,
    public val interpretations: Map<String, Interpretation>,
    public val description: String
) : SchemaTypeDeclaration() {

    override val isLocalized: Boolean by lazy {
        sharedProperties.any { (_, v) -> v.isLocalized || v.type.isLocalized } || interpretations.any { (_, v) -> v.type.isLocalized }
    }

    /**
     * A conditional interpretation.
     *
     * @param interpretationKey             the key used to identify the interpretation
     * @param interpretationNestProperty    TODO doc
     * @param type                          the schema definition for this interpretation
     * @param isDeprecated                  whether the interpretation is deprecated
     * @param since                         the minimum [V2SchemaVersion] required for the interpretation
     * @param until                         the [V2SchemaVersion] up to which the interpretation existed
     */
    public data class Interpretation internal constructor(
        public val interpretationKey: String,
        public val interpretationNestProperty: String?,
        public val type: SchemaTypeUse,
        public val isDeprecated: Boolean,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?
    )

}