# api-generator

[![License](https://img.shields.io/badge/license-MIT-green.svg?style=for-the-badge&label=License)](https://github.com/GW2Toolbelt/api-generator/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.gw2tb.api-generator/api-generator.svg?style=for-the-badge&label=Maven%20Central)](https://maven-badges.herokuapp.com/maven-central/com.gw2tb.api-generator/api-generator)
[![Documentation](https://img.shields.io/maven-central/v/com.gw2tb.api-generator/api-generator.svg?style=for-the-badge&label=Documentation&color=blue)](https://gw2toolbelt.github.io/api-generator/)
![Kotlin](https://img.shields.io/badge/Kotlin-2%2E2-green.svg?style=for-the-badge&color=a97bff&logo=Kotlin)
![Java](https://img.shields.io/badge/Java-17-green.svg?style=for-the-badge&color=b07219&logo=Java)

A library for programmatically creating programs that interface with data exposed
by the official Guild Wars 2 API.

This library contains information about the structure and data-types of the API.
This information can be used to generate custom API clients or other programs
which require definitions of the API.

This library requires Java 8 or later. (Keep in mind that this library is
intended to be used for static code-generation only. This requirement is not
relevant for the generated output.)


## Usage

### Generating implementations

The `com.gw2tb.apigen.APIGenerator` class serves as entry-point for retrieving
information about the Guild Wars 2 API. The _schema_ API serves as high-level
abstraction carrying semantic information about how the Guild Wars 2 API data
should be interpreted.

It is recommended to generate one-to-one mappings between schema types mappings
and appropriate language or library constructs, as well as between queries and
functions.

For a better understanding of schema types and how to work with the library,
please refer to the [user guide](https://gw2toolbelt.github.io/api-generator/latest/userguide/introduction/).


## Building from source

### Setup

This project uses [Gradle's toolchain support](https://docs.gradle.org/current/userguide/toolchains.html)
to detect and select the JDKs required to run the build. Please refer to the
build scripts to find out which toolchains are requested.

An installed JDK 17 (or later) is required to use Gradle.

### Building

Once the setup is complete, invoke the respective Gradle tasks using the
following command on Unix/macOS:

    ./gradlew <tasks>

or the following command on Windows:

    gradlew <tasks>

Important Gradle tasks to remember are:
- `clean`                   - clean build results
- `build`                   - assemble and test the project
- `apiDump`                 - dump binary API to text file (when intentionally
                              changing the public API)
- `publishToMavenLocal`     - build and install all public artifacts to the
                              local maven repository

Additionally `tasks` may be used to print a list of all available tasks.


### Adding/modifying endpoints

From time to time, API endpoints are added or updated. These changes need to be
manually integrated into the [API definition](src/main/kotlin/com/gw2tb/apigen/internal/spec).
The API definition files use a custom Kotlin-based DSL to define endpoint and
queries. For information on the DSL and how the library functions, please refer
to the documentation of the [developer guide](docs/mkdocs/devguide).


## Implementations

| Project                                                        | Language                          |
|----------------------------------------------------------------|-----------------------------------|
| [GW2APIClient](https://github.com/GW2ToolBelt/GW2APIClient)    | Kotlin (Multiplatform)            |

Your project uses the api generator and is not listed here?
Feel free to open a pull request to have it added!


## License

#### api-generator

```
Copyright (c) 2019-2026 Leon Linhart

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

> © ArenaNet LLC. All rights reserved. NCSOFT, ArenaNet, Guild Wars, Guild
> Wars 2, GW2, Guild Wars 2: Heart of Thorns, Guild Wars 2: Path of Fire, Guild
> Wars 2: End of Dragons, and Guild Wars 2: Secrets of the Obscure and all
> associated logos, designs, and composite marks are trademarks or registered
> trademarks of NCSOFT Corporation.

As taken from [Guild Wars 2 Content Terms of Use](https://www.guildwars2.com/en/legal/guild-wars-2-content-terms-of-use/)
on 2024-01-23 00:57 CET.