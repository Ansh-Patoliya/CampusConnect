package com.eventApp.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private String eventName, venue, description, approvalStatus,completionStatus, clubId, userId;
    private int maxParticipants,eventId;
    private double ticketPrice;
    private boolean discountApplicable;
    private static int registeredCount;
    LocalDate eventDate;
    LocalTime startTime, endTime;
    static int eventCount = 1;

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
        this.approvalStatus = "pending";
        this.completionStatus = "not_completed";
        this.ticketPrice= ticketPrice;
        this.discountApplicable= discountApplicable;
        //get club head's id from clubId and assign
    }

    public Event(int eventId,String eventName, String description, String venue,String clubId,String userId, int maxParticipants, LocalDate eventDate, LocalTime startTime,LocalTime endTime ,double ticketPrice,boolean discountApplicable,String approvalStatus,String completionStatus) {
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
    }

    //make method to get count of registrations

    public int getEventId() { return eventId; }

    public String getEventName() { return eventName; }

    public String getVenue() { return venue; }

    public LocalDate getEventDate() { return eventDate; }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public int getMaxParticipants() { return maxParticipants; }

    public String getClubId() { return clubId; }

    public void setClubId(String clubId) { this.clubId = clubId; }

    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }

    public void setEventId(int eventId) { this.eventId = eventId; }

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

    public String createEventID(){
        return "E" +eventCount++;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", venue='" + venue + '\'' +
                ", description='" + description + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", completionStatus='" + completionStatus + '\'' +
                ", clubId='" + clubId + '\'' +
                ", userId='" + userId + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", ticketPrice=" + ticketPrice +
                ", discountApplicable=" + discountApplicable +
                ", eventDate=" + eventDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
