package com.letsplay.dto;

/**
 * This class is a Data Transfer Object (DTO) used for authentication responses.
 * It carries the JWT (JSON Web Token) back to the client after a successful login.
 */
public class AuthResponse {

    /**
     * The generated JWT.
     */
    private final String jwt;

    /**
     * Constructor for AuthResponse.
     * @param jwt The generated JWT.
     */
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Gets the JWT.
     * @return the JWT string.
     */
    public String getJwt() {
        return jwt;
    }
}
