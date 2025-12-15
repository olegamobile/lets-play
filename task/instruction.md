# Instructions to Complete the "Let's Play" Assignment

This document provides detailed instructions on how to execute each step from the `steps.md` file.

## 1. Project Setup

### Initialize a new Spring Boot project
You can use `curl` to generate a project from `start.spring.io`:

```bash
curl https://start.spring.io/starter.zip -d type=maven-project -d dependencies=web,data-mongodb,security -d baseDir=lets-play -d javaVersion=17 -o lets-play.zip
unzip lets-play.zip
```

### Configure MongoDB connection
Open `src/main/resources/application.properties` and add the following configuration:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/lets-play
```

## 2. Model (Domain) Classes

Create a `com.example.letsplay.model` package.

### User.java
```java
package com.example.letsplay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;

    // Getters and setters
}
```

### Product.java
```java
package com.example.letsplay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private String userId;

    // Getters and setters
}
```

## 3. Repository Interfaces

Create a `com.example.letsplay.repository` package.

### UserRepository.java
```java
package com.example.letsplay.repository;

import com.example.letsplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
```

### ProductRepository.java
```java
package com.example.letsplay.repository;

import com.example.letsplay.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
```

## 4. Security Implementation

Create a `com.example.letsplay.security` package.

### SecurityConfig.java
```java
package com.example.letsplay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### UserDetailsServiceImpl.java
```java
package com.example.letsplay.security;

import com.example.letsplay.model.User;
import com.example.letsplay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
```
This is a simplified implementation. You should handle roles properly.

### JWT Implementation
You will need to add a JWT library dependency (e.g., `io.jsonwebtoken:jjwt-api`, `io.jsonwebtoken:jjwt-impl`, `io.jsonwebtoken:jjwt-jackson`) to your `pom.xml`.

A full JWT implementation is too verbose for this document, but it would involve:
- A `JwtUtil` class to create and validate tokens.
- A `JwtRequestFilter` class that extends `OncePerRequestFilter` to check for a JWT in the `Authorization` header of incoming requests.

## 5. Service Layer

Create a `com.example.letsplay.service` package.

### UserService.java
```java
package com.example.letsplay.service;

import com.example.letsplay.model.User;
import com.example.letsplay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Other service methods
}
```

## 6. Controller (API) Layer

Create a `com.example.letsplay.controller` package.

### AuthController.java
```java
package com.example.letsplay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // You will need to inject AuthenticationManager and JwtUtil
    
    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        // Authenticate user, then generate and return a JWT
        return "jwt-token";
    }
}
```

## 7. Error Handling

Create a `com.example.letsplay.exception` package.

### GlobalExceptionHandler.java
```java
package com.example.letsplay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
This is a very basic handler. You should create specific exceptions and handle them accordingly.

## 8. Data Transfer Objects (DTOs)

Create a `com.example.letsplay.dto` package.

### UserDto.java
```java
package com.example.letsplay.dto;

public class UserDto {
    private String id;
    private String name;
    private String email;
    private String role;

    // Getters and setters
}
```

## 9. Documentation and Execution

### Build the project
```bash
./mvnw clean install
```

### Run the project
```bash
java -jar target/lets-play-0.0.1-SNAPSHOT.jar
```
Or you can use the Spring Boot Maven plugin:
```bash
./mvnw spring-boot:run
```

## 10. Bonus Features

### CORS
In your `SecurityConfig`, you can add `cors()` configuration.
```java
// In SecurityConfig.java
http.cors(cors -> cors.configurationSource(request -> {
    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
    corsConfiguration.setAllowedOrigins(java.util.List.of("http://localhost:3000"));
    corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
    corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
    return corsConfiguration;
}));
```

### Rate Limiting
For rate limiting, you could use a library like Bucket4j. This involves adding the dependency and then creating an interceptor to check the rate limit for each request.
