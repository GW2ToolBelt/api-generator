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
package com.gw2tb.apigen.model

import com.gw2tb.apigen.APIGenerator
import com.gw2tb.apigen.schema.Name
import com.gw2tb.apigen.schema.SchemaAlias
import com.gw2tb.apigen.schema.SchemaTypeDeclaration

/**
 * TODO doc
 *
 * @since   0.7.0
 */
public sealed class QualifiedTypeName {

    /**
     * TODO doc
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
    ) : QualifiedTypeName()

    /**
     * A qualified name for a [SchemaTypeDeclaration].
     *
     * Typically, this class will be used to resolve a name against [APIVersion.supportedTypes]
     * to retrieve the matching [SchemaTypeDeclaration].
     *
     * @param nest  TODO doc
     * @param name  the name of the type
     *
     * @since   0.7.0
     */
    public data class Declaration(
        val nest: List<Name>?,
        override val name: Name
    ) : QualifiedTypeName()

}