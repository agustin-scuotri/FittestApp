# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FittestApp is a multiplatform fitness application for gym management, designed for use by nutritionists and trainers. It is built with **Spring Boot 3.5.13 + Vaadin 24.9.13**, running on **Java 21**, with a **PostgreSQL** database.

The only module is `fittestapp-api/` — all backend and UI code lives there.

## Common Commands

All commands run from the `fittestapp-api/` directory using the Maven wrapper.

```bash
# Run the application (dev mode)
./mvnw spring-boot:run

# Build (skip tests)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=MyTestClass

# Production build (compiles Vaadin frontend)
./mvnw -Pproduction clean package
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

## Architecture

- **Spring Boot** handles the REST layer and dependency injection.
- **Vaadin** provides the server-side UI framework — UI components are written in Java, not a separate frontend language.
- **Spring Data JPA / Hibernate** manages persistence. DDL strategy is `update` (schema auto-migrates on startup).
- **Spring Security** handles authentication and authorization.
- **Lombok** is used throughout to reduce boilerplate (`@Data`, `@Builder`, etc.).

Entry point: `src/main/java/com/fittestapp/FittestAppApplication.java`

## Database

- **PostgreSQL** on `localhost:5432`, database `fittestapp`
- Credentials are currently in `src/main/resources/application.properties` (dev only)
- SQL logging is enabled (`spring.jpa.show-sql=true`) — disable for production
- Hibernate DDL auto is `update` — entities drive schema changes

## Key Dependencies

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-web` | REST API |
| `spring-boot-starter-data-jpa` | ORM / repositories |
| `spring-boot-starter-security` | Auth |
| `spring-boot-starter-validation` | Bean validation |
| `vaadin-spring-boot-starter` | Server-side UI |
| `postgresql` | JDBC driver |
| `lombok` | Boilerplate reduction |
