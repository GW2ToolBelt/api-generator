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
    "/Items" {
        summary = "Returns the IDs of the available items."

        schema(record(name = "ItemIDs", description = "Information about the available items.") {
            "Items"(array(INTEGER), "the IDs of the available items")
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
                "Type"(STRING, "the skin's armor slot")
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
                "Type"(STRING, "the skin's weapon type")
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