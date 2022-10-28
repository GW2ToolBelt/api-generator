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

import com.gw2tb.apigen.ir.IRTypeUse
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.model.PathParameter
import com.gw2tb.apigen.model.v2.V2SchemaVersion

/**
 * A low-level representation of a [PathParameter].
 *
 * @since   0.7.0
 */
@LowLevelApiGenApi
public data class IRPathParameter internal constructor(
    val key: String,
    val type: IRTypeUse<*>,
    val description: String,
    val name: String,
    val camelCaseName: String
) {

    internal fun resolve(resolverContext: ResolverContext, v2SchemaVersion: V2SchemaVersion?): PathParameter {
        val type = type.resolve(resolverContext, v2SchemaVersion)

        return PathParameter(
            key = key,
            type = type,
            description = description,
            name = name,
            camelCaseName = camelCaseName
        )
    }


}