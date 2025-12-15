package com.letsplay.dto;

/**
 * This is a Data Transfer Object (DTO) for the User entity.
 * A DTO is an object that carries data between processes. In our case, it's used to transfer data from the server to the client.
 * We use DTOs to control what data is sent to the client.
 * For example, we don't want to send the user's password, so this DTO doesn't have a password field.
 */
public class UserDto {

    /**
     * The user's ID.
     */
    private String id;

    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The user's role.
     */
    private String role;

    // Getters and Setters

    /**
     * Gets the user's ID.
     * @return the user's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     * @param id the user's ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * @param name the user's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's role.
     * @return the user's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * @param role the user's role.
     */
    public void setRole(String role) {
        this.role = role;
    }
}
