package com.letsplay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * This class is responsible for configuring the security of the application.
 * @Configuration indicates that this class contains Spring configuration.
 * @EnableWebSecurity enables Spring Security's web security support.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method defines a SecurityFilterChain bean, which is responsible for all the security (protecting the application URLs, validating username and passwords, etc.).
     * @param http HttpSecurity object to configure security.
     * @return a SecurityFilterChain object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection.
                // This is common for stateless REST APIs where the client is not a browser.
                .csrf(csrf -> csrf.disable())

                // Configure authorization rules for HTTP requests.
                .authorizeHttpRequests(authz -> authz
                        // Permit all requests to URLs starting with "/api/auth/**". This is for authentication endpoints like login.
                        .requestMatchers("/api/auth/**").permitAll()
                        // Permit POST requests to "/api/users" for user creation (sign-up).
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // Permit all GET requests to the "/api/products" endpoint. This is for the public product listing.
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                        // Any other request must be authenticated.
                        .anyRequest().authenticated()
                )
                // Configure session management to be stateless, as we are using JWT.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add our custom JWT filter before Spring Security's UsernamePasswordAuthenticationFilter.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                // Configure CORS (Cross-Origin Resource Sharing).
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    // Allow requests from the specified origin (e.g., a frontend application running on localhost:3000).
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                    // Allow the specified HTTP methods.
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    // Allow all headers.
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                }));

        return http.build();
    }

    /**
     * This method defines a PasswordEncoder bean, which is used to hash passwords.
     * We are using BCrypt, which is a strong hashing algorithm.
     * @return a PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method defines an AuthenticationManager bean.
     * The AuthenticationManager is used to authenticate user credentials.
     * @param authenticationConfiguration The AuthenticationConfiguration object.
     * @return an AuthenticationManager object.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method defines an AuthenticationProvider bean.
     * It uses our custom UserDetailsService and the configured PasswordEncoder.
     * @return an AuthenticationProvider object.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
