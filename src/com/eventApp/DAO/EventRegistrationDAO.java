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

    public List<Student> getParticipantList(String userId) {
        List<Student> participantList = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "SELECT u.user_id, u.name, s.department, s.semester " +
                    "FROM users u INNER JOIN students s ON u.user_id = s.student_id " +
                    "WHERE u.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,"userId");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                participantList.add(new Student(userId, resultSet.getString("name"), null, null,null,
                        resultSet.getString("department"),resultSet.getInt("semester"), null));
            }
            return participantList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
