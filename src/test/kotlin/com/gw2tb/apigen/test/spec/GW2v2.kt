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
@file:OptIn(LowLevelApiGenApi::class)
package com.gw2tb.apigen.test.spec

import com.gw2tb.apigen.*
import com.gw2tb.apigen.internal.dsl.*
import com.gw2tb.apigen.internal.spec.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.model.*
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.TokenScope.*
import com.gw2tb.apigen.schema.*
import com.gw2tb.apigen.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*
import kotlin.time.Duration

class GW2v2 : SpecTest<IRAPIQuery.V2, IRAPIType.V2, GW2v2.ExpectedAPIv2Query>(
    "GW2v2",
    GW2v2.build(EnumSet.allOf(APIv2Endpoint::class.java)),
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Achievements/Categories",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Achievements/Categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery("/Achievements/Daily")

        expectQuery("/Achievements/Daily/Tomorrow")

        expectQuery(
            "/Achievements/Groups",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Achievements/Groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Backstory",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Backstory/Answers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Backstory/Answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Backstory/Questions",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Backstory/Questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery("/Build")

        expectQuery(
            "/Characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Characters/:ID/BuildTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
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
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Characters/:ID/BuildTabs/Active",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
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
            "/Characters/:ID/Equipment",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Characters/:ID/EquipmentTabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("ID", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
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
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Characters/:ID/EquipmentTabs/Active",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Commerce/Delivery",
            security = setOf(ACCOUNT, TRADING_POST)
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Commerce/Listings",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Commerce/Prices",
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Commerce/Prices",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Commerce/Transactions",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADING_POST)
        )

        expectQuery(
            "/Commerce/Transactions/:Relevance",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADING_POST),
            pathParameters = listOf(ExpectedPathParameter("Relevance", STRING))
        )

        expectQuery(
            "/Commerce/Transactions/:Relevance/:Type",
            cache = 1.minutes,
            security = setOf(ACCOUNT, TRADING_POST),
            pathParameters = listOf(
                ExpectedPathParameter("Relevance", STRING),
                ExpectedPathParameter("Type", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Continents",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Continents/:ID/Floors",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Continents/:ID/Floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("ID", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
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
            queryDetails = QueryByPage<IRInteger>()
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/DailyCrafting",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Dungeons",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Emblem",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Emblem/:Type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("Type", STRING)),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Emotes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Files",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Files",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Finishers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
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
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Guild/Permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Guild/Search",
            queryParameters = listOf(ExpectedQueryParameter("name", STRING))
        )

        expectQuery(
            "/Guild/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Guild/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Gliders",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Home/Cats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Home/Nodes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Items",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/ItemStats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/LegendaryArmory",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Legends",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Mailcarriers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/MapChests",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Maps",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Masteries",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Materials",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Minis",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/Mounts/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Mounts/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Mounts/Types",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Mounts/Types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Novelties",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Outfits",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Pets",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Professions",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/PvP",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/PvP/Amulets",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/PvP/Amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/PvP/Games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/PvP/Heroes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/PvP/Heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/PvP/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/PvP/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/PvP/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/PvP/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
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
            queryDetails = QueryByPage<IRInteger>()
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
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Quaggans",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Quests",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Races",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Raids",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Recipes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
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
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Skins",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Specializations",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Stories",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Stories/Seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/Stories/Seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Titles",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/Traits",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/Traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/TokenInfo",
            security = setOf()
        )

        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WorldBosses",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/Worlds",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/Worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/WvW/Abilities",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Overview",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Scores",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WvW/Matches/Stats",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/WvW/Objectives",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/WvW/Objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/WvW/Ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/WvW/Upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/WvW/Upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

    }
) {

    override fun assertProperties(expected: ExpectedAPIv2Query, actual: IRAPIQuery.V2) {
        val qdp = expected.queryDetails
        if (qdp == null) {
            assertNull(actual.details)
        } else {
            assertTrue(qdp(actual.details!!))
        }

        assertEquals(expected.security, actual.security)

        // TODO: In the future we might have to check the isLocalized flag specifically for schema versions.
        assertEquals(expected.isLocalized, actual.flatten { it.resolve(VoidResolverContext, actual.since).isLocalized }, "Mismatched 'isLocalized' flag for ${actual.path}")
    }

    override fun testType(irType: IRAPIType.V2) = sequence<DynamicTest> {
        irType.significantVersions.forEach { version ->
            val type = irType.resolve(VoidResolverContext, v2SchemaVersion = version)
            val schema = type.schema

            yield(DynamicTest.dynamicTest("$prefix${type.name}${if (version != com.gw2tb.apigen.model.v2.SchemaVersion.V2_SCHEMA_CLASSIC) "+${version.identifier}" else ""}") {
                val data = TestData[type, version]
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
        val queryDetails: ((IRAPIQuery.Details) -> Boolean)?,
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

        inline fun <reified T : IRTypeUse<*>> QueryIDs(): (IRAPIQuery.Details) -> Boolean =
            { it.queryType is QueryType.IDs && it.idType is T}

        inline fun <reified T : IRTypeUse<*>> QueryByID(): (IRAPIQuery.Details) -> Boolean =
            { it.queryType is QueryType.ByID && it.idType is T}

        inline fun <reified T : IRTypeUse<*>> QueryByIDs(supportsAll: Boolean = true): (IRAPIQuery.Details) -> Boolean =
            { it.queryType.let { qt -> qt is QueryType.ByIDs && qt.supportsAll == supportsAll } && it.idType is T }

        inline fun <reified T : IRTypeUse<*>> QueryByPage(): (IRAPIQuery.Details) -> Boolean =
            { it.queryType is QueryType.ByPage && it.idType is T }

        fun expectQuery(
            route: String,
            isLocalized: Boolean = false,
            cache: Duration? = null,
            pathParameters: List<ExpectedPathParameter> = emptyList(),
            queryParameters: List<ExpectedQueryParameter> = emptyList(),
            queryDetails: ((IRAPIQuery.Details) -> Boolean)? = null,
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