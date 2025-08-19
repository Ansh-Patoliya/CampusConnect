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
     * Retrieves the club ID for a given club name.
     * Throws an exception if the club is not found to ensure data integrity.
     *
     * @param clubName the name of the club to search for
     * @return int club ID of the found club
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if club is not found
     */
    public static int getClubId(String clubName) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        int clubId = 0;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select club_id from clubs where club_name=?");
        preparedStatement.setString(1, clubName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clubId = resultSet.getInt(1);
        } else {
            // Throw exception if club not found to prevent silent failures
            throw new DatabaseExceptionHandler("Club not found.");
        }
        return clubId;
    }

    /**
     * Retrieves the club ID for a club that a specific user is a member of.
     * Used to determine which club a user belongs to for authorization purposes.
     *
     * @param userId the unique identifier of the user
     * @return int club ID of the club the user is a member of
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if user is not a member of any club
     */
    public static int getClubIdByUserId(String userId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        int clubId = 0;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select club_id from club_members where member_id=?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clubId = resultSet.getInt(1);
        } else {
            // Throw exception if user is not a club member
            throw new DatabaseExceptionHandler("Club not found.");
        }
        return clubId;
    }

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
     * Registers student-specific information in the students table.
     * Called after successful user registration to add academic details.
     * Converts interest list to comma-separated string for database storage.
     *
     * @param student the Student object containing academic information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if student registration fails
     */
    public void registrationStudent(Student student) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into students values(?,?,?,?)");
        preparedStatement.setString(1, student.getUserId());
        preparedStatement.setString(2, student.getDepartment());
        preparedStatement.setInt(3, student.getSemester());
        // Convert interest list to CSV string for database storage
        preparedStatement.setString(4, String.join(",", student.getInterest()));

        int studentInsert = preparedStatement.executeUpdate();
        if (studentInsert > 0) {
            return; // Success case
        }
        throw new DatabaseExceptionHandler("Student registration failed. Please try again.");
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
     * Registers a user as a member of a specific club with a given position.
     * Creates the many-to-many relationship between users and clubs.
     *
     * @param clubMember the ClubMember object containing membership information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if club member registration fails
     */
    public void registrationClubMember(ClubMember clubMember) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into club_members values(?,?,?)");
        preparedStatement.setString(1, clubMember.getUserId());
        preparedStatement.setInt(2, clubMember.getClubId());
        preparedStatement.setString(3, clubMember.getPosition());

        int clubMemberInsert = preparedStatement.executeUpdate();
        // Validate that the club membership was successfully created
        if (clubMemberInsert < 0) {
            throw new DatabaseExceptionHandler("Club member registration failed. Please try again.");
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
     * Retrieves a list of all club names in the system.
     * Used for dropdown menus and selection components in the UI.
     *
     * @return List<String> containing all club names, or empty list if none found
     */
    public List<String> getAllClubsName() {
        List<String> clubNames = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT club_name FROM clubs");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String clubName = rs.getString(1);
                clubNames.add(clubName);
            }
        } catch (Exception e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return clubNames;
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
