# Things to improve

This is where I'm tracking work on the Investment project.

## Done

- Moved the JWT signing value out of Java source. The app now reads `JWT_SECRET` from the environment.
- Switched registration to a validated request DTO.
- Prevented the stored password hash from being included in JSON responses.
- Added a few tests for registration validation and JSON serialization.
- Set up a Maven build and test workflow with a temporary MySQL database.

## Next

**Authorization.** The API still accepts usernames and user objects from callers in places where it should use the signed-in user's identity. Stock creation also needs role checks.

**Purchase logic.** There are two stock-purchase paths with different behaviour. They should use one service, validate quantity and price, and update balances/holdings together in one transaction.

**Money handling.** Use a fixed-precision representation such as `BigDecimal`, agree on rounding rules and test insufficient funds and concurrent purchases.

**API boundaries.** Add dedicated DTOs to the remaining endpoints, predictable error responses and tests for both allowed and forbidden requests.

**Tooling.** Add database migrations and reproducible seed data. Upgrade the Java/Spring dependencies and the older Spring Security configuration once behaviour is covered by tests.

## Before any public deployment

The old JWT values remain in Git history even though the code no longer uses them. Don't reuse those values; rotate any keys that were used beyond local testing.

The project is still an academic application. Build checks passing doesn't mean the authentication, authorization or trading logic is production-ready.
