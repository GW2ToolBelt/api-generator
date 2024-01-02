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
package com.gw2tb.apigen.test

import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.model.QualifiedTypeName
import com.gw2tb.apigen.schema.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*
import org.junit.jupiter.api.Assertions

@OptIn(LowLevelApiGenApi::class)
fun assertLoweredEquals(expected: SchemaTypeUse, actual: SchemaTypeUse) {
    fun equalsSignature(expected: SchemaTypeUse, actual: SchemaTypeUse): Boolean = when (expected) {
        is SchemaArray -> actual is SchemaArray && equalsSignature(expected.elements, actual.elements)
        is SchemaMap -> actual is SchemaMap && equalsSignature(expected.keys as SchemaTypeUse, actual.keys as SchemaTypeUse) && equalsSignature(expected.values, actual.values)
        is SchemaPrimitive -> actual is SchemaPrimitiveOrAlias && (expected::class == actual.lowered()::class)

        is SchemaTypeReference -> TODO("Not yet implemented")
    }

    if (!equalsSignature(expected, actual)) Assertions.fail<Unit>("Expected type did not match. Expected: $expected; got $actual")
}

fun testSchema(
    schema: SchemaTypeDeclaration,
    actual: JsonElement,
    path: String = ""
): List<SchemaMismatch> {
    val context = SchemaMatcher(actual, path)
    context.use { testDeclaration(it, schema) }

    return context.errors
}

data class SchemaMismatch(
    val path: String,
    val message: String,
    val cause: Throwable?
)

private fun testArray(
    context: SchemaMatcherContext,
    schema: SchemaArray,
    nullable: Boolean
) {
    val jsonArray = context.deriveActual(JsonElement::jsonArray, nullable) ?: return

    jsonArray.indices.forEach { index ->
        context.push(index).use { childContext ->
            testTypeUse(
                context = childContext,
                schema = schema.elements,
                nullable = schema.nullableElements
            )
        }
    }
}

private fun testConditional(
    context: SchemaMatcherContext,
    schema: SchemaConditional,
    nullable: Boolean = false,
    inline: Boolean = false,
    sidePropertyInterpretationKey: String? = null
) {
    val jsonObject = context.deriveActual(JsonElement::jsonObject, nullable) ?: return

    val interpretationKey = if (schema.selectorInSideProperty) {
        if (sidePropertyInterpretationKey === null) {
            context.error("No side property interpretation key was provided")
            return
        }

        sidePropertyInterpretationKey
    } else {
        jsonObject[schema.selector]?.jsonPrimitive?.content ?: run {
            context.error("Could not find discriminator '${schema.selector}'")
            return
        }
    }

    val interpretation = schema.interpretations[interpretationKey]
    if (interpretation === null) {
        context.error("Could not find interpretation for key '$interpretationKey'")
        return
    }

    schema.sharedProperties.values.forEach { property ->
        testProperty(
            context = context,
            schema = property,
            isInlinedOptional = inline && nullable
        )
    }

    testTypeUse(
        context = if (schema.interpretationInNestedProperty) {
            context.push(interpretation.interpretationNestProperty!!) ?: run {
                context.error("")
                return
            }
        } else {
            context
        },
        schema = interpretation.type
    )
}

private fun testDeclaration(
    context: SchemaMatcherContext,
    schema: SchemaTypeDeclaration,
    nullable: Boolean = false,
    inline: Boolean = false,
    sidePropertyInterpretationKey: String? = null
) {
    when (schema) {
        is SchemaAlias -> TODO()
        is SchemaEnum -> testEnum(context, schema, nullable)
        is SchemaConditional -> testConditional(context, schema, nullable, sidePropertyInterpretationKey = sidePropertyInterpretationKey)
        is SchemaRecord -> testRecord(context, schema, nullable, inline)
        is SchemaTuple -> testTuple(context, schema, nullable)
    }
}

private tailrec fun SchemaPrimitiveOrAlias.serializer(): KSerializer<out Any> {
    return when (this) {
        is SchemaBitfield -> ULong.serializer()
        is SchemaBoolean -> Boolean.serializer()
        is SchemaDecimal -> Double.serializer()
        is SchemaInteger -> Long.serializer()
        is SchemaString -> String.serializer()
        is SchemaTypeReference.Alias -> alias.type.serializer()
    }
}

private fun testEnum(
    context: SchemaMatcherContext,
    schema: SchemaEnum,
    nullable: Boolean
) {
    fun <T : Any> KSerializer<T>.adjust(): KSerializer<*> = when {
        nullable -> this.nullable
        else -> this
    }

    val serializer = schema.type.serializer().adjust()

    val element = try {
        Json.decodeFromJsonElement(serializer, context.actual)
    } catch (e: SerializationException) {
        context.error("Failed to decode ${schema::class.simpleName} from ${context.actual::class.simpleName}", e)
        return
    }

    val enumValue = schema.values.find { it.value == element.toString() }
    if (enumValue == null) context.error("Could not find value for element: $element")
}

private fun testMap(
    context: SchemaMatcherContext,
    schema: SchemaMap,
    nullable: Boolean
) {
    val jsonObject = context.deriveActual(JsonElement::jsonObject, nullable) ?: return

    jsonObject.forEach { key, _ ->
        // TODO test key

        context.push(key, path = "[$key]")!!.use { childContext ->
            testTypeUse(
                context = childContext,
                schema = schema.values,
                nullable = schema.nullableValues
            )
        }
    }
}

private fun testRecord(
    context: SchemaMatcherContext,
    schema: SchemaRecord,
    nullable: Boolean = false,
    inline: Boolean = false
) {
    context.deriveActual(JsonElement::jsonObject, nullable) ?: return

    schema.properties.values.forEach { property ->
        testProperty(
            context = context,
            schema = property,
            isInlinedOptional = inline && nullable
        )
    }
}

private fun testPrimitive(
    context: SchemaMatcherContext,
    schema: SchemaPrimitive,
    nullable: Boolean,
    lenient: Boolean
) {
    // lenient -> nullable
    require(!lenient || nullable) { "A primitive may not be lenient but not nullable" }

    fun <T : Any> KSerializer<T>.adjust(): KSerializer<*> = when {
        lenient -> this.lenientNullable
        nullable -> this.nullable
        else -> this
    }

    val serializer = schema.serializer().adjust()

    try {
        Json.decodeFromJsonElement(serializer, context.actual)
    } catch (e: SerializationException) {
        context.error("Failed to decode ${schema::class.simpleName} from ${context.actual::class.simpleName}", e)
    }
}

private fun testTuple(
    context: SchemaMatcherContext,
    schema: SchemaTuple,
    nullable: Boolean
) {
    val jsonArray = context.deriveActual(JsonElement::jsonArray, nullable) ?: return

    if (jsonArray.size == 0) {
        if (nullable) return

        context.error("Non-optional tuple must not be empty")
    }

    schema.elements.forEachIndexed { index, element ->
        context.push(index).use { childContext ->
            testTypeUse(
                context = childContext,
                schema = element.type,
                nullable = false
            )
        }
    }
}

private fun testTypeUse(
    context: SchemaMatcherContext,
    schema: SchemaTypeUse,
    nullable: Boolean = false,
    lenient: Boolean = false,
    inline: Boolean = false,
    sidePropertyInterpretationKey: String? = null
) {
    require(schema is SchemaPrimitiveOrAlias || !lenient) { "Only primitives may be lenient" }

    when (schema) {
        is SchemaArray -> testArray(context, schema, nullable)
        is SchemaMap -> testMap(context, schema, nullable)
        is SchemaPrimitive -> testPrimitive(context, schema, nullable, lenient)
        is SchemaTypeReference -> when (schema.name) {
            is QualifiedTypeName.Alias -> testPrimitive(
                context,
                (schema as SchemaTypeReference.Alias).alias.type,
                nullable,
                lenient
            )
            is QualifiedTypeName.Declaration -> testDeclaration(
                context,
                (schema as SchemaTypeReference.Declaration).declaration,
                nullable,
                inline,
                sidePropertyInterpretationKey = sidePropertyInterpretationKey
            )
        }
    }
}

private fun testProperty(
    context: SchemaMatcherContext,
    schema: SchemaProperty,
    isInlinedOptional: Boolean
) {
    val childContext = if (schema.isInline) {
        context.pushVirtual(schema.serialName)
    } else {
        context.push(schema.serialName)
    }

    if (childContext === null) {
        // It's fine if an optional property cannot be found
        if (schema.optionality.isOptional || (isInlinedOptional)) return

        context.error("Could not find required property: ${schema.serialName}")
    } else {
        val propertyType = schema.type
        val sidePropertyInterpretationKey = if (propertyType is SchemaTypeReference.Declaration
            && propertyType.declaration is SchemaConditional
            && propertyType.declaration.selectorInSideProperty
        ) {
            context.actual.jsonObject[propertyType.declaration.selector]?.jsonPrimitive?.content ?: run {
                context.error("Could not find required discriminator '${propertyType.declaration.selector}'")
                return
            }
        } else {
            null
        }

        testTypeUse(
            context = childContext,
            schema = schema.type,
            nullable = schema.optionality.isOptional,
            lenient = schema.isLenient,
            inline = schema.isInline,
            sidePropertyInterpretationKey = sidePropertyInterpretationKey
        )
    }
}

private inline fun <reified T> SchemaMatcherContext.deriveActual(transform: (JsonElement) -> T, nullable: Boolean = false): T? {
    return try {
        transform(actual)
    } catch (e: IllegalArgumentException) {
        if (nullable) {
            try {
                actual.jsonNull
            } catch (e: IllegalArgumentException) {
                this.error("Expected nullable ${T::class.simpleName} but found ${actual::class.simpleName}")
            }
        } else {
            this.error("Expected ${T::class.simpleName} but found ${actual::class.simpleName}")
        }

        return null
    }
}

private interface SchemaMatcherContext : AutoCloseable {

    val trackingConsumer: TrackingConsumer

    val actual: JsonElement
    val path: String

    fun error(message: String, cause: Throwable? = null)

    private fun doPush(actual: JsonElement, path: String): SchemaMatcherContext {
        return object : SchemaMatcherContext {

            override val trackingConsumer = TrackingConsumer(actual)

            override val actual: JsonElement get() = actual
            override val path: String get() = "${this@SchemaMatcherContext.path}/$path"

            override fun error(message: String, cause: Throwable?) {
                // TODO preserve path
                this@SchemaMatcherContext.error(message, cause)
            }

            override fun close() {
                trackingConsumer.report(this::error)
            }

        }
    }

    fun push(index: Int): SchemaMatcherContext {
        val jsonElement = actual.jsonArray[index]
        (trackingConsumer as TrackingConsumer.Array).consume(index)

        return doPush(actual = jsonElement, path = "[$index]")
    }

    fun push(key: String, path: String = key): SchemaMatcherContext? {
        val jsonElement = actual.jsonObject[key] ?: return null
        (trackingConsumer as TrackingConsumer.Object).consume(key)

        return doPush(actual = jsonElement, path)
    }

    fun pushVirtual(path: String): SchemaMatcherContext {
        return object : SchemaMatcherContext by this {
            override val actual: JsonElement get() = this@SchemaMatcherContext.actual
            override val path: String get() = "${this@SchemaMatcherContext.path}/<<$path>>"

            override fun close() {}
        }
    }

}

@Suppress("TestFunctionName")
private fun TrackingConsumer(actual: JsonElement): TrackingConsumer =
    when (actual) {
        is JsonArray -> TrackingConsumer.Array(actual)
        is JsonObject -> TrackingConsumer.Object(actual)
        else -> TrackingConsumer.Element(actual)
    }

private sealed class TrackingConsumer {

    abstract fun report(error: (String) -> Unit)

    class Array(actual: JsonArray) : TrackingConsumer() {

        private val consumedElements = mutableMapOf<Int, Boolean>()

        init {
            consumedElements.putAll(actual.indices.associateWith { false })
        }

        fun consume(index: Int) {
            require(index in consumedElements) { "Element at index '$index' does not exist in ${consumedElements.keys}" }
            consumedElements[index] = true
        }

        override fun report(error: (String) -> Unit) {
            val unconsumedKeys = consumedElements.filter { (_, v) -> !v }.keys
            unconsumedKeys.forEach { key -> error("Array element was not visited: $key") }
        }

    }

    class Element(actual: JsonElement) : TrackingConsumer() {
        override fun report(error: (String) -> Unit) {}
    }

    class Object(actual: JsonObject) : TrackingConsumer() {

        private val consumedElements = mutableMapOf<String, Boolean>()

        init {
            consumedElements.putAll(actual.mapValues { false })
        }

        fun consume(key: String) {
            require(key in consumedElements) { "Element with key '$key' does not exist in ${consumedElements.keys}" }
            consumedElements[key] = true
        }

        override fun report(error: (String) -> Unit) {
            val unconsumedKeys = consumedElements.filter { (_, v) -> !v }.keys
            unconsumedKeys.forEach { key -> error("Found unexpected element: $key") }
        }

    }

}

private class SchemaMatcher(
    override val actual: JsonElement,
    override val path: String = ""
) : SchemaMatcherContext {

    override val trackingConsumer = TrackingConsumer(actual)

    private val mutableErrors = mutableListOf<SchemaMismatch>()

    private lateinit var _errors: List<SchemaMismatch>
    val errors: List<SchemaMismatch> get() {
        check(this::_errors.isInitialized) { "SchemaMatcher has not been closed" }
        return _errors.toList()
    }

    override fun error(message: String, cause: Throwable?) {
        check(!this::_errors.isInitialized) { "SchemaMatcher has already been closed" }
        mutableErrors += SchemaMismatch(path, message, cause)
    }

    override fun close() {
        check(!this::_errors.isInitialized) { "SchemaMatcher has already been closed" }
        _errors = mutableErrors.toList()
    }

}