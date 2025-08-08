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

    public List<Student> getParticipantList(int eventId) {
        List<Student> participantList = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "select u.user_id, u.name, s.department, s.semester " +
                    "from event_registration er " +
                    "inner join users u ON er.user_id = u.user_id " +
                    "inner join students s ON u.user_id = s.student_id " +
                    "where er.event_id = ? order by u.name";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                participantList.add(new Student(resultSet.getString("user_id"), resultSet.getString("name"), null, null,null,
                        resultSet.getString("department"),resultSet.getInt("semester"), null));
            }
            return participantList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
