# api-generator
[![License](https://img.shields.io/badge/license-MIT-green.svg?style=flat-square&label=License)](https://github.com/GW2Toolbelt/api-generator/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.gw2tb.api-generator/api-generator.svg?style=flat-square&label=Maven%20Central)](https://maven-badges.herokuapp.com/maven-central/com.gw2tb.api-generator/api-generator)
[![Documentation](https://img.shields.io/maven-central/v/com.gw2tb.api-generator/api-generator.svg?style=flat-square&label=Documentation&color=blue)](https://gw2toolbelt.github.io/api-generator/)
![Kotlin](https://img.shields.io/badge/Kotlin-1%2E4-green.svg?style=flat-square&color=a97bff&logo=Kotlin)
![Java](https://img.shields.io/badge/Java-8-green.svg?style=flat-square&color=b07219&logo=Java)

A library for programmatically creating programs that interface with data exposed
by the official Guild Wars 2 API.

This library contains information about the structure and data-types of the API.
This information can be used to generate custom API clients or other programs
which require definitions of the API.

This library requires Java 8 or later. (Keep in mind that this library is
intended to be used for static code-generation only. This requirement is not
relevant for the generated output.)


## The Guild Wars 2 API

### Authentication

Some API endpoints provide account-specific data. To query these endpoints, an
_API key_ must be passed to authenticate. Further, each API key has a custom set
of permissions which determine what information can be retrieved using the key.

An API key may be passed along a request by using the `access_token` query
parameter, or using the `Authorization: Bearer ${API Key}` request header
(recommended).

E.g. `https://api.guildwars2.com/v2/account/inventory?access_token=${API Key}`
returns the shared inventory of the key's owner iff the key has the `ACCOUNT`
and `INVENTORY` permissions.


### Localization

The Guild Wars 2 API supports localization (but the actual supported languages
differ based on the API version).

| Language     | API v1 | API v2 |
|--------------|--------|--------|
| Chinese (zh) |    -   |    ✔   |
| English (en) |    ✔   |    ✔   |
| French  (fr) |    ✔   |    ✔   |
| German  (de) |    ✔   |    ✔   |
| Spanish (es) |    ✔   |    ✔   |

Localized data can be accessed by setting the `lang` query parameter to the
language code. (The API makes some effort to guess the language based on your
IP geolocation. Hence, it is recommended to always request a language explicitly
for consistent results.)

E.g. use `https://api.guildwars2.com/v2/items?id=43766&lang=en` to request
english information:

    {
      "name": "Tome of Knowledge",
      "description": "Grants one character level if below level 80 or one spirit shard if level 80.\nOnly usable in PvE and WvW.",
      "type": "Consumable",
      "level": 0,
      "rarity": "Exotic"
      // additional fields were excluded from this example
    }

and `https://api.guildwars2.com/v2/items?id=43766&lang=de` to request
german information:

    {
      "name": "Foliant des Wissens",
      "description": "Lässt Euren Charakter eine Stufe aufsteigen, wenn er Stufe 80 noch nicht erreicht hat, oder gewährt eine Geister-Scherbe, wenn er auf Stufe 80 ist.\nNur im PvE und WvW verwendbar.",
      "type": "Consumable",
      "level": 0,
      "rarity": "Exotic"
      // additional fields were excluded from this example
    }


### Query Types

Generally, API endpoints are structured in two different ways: There are 1. "blob"-style endpoints which return just
return all relevant data (e.g. `/v2/build`) and 2. _indexed_ endpoints which return indices for many datums (e.g.
`v2/items`).

Indexed endpoints provide a couple of different parameters to retrieve the data for a given set of IDs. These are
referred to as `QueryTypes` throughout documentation and source of this project. Possible query types are:

- querying IDs (e.g. `/v2/items`),
- querying by ID (e.g. `/v2/items?id={id}`),
- querying by IDs (e.g. `/v2/items?ids={ids; ","-separated}`), and
- querying by page (e.g. `/v2/items?page={index}&page_size={size}`).

##### Querying by IDs
Some endpoints support a specialized version of this query type that can be used to retrieve all datums at once (e.g.
`/v2/legends?ids=all`).

##### Querying by Page
When querying by page, the response contains additional header elements:

- `x-page-size` which is the requested page size,
- `x-page-total` which is the total amount of pages for the given size, and
- `x-result-count` which is the number of results on the returned page.


## Usage

### Generating implementations

The library defines several entry-points for retrieving all the information
necessary to generate implementations for each API version:

- `com.gw2tb.apigen.APIVersion#getV1(Set<APIv1Endpoint>)`,
- `com.gw2tb.apigen.APIVersion#getV2(Set<APIv2Endpoint>)`

and the data exposed by Guild Wars 2 via MumbleLink:

- `com.gw2tb.apigen.MumbleLink.MUMBLELINK_IDENTITY_DEFINITION`.

These entry-points expose the types and queries supported by the API. Types are
defined as "schema types" - an abstraction that contains necessary information
about the structure of the APIs data-types. It is recommended to generate a
one-to-one  mapping between schema mappings and appropriate language or library
constructs.

For a better understanding of schema types and how to work with them, please
refer to the [user guide](docs/mkdocs/userguide).


## Building from source

### Setup

This project uses [Gradle's toolchain support](https://docs.gradle.org/7.4/userguide/toolchains.html)
to detect and select the JDKs required to run the build. Please refer to the
build scripts to find out which toolchains are requested.

An installed JDK 1.8 (or later) is required to use Gradle.

### Building

Once the setup is complete, invoke the respective Gradle tasks using the
following command on Unix/macOS:

    ./gradlew <tasks>

or the following command on Windows:

    gradlew <tasks>

Important Gradle tasks to remember are:
- `clean`                   - clean build results
- `build`                   - assemble and test the Java library
- `publishToMavenLocal`     - build and install all public artifacts to the
                              local maven repository

Additionally `tasks` may be used to print a list of all available tasks.


### Adding/modifying endpoints

From time to time endpoints are added or updated. These changes need to be
manually integrated into the [API definition](src/main/kotlin/com/gw2tb/apigen/internal/spec).
The API definition files use a custom DSL for defining endpoint that attempts to
be as concise as possible while also providing the additional benefits of type-safety.

Adding a new endpoint is almost as simple as adding a few lines of Kotlin code
to the appropriate file. For example, the `/v2/build` endpoint looks as follows:

```
val BUILD_ID = "BuildID"(INTEGER)

"/Build"(summary = "Returns the current build ID.") {
    schema(record(name = "Build", description = "Information about the current game build.") {
        CamelCase("id").."ID"(BUILD_ID, "the current build ID")
    })
}
```

Each endpoint definition must contain a `summary` describing what the endpoint
does, and a `schema` which defines the shape of the returned data.

For further information on which functions to use in what situations, please
refer to the documentation of the [developer guide](docs/mkdocs/devguide).


## Implementations

| Project                                                        | Language                          |
|----------------------------------------------------------------|-----------------------------------|
| [GW2APIClient](https://github.com/GW2ToolBelt/GW2APIClient)    | Kotlin (Multiplatform)            |

Your project uses the api generator and is not listed here?
Feel free to open a pull request to have it added!


## License

#### api-generator

```
Copyright (c) 2019-2022 Leon Linhart

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

--------------------------------------------------------------------------------

#### Guild Wars 2

> ©2010–2018 ArenaNet, LLC. All rights reserved. Guild Wars, Guild Wars 2, Heart
of Thorns, Guild Wars 2: Path of Fire, ArenaNet, NCSOFT, the Interlocking NC
Logo, and all associated logos and designs are trademarks or registered
trademarks of NCSOFT Corporation. All other trademarks are the property of their
respective owners.

As taken from [Guild Wars 2 Content Terms of Use](https://www.guildwars2.com/en/legal/guild-wars-2-content-terms-of-use/)
on 2019-09-09 17:31 CET.