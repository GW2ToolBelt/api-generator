/*
 * Copyright (c) 2019-2026 Leon Linhart
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
 * A schema primitive representing integral JSON numbers.
 *
 * Contrary to a [SchemaInteger], a SchemaBitfield does not represent values
 * that should be treated as integers. Instead, it should be considered to
 * represent a list of bits encoded in a number.
 *
 * ### Mapping
 *
 * SchemaBitmaps should be mapped to either bit-field abstractions (e.g. Java's
 * BitSet) or integral types with a size of at least 64 bits.
 *
 * @since   0.7.0
 */
public object SchemaBitfield : SchemaPrimitive()

/**
 * A schema primitive representing JSON booleans.
 *
 * ### Mapping
 *
 * SchemaBooleans should be mapped to boolean types.
 *
 * @since   0.7.0
 */
public object SchemaBoolean : SchemaPrimitive()

/**
 * A schema primitive representing decimal floating point JSON numbers.
 *
 * ### Mapping
 *
 * SchemaDecimals should be mapped to floating point types with at least 32 bits
 * of precision. (It is recommended to prefer 64 bits of precision when
 * possible.)
 *
 * @since   0.7.0
 */
public object SchemaDecimal : SchemaPrimitive()

/**
 * A schema primitive representing integral JSON numbers.
 *
 * ### Mapping
 *
 * SchemaIntegers should be mapped to integral types with a size of at least
 * 32 bits. (It is recommended to prefer 64 bits of size when possible.)
 *
 * @since   0.7.0
 */
public object SchemaInteger : SchemaPrimitiveIdentifier()

/**
 * A schema primitive representing JSON strings.
 *
 * The [format], if not null, specifies the format of the data in the string.
 *
 * ### Mapping
 *
 * In general, SchemaStrings can be mapped to string types. If possible, the
 * [format] should be taken into account to map to a more fitting type where
 * applicable.
 *
 * @param format    the format of the string if it carries additional semantic
 *                  meaning, or `null` if it is arbitrary
 *
 * @since   0.7.0
 */
public data class SchemaString(
    val format: Format?
) : SchemaPrimitiveIdentifier() {

    /**
     * The format of the string's content.
     *
     * @since   0.11.0
     */
    public enum class Format {
        /**
         * An ISO-8601 timestamp.
         *
         * @since   0.11.0
         */
        TIMESTAMP,

        /**
         * A UUID.
         *
         * @since   0.11.0
         */
        UUID
    }

}
