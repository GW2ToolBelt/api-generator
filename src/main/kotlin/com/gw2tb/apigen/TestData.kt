/*
 * Copyright (c) 2019-2021 Leon Linhart
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

import com.gw2tb.apigen.model.v2.*

/** Provides access to data that may be used for testing. */
public object TestData {

    public operator fun get(api: APIVersion, key: String, version: V2SchemaVersion): String {
        val resource = buildString {
            append("/com/gw2tb/apigen/")
            append(if (api == APIVersion.API_V2) "v2" else "v1")
            append(key.toLowerCase().split("/").joinToString(separator = "_") { if (it.startsWith(":")) "{${it.substring(1)}}" else it })

            if (version != V2SchemaVersion.V2_SCHEMA_CLASSIC) {
                append("+")
                append(version.identifier!!.replace(':', '_'))
            }

            append(".json")
        }

        return TestData::class.java.getResourceAsStream(resource).use {
            it.reader().readText()
        }
    }

}