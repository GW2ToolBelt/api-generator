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

import com.gw2tb.apigen.internal.spec.*
import com.gw2tb.apigen.model.*
import java.util.*

/**
 * A Guild Wars 2 API version.
 *
 * @param supportedLanguages    the version's supported languages
 * @param supportedQueries      the version's supported queries
 * @param supportedTypes        the version's supported types
 */
public data class APIVersion<Q : APIQuery, T : APIType> internal constructor(
    val supportedLanguages: EnumSet<Language>,
    val supportedQueries: Set<Q>,
    val supportedTypes: Map<TypeLocation, T>,
    internal val version: String
) {

    public companion object {

        public fun getV1(endpoints: Set<APIv1Endpoint> = EnumSet.allOf(APIv1Endpoint::class.java)): APIVersion<APIQuery.V1, APIType.V1> =
            GW2v1.build(endpoints)

        public fun getV2(endpoints: Set<APIv2Endpoint> = EnumSet.allOf(APIv2Endpoint::class.java)): APIVersion<APIQuery.V2, APIType.V2> =
            GW2v2.build(endpoints)

    }

}