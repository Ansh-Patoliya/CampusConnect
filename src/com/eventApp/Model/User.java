package com.eventApp.Model;

/**
 * Represents a user in the CampusConnect system.
 * 
 * This is the base entity for all users in the system, providing core user information
 * and role-based functionality. Users can have different roles (STUDENT, CLUB_MEMBER, ADMIN)
 * which determine their access permissions and available features.
 * 
 * User roles:
 * - STUDENT: Can browse events, register for events, and join clubs
 * - CLUB_MEMBER: Can create events, manage club activities, and organize events
 * - ADMIN: Can approve clubs and events, manage users, and oversee the system
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class User {
    
    /** Unique identifier for the user */
    private String userId;
    
    /** Full name of the user */
    private String name;
    
    /** Email address of the user (must be unique) */
    private String email;
    
    /** Encrypted password for authentication */
    private String password;
    
    /** Role that determines user permissions (STUDENT, CLUB_MEMBER, ADMIN) */
    private String role;

    /**
     * Constructs a new User with the specified details.
     * 
     * @param userId   unique identifier for the user
     * @param name     full name of the user
     * @param email    email address of the user
     * @param password encrypted password for authentication
     * @param role     user role (STUDENT, CLUB_MEMBER, or ADMIN)
     */
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the unique identifier of the user.
     * 
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the full name of the user.
     * 
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the user.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the encrypted password of the user.
     * 
     * @return the encrypted password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password of the user.
     * 
     * @param password the encrypted password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user.
     * 
     * @return the user's role (STUDENT, CLUB_MEMBER, or ADMIN)
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role the role to set (STUDENT, CLUB_MEMBER, or ADMIN)
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Checks if the user is a student.
     * 
     * @return true if the user's role is STUDENT, false otherwise
     */
    public boolean isStudent() {
        return "STUDENT".equals(this.role);
    }
    
    /**
     * Checks if the user is a club member.
     * 
     * @return true if the user's role is CLUB_MEMBER, false otherwise
     */
    public boolean isClubMember() {
        return "CLUB_MEMBER".equals(this.role);
    }
    
    /**
     * Checks if the user is an administrator.
     * 
     * @return true if the user's role is ADMIN, false otherwise
     */
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
    
    /**
     * Returns a string representation of the user.
     * 
     * @return a string containing the user's ID, name, and role
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
