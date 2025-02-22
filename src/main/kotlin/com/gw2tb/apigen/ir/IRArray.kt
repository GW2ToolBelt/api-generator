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
import com.gw2tb.apigen.schema.SchemaArray

/**
 * A low-level representation of a [SchemaArray].
 *
 * @param elements          the type of the array's elements
 * @param nullableElements  whether the array's elements may be `null`
 * @param description       an optional description of the array
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRArray internal constructor(
    public val elements: IRTypeUse<*>,
    public val nullableElements: Boolean,
    public val description: String?
): IRTypeUse<SchemaArray>() {

    override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaArray {
        val elements = elements.resolve(resolverContext, v2SchemaVersion)

        return SchemaArray(
            elements = elements,
            nullableElements = nullableElements,
            description = description
        )
    }

}