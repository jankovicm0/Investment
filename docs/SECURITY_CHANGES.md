# First-pass security improvements

These changes harden an **educational** Spring Boot application. They are not a full security audit and do not make the API production-ready.

## Changes included

- The JWT signing secret is now read from `JWT_SECRET`, rather than hard-coded in Java.
- Registration now accepts `UserRegistrationDto` rather than persistence entity `User`, preventing request-controlled IDs or balances from being bound during registration.
- Registration username/password fields are validated; password must be 8–128 characters.
- The `User.password` property is write-only for JSON serialization, preventing password hashes from appearing in serialized user entities.
- Basic unit tests check JSON handling and registration DTO validation.

## Setup changes

The app now **requires** the `JWT_SECRET` environment variable. Example for a local UNIX-like terminal:

```bash
export JWT_SECRET="$(python3 -c 'import secrets; print(secrets.token_hex(32))')"
./mvnw spring-boot:run
```

On Windows, set the `JWT_SECRET` environment variable in your shell before running `mvnw.cmd`. Use a **different, cryptographically random signing secret per environment**. Do not commit it. Existing JWTs signed with the old key will no longer be valid.

You still need a locally configured MySQL instance, as documented in the existing project README or the documentation pull request.

## Security issues not fixed here

- A secret previously committed to Git remains in **Git history**. Do not reuse it. If the key has ever been used beyond local demos, rotate it, revoke related tokens, and plan history cleanup separately.
- API endpoints for adding stocks, trading, and retrieving holdings still need **resource-level ownership/role authorization**.
- The code still accepts JPA entities for other requests and responses; dedicated API DTOs and field-level response controls remain to be built.
- Balance and stock-purchase operations require correctness, value validation, and concurrency improvements.
- The app needs database integration tests, real CI, and dependency/security upgrades.

**Do not deploy publicly or use real customers, money or personal data.**

## Testing status

A few isolated unit tests have been added, but **no local Maven build or test run has been performed through this GitHub connection**. Review and run `./mvnw test` with a suitable local test database/configuration before merging. The pre-existing context-load test may require additional database setup.