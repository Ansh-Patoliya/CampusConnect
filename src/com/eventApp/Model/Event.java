package com.eventApp.Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an Event in the Event Management System.
 * Stores event details like name, date, time, venue, price, status, etc.
 */
public class Event {

    // Basic event details
    private String eventName, venue, description, approvalStatus, completionStatus, userId, category;
    private int maxParticipants, eventId, clubId;
    private double ticketPrice;
    private int registeredCount = 0;
    private boolean discountApplicable;
    LocalDate eventDate;
    LocalTime startTime, endTime;

    // Constructor for creating a new event
    public Event(String eventName, String description, String venue, int clubId, String userId, int maxParticipants,
                 LocalDate eventDate, LocalTime startTime, LocalTime endTime, double ticketPrice,
                 boolean discountApplicable, String category) {
        this.eventName = eventName;
        this.description = description;
        this.venue = venue;
        this.maxParticipants = maxParticipants;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clubId = clubId;
        this.userId = userId;
        this.approvalStatus = "pending";
        this.completionStatus = "not_completed";
        this.ticketPrice = ticketPrice;
        this.discountApplicable = discountApplicable;
        this.category = category;
    }

    // Constructor for loading event data from database
    public Event(int eventId, String eventName, String description, String venue, int clubId, String userId,
                 int maxParticipants, LocalDate eventDate, LocalTime startTime, LocalTime endTime,
                 double ticketPrice, boolean discountApplicable, String approvalStatus,
                 String completionStatus, String category) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.description = description;
        this.approvalStatus = approvalStatus;
        this.completionStatus = completionStatus;
        this.clubId = clubId;
        this.userId = userId;
        this.maxParticipants = maxParticipants;
        this.ticketPrice = ticketPrice;
        this.discountApplicable = discountApplicable;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    // Constructor for loading only required event data from database
    public Event(int eventId, String eventName, int clubId, String venue, Double ticketPrice,
                 LocalDate eventDate, LocalTime startTime, LocalTime endTime, int totalParticipants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.clubId = clubId;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.registeredCount = totalParticipants;
    }

    // Getters and setters for all properties

    public int getEventId() { return eventId; }

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getVenue() { return venue; }

    public void setVenue(String venue) { this.venue = venue; }

    public LocalDate getEventDate() { return eventDate; }

    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getStartTime() { return startTime; }

    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }

    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public int getMaxParticipants() { return maxParticipants; }

    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }

    public void setClubId(int clubId) { this.clubId = clubId; }

    public int getClubId() { return clubId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public double getTicketPrice() { return ticketPrice; }

    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public boolean isDiscountApplicable() { return discountApplicable; }

    public void setDiscountApplicable(boolean discountApplicable) { this.discountApplicable = discountApplicable; }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Overriding to string method
    @Override
    public String toString() {
        return "Event{" +
                "eventDate=" + eventDate +
                ", category=" + category +
                '}';
    }
}
