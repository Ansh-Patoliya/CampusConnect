package com.eventApp.Service;

import com.eventApp.DAO.EventRegistrationDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRegistrationService {
    // Current logged-in user
    private User user = CurrentUser.getCurrentUser();
    // DAO for event registration database operations
    private EventRegistrationDAO eventRegistrationDAO = new EventRegistrationDAO();

    /**
     * Registers the current user for an event by eventId.
     * It checks if the event has available slots and if the user
     * is not already registered for the event before proceeding.
     *
     * @param eventId The ID of the event to register for
     * @throws DatabaseExceptionHandler if max participant limit reached or user already registered
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public void registerForEvent(int eventId) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        // Obtain a connection to the database
        Connection connection = DatabaseConnection.getConnection();

        // Check if the event still has available participant slots
        PreparedStatement ps = connection.prepareStatement("select checkcount(?)");
        ps.setInt(1, eventId);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        if (resultSet.getBoolean(1)) {
            // Check if the current user has already registered for this event
            ps = connection.prepareStatement("SELECT checkalreadyregistered(?, ?)");
            ps.setString(1, user.getUserId());
            ps.setInt(2, eventId);
            resultSet = ps.executeQuery();
            resultSet.next();

            // If user already registered, throw exception
            if (!resultSet.getBoolean(1)) {
                throw new DatabaseExceptionHandler("You have already registered for this event.");
            }

            // Proceed with registration
            eventRegistrationDAO.registerForEvent(eventId, user.getUserId());
        } else {
            // If max participant limit reached, throw exception
            throw new DatabaseExceptionHandler("Max participants limit reached for this event.");
        }
    }
}
