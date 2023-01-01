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
@file:Suppress("unused")
package com.gw2tb.apigen.model

import com.gw2tb.apigen.schema.model.*

/**
 * A language supported by the Guild Wars 2 API.
 *
 * The subset of languages supported by a given version of the Guild Wars 2 API
 * are available in a corresponding [APIVersion] object.
 *
 * @param language  the ISO 639 alpha-2 or alpha-3 language code
 *
 * @since   0.7.0
 */
public enum class Language(
    public val language: String
) {
    /**
     * The Chinese language (`zh`).
     *
     * @since   0.7.0
     */
    CHINESE("zh"),
    /**
     * The English language (`en`).
     *
     * @since   0.7.0
     */
    ENGLISH("en"),
    /**
     * The French language (`fr`).
     *
     * @since   0.7.0
     */
    FRENCH("fr"),
    /**
     * The German language (`de`).
     *
     * @since   0.7.0
     */
    GERMAN("de"),
    /**
     * The Spanish language (`es`).
     *
     * @since   0.7.0
     */
    SPANISH("es");
}