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
@file:Suppress("RedundantVisibilityModifier")
package com.gw2tb.apigen

import com.gw2tb.apigen.internal.spec.*
import com.gw2tb.apigen.model.*
import java.util.*

/**
 * A Guild Wars 2 API version.
 *
 * @param endpoints             the version's supported endpoints
 * @param supportedLanguages    the version's supported languages
 */
public data class APIVersion internal constructor(
    public val endpoints: Set<Endpoint>,
    public val supportedLanguages: EnumSet<Language>
) {

    public companion object {

        /** The definition for the API version 2 of the Guild Wars 2 API. */
        public val API_V2: APIVersion by lazy {
            APIVersion(
                endpoints = GW2v2(),
                supportedLanguages = EnumSet.allOf(Language::class.java)
            )
        }

    }

}