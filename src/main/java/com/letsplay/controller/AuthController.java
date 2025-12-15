package com.letsplay.controller;

import com.letsplay.dto.AuthRequest;
import com.letsplay.dto.AuthResponse;
import com.letsplay.security.JwtUtil;
import com.letsplay.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a Spring REST controller that handles HTTP requests for authentication.
 * It exposes endpoints for user login and potentially other authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Spring will automatically inject an instance of AuthenticationManager here.
     * This is used to authenticate user credentials.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Spring will automatically inject an instance of UserDetailsServiceImpl here.
     * This service is used to load user-specific data.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Spring will automatically inject an instance of JwtUtil here.
     * This utility is used for generating and validating JWTs.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This method handles POST requests to "/api/auth/login".
     * It authenticates the user with the provided email and password, and if successful, generates and returns a JWT.
     * @param authRequest The AuthRequest object containing the user's email and password.
     * @return a ResponseEntity containing an AuthResponse with the JWT, or an error message if authentication fails.
     * @throws Exception if an error occurs during authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Attempt to authenticate the user with the provided username (email) and password.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // If authentication fails, throw an exception with a specific message.
            throw new Exception("Incorrect username or password", e);
        }

        // If authentication is successful, load the user details.
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // Generate a JWT for the authenticated user.
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return a ResponseEntity with the JWT in an AuthResponse object.
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}