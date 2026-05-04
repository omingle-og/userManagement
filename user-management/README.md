# 🛡️ User Management API

A robust, production-ready backend REST API for managing users, built with **Spring Boot 3**, **MySQL**, **Hibernate/JPA**, and secured with **JWT (JSON Web Tokens)**.

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-brightgreen.svg?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-orange.svg?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg?logo=mysql)
![JWT](https://img.shields.io/badge/Security-JWT-black.svg?logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/API_Docs-Swagger_UI-85EA2D.svg?logo=swagger)

---

## ✨ Features

- 🔐 **Secure Authentication**: Fully stateless backend using JWTs for authorization.
- 👤 **User Registration**: Create new users with unique usernames and emails.
- 🔑 **Password Hashing**: Secure password storage using BCrypt.
- 🛠️ **Environment Variables**: Configuration via `.env` files for security and flexibility.
- 📚 **Interactive API Documentation**: Embedded Swagger UI for easy endpoint testing right from your browser.
- 🛡️ **Validation**: Strict request payload validation using Jakarta Validation.

---

## 🏗️ Technology Stack

- **Core**: Java 17, Spring Boot 3.2.5
- **Data Access**: Spring Data JPA, Hibernate, MySQL Connector
- **Security**: Spring Security, jjwt (JSON Web Token)
- **Tooling**: Lombok (Boilerplate reduction), Maven Wrapper (`mvnw`)
- **Documentation**: Springdoc OpenAPI (Swagger UI)

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
Ensure you have the following installed:
- **Java 17** (or higher)
- **MySQL Server** (running on default port 3306)

### 2️⃣ Database Setup
The application requires a MySQL database named `management`. Log into your MySQL server and execute:
```sql
CREATE DATABASE IF NOT EXISTS management;
```
*(Note: Hibernate will automatically create the `users` table for you upon startup!)*

### 3️⃣ Environment Configuration
Create a `.env` file in the root directory (next to `pom.xml`) to securely configure your database and JWT secrets:
```env
DB_URL=jdbc:mysql://localhost:3306/management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
DB_USERNAME=root
DB_PASSWORD=root123
JWT_SECRET=4f7b60e9d4a8f9c2a5d8b7c6e4f3a2b1c0d9e8f7a6b5c4d3e2f1a0b9c8d7e6f5
JWT_EXPIRATION=86400000
```
*(If no `.env` file is provided, the application will safely fallback to the default values).*

### 4️⃣ Run the Application
You don't need Maven installed globally. Use the included Maven Wrapper to start the server!

**On Linux/Mac:**
```bash
./mvnw clean spring-boot:run
```
**On Windows:**
```cmd
mvnw.cmd clean spring-boot:run
```
The server will boot up on `http://localhost:8080`.

---

## 📖 API Documentation (Swagger UI)

The easiest way to test the API is using the built-in Swagger UI. Once the server is running, simply open your web browser and navigate to:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

From this dashboard, you can visually explore the available endpoints, construct JSON payloads, and execute requests with a single click.

---

## 💻 CLI / cURL Examples

If you prefer testing via terminal, here are the raw `cURL` commands:

### 📝 Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
           "username": "testuser",
           "email": "test@example.com",
           "password": "password123"
         }'
```

### 🔓 Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
           "username": "testuser",
           "password": "password123"
         }'
```
*Note: A successful login will return a JSON object containing your `token`.*

---

## 📁 Project Structure

```text
user-management/
├── src/main/java/com/example/usermanagement/
│   ├── controller/        # REST endpoints (UserController)
│   ├── dto/               # Data Transfer Objects (LoginRequest, RegisterRequest)
│   ├── entity/            # JPA Domain Models (User, Role)
│   ├── repository/        # Spring Data JPA Interfaces (UserRepository)
│   ├── security/          # Security & JWT logic (JwtFilter, JwtUtil, SecurityConfig, ApplicationConfig)
│   ├── service/           # Business Logic (UserService)
│   └── UserManagementApplication.java # Application Runner
├── src/main/resources/
│   └── application.properties # Fallback configurations
├── .env                   # Environment variables (ignored by Git)
├── .gitignore             # Git ignore rules
└── pom.xml                # Maven dependencies
```
