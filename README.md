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

🏗️ Project Setup
1️⃣ Clone the Repository
git clone https://github.com/your-username/sandbox-backend.git
cd sandbox-backend

2️⃣ Configure Database

Update application.properties with your MySQL configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/sandbox
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3️⃣ Run the Application
mvn spring-boot:run


The backend will start on:
👉 http://localhost:8080

🔒 Authentication Flow

User registers (/api/register)

User logs in (/api/auth/login) → receives JWT

Pass JWT in Authorization header:

Authorization: Bearer <your-token>

👥 Roles

STARTUP → Can create and manage their own startup profile.

INVESTOR → Can browse and connect with startups.

📂 Folder Structure
src/main/java/com/sandbox
 ├── config/         # Security, JWT, CORS configs
 ├── controller/     # REST API endpoints
 ├── dto/            # Data Transfer Objects
 ├── exception/      # Global exception handling
 ├── model/          # JPA entities
 ├── repository/     # Data access layer
 ├── service/        # Business logic layer

✅ Future Enhancements

💬 Investor-Startup messaging system

📊 Dashboard with funding statistics

🔍 Advanced filtering & search for startups

🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss.
