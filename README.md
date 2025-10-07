# 🎓 School & Student Microservices System

## 📘 Project Summary

This project demonstrates a **microservices-based system** built using **Spring Boot 3**, **Spring Cloud**, and **Docker**.
It manages schools and students as separate services, exposing endpoints through an **API Gateway**.

Each service is independently deployable and communicates via **HTTP** using **service discovery** (Eureka).
Distributed tracing is enabled using **Zipkin**, and configuration management is centralised through a **Spring Cloud Config Server**.

---

## 🧠 Definitions

| Term              | Definition                                                                   |
| ----------------- | ---------------------------------------------------------------------------- |
| **Microservice**  | An independent, deployable unit that handles a specific business capability. |
| **API Gateway**   | A single entry point routing client requests to different microservices.     |
| **Eureka Server** | Service registry that enables service discovery.                             |
| **Config Server** | Centralised configuration management for all microservices.                  |
| **Zipkin**        | Distributed tracing system to monitor request flow between services.         |
| **Docker**        | Container platform to package and deploy services consistently.              |

---

## 👥 User Stories

1. As a school administrator, I can **add, view, and list schools**.
2. As a teacher or admin, I can **register students** and assign them to schools.
3. As a user, I can **fetch all students in a specific school**.
4. As a developer, I can **trace requests** between microservices using Zipkin.
5. As a system operator, I can **deploy and manage** each service independently via Docker.

---

## 🧩 Domain Overview

### Domain Model Diagram

```
+----------------+       1 ---- *       +----------------+
|     School     |----------------------|     Student     |
|----------------|                      |----------------|
| id             |                      | id              |
| name           |                      | name            |
| address        |                      | email           |
| ...            |                      | schoolId (FK)   |
+----------------+                      +----------------+
```

### Domain Classes

**School.java**

```java
public class School {
    private Long id;
    private String name;
    private String address;
}
```

**Student.java**

```java
public class Student {
    private Long id;
    private String name;
    private String email;
    private Long schoolId;
}
```

### ERD (Entity Relationship Diagram)

```
School (1) —— (M) Student
```

Each student belongs to exactly one school, but a school can have many students.

---

## 💻 UI Overview / User Journeys

Although this project focuses on backend microservices, a simple frontend or Postman workflow can simulate user interaction.

**Example user flow:**

1. User hits `/api/schools` via the API Gateway to create a school.
2. User hits `/api/students` to register students.
3. User queries `/api/schools/{id}/students` to list all students in a specific school.
4. All requests are logged and traced in Zipkin.

---

## 🏗 Architecture Overview

```
        +-------------------+
        |     Internet      |
        +--------+----------+
                 |
          +------v------+
          | API Gateway |
          +------+------+ 
                 |
   +-------------+--------------+
   |                            |
+--v--+                     +---v---+
|School|   <----HTTP---->   |Student|
+--+---+                     +---+--+
   |                             |
Postgres                     Postgres
(Docker)                     (Docker)

Both services:
- Register with Eureka
- Pull configuration from Config Server
- Send traces to Zipkin
```

### Supporting Services

* **Eureka Server**: Handles service discovery.
* **Config Server**: Provides externalised configs.
* **Zipkin Server**: Captures and visualises traces.

---

## 🔍 REST API Overview

### Student Service

| Method | Endpoint                         | Description                        |
| ------ | -------------------------------- | ---------------------------------- |
| `GET`  | `/api/students`                  | Get all students                   |
| `GET`  | `/api/students/{id}`             | Get student by ID                  |
| `POST` | `/api/students`                  | Create a new student               |
| `GET`  | `/api/students/search?name=John` | Search students (in-memory filter) |

### School Service

| Method | Endpoint                     | Description                                      |
| ------ | ---------------------------- | ------------------------------------------------ |
| `GET`  | `/api/schools`               | Get all schools                                  |
| `GET`  | `/api/schools/{id}`          | Get school by ID                                 |
| `POST` | `/api/schools`               | Create a new school                              |
| `GET`  | `/api/schools/{id}/students` | Get students of a school (calls Student service) |

---

## 🔄 Communication Overview

* **School → Student**: via REST API calls using `RestTemplate` or `WebClient`.
* **Service Discovery**: via Eureka.
* **Config Fetching**: via Config Server.
* **Tracing**: all HTTP requests logged in Zipkin.
* **Containers**: each service runs in its own Docker container.

---

## 🧠 Search (In-memory)

Example implementation in StudentService:

```java
public List<Student> searchStudents(String name) {
    return students.stream()
        .filter(s -> s.getName().equalsIgnoreCase(name))
        .collect(Collectors.toList());
}
```

This avoids DB queries for quick lookups.

---

## 🧱 Project Template Structure

```
school-student-microservices/
├── api-gateway/
│   └── src/main/java/com/example/gateway
├── config-server/
│   └── src/main/java/com/example/config
├── eureka-server/
│   └── src/main/java/com/example/eureka
├── school-service/
│   └── src/main/java/com/example/school
├── student-service/
│   └── src/main/java/com/example/student
├── docker-compose.yml
├── README.md
└── zipkin (optional external container)
```

---

## 🧰 Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Cloud Netflix Eureka**
* **Spring Cloud Config**
* **Spring Boot Actuator**
* **Spring Web / WebFlux**
* **Zipkin**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## ⚙️ Running Locally

```bash
# Build and run all services
docker-compose up --build
```

Then open:

* `http://localhost:8761` → Eureka Dashboard
* `http://localhost:9411` → Zipkin
* `http://localhost:8080` → API Gateway entry

---

## 📈 Future Improvements

* Add JWT authentication and role-based access.
* Replace in-memory search with Elasticsearch or database query.
* Add caching using Redis.
* Add CI/CD pipeline with GitHub Actions.
