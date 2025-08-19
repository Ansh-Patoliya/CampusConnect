package com.eventApp.DAO;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Admin;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Access Object for admin-related database operations.
 * Handles event retrieval and event approval status updates for admin workflows.
 */
public class AdminDAO {

    /**
     * Retrieves all events from the database, regardless of approval or completion status.
     *
     * @return MyEventLL - a linked list of all Event objects from the database
     */
    public MyEventLL getEventList(){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            // Query to fetch all events
            String query = "select * from events";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // Extract event fields from the result set
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");
                int clubId = resultSet.getInt("club_id");
                String userId = resultSet.getString("created_by");
                String category = resultSet.getString("category");
                int maxParticipants = resultSet.getInt("max_participants");
                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                double ticketPrice = resultSet.getDouble("ticket_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");
                String approvalStatus = resultSet.getString("approval_status");
                String completionStatus = resultSet.getString("completion_status");

                // Insert the event into the linked list
                eventList.insert(new Event(eventId,eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime , ticketPrice, discountApplicable,approvalStatus,completionStatus,category));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * Retrieves all events with a specific approval status (e.g., "approved", "pending").
     *
     * @param statusOfEvent the approval status to filter events by
     * @return MyEventLL - a linked list of Event objects with the given approval status
     */
    public MyEventLL getEventList(String statusOfEvent){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            // Query to fetch events by approval status
            String query = "select * from events where approval_status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,statusOfEvent);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // Extract event fields from the result set
                int eventId=resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");
                int clubId = resultSet.getInt("club_id");
                String userId = resultSet.getString("created_by");
                String category = resultSet.getString("category");
                int maxParticipants = resultSet.getInt("max_participants");
                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                double ticketPrice = resultSet.getDouble("ticket_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");
                String approvalStatus = resultSet.getString("approval_status");
                String completionStatus = resultSet.getString("completion_status");

                // Insert the event into the linked list
                eventList.insert(new Event(eventId,eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime , ticketPrice, discountApplicable,approvalStatus,completionStatus,category));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * Updates the approval status of a specific event in the database.
     *
     * @param approvalStatus the new approval status to set (e.g., "approved", "rejected")
     * @param eventId the ID of the event to update
     * @return true if the update was successful, false otherwise
     */
    public boolean approvalStatusUpdate(String approvalStatus,int eventId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Prepare update statement for event approval status
            String query = "UPDATE events SET approval_status = ? WHERE event_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, approvalStatus);
            preparedStatement.setInt(2, eventId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the admin details for a given user, if the user is an admin.
     *
     * @param user the User object containing user identification information
     * @return Admin - an Admin object populated with admin details, or null if not found or not an admin
     */
    public static Admin getAdmin(User user) {
        if (user == null || user.getUserId() == null) return null;

        String query = "SELECT user_id, name, email, password, role " +
                "FROM users " +
                "WHERE user_id = ? AND role = 'ADMIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // or use Logger
        }

        return null; // not found or not admin
    }

}
