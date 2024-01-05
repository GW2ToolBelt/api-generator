# The Guild Wars 2 API

ArenaNet provides an official read-only API for Guild Wars 2 that can be used to
query information about the game. There are multiple versions of the API that
are currently supported:

- [Version 1](v1.md) which is the originally available version, and
- [Version 2](v2.md) which is the currently maintained version.

The concepts explained in this document are consistent between both versions of
the API. Visit the documentation for the individual versions for
version-specific information.


### Localization

The Guild Wars 2 API offers support for localized responses. The supported
languages differ between API versions. (See the table below for a full comparison.)

| Language     | API v1 | API v2 |
|--------------|--------|--------|
| Chinese (zh) |    -   |    ✔   |
| English (en) |    ✔   |    ✔   |
| French  (fr) |    ✔   |    ✔   |
| German  (de) |    ✔   |    ✔   |
| Spanish (es) |    ✔   |    ✔   |

A specific language can be requested by setting the `lang` query parameter to
the language code.

    $ curl https://api.guildwars2.com/v2/items?id=43766&lang=en
    {
      "name": "Tome of Knowledge",
      "description": "Grants one character level if below level 80 or one spirit shard if level 80.\nOnly usable in PvE and WvW.",
      "type": "Consumable",
      "level": 0,
      "rarity": "Exotic"
      // additional fields were excluded from this example
    }

    $ curl https://api.guildwars2.com/v2/items?id=43766&lang=de
    {
      "name": "Foliant des Wissens",
      "description": "Lässt Euren Charakter eine Stufe aufsteigen, wenn er Stufe 80 noch nicht erreicht hat, oder gewährt eine Geister-Scherbe, wenn er auf Stufe 80 ist.\nNur im PvE und WvW verwendbar.",
      "type": "Consumable",
      "level": 0,
      "rarity": "Exotic"
      // additional fields were excluded from this example
    }

!!! note

    The API makes some effort to guess the language based on your IP
    geolocation. Hence, it is recommended to always request a language
    explicitly for consistent results.


## Rate Limiting

The Guild Wars 2 API implements basic [rate limiting](https://en.wikipedia.org/wiki/Rate_limiting).
The rate limit is dependent on the request's IP address and applies to all
endpoints whether the endpoint is authenticated or not.

The limit is implemented using a refilling [token bucket](https://en.wikipedia.org/wiki/Token_bucket).
The bucket can hold a maximum of 300 tokens and is fully replenished after one
minute of being depleted.

When the limit is reached, subsequent requests will yield a status `429` response.