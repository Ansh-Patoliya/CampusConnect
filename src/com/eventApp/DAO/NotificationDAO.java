package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Notification;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void createNotification(String userId, String message) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, message);
        int r = preparedStatement.executeUpdate();
        if (r < 0) {
            throw new DatabaseExceptionHandler("Failed to create notification");
        }

    }

    public void markNotificationAsRead(String userId, int notificationId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "UPDATE notifications SET is_read = TRUE WHERE user_id = ? AND id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setInt(2, notificationId);
        int r = preparedStatement.executeUpdate();
        if (r < 0) {
            throw new DatabaseExceptionHandler("Failed to mark notification as read");
        }
    }

    public List<Notification> getNotifications(String userId) throws SQLException, ClassNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String message = resultSet.getString("message");
            boolean isRead = resultSet.getBoolean("is_read");
            Notification notification = new Notification(userId, message, isRead);
            notification.setId(id);
            notifications.add(notification);
        }
        return notifications;
    }
}
