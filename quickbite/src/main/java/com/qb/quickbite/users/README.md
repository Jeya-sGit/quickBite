# QuickBite – User Management Module

## 1. Overview

The User Management module handles user registration, login, user data persistence, input validation and password security.

It is responsible for managing user-related operations within the QuickBite application.

## 2. Package Structure

```text
users
│
├── controller
│   ├── UserController.java
│   └── LoginController.java
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
│   ├── UserResponse.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
│
└── exception
    ├── EmailAlreadyExistsException.java
    └── InvalidCredentialsException.java
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

### 3.1 User Registration

**Endpoint:** `POST /service/users/register`

**Purpose:** Registers a new user in QuickBite.

**Request Body:**

```json
{
  "name": "Jeya",
  "email": "jeya@gmail.com",
  "password": "Quickbite@123",
  "phone": "9876543210"
}
```

**Success Response:** `201 Created`

Returns the newly created user's ID and profile details. Passwords are never included in the response.

### Registration HTTP Status Codes

| Status | Description |
|---|---|
| 201 | User registered successfully. |
| 400 | Request validation failed. |
| 409 | Email address already registered. |
| 500 | Unexpected server error. |

### 3.2 User Login

**Endpoint:** `POST /service/users/login`

**Purpose:** Authenticates an existing user using their registered email and password.

**Request Body:**

```json
{
  "email": "jeya@gmail.com",
  "password": "Quickbite@123"
}
```

**Success Response:** `200 OK`

```json
{
  "id": 1,
  "name": "Jeya",
  "email": "jeya@gmail.com",
  "message": "Login successful"
}
```

### Login HTTP Status Codes

| Status | Description |
|---|---|
| 200 | Login successful. |
| 400 | Request validation failed. |
| 401 | Invalid email or password. |
| 500 | Unexpected server error. |

## 4. Validation and Security

### Registration

- Name and email are mandatory.
- Email format is validated.
- Only configured personal email domains are accepted.
- Password must contain at least 8 characters.
- Password must contain at least one uppercase letter, one lowercase letter, one number and one special character.
- Passwords containing spaces are rejected.
- Duplicate email registration is prevented.
- Passwords are hashed using BCrypt before database persistence.

### Login

- Email and password are mandatory.
- Email format is validated.
- Email containing spaces is rejected.
- Password containing only whitespace is rejected.
- Passwords are verified against stored BCrypt hashes using `PasswordEncoder.matches()`.
- The same authentication error message is returned for unknown email addresses and incorrect passwords.
- Passwords are never returned in API responses.
- Password strength rules are not reapplied during login.

## 5. Exception Handling

User-specific exceptions are defined inside the `exception` package.

Application-wide exception handling is implemented separately in:

`com.qb.quickbite.global.exception.GlobalExceptionHandler`

The global handler currently handles:

- Duplicate email registration – HTTP 409.
- Request validation failures – HTTP 400.
- Invalid login credentials – HTTP 401.

## 6. Current Implementation Status

| Feature | Status |
|---|---|
| User registration | Completed |
| User login | Completed |
| User persistence | Completed |
| Email validation | Completed |
| Password validation | Completed |
| BCrypt password hashing | Completed |
| Login credential verification | Completed |
| Duplicate email detection | Completed |
| Global exception handling | Completed |
| JWT authentication | Planned |

## 7. Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Spring Security Crypto (BCrypt)

## 8. Future Enhancements

- JWT-based authentication and authorization.
- User profile management.
- Password change and reset functionality.
- Account management.

---

**Note:** This module will be extended as additional user-management capabilities are implemented.