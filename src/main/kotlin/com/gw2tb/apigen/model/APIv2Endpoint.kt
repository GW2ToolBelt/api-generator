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
package com.gw2tb.apigen.model

/**
 * The endpoints exposed by version 2 (`v2`) old the Guild Wars 2 API.
 *
 * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2)
 *
 * @param path  the path to the endpoint
 *
 * @since   0.7.0
 */
public enum class APIv2Endpoint(
    override val path: Name
) : APIEndpoint {
    /**
     * The `/v2/account` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT("/Account"),
    /**
     * The `/v2/account/achievements` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/achievements)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_ACHIEVEMENTS("/Account/Achievements"),
    /**
     * The `/v2/account/bank` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/bank)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_BANK("/Account/Bank"),
    /**
     * The `/v2/account/buildstorage` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/buildstorage)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_BUILDSTORAGE("/Account/BuildStorage"),
    /**
     * The `/v2/account/dailycrafting` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/dailycrafting)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_DAILYCRAFTING("/Account/DailyCrafting"),
    /**
     * The `/v2/account/dungeons` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/dungeons)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_DUNGEONS("/Account/Dungeons"),
    /**
     * The `/v2/account/dyes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/dyes)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_DYES("/Account/Dyes"),
    /**
     * The `/v2/account/emotes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/emotes)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_EMOTES("/Account/Emotes"),
    /**
     * The `/v2/account/finishers` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/finishers)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_FINISHERS("/Account/Finishers"),
    /**
     * The `/v2/account/gliders` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/gliders)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_GLIDERS("/Account/Gliders"),
    /**
     * The `/v2/account/home` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/home)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_HOME("/Account/Home"),
    /**
     * The `/v2/account/home/cats` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/home/cats)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_HOME_CATS("/Account/Home/Cats"),
    /**
     * The `/v2/account/home/nodes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/home/nodes)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_HOME_NODES("/Account/Home/Nodes"),
    /**
     * The `/v2/account/inventory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/inventory)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_INVENTORY("/Account/Inventory"),
    /**
     * The `/v2/account/jadebots` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/jadebots)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_JADEBOTS("/Account/JadeBots"),
    /**
     * The `/v2/account/legendaryarmory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/legendaryarmory)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_LEGENDARYARMORY("/Account/LegendaryArmory"),
    /**
     * The `/v2/account/luck` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/luck)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_LUCK("/Account/Luck"),
    /**
     * The `/v2/account/mailcarriers` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mailcarriers)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MAILCARRIERS("/Account/Mailcarriers"),
    /**
     * The `/v2/account/mapchests` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mapchests)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MAPCHESTS("/Account/MapChests"),
    /**
     * The `/v2/account/masteries` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/masteries)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MASTERIES("/Account/Masteries"),
    /**
     * The `/v2/account/mastery/points` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mastery/points)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MASTERY_POINTS("/Account/Mastery/Points"),
    /**
     * The `/v2/account/materials` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/materials)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MATERIALS("/Account/Materials"),
    /**
     * The `/v2/account/minis` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/minis)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MINIS("/Account/Minis"),
    /**
     * The `/v2/account/mounts` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mounts)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MOUNTS("/Account/Mounts"),
    /**
     * The `/v2/account/mounts/skins` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mounts/skins)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MOUNTS_SKINS("/Account/Mounts/Skins"),
    /**
     * The `/v2/account/mounts/types` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/mounts/types)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_MOUNTS_TYPES("/Account/Mounts/Types"),
    /**
     * The `/v2/account/novelties` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/novelties)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_NOVELTIES("/Account/Novelties"),
    /**
     * The `/v2/account/outfits` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/outfits)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_OUTFITS("/Account/Outfits"),
    /**
     * The `/v2/account/pvp/heroes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/pvp/heroes)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_PVP_HEROES("/Account/PvP/Heroes"),
    /**
     * The `/v2/account/raids` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/raids)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_RAIDS("/Account/Raids"),
    /**
     * The `/v2/account/recipes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/recipes)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_RECIPES("/Account/Recipes"),
    /**
     * The `/v2/account/skiffs` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/skiffs)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_SKIFFS("/Account/Skiffs"),
    /**
     * The `/v2/account/skins` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/skins)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_SKINS("/Account/Skins"),
    /**
     * The `/v2/account/titles` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/titles)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_TITLES("/Account/Titles"),
    /**
     * The `/v2/account/wallet` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/wallet)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_WALLET("/Account/Wallet"),
    /**
     * The `/v2/account/worldbosses` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/account/worldbosses)
     *
     * @since   0.7.0
     */
    V2_ACCOUNT_WORLDBOSSES("/Account/WorldBosses"),
    /**
     * The `/v2/achievements` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/achievements)
     *
     * @since   0.7.0
     */
    V2_ACHIEVEMENTS("/Achievements"),
    /**
     * The `/v2/achievements/categories` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/achievements/categories)
     *
     * @since   0.7.0
     */
    V2_ACHIEVEMENTS_CATEGORIES("/Achievements/Categories"),
    /**
     * The `/v2/achievements/daily` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/achievements/daily)
     *
     * @since   0.7.0
     */
    V2_ACHIEVEMENTS_DAILY("/Achievements/Daily"),
    /**
     * The `/v2/achievements/daily/tomorrow` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/achievements/daily/tomorrow)
     *
     * @since   0.7.0
     */
    V2_ACHIEVEMENTS_DAILY_TOMORROW("/Achievements/Daily/Tomorrow"),
    /**
     * The `/v2/achievements/groups` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/achievements/groups)
     *
     * @since   0.7.0
     */
    V2_ACHIEVEMENTS_GROUPS("/Achievements/Groups"),
    /**
     * The `/v2/backstory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/backstory)
     *
     * @since   0.7.0
     */
    V2_BACKSTORY("/Backstory"),
    /**
     * The `/v2/backstory/answers` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/backstory/answers)
     *
     * @since   0.7.0
     */
    V2_BACKSTORY_ANSWERS("/Backstory/Answers"),
    /**
     * The `/v2/backstory/questions` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/backstory/questions)
     *
     * @since   0.7.0
     */
    V2_BACKSTORY_QUESTIONS("/Backstory/Questions"),
    /**
     * The `/v2/build` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/build)
     *
     * @since   0.7.0
     */
    V2_BUILD("/Build"),
    /**
     * The `/v2/characters` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS("/Characters"),
    /**
     * The `/v2/characters/:id/backstory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/backstory)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_BACKSTORY("/Characters/:ID/Backstory"),
    /**
     * The `/v2/characters/:id/buildtabs` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/buildtabs)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_BUILDTABS("/Characters/:ID/BuildTabs"),
    /**
     * The `/v2/characters/:id/buildtabs/active` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/buildtabs)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_BUILDTABS_ACTIVE("/Characters/:ID/BuildTabs/Active"),
    /**
     * The `/v2/characters/:id/core` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/core)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_CORE("/Characters/:ID/Core"),
    /**
     * The `/v2/characters/:id/crafting` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/crafting)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_CRAFTING("/Characters/:ID/Crafting"),
    /**
     * The `/v2/characters/:id/equipment` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/equipment)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_EQUIPMENT("/Characters/:ID/Equipment"),
    /**
     * The `/v2/characters/:id/equipmenttabs` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/equipmenttabs)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_EQUIPMENTTABS("/Characters/:ID/EquipmentTabs"),
    /**
     * The `/v2/characters/:id/equipmenttabs/active` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/equipmenttabs)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_EQUIPMENTTABS_ACTIVE("/Characters/:ID/EquipmentTabs/Active"),
    /**
     * The `/v2/characters/:id/heropoints` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/heropoints)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_HEROPOINTS("/Characters/:ID/HeroPoints"),
    /**
     * The `/v2/characters/:id/inventory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/inventory)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_INVENTORY("/Characters/:ID/Inventory"),
    /**
     * The `/v2/characters/:id/quests` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/quests)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_QUESTS("/Characters/:ID/Quests"),
    /**
     * The `/v2/characters/:id/recipes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/recipes)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_RECIPES("/Characters/:ID/Recipes"),
    /**
     * The `/v2/characters/:id/sab` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/sab)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_SAB("/Characters/:ID/SAB"),
    /**
     * The `/v2/characters/:id/skills` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/skills)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_SKILLS("/Characters/:ID/Skills"),
    /**
     * The `/v2/characters/:id/specializations` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/specializations)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_SPECIALIZATIONS("/Characters/:ID/Specializations"),
    /**
     * The `/v2/characters/:id/training` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/characters/:id/training)
     *
     * @since   0.7.0
     */
    V2_CHARACTERS_TRAINING("/Characters/:ID/Training"),
    /**
     * The `/v2/colors` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/colors)
     *
     * @since   0.7.0
     */
    V2_COLORS("/Colors"),
    /**
     * The `/v2/commerce/delivery` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/commerce/delivery)
     *
     * @since   0.7.0
     */
    V2_COMMERCE_DELIVERY("/Commerce/Delivery"),
    /**
     * The `/v2/commerce/exchange` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/commerce/exchange)
     *
     * @since   0.7.0
     */
    V2_COMMERCE_EXCHANGE("/Commerce/Exchange"),
    /**
     * The `/v2/commerce/listings` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/commerce/listings)
     *
     * @since   0.7.0
     */
    V2_COMMERCE_LISTINGS("/Commerce/Listings"),
    /**
     * The `/v2/commerce/prices` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/commerce/prices)
     *
     * @since   0.7.0
     */
    V2_COMMERCE_PRICES("/Commerce/Prices"),
    /**
     * The `/v2/commerce/transactions` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/commerce/transactions)
     *
     * @since   0.7.0
     */
    V2_COMMERCE_TRANSACTIONS("/Commerce/Transactions"),
    /**
     * The `/v2/continents` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/continents)
     *
     * @since   0.7.0
     */
    V2_CONTINENTS("/Continents"),
    /**
     * The `/v2/continents/:id/floors` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/continents)
     *
     * @since   0.7.0
     */
    V2_CONTINENTS_FLOORS("/Continents/:ID/Floors"),
    /**
     * The `/v2/createsubtoken` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/createsubtoken)
     *
     * @since   0.7.0
     */
    V2_CREATESUBTOKEN("/CreateSubToken"),
    /**
     * The `/v2/currencies` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/currencies)
     *
     * @since   0.7.0
     */
    V2_CURRENCIES("/Currencies"),
    /**
     * The `/v2/dailycrafting` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/dailycrafting)
     *
     * @since   0.7.0
     */
    V2_DAILYCRAFTING("/DailyCrafting"),
    /**
     * The `/v2/dungeons` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/dungeons)
     *
     * @since   0.7.0
     */
    V2_DUNGEONS("/Dungeons"),
    /**
     * The `/v2/emblem` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/emblem)
     *
     * @since   0.7.0
     */
    V2_EMBLEM("/Emblem"),
    /**
     * The `/v2/emotes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/emotes)
     *
     * @since   0.7.0
     */
    V2_EMOTES("/Emotes"),
    /**
     * The `/v2/files` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/files)
     *
     * @since   0.7.0
     */
    V2_FILES("/Files"),
    /**
     * The `/v2/finishers` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/finishers)
     *
     * @since   0.7.0
     */
    V2_FINISHERS("/Finishers"),
    /**
     * The `/v2/gliders` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/gliders)
     *
     * @since   0.7.0
     */
    V2_GLIDERS("/Gliders"),
    /**
     * The `/v2/guild/:id` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id)
     *
     * @since   0.7.0
     */
    V2_GUILD("/Guild/:ID"),
    /**
     * The `/v2/guild/:id/log` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/log)
     *
     * @since   0.7.0
     */
    V2_GUILD_LOG("/Guild/:ID/Log"),
    /**
     * The `/v2/guild/:id/members` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/members)
     *
     * @since   0.7.0
     */
    V2_GUILD_MEMBERS("/Guild/:ID/Members"),
    /**
     * The `/v2/guild/:id/ranks` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/ranks)
     *
     * @since   0.7.0
     */
    V2_GUILD_RANKS("/Guild/:ID/Ranks"),
    /**
     * The `/v2/guild/:id/stash` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/stash)
     *
     * @since   0.7.0
     */
    V2_GUILD_STASH("/Guild/:ID/Stash"),
    /**
     * The `/v2/guild/:id/storage` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/storage)
     *
     * @since   0.7.0
     */
    V2_GUILD_STORAGE("/Guild/:ID/Storage"),
    /**
     * The `/v2/guild/:id/teams` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/teams)
     *
     * @since   0.7.0
     */
    V2_GUILD_TEAMS("/Guild/:ID/Teams"),
    /**
     * The `/v2/guild/:id/treasury` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/treasury)
     *
     * @since   0.7.0
     */
    V2_GUILD_TREASURY("/Guild/:ID/Treasury"),
    /**
     * The `/v2/guild/:id/upgrades` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/:id/upgrades)
     *
     * @since   0.7.0
     */
    V2_GUILD_UPGRADES("/Guild/:ID/Upgrades"),
    /**
     * The `/v2/guild/search` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/search)
     *
     * @since   0.7.0
     */
    V2_GUILD_SEARCH("/Guild/Search"),
    /**
     * The `/v2/guild/permissions` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/permissions)
     *
     * @since   0.7.0
     */
    V2_GUILD_PERMISSIONS("/Guild/Permissions"),
    /**
     * The `/v2/guild/upgrades` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/guild/upgrades)
     *
     * @since   0.7.0
     */
    V2_GUILD_UPGRADES__STATIC("/Guild/Upgrades"),
    /**
     * The `/v2/home` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/home)
     *
     * @since   0.7.0
     */
    V2_HOME("/Home"),
    /**
     * The `/v2/home/cats` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/home/cats)
     *
     * @since   0.7.0
     */
    V2_HOME_CATS("/Home/Cats"),
    /**
     * The `/v2/home/nodes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/home/nodes)
     *
     * @since   0.7.0
     */
    V2_HOME_NODES("/Home/Nodes"),
    /**
     * The `/v2/items` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/items)
     *
     * @since   0.7.0
     */
    V2_ITEMS("/Items"),
    /**
     * The `/v2/itemstats` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/itemstats)
     *
     * @since   0.7.0
     */
    V2_ITEMSTATS("/ItemStats"),
    /**
     * The `/v2/jadebots` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/jadebots)
     *
     * @since   0.7.0
     */
    V2_JADEBOTS("/JadeBots"),
    /**
     * The `/v2/legendaryarmory` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/legendaryarmory)
     *
     * @since   0.7.0
     */
    V2_LEGENDARYARMORY("/LegendaryArmory"),
    /**
     * The `/v2/legends` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/legends)
     *
     * @since   0.7.0
     */
    V2_LEGENDS("/Legends"),
    /**
     * The `/v2/mailcarriers` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/mailcarriers)
     *
     * @since   0.7.0
     */
    V2_MAILCARRIERS("/Mailcarriers"),
    /**
     * The `/v2/mapchests` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/mapchests)
     *
     * @since   0.7.0
     */
    V2_MAPCHESTS("/MapChests"),
    /**
     * The `/v2/maps` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/maps)
     *
     * @since   0.7.0
     */
    V2_MAPS("/Maps"),
    /**
     * The `/v2/masteries` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/masteries)
     *
     * @since   0.7.0
     */
    V2_MASTERIES("/Masteries"),
    /**
     * The `/v2/materials` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/materials)
     *
     * @since   0.7.0
     */
    V2_MATERIALS("/Materials"),
    /**
     * The `/v2/minis` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/minis)
     *
     * @since   0.7.0
     */
    V2_MINIS("/Minis"),
    /**
     * The `/v2/mounts` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/mounts)
     *
     * @since   0.7.0
     */
    V2_MOUNTS("/Mounts"),
    /**
     * The `/v2/mounts/skins` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/mounts/skins)
     *
     * @since   0.7.0
     */
    V2_MOUNTS_SKINS("/Mounts/Skins"),
    /**
     * The `/v2/mounts/types` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/mounts/types)
     *
     * @since   0.7.0
     */
    V2_MOUNTS_TYPES("/Mounts/Types"),
    /**
     * The `/v2/novelties` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/novelties)
     *
     * @since   0.7.0
     */
    V2_NOVELTIES("/Novelties"),
    /**
     * The `/v2/outfits` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/outfits)
     *
     * @since   0.7.0
     */
    V2_OUTFITS("/Outfits"),
    /**
     * The `/v2/pets` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pets)
     *
     * @since   0.7.0
     */
    V2_PETS("/Pets"),
    /**
     * The `/v2/professions` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/professions)
     *
     * @since   0.7.0
     */
    V2_PROFESSIONS("/Professions"),
    /**
     * The `/v2/pvp` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp)
     *
     * @since   0.7.0
     */
    V2_PVP("/PvP"),
    /**
     * The `/v2/pvp/amulets` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/amulets)
     *
     * @since   0.7.0
     */
    V2_PVP_AMULETS("/PvP/Amulets"),
    /**
     * The `/v2/pvp/games` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/games)
     *
     * @since   0.7.0
     */
    V2_PVP_GAMES("/PvP/Games"),
    /**
     * The `/v2/pvp/heroes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/heroes)
     *
     * @since   0.7.0
     */
    V2_PVP_HEROES("/PvP/Heroes"),
    /**
     * The `/v2/pvp/ranks` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/ranks)
     *
     * @since   0.7.0
     */
    V2_PVP_RANKS("/PvP/Ranks"),
    /**
     * The `/v2/pvp/seasons` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/seasons)
     *
     * @since   0.7.0
     */
    V2_PVP_SEASONS("/PvP/Seasons"),
    /**
     * The `/v2/pvp/seasons/:id/leaderboards` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/seasons/:id/leaderboards)
     *
     * @since   0.7.0
     */
    V2_PVP_SEASONS_LEADERBOARDS("/PvP/Seasons/:ID/Leaderboards"),
    /**
     * The `/v2/pvp/standings` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/standings)
     *
     * @since   0.7.0
     */
    V2_PVP_STANDINGS("/PvP/Standings"),
    /**
     * The `/v2/pvp/stats` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/pvp/stats)
     *
     * @since   0.7.0
     */
    V2_PVP_STATS("/PvP/Stats"),
    /**
     * The `/v2/quaggans` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/quaggans)
     *
     * @since   0.7.0
     */
    V2_QUAGGANS("/Quaggans"),
    /**
     * The `/v2/quests` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/quests)
     *
     * @since   0.7.0
     */
    V2_QUESTS("/Quests"),
    /**
     * The `/v2/races` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/races)
     *
     * @since   0.7.0
     */
    V2_RACES("/Races"),
    /**
     * The `/v2/raids` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/raids)
     *
     * @since   0.7.0
     */
    V2_RAIDS("/Raids"),
    /**
     * The `/v2/recipes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/recipes)
     *
     * @since   0.7.0
     */
    V2_RECIPES("/Recipes"),
    /**
     * The `/v2/recipes/search` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/recipes/search)
     *
     * @since   0.7.0
     */
    V2_RECIPES_SEARCH("/Recipes/Search"),
    /**
     * The `/v2/skiffs` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/skiffs)
     *
     * @since   0.7.0
     */
    V2_SKIFFS("/Skiffs"),
    /**
     * The `/v2/skills` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/skills)
     *
     * @since   0.7.0
     */
    V2_SKILLS("/Skills"),
    /**
     * The `/v2/skins` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/skins)
     *
     * @since   0.7.0
     */
    V2_SKINS("/Skins"),
    /**
     * The `/v2/specializations` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/specializations)
     *
     * @since   0.7.0
     */
    V2_SPECIALIZATIONS("/Specializations"),
    /**
     * The `/v2/stories` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/stories)
     *
     * @since   0.7.0
     */
    V2_STORIES("/Stories"),
    /**
     * The `/v2/stories/seasons` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/stories/seasons)
     *
     * @since   0.7.0
     */
    V2_STORIES_SEASONS("/Stories/Seasons"),
    /**
     * The `/v2/titles` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/titles)
     *
     * @since   0.7.0
     */
    V2_TITLES("/Titles"),
    /**
     * The `/v2/tokeninfo` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/tokeninfo)
     *
     * @since   0.7.0
     */
    V2_TOKENINFO("/TokenInfo"),
    /**
     * The `/v2/traits` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/traits)
     *
     * @since   0.7.0
     */
    V2_TRAITS("/Traits"),
    /**
     * The `/v2/wizardsvault/listings` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wizardsvault/listings)
     *
     * @since   0.7.0
     */
    V2_WIZARDSVAULT_LISTINGS("/WizardsVault/Listings"),
    /**
     * The `/v2/wizardsvault/objectives` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wizardsvault/objectives)
     *
     * @since   0.7.0
     */
    V2_WIZARDSVAULT_OBJECTIVES("/WizardsVault/Objectives"),
    /**
     * The `/v2/worldbosses` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/worldbosses)
     *
     * @since   0.7.0
     */
    V2_WORLDBOSSES("/WorldBosses"),
    /**
     * The `/v2/worlds` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/worlds)
     *
     * @since   0.7.0
     */
    V2_WORLDS("/Worlds"),
    /**
     * The `/v2/wvw/abilities` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/abilities)
     *
     * @since   0.7.0
     */
    V2_WVW_ABILITIES("/WvW/Abilities"),
    /**
     * The `/v2/wvw/matches` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/matches)
     *
     * @since   0.7.0
     */
    V2_WVW_MATCHES("/WvW/Matches"),
    /**
     * The `/v2/wvw/matches/overview` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/matches/overview)
     *
     * @since   0.7.0
     */
    V2_WVW_MATCHES_OVERVIEW("/WvW/Matches/Overview"),
    /**
     * The `/v2/wvw/matches/scores` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/matches/scores)
     *
     * @since   0.7.0
     */
    V2_WVW_MATCHES_SCORES("/WvW/Matches/Scores"),
    /**
     * The `/v2/wvw/matches/stats` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/matches/stats)
     *
     * @since   0.7.0
     */
    V2_WVW_MATCHES_STATS("/WvW/Matches/Stats"),
    /**
     * The `/v2/wvw/objectives` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/objectives)
     *
     * @since   0.7.0
     */
    V2_WVW_OBJECTIVES("/WvW/Objectives"),
    /**
     * The `/v2/wvw/ranks` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/ranks)
     *
     * @since   0.7.0
     */
    V2_WVW_RANKS("/WvW/Ranks"),
    /**
     * The `/v2/wvw/upgrades` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:2/wvw/upgrades)
     *
     * @since   0.7.0
     */
    V2_WVW_UPGRADES("/WvW/Upgrades");

    constructor(p: String): this(Name.derive(snakeCase = p.lowercase(), titleCase = p))

}