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
    "/skin_details"(endpoint = "SkinDetails") {
        summary = "Returns information about the skins in the game."
        cache = 1.hours
        isLocalized = true

        queryParameter("SkinID", INTEGER, "the amount to exchange", key = "skin_id")
        schema(conditional("SkinDetails", "Information about a skin.", interpretationInNestedProperty = true, sharedConfigure = {
            SerialName("skin_id").."SkinID"(INTEGER, "")
            "Name"(STRING, "the skin's localized name")
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
        cache = 1.hours

        schema(array(INTEGER, "the IDs of the available skins"))
    }
}