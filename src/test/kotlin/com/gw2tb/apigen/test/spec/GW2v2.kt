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
            "/account",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/achievements",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/bank",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/account/buildstorage",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/dailycrafting",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/account/dungeons",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/dyes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/emotes",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/finishers",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/gliders",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/account/home/cats",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/account/home/nodes",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/inventory",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/account/jadebots",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/legendaryarmory",
            security = setOf(ACCOUNT, INVENTORIES, UNLOCKS)
        )

        expectQuery(
            "/account/luck",
            security = setOf(ACCOUNT, PROGRESSION, UNLOCKS)
        )

        expectQuery(
            "/account/mailcarriers",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/mapchests",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/masteries",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/mastery/points",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/materials",
            security = setOf(ACCOUNT, INVENTORIES)
        )

        expectQuery(
            "/account/minis",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/account/mounts/skins",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/mounts/types",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/novelties",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/outfits",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/pvp/heroes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/raids",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/account/recipes",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/skiffs",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/skins",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/titles",
            security = setOf(ACCOUNT, UNLOCKS)
        )

        expectQuery(
            "/account/wallet",
            security = setOf(ACCOUNT, WALLET)
        )

        expectQuery(
            "/account/wizardsvault/daily",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/wizardsvault/listings",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/wizardsvault/special",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/wizardsvault/weekly",
            security = setOf(ACCOUNT)
        )

        expectQuery(
            "/account/worldbosses",
            security = setOf(ACCOUNT, PROGRESSION)
        )

        expectQuery(
            "/achievements",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/achievements",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/achievements/categories",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/achievements/categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/achievements/categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/achievements/categories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery("/achievements/daily")

        expectQuery("/achievements/daily/tomorrow")

        expectQuery(
            "/achievements/groups",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/achievements/groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/achievements/groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/achievements/groups",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/backstory",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/backstory/answers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/backstory/answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/backstory/answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/backstory/answers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/backstory/questions",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/backstory/questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/backstory/questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/backstory/questions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery("/build")

        expectQuery(
            "/characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/characters",
            security = setOf(ACCOUNT, CHARACTERS),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/characters/:id/backstory",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/buildtabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/characters/:id/buildtabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/characters/:id/buildtabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/characters/:id/buildtabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/characters/:id/buildtabs/active",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/core",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/crafting",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/equipment",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/equipmenttabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/characters/:id/equipmenttabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tab", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/characters/:id/equipmenttabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(ExpectedQueryParameter("tabs", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/characters/:id/equipmenttabs",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/characters/:id/equipmenttabs/active",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/heropoints",
            security = setOf(ACCOUNT, CHARACTERS, PROGRESSION),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/inventory",
            security = setOf(ACCOUNT, CHARACTERS, INVENTORIES),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/quests",
            security = setOf(ACCOUNT, CHARACTERS, PROGRESSION),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/recipes",
            security = setOf(ACCOUNT, UNLOCKS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/sab",
            security = setOf(ACCOUNT, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/skills",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/specializations",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/characters/:id/training",
            security = setOf(ACCOUNT, BUILDS, CHARACTERS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/colors",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/colors",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/commerce/delivery",
            security = setOf(ACCOUNT, TRADING_POST)
        )

        expectQuery(
            "/commerce/exchange",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/commerce/exchange/:type",
            pathParameters = listOf(ExpectedPathParameter("type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("quantity", INTEGER))
        )

        expectQuery(
            "/commerce/listings",
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/commerce/listings",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/commerce/listings",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/commerce/listings",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/commerce/prices",
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/commerce/prices",
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/commerce/prices",
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/commerce/prices",
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/commerce/transactions",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADING_POST)
        )

        expectQuery(
            "/commerce/transactions/:relevance",
            cache = Duration.INFINITE,
            security = setOf(ACCOUNT, TRADING_POST),
            pathParameters = listOf(ExpectedPathParameter("relevance", STRING))
        )

        expectQuery(
            "/commerce/transactions/:relevance/:type",
            cache = 1.minutes,
            security = setOf(ACCOUNT, TRADING_POST),
            pathParameters = listOf(
                ExpectedPathParameter("relevance", STRING),
                ExpectedPathParameter("type", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/continents",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/continents",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/continents/:id/floors",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("id", INTEGER)
            ),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/continents/:id/floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("id", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/continents/:id/floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("id", INTEGER)
            ),
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/continents/:id/floors",
            isLocalized = true,
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("id", INTEGER)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/createsubtoken",
            security = setOf(ACCOUNT),
            queryParameters = listOf(
                ExpectedQueryParameter("expire", STRING),
                ExpectedQueryParameter("permissions", STRING),
                ExpectedQueryParameter("urls", STRING, isOptional = true)
            )
        )

        expectQuery(
            "/currencies",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/currencies",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/dailycrafting",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/dailycrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/dailycrafting",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/dailycrafting",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/dungeons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/dungeons",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/dungeons",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/emblem",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/emblem/:type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("type", STRING)),
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/emblem/:type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("type", STRING)),
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/emblem/:type",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            pathParameters = listOf(ExpectedPathParameter("type", STRING)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/emblem/:type",
            cache = 1.hours,
            pathParameters = listOf(ExpectedPathParameter("type", STRING)),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/emotes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/emotes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/emotes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/files",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/files",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/files",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/finishers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/finishers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/guild/:id",
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/log",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/members",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/ranks",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/stash",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/storage",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/teams",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/treasury",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/:id/upgrades",
            security = setOf(ACCOUNT, GUILDS),
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/guild/permissions",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/guild/permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/guild/permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/guild/permissions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/guild/search",
            queryParameters = listOf(ExpectedQueryParameter("name", STRING))
        )

        expectQuery(
            "/guild/upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/guild/upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/guild/upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/guild/upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/gliders",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/gliders",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/home",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/home/cats",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/home/cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/home/cats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/home/cats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/home/nodes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/home/nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/home/nodes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/home/nodes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/items",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/items",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/itemstats",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/itemstats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/itemstats",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/itemstats",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/jadebots",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/jadebots",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/jadebots",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/jadebots",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/legendaryarmory",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/legendaryarmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/legendaryarmory",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/legendaryarmory",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/legends",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/legends",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/legends",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/mailcarriers",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/mailcarriers",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/mapchests",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/mapchests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/mapchests",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/mapchests",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/maps",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/maps",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/masteries",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/masteries",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/materials",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/materials",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/minis",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/minis",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/mounts",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/mounts/skins",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/mounts/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/mounts/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/mounts/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/mounts/types",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/mounts/types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/mounts/types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/mounts/types",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/novelties",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/novelties",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/outfits",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/outfits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/pets",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/pets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/professions",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/professions",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/pvp",
            cache = Duration.INFINITE
        )

        expectQuery(
            "/pvp/amulets",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/pvp/amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/pvp/amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/pvp/amulets",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/pvp/games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/pvp/games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/pvp/games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/pvp/games",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/pvp/heroes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/pvp/heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/pvp/heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/pvp/heroes",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/pvp/ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/pvp/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/pvp/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/pvp/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/pvp/seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/pvp/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/pvp/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/pvp/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/pvp/seasons/:id/leaderboards",
            cache = Duration.INFINITE,
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING)
            )
        )

        expectQuery(
            "/pvp/seasons/:id/leaderboards/:board",
            cache = Duration.INFINITE,
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING),
                ExpectedPathParameter("board", STRING),
            )
        )

        expectQuery(
            "/pvp/seasons/:id/leaderboards/:board/:region",
            cache = 1.hours,
            pathParameters = listOf(
                ExpectedPathParameter("id", STRING),
                ExpectedPathParameter("board", STRING),
                ExpectedPathParameter("region", STRING)
            ),
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/pvp/standings",
            security = setOf(ACCOUNT, PVP)
        )

        expectQuery(
            "/pvp/stats",
            cache = 1.hours,
            security = setOf(ACCOUNT, PVP)
        )

        expectQuery(
            "/quaggans",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/quaggans",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/quaggans",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/quests",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/quests",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/races",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/races",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/raids",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/raids",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/raids",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/recipes",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/recipes",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/recipes",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/recipes/search",
            queryParameters = listOf(ExpectedQueryParameter("input", INTEGER))
        )
        expectQuery(
            "/recipes/search",
            queryParameters = listOf(ExpectedQueryParameter("output", INTEGER))
        )

        expectQuery(
            "/skiffs",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/skiffs",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/skiffs",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/skiffs",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/skills",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/skills",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/skins",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/skins",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/specializations",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/specializations",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/stories",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/stories",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/stories/seasons",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/stories/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/stories/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/stories/seasons",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/titles",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/titles",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/traits",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>(supportsAll = false)
        )
        expectQuery(
            "/traits",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/tokeninfo",
            security = setOf()
        )

        expectQuery(
            "/wizardsvault",
            cache = 1.hours
        )

        expectQuery(
            "/wizardsvault/listings",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/listings",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/listings",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/listings",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/wizardsvault/objectives",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/objectives",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/objectives",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/wizardsvault/objectives",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/worldbosses",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/worldbosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/worldbosses",
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/worldbosses",
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/worlds",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/worlds",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/wvw/abilities",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/wvw/abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/abilities",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/wvw/matches",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/wvw/matches",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/wvw/matches/overview",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/wvw/matches/overview",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/overview",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/wvw/matches/scores",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/wvw/matches/scores",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/scores",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/wvw/matches/stats",
            cache = 1.seconds,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/wvw/matches/stats",
            cache = 1.seconds,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/wvw/matches/stats",
            cache = 1.seconds,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/wvw/objectives",
            cache = 1.hours,
            queryDetails = QueryIDs<IRString>()
        )
        expectQuery(
            "/wvw/objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", STRING)),
            queryDetails = QueryByID<IRString>()
        )
        expectQuery(
            "/wvw/objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", STRING.array)),
            queryDetails = QueryByIDs<IRString>()
        )
        expectQuery(
            "/wvw/objectives",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRString>()
        )

        expectQuery(
            "/wvw/ranks",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/wvw/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/ranks",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(
                ExpectedQueryParameter("page", INTEGER),
                ExpectedQueryParameter("page_size", INTEGER, isOptional = true)
            ),
            queryDetails = QueryByPage<IRInteger>()
        )

        expectQuery(
            "/wvw/upgrades",
            cache = 1.hours,
            queryDetails = QueryIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("id", INTEGER)),
            queryDetails = QueryByID<IRInteger>()
        )
        expectQuery(
            "/wvw/upgrades",
            isLocalized = true,
            cache = 1.hours,
            queryParameters = listOf(ExpectedQueryParameter("ids", INTEGER.array)),
            queryDetails = QueryByIDs<IRInteger>()
        )
        expectQuery(
            "/wvw/upgrades",
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
            { it.queryType is QueryType.IDs && it.idType.lowered() is T}

        inline fun <reified T : IRTypeUse<*>> QueryByID(): (IRAPIQuery.Details) -> Boolean =
            { it.queryType is QueryType.ByID && it.idType.lowered() is T}

        inline fun <reified T : IRTypeUse<*>> QueryByIDs(supportsAll: Boolean = true): (IRAPIQuery.Details) -> Boolean =
            { it.queryType.let { qt -> qt is QueryType.ByIDs && qt.supportsAll == supportsAll } && it.idType.lowered() is T }

        inline fun <reified T : IRTypeUse<*>> QueryByPage(): (IRAPIQuery.Details) -> Boolean =
            { it.queryType is QueryType.ByPage && it.idType.lowered() is T }

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