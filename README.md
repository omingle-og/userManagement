<div align="center">

# 🛡️ User Management System

### A production-ready REST API for secure user authentication and management

Built with **Spring Boot 3** · **MySQL** · **JWT** · **Hibernate/JPA** · **Spring Security**

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](http://localhost:8080/swagger-ui/index.html)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 📖 Overview

The **User Management System** is a stateless, secure backend REST API designed to handle user registration, JWT-based authentication, and full CRUD user management with **role-based access control (RBAC)**. It is built with Spring Boot 3 following a clean layered architecture and is fully documented with an interactive Swagger UI.

---

## ✨ Features

| Feature | Details |
|:---|:---|
| 🔐 **JWT Authentication** | Stateless, token-based authentication with 24-hour expiry |
| 👤 **User Registration** | Register users with unique username & email validation |
| 🔑 **BCrypt Password Hashing** | Passwords are never stored in plain text |
| 🛡️ **Role-Based Access Control** | `USER` and `ADMIN` roles with method-level enforcement |
| 📋 **User Management (CRUD)** | Admin-only endpoints to list, update roles, and delete users |
| 📚 **Swagger UI** | Interactive API documentation at `/swagger-ui/index.html` |
| ⚙️ **Environment Variables** | `.env`-based configuration for secure secret management |
| 🗄️ **Auto Schema Management** | Hibernate auto-creates and updates database tables |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   HTTP Client / Swagger UI              │
└─────────────────────────┬───────────────────────────────┘
                          │
                    ┌─────▼─────┐
                    │ JwtFilter  │  ← Validates JWT on every request
                    └─────┬─────┘
                          │
                  ┌───────▼────────┐
                  │ Controller Layer│  ← Routes & request mapping
                  └───────┬────────┘
                          │
                   ┌──────▼──────┐
                   │Service Layer │  ← Business logic & role checks
                   └──────┬──────┘
                          │
               ┌──────────▼──────────┐
               │ Repository (JPA)    │  ← Spring Data queries
               └──────────┬──────────┘
                          │
                   ┌──────▼──────┐
                   │   MySQL DB  │  ← `management` schema
                   └─────────────┘
```

---

## 📁 Project Structure

```
user-management/
├── src/main/java/com/example/usermanagement/
│   ├── controller/
│   │   ├── UserController.java            # POST /api/auth/register & /login
│   │   ├── UserManagementController.java  # GET/PUT/DELETE /api/users/**
│   │   └── DemoController.java            # GET /api/demo/user & /admin
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── UserResponse.java              # Outbound safe user data (no password)
│   │   └── RoleUpdateRequest.java
│   ├── entity/
│   │   ├── User.java                      # JPA Entity + UserDetails implementation
│   │   └── Role.java                      # Enum: USER | ADMIN
│   ├── repository/
│   │   └── UserRepository.java
│   ├── security/
│   │   ├── JwtUtil.java                   # Token generation & validation
│   │   ├── JwtFilter.java                 # Per-request JWT servlet filter
│   │   ├── SecurityConfig.java            # HTTP security rules + @EnableMethodSecurity
│   │   ├── ApplicationConfig.java         # Auth beans (prevents circular dependency)
│   │   └── OpenApiConfig.java             # Swagger UI JWT authorization config
│   ├── service/
│   │   └── UserService.java               # All business logic
│   └── UserManagementApplication.java
├── src/main/resources/
│   └── application.properties
├── .env                                   # Local secrets (gitignored)
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- ☕ **Java 17** or higher
- 🗄️ **MySQL Server** running on `localhost:3306`

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd user-management
```

### 2. Create the Database

Connect to your MySQL server and run:

```sql
CREATE DATABASE IF NOT EXISTS management;
```

> Hibernate will automatically create the `users` table on first startup.

### 3. Configure Environment Variables

Create a `.env` file in the project root:

```env
DB_URL=jdbc:mysql://localhost:3306/management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
DB_USERNAME=root
DB_PASSWORD=root123
JWT_SECRET=4f7b60e9d4a8f9c2a5d8b7c6e4f3a2b1c0d9e8f7a6b5c4d3e2f1a0b9c8d7e6f5
JWT_EXPIRATION=86400000
```

> 💡 If no `.env` is provided, the app falls back to the default values in `application.properties`.

### 4. Run the Application

```bash
# Linux / macOS
./mvnw clean spring-boot:run

# Windows
mvnw.cmd clean spring-boot:run
```

The server will start on **`http://localhost:8080`** 🎉

---

## 📋 API Reference

### 🌐 Authentication — `/api/auth` *(Public — No token required)*

| Method | Endpoint | Description |
|:---|:---|:---|
| `POST` | `/api/auth/register` | Register a new user (assigned `USER` role by default) |
| `POST` | `/api/auth/login` | Authenticate and receive a JWT token |

**Register Request:**
```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login Response:**
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

---

### 👤 User Management — `/api/users` *(JWT Required)*

| Method | Endpoint | Required Role | Description |
|:---|:---|:---|:---|
| `GET` | `/api/users/me` | `USER` or `ADMIN` | Get the currently authenticated user's profile |
| `GET` | `/api/users` | `ADMIN` | Get a list of all registered users |
| `PUT` | `/api/users/{id}/role` | `ADMIN` | Promote or demote a user's role |
| `DELETE` | `/api/users/{id}` | `ADMIN` | Permanently delete a user account |

---

### 🔬 Demo Endpoints — `/api/demo` *(JWT Required)*

| Method | Endpoint | Required Role | Description |
|:---|:---|:---|:---|
| `GET` | `/api/demo/user` | `USER` or `ADMIN` | Access USER-level protected resource |
| `GET` | `/api/demo/admin` | `ADMIN` | Access ADMIN-only protected resource |

---

## 🔒 Security Model

- All passwords are hashed using **BCrypt** before being stored.
- Successful login issues a signed **JWT**, valid for **24 hours**.
- Every subsequent request must include the token in the `Authorization` header:
  ```
  Authorization: Bearer <your_token>
  ```
- The `JwtFilter` validates the token and loads the user's **latest role from the database** on every request — so role changes take effect **immediately** without requiring re-login.
- Endpoint access is enforced using Spring Security's `@PreAuthorize` annotations at the method level.

---

## 🗄️ Database Schema

```sql
CREATE TABLE users (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,        -- BCrypt hash
    role     ENUM('USER', 'ADMIN') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email)
) ENGINE=InnoDB;
```

---

## 📖 Interactive API Documentation

Once the server is running, open your browser and navigate to:

> ### 👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

1. Use `POST /api/auth/login` to get your token.
2. Click the **Authorize 🔒** button at the top right.
3. Enter your token in the format: `Bearer <your_token>`
4. All secured endpoints are now unlocked and testable.

---

## 🧪 Quick cURL Test Script

```bash
# 1. Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"pass123"}'

# 2. Login and capture token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"pass123"}' \
  | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

# 3. Get current user profile
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/users/me

# 4. [ADMIN] Get all users
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/users

# 5. [ADMIN] Update role of user with id=2
curl -X PUT http://localhost:8080/api/users/2/role \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"role":"ADMIN"}'

# 6. [ADMIN] Delete user with id=2
curl -X DELETE -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/users/2
```

> **Tip:** To make a user an ADMIN directly via MySQL:
> ```sql
> UPDATE users SET role = 'ADMIN' WHERE username = 'alice';
> ```

---

## ⚙️ Configuration Reference

| Property | Environment Variable | Default | Description |
|:---|:---|:---|:---|
| `spring.datasource.url` | `DB_URL` | `jdbc:mysql://localhost:3306/management` | JDBC connection URL |
| `spring.datasource.username` | `DB_USERNAME` | `root` | MySQL username |
| `spring.datasource.password` | `DB_PASSWORD` | `root123` | MySQL password |
| `jwt.secret` | `JWT_SECRET` | *(64-char hex key)* | HMAC-SHA signing key |
| `jwt.expiration` | `JWT_EXPIRATION` | `86400000` | Token TTL in milliseconds (24h) |

---

<div align="center">

Built with ❤️ using Spring Boot

</div>
