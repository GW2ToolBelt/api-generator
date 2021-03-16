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
package com.gw2tb.apigen.test.spec

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.model.TokenScope.*
import com.gw2tb.apigen.test.*
import kotlin.time.*

class GW2v2 : SpecTest(
    "GW2v2",
    API_V2_DEFINITION,
    {
        expectEndpoint("/Account")
            .security(ACCOUNT)

        expectEndpoint("/Account/Achievements")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Bank")
            .security(ACCOUNT, INVENTORIES)

        expectEndpoint("/Account/BuildStorage")
            .security(ACCOUNT)

        expectEndpoint("/Account/DailyCrafting")
            .security(ACCOUNT, PROGRESSION, UNLOCKS)

        expectEndpoint("/Account/Dungeons")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Dyes")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Emotes")
            .security(ACCOUNT)

        expectEndpoint("/Account/Finishers")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Gliders")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Home")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Account/Home/Cats")
            .security(ACCOUNT, PROGRESSION, UNLOCKS)

        expectEndpoint("/Account/Home/Nodes")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Inventory")
            .security(ACCOUNT, INVENTORIES)

        expectEndpoint("/Account/Luck")
            .security(ACCOUNT, PROGRESSION, UNLOCKS)

        expectEndpoint("/Account/Mailcarriers")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/MapChests")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Masteries")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Mastery/Points")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Materials")
            .security(ACCOUNT, INVENTORIES)

        expectEndpoint("/Account/Minis")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Mounts")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Account/Mounts/Skins")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Mounts/Types")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Novelties")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Outfits")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/PvP/Heroes")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Raids")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Account/Recipes")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Skins")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Titles")
            .security(ACCOUNT, UNLOCKS)

        expectEndpoint("/Account/Wallet")
            .security(ACCOUNT, WALLET)

        expectEndpoint("/Account/WorldBosses")
            .security(ACCOUNT, PROGRESSION)

        expectEndpoint("/Achievements")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/Achievements/Categories")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Achievements/Daily")

        expectEndpoint("/Achievements/Daily/Tomorrow")

        expectEndpoint("/Achievements/Groups")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Backstory")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Backstory/Answers")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Backstory/Questions")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Build")

        expectEndpoint("/Characters/:ID/Backstory")
            .security(ACCOUNT, CHARACTERS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Core")
            .security(ACCOUNT, CHARACTERS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Crafting")
            .security(ACCOUNT, CHARACTERS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/HeroPoints")
            .security(ACCOUNT, CHARACTERS, PROGRESSION)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Inventory")
            .security(ACCOUNT, CHARACTERS, INVENTORIES)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Quests")
            .security(ACCOUNT, CHARACTERS, PROGRESSION)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Recipes")
            .security(ACCOUNT, UNLOCKS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/SAB")
            .security(ACCOUNT, CHARACTERS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Characters/:ID/Training")
            .security(ACCOUNT, BUILDS, CHARACTERS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Colors")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Commerce/Delivery")
            .security(ACCOUNT, TRADINGPOST)

        expectEndpoint("/Commerce/Exchange")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Commerce/Exchange/:Type")
            .pathParameter("Type", STRING)
            .queryParameter("Quantity", INTEGER)

        expectEndpoint("/Commerce/Listings")
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/Commerce/Prices")
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/Commerce/Transactions")
            .cacheTime(DURATION_INFINITE)
            .security(ACCOUNT, TRADINGPOST)

        expectEndpoint("/Commerce/Transactions/:Relevance")
            .cacheTime(DURATION_INFINITE)
            .security(ACCOUNT, TRADINGPOST)
            .pathParameter("Relevance", STRING)

        expectEndpoint("/Commerce/Transactions/:Relevance/:Type")
            .cacheTime(1.minutes)
            .security(ACCOUNT, TRADINGPOST)
            .pathParameter("Relevance", STRING)
            .pathParameter("Type", STRING)
            .queryTypes(BY_PAGE)

        expectEndpoint("/Continents")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/CreateSubToken")
            .security(ACCOUNT)
            .queryParameter("Expire", STRING)
            .queryParameter("Permissions", STRING)
            .queryParameter("URLs", STRING)

        expectEndpoint("/Currencies")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/DailyCrafting")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Dungeons")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Emblem")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Emblem/:Type")
            .cacheTime(1.hours)
            .pathParameter("Type", STRING)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Emotes")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Files")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Finishers")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Guild/:ID")
            .pathParameter("ID", STRING)

        expectEndpoint("/Guild/:ID/Members")
            .security(ACCOUNT, GUILDS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Guild/:ID/Ranks")
            .security(ACCOUNT, GUILDS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Guild/:ID/Upgrades")
            .security(ACCOUNT, GUILDS)
            .pathParameter("ID", STRING)

        expectEndpoint("/Guild/Permissions")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Guild/Upgrades")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Gliders")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Home")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Home/Cats")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Home/Nodes")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Items")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/ItemStats")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Legends")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Mailcarriers")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/MapChests")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Maps")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Masteries")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Materials")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Minis")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Mounts")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/Mounts/Skins")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Mounts/Types")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Novelties")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Outfits")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Pets")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Professions")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/PvP")
            .cacheTime(DURATION_INFINITE)

        expectEndpoint("/PvP/Amulets")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/PvP/Heroes")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/PvP/Ranks")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Quaggans")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Quests")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Races")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Raids")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Recipes")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/Skins")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS(all = false), BY_PAGE)

        expectEndpoint("/Specializations")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Stories")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Stories/Seasons")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Titles")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/TokenInfo")
            .security(ACCOUNT)

        expectEndpoint("/WorldBosses")
            .cacheTime(1.hours)
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/Worlds")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/WvW/Abilities")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/WvW/Objectives")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/WvW/Ranks")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)

        expectEndpoint("/WvW/Upgrades")
            .cacheTime(1.hours)
            .localized()
            .queryTypes(BY_ID, BY_IDS, BY_PAGE)
    }
)