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
package com.github.gw2toolbelt.apigen.schema

import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.model.v2.*

public interface SchemaType {

    public sealed class Kind {

        public object BOOLEAN : Kind(), SchemaType
        public object DECIMAL : Kind(), SchemaType
        public object INTEGER : Kind(), SchemaType
        public object STRING : Kind(), SchemaType

    }

}

public data class SchemaArray internal constructor(
    public val items: SchemaType,
    public val description: String?
) : SchemaType

public data class SchemaConditional internal constructor(
    public val disambiguationBy: String,
    public val interpretations: Map<String, SchemaType>
) : SchemaType

public data class SchemaMap(
    public val properties: Map<String, Property>,
    public val description: String?
) : SchemaType {

    public data class Property(
        public val propertyName: String,
        public val type: SchemaType,
        public val description: String?,
        public val isDeprecated: Boolean,
        public val optionality: Optionality,
        public val since: V2SchemaVersion?,
        public val until: V2SchemaVersion?,
        public val serialName: String
    )

}

public sealed class Optionality {

    public abstract val isOptional: Boolean

    public object OPTIONAL : Optionality() {
        override val isOptional = true
    }

    public class MANDATED(public val scope: TokenScope) : Optionality() {
        override val isOptional = true
    }

    public object REQUIRED : Optionality() {
        override val isOptional = false
    }

}