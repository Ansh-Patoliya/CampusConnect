package com.eventApp.DAO;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object for student-related database operations.
 * Handles student information retrieval, event history tracking, and interest management.
 */
public class StudentDAO {

    /**
     * Retrieves complete student information for a given user.
     * Joins students and users tables to get both academic and personal details.
     * Handles comma-separated interests string conversion to List.
     *
     * @param user the User object containing the user ID to search for
     * @return Student object with complete details, or null if not found or user is null
     */
    public static Student getStudent(User user) {
        if (user == null || user.getUserId() == null) return null;

        // JOIN query to get both student academic info and user personal details
        String query = "SELECT s.student_id, s.department, s.semester, s.interests, " +
                "u.name, u.email, u.password, u.role " +
                "FROM students s JOIN users u ON s.student_id = u.user_id " +
                "WHERE s.student_id" +
                " = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Process interests field: convert CSV string to List<String>
                    String interestCSV = rs.getString("interests");
                    List<String> interestList = (interestCSV == null || interestCSV.trim().isEmpty())
                            ? null
                            : Arrays.stream(interestCSV.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    // Create Student object with all retrieved information
                    return new Student(
                            rs.getString("student_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("department"),
                            rs.getInt("semester"),
                            interestList
                    );
                }
            }
        } catch (Exception e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace(); // or use Logger
        }
        return null;
    }

    /**
     * Retrieves the event history for a specific student user.
     * Uses JOIN between event_history and event_registration to get events the user participated in.
     * Returns events in a custom linked list data structure for easy traversal.
     *
     * @param userId the unique identifier of the student user
     * @return MyEventLL containing all past events the student participated in
     */
    public MyEventLL viewEventsHistory(String userId){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            // JOIN query to get event history for events the user was registered for
            String fetchEventsQuery = "select * from event_history eh inner join event_registration er on eh.event_id = er.event_id where er.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchEventsQuery);
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                // Extract event fields from the result set
                int eventId=resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                int clubId = resultSet.getInt("club_id");
                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                String venue = resultSet.getString("venue");
                int totalParticipants = resultSet.getInt("total_participants");
                double ticketPrice = resultSet.getDouble("ticket_price");
                String category = resultSet.getString("category");

                // Create Event object with historical data
                Event newEvent = new Event(eventId, eventName, clubId, venue, ticketPrice, eventDate, startTime, endTime, totalParticipants );
                newEvent.setUserId(resultSet.getString("founder_id"));
                newEvent.setCategory(category);
                eventList.insert(newEvent);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * Retrieves the list of interests for a specific student.
     * Parses comma-separated interests string from database into individual interest items.
     *
     * @param studentId the unique identifier of the student
     * @return List<String> containing individual interest items, or empty list if none found
     */
    public List<String> getInterestList(String studentId) {
        List<String> interestList = new ArrayList<>();
        String query = "SELECT interests FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Parse comma-separated interests string into individual items
                String[] interests = rs.getString("interests").split(",");
                for (String i: interests) {
                    interestList.add(i.trim()); // Trim whitespace from each interest
                }
            }
        } catch (Exception e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace(); // or use Logger
        }
        return interestList;
    }
}
