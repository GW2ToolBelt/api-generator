/*
 * Copyright (c) 2019-2025 Leon Linhart
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
package com.gw2tb.apigen.model

/**
 * An abstraction for names that provides several functions to convert a name to
 * a [String] representation in a desired casing.
 *
 * @since   0.7.0
 */
@ConsistentCopyVisibility
public data class Name private constructor(
    private val camelCase: String,
    private val snakeCase: String,
    private val titleCase: String
) {

    public constructor(vararg segments: String): this(
        camelCase = segments.first().lowercase() + segments.drop(1).joinToString(separator = ""),
        snakeCase = segments.joinToString(separator = "_") { it.lowercase() },
        titleCase = segments.joinToString(separator = "")
    )

    internal companion object {

        @Suppress("NAME_SHADOWING")
        fun derive(camelCase: String? = null, snakeCase: String? = null, titleCase: String? = null): Name {
            fun String.firstToLowerCase(): String =
                "${toCharArray()[0].lowercaseChar()}${substring(1)}"

            fun String.firstToUpperCase(): String =
                "${toCharArray()[0].uppercaseChar()}${substring(1)}"

            fun String.splitCasing(): List<String> = buildList {
                var remaining = this@splitCasing

                while (remaining.isNotEmpty()) {
                    val segment = buildString {
                        if (remaining.first() == '/') {
                            append("/")
                            remaining = remaining.drop(1)
                        }

                        if (remaining.first().isUpperCase()) {
                            append(remaining.first())
                            remaining = remaining.drop(1)
                        }

                        val buf = remaining.takeWhile { it != '/' && !it.isUpperCase() }
                        remaining = remaining.drop(buf.length)
                        append(buf)
                    }

                    if (segment.isNotEmpty()) {
                        add(segment)
                    }
                }
            }

            fun List<String>.joinToSnakeCase(): String = buildString {
                append(this@joinToSnakeCase.first().lowercase())

                this@joinToSnakeCase.drop(1).forEach { segment ->
                    if (!segment.startsWith("/")) append("_")
                    append(segment.lowercase())
                }
            }

            val snakeCase = snakeCase ?: (camelCase ?: titleCase)?.splitCasing()?.joinToSnakeCase() ?: error("At least one must be non null")
            val camelCase = camelCase ?: titleCase?.firstToLowerCase() ?: snakeCase.split("_").joinToString(separator = "") { it.firstToUpperCase() }
            val titleCase = titleCase ?: camelCase.firstToUpperCase()

            return Name(camelCase = camelCase, snakeCase = snakeCase, titleCase = titleCase)
        }

        fun deriveFromTitleCase(value: String): Name =
            derive(titleCase = value)

    }

    init {
        require(camelCase.isNotBlank()) { "Name[camelCase] must not be blank for $this" }
        require(snakeCase.isNotBlank()) { "Name[snakeCase] must not be blank for $this" }
        require(titleCase.isNotBlank()) { "Name[titleCase] must not be blank for $this" }
    }

    /**
     * Returns a [String] representation of this name in `camelCase`.
     *
     * @since   0.7.0
     */
    public fun toCamelCase(): String = camelCase

    /**
     * Returns a [String] representation of this name in `snake_case`.
     *
     * @since   0.7.0
     */
    public fun toSnakeCase(): String = snakeCase

    /**
     * Returns a [String] representation of this name in `TitleCase`.
     *
     * @since   0.7.0
     */
    public fun toTitleCase(): String = titleCase

}