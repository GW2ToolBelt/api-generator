/*
 * Copyright (c) 2019-2024 Leon Linhart
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
package com.gw2tb.apigen.schema.model

import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.schema.*

/**
 * A type used by the Guild Wars 2 API.
 *
 * @param name                  the type's name
 * @param schema                the type's schema definition
 * @param schemaVersion         the effective [SchemaVersion] that has most recently affected the type
 * @param interpretationHint    additional information about the type if it represents an interpretation of a [SchemaConditional]
 *
 * @since   0.7.0
 */
public data class APIType internal constructor(
    public val name: Name,
    public val schema: SchemaTypeDeclaration,
    public val schemaVersion: SchemaVersion?,
    public val interpretationHint: InterpretationHint?,
    internal val isTopLevel: Boolean,
    internal val testSet: Int?
) {

    /**
     * Additional information about a type that represents an [interpretation][SchemaConditional.Interpretation].
     *
     * @param conditionalBase               the qualified name of the conditional base type
     * @param interpretationKey             the key that identifies this interpretation
     * @param interpretationNestProperty    the key of the property in which this interpretation is nested, or `null`
     *
     * @since   0.7.0
     */
    public data class InterpretationHint internal constructor(
        val conditionalBase: QualifiedTypeName,
        val interpretationKey: String,
        val interpretationNestProperty: String?
    )

}