# Plan to Complete the "Let's Play" Assignment

This document outlines the steps to build the CRUD API as described in the assignment.

## 1. Project Setup
- Initialize a new Spring Boot project using Spring Initializr.
- Add the following dependencies:
    - Spring Web
    - Spring Data MongoDB
    - Spring Security
- Configure the MongoDB connection in `application.properties` or `application.yml`.

## 2. Model (Domain) Classes
- Create the `User` entity class with fields: `id`, `name`, `email`, `password`, `role`.
- Create the `Product` entity class with fields: `id`, `name`, `description`, `price`, `userId`.

## 3. Repository Interfaces
- Create a `UserRepository` interface that extends `MongoRepository` for the `User` entity.
- Create a `ProductRepository` interface that extends `MongoRepository` for the `Product` entity.

## 4. Security Implementation
- Configure Spring Security.
- Implement a `UserDetailsService` to load user-specific data from the database.
- Implement password encoding using a `PasswordEncoder` (e.g., `BCryptPasswordEncoder`).
- Implement token-based authentication (JWT):
    - Create a utility class to generate and validate JWTs.
    - Create a filter that intercepts requests, validates the JWT, and sets the user's authentication context.
- Configure authorization rules:
    - Allow public access to the "GET Products" API.
    - Restrict other APIs based on user roles (admin/user).

## 5. Service Layer
- Create a `UserService` to handle the business logic for user operations.
- Create a `ProductService` for product-related business logic.
- Implement input validation in the service layer to prevent bad data and potential security vulnerabilities.

## 6. Controller (API) Layer
- Create a `UserController` with RESTful endpoints for user CRUD operations.
- Create a `ProductController` with RESTful endpoints for product CRUD operations.
- Create an `AuthenticationController` with an endpoint to handle user login and token generation.

## 7. Error Handling
- Create a global exception handler using `@ControllerAdvice` to catch and manage exceptions.
- Return appropriate HTTP status codes and clear error messages.

## 8. Data Transfer Objects (DTOs)
- Create DTOs for `User` and `Product` to control the data exposed through the API. This is crucial for security, especially to avoid exposing passwords.

## 9. Documentation and Execution
- Create a `README.md` file with clear instructions on how to build and run the application.
- Optionally, provide a shell script to automate running the project.

## 10. Bonus Features (Optional)
- **CORS:** Configure Cross-Origin Resource Sharing (CORS) to control access from different domains.
- **Rate Limiting:** Implement rate limiting on the API to prevent brute-force attacks.
