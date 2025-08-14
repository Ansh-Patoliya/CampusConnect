package com.eventApp.Model;

public class Notification {

    private int id;
    private String userId;
    private String message;
    private boolean isRead;

    public Notification(String userId, String message, boolean isRead) {

        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}