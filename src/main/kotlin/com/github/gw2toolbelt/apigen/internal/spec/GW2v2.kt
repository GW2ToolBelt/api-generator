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
import com.github.gw2toolbelt.apigen.model.v2.V2SchemaVersion.*
import com.github.gw2toolbelt.apigen.schema.SchemaType.Kind.*
import java.util.concurrent.TimeUnit.*

@ExperimentalUnsignedTypes
internal val GW2v2 = GW2APIVersion {
    "/account" {
        summary = "Returns information about a player's account."
        security(ACCOUNT)

        schema(map {
            "id"(STRING, "the unique persistent account GUID")
            "age"(INTEGER, "the age of the account in seconds")
            "name"(STRING, "the unique account name with numerical suffix")
            "world"(INTEGER, "the ID of the home world the account is assigned to")
            "guilds"(
                description = "a list of guilds assigned to the given account",
                type = array(STRING)
            )
            optional(GUILDS).."guild_leader"(
                description = "a list of guilds the account is leader of",
                type = array(STRING)
            )
            "created"(STRING, "an ISO-8601 standard timestamp of when the account was created")
            "access"(
                description = "a list of what content this account has access to",
                type = array(STRING)
            )
            "commander"(BOOLEAN, "true if the player has bought a commander tag")
            optional(PROGRESSION).."fractal_level"(INTEGER, "the account's personal fractal level")
            optional(PROGRESSION).."daily_ap"(INTEGER, "the daily AP the account has")
            optional(PROGRESSION).."monthly_ap"(INTEGER, "the monthly AP the account has")
            optional(PROGRESSION).."wvw_rank"(INTEGER, "the account's personal wvw rank")
            since(V2_SCHEMA_2019_02_21T00_00_00_000Z).."last_modified"(STRING, "an ISO-8601 standard timestamp of when the account information last changed as perceived by the API")
        })
    }
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
    "/account/emotes" {
        summary = "Returns information about a player's unlocked emotes."
        security = setOf(ACCOUNT)

        schema(array(INTEGER, "an array of IDs containing the ID of each emote unlocked by the player"))
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
    "/colors" {
        summary = "Returns information about all dye colors in the game."
        cache(1u, HOURS)
        isLocalized = true

        fun APPEARANCE() = map {
            "brightness"(INTEGER, "the brightness")
            "contrast"(DECIMAL, "the contrast")
            "hue"(INTEGER, "the hue in HSL colorspace")
            "saturation"(DECIMAL, "the saturation in HSL colorspace")
            "lightness"(DECIMAL, "the lightness in HSL colorspace")
            "rbg"(array(INTEGER), "a list containing precalculated RGB values")
        }

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the color's ID")
            "name"(STRING, "the color's name")
            "base_rgb"(array(INTEGER), "the base RGB values")
            "cloth"(APPEARANCE(), "detailed information on its appearance when applied on cloth armor")
            "leather"(APPEARANCE(), "detailed information on its appearance when applied on leather armor")
            "metal"(APPEARANCE(), "detailed information on its appearance when applied on metal armor")
            optional.."fur"(APPEARANCE(), "detailed information on its appearance when applied on fur armor")
            "item"(INTEGER, "the ID of the dye item")
            "categories"(array(STRING), "the categories of the color")
        })
    }
    "/commerce/listings" {
        summary = "Returns current buy and sell listings from the trading post."

        fun LISTING() = map {
            "listings"(INTEGER, "the number of individual listings this object refers to (e.g. two players selling at the same price will end up in the same listing)")
            "unit_price"(INTEGER, "the sell offer or buy order price in coins"),
            "quantity"(INTEGER, "the amount of items being sold/bought in this listing")
        }

        supportedQueries(BY_ID, BY_IDS(all = false), BY_PAGE)
        schema(map {
            "id"(INTEGER, "the item's ID")
            "buys"(array(LISTING()), "list of all buy listings")
            "sells"(array(LISTING()), "list of all sell listings")
        })
    }
    "/commerce/prices" {
        summary = "Returns current aggregated buy and sell listing information from the trading post."

        supportedQueries(BY_ID, BY_IDS(all = false), BY_PAGE)
        schema(map {
            "id"(INTEGER, "the item's ID")
            "whitelisted"(BOOLEAN, "indicates whether or not a free to play account can purchase or sell this item on the trading post")
            "buys"(
                description = "the buy information",
                type = map {
                    "unit_price"(INTEGER, "the highest buy order price in coins")
                    "quantity"(INTEGER, "the amount of items being bought")
                }
            )
            "sells"(
                description = "the sell information",
                type = map {
                    "unit_price"(INTEGER, "the lowest sell order price in coins")
                    "quantity"(INTEGER, "the amount of items being sold")
                }
            )
        })
    }
    "/currencies" {
        summary = "Returns information about currencies contained in the acount wallet."
        cache(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the currency's ID")
            "name"(STRING, "the currency's name")
            "description"(STRING, "a description of the currency")
            "icon"(STRING, "the currency's icon")
            "order"(INTEGER, "a number that can be used to sort the list of currencies")
        })
    }
    "/dailycrafting" {
        summary = "Returns information about the items that can be crafted once per day."
        cache(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the dailycrafting")
        })
    }
    "/emotes" {
        summary = "Returns information about unlockable emotes."
        cache = Duration(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(STRING, "the emote's ID")
            "commands"(
                description = "the commands that may be used to trigger the emote",
                type = array(INTEGER)
            )
            "unlock_items"(
                description = "the IDs of the items that unlock the emote",
                type = array(INTEGER)
            )
        })
    }
    "/files" {
        summary = "Returns commonly requested in-game assets."
        cache = Duration(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(STRING, "the file identifier")
            "icon"(STRING, "the URL to the image")
        })
    }
    "/itemstats" {
        summary = "Returns information about itemstats."
        cache = Duration(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the stat set's ID")
            "name"(STRING, "the name of the stat set")
            "attributes"(
                description = "the list of attribute bonuses",
                type = array(map {
                    "attribute"(STRING, "the name of the attribute")
                    "multiplier"(DECIMAL, "the multiplier for that attribute")
                    "value"(INTEGER, "the default value for that attribute")
                })
            )
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
    "/mapchests" {
        summary = "Returns information about the Hero's Choice Chests that can be acquired once per day."
        cache(1u, HOURS)

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the ID of the mapchest")
        })
    }
    "/outfits" {
        summary = "Returns information about outfits."
        cache = Duration(1u, HOURS)
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "id"(INTEGER, "the outfit's ID")
            "name"(STRING, "the outfit's name")
            "icon"(STRING, "the outfit's icon")
            "unlock_items"(
                description = "the IDs of the items that unlock the outfit",
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