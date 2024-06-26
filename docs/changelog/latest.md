### 0.7.0

_Released 2024 Jun 30_

#### Improvements

- Endpoints
    - Added support for `/v2/account/jadebots`. [[GH-259](https://github.com/GW2ToolBelt/api-generator/issues/259)]
    - Added support for `/v2/account/skiffs`. [[GH-261](https://github.com/GW2ToolBelt/api-generator/issues/261)]
    - Added support for `/v2/account/wizardsvault/daily`. [[GH-297](https://github.com/GW2ToolBelt/api-generator/issues/297)]
    - Added support for `/v2/account/wizardsvault/listings`. [[GH-298](https://github.com/GW2ToolBelt/api-generator/issues/298)]
    - Added support for `/v2/account/wizardsvault/special`. [[GH-299](https://github.com/GW2ToolBelt/api-generator/issues/299)]
    - Added support for `/v2/account/wizardsvault/weekly`. [[GH-300](https://github.com/GW2ToolBelt/api-generator/issues/300)]
    - Added support for `/v2/jadebots`. [[GH-258](https://github.com/GW2ToolBelt/api-generator/issues/258)]
    - Added support for `/v2/skiffs`. [[GH-260](https://github.com/GW2ToolBelt/api-generator/issues/260)]
    - Added support for `/v2/wizardsvault`. [[GH-296](https://github.com/GW2ToolBelt/api-generator/issues/296)]
    - Added support for `/v2/wizardsvault/listings`. [[GH-294](https://github.com/GW2ToolBelt/api-generator/issues/294)]
    - Added support for `/v2/wizardsvault/objectives`. [[GH-295](https://github.com/GW2ToolBelt/api-generator/issues/295)]
- Added a `SchemaBitfield` type to be used for bitfields. This type should
  always be mapped to 64bit integers.
- Added support for V2 schema `2022-03-23T19:00:00.000Z`.
- Introduced a low-level API to move the version information out of the schema
  API into an intermediate representation (IR).
    - APIs can now be generated for specific schema versions making it
      significantly easier for consumers to work with.
- Introduced a `Name` abstraction to make case conversion for names an explicit
  operation.
- Introduced the concept of inlined properties for reference types.
    - Inlined properties can be used to group elements into logical units without
      affecting the serial representation.
- Introduced _enums_. Enums allow defining a known set of values for an element. [[GH-152](https://github.com/GW2ToolBelt/api-generator/issues/152)]
- Introduced _tuples_. Tuples are arrays with a fixed size where each element
  may carry different semantic information. [[GH-189](https://github.com/GW2ToolBelt/api-generator/issues/189)]

#### Fixes

- Endpoints:
    - `/v2/account`:
        - Fixed the optionality of `build_storage_slots` for tokens without `BUILDS`
          scope.
    - `/v2/characters`:
        - Made `amulet` and `runes` optional.
- Changed the type of `worldID` in the `MumbleLinkIdentity` to `BITFIELD`.

#### Breaking Changes

- The library now requires Kotlin 1.8.