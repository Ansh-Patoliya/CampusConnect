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
    private User user = CurrentUser.getCurrentUser();
    private EventRegistrationDAO eventRegistrationDAO = new EventRegistrationDAO();

    public void registerForEvent(int eventId) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select checkcount(?)");
        ps.setInt(1, eventId);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        if (resultSet.getBoolean(1)) {
            ps = connection.prepareStatement("SELECT checkalreadyregistered(?, ?)");
            ps.setString(1, user.getUserId());
            ps.setInt(2, eventId);
            resultSet = ps.executeQuery();
            resultSet.next();
            if (!resultSet.getBoolean(1))
                throw new DatabaseExceptionHandler("You have already registered for this event.");
            eventRegistrationDAO.registerForEvent(eventId, user.getUserId());
        } else
            throw new DatabaseExceptionHandler("Max participants limit reached for this event.");
    }
}
