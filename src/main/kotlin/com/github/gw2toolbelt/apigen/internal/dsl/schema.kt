/*
 * Copyright (c) 2019-2020 Leon Linhart
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
package com.github.gw2toolbelt.apigen.internal.dsl

import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.schema.*

internal interface SchemaAggregateBuildProvider : SchemaArrayBuilderProvider, SchemaMapBuilderProvider

internal interface SchemaArrayBuilderProvider {

    fun array(
        items: SchemaType,
        description: String? = null
    ): SchemaType =
        SchemaArray(items, description)

}

internal class SchemaMapBuilder {

    private val _properties = mutableMapOf<String, SchemaMapPropertyBuilder>()
    val properties get() = _properties.mapValues { it.value.property }

    operator fun String.invoke(
        type: SchemaType,
        description: String? = null
    ): SchemaMapPropertyBuilder =
        SchemaMapPropertyBuilder(type, description).also { _properties[this] = it }

    val deprecated = PropertyModifier.deprecated
    val optional = PropertyModifier.optional

    fun optional(scope: TokenScope): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaMapPropertyBuilder) {
            property.optionality = Optionality.MANDATED(scope)
        }
    }

    operator fun IPropertyModifier.rangeTo(modifier: IPropertyModifier): Set<IPropertyModifier> = setOf(this, modifier)
    operator fun IPropertyModifier.rangeTo(property: SchemaMapPropertyBuilder): SchemaMapPropertyBuilder = property.also { this.applyTo(it) }
    operator fun Set<IPropertyModifier>.rangeTo(property: SchemaMapPropertyBuilder): SchemaMapPropertyBuilder = property.also { forEach { mod -> mod.applyTo(it) } }

    interface IPropertyModifier {
        fun applyTo(property: SchemaMapPropertyBuilder)
    }

    @Suppress("EnumEntryName")
    enum class PropertyModifier : IPropertyModifier {
        deprecated {
            override fun applyTo(property: SchemaMapPropertyBuilder) {
                property.isDeprecated = true
            }
        },
        optional {
            override fun applyTo(property: SchemaMapPropertyBuilder) {
                property.optionality = Optionality.OPTIONAL
            }
        }
    }

}

internal interface SchemaMapBuilderProvider {

    fun map(
        description: String? = null,
        configure: SchemaMapBuilder.() -> Unit
    ): SchemaType =
        SchemaMap(SchemaMapBuilder().also(configure).properties, description)

}

internal class SchemaMapPropertyBuilder(private val type: SchemaType, private val description: String?) {

    var isDeprecated = false
    var optionality: Optionality? = null

    val property get() =
        SchemaMap.Property(
            type = type,
            description = description,
            optionality = optionality ?: Optionality.REQUIRED,
            isDeprecated = isDeprecated
        )

}

internal val BY_ID get() = QueryType.ById
internal val BY_PAGE get() = QueryType.ByPage
internal val BY_IDS = QueryType.ByIds(supportsAll = true)

@Suppress("FunctionName")
internal fun BY_IDS(all: Boolean = true) = QueryType.ByIds(supportsAll = all)