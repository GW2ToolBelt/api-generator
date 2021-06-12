### 0.4.0

_Released 2021 Jun 12_

#### Improvements

- Endpoints:
  - Added support for `/v1/build`. [[GH-159](https://github.com/GW2ToolBelt/api-generator/issues/159)]
  - Added support for `/v1/colors`. [[GH-160](https://github.com/GW2ToolBelt/api-generator/issues/160)]
  - Added support for `/v1/continents`. [[GH-161](https://github.com/GW2ToolBelt/api-generator/issues/161)]
  - Added support for `/v1/event_details`. [[GH-162](https://github.com/GW2ToolBelt/api-generator/issues/162)]
  - Added support for `/v1/files`. [[GH-164](https://github.com/GW2ToolBelt/api-generator/issues/164)]
  - Added support for `/v1/guild_details`. [[GH-165](https://github.com/GW2ToolBelt/api-generator/issues/165)]
  - Added support for `/v1/item_details`. [[GH-166](https://github.com/GW2ToolBelt/api-generator/issues/166)]
  - Added support for `/v1/items`. [[GH-167](https://github.com/GW2ToolBelt/api-generator/issues/167)]
  - Added support for `/v1/map_floor`. [[GH-168](https://github.com/GW2ToolBelt/api-generator/issues/168)]
  - Added support for `/v1/map_names`. [[GH-169](https://github.com/GW2ToolBelt/api-generator/issues/169)]
  - Added support for `/v1/maps`. [[GH-170](https://github.com/GW2ToolBelt/api-generator/issues/170)]
  - Added support for `/v1/recipe_details`. [[GH-171](https://github.com/GW2ToolBelt/api-generator/issues/171)]
  - Added support for `/v1/recipes`. [[GH-172](https://github.com/GW2ToolBelt/api-generator/issues/172)]
  - Added support for `/v1/skin_details`. [[GH-158](https://github.com/GW2ToolBelt/api-generator/issues/158)]
  - Added support for `/v1/skins`. [[GH-157](https://github.com/GW2ToolBelt/api-generator/issues/157)]
  - Added support for `/v1/world_names`. [[GH-173](https://github.com/GW2ToolBelt/api-generator/issues/173)]
  - Added support for `/v1/wvw/match_details`. [[GH-174](https://github.com/GW2ToolBelt/api-generator/issues/174)]
  - Added support for `/v1/wvw/matches`. [[GH-175](https://github.com/GW2ToolBelt/api-generator/issues/175)]
  - Added support for `/v1/wvw/objectives_names`. [[GH-176](https://github.com/GW2ToolBelt/api-generator/issues/176)]
  - Added support for `/v2/characters/:id/backstory`. [[GH-47](https://github.com/GW2ToolBelt/api-generator/issues/47)]
  - Added support for `/v2/characters/:id/buildtabs`. [[GH-48](https://github.com/GW2ToolBelt/api-generator/issues/48)]
  - Added support for `/v2/characters/:id/core`. [[GH-50](https://github.com/GW2ToolBelt/api-generator/issues/50)]
  - Added support for `/v2/characters/:id/crafting`. [[GH-51](https://github.com/GW2ToolBelt/api-generator/issues/51)]
  - Added support for `/v2/characters/:id/heropoints`. [[GH-56](https://github.com/GW2ToolBelt/api-generator/issues/56)]
  - Added support for `/v2/characters/:id/quests`. [[GH-58](https://github.com/GW2ToolBelt/api-generator/issues/58)]
  - Added support for `/v2/characters/:id/recipes`. [[GH-59](https://github.com/GW2ToolBelt/api-generator/issues/59)]
  - Added support for `/v2/characters/:id/sab`. [[GH-60](https://github.com/GW2ToolBelt/api-generator/issues/60)]
  - Added support for `/v2/characters/:id/skills`. [[GH-61](https://github.com/GW2ToolBelt/api-generator/issues/61)]
  - Added support for `/v2/characters/:id/specializations`. [[GH-62](https://github.com/GW2ToolBelt/api-generator/issues/62)]
  - Added support for `/v2/characters/:id/training`. [[GH-63](https://github.com/GW2ToolBelt/api-generator/issues/63)]
  - Added support for `/v2/continents/:id/floors`. [[GH-71](https://github.com/GW2ToolBelt/api-generator/issues/71)]
  - Added support for `/v2/guild/:id/storage`. [[GH-86](https://github.com/GW2ToolBelt/api-generator/issues/86)]
  - Added support for `/v2/guild/:id/teams`. [[GH-87](https://github.com/GW2ToolBelt/api-generator/issues/87)]
  - Added support for `/v2/guild/:id/treasury`. [[GH-88](https://github.com/GW2ToolBelt/api-generator/issues/88)]
  - Added support for `/v2/guild/search`. [[GH-91](https://github.com/GW2ToolBelt/api-generator/issues/91)]
  - Added support for `/v2/pvp/games`. [[GH-114](https://github.com/GW2ToolBelt/api-generator/issues/114)]
  - Added support for `/v2/pvp/seasons`. [[GH-117](https://github.com/GW2ToolBelt/api-generator/issues/117)]
  - Added support for `/v2/pvp/seasons/:id/leaderboards`. [[GH-118](https://github.com/GW2ToolBelt/api-generator/issues/118)]
  - Added support for `/v2/pvp/seasons/:id/leaderboards/:board`.
  - Added support for `/v2/pvp/seasons/:id/leaderboards/:board/:region`. [[GH-119](https://github.com/GW2ToolBelt/api-generator/issues/119)]
  - Added support for `/v2/pvp/standings`. [[GH-120](https://github.com/GW2ToolBelt/api-generator/issues/120)]
  - Added support for `/v2/pvp/stats`. [[GH-121](https://github.com/GW2ToolBelt/api-generator/issues/121)]
  - Added support for `/v2/recipes/search`. [[GH-127](https://github.com/GW2ToolBelt/api-generator/issues/127)]
  - Added support for `/v2/wvw/matches`. [[GH-139](https://github.com/GW2ToolBelt/api-generator/issues/139)]
  - Added support for `/v2/wvw/matches/overview`. [[GH-140](https://github.com/GW2ToolBelt/api-generator/issues/140)]
  - Added support for `/v2/wvw/matches/scores`. [[GH-141](https://github.com/GW2ToolBelt/api-generator/issues/141)]
  - Added support for `/v2/wvw/matches/stats`. [[GH-142](https://github.com/GW2ToolBelt/api-generator/issues/142)]
  - Added missing properties `dyes`, `upgrade_slot_indices`, and `stats` to `/v2/account/bank`.
  - Added documentation for `upgrade_slot_indices` to `/v2/characters/:id/inventory`.
  - Added missing property `unlock_items` to `/v2/gliders`.
  - Added documentation for `attribute_adjustment` to `/v2/items`.
  - Added missing property `type` to `/v2/tokeninfo` classic schema.
- Added support for V2 schema `2021-04-06T21:00:00.000Z`.
- Reworked library entry-points and split them into available types and queries.
- Reworked schema-version-dependent data representation.
- Moved `isLocalized` from `APIQuery` to `SchemaType` to support better representation of the underlying data. [[GH-151](https://github.com/GW2ToolBelt/api-generator/issues/151)]
- Consistently use `ID` spelling instead (instead of `Id`).

#### Fixes

- Endpoints:
  - Changed `ID` type for `/v2/emblem/:type` from `STRING` to `INTEGER`.
  - Changed `ID` type for `/v2/finishers` from `STRING` to `INTEGER`.
  - Moved `InfixUpgrade` and `InfusionSlot` into item details for `/v2/items`.
  - Changed `ID` type for `/v2/minis` from `STRING` to `INTEGER`.
  - Changed `ItemID` type for `/v2/minis` from `STRING` to `INTEGER`.
  - Changed `ID` type for `/v2/pets` from `STRING` to `INTEGER`.
  - Added missing `description` field for `/v2/pvp/heroes`.
  - Corrected serial name for `TeamID` for `/pvp/seasons/:id/leaderboards/:board/:region`
  - Corrected the schema for `/v2/quaggans`.
  - Changed `ID` type for `/v2/quests` from `STRING` to `INTEGER`.
  - Changed `ID` type for `/v2/recipes` from `STRING` to `INTEGER`.
  - Corrected the name for `GuildIngredient` type for `/v2/recipes`.
  - Changed `ID` type for `/v2/skins` from `STRING` to `INTEGER`.
  - Corrected serial names for race-specific color overrides in `/v2/skins`.


---

### 0.3.0

_Released 2021 Jan 21_

#### Improvements

- Endpoints:
    - Added support for `/v2/account/buildstorage`. [[GH-4](https://github.com/GW2ToolBelt/api-generator/issues/4)]
    - Added support for `/v2/account/home`. [[GH-11](https://github.com/GW2ToolBelt/api-generator/issues/11)]
    - Added support for `/v2/account/home/cats`. [[GH-12](https://github.com/GW2ToolBelt/api-generator/issues/12)]
    - Added support for `/v2/account/luck`. [[GH-15](https://github.com/GW2ToolBelt/api-generator/issues/15)]
    - Added support for `/v2/account/mounts`. [[GH-23](https://github.com/GW2ToolBelt/api-generator/issues/23)]
    - Added support for `/v2/achievements`. [[GH-35](https://github.com/GW2ToolBelt/api-generator/issues/35)]
    - Added support for `/v2/achievements/categories`. [[GH-36](https://github.com/GW2ToolBelt/api-generator/issues/36)]
    - Added support for `/v2/achievements/daily`. [[GH-37](https://github.com/GW2ToolBelt/api-generator/issues/37)]
    - Added support for `/v2/achievements/daily/tomorrow`. [[GH-38](https://github.com/GW2ToolBelt/api-generator/issues/38)]
    - Added support for `/v2/achievements/groups`. [[GH-39](https://github.com/GW2ToolBelt/api-generator/issues/39)]
    - Added support for `/v2/backstory`.
    - Added support for `/v2/backstory/answers`. [[GH-43](https://github.com/GW2ToolBelt/api-generator/issues/43)]
    - Added support for `/v2/backstory/questions`. [[GH-44](https://github.com/GW2ToolBelt/api-generator/issues/44)]
    - Added support for `/v2/dungeons`. [[GH-75](https://github.com/GW2ToolBelt/api-generator/issues/75)]
    - Added support for `/v2/finishers`. [[GH-79](https://github.com/GW2ToolBelt/api-generator/issues/79)]
    - Added support for `/v2/guild/:id/members`. [[GH-83](https://github.com/GW2ToolBelt/api-generator/issues/83)]
    - Added support for `/v2/guild/:id/ranks`. [[GH-84](https://github.com/GW2ToolBelt/api-generator/issues/84)]
    - Added support for `/v2/guild/:id/upgrades`. [[GH-89](https://github.com/GW2ToolBelt/api-generator/issues/89)]
    - Added support for `/v2/guild/permissions`. [[GH-90](https://github.com/GW2ToolBelt/api-generator/issues/90)]
    - Added support for `/v2/guild/upgrades`. [[GH-92](https://github.com/GW2ToolBelt/api-generator/issues/92)]
    - Added support for `/v2/home`. [[GH-93](https://github.com/GW2ToolBelt/api-generator/issues/93)]
    - Added support for `/v2/home/cats`. [[GH-94](https://github.com/GW2ToolBelt/api-generator/issues/94)]
    - Added support for `/v2/home/nodes`. [[GH-95](https://github.com/GW2ToolBelt/api-generator/issues/95)]
    - Added support for `/v2/mailcarriers`. [[GH-99](https://github.com/GW2ToolBelt/api-generator/issues/99)]
    - Added support for `/v2/minis`. [[GH-104](https://github.com/GW2ToolBelt/api-generator/issues/104)]
    - Added support for `/v2/mounts`. [[GH-105](https://github.com/GW2ToolBelt/api-generator/issues/105)]
    - Added support for `/v2/mounts/skins`. [[GH-106](https://github.com/GW2ToolBelt/api-generator/issues/106)]
    - Added support for `/v2/mounts/types`. [[GH-107](https://github.com/GW2ToolBelt/api-generator/issues/107)]
    - Added support for `/v2/novelties`. [[GH-108](https://github.com/GW2ToolBelt/api-generator/issues/108)]
    - Added support for `/v2/pets`. [[GH-110](https://github.com/GW2ToolBelt/api-generator/issues/110)]
    - Added support for `/v2/pvp`. [[GH-112](https://github.com/GW2ToolBelt/api-generator/issues/112)]
    - Added support for `/v2/pvp/amulets`. [[GH-113](https://github.com/GW2ToolBelt/api-generator/issues/113)]
    - Added support for `/v2/pvp/heroes`. [[GH-115](https://github.com/GW2ToolBelt/api-generator/issues/115)]
    - Added support for `/v2/pvp/ranks`. [[GH-116](https://github.com/GW2ToolBelt/api-generator/issues/116)]
    - Added support for `/v2/quaggans`. [[GH-122](https://github.com/GW2ToolBelt/api-generator/issues/122)]
    - Added support for `/v2/quests`. [[GH-123](https://github.com/GW2ToolBelt/api-generator/issues/123)]
    - Added support for `/v2/raids`. [[GH-125](https://github.com/GW2ToolBelt/api-generator/issues/125)]
    - Added support for `/v2/skins`. [[GH-129](https://github.com/GW2ToolBelt/api-generator/issues/129)]
    - Added support for `/v2/stories`. [[GH-131](https://github.com/GW2ToolBelt/api-generator/issues/131)]
    - Added support for `/v2/stories/seasons`. [[GH-132](https://github.com/GW2ToolBelt/api-generator/issues/132)]
    - Added support for `/v2/wvw/abilities`. [[GH-138](https://github.com/GW2ToolBelt/api-generator/issues/138)]
- Conditional interpretations may now be bound to schema versions.
- Implemented automated testing (unit tests). [[GH-156](https://github.com/GW2ToolBelt/api-generator/issues/156)]
    - Data intended for testing is now available via `TestData`.

#### Fixes

- Endpoints:
    - Corrected the schema for `/v2/account/emotes`.
    - Corrected the schema for `/v2/account/finishers`.
    - Corrected the schema for `/v2/colors`.
    - `maxZoom` is missing in `/v2/continents`.
    - Corrected the schema for `/v2/commerce/exchange/:type`.
    - `/v2/commerce/transactions/:relevance` and `/v2/commerce/transactions/:relevance/:type`
      now have correct security information.
    - Query parameters of `/v2/createsubtoken` are now in _Title Case_.
    - Corrected the schema for `/v2/dailycrafting`.
    - Corrected the schema for `/v2/emotes`.
    - Corrected the schema for `/v2/mapchests`.
    - Corrected the schema for `/v2/maps`.
    - Corrected the schema for `/v2/masteries`.
    - Corrected the schema for `/v2/recipes`.
    - Corrected the schema for `/v2/wvw/objectives`.
    - Corrected the schema for `/v2/wvw/ranks`.
    - Corrected the schema for `/v2/wvw/upgrades`.
- Changes of conditionals in schema versions are now properly computed.


---

### 0.2.1

_Released 2020 Dec 23_

#### Fixes

- Fixed a bug in parameter validation that made `0.2.0` unusable.


---

### 0.2.0

_Released 2020 Dec 23_

#### Improvements

- Endpoints:
    - Added support for `/v2/account/bank`. [[GH-3](https://github.com/GW2ToolBelt/api-generator/issues/3)]
    - Added support for `/v2/continents`. [[GH-70](https://github.com/GW2ToolBelt/api-generator/issues/70)]
    - Added support for `/v2/emblem`. [[GH-76](https://github.com/GW2ToolBelt/api-generator/issues/76)]
    - Added support for `/v2/emblem/:type`. [[GH-76](https://github.com/GW2ToolBelt/api-generator/issues/76)]
    - Added support for `/v2/gliders`. [[GH-80](https://github.com/GW2ToolBelt/api-generator/issues/80)]
    - Added support for `/v2/guild/:id`. [[GH-81](https://github.com/GW2ToolBelt/api-generator/issues/81)]
    - Added support for `/v2/maps`. [[GH-101](https://github.com/GW2ToolBelt/api-generator/issues/101)]
    - Added support for `/v2/masteries`. [[GH-102](https://github.com/GW2ToolBelt/api-generator/issues/102)]
    - Added support for `/v2/materials`. [[GH-103](https://github.com/GW2ToolBelt/api-generator/issues/103)]
    - Added support for `/v2/recipes`. [[GH-126](https://github.com/GW2ToolBelt/api-generator/issues/126)]
- Added support for V2 schema `2020-11-17T00:30:00.000Z`.
- Path-parameters and query-parameters now have a `camelCaseName` property.

#### Fixes

- Endpoints:
    - `isLocalized` flag is now set for `/v2/items`.


---

### 0.1.0

_Released 2020 Sep 30_

#### Overview

A library for programmatically creating programs that interface with data exposed
by the official Guild Wars 2 API.

This library contains information about the structure and data-types of the API.
This information can be used to generate custom API clients or other programs
which require definitions of the API.