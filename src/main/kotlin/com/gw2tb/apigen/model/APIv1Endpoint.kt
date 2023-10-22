/*
 * Copyright (c) 2019-2023 Leon Linhart
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
 * The endpoints exposed by version 1 (`v1`) old the Guild Wars 2 API.
 *
 * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1)
 *
 * @param endpointName  the name of the endpoint
 * @param path          the path to the endpoint
 *
 * @since   0.7.0
 */
public enum class APIv1Endpoint(
    override val endpointName: Name,
    override val path: String = endpointName.toSnakeCase()
) : APIEndpoint {
    /**
     * The `/v1/build` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/build)
     *
     * @since   0.7.0
     */
    V1_BUILD(Name.derive(titleCase = "/Build")),
    /**
     * The `/v1/colors` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/colors)
     *
     * @since   0.7.0
     */
    V1_COLORS("/Colors"),
    /**
     * The `/v1/continents` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/continents)
     *
     * @since   0.7.0
     */
    V1_CONTINENTS("/Continents"),
    /**
     * The `/v1/event_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/event_details)
     *
     * @since   0.7.0
     */
    V1_EVENT_DETAILS(Name.derive(snakeCase = "/event_details", titleCase = "/EventDetails")),
    /**
     * The `/v1/files` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/files)
     *
     * @since   0.7.0
     */
    V1_FILES("/Files"),
    /**
     * The `/v1/guild_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/guild_details)
     *
     * @since   0.7.0
     */
    V1_GUILD_DETAILS(Name.derive(snakeCase = "/guild_details", titleCase = "/GuildDetails")),
    /**
     * The `/v1/item_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/item_details)
     *
     * @since   0.7.0
     */
    V1_ITEM_DETAILS(Name.derive(snakeCase = "/item_details", titleCase = "/ItemDetails")),
    /**
     * The `/v1/items` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/items)
     *
     * @since   0.7.0
     */
    V1_ITEMS("/Items"),
    /**
     * The `/v1/map_floor` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/map_floor)
     *
     * @since   0.7.0
     */
    V1_MAP_FLOOR(Name.derive(snakeCase = "/map_floor", titleCase = "/MapFloor")),
    /**
     * The `/v1/map_names` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/map_names)
     *
     * @since   0.7.0
     */
    V1_MAP_NAMES(Name.derive(snakeCase = "/map_names", titleCase = "/MapNames")),
    /**
     * The `/v1/maps` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/maps)
     *
     * @since   0.7.0
     */
    V1_MAPS("/Maps"),
    /**
     * The `/v1/recipe_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/recipe_details)
     *
     * @since   0.7.0
     */
    V1_RECIPE_DETAILS(Name.derive(snakeCase = "/recipe_details", titleCase = "/RecipeDetails")),
    /**
     * The `/v1/recipes` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/recipes)
     *
     * @since   0.7.0
     */
    V1_RECIPES("/Recipes"),
    /**
     * The `/v1/skin_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/skin_details)
     *
     * @since   0.7.0
     */
    V1_SKIN_DETAILS(Name.derive(snakeCase = "/skin_details", titleCase = "/SkinDetails")),
    /**
     * The `/v1/skins` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/skins)
     *
     * @since   0.7.0
     */
    V1_SKINS("/Skins"),
    /**
     * The `/v1/world_names` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/world_names)
     *
     * @since   0.7.0
     */
    V1_WORLD_NAMES(Name.derive(snakeCase = "/world_names", titleCase = "/WorldNames")),
    /**
     * The `/v1/wvw/match_details` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/wvw/match_details)
     *
     * @since   0.7.0
     */
    V1_WVW_MATCH_DETAILS(Name.derive(snakeCase = "/wvw/match_details", titleCase = "/WvW/MatchDetails")),
    /**
     * The `/v1/wvw/matches` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/wvw/matches)
     *
     * @since   0.7.0
     */
    V1_WVW_MATCHES("/WvW/Matches"),
    /**
     * The `/v1/wvw/objective_names` endpoint.
     *
     * [Read more on the official Wiki](https://wiki.guildwars2.com/wiki/API:1/wvw/objetive_names)
     *
     * @since   0.7.0
     */
    V1_WVW_OBJECTIVE_NAMES(Name.derive(snakeCase = "/wvw/objective_names", titleCase = "/WvW/ObjectiveNames"));

    constructor(p: String): this(Name.derive(titleCase = p))

}