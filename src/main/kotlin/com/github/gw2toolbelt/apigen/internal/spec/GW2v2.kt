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
package com.github.gw2toolbelt.apigen.internal.spec

import com.github.gw2toolbelt.apigen.internal.dsl.*
import com.github.gw2toolbelt.apigen.model.*
import com.github.gw2toolbelt.apigen.model.TokenScope.*
import com.github.gw2toolbelt.apigen.model.V2SchemaVersion.*
import com.github.gw2toolbelt.apigen.schema.*
import com.github.gw2toolbelt.apigen.schema.SchemaType.Kind.*
import java.util.concurrent.TimeUnit.*

@ExperimentalUnsignedTypes
internal val GW2v2 = GW2APIVersion {
    "/build" {
        summary = "Returns the current build ID."

        schema(map {
            "id"(INTEGER, "the current build ID")
        })
    }
    "/account/worldbosses" {
        summary = "Returns which world bosses that can be looted once per day a player has defeated since the most recent daily reset."
        security = setOf(PROGRESSION)

        schema(array(STRING, "an array of IDs for each world boss that can be looted once per day that the player has defeated since the most recent daily reset"))
    }
    "/tokeninfo" {
        summary = "Returns information about the supplied API key."
        security(ACCOUNT)

        schema(
            V2_SCHEMA_CLASSIC to map {
                "id"(STRING, "the API key that was requested")
                "name"(STRING, "the name given to the API key by the account owner")
                "permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
            },
            V2_SCHEMA_2019_05_22T00_00_00_000Z to map {
                "id"(STRING, "the API key that was requested")
                "name"(STRING, "the name given to the API key by the account owner")
                "permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
                "type"(STRING, "the type of the access token given")
                optional.."expires_at"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken expires")
                optional.."issued_at"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken was created")
                optional.."urls"(
                    description = "if the given subtoken is restricted to a list of URLs, contains an array of strings describing what endpoints are available to this token",
                    type = array(STRING)
                )
            }
        )
    }
}