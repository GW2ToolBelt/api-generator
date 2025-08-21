/*
 * Copyright (c) 2019-2025 Leon Linhart
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
package com.gw2tb.apigen.internal.spec

import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.model.Name

/** A common alias for achievement IDs. */
internal val ACHIEVEMENT_ID = Alias(INTEGER, "AchievementId")

/** A common alias for achievement category IDs. */
internal val ACHIEVEMENT_CATEGORY_ID = Alias(INTEGER, "AchievementCategoryId")

/** A common alias for achievement group IDs. */
internal val ACHIEVEMENT_GROUP_ID = Alias(STRING, "AchievementGroupId")

/** A common alias for backstory answer IDs. */
internal val BACKSTORY_ANSWER_ID = Alias(STRING, "BackstoryAnswerId")

/** A common alias for backstory question IDs. */
internal val BACKSTORY_QUESTION_ID = Alias(INTEGER, "BackstoryQuestionId")

/** A common alias for build IDs. */
internal val BUILD_ID = Alias(INTEGER, "BuildId")

/** A common alias for color IDs. */
internal val COLOR_ID = Alias(INTEGER, "ColorId")

/** A common alias for continent IDs. */
internal val CONTINENT_ID = Alias(INTEGER, "ContinentId")

/** A common alias for currency IDs. */
internal val CURRENCY_ID = Alias(INTEGER, "CurrencyId")

/** A common alias for dungeon IDs. */
internal val DUNGEON_ID = Alias(STRING, "DungeonId")

/** A common alias for dye IDs. */
internal val DYE_ID = Alias(INTEGER, "DyeId")

/** A common alias for emblem IDs. */
internal val EMBLEM_ID = Alias(INTEGER, "EmblemId")

/** A common alias for emote IDs. */
internal val EMOTE_ID = Alias(STRING, "EmoteId")

/** A common alias for event IDs. */
internal val EVENT_ID = Alias(STRING, "EventId")

/** A common alias for file IDs. */
internal val FILE_ID = Alias(STRING, "FileId")

/** A common alias for finisher IDs. */
internal val FINISHER_ID = Alias(INTEGER, "FinisherId")

/** A common alias for floor IDs. */
internal val FLOOR_ID = Alias(INTEGER, "FloorId")

/** A common alias for glider IDs. */
internal val GLIDER_ID = Alias(INTEGER, "GliderId")

/** A common alias for guild IDs. */
internal val GUILD_ID = Alias(STRING, "GuildId")

/** A common alias for guild upgrade IDs. */
internal val GUILD_UPGRADE_ID = Alias(INTEGER, "GuildUpgradeId")

/** A common alias for home instance cat IDs. */
internal val HOME_CAT_ID = Alias(INTEGER, "HomeInstanceCatId")

/** A common alias for home instance node IDs. */
internal val HOME_NODE_ID = Alias(STRING, "HomeInstanceNodeId")

/** A common alias for homestead decoration IDs. */
internal val HOMESTEAD_DECORATION_ID = Alias(INTEGER, "HomesteadDecorationId")

/** A common alias for homestead decoration category IDs. */
internal val HOMESTEAD_DECORATION_CATEGORY_ID = Alias(INTEGER, "HomesteadDecorationCategoryId")

/** A common alias for homestead glyph IDs. */
internal val HOMESTEAD_GLYPH_ID = Alias(STRING, "HomesteadGlyphId")

/** A common alias for item IDs. */
internal val ITEM_ID = Alias(INTEGER, "ItemId")

/** A common alias for itemstat IDs. */
internal val ITEMSTAT_ID = Alias(INTEGER, "ItemStatId")

/** A common alias for jade bot IDs. */
internal val JADEBOT_ID = Alias(INTEGER, "JadeBotId")

/** A common alias for legend IDs. */
internal val LEGEND_ID = Alias(STRING, "LegendId")

/** A common alias for legend IDs. */
internal val LOGO_ID = Alias(STRING, "LogoId")

/** A common alias for mail carrier IDs. */
internal val MAILCARRIER_ID = Alias(INTEGER, "MailCarrierId")

/** A common alias for map IDs. */
internal val MAP_ID = Alias(INTEGER, "MapId")

/** A common alias for mastery IDs. */
internal val MASTERY_ID = Alias(INTEGER, "MasteryId")

/** A common alias for material category IDs. */
internal val MATERIAL_CATEGORY_ID = Alias(INTEGER, "MaterialId")

/** A common alias for mini IDs. */
internal val MINI_ID = Alias(INTEGER, "MiniId")

/** A common alias for mount skin IDs. */
internal val MOUNT_SKIN_ID = Alias(INTEGER, "MountSkinId")

/** A common alias for mount type IDs. */
internal val MOUNT_TYPE_ID = Alias(STRING, "MountTypeId")

/** A common alias for novelty IDs. */
internal val NOVELTY_ID = Alias(INTEGER, "NoveltyId")

/** A common alias for outfit IDs. */
internal val OUTFIT_ID = Alias(INTEGER, "OutfitId")

/** A common alias for pet IDs. */
internal val PET_ID = Alias(INTEGER, "PetId")

/** A common alias for profession IDs. */
internal val PROFESSION_ID = Alias(STRING, "ProfessionId")

/** A common alias for PvP amulet IDs. */
internal val PVP_AMULET_ID = Alias(INTEGER, "PvpAmuletId")

/** A common alias for PvP game IDs. */
internal val PVP_GAME_ID = Alias(STRING, "PvpGameId")

/** A common alias for PvP hero IDs. */
internal val PVP_HERO_ID = Alias(STRING, "PvpHeroId")

/** A common alias for PvP rank IDs. */
internal val PVP_RANK_ID = Alias(INTEGER, "PvpRankId")

/** A common alias for PvP season IDs. */
internal val PVP_SEASON_ID = Alias(STRING, "PvpSeasonId")

/** A common alias for Quaggan IDs. */
internal val QUAGGAN_ID = Alias(STRING, "QuagganId")

/** A common alias for recipe IDs. */
internal val RECIPE_ID = Alias(INTEGER, "RecipeId")

/** A common alias for race IDs. */
internal val RACE_ID = Alias(STRING, "RaceId")

/** A common alias for raid IDs. */
internal val RAID_ID = Alias(STRING, "RaidId")

/** A common alias for region IDs. */
internal val REGION_ID = Alias(INTEGER, "RegionId")

/** A common alias for skiff IDs. */
internal val SKIFF_ID = Alias(INTEGER, "SkiffId")

/** A common alias for skill IDs. */
internal val SKILL_ID = Alias(INTEGER, "SkillId")

/** A common alias for skin IDs. */
internal val SKIN_ID = Alias(INTEGER, "SkinId")

/** A common alias for specialization IDs. */
internal val SPECIALIZATION_ID = Alias(INTEGER, "SpecializationId")

/** A common alias for story IDs. */
internal val STORY_ID = Alias(INTEGER, "StoryId")

/** A common alias for story season IDs. */
internal val STORY_SEASON_ID = Alias(STRING, "StorySeasonId")

/** A common alias for title IDs. */
internal val TITLE_ID = Alias(INTEGER, "TitleId")

/** A common alias for trait IDs. */
internal val TRAIT_ID = Alias(INTEGER, "TraitId")

/** A common alias for wizard's vault listing IDs. */
internal val WIZARDS_VAULT_LISTING_ID = Alias(INTEGER, "WizardsVaultListingId")

/** A common alias for wizard's vault objective IDs. */
internal val WIZARDS_VAULT_OBJECTIVE_ID = Alias(INTEGER, "WizardsVaultObjectiveId")

/** A common alias for world IDs. */
internal val WORLD_ID = Alias(INTEGER, "WorldId")

/** A common alias for world boss IDs. */
internal val WORLD_BOSS_ID = Alias(STRING, "WorldBossId")

/** A common alias for WvW ability IDs. */
internal val WVW_ABILITY_ID = Alias(INTEGER, "WvwAbilityId")

/** A common alias for WvW match IDs. */
internal val WVW_MATCH_ID = Alias(STRING, "WvwMatchId")

/** A common alias for WvW objective IDs. */
internal val WVW_OBJECTIVE_ID = Alias(STRING, "WvwObjectiveId")

/** A common alias for WvW rank IDs. */
internal val WVW_RANK_ID = Alias(INTEGER, "WvwRankId")

/** A common alias for WvW upgrade IDs. */
internal val WVW_UPGRADE_ID = Alias(INTEGER, "WvwUpgradeId")