/*
 * Copyright (c) 2019 Leon Linhart
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
package com.github.gw2toolbelt.apigen.schema

import com.github.gw2toolbelt.apigen.model.*

interface SchemaType {

    sealed class Kind {

        object ARRAY : Kind()
        object MAP : Kind()

        object BOOLEAN : Kind(), SchemaType
        object DECIMAL : Kind(), SchemaType
        object INTEGER : Kind(), SchemaType
        object STRING : Kind(), SchemaType

    }

}

data class SchemaArray internal constructor(
    val items: SchemaType,
    val description: String?
) : SchemaType

data class SchemaMap(
    val properties: Map<String, Property>,
    val description: String?
) : SchemaType {

    data class Property(
        val type: SchemaType,
        val description: String?,
        val isDeprecated: Boolean,
        val optionality: Optionality
    )

}

sealed class Optionality {
    object OPTIONAL : Optionality()
    object REQUIRED : Optionality()
    class MANDATED(val scope: TokenScope) : Optionality()
}