package com.eventApp.Model;

/**
 * Represents a user in the event application.
 * Contains basic user details such as id, name, email, password, and role.
 */
public class User {
    private String userId, name, email, password, role;

    /**
     * Constructor to initialize a user with all details.
     *
     * @param userId   Unique identifier for the user
     * @param name     Name of the user
     * @param email    Email address of the user
     * @param password User's password (should be securely handled)
     * @param role     Role of the user (e.g., admin, member)
     */
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor to initialize a user with only userId, name, and email.
     * Useful when password and role are not needed or will be set later.
     *
     * @param userId Unique identifier for the user
     * @param name   Name of the user
     * @param email  Email address of the user
     */
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Getters and setters for each field

    /**
     * Gets the user ID.
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * @param userId Unique identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email Email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * @param role Role to set (e.g., admin, member)
     */
    public void setRole(String role) {
        this.role = role;
    }
}
