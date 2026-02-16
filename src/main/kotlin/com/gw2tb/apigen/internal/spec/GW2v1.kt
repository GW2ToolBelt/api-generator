/*
 * Copyright (c) 2019-2026 Leon Linhart
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
@file:Suppress("FunctionName", "LocalVariableName")
package com.gw2tb.apigen.internal.spec

import com.gw2tb.apigen.model.APIv1Endpoint.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.ir.LowLevelApiGenApi

@OptIn(LowLevelApiGenApi::class)
@Suppress("FunctionName")
internal val GW2v1 = GW2APISpecV1 {
    val GuildDetails = record(name = "GuildDetails", description = "Information about events.") {
        "GuildId"(GUILD_ID, "the guild's ID")
        "GuildName"(STRING, "the guild's name")
        "Tag"(STRING, "the guild's tag")
        "Emblem"(
            description = "the guild's emblem",
            type = record(name = "Emblem", description = "Information about a guild emblem.") {
                "BackgroundId"(INTEGER, "the background's ID")
                "ForegroundId"(INTEGER, "the foreground's ID")
                "Flags"(array(STRING), "the manipulations applied to the emblem")
                "BackgroundColorId"(INTEGER, "the background color's ID")
                "ForegroundPrimaryColorId"(INTEGER, "the foreground primary color's ID")
                "ForegroundSecondaryColorId"(INTEGER, "the foreground secondary color's ID")
            }
        )
    }

    V1_BUILD(summary = "Returns the current build ID.") {
        schema(record(name = "Build", description = "Information about the current game build.") {
            "BuildId"(BUILD_ID, "the current build ID")
        })
    }
    V1_COLORS(summary = "Returns information about all dye colors in the game.") {
        schema(record(name = "Colors", description = "Information about the available dye colors.") {
            "Colors"(
                description = "the colors",
                type = map(
                    keys = COLOR_ID,
                    values = record(name = "Color", description = "Information about a dye color.") {
                        val APPEARANCE = record(name = "Appearance", description = "Information about the appearance of the color.") {
                            "Brightness"(INTEGER, "the brightness")
                            "Contrast"(DECIMAL, "the contrast")
                            "Hue"(INTEGER, "the hue in HSL colorspace")
                            "Saturation"(DECIMAL, "the saturation in HSL colorspace")
                            "Lightness"(DECIMAL, "the lightness in HSL colorspace")
                            "Rgb"(array(INTEGER), "a list containing precalculated RGB values")
                        }

                        localized.."Name"(STRING, "the color's name")
                        "BaseRgb"(array(INTEGER), "the base RGB values")
                        "Cloth"(APPEARANCE, "detailed information on its appearance when applied on cloth armor")
                        "Leather"(APPEARANCE, "detailed information on its appearance when applied on leather armor")
                        "Metal"(APPEARANCE, "detailed information on its appearance when applied on metal armor")
                        optional.."Default"(APPEARANCE, "detailed information on its default appearance")
                        optional.."Fur"(APPEARANCE, "detailed information on its appearance when applied on fur armor")
                        optional.."Item"(ITEM_ID, "the ID of the dye item")
                        "Categories"(array(STRING), "the categories of the color")
                    }
                )
            )
        })
    }
    V1_CONTINENTS(summary = "Returns information about continents.") {
        schema(record(name = "Continents", description = "Information about continents.") {
            "Continents"(
                description = "the continents",
                type = map(
                    keys = CONTINENT_ID,
                    values = record(name = "Continent", description = "Information about a continent.") {
                        localized.."Name"(STRING, "the continent's localized name")
                        "ContinentDims"(array(INTEGER), "the width and height of the continent")
                        "MinZoom"(INTEGER, "the minimal zoom level for use with the map tile service")
                        "MaxZoom"(INTEGER, "the maximum zoom level for use with the map tile service")
                        "Floors"(array(FLOOR_ID), "the IDs of the continent's floors")
                    }
                )
            )
        })
    }
    V1_EVENT_DETAILS(summary = "Returns information about the events in the game.") {
        schema(record(name = "EventDetails", description = "Information about events.") {
            "Events"(
                description = "the events",
                type = map(
                    keys = EVENT_ID,
                    values = record(name = "EventDetails", description = "Information about an event.") {
                        localized.."Name"(STRING, "the event's name")
                        "Level"(INTEGER, "the event's level")
                        "MapId"(MAP_ID, "the ID of the map where the event takes place")
                        "Flags"(array(STRING), "additional information about the event (e.g. group_event, map_wide, meta_event, dungeon_event)")
                        "Location"(
                            description = "the event's location",
                            type = conditional(name = "Location", description = "Information about an event's location.", sharedConfigure = {
                                "Type"(STRING, "the type of location")
                            }) {
                                "cylinder"(record(name = "Cylinder", description = "Information about a cylindrical event location.") {
                                    "Center"(array(DECIMAL), "the coordinates (x,y,z) of the cylinder's center (in map coordinates)")
                                    "Height"(DECIMAL, "the cylinder's height")
                                    "Radius"(DECIMAL, "the cylinder's radius")
                                    "Rotation"(DECIMAL, "the cylinder's rotation")
                                })
                                "poly"(record(name = "Poly", description = "Information about a polygonal event location.") {
                                    "Center"(array(DECIMAL), "the coordinates (x,y,z) of the polygon's center (in map coordinates)")
                                    "ZRange"(array(DECIMAL), "the polygon's z-range")
                                    "Points"(array(array(DECIMAL)), "the polygon's points")
                                })
                                "sphere"(record(name = "Sphere", description = "Information about a spherical event location.") {
                                    "Center"(array(DECIMAL), "the coordinates (x,y,z) of the sphere's center (in map coordinates)")
                                    "Radius"(DECIMAL, "the sphere's radius")
                                    "Rotation"(DECIMAL, "the sphere's rotation")
                                })
                            }
                        )
                    }
                )
            )
        })
    }
    V1_FILES(summary = "Returns commonly requested in-game assets.") {
        schema(map(
            description = "the available in-game assets",
            keys = STRING,
            values = record(name = "File", description = "Information about an in-game asset.") {
                "FileId"(INTEGER, "the file identifier")
                "Signature"(STRING, "the file signature")
            }
        ))
    }
    V1_GUILD_DETAILS(
        querySuffix = "ByID",
        summary = "Returns information about a guild."
    ) {
        queryParameter("GuildId", STRING, "the ID of the guild")

        schema(GuildDetails)
    }
    V1_GUILD_DETAILS(
        querySuffix = "ByName",
        summary = "Returns information about a guild."
    ) {
        queryParameter("GuildName", STRING, "the name of the guild")

        schema(GuildDetails)
    }
    V1_ITEM_DETAILS(summary = "Returns information an item.") {
        queryParameter("ItemId", ITEM_ID, "the ID of the item")

        schema(conditional(
            name = "ItemDetails",
            description = "Information about an item.",
            interpretationInNestedProperty = true,
            sharedConfigure = {
                "ItemId"(ITEM_ID, "the item's ID")
                localized.."Name"(STRING, "the item's name")
                "Type"(STRING, "the item's type")
                "IconFileId"(STRING, "the icon's file ID to be used with the render service")
                "IconFileSignature"(STRING, "the icon's file signature to be used with the render service")
                optional..localized.."Description"(STRING, "the item's description")
                "Rarity"(STRING, "the item's rarity")
                "Level"(INTEGER, "the level required to use the item")
                "VendorValue"(INTEGER, "the value in coins when selling the item to a vendor")
                optional.."DefaultSkin"(INTEGER, "the ID of the item's default skin")
                "Flags"(array(STRING), "flags applying to the item")
                "GameTypes"(array(STRING), "the game types in which the item is usable")
                "Restrictions"(array(STRING), "restrictions applied to the item")
                optional.."UpgradeRecipes"(
                    description = "lists what items this item can be upgraded from and into, and the method of upgrading",
                    type = array(record(name = "Upgrade", description = "Information about an item's upgrade.") {
                        "Type"(STRING, "describes the method of upgrading")
                        optional.."From"(ITEM_ID, "the ID of the item that is upgraded into the item")
                        optional.."Into"(ITEM_ID, "the ID of the item that results from performing the upgrade")
                    })
                )
            }
        ) {
            val INFIX_UPGRADE = record(name = "InfixUpgrade", description = "Information about an item's infix upgrade.") {
                "Id"(ITEMSTAT_ID, "the itemstat ID")
                "Attributes"(
                    description = "the list of attribute bonuses granted by this item",
                    type = array(record(name = "Attribute", description = "Information about an infix upgrade's attribute bonuses.") {
                        "Attribute"(STRING, "the attribute this bonus applies to")
                        "Modifier"(INTEGER, "the modifier value")
                    })
                )
                optional.."Buff"(
                    description = "object containing an additional effect",
                    type = record(name = "Buff", description = "Information about an infix upgrade's buffs.") {
                        "SkillId"(SKILL_ID, "the skill ID of the effect")
                        optional.."Description"(STRING, "the effect's description")
                    }
                )
            }

            val INFUSION_SLOTS = array(record(name = "InfusionSlot", description = "Information about an items infusion slot.") {
                "Flags"(array(STRING), "infusion slot type of infusion upgrades")
                optional.."ItemId"(ITEM_ID, "the infusion upgrade in the armor piece")
            })

            +record(name = "Armor", description = "Additional information about an armor item.") {
                "Type"(STRING, "the armor slot type")
                "WeightClass"(STRING, "the weight class")
                "Defense"(INTEGER, "the defense value of the armor piece")
                "InfusionSlots"(INFUSION_SLOTS, "infusion slots of the armor piece")
                optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..lenient.."SuffixItemId"(ITEM_ID, "the suffix item ID")
                optional..lenient.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                optional.."StatChoices"(array(ITEMSTAT_ID), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
            }
            +record(name = "Back", description = "Additional information about a backpiece.") {
                "InfusionSlots"(INFUSION_SLOTS, "infusion slots of the back item")
                optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..lenient.."SuffixItemId"(ITEM_ID, "the suffix item ID")
                optional..lenient.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                optional.."StatChoices"(array(ITEMSTAT_ID), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
            }
            +record(name = "Bag", description = "Additional information about a bag.") {
                "Size"(INTEGER, "the number of bag slots")
                "NoSellOrSort"(BOOLEAN, "whether the bag is invisible")
            }
            +record(name = "Consumable", description = "Additional information about a consumable item.") {
                "Type"(STRING, "the consumable type")
                optional.."Description"(STRING, "effect description for consumables applying an effect")
                optional.."DurationMs"(INTEGER, "effect duration in milliseconds")
                optional.."UnlockType"(STRING, "unlock type for unlock consumables")
                optional.."ColorId"(COLOR_ID, "the dye ID for dye unlocks")
                optional.."RecipeId"(RECIPE_ID, "the recipe ID for recipe unlocks")
                optional.."ExtraRecipeIds"(array(RECIPE_ID), "additional recipe IDs for recipe unlocks")
                optional.."GuildUpgradeId"(GUILD_UPGRADE_ID, "the guild upgrade ID for the item")
                optional.."ApplyCount"(INTEGER, "the number of stacks of the effect applied by this item")
                optional.."Name"(STRING, "the effect type name of the consumable")
                optional.."Icon"(STRING, "the icon of the effect")
                optional.."Skins"(array(SKIN_ID), "a list of skin ids which this item unlocks")
            }
            +record(name = "Container", description = "Additional information about a container.") {
                "Type"(STRING, "the container type")
            }
            "Gathering"(record(name = "GatheringTool", description = "Additional information about a gathering tool.") {
                "Type"(STRING, "the tool type")
            })
            +record(name = "Gizmo", description = "Additional information about a gizmo.") {
                "Type"(STRING, "the gizmo type")
                optional.."GuildUpgradeId"(GUILD_UPGRADE_ID, "the guild upgrade ID for the item")
                optional.."VendorIds"(array(INTEGER), "the vendor IDs")
            }
            +record(name = "MiniPet", description = "Additional information about a mini unlock item.") {
                "MinipetId"(MINI_ID, "the miniature it unlocks")
            }
            "Tool"(record(name = "Tool", description = "Additional information about a tool.") {
                "Type"(STRING, "the tool type")
                "Charges"(INTEGER, "the available charges")
            })
            +record(name = "Trinket", description = "Additional information about a trinket.") {
                "Type"(STRING, "the trinket type")
                "InfusionSlots"(INFUSION_SLOTS, "infusion slots of the trinket")
                optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..lenient.."SuffixItemId"(ITEM_ID, "the suffix item ID")
                optional..lenient.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                optional.."StatChoices"(array(ITEMSTAT_ID), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
            }
            +record(name = "UpgradeComponent", description = "Additional information about an upgrade component.") {
                "Type"(STRING, "the type of the upgrade component")
                "Flags"(array(STRING), "the items that can be upgraded with the upgrade component")
                "InfusionUpgradeFlags"(array(STRING), "applicable infusion slot for infusion upgrades")
                "Suffix"(STRING, "the suffix appended to the item name when the component is applied")
                optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional.."Bonuses"(array(STRING), "the bonuses from runes")
                optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
            }
            +record(name = "Weapon", description = "Additional information about a weapon.") {
                "Type"(STRING, "the weapon type")
                "MinPower"(INTEGER, "minimum weapon strength")
                "MaxPower"(INTEGER, "maximum weapon strength")
                "DamageType"(STRING, "the damage type")
                "Defense"(INTEGER, "the defense value of the weapon")
                "InfusionSlots"(INFUSION_SLOTS, "infusion slots of the weapon")
                optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..lenient.."SuffixItemId"(ITEM_ID, "the suffix item ID")
                optional..lenient.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                optional.."StatChoices"(array(ITEMSTAT_ID), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
            }
        })
    }
    V1_ITEMS(summary = "Returns the IDs of the available items.") {
        schema(record(name = "ItemIds", description = "Information about the available items.") {
            "Items"(array(ITEM_ID), "the IDs of the available items")
        })
    }
    V1_MAP_FLOOR(summary = "Returns information about a map floor.") {
        queryParameter("ContinentId", CONTINENT_ID, "the ID of the continent")
        queryParameter("FloorId", FLOOR_ID, "the ID of the floor", key = "floor")

        schema(record(name = "MapFloor", description = "Information about a map floor.") {
            "TextureDims"(array(INTEGER), "the width and height of the texture")
            optional.."ClampedView"(array(array(INTEGER)), "a rectangle of downloadable textures (Every tile coordinate outside this rectangle is not available on the tile server.)")
            "Regions"(
                description = "the floor's regions",
                type = map(
                    keys = REGION_ID,
                    values = record(name = "Region", description = "Information about a region.") {
                        localized.."Name"(STRING, "the region's localized name")
                        "LabelCoord"(array(DECIMAL), "the coordinate of the region's label")
                        "ContinentRect"(array(array(INTEGER)), "the dimensions of the region, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                        "Maps"(
                            description = "the region's maps",
                            type = map(
                                keys = MAP_ID,
                                values = record(name = "Map", description = "Information about a map.") {
                                    localized.."Name"(STRING, "the map's localized name")
                                    "MinLevel"(INTEGER, "the minimum level of the map")
                                    "MaxLevel"(INTEGER, "the maximum level of the map")
                                    "DefaultFloor"(INTEGER, "the ID of the map's default floor")
                                    "MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
                                    "ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                                    optional.."LabelCoord"(array(DECIMAL), "the coordinate of the map's label")
                                    "PointsOfInterest"(
                                        description = "the points of interest on the floor (i.e. landmarks, vistas and waypoints)",
                                        type = array(record(name = "PointOfInterest", description = "Information about a point of interest (i.e. a landmark, vista or waypoint).") {
                                            "PoiId"(INTEGER, "the PoI's ID")
                                            optional..localized.."Name"(STRING, "the PoI's localized name")
                                            "Type"(STRING, "the type of the PoI (landmark, vista, or waypoint)")
                                            "Floor"(INTEGER, "the PoI's floor")
                                            "Coord"(array(DECIMAL), "the PoI's coordinates")
                                            optional.."Marker"(
                                                description = "the PoI's marker icon",
                                                type = record(name = "Marker", "Information about a PoI's marker icon.") {
                                                    "FileId"(INTEGER, "the icon's file ID to be used with the render service")
                                                    "Signature"(STRING, "the icon's file signature to be used with the render service")
                                                }
                                            )
                                        })
                                    )
                                    optional.."GodShrines"(
                                        description = "the god shrines on the floor",
                                        type = array(record(name = "GodShrine", description = "Information about a god shrine.") {
                                            "Id"(INTEGER, "the god shrine's ID")
                                            localized.."Name"(STRING, "the god shrine's localized name")
                                            optional..localized.."NameContested"(STRING, "the god shrine's localized name (when contested)")
                                            optional.."Icon"(STRING, "the god shrine's icon")
                                            optional.."IconContested"(STRING, "the god shrine's icon (when contested)")
                                            "PoiId"(INTEGER, "the god shrine's PoI ID")
                                            "Coord"(array(DECIMAL), "the god shrine's coordinates")
                                        })
                                    )
                                    "Tasks"(
                                        description = "the tasks on the floor",
                                        type = array(record(name = "Task", description = "Information about a task.") {
                                            "TaskId"(INTEGER, "the task's ID")
                                            localized.."Objective"(STRING, "the adventure's localized objective")
                                            "Level"(INTEGER, "the task's level")
                                            "Coord"(array(DECIMAL), "the task's coordinates")
                                            "Bounds"(array(array(DECIMAL)), "the task's bounds")
                                        })
                                    )
                                    "SkillChallenges"(
                                        description = "the skill challenges on the floor",
                                        type = array(record(name = "SkillChallenge", description = "Information about a skill challenge.") {
                                            SerialName("idx").."Id"(INTEGER, "the skill challenge's ID (unique within an expansion)")
                                            "Expac"(INTEGER, "the skill challenge's expansion ID")
                                            "Coord"(array(DECIMAL), "the skill challenge's coordinates")
                                        })
                                    )
                                    "Sectors"(
                                        description = "the sectors on the floor",
                                        type = array(record(name = "Sector", description = "Information about a sector.") {
                                            "SectorId"(INTEGER, "the sector's ID")
                                            optional..localized.."Name"(STRING, "the sector's localized name")
                                            "Level"(INTEGER, "the sector's level")
                                            "Coord"(array(DECIMAL), "the sector's coordinates")
                                            "Bounds"(array(array(DECIMAL)), "the sector's bounds")
                                        })
                                    )
                                    "Adventures"(
                                        description = "the adventures on the floor",
                                        type = array(record(name = "Adventure", description = "Information about an adventure.") {
                                            "Guid"(STRING, "the adventure's ID")
                                            localized.."Name"(STRING, "the adventure's localized name")
                                            "Coord"(array(DECIMAL), "the adventure's coordinates")
                                            "Leaderboard"(
                                                description = "the adventure's leaderboard",
                                                type = record(name = "Leaderboard", description = "Information about an adventure's leaderboard.") {
                                                    "Guid"(STRING, "the leaderboard's ID")
                                                    localized.."Title"(STRING, "the leaderboard's localized title")
                                                    localized.."Description"(STRING, "the leaderboard's localized description")
                                                }
                                            )
                                        })
                                    )
                                    SerialName("training_points").."MasteryPoints"(
                                        description = "the mastery points on the floor",
                                        type = array(record(name = "MasteryPoint", description = "Information about a mastery point.") {
                                            "Id"(INTEGER, "the mastery point's ID")
                                            localized.."Name"(STRING, "the mastery point's localized name")
                                            localized.."Description"(STRING, "the mastery point's localized description")
                                            "Type"(STRING, "the mastery region")
                                            "Coord"(array(DECIMAL), "the mastery point's coordinates")
                                        })
                                    )
                                }
                            )
                        )
                    }
                )
            )
        })
    }
    V1_MAP_NAMES(summary = "Returns information about maps.") {
        schema(array(
            description = "the available maps",
            items = record(name = "MapName", description = "Information about a map.") {
                "Id"(MAP_ID, "the map's ID")
                localized.."Name"(STRING, "the map's name")
            }
        ))
    }
    V1_MAPS(summary = "Returns information about maps.") {
        schema(record(name = "Maps", description = "Information about maps.") {
            "Maps"(
                description = "the maps",
                type = map(
                    keys = MAP_ID,
                    values = record(name = "Map", description = "Information about a map.") {
                        localized.."MapName"(STRING, "the map's localized name")
                        "Type"(STRING, "the type of map")
                        "MinLevel"(INTEGER, "the minimum level of the map")
                        "MaxLevel"(INTEGER, "the maximum level of the map")
                        "DefaultFloor"(FLOOR_ID, "the ID of the map's default floor")
                        "Floors"(array(INTEGER), "the IDs of the floors available on the map")
                        "RegionId"(REGION_ID, "the ID of the region the map belongs to")
                        optional..localized.."RegionName"(STRING, "the name of the region the map belongs to")
                        "ContinentId"(CONTINENT_ID, "the ID of the continent the map belongs to")
                        optional..localized.."ContinentName"(STRING, "the name of the continent the map belongs to")
                        "MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
                        "ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                    }
                )
            )
        })
    }
    V1_RECIPE_DETAILS(summary = "Returns information about the recipes in the game.") {
        queryParameter("RecipeId", RECIPE_ID, "the recipe's ID")

        schema(record(name = "RecipeDetails", description = "Information about a crafting recipe.") {
            "RecipeId"(RECIPE_ID, "the recipe's ID")
            "Type"(STRING, "the recipe's type")
            "OutputItemId"(ITEM_ID, "the ID of the produced item")
            "OutputItemCount"(INTEGER, "the amount of items produced")
            SerialName("time_to_craft_ms").."CraftTimeMillis"(INTEGER, "the time in milliseconds it takes to craft the item")
            "Disciplines"(array(STRING), "the crafting disciplines that can use the recipe")
            "MinRating"(INTEGER, "the minimum rating required to use the recipe")
            "Flags"(array(STRING), "the flags applying to the recipe")
            "Ingredients"(
                description = "the recipe's ingredients",
                type = array(record(name = "Ingredient", description = "Information about a recipe ingredient.") {
                    "ItemId"(ITEM_ID, "the ingredient's item ID")
                    "Count"(INTEGER, "the quantity of this ingredient")
                })
            )
        })
    }
    V1_RECIPES(summary = "Returns the IDs of the available recipes.") {
        schema(record(name = "RecipeIds", description = "Information about the available recipes.") {
            "Recipes"(array(RECIPE_ID), "the IDs of the available recipes")
        })
    }
    V1_SKIN_DETAILS(
        summary = "Returns information about the skins in the game.",
        cache = 1.hours
    ) {
        queryParameter("SkinId", SKIN_ID, "the the skin's ID")

        schema(conditional("SkinDetails", "Information about a skin.", interpretationInNestedProperty = true, sharedConfigure = {
            "SkinId"(SKIN_ID, "the skin's ID")
            localized.."Name"(STRING, "the skin's localized name")
            "Type"(STRING, "the skin's type")
            "Rarity"(STRING, "the skin's rarity")
            "Flags"(array(STRING), "additional skin flags (ShowInWardrobe, NoCost, HideIfLocked, OverrideRarity)")
            "Restrictions"(array(STRING), "the IDs of the races that can use this skin, or empty if it can be used by any race")
            "IconFileId"(STRING, "the icon's file ID to be used with the render service")
            "IconFileSignature"(STRING, "the icon's file signature to be used with the render service")
        }) {
            +record(name = "Armor", description = "Additional information about an armor skin.") {
                SerialName("type").."ArmorType"(STRING, "the skin's armor slot")
                "WeightClass"(STRING, "the skin's armor weight")
                "DyeSlots"(
                    description = "the skin's dye slots",
                    type = record(name = "DyeSlots", description = "Information about a skin's sye slots.") {
                        val DYE_SLOT = record(name = "DyeSlot", description = "Information about a dye slot.") {
                            "ColorId"(COLOR_ID, "the default color's ID")
                            "Material"(STRING, "the material type")
                        }

                        fun DYE_SLOTS() = array(DYE_SLOT, nullableItems = true)

                        "AsuraMale"(DYE_SLOTS(), "the dye slot overrides for asuarn male characters")
                        "AsuraFemale"(DYE_SLOTS(), "the dye slot overrides for asuarn female characters")
                        "CharrMale"(DYE_SLOTS(), "the dye slot overrides for charr male characters")
                        "CharrFemale"(DYE_SLOTS(), "the dye slot overrides for charr female characters")
                        "HumanMale"(DYE_SLOTS(), "the dye slot overrides for human male characters")
                        "HumanFemale"(DYE_SLOTS(), "the dye slot overrides for human female characters")
                        "NornMale"(DYE_SLOTS(), "the dye slot overrides for norn male characters")
                        "NornFemale"(DYE_SLOTS(), "the dye slot overrides for norn female characters")
                        "SylvariMale"(DYE_SLOTS(), "the dye slot overrides for sylvari male characters")
                        "SylvariFemale"(DYE_SLOTS(), "the dye slot overrides for sylvari female characters")
                    }
                )
            }
            +record(name = "Back", description = "Additional information about a backpack skin.") {}
            +record(name = "Gathering", description = "Additional information about a gathering tool skin.") {}
            +record(name = "Weapon", description = "Additional information about a gathering tool skin.") {
                "WeaponType"(STRING, "the skin's weapon type")
                "DamageType"(STRING, "the skin's damage type")
            }
        })
    }
    V1_SKINS(summary = "Returns the IDs of the available skins.") {
        schema(record(name = "SkinIds", description = "Information about the available skins.") {
            "Skins"(array(SKIN_ID), "the IDs of the available skins")
        })
    }
    V1_WORLD_NAMES(summary = "Returns information about the available worlds (or servers).") {
        schema(array(
            description = "the available worlds",
            items = record(name = "WorldName", description = "Information about an available world (or server).") {
                "Id"(WORLD_ID, "the ID of the world")
                localized.."Name"(STRING, "the name of the world")
            }
        ))
    }
    V1_WVW_MATCH_DETAILS(summary = "Returns detailed information about a WvW match.") {
        queryParameter("MatchId", WVW_MATCH_ID, "the ID of the match")

        schema(record(name = "WvwMatchDetails", description = "Information about a WvW match.") {
            "MatchId"(WVW_MATCH_ID, "the match's ID")
            "Scores"(array(INTEGER), "the total scores")
            "Maps"(
                description = "the map information",
                type = array(record(name = "Map", description = "Map-specific information about scores.") {
                    "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                    "Scores"(array(INTEGER), "the scores")
                    "Objectives"(
                        description = "the map's objectives",
                        type = array(record(name = "Objective", description = "Information about an objective.") {
                            "Id"(INTEGER, "the objective's ID")
                            "Owner"(STRING, "the objective's owner (i.e. \"Red\", \"Green\", \"Blue\", or \"Neutral\")")
                            optional.."OwnerGuild"(STRING, "the guild ID of the guild that currently has claimed this objective")
                        })
                    )
                    "Bonuses"(
                        description = "the bonuses granted by this map",
                        type = array(record(name = "Bonus", description = "Information about a bonus.") {
                            "Type"(STRING, "the type of the bonus")
                            "Owner"(STRING, "the owner of the bonus (i.e. \"Red\", \"Green\", \"Blue\")")
                        })
                    )
                })
            )
        })
    }
    V1_WVW_MATCHES(summary = "Returns information about WvW matches.") {
        schema(record(name = "WvwMatches", description = "Information about WvW matches.") {
            SerialName("wvw_matches").."WvwMatches"(
                description = "the matches",
                type = array(record(name = "WvwMatch", description = "Information about a WvW match.") {
                    "WvwMatchId"(WVW_MATCH_ID, "the match's ID")
                    "RedWorldId"(WORLD_ID, "the ID of the red team's primary server")
                    "BlueWorldId"(WORLD_ID, "the ID of the blue team's primary server")
                    "GreenWorldId"(WORLD_ID, "the ID of the green team's primary server")
                    "StartTime"(TIMESTAMP, "the ISO-8601 standard timestamp of when the match's start")
                    "EndTime"(TIMESTAMP, "the ISO-8601 standard timestamp of when the match's end")
                })
            )
        })
    }
    V1_WVW_OBJECTIVE_NAMES(summary = "Returns information about the available WvW objectives.") {
        schema(array(
            description = "the available objectives",
            items = record(name = "ObjectiveName", description = "Information about a WvW objective.") {
                "Id"(WVW_OBJECTIVE_ID, "the ID of the objective")
                localized.."Name"(STRING, "the objective's localized name")
            }
        ))
    }
}
