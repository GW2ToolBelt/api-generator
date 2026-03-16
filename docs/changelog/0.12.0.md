### 0.12.0

_Released 2026 Mar 16_

#### Fixes

- Endpoints:
  - `/v2/achievements`:
    - `Item` rewards are now correctly named (previously `Items`) [[GH-550](https://github.com/GW2ToolBelt/api-generator/issues/550)]
    - `MiniPet` bits now use correct capitalization for disambiguation (`Minipet`).
  - `/v2/gliders`:
    - Made `unlock_items` optional to support old legendary gliders. [[GW2-151](https://github.com/gw2-api/issues/issues/151)]
  - `/v2/skills`:
    - `dual_wield` is now correctly specified as string again.
    - `icon` is now optional.
    - Added support for `Duration` facts.
    - Added basic support for `HealingAdjust` facts. The semantics of these facts are unclear as they appear to be
      incomplete.
  - `/v2/skins`:
    - Added support for skins without dye slots.
