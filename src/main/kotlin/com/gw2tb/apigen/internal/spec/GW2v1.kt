/*
 * Copyright (c) 2019-2021 Leon Linhart
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

import com.gw2tb.apigen.internal.dsl.*
import kotlin.time.*

internal val GW2v1 = GW2APIVersion({ APIVersionBuilder.V1() }) {
    "/Build" {
        summary = "Returns the current build ID."

        schema(record(name = "Build", description = "Information about the current game build.") {
            SerialName("build_id").."BuildID"(INTEGER, "the current build ID")
        })
    }
    "/Colors" {
        summary = "Returns information about all dye colors in the game."

        schema(record(name = "Colors", description = "Information about the available dye colors.") {
            "Colors"(
                description = "the colors",
                type = map(
                    keys = INTEGER,
                    values = record(name = "Color", description = "Information about a dye color.") {
                        val APPEARANCE = record(name = "Appearance", description = "Information about the appearance of the color.") {
                            "Brightness"(INTEGER, "the brightness")
                            "Contrast"(DECIMAL, "the contrast")
                            "Hue"(INTEGER, "the hue in HSL colorspace")
                            "Saturation"(DECIMAL, "the saturation in HSL colorspace")
                            "Lightness"(DECIMAL, "the lightness in HSL colorspace")
                            "RGB"(array(INTEGER), "a list containing precalculated RGB values")
                        }

                        localized.."Name"(STRING, "the color's name")
                        SerialName("base_rgb").."BaseRGB"(array(INTEGER), "the base RGB values")
                        "Cloth"(APPEARANCE, "detailed information on its appearance when applied on cloth armor")
                        "Leather"(APPEARANCE, "detailed information on its appearance when applied on leather armor")
                        "Metal"(APPEARANCE, "detailed information on its appearance when applied on metal armor")
                        optional.."Default"(APPEARANCE, "detailed information on its default appearance")
                        optional.."Fur"(APPEARANCE, "detailed information on its appearance when applied on fur armor")
                        optional.."Item"(INTEGER, "the ID of the dye item")
                        "Categories"(array(STRING), "the categories of the color")
                    }
                )
            )
        })
    }
    "/Continents" {
        summary = "Returns information about continents."

        schema(record(name = "Continents", description = "Information about continents.") {
            "Continents"(
                description = "the continents",
                type = map(
                    keys = INTEGER,
                    values = record(name = "Map", description = "Information about a continent.") {
                        localized.."Name"(STRING, "the continent's localized name")
                        SerialName("continent_dims").."ContinentDims"(array(INTEGER), "the width and height of the continent")
                        SerialName("min_zoom").."MinZoom"(INTEGER, "the minimal zoom level for use with the map tile service")
                        SerialName("max_zoom").."MaxZoom"(INTEGER, "the maximum zoom level for use with the map tile service")
                        "Floors"(array(INTEGER), "the IDs of the continent's floors")
                    }
                )
            )
        })
    }
    "/event_details"(endpoint = "/EventDetails") {
        summary = "Returns information about the events in the game."

        schema(record(name = "EventDetails", description = "Information about events.") {
            "Events"(
                description = "the events",
                type = map(
                    keys = STRING,
                    values = record(name = "EventDetails", description = "Information about an event.") {
                        localized.."Name"(STRING, "the event's name")
                        "Level"(INTEGER, "the event's level")
                        SerialName("map_id").."MapID"(INTEGER, "the ID of the map where the event takes place")
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
                                    SerialName("z_range").."ZRange"(array(DECIMAL), "the polygon's z-range")
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
    "/Files" {
        summary = "Returns commonly requested in-game assets."

        schema(map(
            description = "the available in-game assets",
            keys = STRING,
            values = record(name = "File", description = "Information about an in-game asset.") {
                SerialName("file_id").."FileID"(INTEGER, "the file identifier")
                "Signature"(STRING, "the file signature")
            }
        ))
    }

    val GuildDetails = record(name = "GuildDetails", description = "Information about events.", endpoint = "/GuildDetails") {
        SerialName("guild_id").."GuildID"(STRING, "the guild's ID")
        SerialName("guild_name").."GuildName"(STRING, "the guild's name")
        "Tag"(STRING, "the guild's tag")
        "Emblem"(
            description = "the guild's emblem",
            type = record(name = "Emblem", description = "Information about a guild emblem.") {
                SerialName("background_id").."BackgroundID"(STRING, "the background's ID")
                SerialName("foreground_id").."ForegroundID"(STRING, "the foreground's ID")
                "Flags"(array(STRING), "the manipulations applied to the emblem")
                SerialName("background_color_id").."BackgroundColorID"(STRING, "the background color's ID")
                SerialName("foreground_primary_color_id").."ForegroundPrimaryColorID"(STRING, "the foreground primary color's ID")
                SerialName("foreground_secondary_color_id").."ForegroundSecondaryColorID"(STRING, "the foreground secondary color's ID")
            }
        )
    }

    "/guild_details"(endpoint = "/GuildDetails") {
        summary = "Returns information about a guild."
        querySuffix = "ByID"

        queryParameter("GuildID", STRING, "the ID of the guild", key = "guild_id")
        schema(GuildDetails)
    }
    "/guild_details"(endpoint = "/GuildDetails") {
        summary = "Returns information about a guild."
        querySuffix = "ByName"

        queryParameter("GuildName", STRING, "the name of the guild", key = "guild_name")
        schema(GuildDetails)
    }
    "/item_details"(endpoint = "/ItemDetails") {
        summary = "Returns information an item."

        queryParameter("ItemID", STRING, "the ID of the item", key = "item_id")
        schema(conditional(
            name = "ItemDetails",
            description = "Information about an item.",
            interpretationInNestedProperty = true,
            sharedConfigure = {
                SerialName("item_id").."ItemID"(INTEGER, "the item's ID")
                localized.."Name"(STRING, "the item's name")
                "Type"(STRING, "the item's type")
                SerialName("icon_file_id").."IconFileID"(STRING, "the icon's file ID to be used with the render service")
                SerialName("icon_file_signature").."IconFileSignature"(STRING, "the icon's file signature to be used with the render service")
                optional..localized.."Description"(STRING, "the item's description")
                "Rarity"(STRING, "the item's rarity")
                "Level"(INTEGER, "the level required to use the item")
                SerialName("vendor_value").."VendorValue"(INTEGER, "the value in coins when selling the item to a vendor")
                optional..SerialName("default_skin").."DefaultSkin"(INTEGER, "the ID of the item's default skin")
                "Flags"(array(STRING), "flags applying to the item")
                SerialName("game_types").."GameTypes"(array(STRING), "the game types in which the item is usable")
                "Restrictions"(array(STRING), "restrictions applied to the item")
            }
        ) {
            val INFIX_UPGRADE = record(name = "InfixUpgrade", description = "Information about an item's infix upgrade.") {
                CamelCase("id").."ID"(INTEGER, "the itemstat ID")
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
                        SerialName("skill_id").."SkillID"(INTEGER, "the skill ID of the effect")
                        optional.."Description"(STRING, "the effect's description")
                    }
                )
            }

            val INFUSION_SLOTS = array(record(name = "InfusionSlot", description = "Information about an items infusion slot.") {
                "Flags"(array(STRING), "infusion slot type of infusion upgrades")
                optional..SerialName("item_id").."ItemID"(INTEGER, "the infusion upgrade in the armor piece")
            })

            +record(name = "Armor", description = "Additional information about an armor item.") {
                "Type"(STRING, "the armor slot type")
                SerialName("weight_class").."WeightClass"(STRING, "the weight class")
                "Defense"(INTEGER, "the defense value of the armor piece")
                SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS, "infusion slots of the armor piece")
                optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..SerialName("suffix_item_id").."SuffixItemID"(INTEGER, "the suffix item ID")
                optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemID"(STRING, "the secondary suffix item ID")
                optional..SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional..SerialName("attribute_adjustment").."AttributeAdjustment"(DECIMAL, "") // TODO doc
            }
            +record(name = "Back", description = "Additional information about a backpiece.") {
                SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS, "infusion slots of the back item")
                optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..SerialName("suffix_item_id").."SuffixItemID"(INTEGER, "the suffix item ID")
                optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemID"(STRING, "the secondary suffix item ID")
                optional..SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional..SerialName("attribute_adjustment").."AttributeAdjustment"(DECIMAL, "") // TODO doc
            }
            +record(name = "Bag", description = "Additional information about a bag.") {
                "Size"(INTEGER, "the number of bag slots")
                SerialName("no_sell_or_sort").."NoSellOrSort"(BOOLEAN, "whether the bag is invisible")
            }
            +record(name = "Consumable", description = "Additional information about a consumable item.") {
                "Type"(STRING, "the consumable type")
                optional.."Description"(STRING, "effect description for consumables applying an effect")
                optional..SerialName("duration_ms").."DurationMs"(INTEGER, "effect duration in milliseconds")
                optional..SerialName("unlock_type").."UnlockType"(STRING, "unlock type for unlock consumables")
                optional..SerialName("color_id").."ColorID"(INTEGER, "the dye ID for dye unlocks")
                optional..SerialName("recipe_id").."RecipeID"(INTEGER, "the recipe ID for recipe unlocks")
                optional..SerialName("extra_recipe_ids").."ExtraRecipeIDs"(array(INTEGER), "additional recipe IDs for recipe unlocks")
                optional..SerialName("guild_upgrade_id").."GuildUpgradeID"(INTEGER, "the guild upgrade ID for the item")
                optional..SerialName("apply_count").."ApplyCount"(INTEGER, "the number of stacks of the effect applied by this item")
                optional.."Name"(STRING, "the effect type name of the consumable")
                optional.."Icon"(STRING, "the icon of the effect")
                optional.."Skins"(array(INTEGER), "a list of skin ids which this item unlocks")
            }
            +record(name = "Container", description = "Additional information about a container.") {
                "Type"(STRING, "the container type")
            }
            "Gathering"(record(name = "GatheringTool", description = "Additional information about a gathering tool.") {
                "Type"(STRING, "the tool type")
            })
            +record(name = "Gizmo", description = "Additional information about a gizmo.") {
                "Type"(STRING, "the gizmo type")
                optional..SerialName("guild_upgrade_id").."GuildUpgradeID"(INTEGER, "the guild upgrade ID for the item")
                optional..SerialName("vendor_ids").."VendorIDs"(array(INTEGER), "the vendor IDs")
            }
            +record(name = "MiniPet", description = "Additional information about a mini unlock item.") {
                SerialName("minipet_id").."MinipetID"(INTEGER, "the miniature it unlocks")
            }
            "Tool"(record(name = "Tool", description = "Additional information about a tool.") {
                "Type"(STRING, "the tool type")
                "Charges"(INTEGER, "the available charges")
            })
            +record(name = "Trinket", description = "Additional information about a trinket.") {
                "Type"(STRING, "the trinket type")
                SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS, "infusion slots of the trinket")
                optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..SerialName("suffix_item_id").."SuffixItemID"(INTEGER, "the suffix item ID")
                optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemID"(STRING, "the secondary suffix item ID")
                optional..SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional..SerialName("attribute_adjustment").."AttributeAdjustment"(DECIMAL, "") // TODO doc
            }
            +record(name = "UpgradeComponent", description = "Additional information about an upgrade component.") {
                "Type"(STRING, "the type of the upgrade component")
                "Flags"(array(STRING), "the items that can be upgraded with the upgrade component")
                SerialName("infusion_upgrade_flags").."InfusionUpgradeFlags"(array(STRING), "applicable infusion slot for infusion upgrades")
                "Suffix"(STRING, "the suffix appended to the item name when the component is applied")
                optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional.."Bonuses"(array(STRING), "the bonuses from runes")
                optional..SerialName("attribute_adjustment").."AttributeAdjustment"(DECIMAL, "") // TODO doc
            }
            +record(name = "Weapon", description = "Additional information about a weapon.") {
                "Type"(STRING, "the weapon type")
                SerialName("min_power").."MinPower"(INTEGER, "minimum weapon strength")
                SerialName("max_power").."MaxPower"(INTEGER, "maximum weapon strength")
                SerialName("damage_type").."DamageType"(STRING, "the damage type")
                "Defense"(INTEGER, "the defense value of the weapon")
                SerialName("infusion_slots").."InfusionSlots"(INFUSION_SLOTS, "infusion slots of the weapon")
                optional..SerialName("infix_upgrade").."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                optional..SerialName("suffix_item_id").."SuffixItemID"(INTEGER, "the suffix item ID")
                optional..SerialName("secondary_suffix_item_id").."SecondarySuffixItemID"(STRING, "the secondary suffix item ID")
                optional..SerialName("stat_choices").."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                optional..SerialName("attribute_adjustment").."AttributeAdjustment"(DECIMAL, "") // TODO doc
            }
        })
    }
    "/Items" {
        summary = "Returns the IDs of the available items."

        schema(record(name = "ItemIDs", description = "Information about the available items.") {
            "Items"(array(INTEGER), "the IDs of the available items")
        })
    }
    "/map_floor"(endpoint = "/MapFloor") {
        summary = "Returns information about a map floor."

        queryParameter("ContinentID", INTEGER, "the ID of the continent", key = "continent_id")
        queryParameter("FloorID", INTEGER, "the ID of the floor", key = "floor")
        schema(record(name = "MapFloor", description = "Information about a map floor.") {
            SerialName("texture_dims").."TextureDims"(array(INTEGER), "the width and height of the texture")
            "Regions"(
                description = "the floor's regions",
                type = map(
                    keys = INTEGER,
                    values = record(name = "Region", description = "Information about a region.") {
                        localized.."Name"(STRING, "the region's localized name")
                        SerialName("label_coord").."LabelCoord"(array(DECIMAL), "the coordinate of the region's label")
                        "Maps"(
                            description = "the region's maps",
                            type = map(
                                keys = STRING,
                                values = record(name = "Map", description = "Information about a map.") {
                                    localized.."Name"(STRING, "the map's localized name")
                                    SerialName("min_level").."MinLevel"(INTEGER, "the minimum level of the map")
                                    SerialName("max_level").."MaxLevel"(INTEGER, "the maximum level of the map")
                                    SerialName("default_floor").."DefaultFloor"(INTEGER, "the ID of the map's default floor")
                                    SerialName("map_rect").."MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
                                    SerialName("continent_rect").."ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                                    SerialName("points_of_interest").."PointsOfInterest"(
                                        description = "the points of interest on the floor (i.e. landmarks, vistas and waypoints)",
                                        type = array(record(name = "PointOfInterest", description = "Information about a point of interest (i.e. a landmark, vista or waypoint).") {
                                            SerialName("poi_id").."PoIID"(INTEGER, "the PoI's ID")
                                            optional..localized.."Name"(STRING, "the PoI's localized name")
                                            "Type"(STRING, "the type of the PoI (landmark, vista, or waypoint)")
                                            "Floor"(INTEGER, "the PoI's floor")
                                            "Coord"(array(DECIMAL), "the PoI's coordinates")
                                        })
                                    )
                                    optional.."GodShrines"(
                                        description = "the god shrines on the floor",
                                        type = array(record(name = "GodShrine", description = "Information about a god shrine.") {
                                            CamelCase("id").."ID"(INTEGER, "the god shrine's ID")
                                            localized.."Name"(STRING, "the god shrine's localized name")
                                            optional..localized..SerialName("name_contested").."NameContested"(STRING, "the god shrine's localized name (when contested)")
                                            optional.."Icon"(STRING, "the god shrine's icon")
                                            optional..SerialName("icon_contested").."IconContested"(STRING, "the god shrine's icon (when contested)")
                                            SerialName("poi_id").."PoIID"(INTEGER, "the god shrine's PoI ID")
                                            "Coord"(array(DECIMAL), "the god shrine's coordinates")
                                        })
                                    )
                                    "Tasks"(
                                        description = "the tasks on the floor",
                                        type = array(record(name = "Task", description = "Information about a task.") {
                                            SerialName("task_id").."TaskID"(INTEGER, "the task's ID")
                                            localized.."Objective"(STRING, "the adventure's localized objective")
                                            "Level"(INTEGER, "the task's level")
                                            "Coord"(array(DECIMAL), "the task's coordinates")
                                            "Bounds"(array(array(DECIMAL)), "the task's bounds")
                                        })
                                    )
                                    SerialName("skill_challenges").."SkillChallenges"(
                                        description = "the skill challenges on the floor",
                                        type = array(record(name = "SkillChallenge", description = "Information about a skill challenge.") {
                                            SerialName("idx")..CamelCase("id").."ID"(INTEGER, "the skill challenge's ID (unique within an expansion)")
                                            "Expac"(INTEGER, "the skill challenge's expansion ID")
                                            "Coord"(array(DECIMAL), "the skill challenge's coordinates")
                                        })
                                    )
                                    "Sectors"(
                                        description = "the sectors on the floor",
                                        type = array(record(name = "Sector", description = "Information about a sector.") {
                                            SerialName("sector_id").."SectorID"(INTEGER, "the sector's ID")
                                            optional..localized.."Name"(STRING, "the sector's localized name")
                                            "Level"(INTEGER, "the sector's level")
                                            "Coord"(array(DECIMAL), "the sector's coordinates")
                                            "Bounds"(array(array(DECIMAL)), "the sector's bounds")
                                        })
                                    )
                                    "Adventures"(
                                        description = "the adventures on the floor",
                                        type = array(record(name = "Adventure", description = "Information about an adventure.") {
                                            CamelCase("guid").."GUID"(STRING, "the adventure's ID")
                                            localized.."Name"(STRING, "the adventure's localized name")
                                            "Coord"(array(DECIMAL), "the adventure's coordinates")
                                            "Leaderboard"(
                                                description = "the adventure's leaderboard",
                                                type = record(name = "Leaderboard", description = "Information about an adventure's leaderboard.") {
                                                    CamelCase("guid").."GUID"(STRING, "the leaderboard's ID")
                                                    localized.."Title"(STRING, "the leaderboard's localized title")
                                                    localized.."Description"(STRING, "the leaderboard's localized description")
                                                }
                                            )
                                        })
                                    )
                                    SerialName("training_points").."MasteryPoints"(
                                        description = "the mastery points on the floor",
                                        type = array(record(name = "MasteryPoint", description = "Information about a mastery point.") {
                                            CamelCase("id").."ID"(INTEGER, "the mastery point's ID")
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
    "/map_names"(endpoint = "/MapNames") {
        summary = "Returns information about maps."

        schema(array(
            description = "the available maps",
            items = record(name = "MapName", description = "Information about a map.") {
                CamelCase("id").."ID"(INTEGER, "the map's ID")
                localized.."Name"(STRING, "the map's name")
            }
        ))
    }
    "/Maps" {
        summary = "Returns information about maps."

        schema(record(name = "Maps", description = "Information about maps.") {
            "Maps"(
                description = "the maps",
                type = map(
                    keys = INTEGER,
                    values = record(name = "Map", description = "Information about a map.") {
                        localized..SerialName("map_name").."MapName"(STRING, "the map's localized name")
                        "Type"(STRING, "the type of map")
                        SerialName("min_level").."MinLevel"(INTEGER, "the minimum level of the map")
                        SerialName("max_level").."MaxLevel"(INTEGER, "the maximum level of the map")
                        SerialName("default_floor").."DefaultFloor"(INTEGER, "the ID of the map's default floor")
                        "Floors"(array(INTEGER), "the IDs of the floors available on the map")
                        SerialName("region_id").."RegionID"(INTEGER, "the ID of the region the map belongs to")
                        optional..localized..SerialName("region_name").."RegionName"(STRING, "the name of the region the map belongs to")
                        SerialName("continent_id").."ContinentID"(INTEGER, "the ID of the continent the map belongs to")
                        optional..localized..SerialName("continent_name").."ContinentName"(STRING, "the name of the continent the map belongs to")
                        SerialName("map_rect").."MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
                        SerialName("continent_rect").."ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                    }
                )
            )
        })
    }
    "/recipe_details"(endpoint = "/RecipeDetails") {
        summary = "Returns information about the recipes in the game."

        queryParameter("RecipeID", INTEGER, "the recipe's ID", key = "recipe_id")
        schema(record(name = "Recipe", description = "Information about a crafting recipe.") {
            SerialName("recipe_id").."RecipeID"(INTEGER, "the recipe's ID")
            "Type"(STRING, "the recipe's type")
            SerialName("output_item_id").."OutputItemID"(INTEGER, "the ID of the produced item")
            SerialName("output_item_count").."OutputItemCount"(INTEGER, "the amount of items produced")
            SerialName("time_to_craft_ms").."CraftTimeMillis"(INTEGER, "the time in milliseconds it takes to craft the item")
            "Disciplines"(array(STRING), "the crafting disciplines that can use the recipe")
            SerialName("min_rating").."MinRating"(INTEGER, "the minimum rating required to use the recipe")
            "Flags"(array(STRING), "the flags applying to the recipe")
            "Ingredients"(
                description = "the recipe's ingredients",
                type = array(record(name = "Ingredient", description = "Information about a recipe ingredient.") {
                    SerialName("item_id").."ItemID"(STRING, "the ingredient's item ID")
                    "Count"(INTEGER, "the quantity of this ingredient")
                })
            )
        })
    }
    "/Recipes" {
        summary = "Returns the IDs of the available recipes."

        schema(record(name = "RecipeIDs", description = "Information about the available recipes.") {
            "Recipes"(array(INTEGER), "the IDs of the available recipes")
        })
    }
    "/skin_details"(endpoint = "/SkinDetails") {
        summary = "Returns information about the skins in the game."
        cache = 1.hours

        queryParameter("SkinID", INTEGER, "the the skin's ID", key = "skin_id")
        schema(conditional("SkinDetails", "Information about a skin.", interpretationInNestedProperty = true, sharedConfigure = {
            SerialName("skin_id").."SkinID"(INTEGER, "")
            localized.."Name"(STRING, "the skin's localized name")
            "Type"(STRING, "the skin's type")
            "Rarity"(STRING, "the skin's rarity")
            "Flags"(array(STRING), "additional skin flags (ShowInWardrobe, NoCost, HideIfLocked, OverrideRarity)")
            "Restrictions"(array(STRING), "the IDs of the races that can use this skin, or empty if it can be used by any race")
            SerialName("icon_file_id").."IconFileID"(STRING, "the icon's file ID to be used with the render service")
            SerialName("icon_file_signature").."IconFileSignature"(STRING, "the icon's file signature to be used with the render service")
        }) {
            +record(name = "Armor", description = "Additional information about an armor skin.") {
                SerialName("type").."ArmorType"(STRING, "the skin's armor slot")
                SerialName("weight_class").."WeightClass"(STRING, "the skin's armor weight")
                SerialName("dye_slots").."DyeSlots"(
                    description = "the skin's dye slots",
                    type = record(name = "DyeSlots", description = "Information about a skin's sye slots.") {
                        val DYE_SLOT = record(name = "DyeSlot", description = "Information about a dye slot.") {
                            SerialName("color_id").."ColorID"(INTEGER, "the default color's ID")
                            "Material"(STRING, "the material type")
                        }

                        fun DYE_SLOTS() = array(DYE_SLOT, nullableItems = true)

                        SerialName("asura_male").."AsuraMale"(DYE_SLOTS(), "the dye slot overrides for asuarn male characters")
                        SerialName("asura_female").."AsuraFemale"(DYE_SLOTS(), "the dye slot overrides for asuarn female characters")
                        SerialName("charr_male").."CharrMale"(DYE_SLOTS(), "the dye slot overrides for charr male characters")
                        SerialName("charr_female").."CharrFemale"(DYE_SLOTS(), "the dye slot overrides for charr female characters")
                        SerialName("human_male").."HumanMale"(DYE_SLOTS(), "the dye slot overrides for human male characters")
                        SerialName("human_female").."HumanFemale"(DYE_SLOTS(), "the dye slot overrides for human female characters")
                        SerialName("norn_male").."NornMale"(DYE_SLOTS(), "the dye slot overrides for norn male characters")
                        SerialName("norn_female").."NornFemale"(DYE_SLOTS(), "the dye slot overrides for norn female characters")
                        SerialName("sylvari_male").."SylvariMale"(DYE_SLOTS(), "the dye slot overrides for sylvari male characters")
                        SerialName("sylvari_female").."SylvariFemale"(DYE_SLOTS(), "the dye slot overrides for sylvari female characters")
                    }
                )
            }
            +record(name = "Back", description = "Additional information about a backpack skin.") {}
            +record(name = "Gathering", description = "Additional information about a gathering tool skin.") {}
            +record(name = "Weapon", description = "Additional information about a gathering tool skin.") {
                SerialName("type").."WeaponType"(STRING, "the skin's weapon type")
                SerialName("damage_type").."DamageType"(STRING, "the skin's damage type")
            }
        })
    }
    "/Skins" {
        summary = "Returns the IDs of the available skins."

        schema(record(name = "SkinIDs", description = "Information about the available skins.") {
            "Skins"(array(INTEGER), "the IDs of the available skins")
        })
    }
    "/world_names"(endpoint = "/WorldNames") {
        summary = "Returns information about the available worlds (or servers)."

        schema(array(
            description = "the available worlds",
            items = record(name = "WorldName", description = "Information about an available world (or server).") {
                CamelCase("id").."ID"(INTEGER, "the ID of the world")
                localized.."Name"(STRING, "the name of the world")
            }
        ))
    }
    "/wvw/match_details"(endpoint = "/WvW/MatchDetails") {
        summary = "Returns detailed information about a WvW match."

        queryParameter("MatchID", STRING, "the ID of the match", key = "match_id")
        schema(record(name = "WvWMatchDetails", description = "Information about a WvW match.") {
            SerialName("match_id").."MatchID"(STRING, "the match's ID")
            "Scores"(array(INTEGER), "the total scores")
            "Maps"(
                description = "the map information",
                type = array(record(name = "Map", description = "Map-specific information about scores.") {
                    "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                    "Scores"(array(INTEGER), "the scores")
                    "Objectives"(
                        description = "the map's objectives",
                        type = array(record(name = "Objective", description = "Information about an objective.") {
                            CamelCase("id").."ID"(INTEGER, "the objective's ID")
                            "Owner"(STRING, "the objective's owner (i.e. \"Red\", \"Green\", \"Blue\", or \"Neutral\")")
                            optional..SerialName("owner_guild").."OwnerGuild"(STRING, "the guild ID of the guild that currently has claimed this objective")
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
    "/WvW/Matches" {
        summary = "Returns information about WvW matches."

        schema(record(name = "WvWMatches", description = "Information about WvW matches.") {
            SerialName("wvw_matches").."WvWMatches"(
                description = "the matches",
                type = array(record(name = "WvWMatch", description = "Information about a WvW match.") {
                    SerialName("wvw_match_id").."WvWMatchID"(STRING, "the match's ID")
                    SerialName("red_world_id").."RedWorldID"(INTEGER, "the ID of the red team's primary server")
                    SerialName("blue_world_id").."BlueWorldID"(INTEGER, "the ID of the blue team's primary server")
                    SerialName("green_world_id").."GreenWorldID"(INTEGER, "the ID of the green team's primary server")
                    SerialName("start_time").."StartTime"(STRING, "the ISO-8601 standard timestamp of when the match's start")
                    SerialName("end_time").."EndTime"(STRING, "the ISO-8601 standard timestamp of when the match's end")
                })
            )
        })
    }
    "/wvw/objectives_names"(endpoint = "/WvW/ObjectivesNames") {
        summary = "Returns information about the available WvW objectives."

        schema(array(
            description = "the available objectives",
            items = record(name = "ObjectiveName", description = "Information about a WvW objective.") {
                CamelCase("id").."ID"(STRING, "the ID of the objective")
                localized.."Name"(STRING, "the objective's localized name")
            }
        ))
    }
}