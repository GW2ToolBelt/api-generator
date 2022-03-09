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
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import java.util.*

@APIGenDSL
internal interface QueriesBuilder<T : APIType> {

    fun pathParameter(
        name: String,
        type: DeferredSchemaType<out SchemaPrimitive>,
        description: String,
        key: String = name,
        camelCase: String = name.firstToLowerCase()
    )

    fun queryParameter(
        name: String,
        type: DeferredSchemaType<out SchemaPrimitive>,
        description: String,
        key: String = name.toLowerCase(Locale.ENGLISH),
        camelCase: String = name.firstToLowerCase(),
        isOptional: Boolean = false
    )

    fun schema(schema: DeferredSchemaType<out SchemaTypeUse>)

    fun array(
        items: DeferredSchemaType<out SchemaTypeUse>,
        description: String,
        nullableItems: Boolean = false
    ): DeferredSchemaType<SchemaArray> =
        DeferredSchemaType { typeRegistry -> items.get(typeRegistry, interpretationHint = null).mapData { SchemaArray(it, nullableItems, description) } }

    fun map(
        keys: DeferredSchemaType<out SchemaPrimitive>,
        values: DeferredSchemaType<out SchemaTypeUse>,
        description: String,
        nullableValues: Boolean = false
    ): DeferredSchemaType<SchemaMap> =
        DeferredSchemaType { typeRegistry -> values.get(typeRegistry, interpretationHint = null).mapData { SchemaMap(keys.getFlat(), it, nullableValues, description) } }

    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (SchemaRecordBuilder<T>.() -> Unit)? = null,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>

    fun record(
        name: String,
        description: String,
        block: SchemaRecordBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>

}

/** A builder for one or more V1 queries. */
internal interface QueriesBuilderV1 : QueriesBuilder<APIType.V1>

/** A builder for one or more V2 queries. */
internal interface QueriesBuilderV2 : QueriesBuilder<APIType.V2> {

    fun schema(vararg schemas: Pair<V2SchemaVersion, DeferredSchemaType<out SchemaTypeUse>>)

}