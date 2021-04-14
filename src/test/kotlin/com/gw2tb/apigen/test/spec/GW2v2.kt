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
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Achievements/Categories",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery("/Achievements/Daily")

        expectQuery("/Achievements/Daily/Tomorrow")

        expectQuery(
            "/Achievements/Groups",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Backstory",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Backstory/Answers",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Backstory/Questions",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Commerce/Prices",
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
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
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Continents",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Emblem",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Files",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Finishers",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            "/Guild/:ID/Upgrades",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/Permissions",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Guild/Search",
            queryParameters = listOf(ExpectedQueryParameter("name", STRING))
        )

        expectQuery(
            "/Guild/Upgrades",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Gliders",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Items",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Mailcarriers",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Maps",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Masteries",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Materials",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Minis",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Mounts/Skins",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Mounts/Types",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Novelties",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Outfits",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Pets",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Professions",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/PvP",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/PvP/Amulets",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/PvP/Heroes",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/PvP/Ranks",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/PvP/Seasons",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Quests",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Races",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
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
            "/Skins",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.NotAll, INTEGER)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Specializations",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Stories",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/Stories/Seasons",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Titles",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/TokenInfo",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/Worlds",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/WvW/Abilities",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/WvW/Objectives",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryDetails(QueryType.ByID, STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, STRING)
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, STRING)
        )

        expectQuery(
            "/WvW/Ranks",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

        expectQuery(
            "/WvW/Upgrades",
            cache = 1.hours,
            queryDetails = QueryDetails(QueryType.IDs, INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryDetails(QueryType.ByID, INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryDetails(QueryType.ByIDs.All, INTEGER)
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryDetails(QueryType.ByPage, INTEGER)
        )

    }
) {

    override fun assertProperties(expected: ExpectedAPIv2Query, actual: APIQuery.V2) {
        assertEquals(expected.queryDetails, actual.queryDetails)
        assertEquals(expected.security, actual.security)

        // TODO: In the future we might have to check the isLocalized flag specifically for schema versions.
        assertEquals(expected.isLocalized, actual.schema.isLocalized, "Mismatched 'isLocalized' flag for ${actual.route}")
    }

    override fun testTypes(queries: Collection<APIQuery.V2>) = sequence<DynamicTest> {
        val query = queries.sortedWith(Comparator { a, b ->
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
        }).first()

        fun SchemaType.firstPossiblyNestedClassOrNull(): SchemaClass? = when (this) {
            is SchemaClass -> this
            is SchemaArray -> items.firstPossiblyNestedClassOrNull()
            else -> null
        }

        fun SchemaType.isClassOrArrayOfClasses() = firstPossiblyNestedClassOrNull() != null

        query.versions.forEach { version ->
            val schema = query[version].second
            if (!schema.isClassOrArrayOfClasses()) return@forEach

            val supportedType = spec.supportedTypes.filter { (key, _) -> key.endpoint == query.endpoint }
                .flatMap { (_, value) -> value }
                .find { it[version].second == schema.firstPossiblyNestedClassOrNull() }

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
        val queryDetails: QueryDetails?,
        val security: Set<TokenScope>
    ) : ExpectedAPIQuery

    class V2SpecBuilder {

        private val queries = mutableListOf<ExpectedAPIv2Query>()

        companion object {
            operator fun invoke(block: V2SpecBuilder.() -> Unit): List<ExpectedAPIv2Query> {
                return V2SpecBuilder().also(block).queries
            }
        }

        fun expectQuery(
            route: String,
            isLocalized: Boolean = false,
            cache: Duration? = null,
            pathParameters: List<ExpectedPathParameter> = emptyList(),
            queryParameters: List<ExpectedQueryParameter> = emptyList(),
            queryDetails: QueryDetails? = null,
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