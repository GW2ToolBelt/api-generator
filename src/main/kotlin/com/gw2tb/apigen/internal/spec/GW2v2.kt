/*
 * Copyright (c) 2019-2024 Leon Linhart
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
@file:Suppress("LocalVariableName")
package com.gw2tb.apigen.internal.spec

import com.gw2tb.apigen.model.APIv2Endpoint.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.ir.LowLevelApiGenApi
import com.gw2tb.apigen.model.Name
import com.gw2tb.apigen.model.TokenScope.*
import com.gw2tb.apigen.model.v2.SchemaVersion.*
import kotlin.time.Duration

@OptIn(LowLevelApiGenApi::class)
@Suppress("FunctionName")
internal val GW2v2 = GW2APISpecV2 {
    val DAILY_ACHIEVEMENT = record(name = "DailyAchievement", description = "Information about a daily achievement.") {
        "Id"(ACHIEVEMENT_ID, "the achievement's ID")
        "Level"(
            description = "the level requirement for the daily achievement to appear",
            type = record(name = "LevelRequirement", description = "Information about the level requirement of a daily achievement.") {
                "Min"(INTEGER, "the minimum level for a character to the daily achievement")
                "Max"(INTEGER, "the maximum level for a character to the daily achievement")
            }
        )
        "RequiredAccess"(array(STRING), "the GW2 campaigns required to see the daily achievement")
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_BACKSTORY() {
        "Backstory"(array(STRING), "the IDs of the character's backstory answers")
    }

    val CHARACTERS_BUILDTAB = record(name = "CharactersBuildTab", description = "Information about a character's build tab.") {
        "Tab"(INTEGER, "the tab's ID")
        "IsActive"(BOOLEAN, "a flag indicating whether this tab is the active tab")
        "Build"(
            description = "the stored build",
            type = record(name = "Build", description = "Information about a build.") {
                val SPECIALIZATION = record(name = "Specialization", description = "Information about a build's specialization.") {
                    optional.."Id"(INTEGER, "the specialization's ID")
                    "Traits"(array(INTEGER, nullableItems = true), "the IDs of the selected traits")
                }

                val SKILLS = record(name = "Skills", description = "Information about a build's skills.") {
                    optional.."Heal"(INTEGER, "the heal skill's ID")
                    "Utilities"(array(INTEGER, nullableItems = true), "the IDs of the utility skills")
                    optional.."Elite"(INTEGER, "the elite skill's ID")
                }

                "Name"(STRING, "the build's name")
                "Profession"(STRING, "the build's profession")
                "Specializations"(array(SPECIALIZATION), "the build's specializations")
                "Skills"(SKILLS, "the build's skills")
                "AquaticSkills"(SKILLS, "the build's aquatic skills")
                optional.."Legends"(array(STRING, nullableItems = true), "the build's legend IDs")
                optional.."AquaticLegends"(array(STRING, nullableItems = true), "the build's aquatic legend IDs")
            }
        )
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_CORE() {
        "Name"(STRING, "the character's name")
        "Race"(STRING, "the ID of the character's race")
        "Gender"(STRING, "the character's gender")
        "Profession"(STRING, "the ID of the character's profession")
        "Level"(INTEGER, "the character's level")
        optional.."Guild"(STRING, "the ID of the character's represented guild")
        "Age"(INTEGER, "the amount of seconds the character was played")
        "Created"(STRING, "the ISO-8601 standard timestamp of when the character was created")
        since(V2_SCHEMA_2019_02_21T00_00_00_000Z).."LastModified"(STRING, "the ISO-8601 standard timestamp of when the API record of the character was last modified")
        "Deaths"(INTEGER, "the amount of times the character has been defeated")
        optional.."Title"(INTEGER, "the ID of the character's selected title")
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_CRAFTING() {
        "Crafting"(
            description = "the character's crafting disciplines",
            type = array(record(name = "Discipline", description = "Information about a character's crafting discipline.") {
                "Discipline"(STRING, "the name of the discipline")
                "Rating"(INTEGER, "the character's crafting level for the discipline")
                "Active"(BOOLEAN, "a flag indicating whether the discipline is currently active on the character")
            })
        )
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_EQUIPMENT() {
        "Equipment"(
            description = "the character's equipment",
            type = array(record(name = "EquipmentSlot", description = "Information a character's equipment slot.") {
                "Id"(ITEM_ID, "the equipment piece's item ID")
                until(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Slot"(STRING, "the equipment piece's slot")
                since(V2_SCHEMA_2019_12_19T00_00_00_000Z)..optional.."Slot"(STRING, "the equipment piece's slot")
                optional.."Skin"(SKIN_ID, "the ID of the skin transmuted onto the equipment piece")
                optional.."Dyes"(array(INTEGER, nullableItems = true), "the IDs of the dyes applied to the item")
                optional.."Upgrades"(array(ITEM_ID), "the IDs of the upgrade components slotted into the item")
                optional.."Infusions"(array(ITEM_ID), "the IDs of the infusions slotted into the item")
                optional.."Charges"(INTEGER, "the amount of charges remaining on the item")
                optional.."Binding"(STRING, "the binding of the item")
                optional.."BoundTo"(STRING, "name of the character the item is bound to")
                optional.."Stats"(
                    description = "contains information on the stats chosen if the item offers an option for stats/prefix",
                    type = record(name = "Stats", description = "Information about an item's stats.") {
                        "Id"(INTEGER, "the itemstat ID")
                        "Attributes"(
                            description = "the item's attributes",
                            type = record(name = "Attributes", description = "Information about an item's attributes.") {
                                optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                                optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                                optional..SerialName("CritDamage").."CritDamage"(INTEGER, "the amount of crit damage given by the item")
                                optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                                optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                                optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                                optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                                optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                                optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                            }
                        )
                    }
                )
                since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Location"(STRING, "the storage location of the equipment piece")
                since(V2_SCHEMA_2019_12_19T00_00_00_000Z)..optional.."Tabs"(array(INTEGER), "the IDs of the tabs in which this item is used")
            })
        )
    }

    val CHARACTERS_EQUIPMENTTAB = record(name = "CharactersEquipmentTab", description = "Information about a character's equipment tab.") {
        "Tab"(INTEGER, "the tab's ID")
        "Name"(STRING, "the equipment configuration's name")
        "IsActive"(BOOLEAN, "a flag indicating whether this tab is the active tab")
        "Equipment"(
            description = "the stored equipment",
            type = array(record(name = "Equipment", description = "Information about a piece of equipment.") {
                "Id"(INTEGER, "the equipped item's ID")
                "Slot"(STRING, "the slot in which the equipment piece is slotted into")
                optional.."Skin"(SKIN_ID, "the ID of the skin transmuted onto the equipment piece")
                optional.."Dyes"(array(INTEGER, nullableItems = true), "the IDs of the dyes applied to the item")
                optional.."Upgrades"(array(INTEGER), "the IDs of the upgrade components slotted into the item")
                optional.."Infusions"(array(INTEGER), "the IDs of the infusions slotted into the item")
                optional.."Binding"(STRING, "the binding of the item")
                optional.."BoundTo"(STRING, "name of the character the item is bound to")
                "Location"(STRING, "the storage location of the equipment piece")
                optional.."Stats"(
                    description = "information about the stats chosen for the item (if the item offers the option to select stats/prefix)",
                    type = record(name = "Stats", description = "Information about an item's stats.") {
                        "Id"(INTEGER, "the itemstat ID")
                        optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                        optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                        optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                        optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                        optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                        optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                        optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                        optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                    }
                )
            })
        )
        "EquipmentPvp"(
            description = "the character's PvP equipment",
            type = record(name = "PvpEquipment", "Information about a character's PvP equipment.") {
                optional.."Amulet"(INTEGER, "the ID of the selected amulet")
                optional.."Rune"(INTEGER, "the ID of the selected rune")
                "Sigils"(array(INTEGER, nullableItems = true), "the IDs of the selected sigils")
            }
        )
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_INVENTORY() {
        "Bags"(
            description = "the character's inventory bags",
            type = array(record(name = "Bag", description = "Information about an inventory bag.") {
                "Id"(ITEM_ID, "the bag's item ID")
                "Size"(INTEGER, "the bag's size")
                "Inventory"(
                    description = "the bag's content",
                    type = array(record(name = "Item", description = "Information about an item in a character's inventory.") {
                        "Id"(ITEM_ID, "the item's ID")
                        "Count"(INTEGER, "the amount of items in the stack")
                        optional.."Charges"(INTEGER, "the amount of charges remaining on the item")
                        optional.."Skin"(SKIN_ID, "the ID of the skin applied to the item")
                        optional.."Upgrades"(array(ITEM_ID), "an array of item IDs for each rune or signet applied to the item")
                        optional.."UpgradeSlotIndices"(array(INTEGER), "the slot of the corresponding upgrade")
                        optional.."Infusions"(array(ITEM_ID), "an array of item IDs for each infusion applied to the item")
                        optional.."Stats"(
                            description = "contains information on the stats chosen if the item offers an option for stats/prefix",
                            type = record(name = "Stats", description = "Information about an item's stats.") {
                                "Id"(INTEGER, "the itemstat ID")
                                optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                                optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                                optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                                optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                                optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                                optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                                optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                                optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                            }
                        )
                        optional.."Binding"(STRING, "the binding of the material")
                        optional.."BoundTo"(STRING, "name of the character the item is bound to")
                    }, nullableItems = true)
                )
            })
        )
    }

    val CHARACTERS_SKILLS = record(name = "Skills", description = "Information about a character's equipped skills.") {
        val SKILLS = record(name = "Skills", description = "Information about a character's equipped skills.") {
            optional.."Heal"(INTEGER, "the heal skill's ID")
            "Utilities"(array(INTEGER, nullableItems = true), "the IDs of the utility skills")
            optional.."Elite"(INTEGER, "the elite skill's ID")
            optional.."Legends"(array(STRING, nullableItems = true), "the legend IDs")
        }

        "Pve"(SKILLS, "the character's PvE skills")
        "Pvp"(SKILLS, "the character's PvP skills")
        "Wvw"(SKILLS, "the character's WvW skills")
    }

    val CHARACTERS_SPECIALIZATIONS = record(name = "Specializations", description = "Information about a character's equipped specializations.") {
        val SPECIALIZATION = record(name = "Specialization", description = "Information about an equipped specialization.") {
            optional.."Id"(INTEGER, "the specialization's ID")
            "Traits"(array(INTEGER, nullableItems = true), "the IDs of the selected traits")
        }

        "Pve"(array(SPECIALIZATION), "the character's PvE specializations")
        "Pvp"(array(SPECIALIZATION), "the character's PvP specializations")
        "Wvw"(array(SPECIALIZATION), "the character's WvW specializations")
    }

    @APIGenDSL
    fun SchemaRecordBuilder<*>.CHARACTERS_TRAINING() {
        "Training"(
            description = "the training information for a character's trained skill-trees",
            type = array(record(name = "Training", description = "Information about a character's trained skill-tree.") {
                "Id"(INTEGER, "the skill tree's ID")
                "Spent"(INTEGER, "the amount of hero points spent in the tree")
                "Done"(BOOLEAN, "a flag indicating whether the tree is fully trained")
            })
        )
    }

    @APIGenDSL
    fun SchemaConditionalBuilder<*>.FACTS() {
        +record(name = "AttributeAdjust", description = "Additional information about an attribute adjustment.") {
            optional.."Value"(INTEGER, "the amount 'target' gets adjusted, based on a level 80 character at base stats")
            optional.."Target"(STRING, "the attribute this fact adjusts")
        }
        +record(name = "Buff", description = "Additional information about a buff.") {
            "Status"(STRING, "the boon, condition, or effect referred to by the fact")
            optional.."Duration"(INTEGER, "the duration of the effect in seconds")
            optional.."Description"(STRING, "the description of the status effect")
            optional.."ApplyCount"(INTEGER, "the number of stacks applied")
        }
        +record(name = "BuffConversion", description = "Additional information about a buff-conversion.") {
            "Source"(STRING, "the attribute that is used to calculate the attribute gain")
            "Percent"(INTEGER, "how much of the source attribute is added to target")
            "Target"(STRING, "the attribute that gets added to")
        }
        +record(name = "ComboField", description = "Additional information about a combo-field.") {
            "FieldType"(STRING, "the type of the field")
        }
        +record(name = "ComboFinisher", description = "Additional information about a combo-finisher.") {
            "FinisherType"(STRING, "the type of finisher")
            "Percent"(INTEGER, "the percent chance that the finisher will trigger")
        }
        +record(name = "Damage", description = "Additional information about damage.") {
            "HitCount"(INTEGER, "the amount of times the damage hits")
            SerialName("dmg_multiplier").."DamageMultiplier"(DECIMAL, "the damage multiplier")
        }
        +record(name = "Distance", description = "Additional information about range.") {
            "Distance"(INTEGER, "the distance value")
        }
        +record(name = "NoData", description = "No (special) additional information.") {}
        +record(name = "Number", description = "An additional number.") {
            "Value"(INTEGER, "the number value as referenced by text")
        }
        +record(name = "Percent", description = "An additional percentage value.") {
            "Percent"(DECIMAL, "the percentage value as referenced by text")
        }
        +record(name = "PrefixedBuff", description = "Additional information about a prefixed buff.") {
            optional.."Status"(STRING, "the boon, condition, or effect referred to by the fact")
            optional.."Duration"(INTEGER, "the duration of the effect in seconds")
            optional.."Description"(STRING, "the description of the status effect")
            optional.."ApplyCount"(INTEGER, "the number of stacks applied")
            "Prefix"(
                description = "A buff's prefix icon and description.",
                type = record(name = "Prefix", description = "Information about a buff's prefix.") {
                    "Text"(STRING, "the prefix text")
                    "Icon"(STRING, "the prefix icon url")
                    optional.."Status"(STRING, "the prefix status")
                    optional.."Description"(STRING, "the prefix description")
                }
            )
        }
        +record(name = "Radius", description = "Additional information about a radius.") {
            "Distance"(INTEGER, "the radius value")
        }
        +record(name = "Range", description = "Additional information about range.") {
            "Value"(INTEGER, "the range of the trait/skill")
        }
        +record(name = "Recharge", description = "Additional information about recharge.") {
            "Value"(DECIMAL, "the recharge time in seconds")
        }
        +record(name = "StunBreak", description = "Additional information about a stunbreak.") {
            "Value"(BOOLEAN, "always true")
        }
        +record(name = "Time", description = "Additional information about time.") {
            "Duration"(INTEGER, "the time value in seconds")
        }
        +record(name = "Unblockable", description = "A fact, indicating that a trait/skill is unblockable.") {
            "Value"(BOOLEAN, "always true")
        }
    }

    V2_ACCOUNT(
        summary = "Returns information about a player's account.",
        security = security(ACCOUNT)
    ) {
        schema(record(name = "Account", description = "Information about a player's account.") {
            "Id"(STRING, "the unique persistent account GUID")
            "Age"(INTEGER, "the age of the account in seconds")
            "Name"(STRING, "the unique account name")
            "World"(INTEGER, "the ID of the home world the account is assigned to")
            "Guilds"(array(STRING), "an array containing the IDs of all guilds the account is a member in")
            optional(GUILDS).."GuildLeader"(array(STRING), "an array containing the IDs of all guilds the account is a leader of")
            "Created"(STRING, "the ISO-8601 standard timestamp of when the account was created")
            "Access"(array(STRING), "an array of what content this account has access to")
            "Commander"(BOOLEAN, "a flag indicating whether the commander tag is unlocked for the account")
            optional(PROGRESSION).."FractalLevel"(
                INTEGER,
                "the account's personal fractal level"
            )
            optional(PROGRESSION)..SerialName("daily_ap").."DailyAP"(INTEGER, "the daily AP the account has")
            optional(PROGRESSION)..SerialName("monthly_ap").."MonthlyAP"(INTEGER, "the monthly AP the account has")
            optional(PROGRESSION).."WvwRank"(
                INTEGER,
                "the account's personal wvw rank"
            )
            since(V2_SCHEMA_2019_02_21T00_00_00_000Z).."LastModified"(
                STRING,
                "the ISO-8601 standard timestamp of when the account information last changed (as perceived by the API)"
            )
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z)..optional(BUILDS).."BuildStorageSlots"(
                INTEGER,
                "the number of the account's account-wide build storage slots unlocked"
            )
        })
    }
    V2_ACCOUNT_ACHIEVEMENTS(
        summary = "Returns a player's progress towards all their achievements.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(
            description = "A list of progress information towards all achievements the account has made progress.",
            items = record(name = "AccountAchievement", description = "Information about a player's progress towards an achievement.") {
                "Id"(ACHIEVEMENT_ID, "the achievement's ID")
                "Done"(BOOLEAN, "a flag indicating whether the account has completed the achievement")
                optional.."Bits"(array(INTEGER), "an array of numbers (whose exact meaning differs) giving information about the progress towards an achievement")
                optional.."Current"(INTEGER, "the account's current progress towards the achievement")
                optional.."Max"(INTEGER, "the amount of progress required to complete the achievement")
                optional.."Repeated"(INTEGER, "the number of times the achievement has been completed (if the achievement is repeatable)")
                optional.."Unlocked"(BOOLEAN, "a flag indicating whether the achievement is unlocked (if the achievement can be unlocked)")
            }
        ))
    }
    V2_ACCOUNT_BANK(
        summary = "Returns information about the items stored in a player's vault.",
        security = security(ACCOUNT, INVENTORIES)
    ) {
        schema(array(
            description = "a list of slots in a player's bank.",
            nullableItems = true,
            items = record(name = "AccountBankSlot", description = "Information about a bank slot.") {
                "Id"(ITEM_ID, "the item's ID")
                "Count"(INTEGER, "the amount of items in the stack")
                optional.."Charges"(INTEGER, "the amount of charges remaining on the item")
                optional.."Skin"(SKIN_ID, "the ID of the skin applied to the item")
                optional.."Dyes"(array(INTEGER), "the IDs of the dyes applied to the item")
                optional.."Upgrades"(array(ITEM_ID), "the array of item IDs of runes or sigils applied to the item")
                optional.."UpgradeSlotIndices"(array(INTEGER), "the slot of the corresponding upgrade")
                optional.."Infusions"(array(ITEM_ID), "the array of item IDs of infusions applied to the item")
                optional.."Stats"(
                    description = "contains information on the stats chosen if the item offers an option for stats/prefix",
                    type = record(name = "Stats", description = "Information about an item's stats.") {
                        "Id"(INTEGER, "the itemstat ID")
                        "Attributes"(
                            description = "the item's attributes",
                            type = record(name = "Attributes", description = "Information about an item's attributes.") {
                                optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                                optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                                optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                                optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                                optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                                optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                                optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                                optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                            }
                        )
                    }
                )
                optional.."Binding"(STRING, "the binding of the material")
                optional.."BoundTo"(STRING, "name of the character the item is bound to")
            }
        ))
    }
    V2_ACCOUNT_BUILDSTORAGE(
        summary = "Returns an account's build storage.",
        security = security(ACCOUNT)
    ) {
        schema(array(
            description = "the builds in the account's storage",
            items = record(name = "AccountBuildStorageSlot", description = "Information about a build in an account's storage.") {
                "Name"(STRING, "the build's name")
                "Profession"(STRING, "the profession's ID")
                "Specializations"(
                    description = "the build's specializations",
                    type = array(
                        nullableItems = true,
                        items = record(name = "Specialization", description = "Information about a build's specialization.") {
                            "Id"(INTEGER, "the specializations ID")
                            "Traits"(array(INTEGER, nullableItems = true), "the trait IDs")
                        }
                    )
                )
                optional.."Skills"(
                    description = "the build's skills",
                    type = record(name = "Skills", description = "Information about a build's skills.") {
                        optional.."Heal"(INTEGER, "the heal skill's ID")
                        "Utilities"(array(INTEGER, nullableItems = true), "the IDs of the utility skills")
                        optional.."Elite"(INTEGER, "the elite skill's ID")
                    }
                )
                optional.."AquaticSkills"(
                    description = "the build's aquatic skills",
                    type = record(name = "AuqaticSkills", description = "Information about a build's aquatic skills.") {
                        optional.."Heal"(INTEGER, "the heal skill's ID")
                        "Utilities"(array(INTEGER, nullableItems = true), "the IDs of the utility skills")
                        optional.."Elite"(INTEGER, "the elite skill's ID")
                    }
                )
                optional.."Legends"(array(STRING, nullableItems = true), "the build's legend IDs")
                optional.."AquaticLegends"(array(STRING, nullableItems = true), "the build's aquatic legend IDs")
            }
        ))
    }
    V2_ACCOUNT_DAILYCRAFTING(
        summary = "Returns which items that can be crafted once per day a player crafted since the most recent daily reset.",
        security = security(ACCOUNT, PROGRESSION, UNLOCKS)
    ) {
        schema(array(STRING, "a list of dailycrafting IDs of the items that can be crafted once per day which the player has crafted since the most recent daily reset"))
    }
    V2_ACCOUNT_DUNGEONS(
        summary = "Returns which dungeons paths a player has completed since the most recent daily reset.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(STRING, "an array of IDs containing an ID for each dungeon path that the player has completed since the most recent daily reset"))
    }
    V2_ACCOUNT_DYES(
        summary = "Returns information about a player's unlocked dyes.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "a list of the dye IDs of the account's unlocked dyes"))
    }
    V2_ACCOUNT_EMOTES(
        summary = "Returns information about a player's unlocked emotes.",
        security = security(ACCOUNT)
    ) {
        schema(array(STRING, "an array of IDs containing the ID of each emote unlocked by the player"))
    }
    V2_ACCOUNT_FINISHERS(
        summary = "Returns information about a player's unlocked finishers.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(
            description = "the finishers unlocked by the account",
            items = record(name = "AccountFinisher", description = "Information about finishers unlocked by an account.") {
                "Id"(INTEGER, "the finisher's ID")
                "Permanent"(BOOLEAN, "whether the finisher is unlock permanently")
                optional.."Quantity"(INTEGER, "the remaining uses")
            }
        ))
    }
    V2_ACCOUNT_GLIDERS(
        summary = "Returns information about a player's unlocked gliders.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each glider unlocked by the player"))
    }
    V2_ACCOUNT_HOME(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_ACCOUNT_HOME_CATS(
        summary = "Returns information about a player's unlock home instance cats.",
        security = security(ACCOUNT, PROGRESSION, UNLOCKS)
    ) {
        schema(
            V2_SCHEMA_CLASSIC to array(
                description = "the IDs of the player's unlocked home instance cats",
                items = record(name = "AccountHomeInstanceCat", description = "Information about a player's unlocked home-instance cat.") {
                    "Id"(INTEGER, "the cat's ID")
                    "Hint"(STRING, "the unlock hint")
                }
            ),
            V2_SCHEMA_2019_03_22T00_00_00_000Z to array(INTEGER, "the IDs of the player's unlocked home instance cats")
        )
    }
    V2_ACCOUNT_HOME_NODES(
        summary = "Returns information about a player's unlocked home instance nodes.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(STRING, "an array of IDs containing th ID of each home instance node unlocked by the player"))
    }
    V2_ACCOUNT_INVENTORY(
        summary = "Returns information about a player's shared inventory slots.",
        security = security(ACCOUNT, INVENTORIES)
    ) {
        schema(array(
            description = "A list of stacks of items in an account's shared inventory.",
            items = record(name = "AccountInventorySlot", description = "Information about a stack of items in a player's shared inventory.") {
                "Id"(ITEM_ID, "the item's ID")
                "Count"(INTEGER, "the amount of items in the stack")
                optional.."Charges"(INTEGER, "the amount of charges remaining on the item")
                optional.."Skin"(SKIN_ID, "the ID of the skin applied to the item")
                optional.."Upgrades"(array(ITEM_ID), "the array of item IDs of runes or sigils applied to the item")
                optional.."Infusions"(array(ITEM_ID), "the array of item IDs of infusions applied to the item")
                optional.."Stats"(
                    description = "information about the stats chosen for the item (if the item offers the option to select stats/prefix)",
                    type = record(name = "Stats", description = "Information about an item's stats.") {
                        "Id"(INTEGER, "the itemstat ID")
                        optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                        optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                        optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                        optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                        optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                        optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                        optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                        optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                    }
                )
                optional.."Binding"(STRING, "the binding of the item")
            }
        ))
    }
    V2_ACCOUNT_JADEBOTS(
        summary = "Returns information about a player's unlocked jade bot skins.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each jade bot skin unlocked by the player"))
    }
    V2_ACCOUNT_LEGENDARYARMORY(
        summary = "Returns information about a player's legendary armory.",
        security = security(ACCOUNT, INVENTORIES, UNLOCKS)
    ) {
        schema(array(
            description = "the account's legendary armory unlocks.",
            items = record(name = "AccountLegendaryArmoryUnlock", description = "Information about a player's legendary armory item unlock.") {
                "Id"(ITEM_ID, "the item's ID")
                "Count"(INTEGER, "the number of copies unlocked")
            }
        ))
    }
    V2_ACCOUNT_LUCK(
        summary = "Returns information about a player's luck.",
        security = security(ACCOUNT, PROGRESSION, UNLOCKS)
    ) {
        schema(array(
            description = "the account's luck",
            items = record(name = "Luck", description = "Information about a player's luck.") {
                "Id"(STRING, "the type of luck (always \"luck\")")
                "Value"(INTEGER, "the amount of luck")
            }
        ))
    }
    V2_ACCOUNT_MAILCARRIERS(
        summary = "Returns information about a player's unlocked mail carriers.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each mail carrier unlocked by the player"))
    }
    V2_ACCOUNT_MAPCHESTS(
        summary = "Returns which Hero's Choice Chests a player has acquired since the most recent daily reset.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(STRING, "an array of IDs for Hero's Choice Chests that the player has acquired since the most recent daily reset"))
    }
    V2_ACCOUNT_MASTERIES(
        summary = "Returns information about a player's unlocked masteries.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(
            description = "A list of all masteries unlocked by an account.",
            items = record(name = "AccountMastery", description = "Information about a player's unlocked mastery.") {
                "Id"(INTEGER, "the mastery's ID")
                optional.."Level"(INTEGER, "the index of the unlocked mastery level")
            }
        ))
    }
    V2_ACCOUNT_MASTERY_POINTS(
        summary = "Returns information about a player's unlocked mastery points.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(record(name = "AccountMasteryPoint", description = "Information about a player's unlocked mastery points for a region.") {
            "Totals"(
                description = "information about the total mastery points for a region",
                type = array(record(name = "Total", description = "Information about the mastery points for a region.") {
                    "Region"(STRING, "the mastery region")
                    "Spent"(INTEGER, "the amount of mastery points of this region spent in mastery tracks")
                    "Earned"(INTEGER, "the amount of mastery points of this region earned for the account")
                })
            )
            "Unlocked"(array(INTEGER), "the list of IDs of unlocked mastery points")
        })
    }
    V2_ACCOUNT_MATERIALS(
        summary = "Returns information about the materials stored in a player's vault.",
        security = security(ACCOUNT, INVENTORIES)
    ) {
        schema(array(
            description = "A list of all materials in an account's vault.",
            items = record(name = "AccountMaterial", description = "Information about a stack of materials in a player's vault.") {
                "Id"(ITEM_ID, "the material's item ID")
                "Category"(INTEGER, "the material category the item belongs to")
                "Count"(INTEGER, "the number of the material that is stored in the player's vault")
                optional.."Binding"(STRING, "the binding of the material")
            }
        ))
    }
    V2_ACCOUNT_MINIS(
        summary = "Returns information about a player's unlocked miniatures.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each miniature unlocked by the player"))
    }
    V2_ACCOUNT_MOUNTS(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_ACCOUNT_MOUNTS_SKINS(
        summary = "Returns information about a player's unlocked mount skins.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(STRING, "an array of IDs containing the ID of each mount skin unlocked by the player"))
    }
    V2_ACCOUNT_MOUNTS_TYPES(
        summary = "Returns information about a player's unlocked mounts.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(STRING, "an array of IDs containing the ID of each mount unlocked by the player"))
    }
    V2_ACCOUNT_NOVELTIES(
        summary = "Returns information about a player's unlocked novelties.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each novelty unlocked by the player"))
    }
    V2_ACCOUNT_OUTFITS(
        summary = "Returns information about a player's unlocked outfits.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each outfit unlocked by the player"))
    }
    V2_ACCOUNT_PVP_HEROES(
        summary = "Returns information about a player's unlocked PvP heroes.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each PvP hero unlocked by the player"))
    }
    V2_ACCOUNT_RAIDS(
        summary = "Returns which raid encounter a player has cleared since the most recent raid reset.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(STRING, "an array of IDs containing the ID of each raid encounter that the player has cleared since the most recent raid reset"))
    }
    V2_ACCOUNT_RECIPES(
        summary = "Returns information about a player's unlocked recipes.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each recipe unlocked by the player"))
    }
    V2_ACCOUNT_SKIFFS(
        summary = "Returns information about a player's unlocked skiff skins.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each skiff skin unlocked by the player"))
    }
    V2_ACCOUNT_SKINS(
        summary = "Returns information about a player's unlocked skins.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(SKIN_ID, "an array of IDs containing the ID of each skin unlocked by the player"))
    }
    V2_ACCOUNT_TITLES(
        summary = "Returns information about a player's unlocked titles.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        schema(array(INTEGER, "an array of IDs containing the ID of each title unlocked by the player"))
    }
    V2_ACCOUNT_WALLET(
        summary = "Returns information about a player's wallet.",
        security = security(ACCOUNT, WALLET)
    ) {
        schema(array(
            description = "A list of all currencies in an account's wallet.",
            items = record(name = "AccountWalletCurrency", description = "Information about a currency in a player's wallet.") {
                "Id"(INTEGER, "the currency ID that can be resolved against /v2/currencies")
                "Value"(INTEGER, "the amount of this currency in the player's wallet")
            }
        ))
    }
    V2_ACCOUNT_WORLDBOSSES(
        summary = "Returns which world bosses that can be looted once per day a player has defeated since the most recent daily reset.",
        security = security(ACCOUNT, PROGRESSION)
    ) {
        schema(array(STRING, "an array of IDs for each world boss that can be looted once per day that the player has defeated since the most recent daily reset"))
    }
    V2_ACHIEVEMENTS(
        summary = "Returns information about achievements.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Achievement", description = "Information about an achievement.") {
            "Id"(ACHIEVEMENT_ID, "the achievement's ID")
            optional.."Icon"(STRING, "the URL for the achievement's icon")
            localized.."Name"(STRING, "the achievement's localized name")
            localized.."Description"(STRING, "the achievement's localized description")
            localized.."Requirement"(STRING, "the achievement's requirement as listed in-game")
            localized.."LockedText"(STRING, "the achievement's in-game description prior to unlocking it")
            "Type"(STRING, "the achievement's type")
            "Flags"(array(STRING), "the achievement's categories")
            "Tiers"(
                description = "the achievement's tiers",
                type = array(record(name = "Tier", description = "Information about an achievement's tier.") {
                    "Count"(INTEGER, "the number of \"things\" (achievement-specific) that must be completed to achieve this tier")
                    "Points"(INTEGER, "the amount of AP awarded for completing this tier")
                })
            )
            optional.."Prerequisites"(array(INTEGER), "the IDs of the achievements that are required to progress this achievement")
            optional.."Rewards"(
                description = "the achievement's rewards",
                type = array(conditional(
                    name = "Reward",
                    description = "Information about an achievement reward.",
                    sharedConfigure = {
                        "Type"(STRING, "the type of reward")
                    }
                ) {
                    +record(name = "Coins", description = "Information about a coin reward.") {
                        "Count"(INTEGER, "the amount of coins")
                    }
                    +record(name = "Items", description = "Information about an item reward.") {
                        "Id"(ITEM_ID, "the item's ID")
                        "Count"(INTEGER, "the amount of the item")
                    }
                    +record(name = "Mastery", description = "Information about a mastery point reward.") {
                        "Id"(INTEGER, "the mastery point's ID")
                        "Region"(STRING, "the mastery point's region")
                    }
                    +record(name = "Title", description = "Information about a title reward") {
                        "Id"(INTEGER, "the title's ID")
                    }
                })
            )
            optional.."Bits"(
                description = "the achievement's bits",
                type = array(record(name = "Bit", description = "Information about an achievement bit.") {
                    "Type"(STRING, "the bit's type")
                    optional.."Id"(INTEGER, "the ID of the bit's object")
                    optional.."Text"(STRING, "the bit's text")
                })
            )
            optional.."PointCap"(INTEGER, "the maximum number of AP that can be rewarded by an achievement flagged as \"Repeatable\"")
        })
    }
    V2_ACHIEVEMENTS_CATEGORIES(
        summary = "Returns information about achievement categories.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "AchievementCategory", description = "Information about an achievement category.") {
            "Id"(ACHIEVEMENT_CATEGORY_ID, "the achievement category's ID")
            "Icon"(STRING, "the URL for the achievement category's icon")
            localized.."Name"(STRING, "the achievement category's localized name")
            localized.."Description"(STRING, "the achievement category's localized description")
            "Order"(INTEGER, "a number that can be used to sort the list of categories")
            until(V2_SCHEMA_2022_03_23T19_00_00_000Z).."Achievements"(array(ACHIEVEMENT_ID), "an array containing the IDs of the achievements that this category contains")
            since(V2_SCHEMA_2022_03_23T19_00_00_000Z).."Achievements"(
                description = "an array containing information about the achievements that this category contains",
                type = array(record(name = "Entry", description = "An achievement entry of a category.") {
                    "Id"(ACHIEVEMENT_ID, "the achievement's ID")
                    optional.."Flags"(array(STRING), "additional informational flags")
                    optional.."RequiredAccess"(
                        description = "the access constraints for the achievement",
                        type = record(name = "AccessConstraint", description = "Information about the product requirements for an achievement.") {
                            "Product"(STRING, "the product")
                            "Condition"(
                                description = "the type of the condition",
                                type = enum(STRING, name = "ConditionType", description = "Information about a condition for an access constraint.") {
                                    "HasAccess"("the account has access")
                                    "NoAccess"("the account does not have access")
                                }
                            )
                        }
                    )
                    optional.."Level"(
                        description = "the level constraints for the achievement",
                        type = tuple(name = "LevelConstraint", description = "Information about the level requirements for an achievement.") {
                            "Minimum"(INTEGER, "the minimum level for the achievement")
                            "Maximum"(INTEGER, "the maximum level for the achievement")
                        }
                    )
                })
            )
        })
    }
    V2_ACHIEVEMENTS_DAILY(summary = "Returns information about daily achievements.") {
        schema(record(name = "AchievementsDaily", description = "Information about daily achievements.") {
            "Pve"(array(DAILY_ACHIEVEMENT), "the PvE achievements")
            "Pvp"(array(DAILY_ACHIEVEMENT), "the PvP achievements")
            "Wvw"(array(DAILY_ACHIEVEMENT), "the WvW achievements")
            "Fractals"(array(DAILY_ACHIEVEMENT), "the fractal achievements")
            "Special"(array(DAILY_ACHIEVEMENT), "the special achievements (e.g. festival dailies)")
        })
    }
    V2_ACHIEVEMENTS_DAILY_TOMORROW(summary = "Returns information about tomorrow's daily achievements.") {
        schema(record(name = "AchievementsDailyTomorrow", description = "Information about daily achievements.") {
            "Pve"(array(DAILY_ACHIEVEMENT), "the PvE achievements")
            "Pvp"(array(DAILY_ACHIEVEMENT), "the PvP achievements")
            "Wvw"(array(DAILY_ACHIEVEMENT), "the WvW achievements")
            "Fractals"(array(DAILY_ACHIEVEMENT), "the fractal achievements")
            "Special"(array(DAILY_ACHIEVEMENT), "the special achievements (e.g. festival dailies)")
        })
    }
    V2_ACHIEVEMENTS_GROUPS(
        summary = "Returns information about achievement groups.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "AchievementGroups", description = "Information about an achievement group.") {
            "Id"(ACHIEVEMENT_GROUP_ID, "the achievement group's ID")
            localized.."Name"(STRING, "the achievement group's localized name")
            localized.."Description"(STRING, "the achievement group's localized description")
            "Order"(INTEGER, "a number that can be used to sort the list of groups")
            "Categories"(array(ACHIEVEMENT_CATEGORY_ID), "an array containing the IDs of the categories that this group contains")
        })
    }
    V2_BACKSTORY(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_BACKSTORY_ANSWERS(
        summary = "Returns information about biography answers.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "BackstoryAnswer", description = "Information about a biography answer.") {
            "Id"(STRING, "the answer's ID")
            localized.."Title"(STRING, "the answer's localized title")
            localized.."Description"(STRING, "the answer's localized description")
            localized.."Journal"(STRING, "the answer's localized journal entry")
            "Question"(INTEGER, "the ID of the biography question the answer answers")
            optional.."Professions"(array(STRING), "the IDs of the professions that the answer is available for")
            optional.."Races"(array(STRING), "the IDs of the races that the answer is available for")
        })
    }
    V2_BACKSTORY_QUESTIONS(
        summary = "Returns information about biography questions.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "BackstoryQuestion", description = "Information about a biography question.") {
            "Id"(INTEGER, "the question's ID")
            localized.."Title"(STRING, "the question's localized title")
            localized.."Description"(STRING, "the question's localized description")
            "Answers"(array(STRING), "the IDs of the possible answers to the question")
            "Order"(INTEGER, "a number that can be used to sort the list of questions")
            optional.."Professions"(array(STRING), "the IDs of the professions that the question is presented to")
            optional.."Races"(array(STRING), "the IDs of the races that the question is presented to")
        })
    }
    V2_BUILD(summary = "Returns the current build ID.") {
        schema(record(name = "Build", description = "Information about the current game build.") {
            "Id"(BUILD_ID, "the current build ID")
        })
    }
    V2_CHARACTERS(
        idTypeKey = "name",
        summary = "Returns information about characters.",
        queryTypes = defaultQueryTypes(all = true),
        security = security(ACCOUNT, CHARACTERS)
    ) {
        schema(record(name = "Character", description = "Information about a character.") {
            CHARACTERS_CORE()

            "WvwAbilities"(
                description = "information about the WvW abilities of the character",
                type = array(record(name = "WvwAbility", description = "Information about a character's WvW ability.") {
                    "Id"(INTEGER, "the ability's ID")
                    "Rank"(INTEGER, "the ability's rank")
                })
            )
            until(V2_SCHEMA_2021_04_06T21_00_00_000Z).."EquipmentPvp"(
                description = "information about the character's PvP equipment",
                type = record(name = "EquipmentPvp", description = "Information about a character's PvP equipment.") {
                    optional.."Amulet"(INTEGER, "the ID of the character's PvP amulet")
                    optional.."Rune"(INTEGER, "the ID of the character's PvP rune")
                    "Sigils"(array(INTEGER, nullableItems = true), "the IDs of the character's PvP sigils")
                }
            )
            "Flags"(array(STRING), "various additional flags")

            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."BuildTabsUnlocked"(INTEGER, "the number of build tabs unlocked for the character")
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."ActiveBuildTab"(INTEGER, "the ID of the character's active build tab")
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."EquipmentTabsUnlocked"(INTEGER, "the number of equipment tabs unlocked for the character")
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."ActiveEquipmentTab"(INTEGER, "the ID of the character's active equipment tab")

            CHARACTERS_BACKSTORY()
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."BuildTabs"(array(CHARACTERS_BUILDTAB), "the character's build tabs")
            CHARACTERS_CRAFTING()
            CHARACTERS_EQUIPMENT()
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."EquipmentTabs"(
                description = "the character's equipment tabs",
                type = array(record(name = "CharactersEquipmentTab", description = "Information about a character's equipment tab.") {
                    "Tab"(INTEGER, "the tab's ID")
                    "Name"(STRING, "the equipment configuration's name")
                    "IsActive"(BOOLEAN, "a flag indicating whether this tab is the active tab")
                    "Equipment"(
                        description = "the stored equipment",
                        type = array(record(name = "Equipment", description = "Information about a piece of equipment.") {
                            "Id"(ITEM_ID, "the equipped item's ID")
                            "Slot"(STRING, "the slot in which the equipment piece is slotted into")
                            optional.."Skin"(SKIN_ID, "the ID of the skin transmuted onto the equipment piece")
                            optional.."Dyes"(array(INTEGER, nullableItems = true), "the IDs of the dyes applied to the item")
                            optional.."Upgrades"(array(ITEM_ID), "the IDs of the upgrade components slotted into the item")
                            optional.."Infusions"(array(ITEM_ID), "the IDs of the infusions slotted into the item")
                            optional.."Binding"(STRING, "the binding of the item")
                            optional.."BoundTo"(STRING, "name of the character the item is bound to")
                            "Location"(STRING, "the storage location of the equipment piece")
                            optional.."Stats"(
                                description = "information about the stats chosen for the item (if the item offers the option to select stats/prefix)",
                                type = record(name = "Stats", description = "Information about an item's stats.") {
                                    "Id"(INTEGER, "the itemstat ID")
                                    optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the item")
                                    optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the item")
                                    optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the item")
                                    optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the item")
                                    optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the item")
                                    optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the item")
                                    optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the item")
                                    optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the item")
                                }
                            )
                        })
                    )
                    since(V2_SCHEMA_2021_04_06T21_00_00_000Z).."EquipmentPvp"(
                        description = "the character's PvP equipment",
                        type = record(name = "PvpEquipment", "Information about a character's PvP equipment.") {
                            "Amulet"(INTEGER, "the ID of the selected amulet")
                            "Rune"(INTEGER, "the ID of the selected rune")
                            "Sigils"(array(INTEGER, nullableItems = true), "the IDs of the selected sigils")
                        }
                    )
                })
            )
            CHARACTERS_INVENTORY()
            "Recipes"(array(INTEGER), "the IDs of the character's crafting recipes")
            until(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Skills"(CHARACTERS_SKILLS, "the character's equipped skills")
            until(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Specializations"(CHARACTERS_SPECIALIZATIONS, "the character's equipped specializations")
            CHARACTERS_TRAINING()
        })
    }
    V2_CHARACTERS_BACKSTORY(
        summary = "Returns information about a character's backstory.",
        security = security(ACCOUNT, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersBackstory", description = "Information about a character's backstory.") {
            CHARACTERS_BACKSTORY()
        })
    }
    V2_CHARACTERS_BUILDTABS(
        idTypeKey = "tab",
        summary = "Returns information about a character's equipped specializations.",
        queryTypes = queryTypes(
            IDS,
            BY_ID("Tab", "the ID of the requested tab"),
            BY_IDS("Tabs", "the IDs of the requested tabs"),
            BY_PAGE
        ),
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(CHARACTERS_BUILDTAB)
    }
    V2_CHARACTERS_BUILDTABS_ACTIVE(
        summary = "Returns information about a character's current build.",
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(CHARACTERS_EQUIPMENTTAB)
    }
    V2_CHARACTERS_CORE(
        summary = "Returns general information about a character.",
        security = security(ACCOUNT, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersCore", description = "General Information about a character.") {
            CHARACTERS_CORE()
        })
    }
    V2_CHARACTERS_CRAFTING(
        summary = "Returns information about a character's crafting disciplines.",
        security = security(ACCOUNT, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersCrafting", description = "Information about a character's crafting disciplines.") {
            CHARACTERS_CRAFTING()
        })
    }
    V2_CHARACTERS_EQUIPMENT(
        summary = "Returns information about a character's equipment.",
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersEquipment", description = "Information about a character's equipment.") {
            CHARACTERS_EQUIPMENT()
        })
    }
    V2_CHARACTERS_EQUIPMENTTABS(
        idTypeKey = "tab",
        summary = "Returns information about a character's equipment.",
        queryTypes = queryTypes(
            IDS,
            BY_ID("Tab", "the ID of the requested tab"),
            BY_IDS("Tabs", "the IDs of the requested tabs"),
            BY_PAGE
        ),
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(CHARACTERS_EQUIPMENTTAB)
    }
    V2_CHARACTERS_EQUIPMENTTABS_ACTIVE(
        summary = "Returns information about a character's current equipment.",
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(CHARACTERS_EQUIPMENTTAB)
    }
    V2_CHARACTERS_HEROPOINTS(
        summary = "Returns information about a character's unlock hero points.",
        security = security(ACCOUNT, CHARACTERS, PROGRESSION)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(array(STRING, "the IDs of the heropoints unlocked by the character"))
    }
    V2_CHARACTERS_INVENTORY(
        summary = "Returns information about a character's inventory.",
        security = security(ACCOUNT, CHARACTERS, INVENTORIES)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersInventorySlot", description = "Information about a bag in a character's inventory.") {
            CHARACTERS_INVENTORY()
        })
    }
    V2_CHARACTERS_QUESTS(
        summary = "Returns information about a character's selected quests.",
        security = security(ACCOUNT, CHARACTERS, PROGRESSION)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(array(INTEGER, "the IDs of the quests selected by the character"))
    }
    V2_CHARACTERS_RECIPES(
        summary = "Returns information about a character's crafting recipes.",
        security = security(ACCOUNT, UNLOCKS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersRecipes", description = "Information about a character's crafting recipes.") {
            "Recipes"(array(INTEGER), "the IDs of the character's crafting recipes")
        })
    }
    V2_CHARACTERS_SAB(
        summary = "Returns information about a character's Super Adventure Box (SAB) progression.",
        security = security(ACCOUNT, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersSAB", description = "Information about a character's Super Adventure Box (SAB) progression.") {
            "Zones"(
                description = "the character's completed zones",
                type = array(record(name = "Zone", description = "Information about a zone completed by a character.") {
                    "Id"(INTEGER, "the zone's ID")
                    "Mode"(STRING, "the mode used when completing this zone")
                    "World"(INTEGER, "the world this zone is in")
                    "Zone"(INTEGER, "the zone's number")
                })
            )
            "Unlocks"(
                description = "the character's unlocked unlocks",
                type = array(record(name = "Unlock", description = "Information about an unlock unlocked by a character.") {
                    "Id"(INTEGER, "the unlock's ID")
                    optional.."Name"(STRING, "an unlocalized name describing the unlock")
                })
            )
            "Songs"(
                description = "the character's unlocked songs",
                type = array(record(name = "Song", description = "Information about a song unlocked by a character.") {
                    "Id"(INTEGER, "the song's ID")
                    optional.."Name"(STRING, "an unlocalized name describing the song")
                })
            )
        })
    }
    V2_CHARACTERS_SKILLS(
        summary = "Returns information about a character's equipped skills.",
        security = security(ACCOUNT, BUILDS, CHARACTERS),
        until = V2_SCHEMA_2019_12_19T00_00_00_000Z
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersSkills", description = "Information about a character's equipped skills.") {
            "Skills"(CHARACTERS_SKILLS, "the character's equipped skills")
        })
    }
    V2_CHARACTERS_SPECIALIZATIONS(
        summary = "Returns information about a character's equipped specializations.",
        security = security(ACCOUNT, BUILDS, CHARACTERS),
        until = V2_SCHEMA_2019_12_19T00_00_00_000Z
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersSpecializations", description = "Information about a character's equipped specializations.") {
            "Specializations"(CHARACTERS_SPECIALIZATIONS, "the character's equipped specializations")
        })
    }
    V2_CHARACTERS_TRAINING(
        summary = "Returns information about a character's (skill-tree) training.",
        security = security(ACCOUNT, BUILDS, CHARACTERS)
    ) {
        pathParameter("Id", STRING, "the character's ID")

        schema(record(name = "CharactersTraining", description = "Information about a character's (skill-tree) training.") {
            CHARACTERS_TRAINING()
        })
    }
    V2_COLORS(
        summary = "Returns information about all dye colors in the game.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Color", description = "Information about a dye color.") {
            val APPEARANCE = record(name = "Appearance", description = "Information about the appearance of the color.") {
                "Brightness"(INTEGER, "the brightness")
                "Contrast"(DECIMAL, "the contrast")
                "Hue"(INTEGER, "the hue in HSL colorspace")
                "Saturation"(DECIMAL, "the saturation in HSL colorspace")
                "Lightness"(DECIMAL, "the lightness in HSL colorspace")
                "Rgb"(array(INTEGER), "a list containing precalculated RGB values")
            }

            "Id"(INTEGER, "the color's ID")
            localized.."Name"(STRING, "the color's name")
            "BaseRgb"(array(INTEGER), "the base RGB values")
            "Cloth"(APPEARANCE, "detailed information on its appearance when applied on cloth armor")
            "Leather"(APPEARANCE, "detailed information on its appearance when applied on leather armor")
            "Metal"(APPEARANCE, "detailed information on its appearance when applied on metal armor")
            optional.."Fur"(APPEARANCE, "detailed information on its appearance when applied on fur armor")
            optional.."Item"(INTEGER, "the ID of the dye item")
            optional.."Categories"(
                description = "the categories of the color",
                type = tuple(name = "Categories", description = "The color's categories.") {
                    "Hue"(
                        description = "the color's hue",
                        type = enum(STRING, name = "Hue", description = "A hue.") {
                            "Blue"(description = "Blue")
                            "Brown"(description = "Brown")
                            "Gray"(description = "Gray")
                            "Green"(description = "Green")
                            "Orange"(description = "Orange")
                            "Purple"(description = "Purple")
                            "Red"(description = "Red")
                            "Yellow"(description = "Yellow")
                        }
                    )
                    "Material"(
                        description = "the color's material",
                        type = enum(STRING, "Material", description = "The material of a color.") {
                            "Vibrant"("Vibrant")
                            "Leather"("Leather")
                            "Metal"("Metal")
                        }
                    )
                    "Rarity"(
                        description = "the color's rarity",
                        type = enum(STRING, "Rarity", description = "The rarity of a color.") {
                            "Starter"("Indicates that the color is unlocked from the start.")
                            "Common"("Common rarity.")
                            "Uncommon"("Uncommon rarity.")
                            "Rare"("Rare rarity.")
                            "Exclusive"("Exclusive rarity.")
                        }
                    )
                }
            )
        })
    }
    V2_COMMERCE_DELIVERY(
        summary = "Returns information about the items and coins currently available for pickup.",
        security = security(ACCOUNT, TRADING_POST)
    ) {
        schema(record(name = "CommerceDelivery", description = "Information about the items and coins currently available for pickup.") {
            "Coins"(INTEGER, "the amount of coins ready for pickup")
            "Items"(
                description = "the items ready for pickup",
                type = array(record(name = "Item", description = "Information about an item ready for pickup.") {
                    "Id"(ITEM_ID, "the item's ID")
                    "Count"(INTEGER, "the amount of this item ready for pickup")
                })
            )
        })
    }
    V2_COMMERCE_EXCHANGE(
        summary = "Returns information about the gem exchange.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_COMMERCE_EXCHANGE(
        path = "/commerce/exchange/:type",
        summary = "Returns information about the gem exchange."
    ) {
        pathParameter("Type", STRING, "the exchange type")
        queryParameter("Quantity", INTEGER, "the amount to exchange")

        schema(record(name = "CommerceExchange", description = "Information about an exchange.") {
            "CoinsPerGem"(INTEGER, "the number of coins received/required for a single gem")
            "Quantity"(INTEGER, "the number of coins/gems for received for the specified quantity of gems/coins")
        })
    }
    V2_COMMERCE_LISTINGS(
        summary = "Returns current buy and sell listings from the trading post.",
        queryTypes = defaultQueryTypes()
    ) {
        val LISTING = record(name = "Listing", description = "Information about a listing.") {
            "Listings"(INTEGER, "the number of individual listings this object refers to (e.g. two players selling at the same price will end up in the same listing)")
            "UnitPrice"(INTEGER, "the sell offer or buy order price in coins")
            "Quantity"(INTEGER, "the amount of items being sold/bought in this listing")
        }

        schema(record(name = "CommerceListing", description = "Information about an item listed in the trading post.") {
            "Id"(ITEM_ID, "the item's ID")
            "Buys"(array(LISTING), "list of all buy listings")
            "Sells"(array(LISTING), "list of all sell listings")
        })
    }
    V2_COMMERCE_PRICES(
        summary = "Returns current aggregated buy and sell listing information from the trading post.",
        queryTypes = defaultQueryTypes()
    ) {
        schema(record(name = "CommercePrices", description = "Information about an item listed in the trading post.") {
            "Id"(ITEM_ID, "the item's ID")
            "Whitelisted"(BOOLEAN, "indicates whether a free to play account can purchase or sell this item on the trading post")
            "Buys"(
                description = "the buy information",
                type = record(name = "BuyListing", description = "Information about an item's buy listing.") {
                    "UnitPrice"(INTEGER, "the highest buy order price in coins")
                    "Quantity"(INTEGER, "the amount of items being bought")
                }
            )
            "Sells"(
                description = "the sell information",
                type = record(name = "SellListing", description = "Information about an item's sell listing.") {
                    "UnitPrice"(INTEGER, "the lowest sell order price in coins")
                    "Quantity"(INTEGER, "the amount of items being sold")
                }
            )
        })
    }
    V2_COMMERCE_TRANSACTIONS(
        summary = "Returns information about an account's transactions.",
        cache = Duration.INFINITE, // We don't expect this to change. Ever.
        security = security(ACCOUNT, TRADING_POST)
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_COMMERCE_TRANSACTIONS(
        path = "/commerce/transactions/:relevance",
        summary = "Returns information about an account's transactions.",
        cache = Duration.INFINITE, // We don't expect this to change. Ever.
        security = security(ACCOUNT, TRADING_POST)
    ) {
        pathParameter("Relevance", STRING, "the temporal relevance")

        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_COMMERCE_TRANSACTIONS(
        path = "/commerce/transactions/:relevance/:type",
        summary = "Returns information about an account's transactions.",
        queryTypes = queryTypes(BY_PAGE),
        cache = 1.minutes,
        security = security(ACCOUNT, TRADING_POST)
    ) {
        pathParameter("Relevance", STRING, "the temporal relevance")
        pathParameter("Type", STRING, "the transaction type")

        schema(record(name = "CommerceTransaction", description = "Information about a transaction.") {
            "Id"(INTEGER, "the transaction's ID")
            "ItemId"(ITEM_ID, "the item's ID")
            "Price"(INTEGER, "the price in coins")
            "Quantity"(INTEGER, "the quantity of the item")
            "Created"(STRING, "the ISO-8601 standard timestamp of when the transaction was created")
            optional.."Purchased"(STRING, "the ISO-8601 standard timestamp of when the transaction was completed")
        })
    }
    V2_CONTINENTS(
        summary = "Returns information about continents.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Continent", description = "Information about a continent.") {
            "Id"(INTEGER, "the continent's ID")
            localized.."Name"(STRING, "the continent's name")
            "ContinentDims"(array(INTEGER), "the width and height of the continent")
            "MinZoom"(INTEGER, "the minimal zoom level for use with the map tile service")
            "MaxZoom"(INTEGER, "the maximum zoom level for use with the map tile service")
            "Floors"(array(INTEGER), "the IDs of the continent's floors")
        })
    }
    V2_CONTINENTS_FLOORS(
        summary = "Returns information about a continent's floors.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        pathParameter("ContinentId", INTEGER, "the continent's ID", key = "id")

        schema(record(name = "ContinentFloor", description = "Information about a continent floor.") {
            "Id"(INTEGER, "the floor's ID")
            "TextureDims"(array(INTEGER), "the width and height of the texture")
            optional.."ClampedView"(array(array(INTEGER)), "a rectangle of downloadable textures (Every tile coordinate outside this rectangle is not available on the tile server.)")
            "Regions"(
                description = "the floor's regions",
                type = map(
                    keys = INTEGER,
                    values = record(name = "Region", description = "Information about a region.") {
                        "Id"(INTEGER, "the region's ID")
                        localized.."Name"(STRING, "the region's localized name")
                        "LabelCoord"(array(DECIMAL), "the coordinate of the region's label")
                        "ContinentRect"(array(array(INTEGER)), "the dimensions of the region, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                        "Maps"(
                            description = "the region's maps",
                            type = map(
                                keys = INTEGER,
                                values = record(name = "Map", description = "Information about a map.") {
                                    "Id"(INTEGER, "the map's ID")
                                    localized.."Name"(STRING, "the map's localized name")
                                    "MinLevel"(INTEGER, "the minimum level of the map")
                                    "MaxLevel"(INTEGER, "the maximum level of the map")
                                    "DefaultFloor"(INTEGER, "the ID of the map's default floor")
                                    "MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
                                    "ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
                                    "LabelCoord"(array(DECIMAL), "the coordinate of the map's label")
                                    "PointsOfInterest"(
                                        description = "the points of interest on the floor (i.e. landmarks, vistas and waypoints)",
                                        type = map(
                                            keys = INTEGER,
                                            values = record(name = "PointOfInterest", description = "Information about a point of interest (i.e. a landmark, vista or waypoint).") {
                                                "Id"(INTEGER, "the PoI's ID")
                                                optional..localized.."Name"(STRING, "the PoI's localized name")
                                                "Type"(STRING, "the type of the PoI (landmark, vista, or waypoint)")
                                                "ChatLink"(STRING, "the chat link")
                                                "Floor"(INTEGER, "the PoI's floor")
                                                "Coord"(array(DECIMAL), "the PoI's coordinates")
                                                optional.."Icon"(STRING, "the PoI's icon")
                                            }
                                        )
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
                                        type = map(
                                            keys = INTEGER,
                                            values = record(name = "Task", description = "Information about a task.") {
                                                "Id"(INTEGER, "the task's ID")
                                                localized.."Objective"(STRING, "the adventure's localized objective")
                                                "Level"(INTEGER, "the task's level")
                                                "ChatLink"(STRING, "the chat link")
                                                "Coord"(array(DECIMAL), "the task's coordinates")
                                                "Bounds"(array(array(DECIMAL)), "the task's bounds")
                                            }
                                        )
                                    )
                                    "SkillChallenges"(
                                        description = "the skill challenges on the floor",
                                        type = array(record(name = "SkillChallenge", description = "Information about a skill challenge.") {
                                            "Id"(STRING, "the skill challenge's ID")
                                            "Coord"(array(DECIMAL), "the skill challenge's coordinates")
                                        })
                                    )
                                    "Sectors"(
                                        description = "the sectors on the floor",
                                        type = map(
                                            keys = INTEGER,
                                            values = record(name = "Sector", description = "Information about a sector.") {
                                                "Id"(INTEGER, "the sector's ID")
                                                optional..localized.."Name"(STRING, "the sector's localized name")
                                                "Level"(INTEGER, "the sector's level")
                                                "Coord"(array(DECIMAL), "the sector's coordinates")
                                                "Bounds"(array(array(DECIMAL)), "the sector's bounds")
                                                "ChatLink"(STRING, "the chat link")
                                            }
                                        )
                                    )
                                    "Adventures"(
                                        description = "the adventures on the floor",
                                        type = array(record(name = "Adventure", description = "Information about an adventure.") {
                                            "Id"(STRING, "the adventure's ID")
                                            localized.."Name"(STRING, "the adventure's localized name")
                                            localized.."Description"(STRING, "the adventure's localized description")
                                            "Coord"(array(DECIMAL), "the adventure's coordinates")
                                        })
                                    )
                                    "MasteryPoints"(
                                        description = "the mastery points on the floor",
                                        type = array(record(name = "MasteryPoint", description = "Information about a mastery point.") {
                                            "Id"(INTEGER, "the mastery point's ID")
                                            "Region"(STRING, "the mastery region")
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
    V2_CREATESUBTOKEN(
        summary = "Creates a new subtoken.",
        security = security(ACCOUNT)
    ) {
        queryParameter("Expire", STRING, "an ISO-8601 datetime specifying when the generated subtoken will expire")
        queryParameter("Permissions", STRING, "a comma separated list of permissions to inherit")
        queryParameter("Urls", STRING, "a comma separated list of endpoints that will be accessible using this subtoken", isOptional = true)

        schema(record(name = "SubToken", description = "A created subtoken.") {
            "Subtoken"(STRING, "a JWT which can be used like an API key")
        })
    }
    V2_CURRENCIES(
        summary = "Returns information about currencies contained in the account wallet.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Currency", description = "Information about a currency.") {
            "Id"(INTEGER, "the currency's ID")
            localized.."Name"(STRING, "the currency's name")
            localized.."Description"(STRING, "a description of the currency")
            "Icon"(STRING, "the currency's icon")
            "Order"(INTEGER, "a number that can be used to sort the list of currencies")
        })
    }
    V2_DAILYCRAFTING(
        summary = "Returns information about the items that can be crafted once per day.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "DailyCrafting", description = "Information about an item that can be crafted once per day.") {
            "Id"(STRING, "the ID of the dailycrafting")
        })
    }
    V2_DUNGEONS(
        summary = "Returns information about the dungeons in the game.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Dungeon", description = "Information about a dungeon.") {
            "Id"(STRING, "the dungeon's ID")
            "Paths"(
                description = "the dungeon's paths",
                type = array(record(name = "Path", description = "Information about a dungeon path.") {
                    "Id"(STRING, "the path's ID")
                    "Type"(STRING, "the path's type")
                })
            )
        })
    }
    V2_EMBLEM(
        summary = "Returns information about guild emblem assets.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_EMBLEM(
        path = "/emblem/:type",
        summary = "Returns information about guild emblem assets.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        pathParameter("Type", STRING, "the layer for the emblem parts")

        schema(record(name = "EmblemPart", description = "Information about an emblem part.") {
            "Id"(INTEGER, "the emblem part's ID")
            "Layers"(array(STRING), "an array of URLs to images that make up the various parts of the emblem")
        })
    }
    V2_EMOTES(
        summary = "Returns information about unlockable emotes.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Emote", description = "Information about an unlockable emote.") {
            "Id"(STRING, "the emote's ID")
            "Commands"(
                description = "the commands that may be used to trigger the emote",
                type = array(STRING)
            )
            "UnlockItems"(
                description = "the IDs of the items that can be used to unlock the emote",
                type = array(ITEM_ID)
            )
        })
    }
    V2_FILES(
        summary = "Returns commonly requested in-game assets.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "File", description = "Information about an in-game asset.") {
            "Id"(STRING, "the file identifier")
            "Icon"(STRING, "the URL to the image")
        })
    }
    V2_FINISHERS(
        summary = "Returns information about finishers.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Finisher", description = "Information about a finisher.") {
            "Id"(INTEGER, "the finisher's ID")
            localized.."Name"(STRING, "the finisher's name")
            "Icon"(STRING, "the URL for the finisher's icon")
            "Order"(INTEGER, "a (non-unique) number that can be used as basis to sort the list of finishers")
            "UnlockDetails"(STRING, "a description explaining how to acquire the finisher")
            "UnlockItems"(array(ITEM_ID), "an array of item IDs used to unlock the finisher")
        })
    }
    V2_GLIDERS(
        summary = "Returns information about gliders.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Glider", description = "Information about a glider.") {
            "Id"(INTEGER, "the glider's ID")
            localized.."Name"(STRING, "the glider's name")
            localized.."Description"(STRING, "the glider's description")
            "Icon"(STRING, "the URL for the glider's icon")
            "Order"(INTEGER, "a (non-unique) number that can be used as basis to sort the list of gliders")
            "DefaultDyes"(array(INTEGER), "the IDs of the dyes that are applied to the glider by default")
            "UnlockItems"(array(ITEM_ID), "an array of item IDs used to unlock the glider")
        })
    }
    V2_GUILD(summary = "Returns information about a guild.") {
        pathParameter("Id", STRING, "the guild's ID")

        schema(record(name = "Guild", description = "Information about a guild.") {
            "Id"(STRING, "the guild's ID")
            "Name"(STRING, "the guild's name")
            "Tag"(STRING, "the guild's tag")
            "Level"(INTEGER, "the guild's level")
            optional(GUILDS).."Motd"(STRING, "the guild's message of the day")
            optional(GUILDS).."Influence"(INTEGER, "the guild's current influence")
            optional(GUILDS).."Aetherium"(INTEGER, "the guild's current aetherium")
            optional(GUILDS).."Favor"(INTEGER, "the guild's current favor")
            optional(GUILDS).."Resonance"(INTEGER, "the guild's current resonance")
            optional(GUILDS).."MemberCount"(INTEGER, "the guild's current member count")
            optional(GUILDS).."MemberCapacity"(INTEGER, "the guild's current member capacity")
            "Emblem"(
                description = "the guild's emblem",
                type = record(name = "Emblem", description = "") {
                    "Background"(
                        description = "the emblem's background",
                        type = record(name = "Background", description = "Information about a guild emblem's background.") {
                            "Id"(INTEGER, "the background's ID")
                            "Colors"(array(INTEGER), "the background's colors")
                        }
                    )
                    "Foreground"(
                        description = "the emblem's foreground",
                        type = record(name = "Foreground", description = "Information about a guild emblem's foreground.") {
                            "Id"(INTEGER, "the foreground's ID")
                            "Colors"(array(INTEGER), "the foreground's colors")
                        }
                    )
                    "Flags"(array(STRING), "the manipulations applied to the emblem")
                }
            )
        })
    }
    V2_GUILD_LOG(
        summary = "Returns information about events in a guild's log.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's log entries",
            items = conditional(
                name = "GuildLogEntry",
                description = "Information about a logged guild event.",
                sharedConfigure = {
                    "Id"(INTEGER, "the log entry's ID")
                    "Time"(STRING, "ISO-8601 timestamp for when the log entry was created")
                    optional.."User"(STRING, "the account name of the guild member who generated this log entry")
                    "Type"(STRING, "the type of log entry")
                }
            ) {
                "joined"(record(name = "Joined", description = "A log entry indicating that the user joined the guild.") {})
                "invited"(record(name = "Invited", description = "A log entry indicating that the user has been invited to the guild.") {
                    "InvitedBy"(STRING, "the account name of the guild member who invited the user")
                })
                "kick"(record(name = "Kick", description = "A log entry indicating that the user has been kicked from the guild.") {
                    "KickedBy"(STRING, "the account name of the guild member who kicked the user")
                })
                "rank_change"(record(name = "RankChange", description = "A log entry indicating that the rank for the user changed.") {
                    "ChangedBy"(STRING, "the account name of the guild member who changed the rank of the user")
                    "OldRank"(STRING, "the name of the old rank")
                    "NewRank"(STRING, "the name of the new rank")
                })
                "treasury"(record(name = "Treasury", description = "A log entry indicating that the user has deposited an item into the guild's treasury.") {
                    "ItemId"(INTEGER, "the item's ID")
                    "Count"(INTEGER, "how many of the item was deposited")
                })
                "stash"(record(name = "Stash", description = "A log entry indicating that the user has deposited/withdrawn an item into the guild stash.") {
                    "Operation"(STRING, "the action (may be \"deposit\", \"withdraw\" or \"move\"")
                    "ItemId"(INTEGER, "the item's ID")
                    "Count"(INTEGER, "how many of the item was deposited")
                    "Coins"(INTEGER, "the amount of deposited coins")
                })
                "motd"(record(name = "Motd", description = "A log entry indicating that the user has changed the guild's MOTD.") {
                    "Motd"(STRING, "the new message of the day")
                })
                "upgrade"(record(name = "Upgrade", description = "A log entry indicating that the user has interacted with a guild upgrade.") {
                    "Action"(STRING, "the action (may be \"queued\", \"cancelled\", \"completed\" or \"sped_up\"")
                    optional.."Count"(INTEGER, "how many upgrade were added")
                    "UpgradeId"(INTEGER, "the ID of the completed upgrade")
                    optional.."RecipeId"(INTEGER, "the recipe that generated the upgrade")
                })
            }
        ))
    }
    V2_GUILD_MEMBERS(
        summary = "Returns information about a guild's members.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's members",
            items = record(name = "GuildMember", description = "Information about a guild member.") {
                "Name"(STRING, "the member's account name")
                "Rank"(STRING, "the member's rank")
                "Joined"(STRING, "the ISO-8601 timestamp of when the member joined the guild")
            }
        ))
    }
    V2_GUILD_RANKS(
        summary = "Returns information about a guild's ranks.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's ranks",
            items = record(name = "GuildRank", description = "Information about a guild rank.") {
                "Id"(STRING, "the rank's ID")
                "Order"(INTEGER, "a number that can be used to sort the list of ranks")
                "Permissions"(array(STRING), "the rank's permissions")
                "Icon"(STRING, "a render service URL for the rank's icon")
            }
        ))
    }
    V2_GUILD_STASH(
        summary = "Returns information about the items in a guild's vault.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's vault items",
            items = record(name = "GuildStashSection", description = "Information about a section of a guild's vault.") {
                "UpgradeId"(INTEGER, "the ID of the guild upgrade that granted access to this section of the guild's vault")
                "Size"(INTEGER, "the number of slots in this section of the guild's vault")
                "Coins"(INTEGER, "the number of coins deposited in this section of the guild's vault")
                "Note"(STRING, "the description set for this section of the guild's vault")
                "Inventory"(
                    description = "the items in this section of the guild's vault",
                    type = array(
                        nullableItems = true,
                        items = record(name = "GuildStashSlot", description = "Information about an item in a guild vault's slot.") {
                            "Id"(ITEM_ID, "the item's ID")
                            "Count"(INTEGER, "the amount of items in the stack")
                        }
                    )
                )
            }
        ))
    }
    V2_GUILD_STORAGE(
        summary = "Returns information about a guild's storage.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's storage items",
            items = record(name = "GuildStorageSlot", description = "Information about an item in a guild's storage.") {
                "Id"(INTEGER, "the guild upgrade's ID")
                "Count"(INTEGER, "the amount of the upgrade in the guild's treasury")
            }
        ))
    }
    V2_GUILD_TEAMS(
        summary = "Returns information about a guild's PvP teams.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's PvP teams",
            items = record(name = "GuildTeam", description = "Information about a guild's PvP team.") {
                val STATS = record(name = "Stats", description = "Information about category-specific PvP stats.") {
                    "Wins"(INTEGER, "the amount of wins")
                    "Losses"(INTEGER, "the amount of losses")
                    "Desertions"(INTEGER, "the amount desertions")
                    "Byes"(INTEGER, "the amount of byes")
                    "Forfeits"(INTEGER, "the amount of forfeits")
                }

                "Id"(INTEGER, "the team's ID (only unique within the guild)")
                "Members"(
                    description = "the team's members",
                    type = array(record(name = "Member", description = "Information about a team member.") {
                        "Name"(STRING, "the member's account name")
                        "Role"(STRING, "the member's role (i.e. \"Captain\" or \"Member\")")
                    })
                )
                "Name"(STRING, "the team's name")
                "Aggregate"(STATS, "the aggregated statistics")
                "Ladders"(map(STRING, STATS), "the stats by ladder (e.g. \"ranked\", \"unranked\")")
                "Games"(
                    description = "the team's recent PvP games",
                    type = array(record(name = "PvpGame", description = "Information about a team's PvP game.") {
                        "Id"(STRING, "the game's ID")
                        "MapId"(INTEGER, "the map's ID")
                        "Started"(STRING, "the ISO-8601 standard timestamp of when the game started")
                        "Ended"(STRING, "the ISO-8601 standard timestamp of when the game ended")
                        "Result"(STRING, "the game's result for the team (\"Victory\" or \"Defeat\")")
                        "Team"(STRING, "the team's color (\"Blue\" or \"Red\")")
                        "RatingType"(STRING, "the type of rating of the game")
                        optional.."RatingChange"(INTEGER, "the change in rating for the team")
                        optional.."Season"(STRING, "the ID of the game's PvP season")
                        "Scores"(
                            description = "the game's final scores",
                            type = record(name = "Scores", description = "Information about a PvP game's scores.") {
                                "Red"(INTEGER, "the red team's score")
                                "Blue"(INTEGER, "the blue team's score")
                            }
                        )
                    }
                ))
                "Seasons"(
                    description = "the team's season-specific stats",
                    type = array(record(name = "SeasonStats", description = "Information about a team's PvP season.") {
                        "Id"(STRING, "the season's ID")
                        "Wins"(INTEGER, "the amount of wins")
                        "Losses"(INTEGER, "the amount of losses")
                        "Rating"(INTEGER, "the team's rating")
                    })
                )
            }
        ))
    }
    V2_GUILD_TREASURY(
        summary = "Returns information about a guild's treasury.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(
            description = "the guild's treasury items",
            items = record(name = "GuildTreasurySlot", description = "Information about an item in a guild's treasury.") {
                "ItemId"(ITEM_ID, "the item's ID")
                "Count"(INTEGER, "the amount of the item in the guild's treasury")
                "NeededBy"(
                    description = "the currently in-progress upgrades requiring the item",
                    type = array(record(name = "UpgradeRequirement", description = "Information about the usage for an item.") {
                        "UpgradeId"(INTEGER, "the guild upgrade's ID")
                        "Count"(INTEGER, "the total amount of the item required for the upgrade")
                    })
                )
            }
        ))
    }
    V2_GUILD_UPGRADES(
        summary = "Returns information about a guild's upgrades.",
        security = security(ACCOUNT, GUILDS)
    ) {
        pathParameter("Id", STRING, "the guild's ID")

        schema(array(INTEGER, "the IDs of the guild's unlocked upgrades"))
    }
    V2_GUILD_SEARCH(summary = "Returns an array of guild IDs for a given guild name.") {
        queryParameter("Name", STRING, "the guild name to search for")

        schema(array(STRING, "the IDs of the found guilds"))
    }
    V2_GUILD_PERMISSIONS(
        summary = "Returns information about available guild permissions.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "GuildPermission", description = "Information about a guild permission.") {
            "Id"(STRING, "the permission's ID")
            localized.."Name"(STRING, "the permission's localized name")
            localized.."Description"(STRING, "the permission's localized description")
        })
    }
    V2_GUILD_UPGRADES__STATIC(
        summary = "Returns information about available guild hall upgrades.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(conditional(
            name = "GuildUpgrade",
            description = "Information about a guild upgrade.",
            sharedConfigure = {
                "Id"(INTEGER, "the upgrade's ID")
                localized.."Name"(STRING, "the upgrade's name")
                localized.."Description"(STRING, "the upgrade's description")
                "Type"(STRING, "the upgrade's type")
                "Icon"(STRING, "the URL for the upgrade's icon")
                "BuildTime"(INTEGER, "the time it takes to build the upgrade")
                "RequiredLevel"(INTEGER, "the prerequisite level the guild must be at to build the upgrade")
                "Experience"(INTEGER, "the amount of guild experience that will be awarded upon building the upgrade")
                "Prerequisites"(array(INTEGER), "an array of upgrade IDs that must be completed before this can be built")
                "Costs"(
                    description = "an array of objects describing the upgrade's cost",
                    type = array(record(name = "Cost", description = "Information about an upgrade's cost.") {
                        "Type"(STRING, "the cost's type")
                        localized.."Name"(STRING, "the cost's name")
                        "Count"(INTEGER, "the amount needed")
                        optional.."ItemId"(ITEM_ID, "the ID of the cost's item")
                    })
                )
            }
        ) {
            +record(name = "AccumulatingCurrency", description = "Information about a mine capacity upgrade.") {}
            +record(name = "BankBag", description = "Information about a guild bank upgrades.") {
                "BagMaxItems"(INTEGER, "the maximum item slots of the guild bank tab")
                "BagMaxCoins"(INTEGER, "the maximum amount of coins that can be stored in the bank tab")
            }
            +record(name = "Boost", description = "Information about a permanent guild buffs upgrade.") {}
            +record(name = "Claimable", description = "Information about a guild WvW tactics.") {}
            +record(name = "Consumable", description = "Information about a banners and guild siege.") {}
            +record(name = "Decoration", description = "Information about a decoration that must be crafted by a Scribe.") {}
            +record(name = "GuildHall", description = "Information about claiming a Guild Hall.") {}
            +record(name = "GuildHallExpedition", description = "Information about an expedition unlock.") {}
            +record(name = "Hub", description = "Information about Guild Initiative office unlock.") {}
            +record(name = "Queue", description = "Information about Workshop Restoration 1.") {}
            +record(name = "Unlock", description = "Information about permanent unlocks, such as merchants and arena decorations.") {}
        })
    }
    V2_HOME(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_HOME_CATS(
        summary = "Returns information about home-instance cats.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "HomeInstanceCat", description = "Information about a home-instance cat.") {
            "Id"(INTEGER, "the cat's ID")
            "Hint"(STRING, "the unlock hint")
        })
    }
    V2_HOME_NODES(
        summary = "Returns information about home-instance nodes.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "HomeInstanceNode", description = "Information about a home-instance node.") {
            "Id"(STRING, "the node's ID")
        })
    }
    V2_ITEMS(
        summary = "Returns information about items in the game.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Item", description = "Information about an item.") {
            "Id"(ITEM_ID, "the item's ID")
            localized.."Name"(STRING, "the item's name")
            "Type"(STRING, "the item's type")
            "ChatLink"(STRING, "the chat link")
            optional.."Icon"(STRING, "the URL for the item's icon")
            optional..localized.."Description"(STRING, "the item's description")
            "Rarity"(STRING, "the item's rarity")
            "Level"(INTEGER, "the level required to use the item")
            "VendorValue"(INTEGER, "the value in coins when selling the item to a vendor")
            optional.."DefaultSkin"(SKIN_ID, "the ID of the item's default skin")
            "Flags"(array(STRING), "flags applying to the item")
            "GameTypes"(array(STRING), "the game types in which the item is usable")
            "Restrictions"(array(STRING), "restrictions applied to the item")
            optional.."UpgradesInto"(
                description = "lists what items this item can be upgraded into, and the method of upgrading",
                type = array(record(name = "Upgrade", description = "Information about an item's upgrade.") {
                    "Upgrade"(STRING, "describes the method of upgrading")
                    "ItemId"(ITEM_ID, "the ID that results from performing the upgrade")
                })
            )
            optional.."UpgradesFrom"(
                description = "lists what items this item can be upgraded from, and the method of upgrading",
                type = array(record(name = "Precursor", description = "Information about an item's precursor.") {
                    "Upgrade"(STRING, "describes the method of upgrading")
                    "ItemId"(ITEM_ID, "the ID of the item that is upgraded into the item")
                })
            )
            optional.."Details"(
                description = "additional information about the item",
                type = conditional(name = "Details", description = "Additional information about an item.", disambiguationBySideProperty = true) {
                    val INFIX_UPGRADE = record(name = "InfixUpgrade", description = "Information about an item's infix upgrade.") {
                        "Id"(INTEGER, "the itemstat ID")
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
                                "SkillId"(INTEGER, "the skill ID of the effect")
                                optional.."Description"(STRING, "the effect's description")
                            }
                        )
                    }

                    val UPGRADES = record(name = "Upgrades", description = "Information about an item's upgrades.") {
                        "InfusionSlots"(
                            description = "infusion slots of the armor piece",
                            type = array(record(name = "InfusionSlot", description = "Information about an items infusion slot.") {
                                "Flags"(array(STRING), "infusion slot type of infusion upgrades")
                                optional.."ItemId"(ITEM_ID, "the infusion upgrade in the armor piece")
                            })
                        )
                        optional.."InfixUpgrade"(INFIX_UPGRADE, "infix upgrade object")
                        optional.."SuffixItemId"(INTEGER, "the suffix item ID")
                        until(V2_SCHEMA_2020_11_17T00_30_00_000Z)..optional..lenient.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                        since(V2_SCHEMA_2020_11_17T00_30_00_000Z)..optional.."SecondarySuffixItemId"(ITEM_ID, "the secondary suffix item ID")
                        optional.."StatChoices"(array(INTEGER), "a list of selectable stat IDs which are visible in /v2/itemstats")
                        optional.."AttributeAdjustment"(DECIMAL, "The value to be combined with the gradient multiplier and offset value to calculate the value of an attribute using the itemstats")
                    }

                    +record(name = "Armor", description = "Additional information about an armor item.") {
                        "Type"(STRING, "the armor slot type")
                        "WeightClass"(STRING, "the weight class")
                        "Defense"(INTEGER, "the defense value of the armor piece")
                        inline.."Upgrades"(UPGRADES, "the item's upgrades")
                    }
                    +record(name = "Back", description = "Additional information about a backpiece.") {
                        inline.."Upgrades"(UPGRADES, "the item's upgrades")
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
                        optional.."ColorId"(INTEGER, "the dye ID for dye unlocks")
                        optional.."RecipeId"(INTEGER, "the recipe ID for recipe unlocks")
                        optional.."ExtraRecipeIds"(array(INTEGER), "additional recipe IDs for recipe unlocks")
                        optional.."GuildUpgradeId"(INTEGER, "the guild upgrade ID for the item")
                        optional.."ApplyCount"(INTEGER, "the number of stacks of the effect applied by this item")
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
                        optional.."GuildUpgradeId"(INTEGER, "the guild upgrade ID for the item")
                        optional.."VendorIds"(array(INTEGER), "the vendor IDs")
                    }
                    +record(name = "MiniPet", description = "Additional information about a mini unlock item.") {
                        "MinipetId"(INTEGER, "the miniature it unlocks")
                    }
                    "Tool"(record(name = "Tool", description = "Additional information about a tool.") {
                        "Type"(STRING, "the tool type")
                        "Charges"(INTEGER, "the available charges")
                    })
                    +record(name = "Trinket", description = "Additional information about a trinket.") {
                        "Type"(STRING, "the trinket type")
                        inline.."Upgrades"(UPGRADES, "the item's upgrades")
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
                        inline.."Upgrades"(UPGRADES, "the item's upgrades")
                    }
                }
            )
        })
    }
    V2_ITEMSTATS(
        summary = "Returns information about itemstats.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "ItemStatSet", description = "Information about a stat set.") {
            "Id"(INTEGER, "the stat set's ID")
            "Name"(STRING, "the name of the stat set")
            "Attributes"(
                description = "the list of attribute bonuses",
                type = array(record(name = "Attribute", description = "Information about an attribute bonus.") {
                    "Attribute"(STRING, "the name of the attribute")
                    "Multiplier"(DECIMAL, "the multiplier for that attribute")
                    "Value"(INTEGER, "the default value for that attribute")
                })
            )
        })
    }
    V2_JADEBOTS(
        summary = "Returns information about jade bot skins.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "JadeBot", description = "Information about a jade bot skin.") {
            "Id"(INTEGER, "the skin's ID")
            localized.."Name"(STRING, "the skin's name")
            localized.."Description"(STRING, "the skin's description")
            "UnlockItem"(ITEM_ID, "the ID of the item used to unlock the skin")
        })
    }
    V2_LEGENDARYARMORY(
        summary = "Returns information about what can be stored in the legendary armory.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "LegendaryArmorySlot", description = "Information about an item that can be stored in the legendary armory.") {
            "Id"(ITEM_ID, "the item's ID")
            "MaxCount"(INTEGER, "the maximum number of copies of this item that can be stored in the armory for an account")
        })
    }
    V2_LEGENDS(
        summary = "Returns information about the Revenant legends.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Legend", description = "Information about a Revenant legend.") {
            "Id"(STRING, "the legend's ID")
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Code"(INTEGER, "the build template ID of the legend")
            "Swap"(INTEGER, "the ID of the profession (swap Legend) skill")
            "Heal"(INTEGER, "the ID of the heal skill")
            "Elite"(INTEGER, "the ID of the elite skills")
            "Utilities"(
                description = "the IDs of the utility skills",
                type = array(INTEGER)
            )
        })
    }
    V2_MAILCARRIERS(
        summary = "Returns information about mailcarriers.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Mailcarrier", description = "Information about a mailcarrier.") {
            "Id"(INTEGER, "the mailcarrier's ID")
            "Icon"(STRING, "the URL for the mailcarrier's icon")
            localized.."Name"(STRING, "the mailcarrier's name")
            "Order"(INTEGER, "a number that can be used to sort the list of mailcarriers")
            "UnlockItems"(array(ITEM_ID), "the IDs of the items that can be used to unlock the mailcarrier")
            "Flags"(array(STRING), "additional flags describing the mailcarrier")
        })
    }
    V2_MAPCHESTS(
        summary = "Returns information about the Hero's Choice Chests that can be acquired once per day.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "MapChest", description = "Information about a Hero's Choice Chests that can be acquired once per day.") {
            "Id"(STRING, "the ID of the chest")
        })
    }
    V2_MAPS(
        summary = "Returns information about maps.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Map", description = "Information about a map.") {
            "Id"(INTEGER, "the map's ID")
            localized.."Name"(STRING, "the map's name")
            "Type"(STRING, "the type of map")
            "MinLevel"(INTEGER, "the minimum level of the map")
            "MaxLevel"(INTEGER, "the maximum level of the map")
            "DefaultFloor"(INTEGER, "the ID of the map's default floor")
            "Floors"(array(INTEGER), "the IDs of the floors available on the map")
            "RegionId"(INTEGER, "the ID of the region the map belongs to")
            optional..localized.."RegionName"(STRING, "the name of the region the map belongs to")
            "ContinentId"(INTEGER, "the ID of the continent the map belongs to")
            optional..localized.."ContinentName"(STRING, "the name of the continent the map belongs to")
            "MapRect"(array(array(INTEGER)), "the dimensions of the map, given as the coordinates of the lower-left (SW) and upper-right (NE) corners")
            "ContinentRect"(array(array(INTEGER)), "the dimensions of the map within the continent coordinate system, given as the coordinates of the upper-left (NW) and lower-right (SE) corners")
        })
    }
    V2_MASTERIES(
        summary = "Returns information about masteries.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Mastery", description = "Information about a mastery.") {
            "Id"(INTEGER, "the mastery's ID")
            localized.."Name"(STRING, "the mastery's name")
            localized.."Requirement"(STRING, "the written out requirement to unlock the mastery track")
            "Order"(INTEGER, "the order in which the mastery track appears in a list")
            "Background"(STRING, "the URL for the mastery track's background graphic")
            "Region"(STRING, "the mastery region the track belongs to")
            "Levels"(
                description = "information about each mastery level",
                type = array(record(name = "Level", description = "Information about a mastery level.") {
                    localized.."Name"(STRING, "the mastery level's name")
                    localized.."Description"(STRING, "the mastery level's description")
                    localized.."Instruction"(STRING, "the in-game instruction for the mastery level")
                    "Icon"(STRING, "the URL for the mastery level's icon")
                    "PointCost"(INTEGER, "the amount of mastery points required to unlock the level")
                    "ExpCost"(INTEGER, "the amount of experience required to unlock the level")
                })
            )
        })
    }
    V2_MATERIALS(
        summary = "Returns information about the categories in the material storage.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "MaterialCategory", description = "Information about a material category.") {
            "Id"(INTEGER, "the category's ID")
            localized.."Name"(STRING, "the category's name")
            "Items"(array(ITEM_ID), "the IDs of the items in this category")
            "Order"(INTEGER, "the category's sorting key")
        })
    }
    V2_MINIS(
        summary = "Returns information about minis.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Mini", description = "Information about a mini.") {
            "Id"(INTEGER, "the mini's ID")
            localized.."Name"(STRING, "the mini's name")
            optional..localized.."Description"(STRING, "the description of how to unlock the mini")
            "Icon"(STRING, "the URL for the mini's icon")
            "Order"(INTEGER, "a (non-unique) number that can be used as basis to sort the list of minis")
            "ItemId"(ITEM_ID, "the ID of the item that can be used to unlock the mini")
        })
    }
    V2_MOUNTS(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_MOUNTS_SKINS(
        summary = "Returns information about mount skins.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "MountSkin", description = "Information about a mount skin.") {
            "Id"(INTEGER, "the mount skin's ID")
            localized.."Name"(STRING, "the mount skin's name")
            "Icon"(STRING, "a render service URL for the mount skin's icon")
            "Mount"(STRING, "the mount type id for the mount skin")
            "DyeSlots"(
                description = "the mount skin's dye slots",
                type = array(record(name = "DyeSlot", description = "Information about a dye slot.") {
                    "ColorId"(INTEGER, "the ID of the color")
                    "Material"(STRING, "the slot's material")
                })
            )
        })
    }
    V2_MOUNTS_TYPES(
        summary = "Returns information about mount types.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "MountType", description = "Information about a mount type.") {
            "Id"(STRING, "the mount type's ID")
            localized.."Name"(STRING, "the mount type's name")
            "DefaultSkin"(INTEGER, "the ID of the mount type's default skin")
            "Skins"(array(INTEGER), "the IDs of the skins available for the mount type")
            "Skills"(
                description = "the mount type's skills",
                type = array(record(name = "Skill", description = "Information about a mount skill.") {
                    "Id"(INTEGER, "the mount skill's ID")
                    "Slot"(STRING, "the mount skill's slot")
                })
            )
        })
    }
    V2_NOVELTIES(
        summary = "Returns information about novelties.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Novelty", description = "Information about a novelty.") {
            "Id"(INTEGER, "the novelty's ID")
            localized.."Name"(STRING, "the novelty's name")
            "Icon"(STRING, "a render service URL for the novelty's icon")
            localized.."Description"(STRING, "the novelty's description")
            "Slot"(STRING, "the novelty's slot")
            // Yes, "unlock_item". This is no mistake. The API actually returns this.
            SerialName("unlock_item").."UnlockItems"(
                description = "the IDs of the items that can be used to unlock the novelty",
                type = array(ITEM_ID)
            )
        })
    }
    V2_OUTFITS(
        summary = "Returns information about outfits.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Outfit", description = "Information about an outfit.") {
            "Id"(INTEGER, "the outfit's ID")
            localized.."Name"(STRING, "the outfit's name")
            "Icon"(STRING, "the outfit's icon")
            "UnlockItems"(array(ITEM_ID), "the IDs of the items that can be used to unlock the outfit")
        })
    }
    V2_PETS(
        summary = "Returns information about pets.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Pet", description = "Information about a pet.") {
            "Id"(INTEGER, "the pet's ID")
            localized.."Name"(STRING, "the pet's name")
            localized.."Description"(STRING, "the pet's description")
            "Icon"(STRING, "a render service URL for the pet's icon")
            "Skills"(
                description = "the pet's skills",
                type = array(record(name = "Skill", description = "Information about a pet's skill.") {
                    "Id"(INTEGER, "the skill's ID")
                })
            )
        })
    }
    V2_PROFESSIONS(
        summary = "Returns information about the game's playable professions.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Profession", description = "Information about a playable profession.") {
            "Id"(STRING, "the profession's ID")
            localized.."Name"(STRING, "the profession's localized name")
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."Code"(INTEGER, "the profession's palette code")
            "Icon"(STRING, "a render service URL for the profession's icon")
            SerialName("icon_big").."BigIcon"(STRING, "a render service URL for a big version of the profession's icon")
            "Specializations"(array(INTEGER), "the IDs of the profession's specializations")
            "Weapons"(
                description = "information about the weapons usable by this profession",
                type = map(
                    keys = STRING,
                    values = record(name = "Weapon", description = "Information about a profession's weapon and it's skills.") {
                        optional.."Specialization"(INTEGER, "the ID of the profession's specializations required for this weapon")
                        "Flags"(array(STRING), "additional flags describing this weapon's properties (e.g. MainHand, OffHand, TwoHand, Aquatic)")
                        "Skills"(
                            description = "the skills for the weapon if wielded by this profession",
                            type = array(record(name = "Skill", description = "Information about a weapon's skills.") {
                                "Id"(INTEGER, "the skill's ID")
                                "Slot"(STRING, "the skill's slot")
                                optional.."Attunement"(STRING, "the elementalist attunement for this skill")
                                optional.."Offhand"(STRING, "the offhand weapon for this skill")
                            })
                        )
                    }
                )
            )
            "Flags"(array(STRING), "additional flags describing this profession's properties (e.g. NoRacialSkills)")
            "Skills"(
                description = "the profession specific skills",
                type = array(record(name = "Skill", description = "Information about a profession skill.") {
                    "Id"(INTEGER, "the skill's ID")
                    "Slot"(STRING, "the skill's slot")
                    "Type"(STRING, "the skill's type")
                    optional.."Attunement"(STRING, "the elementalist attunement for this skill")
                    optional.."Source"(STRING, "the profession ID of the source of the stolen skill") // TODO is this correct?
                })
            )
            "Training"(
                description = "array of trainings of this profession",
                type = array(record(name = "Training", description = "Information about training track.") {
                    "Id"(INTEGER, "the training's ID")
                    "Category"(STRING, "the training's category")
                    localized.."Name"(STRING, "the training's localized name")
                    "Track"(
                        description = "array of skill/trait in the training track",
                        type = array(record(name = "Track", description = "Information about a skill/trait in a track.") {
                            "Cost"(INTEGER, "the amount of skill points required to unlock this step")
                            "Type"(STRING, "the type of the step (e.g. Skill, Trait)")
                            optional.."SkillId"(INTEGER, "the ID of the skill unlocked by this step")
                            optional.."TraitId"(INTEGER, "the ID of the trait unlocked by this step")
                        })
                    )
                })
            )
            since(V2_SCHEMA_2019_12_19T00_00_00_000Z).."SkillsByPalette"(
                description = "mappings from palette IDs to skill IDs",
                type = array(array(INTEGER))
            )
        })
    }
    V2_PVP(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_PVP_AMULETS(
        summary = "Returns information about available PvP amulets.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "PvpAmulet", description = "Information about a PvP amulet.") {
            "Id"(INTEGER, "the amulet's ID")
            localized.."Name"(STRING, "the amulet's localized name")
            "Icon"(STRING, "a render service URL for the amulet's icon")
            "Attributes"(
                description = "the amulet's stats",
                type = record(name = "Stats", description = "Information about an amulet's stats.") {
                    optional..SerialName("AgonyResistance").."AgonyResistance"(INTEGER, "the amount of agony resistance given by the amulet")
                    optional..SerialName("BoonDuration").."BoonDuration"(INTEGER, "the amount of boon duration given by the amulet")
                    optional..SerialName("ConditionDamage").."ConditionDamage"(INTEGER, "the amount of condition damage given by the amulet")
                    optional..SerialName("ConditionDuration").."ConditionDuration"(INTEGER, "the amount of condition duration given by the amulet")
                    optional..SerialName("CritDamage").."CritDamage"(INTEGER, "the amount of crit damage given by the amulet")
                    optional..SerialName("Healing").."Healing"(INTEGER, "the amount of healing given by the amulet")
                    optional..SerialName("Power").."Power"(INTEGER, "the amount of power given by the amulet")
                    optional..SerialName("Precision").."Precision"(INTEGER, "the amount of precision given by the amulet")
                    optional..SerialName("Toughness").."Toughness"(INTEGER, "the amount of toughness given by the amulet")
                    optional..SerialName("Vitality").."Vitality"(INTEGER, "the amount of vitality given by the amulet")
                }
            )
        })
    }
    V2_PVP_GAMES(
        summary = "Returns information about an account's recent PvP games.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours,
        security = security(ACCOUNT, PVP)
    ) {
        schema(record(name = "PvpGame", description = "Information about an account's PvP game.") {
            "Id"(STRING, "the game's ID")
            "MapId"(INTEGER, "the map's ID")
            "Started"(STRING, "the ISO-8601 standard timestamp of when the game started")
            "Ended"(STRING, "the ISO-8601 standard timestamp of when the game ended")
            "Result"(STRING, "the game's result for the account (\"Victory\" or \"Defeat\")")
            "Team"(STRING, "the player's team (\"Blue\" or \"Red\")")
            "Profession"(STRING, "the ID of the player's profession")
            "RatingType"(STRING, "the type of rating of the game")
            optional.."RatingChange"(INTEGER, "the change in rating for the account")
            optional.."Season"(STRING, "the ID of the game's PvP season")
            "Scores"(
                description = "the game's final scores",
                type = record(name = "Scores", description = "Information about a PvP game's scores.") {
                    "Red"(INTEGER, "the red team's score")
                    "Blue"(INTEGER, "the blue team's score")
                }
            )
        })
    }
    V2_PVP_HEROES(
        summary = "Returns information about available PvP heroes.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "PvpHero", description = "Information about a PvP hero.") {
            "Id"(STRING, "the PvP hero's ID")
            localized.."Name"(STRING, "the hero's localized name")
            localized.."Description"(STRING, "the hero's localized description")
            localized.."Type"(STRING, "the flavor type describing the hero")
            "Stats"(
                description = "the hero's stats",
                type = record(name = "Stats", description = "Information about a hero's stats.") {
                    "Offense"(INTEGER, "the offense stat")
                    "Defense"(INTEGER, "the defense stat")
                    "Speed"(INTEGER, "the speed stat")
                }
            )
            "Overlay"(STRING, "the render service URL for the hero's overlay art")
            "Underlay"(STRING, "the render service URL for the hero's underlay art")
            "Skins"(
                description = "the hero's skins",
                type = array(record(name = "Skin", description = "Information about a PvP hero's skin.") {
                    "Id"(INTEGER, "the PvP hero skin's ID")
                    localized.."Name"(STRING, "the hero skin's localized name")
                    "Icon"(STRING, "a render service URL for the skin's icon")
                    "Default"(BOOLEAN, "whether the skin is the champion's default skin")
                    "UnlockItems"(array(ITEM_ID), "an array of item IDs used to unlock the skin")
                })
            )
        })
    }
    V2_PVP_RANKS(
        summary = "Returns information about the PvP ranks.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "PvpRank", description = "Information about a PvP rank.") {
            "Id"(INTEGER, "the PvP rank's ID")
            "FinisherId"(INTEGER, "the rank finisher's ID")
            localized.."Name"(STRING, "the rank's localized name")
            "Icon"(STRING, "a render service URL for the rank's icon")
            "MinRank"(INTEGER, "the minimum PvP level required for the rank")
            "MaxRank"(INTEGER, "the maximum PvP level for the rank")
            "Levels"(
                description = "the rank's levels",
                type = array(record(name = "Level", description = "Information about a PvP rank's level.") {
                    "MinRank"(INTEGER, "the minimum PvP level required for the level")
                    "MaxRank"(INTEGER, "the maximum PvP level for the level")
                    "Points"(INTEGER, "the amount of PvP experience needed to go from the given minimum rank to maximum rank")
                })
            )
        })
    }
    V2_PVP_SEASONS(
        summary = "Returns information about PvP seasons.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "PvpSeason", description = "Information about a PvP season.") {
            "Id"(STRING, "the PvP season's ID")
            localized.."Name"(STRING, "the season's localized name")
            "Start"(STRING, "the ISO-8601 standard timestamp of when the season started")
            "End"(STRING, "the ISO-8601 standard timestamp of when the season ended")
            "Active"(BOOLEAN, "whether the season is currently active")
            "Divisions"(
                description = "the season's divisions",
                type = array(record(name = "Division", description = "Information about a division.") {
                    localized.."Name"(STRING, "the division's localized name")
                    "Flags"(array(STRING), "the flags providing additional information about the division")
                    "LargeIcon"(STRING, "the render service URL for the division's large icon")
                    "SmallIcon"(STRING, "the render service URL for the division's small icon")
                    "PipIcon"(STRING, "the render service URL for the division's pip icon")
                    "Tiers"(
                        description = "the division's tiers",
                        type = array(record(name = "Tier", description = "Information about a division's tier.") {
                            "Points"(INTEGER, "the number of pips in the tier")
                        })
                    )
                })
            )
            optional.."Ranks"(
                description = "the season's ranks",
                type = array(record(name = "Ranks", description = "Information about a season's ranks.") {
                    localized.."Name"(STRING, "the rank's localized name")
                    localized.."Description"(STRING, "the rank's localized description")
                    "Icon"(STRING, "the render service URL for the rank's icon")
                    "Overlay"(STRING, "the render service URL for the rank's overlay icon")
                    "OverlaySmall"(STRING, "the render service URL for the rank's overlay icon")
                    "Tiers"(
                        description = "the rank's tiers",
                        type = array(record(name = "Tier", description = "Information about a rank's tier.") {
                            "Rating"(INTEGER, "the rating required for the tier")
                        })
                    )
                })
            )
            "Leaderboards"(
                description = "the season's leaderboards",
                type = record(name = "Leaderboards", description = "Information about a seasons leaderboards.") {
                    val LEADERBOARD = record(name = "Leaderboard", description = "Information about a leaderboard.") {
                        "Settings"(
                            description = "the leaderboard's settings",
                            type = record(name = "Settings", description = "Information about a leaderboard's settings.") {
                                "Name"(STRING, "the setting's name")
                                optional.."Duration"(STRING, "the setting's duration (unknown purpose)")
                                "Scoring"(STRING, "the ID of the primary scoring component")
                                "Tiers"(
                                    description = "the tiers",
                                    type = array(record(name = "Tier", description = "Information about a leaderboard's tiers.") {
                                        optional.."Color"(STRING, "the tier's color as hex value")
                                        optional.."Type"(STRING, "the tier's type")
                                        optional.."Name"(STRING, "the tier's name")
                                        "Range"(array(DECIMAL), "the tier's scoring range")
                                    })
                                )
                            }
                        )
                        "Scorings"(
                            description = "the leaderboard's scoring information",
                            type = array(record(name = "Scoring", description = "Information about scoring.") {
                                "Id"(STRING, "the scoring's ID")
                                "Type"(STRING, "the scoring's type")
                                localized.."Description"(STRING, "the scoring's localized description")
                                localized.."Name"(STRING, "the scoring's localized name")
                                "Ordering"(STRING, "the scoring's ordering (\"MoreIsBetter\" or \"LessIsBetter\")")
                            })
                        )
                    }

                    optional.."Guild"(LEADERBOARD, "the season's guild leaderboard")
                    optional.."Ladder"(LEADERBOARD, "the season's leaderboard")
                    optional.."Legendary"(LEADERBOARD, "the season's legendary rank leaderboard")
                }
            )
        })
    }
    V2_PVP_SEASONS_LEADERBOARDS(
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        pathParameter("Id", STRING, "the season's ID")

        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_PVP_SEASONS_LEADERBOARDS(
        path = "/pvp/seasons/:id/leaderboards/:board",
        summary = "Returns information about the available sub-endpoints.",
        cache = Duration.INFINITE // We don't expect this to change. Ever.
    ) {
        pathParameter("Id", STRING, "the season's ID")
        pathParameter("Board", STRING, "the board")

        schema(array(STRING, "the available sub-endpoints"))
    }
    V2_PVP_SEASONS_LEADERBOARDS(
        path = "/pvp/seasons/:id/leaderboards/:board/:region",
        idTypeKey = "rank",
        summary = "Returns information about a PvP leaderboard.",
        queryTypes = queryTypes(BY_PAGE),
        cache = 1.hours
    ) {
        pathParameter("Id", STRING, "the season's ID")
        pathParameter("Board", STRING, "the board")
        pathParameter("Region", STRING, "the region")

        schema(record(name = "PvpSeasonsLeaderboardEntry", description = "Information about a leaderboard entry.") {
            optional.."Name"(STRING, "the account's name")
            optional.."Id"(STRING, "the guild's ID")
            "Rank"(INTEGER, "the account's rank")
            optional.."Team"(STRING, "the guild team's name")
            optional.."TeamId"(INTEGER, "the guild team's ID")
            "Date"(STRING, "the date at which the rank was reached")
            "Scores"(
                description = "the entry's scoring values",
                type = array(record(name = "Scoring", description = "Information about a leaderboard entry's scoring") {
                    "Id"(STRING, "the scoring's ID")
                    "Value"(INTEGER, "the scoring's value")
                })
            )
        })
    }
    V2_PVP_STANDINGS(
        summary = "Returns information about an account's PvP standings.",
        security = security(ACCOUNT, PVP)
    ) {
        schema(array(
            description = "the player's standings information for recent seasons",
            items = record(name = "PvpStandings", description = "Information about an account's PvP standings.") {
                "Current"(
                    description = "the season's current standing",
                    type = record(name = "Current", description = "Information about the current standing.") {
                        "TotalPoints"(INTEGER, "the total number of points")
                        "Division"(INTEGER, "the index of the reached division")
                        "Tier"(INTEGER, "the index of the reached tier")
                        "Points"(INTEGER, "the number of pips towards the next tier")
                        "Repeats"(INTEGER, "the number of times the account maxed out the repeat division")
                        optional.."Rating"(INTEGER, "the rating level")
                        optional.."Decay"(INTEGER, "the decay value")
                    }
                )
                "Best"(
                    description = "the season's best standing",
                    type = record(name = "Best", description = "Information about the season's best standing.") {
                        "TotalPoints"(INTEGER, "the total number of points")
                        "Division"(INTEGER, "the index of the reached division")
                        "Tier"(INTEGER, "the index of the reached tier")
                        "Points"(INTEGER, "the number of pips towards the next tier")
                        "Repeats"(INTEGER, "the number of times the account maxed out the repeat division")
                    }
                )
                "SeasonId"(STRING, "the season's ID")
            }
        ))
    }
    V2_PVP_STATS(
        summary = "Returns information about an account's PvP stats.",
        cache = 1.hours,
        security = security(ACCOUNT, PVP)
    ) {
        schema(record(name = "PvpStats", description = "Information about an account's PvP stats.") {
            val STATS = record(name = "Stats", description = "Information about category-specific PvP stats.") {
                "Wins"(INTEGER, "the amount of wins")
                "Losses"(INTEGER, "the amount of losses")
                "Desertions"(INTEGER, "the amount desertions")
                "Byes"(INTEGER, "the amount of byes")
                "Forfeits"(INTEGER, "the amount of forfeits")
            }

            "PvpRank"(INTEGER, "the account's PvP rank")
            "PvpRankPoints"(INTEGER, "the account's PvP rank points")
            "PvpRankRollovers"(INTEGER, "the number of times the account leveled up after reaching rank 80")
            "Aggregate"(STATS, "the aggregated statistics")
            "Professions"(map(STRING, STATS), "the stats by profession ID")
            "Ladders"(map(STRING, STATS), "the stats by ladder (e.g. \"ranked\", \"unranked\")")
        })
    }
    V2_QUAGGANS(
        summary = "Returns images of quaggans.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Quaggan", description = "Information about an image of a quaggan.") {
            "Id"(STRING, "the quaggans's ID")
            "Url"(STRING, "the URL to the quaggan image")
        })
    }
    V2_QUESTS(
        summary = "Returns information about Story Journal missions.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Quest", description = "Information about a quest.") {
            "Id"(INTEGER, "the quest's ID")
            localized.."Name"(STRING, "the quest's localized name")
            "Level"(INTEGER, "the minimum level required to begin the quest")
            "Story"(INTEGER, "the story's ID")
            "Goals"(
                description = "the quest's goals",
                type = array(record(name = "Goal", description = "Information about a quest's goal.") {
                    localized.."Active"(STRING, "the text displayed for the quest step if it is active")
                    localized.."Complete"(STRING, "the text displayed for the quest step if it is complete")
                })
            )
        })
    }
    V2_RACES(
        summary = "Returns information about the game's playable races.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Race", description = "Information about a playable race.") {
            "Id"(STRING, "the race's ID")
            localized.."Name"(STRING, "the race's localized name")
            "Skills"(array(INTEGER), "an array of racial skill IDs")
        })
    }
    V2_RAIDS(
        summary = "Returns information about the raids in the game.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Raid", description = "Information about a raid.") {
            "Id"(STRING, "the raid's ID")
            "Wings"(
                description = "the raid's wings",
                type = array(record(name = "Wing", description = "Information about a wing.") {
                    "Id"(STRING, "the wing's ID")
                    "Events"(
                        description = "the wing's events",
                        type = array(record(name = "Event", description = "Information about an event.") {
                            "Id"(STRING, "the event's ID")
                            "Type"(STRING, "the event's type")
                        })
                    )
                })
            )
        })
    }
    V2_RECIPES(
        summary = "Returns information about the crafting recipes in the game.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Recipe", description = "Information about a crafting recipe.") {
            "Id"(INTEGER, "the recipe's ID")
            "Type"(STRING, "the recipe's type")
            "OutputItemId"(ITEM_ID, "the ID of the produced item")
            "OutputItemCount"(INTEGER, "the amount of items produced")
            SerialName("time_to_craft_ms").."CraftTimeMillis"(INTEGER, "the time in milliseconds it takes to craft the item")
            "Disciplines"(array(STRING), "the crafting disciplines that can use the recipe")
            "MinRating"(INTEGER, "the minimum rating required to use the recipe")
            "Flags"(array(STRING), "the flags applying to the recipe")
            until(V2_SCHEMA_2022_03_09T02_00_00_000Z).."Ingredients"(
                description = "the recipe's ingredients",
                type = array(record(name = "Ingredient", description = "Information about a recipe ingredient.") {
                    "ItemId"(ITEM_ID, "the ingredient's item ID")
                    "Count"(INTEGER, "the quantity of this ingredient")
                })
            )
            since(V2_SCHEMA_2022_03_09T02_00_00_000Z).."Ingredients"(
                description = "the recipe's ingredients",
                type = array(conditional(
                    name = "Ingredient",
                    description = "Information about a recipe ingredient.",
                    sharedConfigure = {
                        "Type"(STRING, "the ingredient's type")
                        "Id"(INTEGER, "the ingredient's ID")
                        "Count"(INTEGER, "the amount")
                    }
                ) {
                    +record(name = "Currency", description = "A currency ingredient.") {}
                    +record(name = "GuildUpgrade", description = "A guild upgrade ingredient.") {}
                    +record(name = "Item", description = "An item ingredient.") {}
                })
            )
            optional..until(V2_SCHEMA_2022_03_09T02_00_00_000Z).."GuildIngredients"(
                description = "the recipe's guild ingredients",
                type = array(record(name = "GuildIngredient", description = "Information about a recipe guild ingredient.") {
                    "UpgradeId"(STRING, "the guild ingredient's guild upgrade ID")
                    "Count"(INTEGER, "the quantity of this guild ingredient")
                })
            )
            optional.."OutputUpgradeId"(INTEGER, "the ID of the produced guild upgrade")
            "ChatLink"(STRING, "the recipe's chat code")
        })
    }
    V2_RECIPES_SEARCH(
        querySuffix = "ByInput",
        summary = "Returns an array of item IDs for recipes using a given item as ingredient."
    ) {
        queryParameter("Input", ITEM_ID, "the item ID of the crafting ingredient")

        schema(array(INTEGER, "the IDs of the found recipes"))
    }
    V2_RECIPES_SEARCH(
        querySuffix = "ByOutput",
        summary = "Returns an array of item IDs for recipes to craft a given item."
    ) {
        queryParameter("Output", ITEM_ID, "the item ID of the crafting result")

        schema(array(INTEGER, "the IDs of the found recipes"))
    }
    V2_SKIFFS(
        summary = "Returns information about skiff skins.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Skiff", description = "Information about a skiff skin.") {
            "Id"(INTEGER, "the skin's ID")
            localized.."Name"(STRING, "the skin's name")
            "Icon"(STRING, "a render service URL for the skin's icon")
            "DyeSlots"(
                description = "the skin's dye slots",
                type = array(record(name = "DyeSlot", description = "Information about a dye slot.") {
                    "ColorId"(INTEGER, "the ID of the color")
                    "Material"(STRING, "the slot's material")
                })
            )
        })
    }
    V2_SKILLS(
        summary = "Returns information about the skills in the game.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Skill", description = "Information about a skill.") {
            "Id"(INTEGER, "the skill's ID")
            localized.."Name"(STRING, "the skill's localized name")
            localized.."Description"(STRING, "the skill's localized description")
            "Icon"(STRING, "a render service URL for the skill's icon")
            "ChatLink"(STRING, "the skill's chat code")
            optional.."Flags"(array(STRING), "additional skill flags")
            optional.."Type"(STRING, "the type of skill")
            optional.."WeaponType"(STRING, "the type of weapon that the skill is on. (May be \"None\".)")
            optional.."Professions"(array(STRING), "the IDs of the professions that can use the skill")
            optional.."Specialization"(INTEGER, "the ID of the specialization required for the skill")
            optional.."Slot"(STRING, "the slot that the skill fits into")
            optional.."Facts"(
                description = "an array of facts describing the skill's effect",
                type = array(conditional(
                    name = "Fact",
                    description = "Information about a trait's fact (i.e. effect/property).",
                    sharedConfigure = {
                        "Type"(STRING, "the type of the fact")
                        optional.."Icon"(STRING, "the URL for the fact's icon")
                        optional..localized.."Text"(STRING, "an arbitrary localized string describing the fact")
                    }
                ) {
                    FACTS()
                })
            )
            optional.."TraitedFacts"(
                description = "Information about a trait's fact (i.e. effect/property) that is only active if a specific trait is active.",
                type = array(conditional(
                    name = "TraitedFact",
                    description = "a list of traited facts",
                    sharedConfigure = {
                        "Type"(STRING, "the type of the fact")
                        optional.."Icon"(STRING, "the URL for the fact's icon")
                        optional..localized.."Text"(STRING, "an arbitrary localized string describing the fact")
                        "RequiresTrait"(INTEGER, "specifies which trait has to be selected in order for this fact to take effect")
                        optional.."Overrides"(INTEGER, "the array index of the facts object it will override, if the trait specified in requires_trait is selected")
                    }
                ) { FACTS() })
            )
            optional.."Categories"(array(STRING), "the categories that the skill falls under")
            optional.."Attunement"(STRING, "the attunement required for the skill")
            optional.."Cost"(INTEGER, "the cost associated with the skill")
            optional.."DualWield"(STRING, "the type of off-hand weapon that must be equipped for this dual-wield skill to appear")
            optional.."FlipSkill"(INTEGER, "the ID of the skill that the skill flips over into")
            optional.."Initiative"(INTEGER, "the skill's initiative cost")
            optional.."NextChain"(INTEGER, "the ID of the next skill in the chain")
            optional.."PrevChain"(INTEGER, "the ID of the previous skill in the chain")
            optional.."TransformSkills"(array(INTEGER), "the IDs of the skills that will replace the player's skills when using the skill")
            optional.."BundleSkills"(array(INTEGER), "the IDs of the skills that will replace the player's skills when using the skill")
            optional.."ToolbeltSkill"(INTEGER, "the ID of the associated toolbelt skill")
        })
    }
    V2_SKINS(
        summary = "Returns information about the skins in the game.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Skin", description = "Information about a skin.") {
            "Id"(SKIN_ID, "the skin's ID")
            localized.."Name"(STRING, "the skin's localized name")
            "Type"(STRING, "the skin's type")
            "Flags"(array(STRING), "additional skin flags (ShowInWardrobe, NoCost, HideIfLocked, OverrideRarity)")
            "Restrictions"(array(STRING), "the IDs of the races that can use this skin, or empty if it can be used by any race")
            "Icon"(STRING, "a render service URL for the skin's icon")
            "Rarity"(STRING, "the skin's rarity")
            optional.."Description"(STRING, "the skin's description")
            optional.."Details"(
                description = "additional information about the skin",
                type = conditional(name = "Details", description = "Additional information about a skin.", disambiguationBySideProperty = true) {
                    "Armor"(record(name = "Armor", description = "Additional information about an armor skin.") {
                        "Type"(STRING, "the skin's armor slot")
                        "WeightClass"(STRING, "the skin's armor weight")
                        "DyeSlots"(
                            description = "the skin's dye slots",
                            type = record(name = "DyeSlots", description = "Information about a skin's sye slots.") {
                                val DYE_SLOT = record(name = "DyeSlot", description = "Information about a dye slot.") {
                                    "ColorId"(INTEGER, "the default color's ID")
                                    "Material"(STRING, "the material type")
                                }

                                fun DYE_SLOTS() = array(DYE_SLOT, nullableItems = true)

                                "Default"(DYE_SLOTS(), "the default dye slots")
                                "Overrides"(
                                    description = "the race/gender dye slot overrides",
                                    type = record(name = "Overrides", description = "Information about race/gender dye slot overrides.") {
                                        optional..SerialName("AsuraMale").."AsuraMale"(DYE_SLOTS(), "the dye slot overrides for asuarn male characters")
                                        optional..SerialName("AsuraFemale").."AsuraFemale"(DYE_SLOTS(), "the dye slot overrides for asuarn female characters")
                                        optional..SerialName("CharrMale").."CharrMale"(DYE_SLOTS(), "the dye slot overrides for charr male characters")
                                        optional..SerialName("CharrFemale").."CharrFemale"(DYE_SLOTS(), "the dye slot overrides for charr female characters")
                                        optional..SerialName("HumanMale").."HumanMale"(DYE_SLOTS(), "the dye slot overrides for human male characters")
                                        optional..SerialName("HumanFemale").."HumanFemale"(DYE_SLOTS(), "the dye slot overrides for human female characters")
                                        optional..SerialName("NornMale").."NornMale"(DYE_SLOTS(), "the dye slot overrides for norn male characters")
                                        optional..SerialName("NornFemale").."NornFemale"(DYE_SLOTS(), "the dye slot overrides for norn female characters")
                                        optional..SerialName("SylvariMale").."SylvariMale"(DYE_SLOTS(), "the dye slot overrides for sylvari male characters")
                                        optional..SerialName("SylvariFemale").."SylvariFemale"(DYE_SLOTS(), "the dye slot overrides for sylvari female characters")
                                    }
                                )
                            }
                        )
                    })
                    "Gathering"(record(name = "Gathering", description = "Additional information about a gathering tool skin.") {
                        "Type"(STRING, "the skin's tool type")
                    })
                    "Weapon"(record(name = "Weapon", description = "Additional information about a weapon skin.") {
                        "Type"(STRING, "the skin's weapon type")
                        "DamageType"(STRING, "the skin's damage type")
                    })
                }
            )
        })
    }
    V2_SPECIALIZATIONS(
        summary = "Returns information about the specializations in the game.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Specialization", description = "Information about a specialization.") {
            "Id"(INTEGER, "the ID of the specialization")
            localized.."Name"(STRING, "the localized name of the specialization")
            "Profession"(STRING, "the ID of the profession the specialization belongs to")
            "Elite"(BOOLEAN, "a flag indicating whether the specialization is an elite specialization")
            "Icon"(STRING, "a render service URL for the specialization's icon")
            "Background"(STRING, "a render service URL for the specialization's background image")
            "MinorTraits"(array(INTEGER), "a list of all IDs of the specialization's minor traits")
            "MajorTraits"(array(INTEGER), "a list of all IDs of the specialization's major traits")
            optional.."WeaponTrait"(INTEGER, "the ID of the elite specialization's weapon trait")
            optional.."ProfessionIcon"(STRING, "a render service URL for the elite specialization's icon")
            optional..SerialName("profession_icon_big").."BigProfessionIcon"(STRING, "a render service URL for a large variant of the elite specialization's icon")
        })
    }
    V2_STORIES(
        summary = "Returns information about the Story Journal stories.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Story", description = "Information about a Story Journal season.") {
            "Id"(INTEGER, "the ID of the story")
            "Season"(STRING, "the ID of the story's season")
            localized.."Name"(STRING, "the localized name of the story")
            localized.."Description"(STRING, "the localized description of the story")
            "Timeline"(STRING, "the in-game date of the story")
            "Level"(INTEGER, "the minimum level required to start to begin the story")
            "Order"(INTEGER, "a number that can be used to sort the list of stories")
            "Chapters"(
                description = "the story's chapters",
                type = array(record(name = "Chapter", description = "Information about a story chapter.") {
                    "Name"(STRING, "the localized name of the chapter")
                })
            )
            optional.."Races"(array(STRING), "the races eligible to participate in the story")
            optional.."Flags"(array(STRING), "additional requirements for a character to participate in the story")
        })
    }
    V2_STORIES_SEASONS(
        summary = "Returns information about the Story Journal seasons.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "StorySeason", description = "Information about a Story Journal season.") {
            "Id"(STRING, "the ID of the season")
            localized.."Name"(STRING, "the localized name of the season")
            "Order"(INTEGER, "a number that can be used to sort the list of seasons")
            "Stories"(array(INTEGER), "the IDs of the stories in the season")
        })
    }
    V2_TITLES(
        summary = "Returns information about the titles that are in the game.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "Title", description = "Information about a title.") {
            "Id"(INTEGER, "the ID of the title")
            localized.."Name"(STRING, "the display name of the title")
            deprecated..optional.."Achievement"(ACHIEVEMENT_ID, "the ID of the achievement that grants this title")
            optional.."Achievements"(array(ACHIEVEMENT_ID), "the IDs of the achievements that grant this title")
            optional..SerialName("ap_required")..CamelCase("apRequired").."APRequired"(INTEGER, "the amount of AP required to unlock this title")
        })
    }
    V2_TOKENINFO(
        summary = "Returns information about the supplied API key.",
        security = security()
    ) {
        schema(
            V2_SCHEMA_CLASSIC to record(name = "TokenInfo", description = "Information about an API key.") {
                "Id"(STRING, "the API key that was requested")
                "Name"(STRING, "the name given to the API key by the account owner")
                "Permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
            },
            V2_SCHEMA_2019_05_22T00_00_00_000Z to record(name = "TokenInfo", description = "Information about an API key.") {
                "Id"(STRING, "the API key that was requested")
                "Name"(STRING, "the name given to the API key by the account owner")
                "Permissions"(
                    description = "an array of strings describing which permissions the API key has",
                    type = array(STRING)
                )
                "Type"(STRING, "the type of the access token given")
                inline..optional.."SubtokenDetails"(
                    description = "",
                    type = record(name = "SubtokenDetails", description = "Additional information about a subtoken") {
                        "ExpiresAt"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken expires")
                        "IssuedAt"(STRING, "if a subtoken is given, ISO8601 timestamp indicating when the given subtoken was created")
                        "Urls"(array(STRING), "an array of strings describing what endpoints are available to this token (if the given subtoken is restricted to a list of URLs)")
                    }
                )
            }
        )
    }
    V2_TRAITS(
        summary = "Returns information about the traits in the game.",
        queryTypes = defaultQueryTypes(),
        cache = 1.hours
    ) {
        schema(record(name = "Trait", description = "Information about a trait.") {
            "Id"(INTEGER, "the trait's ID")
            "Tier"(INTEGER, "the trait's tier")
            "Order"(INTEGER, "the trait's order")
            localized.."Name"(STRING, "the trait's localized name")
            optional..localized.."Description"(STRING, "the trait's localized description")
            "Slot"(STRING, "the slot for the trait")
            optional.."Facts"(
                description = "a list of facts",
                type = array(conditional(
                    name = "Fact",
                    description = "Information about a trait's fact (i.e. effect/property).",
                    sharedConfigure = {
                        "Type"(STRING, "the type of the fact")
                        optional.."Icon"(STRING, "the URL for the fact's icon")
                        optional..localized.."Text"(STRING, "an arbitrary localized string describing the fact")
                    }
                ) { FACTS() })
            )
            optional.."TraitedFacts"(
                description = "Information about a trait's fact (i.e. effect/property) that is only active if a specific trait is active.",
                type = array(conditional(
                    name = "TraitedFact",
                    description = "a list of traited facts",
                    sharedConfigure = {
                        "Type"(STRING, "the type of the fact")
                        optional.."Icon"(STRING, "the URL for the fact's icon")
                        optional..localized.."Text"(STRING, "an arbitrary localized string describing the fact")
                        "RequiresTrait"(INTEGER, "specifies which trait has to be selected in order for this fact to take effect")
                        optional.."Overrides"(INTEGER, "the array index of the facts object it will override, if the trait specified in requires_trait is selected")
                    }
                ) { FACTS() })
            )
            optional.."Skills"(
                description = "a list of skills related to this trait",
                type = array(record(name = "Skill", description = "Information about a skill related to a trait.") {
                    "Id"(INTEGER, "the skill's ID")
                    localized.."Name"(STRING, "the skill's localized name")
                    localized.."Description"(STRING, "the skill's localized description")
                    "Icon"(STRING, "a render service URL for the skill's icon")
                    "ChatLink"(STRING, "the skill's chat code")
                    optional.."Categories"(array(STRING), "the categories that the skill falls under")
                    optional.."Flags"(array(STRING), "additional skill flags")
                    optional.."Facts"(
                        description = "an array of facts describing the skill's effect",
                        type = array(conditional(
                            name = "Fact",
                            description = "Information about a trait's fact (i.e. effect/property).",
                            sharedConfigure = {
                                "Type"(STRING, "the type of the fact")
                                optional.."Icon"(STRING, "the URL for the fact's icon")
                                optional..localized.."Text"(STRING, "an arbitrary localized string describing the fact")
                            }
                        ) { FACTS() })
                    )
                    optional.."TraitedFacts"(
                        description = "Information about a trait's fact (i.e. effect/property) that is only active if a specific trait is active.",
                        type = array(conditional(
                            name = "TraitedFact",
                            description = "a list of traited facts",
                            sharedConfigure = {
                                "Type"(STRING, "the type of the fact")
                                optional.."Icon"(STRING, "the URL for the fact's icon")
                                optional.."Text"(STRING, "an arbitrary localized string describing the fact")
                                "RequiresTrait"(INTEGER, "specifies which trait has to be selected in order for this fact to take effect")
                                optional.."Overrides"(INTEGER, "the array index of the facts object it will override, if the trait specified in requires_trait is selected")
                            }
                        ) { FACTS() })
                    )
                })
            )
            "Specialization"(INTEGER, "the specialization that this trait is part of")
            "Icon"(STRING, "the URL for the trait's icon")
        })
    }
    V2_WIZARDSVAULT(
        summary = "Returns information about current Wizard's Vault season.",
        cache = 1.hours
    ) {
        schema(record(name = "WizardsVaultSeason", description = "Information about a Wizard's Vault season.") {
            "Title"(STRING, "current season's title")
            "Start"(STRING, "the ISO-8601 standard timestamp of the start of the season")
            "End"(STRING, "the ISO-8601 standard timestamp of the end of the season")
            "Listings"(array(INTEGER), "the IDs of the available listings")
            "Objectives"(array(INTEGER), "the IDs of the available objectives")
        })
    }
    V2_WIZARDSVAULT_LISTINGS(
        summary = "Returns information about the items available in the Wizard's Vault.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WizardsVaultListing", description = "Information about an item available in the Wizard's Vault.") {
            "Id"(INTEGER, "the listing's ID")
            "ItemId"(ITEM_ID, "the item's ID")
            "ItemCount"(INTEGER, "the amount of items the player receives")
            "Type"(
                description = "the type of the listing",
                type = enum(STRING, name = "Type", description = "the type of the listing") {
                    "Featured"(description = "a featured listing")
                    "Normal"(description = "a normal listing in the current rewards tab")
                    "Legacy"(description = "a normal listing in the legacy rewards tab")
                }
            )
            "Cost"(INTEGER, "the cost of this listing (in Astral Acclaim)")
        })
    }
    V2_WIZARDSVAULT_OBJECTIVES(
        summary = "Returns information about the Wizard's Vault's objectives.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WizardsVaultObjective", description = "Information about a Wizard's Vault's objective.") {
            "Id"(INTEGER, "the objective's ID")
            "Title"(STRING, "the objective's title")
            "Track"(
                description = "the objective's track",
                type = enum(STRING, name = "Track", description = "the objective's track") {
                    "PvE"(description = "a PvE objective")
                    "PvP"(description = "a PvP objective")
                    "WvW"(description = "a WvW objective")
                }
            )
            "Acclaim"(INTEGER, "the amount of Astral Acclaim the player receives for completing the objective")
        })
    }
    V2_WORLDBOSSES(
        summary = "Returns information about the worldbosses that reward boss chests that can be opened once a day.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WorldBoss", description = "Information about a worldboss that reward boss chests that can be opened once a day.") {
            "Id"(STRING, "the worldboss's ID")
        })
    }
    V2_WORLDS(
        summary = "Returns information about the available worlds (or servers).",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "World", description = "Information about an available world (or server).") {
            "Id"(INTEGER, "the ID of the world")
            localized.."Name"(STRING, "the name of the world")
            "Population"(STRING, "the population level of the world")
        })
    }
    V2_WVW_ABILITIES(
        summary = "Returns information about the achievable ranks in the World versus World game mode.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WvwAbility", description = "Information about an ability in the World versus World game mode.") {
            "Id"(INTEGER, "the ID of the ability")
            localized.."Name"(STRING, "the ability's localized name")
            localized.."Description"(STRING, "the ability's localized description")
            "Icon"(STRING, "a render service URL for the ability's icon")
            "Ranks"(
                description = "the ability's ranks",
                type = array(record(name = "Rank", description = "Information about an ability's rank.") {
                    "Cost"(INTEGER, "the WvW experience points required to unlock the rank")
                    localized.."Effect"(STRING, "the rank's localized effect")
                })
            )
        })
    }
    V2_WVW_MATCHES(
        summary = "Returns information about the active WvW matches.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.seconds
    ) {
        schema(record(name = "WvwMatch", description = "General information about a WvW match.") {
            "Id"(STRING, "the match's ID")
            "StartTime"(STRING, "the ISO-8601 standard timestamp of when the match's start")
            "EndTime"(STRING, "the ISO-8601 standard timestamp of when the match's end")
            "Scores"(map(STRING, INTEGER), "the total scores by team color")
            "Worlds"(map(STRING, INTEGER), "the IDs of the three primary servers by team color")
            "AllWorlds"(map(STRING, array(INTEGER)), "the IDs of the servers by team color")
            "Deaths"(map(STRING, INTEGER), "the total deaths by team color")
            "Kills"(map(STRING, INTEGER), "the total kills by team color")
            "VictoryPoints"(map(STRING, INTEGER), "the victory points by team color")
            "Skirmishes"(
                description = "the match's skirmishes",
                type = array(record(name = "Skirmish", description = "Information about skirmish scores.") {
                    "Id"(INTEGER, "the skirmish's ID")
                    "Scores"(map(STRING, INTEGER), "the scores by team color")
                    "MapScores"(
                        description = "the scores by map",
                        type = array(record(name = "MapScores", description = "Map-specific information about scores.") {
                            "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                            "Scores"(map(STRING, INTEGER), "the scores by team color")
                        })
                    )
                })
            )
            "Maps"(
                description = "the total scores by map",
                type = array(record(name = "Map", description = "Map-specific information about scores.") {
                    "Id"(INTEGER, "the map's ID")
                    "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                    "Scores"(map(STRING, INTEGER), "the scores by team color")
                    "Deaths"(map(STRING, INTEGER), "the deaths by team color")
                    "Kills"(map(STRING, INTEGER), "the kills by team color")
                    "Objectives"(
                        description = "the list of the map's objective",
                        type = array(record(name = "Objective", description = "Information about a map objective.") {
                            "Id"(STRING, "the objective's ID")
                            "Type"(STRING, "the objective's type")
                            "Owner"(STRING, "the objective's owner (i.e. \"Red\", \"Green\", \"Blue\", or \"Neutral\")")
                            "LastFlipped"(STRING, "the ISO-8601 standard timestamp of when the objective was last flipped")
                            optional.."ClaimedBy"(STRING, "the guild ID of the guild that currently has claimed this objective")
                            optional.."ClaimedAt"(STRING, "the ISO-8601 standard timestamp of when the objective was claimed")
                            "PointsTick"(INTEGER, "the amount of points per tick given by the objective")
                            "PointsCapture"(INTEGER, "the amount of points awarded for capturing the objective")
                            optional.."GuildUpgrades"(array(INTEGER), "the IDs of the currently slotted guild upgrades")
                            optional.."YaksDelivered"(INTEGER, "the total number of shipments delivered to the objective")
                        }
                    ))
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
    V2_WVW_MATCHES_OVERVIEW(
        summary = "Returns general information about the active WvW matches.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.seconds
    ) {
        schema(record(name = "WvwMatchOverview", description = "General information about a WvW match.") {
            "Id"(STRING, "the match's ID")
            "Worlds"(map(STRING, INTEGER), "the IDs of the three primary servers by team color")
            "AllWorlds"(map(STRING, array(INTEGER)), "the IDs of the servers by team color")
            "StartTime"(STRING, "the ISO-8601 standard timestamp of when the match's start")
            "EndTime"(STRING, "the ISO-8601 standard timestamp of when the match's end")
        })
    }
    V2_WVW_MATCHES_SCORES(
        summary = "Returns information about the active WvW match scores.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.seconds
    ) {
        schema(record(name = "WvwMatchScore", description = "Information about a WvW match scores.") {
            "Id"(STRING, "the match's ID")
            "Scores"(map(STRING, INTEGER), "the total scores by team color")
            "VictoryPoints"(map(STRING, INTEGER), "the victory points by team color")
            "Skirmishes"(
                description = "the match's skirmishes",
                type = array(record(name = "Skirmish", description = "Information about skirmish scores.") {
                    "Id"(INTEGER, "the skirmish's ID")
                    "Scores"(map(STRING, INTEGER), "the scores by team color")
                    "MapScores"(
                        description = "the scores by map",
                        type = array(record(name = "MapScores", description = "Map-specific information about scores.") {
                            "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                            "Scores"(map(STRING, INTEGER), "the scores by team color")
                        })
                    )
                })
            )
            "Maps"(
                description = "the total scores by map",
                type = array(record(name = "Map", description = "Map-specific information about scores.") {
                    "Id"(INTEGER, "the map's ID")
                    "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                    "Scores"(map(STRING, INTEGER), "the scores by team color")
                })
            )
        })
    }
    V2_WVW_MATCHES_STATS(
        summary = "Returns information about the active WvW match stats.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.seconds
    ) {
        schema(record(name = "WvwMatchStats", description = "Information about a WvW match stats.") {
            "Id"(STRING, "the match's ID")
            "Deaths"(map(STRING, INTEGER), "the deaths by team color")
            "Kills"(map(STRING, INTEGER), "the deaths by team color")
            "Maps"(
                description = "the stats by map",
                type = array(record(name = "Map", description = "Map-specific information about scores.") {
                    "Id"(INTEGER, "the map's ID")
                    "Type"(STRING, "the map's type (i.e. \"Center\", \"RedHome\", \"BlueHome\", or \"GreenHome\")")
                    "Deaths"(map(STRING, INTEGER), "the deaths by team color")
                    "Kills"(map(STRING, INTEGER), "the deaths by team color")
                })
            )
        })
    }
    V2_WVW_OBJECTIVES(
        summary = "Returns information about the objectives in the World versus World game mode.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WvwObjective", description = "Information about an objective in the World versus World game mode.") {
            "Id"(STRING, "the ID of the objective")
            localized.."Name"(STRING, "the name of the objective")
            "Type"(STRING, "the type of the objective")
            "SectorId"(INTEGER, "the map sector the objective can be found in")
            "MapId"(INTEGER, "the ID of the map the objective can be found on")
            "MapType"(STRING, "the type of the map the objective can be found on")
            optional.."Coord"(array(DECIMAL), "an array of three numbers representing the X, Y and Z coordinates of the objectives marker on the map")
            optional.."LabelCoord"(array(DECIMAL), "an array of two numbers representing the X and Y coordinates of the sector centroid")
            optional.."Marker"(STRING, "the icon link")
            "ChatLink"(STRING, "the chat code for the objective")
            optional.."UpgradeId"(INTEGER, "the ID of the upgrades available for the objective")
        })
    }
    V2_WVW_RANKS(
        summary = "Returns information about the achievable ranks in the World versus World game mode.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WvwRank", description = "Information about an achievable rank in the World versus World game mode.") {
            "Id"(WVW_RANK_ID, "the ID of the rank")
            localized.."Title"(STRING, "the title of the rank")
            "MinRank"(INTEGER, "the WvW level required to unlock this rank")
        })
    }
    V2_WVW_UPGRADES(
        summary = "Returns information about available upgrades for objectives in the World versus World game mode.",
        queryTypes = defaultQueryTypes(all = true),
        cache = 1.hours
    ) {
        schema(record(name = "WvwUpgrade", description = "Information about an upgrade for objectives in the World versus World game mode.") {
            "Id"(INTEGER, "the upgrade's ID")
            "Tiers"(
                description = "the different tiers of the upgrade",
                type = array(record(name = "Tier", description = "Information about an upgrade tier.") {
                    localized.."Name"(STRING, "the name of the upgrade tier")
                    "YaksRequired"(INTEGER, "the amount of dolyaks required to reach this upgrade tier")
                    "Upgrades"(
                        description = "the upgrades available at the tier",
                        type = array(record(name = "Upgrade", description = "Information about an upgrade.") {
                            localized.."Name"(STRING, "the name of the upgrade")
                            localized.."Description"(STRING, "the description for the upgrade")
                            "Icon"(STRING, "the icon link")
                        })
                    )
                })
            )
        })
    }
}