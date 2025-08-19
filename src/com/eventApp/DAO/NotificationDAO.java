package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Notification;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for notification-related database operations.
 * Handles creation, reading status updates, and retrieval of user notifications.
 */
public class NotificationDAO {

    /**
     * Creates a new notification for a specific user in the database.
     * The notification will be marked as unread by default.
     *
     * @param userId  the unique identifier of the user to receive the notification
     * @param message the notification message content
     * @throws SQLException                  if database operation fails
     * @throws ClassNotFoundException       if database driver is not found
     * @throws DatabaseExceptionHandler      if notification creation fails
     */
    public void createNotification(String userId, String message) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

        // Insert new notification record with user ID and message
        String sql = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, message);

        int r = preparedStatement.executeUpdate();
        // Validate that the notification was successfully created
        if (r < 0) {
            throw new DatabaseExceptionHandler("Failed to create notification");
        }
    }

    /**
     * Marks a specific notification as read for a user.
     * Uses both user ID and notification ID to ensure users can only mark their own notifications.
     *
     * @param userId        the unique identifier of the user who owns the notification
     * @param notificationId the unique identifier of the notification to mark as read
     * @throws SQLException                  if database operation fails
     * @throws ClassNotFoundException       if database driver is not found
     * @throws DatabaseExceptionHandler      if the update operation fails
     */
    public void markNotificationAsRead(String userId, int notificationId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

        // Update notification status with security check on both user_id and notification_id
        String sql = "UPDATE notifications SET is_read = TRUE WHERE user_id = ? AND id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setInt(2, notificationId);

        int r = preparedStatement.executeUpdate();
        // Validate that the notification was successfully updated
        if (r < 0) {
            throw new DatabaseExceptionHandler("Failed to mark notification as read");
        }
    }

    /**
     * Retrieves all unread notifications for a specific user.
     * Only returns notifications that have not been marked as read to avoid clutter.
     *
     * @param userId the unique identifier of the user whose notifications to retrieve
     * @return List<Notification> containing all unread notifications for the user
     * @throws SQLException                  if database operation fails
     * @throws ClassNotFoundException       if database driver is not found
     */
    public List<Notification> getNotifications(String userId) throws SQLException, ClassNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        // Query to fetch only unread notifications for the specified user
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        var resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Extract notification fields from the result set
            int id = resultSet.getInt("id");
            String message = resultSet.getString("message");
            boolean isRead = resultSet.getBoolean("is_read");

            // Create Notification object and set the database-generated ID
            Notification notification = new Notification(userId, message, isRead);
            notification.setId(id);
            notifications.add(notification);
        }
        return notifications;
    }
}
