package com.eventApp.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private String eventId, eventName, venue, description, status, clubId, userId;
    private int maxParticipants;
    private double ticketPrice;
    private boolean discountApplicable;
    private static int registeredCount;
    LocalDate eventDate;
    LocalTime startTime, endTime;

    public Event(String eventName, String description, String venue,String clubId,String userId, int maxParticipants, LocalDate eventDate, LocalTime startTime,LocalTime endTime ,double ticketPrice,boolean discountApplicable) {
        this.eventName = eventName;
        this.description = description;
        this.venue = venue;
        this.maxParticipants = maxParticipants;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clubId = clubId;
        this.userId = userId;
        this.status = "pending";
        this.ticketPrice= ticketPrice;
        this.discountApplicable= discountApplicable;
        //get club head's id from clubId and assign
    }
    //make method to get count of registrations

    public String getEventId() { return eventId; }

    public String getEventName() { return eventName; }

    public String getVenue() { return venue; }

    public LocalDate getEventDate() { return eventDate; }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public int getMaxParticipants() { return maxParticipants; }

    public String getClubId() { return clubId; }

    public void setClubId(String clubId) { this.clubId = clubId; }

    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }

    public void setEventId(String eventId) { this.eventId = eventId; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public void setVenue(String venue) { this.venue = venue; }

    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public boolean isDiscountApplicable() {
        return discountApplicable;
    }

    public void setDiscountApplicable(boolean discountApplicable) {
        this.discountApplicable = discountApplicable;
    }

    public static int getRegisteredCount() {
        return registeredCount;
    }

    public static void setRegisteredCount(int registeredCount) {
        Event.registeredCount = registeredCount;
    }
}
