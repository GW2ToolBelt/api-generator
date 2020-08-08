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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*

val BOOLEAN get() = SchemaBoolean
val DECIMAL get() = SchemaDecimal
val INTEGER get() = SchemaInteger
val STRING get() = SchemaString

internal class SchemaConditionalBuilder {

    val interpretations = mutableMapOf<String, SchemaType>()

    operator fun String.invoke(type: SchemaType) {
        interpretations[this] = type
    }

}

internal class SchemaRecordBuilder {

    private val _properties = mutableMapOf<String, SchemaRecordPropertyBuilder>()
    val properties get() = _properties.mapValues { it.value.property }

    operator fun String.invoke(
        type: SchemaType,
        description: String? = null
    ): SchemaRecordPropertyBuilder {
        return SchemaRecordPropertyBuilder(this, type, description).also { _properties[this] = it }
    }

    val deprecated = PropertyModifier.deprecated
    val optional = PropertyModifier.optional

    fun optional(scope: TokenScope): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.optionality = Optionality.MANDATED(scope)
        }
    }

    fun since(version: V2SchemaVersion): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.since = version
        }
    }

    fun until(version: V2SchemaVersion): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.until = version
        }
    }

    @Suppress("FunctionName")
    fun CamelCase(value: String): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.camelCase = value
        }
    }

    @Suppress("FunctionName")
    fun SerialName(value: String): IPropertyModifier = object : IPropertyModifier {
        override fun applyTo(property: SchemaRecordPropertyBuilder) {
            property.serialName = value
        }
    }

    operator fun IPropertyModifier.rangeTo(modifier: IPropertyModifier): Set<IPropertyModifier> = setOf(this, modifier)
    operator fun Set<IPropertyModifier>.rangeTo(modifier: IPropertyModifier): Set<IPropertyModifier> = setOf(modifier, *this.toTypedArray())

    operator fun IPropertyModifier.rangeTo(property: SchemaRecordPropertyBuilder): SchemaRecordPropertyBuilder = property.also { this.applyTo(it) }
    operator fun Set<IPropertyModifier>.rangeTo(property: SchemaRecordPropertyBuilder): SchemaRecordPropertyBuilder = property.also { forEach { mod -> mod.applyTo(it) } }

    interface IPropertyModifier {
        fun applyTo(property: SchemaRecordPropertyBuilder)
    }

    @Suppress("EnumEntryName")
    enum class PropertyModifier : IPropertyModifier {
        deprecated {
            override fun applyTo(property: SchemaRecordPropertyBuilder) {
                property.isDeprecated = true
            }
        },
        optional {
            override fun applyTo(property: SchemaRecordPropertyBuilder) {
                property.optionality = Optionality.OPTIONAL
            }
        }
    }

}

internal class SchemaRecordPropertyBuilder(
    private val propertyName: String,
    private val type: SchemaType,
    private val description: String?
) {

    init {
        require(propertyName[0].isUpperCase()) { "propertyName should be in TitleCase" }
    }

    private var isUnused = true

    var isDeprecated = false
        set(value) {
            require(isUnused)
            field = value
        }

    var optionality: Optionality? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var since: V2SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var until: V2SchemaVersion? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var serialName: String? = null
        set(value) {
            require(isUnused)
            field = value
        }

    var camelCase: String? = null
        set(value) {
            require(isUnused)
            field = value
        }

    val property by lazy {
        isUnused = false

        SchemaRecord.Property(
            propertyName = propertyName,
            type = type,
            description = description,
            optionality = optionality ?: Optionality.REQUIRED,
            isDeprecated = isDeprecated,
            since = since,
            until = until,
            serialName = serialName ?: propertyName.toLowerCase(),
            camelCaseName = camelCase ?: propertyName.run { "${toCharArray()[0].toLowerCase()}${substring(1)}" }
        )
    }

}

internal val BY_ID get() = QueryType.ById
internal val BY_PAGE get() = QueryType.ByPage
internal val BY_IDS = QueryType.ByIds(supportsAll = true)

@Suppress("FunctionName")
internal fun BY_IDS(all: Boolean = true) = QueryType.ByIds(supportsAll = all)