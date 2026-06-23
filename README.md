# Learn Spring Boot Todo API

A robust, production-grade Spring Boot REST API for a Todo application. This application features user registration, secure login with JSON Web Tokens (JWT), and persistent storage in PostgreSQL hosted on Neon DB.

---

## 🛠️ Tech Stack & Badges

[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)](https://jwt.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Neon](https://img.shields.io/badge/Neon-00E599?style=for-the-badge&logo=neon&logoColor=black)](https://neon.tech/)
[![Maven](https://img.shields.io/badge/apachemaven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Lombok](https://img.shields.io/badge/lombok-orange?style=for-the-badge)](https://projectlombok.org/)

---

## ✨ Features

- **User Authentication**: Secure registration and login using JWT.
- **RESTful Todo Management**: Full CRUD operations (Create, Read, Update, Delete) mapped to specific users.
- **Validation**: Strict input validation using Spring Boot Starter Validation.
- **Database Integration**: Dynamic schema generation and updates utilizing Hibernate/JPA connected to Neon DB.
- **Logging**: Configured system logging facade (SLF4J) with customizable package-level verbosity.

---

## 📂 Project Directory Structure

```text
learn-spring/
├── .env                              # Local environment configurations (ignored in production)
├── pom.xml                           # Maven project dependencies & build configuration
├── src/
│   ├── main/
│   │   ├── java/org/example/learnspring/
│   │   │   ├── LearnSpringApplication.java   # Core application runner
│   │   │   ├── controller/                   # REST API Controllers (Auth, Todo)
│   │   │   ├── dto/                          # Data Transfer Objects (AuthRequest, TodoRequest)
│   │   │   ├── exception/                    # Global Exception Handler and Error Response models
│   │   │   ├── model/                        # JPA Entities (User, Todo)
│   │   │   ├── repository/                   # JPA Repository interfaces (UserRepository, TodoRepository)
│   │   │   ├── security/                     # JWT Authentication filter, Provider, and Security Config
│   │   │   └── service/                      # Business logic layers (TodoService)
│   │   └── resources/
│   │       ├── application.properties        # Main Spring Boot properties configuration
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/org/example/learnspring/     # Context loads and Service unit tests
```

---

## 🔌 API Endpoints Reference

### 1. Authentication (`/api/auth`)
*All auth endpoints are public.*

| HTTP Method | Endpoint | Request Body | Response Description |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/auth/register` | `AuthRequest` (JSON) | Registers a new user. Returns `Register Successfully` or `username already taken`. |
| **POST** | `/api/auth/login` | `AuthRequest` (JSON) | Log in. Returns a JWT Bearer Token string on success, or `401 Unauthorized` on failure. |

#### Auth Request Schema (`AuthRequest`)
```json
{
  "username": "johndoe",
  "password": "securepassword123"
}
```

---

### 2. Todo Management (`/api/todo`)
*All todo endpoints require a valid JWT token passed in the `Authorization` header as `Bearer <token>`.*

| HTTP Method | Endpoint | Request Body | Response Description |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/todo/allTask` | None | Returns a JSON list of all Todo items belonging to the authenticated user. |
| **GET** | `/api/todo/byId/{id}` | None | Returns the Todo item matching the specified ID if it belongs to the user. |
| **POST** | `/api/todo/addTask` | `TodoRequest` (JSON) | Creates and returns a new Todo item associated with the authenticated user. |
| **PUT** | `/api/todo/{id}` | `Todo` (JSON) | Updates and returns the Todo item corresponding to the provided ID. |
| **DELETE** | `/api/todo/{id}` | None | Deletes the Todo item. Returns status `200` with text `Deleted`. |

#### Add Task Schema (`TodoRequest`)
```json
{
  "name": "Complete Spring Boot Tutorial",
  "completed": false
}
```

---

## 🚀 Setup & Execution

### Prerequisites
- **Java Development Kit (JDK) 17** or higher
- **Maven** (or use the included Maven wrapper `mvnw`)
- A **PostgreSQL** instance (e.g., [Neon DB](https://neon.tech/))

### 1. Environment Configuration
Create a `.env` file in the root directory of the project. Configure your PostgreSQL database credentials as shown below:

```env
DB_URL=jdbc:postgresql://<neon-db-hostname>/neondb?sslmode=require
DB_USER=<your-database-username>
DB_PASSWORD=<your-database-password>
```

### 2. Build the Project
Compile the codebase and verify the test suite:
```bash
# On Linux/macOS
./mvnw clean test

# On Windows (PowerShell)
.\mvnw.cmd clean test
```

### 3. Run the Application
Start the Spring Boot application locally:
```bash
# On Linux/macOS
./mvnw spring-boot:run

# On Windows (PowerShell)
.\mvnw.cmd spring-boot:run
```
The application will bootstrap and listen on `http://localhost:8080` by default.
