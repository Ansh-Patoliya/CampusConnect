package com.eventApp.DAO;

import com.eventApp.DataStructures.CircularLL;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.*;
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
     * Creates a new event in the database for a specific club.
     *
     * @param event the Event object containing all event details to be inserted
     * @throws SQLException           if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws ValidationException    if event creation fails at database level
     */
    public void createEvent(Event event) throws SQLException, ClassNotFoundException, ValidationException {
        // SQL query to insert new event with all required fields
        String sql = "INSERT INTO events (club_id,event_name,description,event_date,ticket_price,created_by,discount_available,start_time,end_time,venue,max_participants,category) VALUES ( ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        // Set all event parameters in the prepared statement
        preparedStatement.setInt(1, event.getClubId());
        preparedStatement.setString(2, event.getEventName());
        preparedStatement.setString(3, event.getDescription());
        preparedStatement.setDate(4, Date.valueOf(event.getEventDate()));
        preparedStatement.setDouble(5, event.getTicketPrice());
        preparedStatement.setString(6, event.getUserId());
        preparedStatement.setBoolean(7, event.isDiscountApplicable());
        preparedStatement.setTime(8, Time.valueOf(event.getStartTime()));
        preparedStatement.setTime(9, Time.valueOf(event.getEndTime()));
        preparedStatement.setString(10, event.getVenue());
        preparedStatement.setInt(11, event.getMaxParticipants());
        preparedStatement.setString(12, event.getCategory());

        int r = preparedStatement.executeUpdate();
        // Validate that the event was successfully inserted
        if (r < 0) {
            throw new ValidationException("Event creation failed. Please try again.");
        }
    }

    /**
     * Retrieves events filtered by both approval status and completion status.
     * Used to fetch events that match specific workflow states.
     *
     * @param approvalStatus   the approval status to filter by (e.g., "approved", "pending")
     * @param completionStatus the completion status to filter by (e.g., "completed", "not completed")
     * @return List<Event> containing all events matching both status criteria
     */
    public List<Event> getEventList(String approvalStatus, String completionStatus) {
        List<Event> eventList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to filter events by both approval and completion status
            String query = "select * from events where approval_status = ? and completion_status=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, approvalStatus);
            preparedStatement.setString(2, completionStatus);

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

                double ticketPrice = resultSet.getDouble("ticket_price");
                double discountedPrice = resultSet.getDouble("discounted_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");

                // Re-read status from database to ensure consistency
                approvalStatus = resultSet.getString("approval_status");
                completionStatus = resultSet.getString("completion_status");

                Event event = new Event(eventId, eventName, description, venue, clubId, userId, maxParticipants,
                        eventDate, startTime, endTime, ticketPrice, discountApplicable, approvalStatus, completionStatus, category);
                event.setDiscountedPrice(discountedPrice);
                eventList.add(event);

            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * Retrieves all events from the database, regardless of approval or completion status.
     *
     * @return MyEventLL - a linked list of all Event objects from the database
     */
    public MyEventLL getEventList() {
        MyEventLL eventList = new MyEventLL();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch all events
            String query = "select * from events";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                eventList.insert(new Event(eventId, eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime, ticketPrice, discountApplicable, approvalStatus, completionStatus, category));
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
    public MyEventLL getEventList(String statusOfEvent) {
        MyEventLL eventList = new MyEventLL();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch events by approval status
            String query = "select * from events where approval_status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statusOfEvent);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                eventList.insert(new Event(eventId, eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime, ticketPrice, discountApplicable, approvalStatus, completionStatus, category));
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
     * @param eventId        the ID of the event to update
     * @return true if the update was successful, false otherwise
     */
    public boolean approvalStatusUpdate(String approvalStatus, int eventId) {
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
                double ticketPrice = resultSet.getDouble("ticket_price");
                double discountedPrice = resultSet.getDouble("discounted_price");

                Event e = new Event(
                        resultSet.getInt("event_id"),
                        resultSet.getString("event_name"),
                        resultSet.getInt("club_id"),
                        resultSet.getString("venue"),
                        ticketPrice,
                        resultSet.getDate("event_date").toLocalDate(),
                        resultSet.getTime("start_time").toLocalTime(),
                        resultSet.getTime("end_time").toLocalTime(),
                        resultSet.getInt("max_participants")
                );
                e.setDiscountedPrice(discountedPrice);
                myEvents.add(e);
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
     * @throws SQLException             if database operation fails
     * @throws ClassNotFoundException   if database driver is not found
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
     * @throws SQLException             if database operation fails
     * @throws ClassNotFoundException   if database driver is not found
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
     * @throws SQLException             if database operation fails
     * @throws ClassNotFoundException   if database driver is not found
     * @throws DatabaseExceptionHandler if the update operation fails
     */
    public void updateEvent(Event currentEvent) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

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

        int r = preparedStatement.executeUpdate();
        // Validate that the update operation was successful
        if (r < 0) {
            throw new DatabaseExceptionHandler("Failed to update the event. Please try again later.");
        }
    }
}
