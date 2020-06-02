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
@file:Suppress("DuplicatedCode", "FunctionName")
package com.github.gw2toolbelt.apigen.internal.spec

import com.github.gw2toolbelt.apigen.internal.dsl.*
import com.github.gw2toolbelt.apigen.model.TokenScope.*
import com.github.gw2toolbelt.apigen.model.v2.V2SchemaVersion.*
import com.github.gw2toolbelt.apigen.schema.SchemaType.Kind.*
import kotlin.time.*

internal val GW2v2 = GW2APIVersion {
    "/Account" {
        summary = "Returns information about a player's account."
        security(ACCOUNT)

        schema(map {
            "Id"(STRING, "the unique persistent account GUID")
            "Age"(INTEGER, "the age of the account in seconds")
            "Name"(STRING, "the unique account name with numerical suffix")
            "World"(INTEGER, "the ID of the home world the account is assigned to")
            "Guilds"(
                description = "a list of guilds assigned to the given account",
                type = array(STRING)
            )
            optional(GUILDS)..SerialName("guild_leader").."GuildLeader"(
                description = "a list of guilds the account is leader of",
                type = array(STRING)
            )
            "Created"(STRING, "an ISO-8601 standard timestamp of when the account was created")
            "Access"(
                description = "a list of what content this account has access to",
                type = array(STRING)
            )
            "Commander"(BOOLEAN, "true if the player has bought a commander tag")
            optional(PROGRESSION)..SerialName("fractal_level").."FractalLevel"(INTEGER, "the account's personal fractal level")
            optional(PROGRESSION)..SerialName("daily_ap").."DailyAP"(INTEGER, "the daily AP the account has")
            optional(PROGRESSION)..SerialName("monthly_ap").."MonthlyAP"(INTEGER, "the monthly AP the account has")
            optional(PROGRESSION)..SerialName("wvw_rank").."WvWRank"(INTEGER, "the account's personal wvw rank")
            since(V2_SCHEMA_2019_02_21T00_00_00_000Z)..SerialName("last_modified").."LastModified"(STRING, "an ISO-8601 standard timestamp of when the account information last changed as perceived by the API")
        })
    }
    "/Account/Achievements" {
        summary = "Returns a player's progress towards all their achievements."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(map {
            "Id"(INTEGER, "the achievement's ID")
            "Done"(BOOLEAN, "whether or not the achievement is done")
            optional.."Bits"(
                description = "the meaning of each value varies with each achievement",
                type = array(INTEGER, "this attribute contains an array of numbers, giving more specific information on the progress for the achievement")
            )
            optional.."Current"(INTEGER, "the player's current progress towards the achievement")
            optional.."Max"(INTEGER, "the amount needed to complete the achievement")
            optional.."Repeated"(INTEGER, "the number of times the achievement has been completed if the achievement is repeatable")
            optional.."Unlocked"(BOOLEAN, "if this achievement can be unlocked, whether or not the achievement is unlocked")
        }))
    }
    "/Account/DailyCrafting" {
        summary = "Returns which items that can be crafted once per day a player crafted since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)

        schema(array(STRING, "an array of IDs for each item that can be crafted once per day that the player has crafted since the most recent daily reset"))
    }
    "/Account/Dungeons" {
        summary = "Returns which dungeons paths a player has completed since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing an ID for each dungeon path that the player has completed since the most recent daily reset"))
    }
    "/Account/Dyes" {
        summary = "Returns information about a player's unlocked dyes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each dye unlocked by the player"))
    }
    "/Account/Emotes" {
        summary = "Returns information about a player's unlocked emotes."
        security = setOf(ACCOUNT)

        schema(array(INTEGER, "an array of IDs containing the ID of each emote unlocked by the player"))
    }
    "/Account/Finishers" {
        summary = "Returns information about a player's unlocked finishers."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each finisher unlocked by the player"))
    }
    "/Account/Gliders" {
        summary = "Returns information about a player's unlocked gliders."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each glider unlocked by the player"))
    }
    "/Account/Home/Nodes" {
        summary = "Returns information about a player's unlocked home instance nodes."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing th ID of each home instance node unlocked by the player"))
    }
    "/Account/Mailcarriers" {
        summary = "Returns information about a player's unlocked mail carriers."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each mail carrier unlocked by the player"))
    }
    "/Account/MapChests" {
        summary = "Returns which Hero's Choice Chests a player has acquired since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs for Hero's Choice Chests that the player has acquired since the most recent daily reset"))
    }
    "/Account/Masteries" {
        summary = "Returns information about a player's unlocked masteries."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(map {
            "Id"(INTEGER, "the mastery's ID")
            optional.."Level"(INTEGER, "the index of the unlocked mastery level")
        }))
    }
    "/Account/Mastery/Points" {
        summary = "Returns information about a player's unlocked mastery points."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(map {
            "Totals"(
                description = "information about the total mastery points for a reason",
                type = array(map {
                    "Region"(STRING, "the mastery region")
                    "Spent"(INTEGER, "the amount of mastery points of this region spent in mastery tracks")
                    "Earned"(INTEGER, "the amount of mastery points of this region earned for the account")
                })
            )
            "Unlocked"(
                description = "an array of IDs of unlocked mastery points",
                type = array(INTEGER)
            )
        })
    }
    "/Account/Materials" {
        summary = "Returns information about the materials stored in a player's vault."
        security = setOf(ACCOUNT, INVENTORIES)

        schema(array(map {
            "Id"(INTEGER, "the material's item ID")
            "Category"(INTEGER, "the material category the item belongs to")
            "Count"(INTEGER, "the number of the material that is stored in the player's vault")
            optional.."Binding"(STRING, "the binding of the material")
        }))
    }
    "/Account/Minis" {
        summary = "Returns information about a player's unlocked miniatures."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each miniature unlocked by the player"))
    }
    "/Account/Mounts/Skins" {
        summary = "Returns information about a player's unlocked mount skins."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(STRING, "an array of IDs containing the ID of each mount skin unlocked by the player"))
    }
    "/Account/Mounts/Types" {
        summary = "Returns information about a player's unlocked mounts."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(STRING, "an array of IDs containing the ID of each mount unlocked by the player"))
    }
    "/Account/Novelties" {
        summary = "Returns information about a player's unlocked novelties."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each novelty unlocked by the player"))
    }
    "/Account/Outfits" {
        summary = "Returns information about a player's unlocked outfits."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each outfit unlocked by the player"))
    }
    "/Account/PvP/Heroes" {
        summary = "Returns information about a player's unlocked PvP heroes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each PvP hero unlocked by the player"))
    }
    "/Account/Raids" {
        summary = "Returns which raid encounter a player has cleared since the most recent raid reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs containing the ID of each raid encounter that the player has cleared since the most recent raid reset"))
    }
    "/Account/Recipes" {
        summary = "Returns information about a player's unlocked recipes."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each recipe unlocked by the player"))
    }
    "/Account/Skins" {
        summary = "Returns information about a player's unlocked skins."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each skin unlocked by the player"))
    }
    "/Account/Titles" {
        summary = "Returns information about a player's unlocked titles."
        security = setOf(ACCOUNT, UNLOCKS)

        schema(array(INTEGER, "an array of IDs containing the ID of each title unlocked by the player"))
    }
    "/Account/Wallet" {
        summary = "Returns information about a player's wallet."
        security = setOf(ACCOUNT, WALLET)

        schema(array(map {
            "Id"(INTEGER, "the currency ID that can be resolved against /v2/currencies")
            "Value"(INTEGER, "the amount of this currency in the player's wallet")
        }))
    }
    "/Account/WorldBosses" {
        summary = "Returns which world bosses that can be looted once per day a player has defeated since the most recent daily reset."
        security = setOf(ACCOUNT, PROGRESSION)

        schema(array(STRING, "an array of IDs for each world boss that can be looted once per day that the player has defeated since the most recent daily reset"))
    }
    "/Build" {
        summary = "Returns the current build ID."

        schema(map {
            "Id"(INTEGER, "the current build ID")
        })
    }
    "/Colors" {
        summary = "Returns information about all dye colors in the game."
        cache = 1.hours
        isLocalized = true

        fun APPEARANCE() = map {
            "Brightness"(INTEGER, "the brightness")
            "Contrast"(DECIMAL, "the contrast")
            "Hue"(INTEGER, "the hue in HSL colorspace")
            "Saturation"(DECIMAL, "the saturation in HSL colorspace")
            "Lightness"(DECIMAL, "the lightness in HSL colorspace")
            "RBG"(array(INTEGER), "a list containing precalculated RGB values")
        }

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the color's ID")
            "Name"(STRING, "the color's name")
            SerialName("base_rgb").."BaseRGB"(array(INTEGER), "the base RGB values")
            "Cloth"(APPEARANCE(), "detailed information on its appearance when applied on cloth armor")
            "Leather"(APPEARANCE(), "detailed information on its appearance when applied on leather armor")
            "Metal"(APPEARANCE(), "detailed information on its appearance when applied on metal armor")
            optional.."Fur"(APPEARANCE(), "detailed information on its appearance when applied on fur armor")
            "Item"(INTEGER, "the ID of the dye item")
            "Categories"(array(STRING), "the categories of the color")
        })
    }
    "/Commerce/Listings" {
        summary = "Returns current buy and sell listings from the trading post."

        fun LISTING() = map {
            "Listings"(INTEGER, "the number of individual listings this object refers to (e.g. two players selling at the same price will end up in the same listing)")
            SerialName("unit_price").."UnitPrice"(INTEGER, "the sell offer or buy order price in coins")
            "Quantity"(INTEGER, "the amount of items being sold/bought in this listing")
        }

        supportedQueries(BY_ID, BY_IDS(all = false), BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the item's ID")
            "Buys"(array(LISTING()), "list of all buy listings")
            "Sells"(array(LISTING()), "list of all sell listings")
        })
    }
    "/Commerce/Prices" {
        summary = "Returns current aggregated buy and sell listing information from the trading post."

        supportedQueries(BY_ID, BY_IDS(all = false), BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the item's ID")
            "Whitelisted"(BOOLEAN, "indicates whether or not a free to play account can purchase or sell this item on the trading post")
            "Buys"(
                description = "the buy information",
                type = map {
                    SerialName("unit_price").."UnitPrice"(INTEGER, "the highest buy order price in coins")
                    "Quantity"(INTEGER, "the amount of items being bought")
                }
            )
            "sells"(
                description = "the sell information",
                type = map {
                    SerialName("unit_price").."UnitPrice"(INTEGER, "the lowest sell order price in coins")
                    "Quantity"(INTEGER, "the amount of items being sold")
                }
            )
        })
    }
    "/Currencies" {
        summary = "Returns information about currencies contained in the account wallet."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the currency's ID")
            "Name"(STRING, "the currency's name")
            "Description"(STRING, "a description of the currency")
            "Icon"(STRING, "the currency's icon")
            "Order"(INTEGER, "a number that can be used to sort the list of currencies")
        })
    }
    "/DailyCrafting" {
        summary = "Returns information about the items that can be crafted once per day."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the dailycrafting")
        })
    }
    "/Emotes" {
        summary = "Returns information about unlockable emotes."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(STRING, "the emote's ID")
            "Commands"(
                description = "the commands that may be used to trigger the emote",
                type = array(INTEGER)
            )
            SerialName("unlock_items").."UnlockItems"(
                description = "the IDs of the items that unlock the emote",
                type = array(INTEGER)
            )
        })
    }
    "/Files" {
        summary = "Returns commonly requested in-game assets."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(STRING, "the file identifier")
            "Icon"(STRING, "the URL to the image")
        })
    }
    "/Items" {
        summary = "Returns information about items in the game."
        cache = 1.hours

        fun INFIX_UPGRADES() = array(
            description = "",
            items = map {
                "Id"(INTEGER, "the itemstat id")
                "Attributes"(
                    description = "list of attribute bonuses",
                    type = map {
                        "Attribute"(STRING, "attribute this bonus applies to")
                        "Modifier"(INTEGER, "the modifier value")
                    }
                )
                optional.."Buff"(
                    description = "object containing an additional effect",
                    type = map {
                        SerialName("skill_id").."SkillId"(INTEGER, "the skill id of the effect")
                        optional.."Description"(STRING, "the effect's description")
                    }
                )
            }
        )

        fun INFUSION_SLOTS() = array(
            description = "",
            items = map {
                "Flags"(array(STRING), "infusion slot type of infusion upgrades")
                optional..SerialName("infusion_type").."ItemId"(INTEGER, "the infusion upgrade already in the armor piece") // TODO is this correct?
            }
        )

        supportedQueries(BY_ID, BY_IDS(all = false), BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the item's ID")
            "Name"(STRING, "the item's name")
            "Type"(STRING, "the item's type")
            SerialName("chat_link").."ChatLink"(STRING, "the chat link")
            optional.."Icon"(STRING, "the icon URL")
            optional.."Description"(STRING, "the item description")
            "Rarity"(STRING, "the item rarity")
            "Level"(INTEGER, "the required level")
            SerialName("vendor_value").."VendorValue"(INTEGER, "the value in coins when selling to a vendor")
            optional..SerialName("default_skin").."DefaultSkin"(INTEGER, "the default skin id")
            "Flags"(array(STRING), "flags applying to the item")
            SerialName("game_types").."GameTypes"(array(STRING), "the game types in which the item is usable")
            "Restrictions"(array(STRING), "restrictions applied to the item")
            optional..SerialName("upgrades_into").."UpgradesInto"(
                description = "lists what items this item can be upgraded into, and the method of upgrading",
                type = array(map {
                    "Upgrade"(STRING, "describes the method of upgrading")
                    SerialName("item_id").."ItemId"(INTEGER, "the item ID that results from performing the upgrade")
                })
            )
            optional..SerialName("upgrades_from").."UpgradesFrom"(
                description = "lists what items this item can be upgraded from, and the method of upgrading",
                type = array(map {
                    "Upgrade"(STRING, "describes the method of upgrading")
                    SerialName("item_id").."ItemId"(INTEGER, "the item ID that results from performing the upgrade")
                })
            )
            "details"(disambiguationBy = "type", interpretations = mapOf(
                "Armor" to map {
                    "Type"(STRING, "the armor slot type")
                    SerialName("weight_class").."WeightClass"(STRING, "the weight class")
                    "Defense"(INTEGER, "the defense value of the armor piece")
                    SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS(), "infusion slots of the armor piece")
                    SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADES(), "infix upgrade object")
                    optional..SerialName("suffix_item_id").."SuffixItemId"(INTEGER, "the suffix item id")
                    optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemId"(STRING, "the secondary suffix item id")
                    SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                },
                "Back" to map {
                    SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS(), "infusion slots of the back item")
                    SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADES(), "infix upgrade object")
                    optional..SerialName("suffix_item_id").."SuffixItemId"(INTEGER, "the suffix item id")
                    optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemId"(STRING, "the secondary suffix item id")
                    SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                },
                "Bag" to map {
                    "Size"(INTEGER, "the number of bag slots")
                    SerialName("no_sell_or_sort").."NoSellOrSort"(BOOLEAN, "whether the bag is invisible")
                },
                "Consumable" to map {
                    "Type"(STRING, "the consumable type")
                    optional.."Description"(STRING, "effect description for consumables applying an effect")
                    optional..SerialName("duration_ms").."DurationMs"(INTEGER, "effect duration in milliseconds")
                    optional..SerialName("unlock_type").."UnlockType"(STRING, "unlock type for unlock consumables")
                    optional..SerialName("color_id").."ColorId"(INTEGER, "the dye id for dye unlocks")
                    optional..SerialName("recipe_id").."RecipeId"(INTEGER, "the recipe id for recipe unlocks")
                    optional..SerialName("extra_recipe_ids").."ExtraRecipeIds"(array(INTEGER), "additional recipe ids for recipe unlocks")
                    optional..SerialName("guild_upgrade_id").."GuildUpgradeId"(array(INTEGER), "the guild upgrade id for the item")
                    optional..SerialName("apply_count").."ApplyCount"(INTEGER, "the number of stacks of the effect applied by this item")
                    optional.."Name"(STRING, "the effect type name of the consumable")
                    optional.."Icon"(STRING, "the icon of the effect")
                    optional.."Skin"(array(INTEGER), "a list of skin ids which this item unlocks")
                },
                "Container" to map {
                    "Type"(STRING, "the container type")
                },
                "Gathering" to map {
                    "Type"(STRING, "the tool type")
                },
                "Gizmo" to map {
                    "type"(STRING, "the gizmo type")
                    optional..SerialName("guild_upgrade_id").."GuildUpgradeId"(INTEGER, "the guild upgrade id for the item")
                    SerialName("vendor_ids").."VendorIds"(array(INTEGER), "the vendor ids")
                },
                "MiniPet" to map {
                    SerialName("minipet_id").."MinipetId"(INTEGER, "the miniature it unlocks")
                },
                "Tool" to map {
                    "Type"(STRING, "the tool type")
                    "Charges"(INTEGER, "the available charges")
                },
                "Trinket" to map {
                    "Type"(STRING, "the trinket type")
                    SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS(), "infusion slots of the trinket")
                    SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADES(), "infix upgrade object")
                    optional..SerialName("suffix_item_id").."SuffixItemId"(INTEGER, "the suffix item id")
                    optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemId"(STRING, "the secondary suffix item id")
                    SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                },
                "UpgradeComponent" to map {
                    "Type"(STRING, "the type of the upgrade component")
                    "Flags"(array(STRING), "the items that can be upgraded with the upgrade component")
                    SerialName("infusion_upgrade_flags").."InfusionUpgradeFlags"(array(STRING), "applicable infusion slot for infusion upgrades")
                    "Suffix"(STRING, "the suffix appended to the item name when the component is applied")
                    SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADES(), "infix upgrade object")
                    optional.."Bonuses"(array(STRING), "the bonuses from runes")
                },
                "Weapon" to map {
                    "Type"(STRING, "the weapon type")
                    SerialName("min_power").."MinPower"(INTEGER, "minimum weapon strength")
                    SerialName("max_power").."MaxPower"(INTEGER, "maximum weapon strength")
                    SerialName("damage_type").."DamageType"(STRING, "the damage type")
                    "Defense"(INTEGER, "the defense value of the weapon")
                    SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS(), "infusion slots of the weapon")
                    optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADES(), "infix upgrade object")
                    optional..SerialName("suffix_item_id").."SuffixItemId"(INTEGER, "the suffix item id")
                    optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemId"(STRING, "the secondary suffix item id")
                    optional..SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                }
            ))
        })
    }
    "/ItemStats" {
        summary = "Returns information about itemstats."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the stat set's ID")
            "Name"(STRING, "the name of the stat set")
            "Attributes"(
                description = "the list of attribute bonuses",
                type = array(map {
                    "Attribute"(STRING, "the name of the attribute")
                    "Multiplier"(DECIMAL, "the multiplier for that attribute")
                    "Value"(INTEGER, "the default value for that attribute")
                })
            )
        })
    }
    "/Legends" {
        summary = "Returns information about the Revenant legends."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(STRING, "the legend's ID")
            "Swap"(INTEGER, "the ID of the profession (swap Legend) skill")
            "Heal"(INTEGER, "the ID of the heal skill")
            "Elite"(INTEGER, "the ID of the elite skills")
            "Utilities"(
                description = "the IDs of the utility skills",
                type = array(INTEGER)
            )
        })
    }
    "/MapChests" {
        summary = "Returns information about the Hero's Choice Chests that can be acquired once per day."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the mapchest")
        })
    }
    "/Outfits" {
        summary = "Returns information about outfits."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the outfit's ID")
            "Name"(STRING, "the outfit's name")
            "Icon"(STRING, "the outfit's icon")
            SerialName("unlock_items").."unlockItems"(
                description = "the IDs of the items that unlock the outfit",
                type = array(INTEGER)
            )
        })
    }
    "/Races" {
        summary = "Returns information about the game's playable races."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(STRING, "the race's ID")
            "Name"(STRING, "the race's localized name")
            "Skills"(
                description = "an array of racial skill IDs",
                type = array(INTEGER)
            )
        })
    }
    "/Titles" {
        summary = "Returns information about the titles that are in the game."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the title")
            "Name"(STRING, "the display name of the title")
            deprecated..optional.."Achievement"(INTEGER, "the ID of the achievement that grants this title")
            optional.."Achievements"(array(INTEGER), "the IDs of the achievements that grant this title")
            optional..SerialName("ap_required").."APRequired"(INTEGER, "the amount of AP required to unlock this title")
        })
    }
    "/TokenInfo" {
        summary = "Returns information about the supplied API key."
        security(ACCOUNT)

        schema(
            V2_SCHEMA_CLASSIC to map {
                "Id"(STRING, "the API key that was requested")
                "Name"(STRING, "the name given to the API key by the account owner")
                "Permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
            },
            V2_SCHEMA_2019_05_22T00_00_00_000Z to map {
                "Id"(STRING, "the API key that was requested")
                "Name"(STRING, "the name given to the API key by the account owner")
                "Permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
                "Type"(STRING, "the type of the access token given")
                optional..SerialName("expires_at").."ExpiresAt"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken expires")
                optional..SerialName("issued_at").."IssuedAt"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken was created")
                optional.."URLs"(
                    description = "if the given subtoken is restricted to a list of URLs, contains an array of strings describing what endpoints are available to this token",
                    type = array(STRING)
                )
            }
        )
    }
    "/WorldBosses" {
        summary = "Returns information about the worldbosses that reward boss chests that can be opened once a day."
        cache = 1.hours

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the worldboss")
        })
    }
    "/Worlds" {
        summary = "Returns information about the available worlds (or servers)."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the world")
            "Name"(STRING, "the name of the world")
            "Population"(STRING, "the population level of the world")
        })
    }
    "/WvW/Objectives" {
        summary = "Returns information about the objectives in the World versus World game mode."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(STRING, "the ID of the objective")
            "Name"(STRING, "the name of the objective")
            "Type"(STRING, "the type of the objective")
            SerialName("sector_id").."SectorId"(INTEGER, "the map sector the objective can be found in")
            SerialName("map_id").."MapId"(INTEGER, "the ID of the map the objective can be found on")
            SerialName("map_type").."MapType"(STRING, "the type of the map the objective can be found on")
            "Coord"(
                description = "an array of three numbers representing the X, Y and Z coordinates of the objectives marker on the map",
                type = array(DECIMAL)
            )
            SerialName("label_coord").."LabelCoord"(
                description = "an array of two numbers representing the X and Y coordinates of the sector centroid",
                type = array(DECIMAL)
            )
            "marker"(STRING, "the icon link")
            SerialName("chat_link").."ChatLink"(STRING, "the chat code for the objective")
            optional..SerialName("upgrade_id").."UpgradeId"(INTEGER, "the ID of the upgrades available for the objective")
        })
    }
    "/WvW/Ranks" {
        summary = "Returns information about the achievable ranks in the World versus World game mode."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the rank")
            "Title"(STRING, "the title of the rank")
            SerialName("min_level").."MinLevel"(INTEGER, "the WvW level required to unlock this rank")
        })
    }
    "/WvW/Upgrades" {
        summary = "Returns information about available upgrades for objectives in the World versus World game mode."
        cache = 1.hours
        isLocalized = true

        supportedQueries(BY_ID, BY_IDS, BY_PAGE)
        schema(map {
            "Id"(INTEGER, "the ID of the upg")
            "Tiers"(
                description = "",
                type = map {
                    "Name"(STRING, "the name of the upgrade tier")
                    SerialName("yaks_required").."YaksRequired"(INTEGER, "the amount of dolyaks required to reach this upgrade tier")
                    "Upgrades"(
                        description = "",
                        type = map {
                            "Name"(STRING, "the name of the upgrade")
                            "Description"(STRING, "the description for the upgrade")
                            "Icon"(STRING, "the icon link")
                        }
                    )
                }
            )
        })
    }
}