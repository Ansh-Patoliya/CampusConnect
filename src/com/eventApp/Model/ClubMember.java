package com.eventApp.Model;

/**
 * Represents a member of a club who is also a user of the system.
 * Inherits basic user details from the User class and adds club-specific information.
 */
public class ClubMember extends User {

    private String position;  // Position of the member in the club (e.g., President, Member)

    /**
     * Full constructor for ClubMember with all user and club-specific details.
     *
     * @param userId   Unique identifier for the user
     * @param name     Name of the user
     * @param email    Email address of the user
     * @param password Password for user login
     * @param role     Role assigned to the user (e.g., "ClubMember")
     * @param position Position held in the club (e.g., President, Secretary)
     */
    public ClubMember(String userId, String name, String email, String password, String role, String position) {
        super(userId, name, email, password, role);
        this.position = position;
    }

    /**
     * Alternate constructor for ClubMember without password and role.
     * Useful in scenarios where authentication is not immediately needed.
     *
     * @param userId   Unique identifier for the user
     * @param name     Name of the user
     * @param email    Email address of the user
     * @param position Position held in the club
     */
    public ClubMember(String userId, String name, String email, String position) {
        super(userId, name, email);
        this.position = position;
    }

    /**
     * Retrieves the position held by the club member.
     *
     * @return the position string (e.g., President, Member)
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position held by the club member.
     *
     * @param position the position string to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
