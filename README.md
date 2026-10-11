# Investment — Stock Trading API

> Educational Spring Boot project demonstrating user accounts, JWT-based login and stock purchase workflows. This is a learning project, **not a production trading or banking system**.

## Overview

Investment is a small Java backend application with REST endpoints for:

- Registering users and logging in with a JWT
- Adding stock records
- Recording a stock purchase and debiting a user's balance
- Saving and retrieving a user's stock holdings through a separate endpoint
- Serving a simple static web interface from `src/main/resources/static/`

The application was originally built as a university project. This repository preserves that starting point while documenting areas for further improvement.

## Technology stack

| Area | Technology |
| --- | --- |
| Language | Java 11 |
| Framework | Spring Boot 2.7.3, Spring MVC |
| Security | Spring Security, JWT, BCrypt |
| Data | Spring Data JPA, MySQL |
| Build | Maven / Maven Wrapper |
| UI | HTML, CSS, JavaScript (basic static page) |
| Testing | JUnit 5 / Spring Boot Test (basic context-load test) |

## Project structure

```text
src/
├── main/
│   ├── java/com/example/investment/
│   │   ├── controller/  # REST endpoints
│   │   ├── dto/         # Request-related DTOs
│   │   ├── model/       # JPA entities
│   │   ├── repository/  # Database access
│   │   ├── security/    # Authentication and JWT helpers
│   │   └── service/     # Business logic
│   └── resources/
│       ├── application.properties
│       └── static/     # Basic web assets
└── test/               # Spring Boot context test
```

## Local setup

**Requirements:** JDK 11, MySQL and a Java-compatible build environment. Maven Wrapper scripts (`mvnw`, `mvnw.cmd`) are included.

1. Clone the repository.
2. Create a local MySQL database.
3. Configure your own `spring.datasource.url`, `spring.datasource.username` and `spring.datasource.password` for local development. Spring Boot also supports environment overrides such as `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD`; avoid committing real credentials.
4. Run the application:

```bash
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run`. The app is configured to start on the default Spring Boot HTTP port (typically `8080`). Database setup and credentials must be valid for successful startup.

**Security warning:** the current learning-project code contains hard-coded JWT signing values and other security limitations. **Do not deploy it publicly or use real user data** until these issues are addressed. See [Improvement Plan](docs/IMPROVEMENT_PLAN.md).

## API endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| `POST` | `/api/users/register` | Register a user |
| `POST` | `/api/users/login` | Authenticate and receive a JWT |
| `POST` | `/api/stocks/add` | Create a stock |
| `POST` | `/api/stocks/buy` | Create a purchase transaction and debit a balance |
| `POST` | `/api/user-stocks/buy` | Add a stock holding for a user |
| `GET` | `/api/user-stocks/{username}` | List the specified user's holdings |

Registration and login are public endpoints; the other routes require authentication under the current Spring Security configuration. **Authenticated access does not currently imply correct resource-level authorization.**

### Example: register

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo-user","password":"demo-only-password"}'
```

### Example: login

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo-user","password":"demo-only-password"}'
```

The login response contains a JWT that clients send in an `Authorization: Bearer <token>` header to protected endpoints. For a legacy request/response example, see [`README.txt`](README.txt); verify responses against the current code rather than treating its samples as a formal API contract.

## Current limitations and next steps

This repository is a learning project, and several aspects need improvement before production-style use:

- Move signing secrets and configuration out of committed source and rotate any real exposed secrets.
- Use dedicated response DTOs rather than returning persistence entities, to prevent leaking sensitive fields.
- Verify that users can only access and modify resources they are authorized to own.
- Consolidate purchase logic; the two purchase routes do not enforce the same business rules.
- Use `BigDecimal` for monetary amounts and validate quantities, prices and account balances.
- Add automated service, controller, and database integration tests.
- Introduce reproducible local development tooling and CI after tests are in place.
- Upgrade the Java / Spring Boot stack and replace deprecated security configuration.

More details are in [docs/IMPROVEMENT_PLAN.md](docs/IMPROVEMENT_PLAN.md).

## Project status

**Academic / work in progress.** Features above are based on the repository's current code, not claims of production readiness. No publicly deployed demo or verified CI pipeline is claimed here.
