package com.letsplay.dto;

/**
 * This class is a Data Transfer Object (DTO) used for authentication requests.
 * It carries the user's email and password from the client to the server during the login process.
 */
public class AuthRequest {

    /**
     * The user's email.
     */
    private String email;

    /**
     * The user's password (in plain text for the login attempt).
     */
    private String password;

    // Getters and Setters

    /**
     * Gets the email from the authentication request.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email in the authentication request.
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password from the authentication request.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password in the authentication request.
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
