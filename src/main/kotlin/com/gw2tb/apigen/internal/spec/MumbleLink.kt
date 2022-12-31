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
package com.gw2tb.apigen.internal.spec

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.Optionality
import com.gw2tb.apigen.schema.*

@OptIn(LowLevelApiGenApi::class)
internal val MUMBLE_IDENTITY by lazy {
    operator fun String.invoke(type: DeferredPrimitiveType, description: String, serialName: String? = null): IRProperty {
        val name = Name.derive(titleCase = this, snakeCase = serialName)

        return IRProperty(
            name = name,
            type = type.getFlat(),
            description = description,
            isDeprecated = false,
            isInline = false,
            isLenient = false,
            isLocalized = false,
            optionality = Optionality.REQUIRED,
            serialName = name.toSnakeCase(),
            since = null,
            until = null
        )
    }

    IRRecord(
        name = Name.deriveFromTitleCase("MumbleLink"),
        description = "The information exposed by MumbleLink's \"identity\" buffer.",
        properties = setOf(
            "Name"(STRING, "the name of the currently played character"),
            "Profession"(PROFESSION_ID, "the name of the currently played character"),
            "Spec"(SPECIALIZATION_ID, "the ID of the currently equipped elite-specialization, or zero"),
            "Race"(RACE_ID, "the ID of the race of the currently played character"),
            "MapID"(MAP_ID, "the ID of the current map", serialName = "map_id"),
            "WorldID"(BITFIELD, "the ID of the current world", serialName = "world_id"),
            "TeamColorId"(INTEGER, "the ID of the current team", serialName = "team_color_id"),
            "Commander"(BOOLEAN, "a flag indicating whether the player currently is commanding a squad"),
            "Map"(MAP_ID, "the ID of the current map"),
            "FoV"(DECIMAL, "the scaling of the FOV"),
            "UISize"(INTEGER, "the selected UI size", serialName = "uisz")
        )
    )
}