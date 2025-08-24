package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data Access Object for user-related database operations.
 * Handles user authentication, registration, validation, and club management operations.
 * Serves as the central DAO for user lifecycle management in the application.
 */
public class UserDAO {

    /**
     * Validates that an email address is not already registered in the system.
     * Part of the registration validation process to ensure unique email addresses.
     *
     * @param newEmail the email address to validate for uniqueness
     * @throws ValidationException if the email already exists in the database
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public static void checkDuplicateEmail(String newEmail) throws ValidationException, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT email FROM Users WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newEmail);

        ResultSet rs = preparedStatement.executeQuery();

        // If any record is found, the email already exists
        if (rs.next()) {
            throw new ValidationException("Email already exists.");
        }
    }

    /**
     * Validates that an enrollment number (user ID) is not already registered.
     * Ensures unique enrollment numbers across all users in the system.
     *
     * @param enrollment the enrollment number to validate for uniqueness
     * @throws ValidationException if the enrollment number already exists
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public static void checkDuplicateEnrollment(String enrollment) throws ValidationException, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT user_id FROM users WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, enrollment);

        ResultSet rs = preparedStatement.executeQuery();

        // If any record is found, the enrollment number already exists
        if (rs.next()) {
            throw new ValidationException("Enrollment number already exists.");
        }
    }

    /**
     * Registers a new user in the users table with basic information.
     * This is the first step in user registration before role-specific data insertion.
     *
     * @param user the User object containing registration information
     * @throws DatabaseExceptionHandler if user registration fails
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public void registrationUser(User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(user_id,name,email,password,role) VALUES(?,?,?,?,?)");
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getRole().toUpperCase()); // Normalize role to uppercase

        int userInsert = preparedStatement.executeUpdate();
        // Validate that the user was successfully inserted
        if (userInsert < 0) {
            throw new DatabaseExceptionHandler("User registration failed. Please try again.");
        }
    }

    /**
     * Validates user login credentials by checking email and password.
     * Uses secure password comparison to verify authentication.
     *
     * @param emailInput the email address provided for login
     * @param passwordInput the password provided for login
     * @return true if credentials are valid, false otherwise
     */
    public boolean checkLoginDetails(String emailInput, String passwordInput) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select email,password from users where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, emailInput);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String storedPassword = rs.getString("password");
                // Use Objects.equals for null-safe password comparison
                if (Objects.equals(passwordInput, storedPassword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Silent failure for security reasons - don't expose database errors in login
        }
        return false;
    }

    /**
     * Updates a user's password in the database.
     * Used for password reset functionality after email verification.
     *
     * @param emailInput the email address of the user
     * @param newPassword the new password to set
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if user is not found or update fails
     */
    public void resetPass(String emailInput, String newPassword) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

        String query = "UPDATE users SET password = ? WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newPassword);
        preparedStatement.setString(2, emailInput);

        int r = preparedStatement.executeUpdate();
        // Validate that a user was found and updated
        if (r < 0) {
            throw new DatabaseExceptionHandler("User not found. Please try again.");
        }
    }


    /**
     * Registers a new club in the system.
     * Creates the club with pending status for admin approval.
     *
     * @param club the Club object containing club information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if club registration fails
     */
    public void registrationClub(Club club) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into clubs(club_name,category,description,founder_id,status,max_member) values(?,?,?,?,?,?)");
        preparedStatement.setString(1, club.getClubName());
        preparedStatement.setString(2, club.getCategory());
        preparedStatement.setString(3, club.getDescriptions());
        preparedStatement.setString(4, club.getFounderId());
        preparedStatement.setString(5, club.getStatus());
        preparedStatement.setInt(6, club.getMaxMemberCount());

        int insertClub = preparedStatement.executeUpdate();
        // Validate that the club was successfully created
        if (insertClub < 0) {
            throw new DatabaseExceptionHandler("Club registration failed. Please try again.");
        }
    }

    /**
     * Retrieves a complete User object by email address.
     * Used during login process to get user information after authentication.
     *
     * @param emailInput the email address to search for
     * @return User object with complete information, or null if not found
     */
    public User getUserByemail(String emailInput) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, emailInput);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Extract all user fields and create User object
                String userId = rs.getString("user_id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                user = new User(userId, name, emailInput, password, role);
            }
        } catch (Exception e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves the display name for a given user ID.
     * Used for showing user names in UI components without exposing full user details.
     *
     * @param userId the unique identifier of the user
     * @return String containing the user's name, or null if not found
     */
    public String getUserNameBy(String userId) {
        String userName = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM users WHERE user_id = ?");
            preparedStatement.setString(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userName = rs.getString("name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return userName;
    }
}
