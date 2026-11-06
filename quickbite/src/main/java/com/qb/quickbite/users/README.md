# QuickBite – User Management Module

## 1. Overview

The User Management module handles user registration, user data persistence, input validation and password security.

It is responsible for managing user-related operations within the QuickBite application.

## 2. Package Structure

```text
users
│
├── controller
│   └── UserController.java
│
├── service
│   └── UserService.java
│
├── repository
│   └── UserRepository.java
│
├── entity
│   └── User.java
│
├── dto
│   ├── UserRequest.java
│   └── UserResponse.java
│
└── exception
    └── EmailAlreadyExistsException.java
```

### Package Responsibilities

| Package | Responsibility |
|---|---|
| controller | Handles incoming HTTP requests and returns API responses. |
| service | Contains user-related business logic. |
| repository | Handles database operations using Spring Data JPA. |
| entity | Defines the User database entity and its mapping. |
| dto | Defines request and response objects for API communication. |
| exception | Contains user-module-specific custom exceptions. |

## 3. Available APIs

### User Registration

**Endpoint:** `POST /service/users/register`

**Purpose:** Registers a new user in QuickBite.

**Request Body:**

```json
{
  "name": "Jey",
  "email": "jey@gmail.com",
  "password": "Quickbite@123",
  "phone": "9876543210"
}
```

**Success Response:** `201 Created`

Returns the newly created user's ID and profile details. Passwords are never included in the response.

### HTTP Status Codes

| Status | Description |
|---|---|
| 201 | User registered successfully. |
| 400 | Request validation failed. |
| 409 | Email address already registered. |
| 500 | Unexpected server error. |

## 4. Validation and Security

- Name and email are mandatory.
- Email format is validated.
- Only the configured personal email domains are accepted.
- Password must contain at least 8 characters.
- Password must contain uppercase and lowercase letters, a number and a special character.
- Passwords containing spaces are rejected.
- Passwords are hashed using BCrypt before database persistence.
- Duplicate email registration is prevented.

## 5. Exception Handling

User-specific exceptions are defined inside the `exception` package.

Application-wide exception handling is implemented separately in:

`com.qb.quickbite.global.exception.GlobalExceptionHandler`

The global handler currently handles:
- Duplicate email registration.
- Request validation failures.

## 6. Current Implementation Status

| Feature | Status |
|---|---|
| User registration | Completed |
| User persistence | Completed |
| Email validation | Completed |
| Password validation | Completed |
| BCrypt password hashing | Completed |
| Duplicate email detection | Completed |
| Global exception handling | Completed |
| User login | Planned |
| JWT authentication | Planned |

## 7. Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Spring Security Crypto (BCrypt)

---

**Note:** This module will be extended as additional user-management capabilities are implemented.