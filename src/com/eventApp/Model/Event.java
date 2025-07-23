package com.eventApp.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private String eventName, venue, description, approvalStatus,completionStatus, userId,category;
    private int maxParticipants,eventId,clubId;
    private double ticketPrice;
    private boolean discountApplicable;
    private int registeredCount;
    LocalDate eventDate;
    LocalTime startTime, endTime;
    static int eventCount = 1;

    public Event(String eventName, String description, String venue,int clubId,String userId, int maxParticipants, LocalDate eventDate, LocalTime startTime,LocalTime endTime ,double ticketPrice,boolean discountApplicable) {
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

    public Event(int eventId,String eventName, String description, String venue,int clubId,String userId, int maxParticipants, LocalDate eventDate, LocalTime startTime,LocalTime endTime ,double ticketPrice,boolean discountApplicable,String approvalStatus,String completionStatus,String category) {
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

    public Event(int eventId, String eventName, int clubId, String venue, Double ticketPrice,LocalDate eventDate, LocalTime startTime, LocalTime endTime, int totalParticipants) {
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


    //make method to get count of registrations

    public int getEventId() { return eventId; }

    public String getEventName() { return eventName; }

    public String getVenue() { return venue; }

    public LocalDate getEventDate() { return eventDate; }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public int getMaxParticipants() { return maxParticipants; }

    public int getClubId() { return clubId; }

    public void setClubId(int clubId) { this.clubId = clubId; }

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

    public static int getRegisteredCount(int registeredCount) {
        return registeredCount;
    }

    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventDate=" + eventDate +
                "category"+category+
                '}';
    }
}
