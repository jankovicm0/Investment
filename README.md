# Investment

A small stock-trading application I built with Java and Spring Boot for university. It covers user registration, JWT login, stock records, and basic purchase workflows backed by MySQL.

This is a learning project, not an application for real trading. I'm keeping the original work and improving it in smaller changes.

## Stack

Java 11 · Spring Boot 2.7 · Spring Security · Spring Data JPA · MySQL · Maven · HTML/CSS/JavaScript

## What's in the project

- User registration and JWT-based login
- Stock creation
- Purchase transactions that update a user's balance
- A separate route for saving and listing stock holdings
- A basic browser page for registration and login

The source is organised into controllers, services, repositories, JPA models and security classes.

## Run locally

You'll need **Java 11** and **MySQL**.

1. Create a local MySQL database.
2. Configure `spring.datasource.url`, `spring.datasource.username` and `spring.datasource.password` for your own local setup.
3. Set a local JWT signing key and start the app:

```bash
export JWT_SECRET="$(openssl rand -hex 32)"
bash ./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run`. The application normally starts on `http://localhost:8080`.

The `JWT_SECRET` environment variable is required. Keep it out of the repository, and use a different value for each environment.

**Don't deploy the current version publicly or use real financial data.** The original JWT keys remain visible in Git history, and resource-level authorization is still incomplete.

## API

| Method | Route | Purpose |
| --- | --- | --- |
| POST | `/api/users/register` | Register an account |
| POST | `/api/users/login` | Log in and receive a JWT |
| POST | `/api/stocks/add` | Add a stock |
| POST | `/api/stocks/buy` | Record a purchase transaction |
| POST | `/api/user-stocks/buy` | Save a stock holding |
| GET | `/api/user-stocks/{username}` | List a user's holdings |

The stock endpoints require a bearer token in the current security configuration. See the original [API notes](README.txt) for example requests.

## What I'm improving

The two purchase routes don't yet apply the same business rules. Authorization, money calculations and validation also need work before the project can be treated as a reliable backend. I started a [technical checklist](docs/IMPROVEMENT_PLAN.md) to track those changes.

The project currently has a basic Spring context test; broader service and integration tests are still needed.
