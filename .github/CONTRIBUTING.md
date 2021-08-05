# Contributing

Thank you for considering contributing to api-generator!

We welcome any type of contribution, regardless of whether it's code,
documentation, or a bug report.

Any contribution must be submitted as a pull request. (If you wish to report an
issue or ask a question please use the [issue tracker](https://github.com/GW2ToolBelt/api-generator/issues)
or [discussions](https://github.com/GW2ToolBelt/api-generator/discussions) instead.)


## General guidelines

The bigger the pull request, the longer it will take to review and merge. Try to
break down pull requests in smaller chunks that are logical self-contained
changes.

This project does not strictly follow any public formatting guidelines we could
link here, but we ask you to stick to the formatting used when possible.


## Contributing code

This project defines a custom DSL that is used for the endpoint definitions.
Please familiarize yourself with it by reading the user and developer guidelines
before contributing.


### Contributing endpoints

Endpoint definitions can be found in `/src/main/kotlin/com/gw2tb/apigen/internal/spec`.
When making changes to existing definitions or adding a new endpoint, a few
other places need to be updated:

- The queries need to be added to `src/test/kotlin/com/gw2tb/apigen/test/spec`
- A JSON file with test vectors should be added to `src/main/resources/com/gw2tb/apigen`


## Contributing documentation

If you are contributing documentation on how to use this project, please
describe why you think these improvements are necessary.