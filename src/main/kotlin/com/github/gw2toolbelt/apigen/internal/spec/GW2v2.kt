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
import com.github.gw2toolbelt.apigen.schema.SchemaType.Kind.*
import java.util.concurrent.TimeUnit.*

@ExperimentalUnsignedTypes
internal val GW2v2 = GW2APIVersion {
    "/account/achievements" {
        summary = "Returns a player's progress towards all their achievements."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(map {
            "id"(INTEGER, "the achievement's ID")
            "done"(BOOLEAN, "whether or not the achievement is done")
            optional.."bits"(
                description = "the meaning of each value varies with each achievement",
                type = array(INTEGER, "this attribute contains an array of numbers, giving more specific information on the progress for the achievement")
            )
            optional.."current"(INTEGER, "the player's current progress towards the achievement")
            optional.."max"(INTEGER, "the amount needed to complete the achievement")
            optional.."repeated"(INTEGER, "the number of times the achievement has been completed if the achievement is repeatable")
            optional.."unlocked"(BOOLEAN, "if this achievement can be unlocked, whether or not the achievement is unlocked")
        }))
    }
    "/account/dailycrafting" {
        summary = "Returns which items that can be crafted once per day a player crafted since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)

        schema(array(STRING, "an array of IDs for each item that can be crafted once per day that the player has crafted since the most recent daily reset"))
    }
    "/account/dungeons" {
        summary = "Returns which dungeons paths a player has completed since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing an ID for each dungeon path that the player has completed since the most recent daily reset"))
    }
    "/account/dyes" {
        summary = "Returns information about a player's unlocked dyes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each dye unlocked by the player"))
    }
    "/account/finishers" {
        summary = "Returns information about a player's unlocked finishers."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each finisher unlocked by the player"))
    }
    "/account/gliders" {
        summary = "Returns information about a player's unlocked gliders."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each glider unlocked by the player"))
    }
    "/account/home/nodes" {
        summary = "Returns information about a player's unlocked home instance nodes."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing th ID of each home instance node unlocked by the player"))
    }
    "/account/mailcarriers" {
        summary = "Returns information about a player's unlocked mail carriers."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each mail carrier unlocked by the player"))
    }
    "/account/mapchests" {
        summary = "Returns which Hero's Choice Chests a player has acquired since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs for Hero's Choice Chests that the player has acquired since the most recent daily reset"))
    }
    "/account/masteries" {
        summary = "Returns information about a player's unlocked masteries."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(map {
            "id"(INTEGER, "the mastery's ID")
            optional.."level"(INTEGER, "the index of the unlocked mastery level")
        }))
    }
    "/account/mastery/points" {
        summary = "Returns information about a player's unlocked mastery points."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(map {
            "totals"(
                description = "information about the total mastery points for a reason",
                type = array(map {
                    "region"(STRING, "the mastery region")
                    "spent"(INTEGER, "the amount of mastery points of this region spent in mastery tracks")
                    "earned"(INTEGER, "the amount of mastery points of this region earned for the account")
                })
            )
            "unlocked"(
                description = "an array of IDs of unlocked mastery points",
                type = array(INTEGER)
            )
        })
    }
    "/account/materials" {
        summary = "Returns information about the materials stored in a player's vault."
        security = setOf(ACCOUNT, INVENTORIES)

        schema(array(map {
            "id"(INTEGER, "the material's item ID")
            "category"(INTEGER, "the material category the item belongs to")
            "count"(INTEGER, "the number of the material that is stored in the player's vault")
            optional.."binding"(STRING, "the binding of the material")
        }))
    }
    "/account/minis" {
        summary = "Returns information about a player's unlocked miniatures."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each miniature unlocked by the player"))
    }
    "/account/mounts/skins" {
        summary = "Returns information about a player's unlocked mount skins."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(STRING, "an array of IDs containing the ID of each mount skin unlocked by the player"))
    }
    "/account/mounts/types" {
        summary = "Returns information about a player's unlocked mounts."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(STRING, "an array of IDs containing the ID of each mount unlocked by the player"))
    }
    "/account/novelties" {
        summary = "Returns information about a player's unlocked novelties."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each novelty unlocked by the player"))
    }
    "/account/outfits" {
        summary = "Returns information about a player's unlocked outfits."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each outfit unlocked by the player"))
    }
    "/account/pvp/heroes" {
        summary = "Returns information about a player's unlocked PvP heroes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each PvP hero unlocked by the player"))
    }
    "/account/raids" {
        summary = "Returns which raid encounter a player has cleared since the most recent raid reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing the ID of each raid encounter that the player has cleared since the most recent raid reset"))
    }
    "/account/recipes" {
        summary = "Returns information about a player's unlocked recipes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each recipe unlocked by the player"))
    }
    "/account/skins" {
        summary = "Returns information about a player's unlocked skins."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each skin unlocked by the player"))
    }
    "/account/titles" {
        summary = "Returns information about a player's unlocked titles."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each title unlocked by the player"))
    }
    "/account/wallet" {
        summary = "Returns information about a player's wallet."
        security = setOf(ACCOUNT, WALLET)

        schema(array(map {
            "id"(INTEGER, "the currency ID that can be resolved against /v2/currencies")
            "value"(INTEGER, "the amount of this currency in the player's wallet")
        }))
    }
    "/account/worldbosses" {
        summary = "Returns which world bosses that can be looted once per day a player has defeated since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs for each world boss that can be looted once per day that the player has defeated since the most recent daily reset"))
    }
    "/build" {
        summary = "Returns the current build ID."

        schema(map {
            "id"(INTEGER, "the current build ID")
        })
    }
    "/legends" {
        summary = "Returns information about the Revenant legends."
        cache = Duration(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(STRING, "the legend's ID")
            "swap"(INTEGER, "the ID of the profession (swap Legend) skill")
            "heal"(INTEGER, "the ID of the heal skill")
            "elite"(INTEGER, "the ID of the elite skills")
            "utilities"(
                description = "the IDs of the utility skills",
                type = array(INTEGER)
            )
        })
    }
    "/races" {
        summary = "Returns information about the game's playable races."
        cache = Duration(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(STRING, "the race's ID")
            "name"(STRING, "the race's localized name")
            "skills"(
                description = "an array of racial skill IDs",
                type = array(INTEGER)
            )
        })
    }
    "/titles" {
        summary = "Returns information about the titles that are in the game."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the title")
            "name"(STRING, "the display name of the title")
            deprecated..optional.."achievement"(INTEGER, "the ID of the achievement that grants this title")
            optional.."achievements"(array(INTEGER), "the IDs of the achievements that grant this title")
            optional.."ap_required"(INTEGER, "the amount of AP required to unlock this title")
        })
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
    "/worldbosses" {
        summary = "Returns information about the worldbosses that reward boss chests that can be opened once a day."
        cache(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the worldboss")
        })
    }
    "/worlds" {
        summary = "Returns information about the available worlds (or servers)."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the world")
            "name"(STRING, "the name of the world")
            "population"(STRING, "the population level of the world")
        })
    }
    "/wvw/objectives" {
        summary = "Returns information about the objectives in the World versus World game mode."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(STRING, "the ID of the objective")
            "name"(STRING, "the name of the objective")
            "type"(STRING, "the type of the objective")
            "sector_id"(INTEGER, "the map sector the objective can be found in")
            "map_id"(INTEGER, "the ID of the map the objective can be found on")
            "map_type"(STRING, "the type of the map the objective can be found on")
            "coord"(
                description = "an array of three numbers representing the X, Y and Z coordinates of the objectives marker on the map",
                type = array(DECIMAL)
            )
            "label_coord"(
                description = "an array of two numbers representing the X and Y coordinates of the sector centroid",
                type = array(DECIMAL)
            )
            "marker"(STRING, "the icon link")
            "chat_link"(STRING, "the chat code for the objective")
            optional.."upgrade_id"(INTEGER, "the ID of the upgrades available for the objective")
        })
    }
    "/wvw/ranks" {
        summary = "Returns information about the achievable ranks in the World versus World game mode."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the rank")
            "title"(STRING, "the title of the rank")
            "min_level"(INTEGER, "the WvW level required to unlock this rank")
        })
    }
    "/wvw/upgrades" {
        summary = "Returns information about available upgrades for objectives in the World versus World game mode."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the upg")
            "tiers"(
                description = "",
                type = map {
                    "name"(STRING, "the name of the upgrade tier")
                    "yaks_required"(INTEGER, "the amount of dolyaks required to reach this upgrade tier")
                    "upgrades"(
                        description = "",
                        type = map {
                            "name"(STRING, "the name of the upgrade")
                            "description"(STRING, "the description for the upgrade")
                            "icon"(STRING, "the icon link")
                        }
                    )
                }
            )
        })
    }
}