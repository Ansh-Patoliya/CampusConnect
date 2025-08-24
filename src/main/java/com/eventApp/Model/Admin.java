package com.eventApp.Model;

/**
 * Represents an Admin user in the application.
 * Inherits from User and can be extended with admin-specific functionality if needed.
 */
public class Admin extends User {

    /**
     * Constructs an Admin user with specified details.
     *
     * @param userId   the unique identifier for the admin user
     * @param name     the admin's name
     * @param email    the admin's email
     * @param password the admin's password
     * @param role     the role designation (likely "Admin")
     */
    public Admin(String userId, String name, String email, String password, String role) {
        super(userId, name, email, password, role);
    }
}
