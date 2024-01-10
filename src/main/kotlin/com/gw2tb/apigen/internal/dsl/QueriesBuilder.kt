/*
 * Copyright (c) 2019-2024 Leon Linhart
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

import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.*
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.v2.*

@APIGenDSL
@OptIn(LowLevelApiGenApi::class)
internal interface QueriesBuilder<T : IRAPIType> {

    fun pathParameter(
        name: String,
        type: DeferredPrimitiveType<*>,
        description: String,
        key: String = Name.deriveFromTitleCase(name).toSnakeCase(),
        camelCase: String? = null
    )

    fun queryParameter(
        name: String,
        type: DeferredPrimitiveType<*>,
        description: String,
        key: String = Name.deriveFromTitleCase(name).toSnakeCase(),
        camelCase: String? = null,
        isOptional: Boolean = false
    )

    fun schema(schema: DeferredType<IRTypeUse<*>>)

    fun array(
        items: DeferredType<IRTypeUse<*>>,
        description: String,
        nullableItems: Boolean = false
    ): DeferredType<IRArray> =
        DeferredSchemaType { typeRegistry, isTopLevel ->
            items.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { IRArray(it, nullableItems, description) }
        }

    fun map(
        keys: DeferredType<IRPrimitive>,
        values: DeferredType<IRTypeUse<*>>,
        description: String,
        nullableValues: Boolean = false
    ): DeferredType<IRMap> =
        DeferredSchemaType { typeRegistry, isTopLevel ->
            values.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { IRMap(keys.getFlat(), it, nullableValues, description) }
        }

    fun conditional(
        name: String,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)? = null,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T> = conditional(
        Name.deriveFromTitleCase(name),
        description,
        disambiguationBy,
        disambiguationBySideProperty,
        interpretationInNestedProperty,
        sharedConfigure,
        block
    )

    fun conditional(
        name: Name,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)? = null,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>


    fun enum(type: DeferredPrimitiveType<*>, name: String, description: String, block: SchemaEnumBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        enum(type, Name.deriveFromTitleCase(name), description, block)

    fun enum(type: DeferredPrimitiveType<*>, name: Name, description: String, block: SchemaEnumBuilder<T>.() -> Unit): DeferredSchemaClass<T>


    fun record(name: String, description: String, block: SchemaRecordBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        record(Name.deriveFromTitleCase(name), description, block)

    fun record(name: Name, description: String, block: SchemaRecordBuilder<T>.() -> Unit): DeferredSchemaClass<T>


    fun tuple(name: String, description: String, block: SchemaTupleBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        tuple(Name.deriveFromTitleCase(name), description, block)

    fun tuple(name: Name, description: String, block: SchemaTupleBuilder<T>.() -> Unit): DeferredSchemaClass<T>

}

/** A builder for one or more V1 queries. */
@OptIn(LowLevelApiGenApi::class)
internal interface QueriesBuilderV1 : QueriesBuilder<IRAPIType.V1>

/** A builder for one or more V2 queries. */
@OptIn(LowLevelApiGenApi::class)
internal interface QueriesBuilderV2 : QueriesBuilder<IRAPIType.V2> {

    fun schema(vararg schemas: Pair<SchemaVersion, DeferredType<IRTypeUse<*>>>)

}