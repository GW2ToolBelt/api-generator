/*
 * Copyright (c) 2019-2020 Leon Linhart
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
@file:Suppress("RedundantVisibilityModifier")
package com.gw2tb.apigen.model

import com.gw2tb.apigen.schema.*

/**
 * An http query parameter.
 *
 * @param key           the name of the parameter (as used in the query)
 * @param type          the type of the parameter
 * @param description   the description of the parameter
 * @param name          the name of the parameter in _TitleCase_
 * @param camelCaseName the name of the parameter in _camelCase_
 * @param isOptional    whether or not the parameter is optional
 *
 * @since   0.1.0
 */
public data class QueryParameter internal constructor(
    public val key: String,
    public val type: SchemaPrimitive,
    public val description: String,
    public val name: String,
    public val camelCaseName: String,
    public val isOptional: Boolean
)