# Finance Tracking API (Backend)

A Spring Boot REST API for personal finance tracking — transactions, category budgets, and
dashboard analytics, secured with stateless JWT authentication.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-8B93A1)

---

## Table of contents

- [Overview](#overview)
- [Tech stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
- [Configuration](#configuration)
- [Authentication](#authentication)
- [API reference](#api-reference)
  - [Auth](#auth)
  - [Transactions](#transactions)
  - [Budgets](#budgets)
  - [Dashboard](#dashboard)
- [Data model](#data-model)
- [Error format](#error-format)
- [Project structure](#project-structure)
- [Known limitations](#known-limitations)
- [License](#license)

---

## Overview

This API lets a user register, log in, record income/expense transactions, set monthly budgets
per category, and retrieve dashboard-ready analytics (category breakdowns, monthly trends,
totals) — all scoped to the authenticated user.

A reference frontend for this API is available separately (React + Vite).

## Tech stack

| Layer | Choice |
|---|---|
| Language / runtime | Java 21 |
| Framework | Spring Boot 4.1 (Web MVC, Security, Data JPA) |
| Database | MySQL |
| Auth | JWT (`jjwt`), httpOnly cookie, stateless sessions |
| Build tool | Maven |

## Prerequisites

- JDK 21+
- MySQL 8+ (a running instance with a database created for this app)
- Maven (or use the bundled `./mvnw`)

## Getting started

1. **Create a database:**
   ```sql
   CREATE DATABASE finance_tracking;
   ```

2. **Configure `src/main/resources/application.properties`** (see [Configuration](#configuration)
   below — this file isn't committed, so you'll need to create it).

3. **Run the app:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The API starts on `http://localhost:8080` by default.

4. **Run tests:**
   ```bash
   ./mvnw test
   ```

## Configuration

Create `src/main/resources/application.properties` with at least:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracking
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# Base64-encoded HMAC-SHA key, e.g. generated with:
#   openssl rand -base64 32
app.jwt.secret=REPLACE_WITH_A_BASE64_SECRET
```

| Property | Required | Notes |
|---|---|---|
| `spring.datasource.url` | Yes | MySQL connection string |
| `spring.datasource.username` / `password` | Yes | MySQL credentials |
| `spring.jpa.hibernate.ddl-auto` | Recommended | `update` auto-creates/updates tables from the JPA entities during development |
| `app.jwt.secret` | Yes | Base64 string, decoded into an HMAC-SHA signing key for JWTs |

**Never commit real credentials or the JWT secret.** Add `application.properties` to
`.gitignore` and use environment-specific config (env vars, a secrets manager, or per-profile
properties files) in any shared or deployed environment.

## Authentication

- Auth is **stateless** — no server-side session, no CSRF protection needed (disabled).
- `POST /auth/login` issues a JWT and sets it as an **httpOnly cookie** named `jwt`. It is not
  returned in a JSON body for client-side storage.
- Every request after login must include that cookie; a servlet filter (`JwtFilter`) validates it
  and populates the Spring Security context.
- All `/auth/**` routes are public; every other route requires a valid `jwt` cookie.

## API reference

Base URL: `http://localhost:8080`

### Auth

#### `POST /auth/register`
Create a new user.

```json
// Request
{ "userName": "Jordan Rivera", "email": "jordan@example.com", "password": "••••••••" }
```
```json
// 200 OK
{ "userName": "Jordan Rivera", "email": "jordan@example.com", "createdAt": "2026-07-17T09:00:00" }
```
Returns `404` (via `UserNotFoundException`) if the email is already registered.

#### `POST /auth/login`
Authenticate and receive a session cookie.

```json
// Request
{ "email": "jordan@example.com", "password": "••••••••" }
```
Response is **plain text**, not JSON: `"Login Successful <jwt>"` on success, `"Login Faild"` on
failure. The actual session is the `jwt` httpOnly cookie set alongside this response.

---

### Transactions
All routes below require authentication and operate only on the current user's data.

#### `GET /transactions`
Paginated, filterable list.

| Query param | Type | Notes |
|---|---|---|
| `year`, `month` | int | Filter by transaction date |
| `category` | enum | See [Data model](#data-model) |
| `type` | enum | `INCOME` \| `EXPENSE` |
| `startDate`, `endDate` | `YYYY-MM-DD` | Inclusive range |
| `minAmount`, `maxAmount` | decimal | |
| `page` | int | Default `0` |
| `size` | int | Default `10` |

Returns a Spring `Page<TransactionResponse>`.

#### `POST /transactions`
```json
{
  "title": "Weekly groceries",
  "amount": 86.40,
  "type": "EXPENSE",
  "category": "FOOD",
  "description": "Trader Joe's run"
}
```
Returns the created `TransactionResponse` (`201`-equivalent — currently returns `200`).

#### `PUT /transactions/{id}`
Same body shape as create; updates title, amount, type, category, and description.

#### `DELETE /transactions/{id}`
Returns a plain-text confirmation string.

#### `POST /transactions/bulk`
Accepts a JSON array of the same create-body shape.

---

### Budgets
All routes below require authentication and operate only on the current user's data.

#### `GET /budgets`

| Query param | Type | Notes |
|---|---|---|
| `category` | enum | |
| `year`, `month` | int | |
| `page`, `size` | int | Defaults `0` / `10` |

Returns a Spring `Page<BudgetResponse>`.

#### `POST /budgets`
```json
{ "category": "FOOD", "monthlyLimit": 400.00, "month": 7, "year": 2026 }
```

#### `PUT /budgets/{id}`
Same body shape as create.

#### `DELETE /budgets/{id}`
Returns a plain-text confirmation string.

#### `POST /budgets/bulk`
Accepts a JSON array of the same create-body shape.

---

### Dashboard

#### `GET /dashboard?month={month}&year={year}`
Returns aggregated analytics for the given period:

```json
{
  "categoryExpenses": [{ "category": "FOOD", "amount": 240.10 }],
  "categoryBudgets": [{ "category": "FOOD", "monthlyLimit": 400.00 }],
  "monthlyTrends": [{ "month": "JULY", "income": 6240.00, "expense": 3812.50 }],
  "dashboardSummary": { "totalIncome": 6240.00, "totalExpense": 3812.50, "totalBudget": 4500.00 },
  "transactions": { "content": [ /* TransactionResponse[] */ ], "totalPages": 4, "...": "..." }
}
```

---

## Data model

**`Category`**: `SALARY`, `FREELANCE`, `FOOD`, `TRAVEL`, `SHOPPING`, `BILLS`, `ENTERTAINMENT`,
`HEALTH`, `EDUCATION`, `INVESTMENT`, `OTHER`

**`TransactionType`**: `INCOME`, `EXPENSE`

**Entities**: `User` (1—many → `Transaction`, `Budget`), `Transaction`, `Budget` — each
transaction/budget belongs to exactly one user via `user_id`.

## Error format

Errors are returned as JSON with a consistent shape:

```json
{ "status": "NOT_FOUND", "error": "Invalid Budget operation", "msg": "Budget not found" }
```

| Exception | Status |
|---|---|
| `UserNotFoundException` | Varies (e.g. `404`) |
| `BudgetNotFoundException` | Varies |
| `TransactionNotFoundException` | Varies |
| `InvalidField` | Varies |
| Malformed enum in request body | `400 BAD_REQUEST` |
| Uncaught exception | `502 BAD_GATEWAY` |

## Project structure

```
src/main/java/finance_management/
├── controller/       REST endpoints (Auth, Transaction, Budget, Dashboard, Test)
├── service/           business logic + JWT handling
├── repo/               Spring Data JPA repositories
├── model/              JPA entities (User, Transaction, Budget)
├── dto/                 request/response DTOs, grouped by resource
├── mapper/               entity <-> DTO mapping
├── enums/                 Category, TransactionType
├── specifications/         JPA Specifications powering dynamic filters
├── validations/             custom validation annotations
├── exceptions/                custom exceptions + global exception handler
├── security/                   Spring Security filter chain config
└── config/                     JwtFilter, UserDetailsService, PasswordEncoder
```

## Known limitations

Worth addressing before a production deployment:

- **No CORS configuration** — a frontend on a different origin will be blocked until a
  `WebMvcConfigurer` CORS bean is added (see the frontend README for a drop-in example).
- **No logout endpoint** — there's no server-side way to invalidate a cookie early; sessions
  simply expire.
- **Cookie `maxAge` is very short** — set to `60 * 24` (1,440 **seconds** ≈ 24 minutes) in
  `AuthService.verify()`. If a longer session is intended, this is likely meant to be
  `60 * 60 * 24` (24 hours).
- **No `/auth/me` endpoint** — clients must probe a protected route to check session validity.

## License

MIT
