# QuickBite – User Management Module

## 1. Overview

The User Management module handles customer registration, login, authentication and profile retrieval.

It uses Spring Boot, Spring Data JPA, PostgreSQL, BCrypt password hashing and JWT-based authentication.

## 2. Features Implemented

- Customer registration
- Email uniqueness validation
- Input validation
- Password hashing using BCrypt
- Customer login
- Credential verification
- JWT access token generation
- JWT signature and expiration validation
- Stateless authentication using Spring Security
- Protected customer profile API
- Centralized exception handling

## 3. Package Structure

```text
com.qb.quickbite/
│
├── auth/
│   ├── filter/
│   │   └── JwtAuthenticationFilter.java
│   │
│   └── service/
│       ├── JwtService.java
│       └── CustomUserDetailsService.java
│
├── config/
│   ├── SecurityConfig.java
│   └── JwtProperties.java
│
├── users/
│   ├── controller/
│   │   └── UserController.java
│   │
│   ├── service/
│   │   └── UserService.java
│   │
│   ├── repository/
│   │   └── UserRepository.java
│   │
│   ├── entity/
│   │   └── User.java
│   │
│   ├── dto/
│   │   ├── UserRequest.java
│   │   ├── UserResponse.java
│   │   ├── LoginRequest.java
│   │   └── LoginResponse.java
│   │
│   └── exception/
│       ├── EmailAlreadyExistsException.java
│       └── InvalidCredentialsException.java
│
└── global/
    └── exception/
        └── GlobalExceptionHandler.java
```

## 4. API Endpoints

### 4.1 Customer Registration

**Endpoint:** `POST /service/users/register`

**Access:** Public

Request body:

```json
{
    "name": "Jeya",
    "email": "jeya@gmail.com",
    "password": "Jeya@12345",
    "phone": "9876543210"
}
```

Success response: `201 Created`

Registration validations:
- Name is mandatory.
- Email must have a valid format.
- Only supported email providers are accepted.
- Password must contain at least eight characters.
- Password must contain uppercase, lowercase, numeric and special characters.
- Password must not contain whitespace.
- Duplicate email registration is rejected.

Passwords are stored as BCrypt hashes, never as plain text.

### 4.2 Customer Login

**Endpoint:** `POST /service/users/login`

**Access:** Public

Request body:

```json
{
    "email": "jeya@gmail.com",
    "password": "Jeya@12345"
}
```

Success response: `200 OK`

Response:

```json
{
    "id": 1,
    "name": "Jeya",
    "email": "jeya@gmail.com",
    "message": "Login successful",
    "accessToken": "<JWT_ACCESS_TOKEN>"
}
```

Login functionality:
- Verifies the registered email.
- Compares the submitted password with the stored BCrypt hash.
- Generates a JWT only after successful credential verification.
- Returns the access token in the login response.
- Uses the same generic error message for unknown email and incorrect password.

### 4.3 Customer Profile

**Endpoint:** `GET /service/users/profile`

**Access:** Authenticated customers only.

Authorization header:

```http
Authorization: Bearer <JWT_ACCESS_TOKEN>
```

Success response: `200 OK`

```json
{
    "id": 1,
    "name": "Jeya",
    "email": "jeya@gmail.com",
    "phone": "9876543210"
}
```

The profile API retrieves the currently authenticated customer's details using the email extracted from Spring Security's authentication context.

Password information is never returned.

## 5. JWT Authentication Architecture

### JWT Generation

The JWT is generated after successful customer login.

The token contains:
- Subject: Customer email
- Issued-at timestamp
- Expiration timestamp
- Cryptographic signature

### JWT Validation

The `JwtAuthenticationFilter`:
1. Reads the Authorization header.
2. Checks for the Bearer authentication scheme.
3. Extracts the JWT.
4. Validates its signature and expiration.
5. Loads the customer using `CustomUserDetailsService`.
6. Establishes authentication in Spring Security's SecurityContext.

### Security Configuration

`SecurityConfig.java` configures:

- Stateless session management.
- CSRF disabled for the stateless API configuration.
- Public access to registration and login.
- Authentication required for other endpoints.
- JWT authentication filter integration.
- HTTP 401 response for unauthenticated requests.

### Token Configuration

JWT configuration is maintained in `application.properties`.

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

Token expiration: **1 hour**

The signing secret is stored in the local `.env` file and must never be committed to Git.

## 6. HTTP Status Codes

| Status | Description |
|---|---|
| 200 OK | Login or profile retrieval successful |
| 201 Created | Customer registration successful |
| 400 Bad Request | Request validation failure |
| 401 Unauthorized | Invalid credentials, missing, invalid or expired JWT |
| 403 Forbidden | Authenticated user lacks required permission |
| 409 Conflict | Email already registered |

## 7. Technology Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Security Crypto
- JJWT
- Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Maven

## 8. Testing Scenarios

The following scenarios have been tested:

| Scenario | Expected Result |
|---|---|
| Valid customer registration | 201 Created |
| Duplicate email registration | 409 Conflict |
| Invalid registration fields | 400 Bad Request |
| Valid customer login | 200 OK with JWT |
| Incorrect password | 401 Unauthorized |
| Unregistered email | 401 Unauthorized |
| Access profile without JWT | 401 Unauthorized |
| Access profile with valid JWT | 200 OK |
| Access profile with invalid JWT | 401 Unauthorized |
| Access profile with expired JWT | 401 Unauthorized |

## 9. Current Implementation Status

Completed:
- Customer registration
- Customer login
- BCrypt password hashing
- JWT generation and validation
- Spring Security integration
- Stateless authentication
- Protected customer profile API
- Centralized exception handling
- API validation and testing

## 10. Future Enhancements

- Refresh token implementation
- Logout and token revocation strategy
- Password change and reset functionality
- Dynamic user roles and role-based authorization
- Admin authentication
- Restaurant-owner authentication
- Additional customer profile management APIs

---

**Module Status:** Customer registration, login, JWT authentication and customer profile retrieval implemented and tested.