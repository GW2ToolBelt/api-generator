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
package com.gw2tb.apigen

public enum class APIv1Endpoint(internal val endpointName: String) {
    V1_BUILD("/Build"),
    V1_COLORS("/Colors"),
    V1_CONTINENTS("/Continents"),
    V1_EVENT_DETAILS("/event_details"),
    V1_FILES("/Files"),
    V1_GUILD_DETAILS("/guild_details"),
    V1_ITEM_DETAILS("/item_details"),
    V1_ITEMS("/Items"),
    V1_MAP_FLOOR("/map_floor"),
    V1_MAP_NAMES("/map_names"),
    V1_MAPS("/Maps"),
    V1_RECIPE_DETAILS("/recipe_details"),
    V1_RECIPES("/Recipes"),
    V1_SKIN_DETAILS("/skin_details"),
    V1_SKINS("/Skins"),
    V1_WORLD_NAMES("/world_names"),
    V1_WVW_MATCH_DETAILS("/wvw/match_details"),
    V1_WVW_MATCHES("/wvw/matches"),
    V1_WVW_OBJECTIVE_NAMES("/wvw/objective_names")
}