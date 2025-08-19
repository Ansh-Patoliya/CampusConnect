package com.eventApp.DAO;

import com.eventApp.DataStructures.CircularLL;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for event-related database operations.
 * Handles CRUD operations for events, event filtering, and event management workflows.
 */
public class EventDAO {

    /**
     * Retrieves events filtered by both approval status and completion status.
     * Used to fetch events that match specific workflow states.
     *
     * @param approvalStatus the approval status to filter by (e.g., "approved", "pending")
     * @param completionStatus the completion status to filter by (e.g., "completed", "not completed")
     * @return List<Event> containing all events matching both status criteria
     */
    public List<Event> getEventList(String approvalStatus,String completionStatus) {
        List<Event> eventList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to filter events by both approval and completion status
            String query = "select * from events where approval_status = ? and completion_status=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, approvalStatus);
            preparedStatement.setString(2,completionStatus);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Extract all event fields from the result set
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");
                String category = resultSet.getString("category");

                int clubId = resultSet.getInt("club_id");
                String userId = resultSet.getString("created_by");

                int maxParticipants = resultSet.getInt("max_participants");

                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();

                double ticketPrice = resultSet.getDouble("discounted_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");

                // Re-read status from database to ensure consistency
                approvalStatus = resultSet.getString("approval_status");
                completionStatus = resultSet.getString("completion_status");

                eventList.add(new Event(eventId, eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime, ticketPrice, discountApplicable, approvalStatus, completionStatus,category));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * Retrieves names of events associated with clubs where the user is a member.
     * Uses JOIN query to find events through club membership relationship.
     *
     * @param userId the ID of the user whose club events to retrieve
     * @return List<String> containing event names accessible to the user
     */
    public List<String> getEventNames(String userId) {
        List<String> eventNameList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // JOIN query to get events from clubs where user is a member
            String query = "SELECT e.event_name from events e INNER join club_members cm on cm.club_id=e.club_id where cm.member_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                eventNameList.add(resultSet.getString("event_name"));
            }
        } catch (Exception e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventNameList;
    }

    /**
     * Retrieves the event ID for a given event name.
     * Assumes event names are unique in the system.
     *
     * @param eventName the name of the event to search for
     * @return int event ID, or -1 if event not found
     */
    public int getEventIdBy(String eventName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "select event_id from events where event_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, eventName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("event_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return -1; // Return -1 if no event found
    }

    /**
     * Retrieves events that a user has registered for and are not yet completed.
     * Uses JOIN with event_registration table to find user's registered events.
     * Returns minimal event information needed for user participation view.
     *
     * @param userId the ID of the user whose registered events to retrieve
     * @return List<Event> containing simplified Event objects for registered events
     */
    public List<Event> getMyEventList(String userId) {
        List<Event> myEvents = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // JOIN query to get user's registered events that are still active
            String query = "select * from events e inner join event_registration er on e.event_id = er.event_id where er.user_id = ? and e.completion_status = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, "Not Completed");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create simplified Event objects with only essential information for user view
                myEvents.add(new Event(0, resultSet.getString("event_name"), resultSet.getInt("club_id"), null,
                        resultSet.getDouble("discounted_price"), resultSet.getDate("event_date").toLocalDate(), null, null, 0));
            }
        } catch (Exception e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return myEvents;
    }

    /**
     * Retrieves all events for a specific club using a circular linked list data structure.
     * Used for club-specific event management and display.
     *
     * @param clubId the unique identifier of the club
     * @return CircularLL containing all Event objects for the specified club
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if data retrieval fails
     */
    public CircularLL getEventListByClubId(int clubId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        CircularLL ll = new CircularLL();
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM events WHERE club_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, clubId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Extract all event fields from the result set
            int eventId = resultSet.getInt("event_id");
            String eventName = resultSet.getString("event_name");
            String description = resultSet.getString("description");
            String venue = resultSet.getString("venue");
            String category = resultSet.getString("category");

            String userId = resultSet.getString("created_by");
            int maxParticipants = resultSet.getInt("max_participants");

            LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
            LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
            LocalTime endTime = resultSet.getTime("end_time").toLocalTime();

            double ticketPrice = resultSet.getDouble("discounted_price");
            boolean discountApplicable = resultSet.getBoolean("discount_available");

            String approvalStatus = resultSet.getString("approval_status");
            String completionStatus = resultSet.getString("completion_status");

            // Add complete Event object to the circular linked list
            ll.add(new Event(eventId, eventName, description, venue, clubId, userId, maxParticipants,
                    eventDate, startTime, endTime, ticketPrice, discountApplicable, approvalStatus, completionStatus, category));
        }
        return ll;
    }

    /**
     * Cancels an event by updating its completion status to "Cancel".
     * This is a soft delete operation - the event record remains in the database.
     *
     * @param eventId the unique identifier of the event to cancel
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if the cancellation operation fails
     */
    public void cancelEvent(int eventId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        String query = "UPDATE events SET completion_status = ? WHERE event_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "Cancel");
        preparedStatement.setInt(2, eventId);
        int i = preparedStatement.executeUpdate();

        // Validate that the update operation was successful
        if (i < 0)
            throw new DatabaseExceptionHandler("Failed to cancel the event. Please try again later.");
    }

    /**
     * Updates all modifiable fields of an existing event.
     * Performs a comprehensive update of event details while preserving the event ID.
     *
     * @param currentEvent the Event object containing updated information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if the update operation fails
     */
    public void updateEvent(Event currentEvent) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection=DatabaseConnection.getConnection();
        
        // Comprehensive UPDATE query for all modifiable event fields
        String query = "UPDATE events SET event_name = ?, description = ?, venue = ?, event_date = ?, start_time = ? , end_time = ?, ticket_price = ?, discount_available = ?, category = ? , max_participants = ? , club_id = ? , created_by = ? where event_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set all event parameters in the prepared statement
        preparedStatement.setString(1, currentEvent.getEventName());
        preparedStatement.setString(2, currentEvent.getDescription());
        preparedStatement.setString(3, currentEvent.getVenue());
        preparedStatement.setDate(4, java.sql.Date.valueOf(currentEvent.getEventDate()));
        preparedStatement.setTime(5, java.sql.Time.valueOf(currentEvent.getStartTime()));
        preparedStatement.setTime(6, java.sql.Time.valueOf(currentEvent.getEndTime()));
        preparedStatement.setDouble(7, currentEvent.getTicketPrice());
        preparedStatement.setBoolean(8, currentEvent.isDiscountApplicable());
        preparedStatement.setString(9, currentEvent.getCategory());
        preparedStatement.setInt(10, currentEvent.getMaxParticipants());
        preparedStatement.setInt(11, currentEvent.getClubId());
        preparedStatement.setString(12, currentEvent.getUserId());
        preparedStatement.setInt(13, currentEvent.getEventId()); // WHERE clause parameter

        int r=preparedStatement.executeUpdate();
        // Validate that the update operation was successful
        if (r<0){
            throw new DatabaseExceptionHandler("Failed to update the event. Please try again later.");
        }
        
    }
}
