# User Management System

A robust backend REST API for managing users, built with **Spring Boot 3**, **MySQL**, **Hibernate/JPA**, and secured with **JWT (JSON Web Tokens)**.

## 🚀 Features

- **User Registration**: Create new users with unique usernames and emails.
- **User Authentication**: Login to receive a secure JWT token.
- **Stateless Sessions**: Fully stateless backend using JWTs for authorization.
- **Password Hashing**: Secure password storage using BCrypt.
- **Interactive API Documentation**: Embedded Swagger UI for easy endpoint testing right from your browser.
- **Validation**: Strict request payload validation.

---

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.5** (Web, Data JPA, Security, Validation)
- **MySQL** & **Hibernate**
- **JWT (jjwt)**
- **Lombok** (Boilerplate reduction)
- **Springdoc OpenAPI / Swagger UI**
- **Maven**

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
1. **Java 17** (or higher)
2. **MySQL Server** (running on default port 3306)

---

## ⚙️ Configuration

1. **Database Setup**:
   The application expects a MySQL database named `management`. 
   If you haven't created it yet, log into your MySQL server and run:
   ```sql
   CREATE DATABASE IF NOT EXISTS management;
   ```

2. **Application Properties**:
   By default, the application is configured to connect to MySQL using the username `root` and password `root123`.
   If your MySQL credentials differ, update the following lines in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root123
   ```

3. **JWT Secret**:
   The JWT secret key and expiration time are configured in `application.properties`:
   ```properties
   jwt.secret=4f7b60e9d4a8f9c2a5d8b7c6e4f3a2b1c0d9e8f7a6b5c4d3e2f1a0b9c8d7e6f5
   jwt.expiration=86400000 # (24 hours)
   ```

---

## 🏃‍♂️ How to Run

You do not need Maven installed globally; the project includes a Maven Wrapper (`mvnw`).

Open a terminal in the root directory of the project (`user-management`) and run:

**On Linux/Mac:**
```bash
./mvnw clean spring-boot:run
```

**On Windows:**
```cmd
mvnw.cmd clean spring-boot:run
```

The server will start on `http://localhost:8080`.

---

## 📖 API Documentation & Testing (Swagger UI)

The easiest way to test the API is using the built-in Swagger UI. Once the server is running, open your web browser and navigate to:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

From here, you can visually explore the endpoints, construct JSON payloads, and execute requests.

---

## 💻 CLI / cURL Examples

If you prefer testing via terminal, here are the `cURL` commands:

### 1. Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
           "username": "testuser",
           "email": "test@example.com",
           "password": "password123"
         }'
```

### 2. Login
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

```
src/main/java/com/example/usermanagement/
├── controller/        # REST endpoints (UserController)
├── dto/               # Data Transfer Objects (LoginRequest, RegisterRequest)
├── entity/            # JPA Domain Models (User, Role)
├── repository/        # Spring Data JPA Interfaces (UserRepository)
├── security/          # Security & JWT logic (JwtFilter, JwtUtil, SecurityConfig)
├── service/           # Business Logic (UserService)
└── UserManagementApplication.java # Application Runner
```
