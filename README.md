# SANDBOX - Startup Funding Platform Backend

This is the backend for SANDBOX, a full-stack web application designed to connect innovative startups with potential investors. The platform is built with a modern, secure, and scalable Java Spring Boot architecture.

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security** (JWT Authentication & Authorization)
- **Spring Data JPA** / **Hibernate**
- **MySQL**
- **Maven**
- **Lombok**

## Core Features

- Secure user registration with password hashing (BCrypt).
- Stateless JWT-based authentication and authorization for the REST API.
- Role-based access control (RBAC) for `STARTUP` and `INVESTOR` roles.
- RESTful API for managing users and startup profiles.
- Professional global exception handling for clear and consistent error responses.

## API Endpoints

A full collection of API requests is available for testing in Postman.

| Method | Endpoint                  | Description                                | Authorization Required |
| ------ | ------------------------- | ------------------------------------------ | ---------------------- |
| `POST` | `/api/register`           | Registers a new user (STARTUP or INVESTOR).| Public                 |
| `POST` | `/api/auth/login`         | Authenticates a user and returns a JWT.    | Public                 |
| `GET`  | `/api/startups`           | Retrieves a list of all startup profiles.  | Any Authenticated User |
| `POST` | `/api/startups/profile`   | Creates a new profile for a startup user.  | `ROLE_STARTUP`         |

---
