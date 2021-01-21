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