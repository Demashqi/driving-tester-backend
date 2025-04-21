
# 🚗 Driving Tester - Backend (Spring Boot)

![Java](https://img.shields.io/badge/Java-17+-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-Backend-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)

This is the backend system for the **Driving Tester** web application, built with **Spring Boot**. It provides a RESTful API for user accounts, driving test questions, quizzes, saved questions, and results. It supports multi-language question translations and allows users to take online driving practice tests with full explanations and images.

---

## 🌟 Features

- ✅ User Registration & Login (Email-based)
- 🧠 Quiz System with Category & Language Filters
- 💾 Save & Unsave Questions (Bookmarks)
- 📊 Track Attempt History per User
- 🌐 Multi-language Question Translations (Arabic, English, etc.)
- 📷 Image Support for Questions
- 🔐 JWT-based Auth (can be added if needed)

---

## 🔧 Technologies Used

- **Spring Boot** (REST API)
- **Maven** (Build Tool)
- **JPA / Hibernate** (ORM)
- **MySQL** (Database)
- **Lombok** (Boilerplate reduction)
- **Postman** (API Testing)
- **Docker** (Optional for containerized deployment)

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL Server running locally or remotely

### 1. Clone the Repo

```bash
git clone https://github.com/your-username/driving-tester-backend.git
cd driving-tester-backend
```

### 2. Configure the Database

Update your `application.properties` or `application.yml` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/driving_tester
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

Create the database in MySQL:

```sql
CREATE DATABASE driving_tester;
```

### 3. Build & Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

The backend will be available at:

```
http://localhost:8080
```

---

## 🧪 API Testing

Use our full API documentation and test all endpoints with this Postman collection:

📬 **Postman Collection:**  
[Driving Tester API Docs (Postman)](https://documenter.getpostman.com/view/39743668/2sB2iwFaEn)

---

## 📁 Project Structure

```
src
├── accounts         → User entity, auth, repository
├── questions        → Question, translation, attempts
├── quizzes          → Quiz logic, saved questions
├── controller       → API endpoints (REST Controllers)
├── dto              → DTOs for question delivery
└── application.properties
```

---

## 🛠 Common Endpoints

| Method | Endpoint                         | Description                       |
|--------|----------------------------------|-----------------------------------|
| GET    | `/api/questions/all`             | Get all questions (translated)    |
| GET    | `/api/questions/{id}`            | Get single question by ID         |
| POST   | `/api/saved/toggle/{questionId}` | Save or unsave question           |
| GET    | `/api/saved/all`                 | Get all saved questions (user)    |
| GET    | `/api/saved/{id}`                | Get saved question by ID          |

Use Postman for the full set of routes and examples.

---

## 🧑‍💻 Author

**Driving Tester** is developed as part of a full-stack web application project by a Master's student in Software Engineering.  
Educational, reliable, and user-friendly for learner drivers in Ireland and beyond.

---

## 📃 License

This project is for educational and non-commercial use. Please contact the author for usage inquiries.

---
