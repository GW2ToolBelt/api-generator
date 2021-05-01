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
    "/event_details"(endpoint = "EventDetails") {
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
    "/map_names"(endpoint = "MapNames") {
        summary = "Returns information about maps."

        schema(array(
            description = "the available maps",
            items = record(name = "MapName", description = "Information about a map.") {
                CamelCase("id").."ID"(INTEGER, "the map's ID")
                localized.."Name"(STRING, "the map's name")
            }
        ))
    }
    "/skin_details"(endpoint = "SkinDetails") {
        summary = "Returns information about the skins in the game."
        cache = 1.hours

        queryParameter("SkinID", INTEGER, "the amount to exchange", key = "skin_id")
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
    "/world_names"(endpoint = "WorldNames") {
        summary = "Returns information about the available worlds (or servers)."

        schema(array(
            description = "the available worlds",
            items = record(name = "WorldName", description = "Information about an available world (or server).") {
                CamelCase("id").."ID"(INTEGER, "the ID of the world")
                localized.."Name"(STRING, "the name of the world")
            }
        ))
    }
}