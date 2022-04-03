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
package com.gw2tb.apigen.test

import com.gw2tb.apigen.schema.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*

private val json = Json

private val lenientJson = Json(json) {
    isLenient = true
}

fun testSchema(
    expected: SchemaTypeDeclaration,
    actual: JsonElement,
    context: RootSchemaMatcherContext = RootSchemaMatcherContext()
): List<SchemaMatcherContext.MatchingError> {
    testDeclaration(context, expected, actual, nullable = false)
    return context.errors.toList()
}

private fun test(
    context: SchemaMatcherContext,
    expected: SchemaTypeUse,
    actual: JsonElement,
    nullable: Boolean,
    lenient: Boolean,
    inheritedExpectedProperties: Map<String, SchemaProperty> = emptyMap(),
    sidePropertyInterpretationKey: String? = null
) {
    when (expected) {
        is SchemaArray -> testArray(context, expected, actual, nullable)
        is SchemaPrimitive -> testPrimitive(context, expected, actual, nullable, lenient)
        is SchemaMap -> testMap(context, expected, actual, nullable)
        is SchemaTypeReference -> testDeclaration(context, expected.declaration, actual, nullable, inheritedExpectedProperties, sidePropertyInterpretationKey)
        else -> error("Unexpected type: ${expected::class.simpleName}")
    }
}

private fun testDeclaration(
    context: SchemaMatcherContext,
    expected: SchemaTypeDeclaration,
    actual: JsonElement,
    nullable: Boolean,
    inheritedExpectedProperties: Map<String, SchemaProperty> = emptyMap(),
    sidePropertyInterpretationKey: String? = null
) {
    when (expected) {
        is SchemaConditional -> testConditional(context, expected, actual, nullable, inheritedExpectedProperties, sidePropertyInterpretationKey)
        is SchemaRecord -> testRecord(context, expected, actual, nullable, inheritedExpectedProperties)
        else -> error("Unexpected type: ${expected::class.simpleName}")
    }
}

private fun testArray(
    context: SchemaMatcherContext,
    expected: SchemaArray,
    actual: JsonElement,
    nullable: Boolean
) {
    val jsonArray = try {
        actual.jsonArray
    } catch (e: IllegalArgumentException) {
        if (nullable) {
            try {
                actual.jsonNull
            } catch (e: IllegalArgumentException) {
                context.error("Expected nullable array but found ${actual::class.simpleName}", e)
            }
        } else {
            context.error("Expected array but found ${actual::class.simpleName}", e)
        }

        return
    }

    jsonArray.forEachIndexed { index, element ->
        test(
            context.push("[$index]"),
            expected.elements,
            element,
            expected.nullableElements,
            lenient = false
        )
    }
}

private fun testConditional(
    context: SchemaMatcherContext,
    expected: SchemaConditional,
    actual: JsonElement,
    nullable: Boolean,
    inheritedExpectedProperties: Map<String, SchemaProperty> = emptyMap(),
    sidePropertyInterpretationKey: String?
) {
    val jsonObject = try {
        actual.jsonObject
    } catch (e: IllegalArgumentException) {
        if (nullable) {
            try {
                actual.jsonNull
            } catch (e: IllegalArgumentException) {
                context.error("Expected nullable object but found ${actual::class.simpleName}", e)
            }
        } else {
            context.error("Expected object but found ${actual::class.simpleName}", e)
        }

        return
    }

    val expectedProperties = (expected.sharedProperties + inheritedExpectedProperties)

    val interpretationKey = if (expected.disambiguationBySideProperty) {
        if (sidePropertyInterpretationKey == null) {
            error("No side property interpretation key was provided")
        }

        sidePropertyInterpretationKey
    } else {
        jsonObject[expected.disambiguationBy]?.jsonPrimitive?.content ?: run {
            context.error("Could not find discriminator '${expected.disambiguationBy}'")
            return
        }
    }

    val expectedInterpretation = expected.interpretations[interpretationKey]
    if (expectedInterpretation == null) {
        context.error("Could not find interpretation for key '$interpretationKey'")
        return
    }

    val interpretation = if (expected.interpretationInNestedProperty) {
        jsonObject[expectedInterpretation.interpretationNestProperty] ?: run {
            context.error("Interpretation nest property '${expectedInterpretation.interpretationNestProperty}' not found")
            return
        }
    } else {
        jsonObject
    }

    if (expected.interpretationInNestedProperty) { // Test base class separately
        val unvisitedActualKeys = jsonObject.keys.toMutableSet()
        val unvisitedRequiredKeys = expectedProperties.values
            .filter { !it.optionality.isOptional }
            .map(SchemaProperty::serialName)
            .toMutableSet()

        expected.interpretations.forEach { (_, interpretation) ->
            unvisitedActualKeys.remove(interpretation.interpretationNestProperty!!)
        }

        expectedProperties.forEach { (_, property) ->
            val value = jsonObject[property.serialName] ?: return@forEach
            unvisitedActualKeys -= property.serialName
            unvisitedRequiredKeys -= property.serialName

            val propertyType = property.type

            @Suppress("NAME_SHADOWING")
            val sidePropertyInterpretationKey = if (propertyType is SchemaTypeReference
                && propertyType.declaration is SchemaConditional
                && propertyType.declaration.disambiguationBySideProperty
            ) {
                jsonObject[propertyType.declaration.disambiguationBy]?.jsonPrimitive?.content ?: run {
                    context.error("Could not find required discriminator '${propertyType.declaration.disambiguationBy}'")
                    return@forEach
                }
            } else {
                null
            }

            test(
                context.push(property.serialName),
                expected = propertyType,
                actual = value,
                nullable = property.optionality.isOptional,
                lenient = property.isLenient,
                sidePropertyInterpretationKey = sidePropertyInterpretationKey
            )
        }

        unvisitedActualKeys.forEach { context.error("Found unexpected property '$it'") }
        unvisitedRequiredKeys.forEach { context.error("Could not find required property '$it'") }
    }

    test(
        if (expected.interpretationInNestedProperty) context.push(expectedInterpretation.interpretationNestProperty!!) else context,
        expected = expectedInterpretation.type,
        actual = interpretation,
        nullable = false,
        lenient = false,
        inheritedExpectedProperties = if (expected.interpretationInNestedProperty) emptyMap() else expectedProperties
    )
}

private fun testMap(
    context: SchemaMatcherContext,
    expected: SchemaMap,
    actual: JsonElement,
    nullable: Boolean
) {
    val jsonObject = try {
        actual.jsonObject
    } catch (e: IllegalArgumentException) {
        if (nullable) {
            try {
                actual.jsonNull
            } catch (e: IllegalArgumentException) {
                context.error("Expected nullable object but found ${actual::class.simpleName}", e)
            }
        } else {
            context.error("Expected object but found ${actual::class.simpleName}", e)
        }

        return
    }

    jsonObject.forEach { key, value ->
        // TODO test key

//        test(
//            context.push(),
//            expected.keys,
//            key,
//            nullable = false
//        )

        test(
            context.push("[$key]"),
            expected.values,
            value,
            expected.nullableValues,
            lenient = false
        )
    }
}

private fun testPrimitive(
    context: SchemaMatcherContext,
    expected: SchemaPrimitive,
    actual: JsonElement,
    nullable: Boolean,
    lenient: Boolean
) {
    fun <T : Any> KSerializer<T>.adjustNullable(): KSerializer<*> =
        if (nullable)
            if (lenient) this.lenientNullable else this.nullable
        else
            this

    val json = if (lenient) lenientJson else json

    try {
        when (expected) {
            is SchemaBoolean -> json.decodeFromJsonElement(Boolean.serializer().adjustNullable(), actual)
            is SchemaDecimal -> json.decodeFromJsonElement(Double.serializer().adjustNullable(), actual)
            is SchemaInteger -> json.decodeFromJsonElement(Long.serializer().adjustNullable(), actual)
            is SchemaString -> json.decodeFromJsonElement(String.serializer().adjustNullable(), actual)
        }
    } catch (e: SerializationException) {
        context.error("Could not decode ${expected::class.simpleName} from ${actual::class.simpleName}", e)
    }
}

private fun testRecord(
    context: SchemaMatcherContext,
    expected: SchemaRecord,
    actual: JsonElement,
    nullable: Boolean,
    inheritedExpectedProperties: Map<String, SchemaProperty> = emptyMap()
) {
    val jsonObject = try {
        actual.jsonObject
    } catch (e: IllegalArgumentException) {
        if (nullable) {
            try {
                actual.jsonNull
            } catch (e: IllegalArgumentException) {
                context.error("Expected nullable object but found ${actual::class.simpleName}", e)
            }
        } else {
            context.error("Expected object but found ${actual::class.simpleName}", e)
        }

        return
    }

    val unvisitedActualKeys = jsonObject.keys.toMutableSet()

    val expectedProperties = (expected.properties + inheritedExpectedProperties)
    val unvisitedRequiredKeys = expectedProperties.values
        .filter { !it.optionality.isOptional }
        .map(SchemaProperty::serialName)
        .toMutableSet()

    expectedProperties.forEach { (_, property) ->
        val value = jsonObject[property.serialName] ?: return@forEach
        unvisitedActualKeys -= property.serialName
        unvisitedRequiredKeys -= property.serialName

        val propertyType = property.type

        val sidePropertyInterpretationKey = if (propertyType is SchemaTypeReference
            && propertyType.declaration is SchemaConditional
            && propertyType.declaration.disambiguationBySideProperty
        ) {
            jsonObject[propertyType.declaration.disambiguationBy]?.jsonPrimitive?.content ?: run {
                context.error("Could not find required discriminator '${propertyType.declaration.disambiguationBy}'")
                return@forEach
            }
        } else {
            null
        }

        test(
            context.push(property.serialName),
            expected = propertyType,
            actual = value,
            nullable = property.optionality.isOptional,
            lenient = property.isLenient,
            sidePropertyInterpretationKey = sidePropertyInterpretationKey
        )
    }

    unvisitedActualKeys.forEach { context.error("Found unexpected property '$it'") }
    unvisitedRequiredKeys.forEach { context.error("Could not find required property '$it'") }
}

interface SchemaMatcherContext {

    val path: String

    fun push(path: String): SchemaMatcherContext {
        return object : SchemaMatcherContext {
            override val path: String = "${this@SchemaMatcherContext.path}/$path"

            override fun error(message: String, cause: Exception?, path: String) {
                this@SchemaMatcherContext.error(message, cause, path)
            }
        }
    }

    fun error(message: String, cause: Exception? = null, path: String = this.path)

    data class MatchingError(
        val path: String,
        val message: String,
        val cause: Exception?
    )

}

class RootSchemaMatcherContext(override val path: String = "") : SchemaMatcherContext {

    val errors = mutableListOf<SchemaMatcherContext.MatchingError>()

    override fun error(message: String, cause: Exception?, path: String) {
        errors += SchemaMatcherContext.MatchingError(path, message, cause)
    }

}