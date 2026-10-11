# Investment: Engineering Improvement Plan

This checklist is a forward-looking plan, **not** a list of completed features. It prioritizes correctness and safety before visual polish.

## P0 — Before any deployment

- [ ] Move JWT signing keys out of committed source. Check both `security/JwtUtil.java` and `util/JwtTokenUtil.java`. Rotate any keys used beyond throwaway local testing.
- [ ] Verify no credential or token is exposed in current files or Git history; replace real credentials and consider history cleanup only with an appropriate plan.
- [ ] Stop exposing JPA `User` directly in responses: it contains a `password` field (hashed passwords must remain private). Add dedicated request/response DTOs.
- [ ] Implement ownership/authorization checks: `/api/user-stocks/{username}` and purchase endpoints currently depend on client-supplied usernames or user objects.
- [ ] Validate quantities and prices to prevent zero/negative purchases, invalid state changes and unexpected balances.
- [ ] Unify `/api/stocks/buy` and `/api/user-stocks/buy` behind a consistent purchase service; currently one debits user balance while the other only creates a holding.

## P1 — Correctness and maintainability

- [ ] Replace `Double` with suitable monetary representations such as `BigDecimal`, with explicit scale/rounding rules.
- [ ] Test insufficient funds, missing user/stock, invalid quantities and unauthorized cross-user access.
- [ ] Add transaction-level consistency and concurrency tests, especially when two purchases race for the same funds.
- [ ] Avoid directly binding database entities as public API request bodies.
- [ ] Handle domain errors with appropriate HTTP status codes and structured responses.
- [ ] Add database migration scripts and sample seed data if needed for repeatable setup.

## P2 — Developer experience

- [ ] Upgrade from Java 11 / Spring Boot 2.7.3 to supported versions after verifying compatibility.
- [ ] Replace `WebSecurityConfigurerAdapter` with modern security configuration during the upgrade.
- [ ] Add OpenAPI specification and endpoint examples.
- [ ] Add GitHub Actions build-and-test pipeline **after** meaningful tests exist.
- [ ] Add reproducible development setup (such as Docker Compose) and screenshots/diagram if useful.

## What has actually been checked

- Source tree, Maven dependencies, existing legacy README and selected controllers/services/security/configuration.
- `src/test/java/.../InvestmentApplicationTests.java` contains only a basic `contextLoads` test.
- No automated test run or local deployment verification has been performed as part of this documentation change.

## Working style

Address these items in small, reviewed pull requests. Do not rewrite the original academic history or claim that planned work has already shipped.