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
package com.gw2tb.apigen.model

import com.gw2tb.apigen.APIGenerator
import com.gw2tb.apigen.schema.SchemaAlias
import com.gw2tb.apigen.schema.SchemaTypeDeclaration

/**
 * A qualified type name is the name for a possibly nested type.
 *
 * @since   0.7.0
 */
public sealed class QualifiedTypeName : Iterable<Name> {

    /**
     * The simple name of the type.
     *
     * @since   0.7.0
     */
    public abstract val name: Name

    /**
     * A qualified name for a [SchemaAlias].
     *
     * As alias is never nested in another type, the qualified name consists of
     * only the alias' name.
     *
     * Typically, this class will be used to resolve a name against [APIGenerator.aliases]
     * to retrieve the matching [SchemaAlias].
     *
     * @param name  the name of the alias
     *
     * @since   0.7.0
     */
    public data class Alias(
        override val name: Name
    ) : QualifiedTypeName() {

        /**
         * Returns an iterator that yields the [name] of this alias.
         *
         * @since   0.7.0
         */
        override fun iterator(): Iterator<Name> = iterator {
            yield(name)
        }

    }

    /**
     * A qualified name for a [SchemaTypeDeclaration].
     *
     * Typically, this class will be used to resolve a name against [APIGenerator]
     * to retrieve the matching [SchemaTypeDeclaration].
     *
     * @param nest  the list of names identifying the nest
     * @param name  the name of the type
     *
     * @since   0.7.0
     */
    public data class Declaration(
        val nest: List<Name>?,
        override val name: Name
    ) : QualifiedTypeName() {

        /**
         * Returns an iterator that yields all components of this qualified
         * name. First, the [nest] elements are yielded one-by-one. Finally,
         * [name] is yielded.
         *
         * @since   0.7.0
         */
        override fun iterator(): Iterator<Name> = iterator {
            if (nest != null) yieldAll(nest)
            yield(name)
        }

    }

}

/**
 * Converts the receiver to a string.
 *
 * @receiver    the qualified name to convert to a string
 *
 * @param separator the separator for the segments
 * @param transform the function used to transform the [Name] segments
 *
 * @return  the resulting string
 *
 * @since   0.7.0
 */
public fun QualifiedTypeName.joinToString(separator: CharSequence = "/", transform: (Name) -> String): String = when (this) {
    is QualifiedTypeName.Alias -> transform(name)
    is QualifiedTypeName.Declaration -> (nest?.joinToString(separator = separator, postfix = separator, transform = transform) ?: "") + transform(name)
}