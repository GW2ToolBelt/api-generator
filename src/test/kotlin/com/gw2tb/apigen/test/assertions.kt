/*
 * Copyright (c) 2019-2023 Leon Linhart
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
package com.gw2tb.apigen.test

import com.gw2tb.apigen.schema.*
import org.junit.jupiter.api.Assertions.*

fun assertHintedEquals(expected: SchemaTypeUse, actual: SchemaTypeUse) {
    fun equalsSignature(expected: SchemaTypeUse, actual: SchemaTypeUse): Boolean = when (expected) {
        is SchemaBitfield -> actual is SchemaBitfield
        is SchemaBoolean -> actual is SchemaBoolean
        is SchemaDecimal -> actual is SchemaDecimal
        is SchemaInteger -> actual is SchemaInteger
        is SchemaString -> actual is SchemaString

        is SchemaArray -> actual is SchemaArray && equalsSignature(expected.elements, actual.elements)
        is SchemaMap -> actual is SchemaMap && equalsSignature(expected.keys, actual.keys) && equalsSignature(expected.values, actual.values)

        is SchemaTypeReference -> TODO("Not yet implemented")
    }

    if (!equalsSignature(expected, actual)) fail<Unit>("Expected type did not match. Expected: $expected; got $actual")
}