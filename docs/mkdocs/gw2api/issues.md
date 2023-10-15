# API Issues

Unfortunately, ArenaNet does not allocate many resources to API development. As
the game evolved, API bugs were introduced and inconsistencies surfaced.
api-generator does not generally take bugged output into account and instead
assumes well-formedness in most cases. Thus, it is recommended to provide
fallbacks for partially parsing responses when possible.

!!! note

    [GW2APIClient](https://github.com/GW2ToolBelt/GW2APIClient) implements a
    fallback mechanism that can be used to decode partial responses for some
    endpoints that return lists or maps of elements, and unknown enum values.

A community-driven issue tracker is available at the [gw2api-issues](https://github.com/gw2-api/issues)
GitHub repository. This is a replacement for the unmaintained [official issue tracker](https://github.com/arenanet/api-cdi)
and should be used to report bugs.