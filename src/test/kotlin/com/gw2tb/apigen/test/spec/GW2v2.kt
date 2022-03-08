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
package com.gw2tb.apigen.test.spec

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.TokenScope.*
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.time.Duration

class GW2v2 : SpecTest<APIQuery.V2, APIType.V2, GW2v2.ExpectedAPIv2Query>(
    "GW2v2",
    APIVersion.getV2(),
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Achievements/Categories",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery("/Achievements/Daily")

        expectQuery("/Achievements/Daily/Tomorrow")

        expectQuery(
            "/Achievements/Groups",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Backstory",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Backstory/Answers",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Backstory/Questions",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
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
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
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
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Commerce/Prices",
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Continents",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Continents/:ID/Floors",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
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
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Emblem",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Files",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Finishers",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Guild/:ID",
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            )
        )

        expectQuery(
            "/Guild/:ID/Log",
            security = setOf(ACCOUNT, GUILDS),
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
            "/Guild/:ID/Stash",
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
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Guild/Search",
            queryParameters = listOf(ExpectedQueryParameter("name", STRING))
        )

        expectQuery(
            "/Guild/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Gliders",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Items",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Mailcarriers",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Maps",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Masteries",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Materials",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Minis",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Mounts/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Mounts/Types",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Novelties",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Outfits",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Pets",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Professions",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/PvP",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/PvP/Amulets",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/PvP/Heroes",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/PvP/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/PvP/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
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
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Quests",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Races",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
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
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Specializations",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Stories",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Stories/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Titles",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/Traits",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>(supportsAll = false)
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/TokenInfo",
            security = setOf()
        )

        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/Worlds",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/WvW/Abilities",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/WvW/Objectives",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<SchemaString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<SchemaString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaString>()
        )

        expectQuery(
            "/WvW/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
        )

        expectQuery(
            "/WvW/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<SchemaInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<SchemaInteger>()
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

    override fun testType(type: APIType.V2) = sequence<DynamicTest> {

        type.significantVersions.forEach { version ->
            val schema = type[version].data

            yield(DynamicTest.dynamicTest("$prefix${type.name}${if (version != com.gw2tb.apigen.model.v2.V2SchemaVersion.V2_SCHEMA_CLASSIC) "+${version.identifier}" else ""}") {
                val data = TestData[spec, type.name, version]
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

        inline fun <reified T : SchemaTypeUse> QueryIDs(): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.IDs && it.idType is T}

        inline fun <reified T : SchemaTypeUse> QueryByID(): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.ByID && it.idType is T}

        inline fun <reified T : SchemaTypeUse> QueryByIDs(supportsAll: Boolean = true): (QueryDetails) -> Boolean =
            { it.queryType.let { qt -> qt is QueryType.ByIDs && qt.supportsAll == supportsAll } && it.idType is T }

        inline fun <reified T : SchemaTypeUse> QueryByPage(): (QueryDetails) -> Boolean =
            { it.queryType is QueryType.ByPage && it.idType is T }

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