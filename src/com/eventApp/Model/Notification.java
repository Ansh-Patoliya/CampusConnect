package com.eventApp.Model;

/**
 * Represents a notification sent to a user within the Event App.
 * Contains information about the notification message and its read status.
 */
public class Notification {

    private int id;            // Unique identifier for the notification
    private String userId;     // ID of the user this notification is intended for
    private String message;    // Content of the notification message
    private boolean isRead;    // Flag indicating whether the notification has been read

    /**
     * Constructor to create a Notification with specified user, message, and read status.
     *
     * @param userId  Unique identifier of the user receiving the notification
     * @param message Content of the notification
     * @param isRead  Indicates if the notification has already been read
     */
    public Notification(String userId, String message, boolean isRead) {
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
    }

    /**
     * Retrieves the unique ID of the notification.
     *
     * @return the notification's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for the notification.
     * Assigned by the database.
     *
     * @param id the unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the user ID associated with this notification.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for this notification.
     *
     * @param userId the user identifier to assign
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the notification message content.
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the notification message.
     *
     * @param message the notification message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the notification has been read.
     *
     * @return true if the notification is read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Updates the read status of the notification.
     *
     * @param read boolean flag to set the notification as read or unread
     */
    public void setRead(boolean read) {
        isRead = read;
    }
}
