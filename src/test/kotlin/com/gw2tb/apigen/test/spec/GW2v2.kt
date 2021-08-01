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
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.TokenScope.*
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.test.*
import com.gw2tb.apigen.test.assertNotNull
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.time.*

class GW2v2 : SpecTest<APIQuery.V2, APIType.V2, GW2v2.ExpectedAPIv2Query>(
    "GW2v2",
    APIVersion.API_V2,
    V2SpecBuilder {
        expectQuery(
            "/Account",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/Account/Achievements",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Bank",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/Account/BuildStorage",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/Account/DailyCrafting",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/Account/Dungeons",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Dyes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Emotes",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/Account/Finishers",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Gliders",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Account/Home/Cats",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/Account/Home/Nodes",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Inventory",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/Account/LegendaryArmory",
            security = setOf(ACCOUNT, INVENTORIES, UNLOCKS)
        )

        expectQuery(
            "/Account/Luck",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/Account/Mailcarriers",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/MapChests",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Masteries",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Mastery/Points",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Materials",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/Account/Minis",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Account/Mounts/Skins",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Mounts/Types",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Novelties",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Outfits",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/PvP/Heroes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Raids",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Account/Recipes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Skins",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Titles",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/Account/Wallet",
            security = setOf(ACCOUNT, WALLET)
        )

        expectQuery(
            "/Account/WorldBosses",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/Achievements",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Achievements/Categories",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery("/Achievements/Daily")

        expectQuery("/Achievements/Daily/Tomorrow")

        expectQuery(
            "/Achievements/Groups",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Backstory",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Backstory/Answers",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Backstory/Questions",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery("/Build")

        expectQuery(
            "/Characters/:ID/Backstory",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Characters/:ID/Core",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Crafting",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Characters/:ID/HeroPoints",
            security = setOf(ACCOUNT, CHARACTERS, PROGRESSION),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Inventory",
            security = setOf(ACCOUNT, CHARACTERS, INVENTORIES),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Quests",
            security = setOf(ACCOUNT, CHARACTERS, PROGRESSION),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Recipes",
            security = setOf(ACCOUNT, UNLOCKS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/SAB",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Skills",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Specializations",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Characters/:ID/Training",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Colors",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Commerce/Delivery",
            security = setOf(ACCOUNT, TRADINGPOST)
        )

        expectQuery(
            "/Commerce/Exchange",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Commerce/Exchange/:Type",
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("quantity", INTEGER))
        )

        expectQuery(
            "/Commerce/Listings",
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Commerce/Prices",
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Commerce/Transactions",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADINGPOST)
        )

        expectQuery(
            "/Commerce/Transactions/:Relevance",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADINGPOST),
            pathParameters = listOf(ExpectedPathParameter("Relevance", STRING))
        )

        expectQuery(
            "/Commerce/Transactions/:Relevance/:Type",
            cache = 1.minutes,
            security = setOf(ACCOUNT, TRADINGPOST),
            pathParameters = listOf(
                ExpectedPathParameter("Relevance", STRING),
                ExpectedPathParameter("Type", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Continents",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Continents/:ID/Floors",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/CreateSubToken",
            security = setOf(ACCOUNT),
            queryParameters = listOf(
                ExpectedQueryParameter("expire", STRING),
                ExpectedQueryParameter("permissions", STRING),
                ExpectedQueryParameter("urls", STRING, isOptional = true)
            )
        )

        expectQuery(
            "/Currencies",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Emblem",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Files",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Finishers",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Guild/:ID",
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Members",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Ranks",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Storage",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Teams",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Treasury",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Upgrades",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/Permissions",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Guild/Search",
            queryParameters = listOf(ExpectedQueryParameter("name", STRING))
        )

        expectQuery(
            "/Guild/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Gliders",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Items",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Mailcarriers",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Maps",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Masteries",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Materials",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Minis",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Mounts/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Mounts/Types",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Novelties",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Outfits",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Pets",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Professions",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/PvP",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/PvP/Amulets",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/PvP/Heroes",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/PvP/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/PvP/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/PvP/Seasons/:ID/Leaderboards",
            cache = Duration.INFINITE,
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/PvP/Seasons/:ID/Leaderboards/:Board",
            cache = Duration.INFINITE,
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING),
                ExpectedPathParameter("Board", STRING),
            )
        )

        expectQuery(
            "/PvP/Seasons/:ID/Leaderboards/:Board/:Region",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING),
                ExpectedPathParameter("Board", STRING),
                ExpectedPathParameter("Region", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/PvP/Standings",
            security = setOf(ACCOUNT, PVP)
        )

        expectQuery(
            "/PvP/Stats",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP)
        )

        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Quests",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Races",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Recipes/Search",
            queryParameters = listOf(ExpectedQueryParameter("input", INTEGER))
        )
        expectQuery(
            "/Recipes/Search",
            queryParameters = listOf(ExpectedQueryParameter("output", INTEGER))
        )

        expectQuery(
            "/Skills",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Specializations",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Stories",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Stories/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Titles",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/Traits",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER, supportsAll = false)
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/TokenInfo",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/Worlds",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/WvW/Abilities",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/WvW/Objectives",
            cache = 1.hours,
            queryDetails = QueryIDs(STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID(STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs(STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(STRING)
        )

        expectQuery(
            "/WvW/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

        expectQuery(
            "/WvW/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID(INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs(INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage(INTEGER)
        )

    }
) {

    override fun assertProperties(expected: ExpectedAPIv2Query, actual: APIQuery.V2) {
        val qdp = expected.queryDetails
        if (qdp == null) {
            assertNull(actual.queryDetails)
        } else {
            assertTrue(qdp(actual.queryDetails!!))
        }

        assertEquals(expected.security, actual.security)

        // TODO: In the future we might have to check the isLocalized flag specifically for schema versions.
        assertEquals(expected.isLocalized, actual.flatMapData { it.isLocalized }, "Mismatched 'isLocalized' flag for ${actual.route}")
    }

    override fun testTypes(queries: Collection<APIQuery.V2>) = sequence<DynamicTest> {
        val query = queries.sortedWith { a, b ->
            fun QueryDetails?.weight(): Int = when (this) {
                null -> 2
                else -> when (queryType) {
                    is QueryType.ByIDs -> -3
                    is QueryType.ByPage -> -2
                    is QueryType.ByID -> -1
                    is QueryType.IDs -> 0
                    else -> 1
                }
            }

            val wA = a.queryDetails.weight()
            val wB = b.queryDetails.weight()

            wA.compareTo(wB)
        }.first()

        fun SchemaType.firstPossiblyNestedClassOrNull(): SchemaClass? = when (this) {
            is SchemaClass -> this
            is SchemaArray -> items.firstPossiblyNestedClassOrNull()
            else -> null
        }

        fun SchemaType.isClassOrArrayOfClasses() = firstPossiblyNestedClassOrNull() != null

        query.significantVersions.forEach { version ->
            val schema = query[version].data
            if (!schema.isClassOrArrayOfClasses()) return@forEach

            val supportedType = spec.supportedTypes.filter { (key, _) -> key.endpoint == query.endpoint }
                .flatMap { (_, value) -> value }
                .find { it[version].data == schema.firstPossiblyNestedClassOrNull() }

            yield(DynamicTest.dynamicTest("$prefix${query.route}") {
                assertNotNull(supportedType)

                val data = TestData[spec, query.route, version]
                assertSchema(schema, data)
            })
        }
    }.asIterable()

    data class ExpectedAPIv2Query(
        override val route: String,
        override val isLocalized: Boolean,
        override val cache: Duration?,
        override val pathParameters: List<ExpectedPathParameter>,
        override val queryParameters: List<ExpectedQueryParameter>,
        val queryDetails: ((QueryDetails) -> Boolean)?,
        val security: Set<TokenScope>
    ) : ExpectedAPIQuery

    @Suppress("TestFunctionName")
    class V2SpecBuilder {

        private val queries = mutableListOf<ExpectedAPIv2Query>()

        companion object {
            operator fun invoke(block: V2SpecBuilder.() -> Unit): List<ExpectedAPIv2Query> {
                return V2SpecBuilder().also(block).queries
            }
        }

        fun QueryIDs(idType: SchemaType): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.IDs && it.idType == idType}

        fun QueryByID(idType: SchemaType): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.ByID && it.idType == idType}

        fun QueryByIDs(idType: SchemaType, supportsAll: Boolean = true): (QueryDetails) -> Boolean =
            { it.queryType.let { qt -> qt is QueryType.ByIDs && qt.supportsAll == supportsAll } && it.idType == idType }

        fun QueryByPage(idType: SchemaType): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.ByPage && it.idType == idType}

        fun expectQuery(
            route: String,
            isLocalized: Boolean = false,
            cache: Duration? = null,
            pathParameters: List<ExpectedPathParameter> = emptyList(),
            queryParameters: List<ExpectedQueryParameter> = emptyList(),
            queryDetails: ((QueryDetails) -> Boolean)? = null,
            security: Set<TokenScope> = emptySet()
        ) {
            queries += ExpectedAPIv2Query(
                route,
                isLocalized,
                cache,
                pathParameters,
                queryParameters,
                queryDetails,
                security
            )
        }

    }

}