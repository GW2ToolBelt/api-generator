### 0.8.0

_Released 2025 Feb 27_

#### Improvements

- Endpoints
    - Added support for `/v2/account/homestead/decorations`. [[GH-357](https://github.com/GW2ToolBelt/api-generator/issues/357)]
    - Added support for `/v2/account/homestead/glyphs`. [[GH-355](https://github.com/GW2ToolBelt/api-generator/issues/355)]
    - Added support for `/v2/account/wvw`. [[GH-326](https://github.com/GW2ToolBelt/api-generator/issues/326)]
    - Added support for `/v2/homestead/decorations`. [[GH-356](https://github.com/GW2ToolBelt/api-generator/issues/356)]
    - Added support for `/v2/homestead/decorations/categories`. [[GH-358](https://github.com/GW2ToolBelt/api-generator/issues/358)]
    - Added support for `/v2/homestead/glyphs`. [[GH-354](https://github.com/GW2ToolBelt/api-generator/issues/354)]
    - Added support for `/v2/wvw/guilds`. [[GH-331](https://github.com/GW2ToolBelt/api-generator/issues/331)]
    - Added support for `/v2/wvw/guilds/:region`. [[GH-332](https://github.com/GW2ToolBelt/api-generator/issues/332)]
    - Added support for `/v2/wvw/timers`. [[GH-327](https://github.com/GW2ToolBelt/api-generator/issues/327)]
    - Added support for `/v2/wvw/timers/lockout`. [[GH-328](https://github.com/GW2ToolBelt/api-generator/issues/328)]
    - Added support for `/v2/wvw/timers/teamAssignment`. [[GH-329](https://github.com/GW2ToolBelt/api-generator/issues/329)]
    - Added `inventories` to required permissions to access `/v2/characters/:id/equipment`. [[GH-342](https://github.com/GW2ToolBelt/api-generator/issues/342)]
- Added support for V2 schema `2024-07-20T01:00:00.000Z`.
- Added support for fields whose presence is dependent on the availability of
  multiple token scopes.
- Introduced aliases for many unmapped ID types. [[GH-283](https://github.com/GW2ToolBelt/api-generator/issues/283)]

#### Fixes

- Endpoints:
    - `/v2/character`
        - Corrected token scope information for conditional fields. [[GH-342](https://github.com/GW2ToolBelt/api-generator/issues/342)]

#### Breaking Changes

- The API of `Optionality.MANDATED` has been changed to expose a `Set<TokenScope>`
  instead of single token scope.