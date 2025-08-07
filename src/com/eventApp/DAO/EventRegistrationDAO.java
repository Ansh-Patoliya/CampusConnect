package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRegistrationDAO {
    public void registerForEvent(int eventId, String userId) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO event_registration (event_id, user_id) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, eventId);
        ps.setString(2, userId);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected < 0)
            throw new DatabaseExceptionHandler("Registration failed. Please try again.");

    }
}
