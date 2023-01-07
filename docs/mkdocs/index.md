# Overview

**api-generator** is a library for programmatically creating programs that
interface with the official Guild Wars 2 API. It provides information about the
API's available endpoints and the format of the exposed data.

api-generator is intended to be used as source of information for static code
generation. It does not make any assumption about the generated output.

---

The `com.gw2tb.apigen.APIGenerator` class is the main entry-point of this
library and allows users to configure which data to include.

```kotlin
val apiGenerator = APIGenerator(
    v2SchemaVersion = SchemaVersion.V2_SCHEMA_CLASSIC
)

apiGenerator.v2Endpoints.forEach {
    println("V2 endpoint: ${it.endpointName}")
}
```

Head over to the [user guide](userguide/introduction.md) to get started with
api-generator and learn how the provided information is structured.

---

The data provided by api-generator is defined in a custom [Kotlin-based DSL](https://kotlinlang.org/docs/type-safe-builders.html).
This enables a concise and declarative definition while benefiting from Kotlin's
type-safety and IDE integration.

```kotlin
V2_BUILD(summary = "Returns the current build ID.") {
    schema(record(name = "Build", description = "Information about the current game build.") {
        CamelCase("id").."ID"(BUILD_ID, "the current build ID")
    })
}
```

To learn more about the internal workings of this project, have a look at the
[developer guide](devguide/introduction.md).


## Used By

This is a non-exhaustive list of projects using the api-generator. If you want
your project to be added, please file an issue or a pull request.

### GW2APIClient

The [GW2APIClient](https://github.com/GW2ToolBelt/GW2APIClient) provided by
GW2ToolBelt uses the api-generator to generate a Kotlin API including data-types
and queries for all API endpoints.