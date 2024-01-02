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
package com.gw2tb.apigen.ir

import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.schema.SchemaMap

/**
 * A low-level representation of a [SchemaMap].
 *
 * @param keys              the type of the map's keys
 * @param values            the type of the map's values
 * @param nullableValues    whether the map's values may be `null`
 * @param description       an optional description of the map
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRMap internal constructor(
    public val keys: IRPrimitiveOrAlias,
    public val values: IRTypeUse<*>,
    public val nullableValues: Boolean,
    public val description: String?
): IRTypeUse<SchemaMap>() {

    override fun resolve(resolverContext: ResolverContext, v2SchemaVersion: SchemaVersion?): SchemaMap {
        val keys = keys.resolve(resolverContext, v2SchemaVersion)
        val values = values.resolve(resolverContext, v2SchemaVersion)

        return SchemaMap(
            keys = keys,
            values = values,
            nullableValues = nullableValues,
            description = description
        )
    }

}