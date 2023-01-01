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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.internal.dsl

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.impl.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.IRAPIQuery
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.*
import com.gw2tb.apigen.schema.*
import kotlin.time.*

@Suppress("FunctionName")
private fun <
    E,
    Q : IRAPIQuery,
    T : IRAPIType,
    S : SpecBuilderImplBase<E, Q, T, *>
> GW2APISpec(builder: S, block: S.() -> Unit): S =
    builder.also(block)

@Suppress("FunctionName")
internal fun GW2APISpecV1(block: SpecBuilderV1.() -> Unit): SpecBuilderV1Impl =
    GW2APISpec(SpecBuilderV1Impl(), block)

@Suppress("FunctionName")
internal fun GW2APISpecV2(block: SpecBuilderV2.() -> Unit): SpecBuilderV2Impl =
    GW2APISpec(SpecBuilderV2Impl(), block)

@APIGenDSL
internal interface SpecBuilder<T : IRAPIType> {

    fun array(
        items: DeferredSchemaType<out IRTypeUse<*>>,
        description: String,
        nullableItems: Boolean = false
    ): DeferredSchemaType<IRArray> =
        DeferredSchemaType { typeRegistry, isTopLevel ->
            items.get(typeRegistry, interpretationHint = null, isTopLevel).mapData { IRArray(it, nullableItems, description) }
        }

    fun map(
        keys: DeferredSchemaType<out IRPrimitive>,
        values: DeferredSchemaType<out IRTypeUse<*>>,
        description: String,
        nullableValues: Boolean = false
    ): DeferredSchemaType<IRMap> =
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
    ): DeferredSchemaClass<T> =
        conditional(Name.deriveFromTitleCase(name), description, disambiguationBy, disambiguationBySideProperty, interpretationInNestedProperty, sharedConfigure, block)

    fun conditional(
        name: Name,
        description: String,
        disambiguationBy: String = "type",
        disambiguationBySideProperty: Boolean = false,
        interpretationInNestedProperty: Boolean = false,
        sharedConfigure: (AbstractSchemaRecordBuilder<T>.() -> Unit)? = null,
        block: SchemaConditionalBuilder<T>.() -> Unit
    ): DeferredSchemaClass<T>


    fun enum(type: DeferredPrimitiveType, name: String, description: String, block: SchemaEnumBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        enum(type, Name.deriveFromTitleCase(name), description, block)

    fun enum(type: DeferredPrimitiveType, name: Name, description: String, block: SchemaEnumBuilder<T>.() -> Unit): DeferredSchemaClass<T>


    fun record(name: String, description: String, block: SchemaRecordBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        record(Name.deriveFromTitleCase(name), description, block)

    fun record(name: Name, description: String, block: SchemaRecordBuilder<T>.() -> Unit): DeferredSchemaClass<T>


    fun tuple(name: String, description: String, block: SchemaTupleBuilder<T>.() -> Unit): DeferredSchemaClass<T> =
        tuple(Name.deriveFromTitleCase(name), description, block)

    fun tuple(name: Name, description: String, block: SchemaTupleBuilder<T>.() -> Unit): DeferredSchemaClass<T>

}

internal interface SpecBuilderV1 : SpecBuilder<IRAPIType.V1> {

    operator fun APIv1Endpoint.invoke(
        endpointTitleCase: String = path,
        route: String = path,
        querySuffix: String? = null,
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

    operator fun APIv1Endpoint.invoke(
        endpointTitleCase: String = path,
        route: String = path,
        idTypeKey: String = "id",
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration? = null,
        security: Security? = null,
        block: QueriesBuilderV1.() -> Unit
    )

}

internal interface SpecBuilderV2 : SpecBuilder<IRAPIType.V2> {

    operator fun APIv2Endpoint.invoke(
        endpointTitleCase: String = path,
        route: String = path,
        querySuffix: String? = null,
        summary: String,
        cache: Duration? = null,
        security: Security? = null,
        since: SchemaVersion = SchemaVersion.V2_SCHEMA_CLASSIC,
        until: SchemaVersion? = null,
        block: QueriesBuilderV2.() -> Unit
    )

    operator fun APIv2Endpoint.invoke(
        endpointTitleCase: String = path,
        route: String = path,
        idTypeKey: String = "id",
        summary: String,
        queryTypes: QueryTypes,
        cache: Duration? = null,
        security: Security? = null,
        since: SchemaVersion = SchemaVersion.V2_SCHEMA_CLASSIC,
        until: SchemaVersion? = null,
        block: QueriesBuilderV2.() -> Unit
    )

}