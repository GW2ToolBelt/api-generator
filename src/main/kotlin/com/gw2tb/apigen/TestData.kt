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
package com.gw2tb.apigen

import com.gw2tb.apigen.model.APIType
import com.gw2tb.apigen.model.v2.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/** Provides access to data that may be used for testing. */
public object TestData {

    public operator fun get(type: APIType, version: V2SchemaVersion? = null): List<JsonElement> {
        val resourceName = buildString {
            append("/com/gw2tb/apigen/")

            val testSet = type.testSet ?: return emptyList()
            append("v$testSet")

            append("_")
            append(type.name.toTitleCase())

            if (testSet == 2 && version != null && version != V2SchemaVersion.V2_SCHEMA_CLASSIC) {
                append("+")
                append(version.identifier!!.replace(':', '_'))
            }

            append(".json")
        }

        return Json.decodeFromString<JsonArray>(TestData::class.java.getResourceAsStream(resourceName).use {
            if (it == null) {
                if (type.isTopLevel) {
                    error("Did not find test vectors for top-level type ${type.name.toTitleCase()} (Resource: '$resourceName')")
                }

                return emptyList()
            }

            it.reader().readText()
        }).toList()
    }

}