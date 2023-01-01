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
 * An API key permission scope.
 *
 * @since   0.7.0
 */
public enum class TokenScope {
    /**
     * The `account` scope.
     *
     * @since   0.7.0
     */
    ACCOUNT,
    /**
     * The `builds` scope.
     *
     * @since   0.7.0
     */
    BUILDS,
    /**
     * The `characters` scope.
     *
     * @since   0.7.0
     */
    CHARACTERS,
    /**
     * The `guilds` scope.
     *
     * @since   0.7.0
     */
    GUILDS,
    /**
     * The `inventories` scope.
     *
     * @since   0.7.0
     */
    INVENTORIES,
    /**
     * The `progression` scope.
     *
     * @since   0.7.0
     */
    PROGRESSION,
    /**
     * The `pvp` scope.
     *
     * @since   0.7.0
     */
    PVP,
    /**
     * The `tradingpost` scope.
     *
     * @since   0.7.0
     */
    TRADING_POST,
    /**
     * The `unlock` scope.
     *
     * @since   0.7.0
     */
    UNLOCKS,
    /**
     * The `wallet` scope.
     *
     * @since   0.7.0
     */
    WALLET
}