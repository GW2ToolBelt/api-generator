/*
 * Copyright (c) 2019-2022 Leon Linhart
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
internal val ACHIEVEMENT_ID = Alias(INTEGER, "AchievementID")

/** A common alias for achievement category IDs. */
internal val ACHIEVEMENT_CATEGORY_ID = Alias(INTEGER, "AchievementCategoryID")

/** A common alias for achievement group IDs. */
internal val ACHIEVEMENT_GROUP_ID = Alias(STRING, "AchievementGroupID")

/** A common alias for build IDs. */
internal val BUILD_ID = Alias(INTEGER, "BuildID")

/** A common alias for color IDs. */
internal val COLOR_ID = Alias(INTEGER, "ColorID")

/** A common alias for continent IDs. */
internal val CONTINENT_ID = Alias(INTEGER, "ContinentID")

/** A common alias for event IDs. */
internal val EVENT_ID = Alias(STRING, "EventID")

/** A common alias for floor IDs. */
internal val FLOOR_ID = Alias(INTEGER, "FloorID")

/** A common alias for guild IDs. */
internal val GUILD_ID = Alias(STRING, "GuildID")

/** A common alias for guild upgrade IDs. */
internal val GUILD_UPGRADE_ID = Alias(INTEGER, "GuildUpgradeID")

/** A common alias for item IDs. */
internal val ITEM_ID = Alias(INTEGER, "ItemID")

/** A common alias for itemstat IDs. */
internal val ITEMSTAT_ID = Alias(INTEGER, "ItemStatID")

/** A common alias for map IDs. */
internal val MAP_ID = Alias(INTEGER, "MapID")

/** A common alias for mini IDs. */
internal val MINI_ID = Alias(INTEGER, "MiniID")

/** A common alias for profession IDs. */
internal val PROFESSION_ID = Alias(INTEGER, "ProfessionID")

/** A common alias for recipe IDs. */
internal val RECIPE_ID = Alias(INTEGER, "RecipeID")

/** A common alias for race IDs. */
internal val RACE_ID = Alias(INTEGER, "RaceID")

/** A common alias for region IDs. */
internal val REGION_ID = Alias(INTEGER, "RegionID")

/** A common alias for skill IDs. */
internal val SKILL_ID = Alias(INTEGER, "SkillID")

/** A common alias for skin IDs. */
internal val SKIN_ID = Alias(INTEGER, "SkinID")

/** A common alias for specialization IDs. */
internal val SPECIALIZATION_ID = Alias(INTEGER, "SpecializationID")

/** A common alias for world IDs. */
internal val WORLD_ID = Alias(INTEGER, "WorldID")

/** A common alias for WvW match IDs. */
internal val WVW_MATCH_ID = Alias(STRING, Name("WvW", "Match", "ID"))

/** A common alias for WvW objective IDs. */
internal val WVW_OBJECTIVE_ID = Alias(STRING, Name("WvW", "Objective", "ID"))

/** A common alias for WvW rank IDs. */
internal val WVW_RANK_ID = Alias(INTEGER, Name("WvW", "Rank", "ID"))