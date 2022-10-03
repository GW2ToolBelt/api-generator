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
package com.gw2tb.apigen.schema

import com.gw2tb.apigen.model.TokenScope

/**
 * A property's optionality. There are three different _levels_ of optionality:
 *
 * 1. Optional - The property is unconditionally optional,
 * 2. Mandated - The property is required under certain conditions, and
 * 3. Required - The property is required.
 */
public sealed class Optionality {

    /** Returns `true` for [OPTIONAL] and [MANDATED], and `false` otherwise. */
    public abstract val isOptional: Boolean

    /** The property is optional. */
    public object OPTIONAL : Optionality() {
        override val isOptional: Boolean = true
    }

    /** The property is required for a key with the appropriate [scope]. */
    public class MANDATED(public val scope: TokenScope) : Optionality() {
        override val isOptional: Boolean = true
    }

    /** The property is required. */
    public object REQUIRED : Optionality() {
        override val isOptional: Boolean = false
    }

}