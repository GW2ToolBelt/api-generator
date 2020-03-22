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
@file:JvmName("GW2APIGen")
package com.github.gw2toolbelt.apigen

import com.github.gw2toolbelt.apigen.internal.dsl.*
import com.github.gw2toolbelt.apigen.internal.spec.*
import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.schema.*
import com.github.gw2toolbelt.apigen.schema.SchemaType.Kind.*
import java.util.*

/**
 * The definition for the API version 2 of the Guild Wars 2 API.
 *
 * @since   0.1.0
 */
public val API_V2_DEFINITION: APIVersion by lazy {
    APIVersion(
        endpoints = GW2v2(),
        supportedLanguages = EnumSet.allOf(Language::class.java)
    )
}

/**
 * The definition Guild Wars 2's use of the `identity` field of the MumbleLink protocol.
 *
 * @since   0.1.0
 */
public val MUMBLELINK_IDENTITY_DEFINITION: SchemaMap by lazy {
    SchemaMap(SchemaMapBuilder().apply {
        "name"(STRING, "the name of the currently played character")
        "profession"(INTEGER, "the current profession (class) of the currently played character")
        "spec"(INTEGER, "the ID of the current elite-specialization of the currently played character, or 0")
        "race"(INTEGER, "the ID of the race of the currently played character")
        SerialName("map_id").."mapID"(INTEGER, "the ID of the current map")
        SerialName("world_id").."worldID"(INTEGER, "the ID of the current world")
        SerialName("team_color_id").."teamColorID"(INTEGER, "the ID of the current team")
        "commander"(BOOLEAN, "whether or not the player currently is commanding a squad")
        "map"(INTEGER, "the ID of the current map")
        "fov"(DECIMAL, "the scaling of the FOV")
        "uisz"(INTEGER, "the selected UI size")
    }.properties, null)
}