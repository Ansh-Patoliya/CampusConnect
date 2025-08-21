package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Student;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for event registration-related database operations.
 * Handles user registration for events and retrieval of participant information.
 */
public class EventRegistrationDAO {

    /**
     * Registers a user for a specific event by creating an entry in the event_registration table.
     * This establishes a many-to-many relationship between users and events.
     *
     * @param eventId the unique identifier of the event to register for
     * @param userId the unique identifier of the user registering for the event
     * @throws DatabaseExceptionHandler if the registration operation fails
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public void registerForEvent(int eventId, String userId) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();

        // Insert new registration record linking user to event
        String query = "INSERT INTO event_registration (event_id, user_id) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, eventId);
        ps.setString(2, userId);

        int rowsAffected = ps.executeUpdate();
        // Validate that the registration was successfully created
        if (rowsAffected < 0)
            throw new DatabaseExceptionHandler("Registration failed. Please try again.");
    }

    /**
     * Retrieves a list of all students registered for a specific event.
     * Uses complex JOIN query to combine event_registration, users, and students tables
     * to get complete participant information including academic details.
     * Results are ordered alphabetically by student name.
     *
     * @param eventId the unique identifier of the event
     * @return List&lt;Student&gt; containing all registered participants with their academic information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public List<Student> getParticipantList(int eventId) throws SQLException, ClassNotFoundException {
        List<Student> participantList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        // Complex JOIN query to get participant details from multiple tables
        // Links event_registration -> users -> students to get complete student information
        String query = "select u.user_id, u.name, s.department, s.semester " +
                "from event_registration er " +
                "inner join users u ON er.user_id = u.user_id " +
                "inner join students s ON u.user_id = s.student_id " +
                "where er.event_id = ? order by u.name";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, eventId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Create Student objects with essential information for participant display
            // Note: email, password, and role are set to null for security/privacy reasons
            participantList.add(new Student(resultSet.getString("user_id"), resultSet.getString("name"), null, null, null,
                    resultSet.getString("department"), resultSet.getInt("semester"), null));
        }
        return participantList;
    }

    public void getFinalTicketPrice(){
        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareCall("call apply_ticket_price_rules()");
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
