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

import com.gw2tb.apigen.internal.spec.*
import com.gw2tb.apigen.ir.*
import com.gw2tb.apigen.ir.AliasCollector
import com.gw2tb.apigen.ir.ReferenceCollector
import com.gw2tb.apigen.ir.ResolverContext
import com.gw2tb.apigen.ir.collectAliases
import com.gw2tb.apigen.ir.model.IRAPIQuery
import com.gw2tb.apigen.ir.model.IRAPIType
import com.gw2tb.apigen.ir.model.IRAPIVersion
import com.gw2tb.apigen.model.*
import com.gw2tb.apigen.model.v2.SchemaVersion
import com.gw2tb.apigen.schema.SchemaAlias
import com.gw2tb.apigen.schema.SchemaTypeDeclaration
import com.gw2tb.apigen.schema.model.APIVersion
import java.util.*

/**
 * The main entry-point of the `api-generator` library.
 *
 * Invoke the constructor to run the generator and use the constructor
 * parameters to configure the included API endpoints. The resulting
 * [APIGenerator] instance contains all generated information. The [apiV1] and
 * [apiV2] fields may be used to retrieve V1 and V2 data respectively.
 * Additionally, [primitive aliases][SchemaAlias] are available via the
 * [aliases] field.
 *
 * @param v1Endpoints       the [v1 endpoints][APIv1Endpoint] to include
 * @param v2Endpoints       the [v2 endpoints][APIv2Endpoint] to include
 * @param v2SchemaVersion   the schema version of the v2 endpoints
 * @param includeMumbleLink whether to include the MumbleLink API
 *
 * @since   0.7.0
 */
@OptIn(LowLevelApiGenApi::class)
public class APIGenerator(
    v1Endpoints: Set<APIv1Endpoint> = EnumSet.allOf(APIv1Endpoint::class.java),
    v2Endpoints: Set<APIv2Endpoint> = EnumSet.allOf(APIv2Endpoint::class.java),
    v2SchemaVersion: SchemaVersion = SchemaVersion.V2_SCHEMA_CLASSIC,
    includeMumbleLink: Boolean = true
) {

    /**
     * The [aliases][SchemaAlias] for primitive types.
     *
     * @since   0.7.0
     */
    public val aliases: Map<QualifiedTypeName.Alias, SchemaAlias>

    /**
     * A low-level representation of version 1 (`v1`) of the Guild Wars 2 API.
     *
     * @since   0.7.0
     */
    public val irApiV1: IRAPIVersion<IRAPIQuery.V1, IRAPIType.V1>

    /**
     * Version 1 (`v1`) of the Guild Wars 2 API.
     *
     * @since   0.7.0
     */
    public val apiV1: APIVersion

    /**
     * A low-level representation of version 2 (`v2`) of the Guild Wars 2 API.
     *
     * @since   0.7.0
     */
    public val irApiV2: IRAPIVersion<IRAPIQuery.V2, IRAPIType.V2>

    /**
     * Version 2 (`v2`) of the Guild Wars 2 API.
     *
     * @since   0.7.0
     */
    public val apiV2: APIVersion

    /**
     * The definition of Guild Wars 2's use of the `identity` buffer of the MumbleLink protocol.
     *
     * @since   0.7.0
     */
    public val mumbleIdentity: SchemaTypeDeclaration?

    init {
        irApiV1 = GW2v1.build(v1Endpoints)
        irApiV2 = GW2v2.build(v2Endpoints)

        aliases = collectAliases { collector ->
            apiV1 = irApiV1.resolve(collector)
            apiV2 = irApiV2.resolve(collector, v2SchemaVersion)

            mumbleIdentity = if (includeMumbleLink) {
                val noopReferenceCollector = ReferenceCollector { _, _ -> }

                MUMBLE_IDENTITY.resolve(
                    resolverContext = object : ResolverContext {
                        override val aliasCollector: AliasCollector get() = collector
                        override val referenceCollector: ReferenceCollector get() = noopReferenceCollector
                    },
                    v2SchemaVersion = null
                )
            } else {
                null
            }
        }
    }

}