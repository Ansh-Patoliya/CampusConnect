package com.eventApp.Model;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an event in the CampusConnect system.
 * 
 * Events are activities organized by clubs that students can register for.
 * Each event has comprehensive details including scheduling, venue, pricing,
 * and approval status. Events go through an approval workflow before being
 * available to students.
 * 
 * Event States:
 * - Pending: Waiting for administrator approval
 * - Approved: Available for student registration
 * - Rejected: Not approved by administrators
 * - Completed: Event has finished
 * - Cancelled: Event was cancelled
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class Event {
    
    /** Unique identifier for the event */
    private int eventId;
    
    /** Name of the event */
    private String eventName;
    
    /** Venue or location where the event will be held */
    private String venue;
    
    /** Detailed description of the event */
    private String description;
    
    /** Current approval status (Pending, Approved, Rejected) */
    private String approvalStatus;
    
    /** Current completion status (Not Completed, Completed, Cancel) */
    private String completionStatus;
    
    /** ID of the user who created the event */
    private String userId;
    
    /** Category of the event */
    private String category;
    
    /** Maximum number of participants allowed */
    private int maxParticipants;
    
    /** ID of the club organizing the event */
    private int clubId;
    
    /** Price of the event ticket */
    private double ticketPrice;
    
    /** Whether discount is available for this event */
    private boolean discountApplicable;
    
    /** Current number of registered participants */
    private int registeredCount;
    
    /** Date when the event will occur */
    LocalDate eventDate;
    
    /** Start time of the event */
    LocalTime startTime;
    
    /** End time of the event */
    LocalTime endTime;
    
    /** Static counter for generating event IDs */
    static int eventCount = 1;

    /**
     * Constructs a new Event with basic details for creation.
     * 
     * This constructor is used when creating a new event. The approval status
     * is automatically set to "pending" and completion status to "not_completed".
     * 
     * @param eventName        name of the event
     * @param description      detailed description of the event
     * @param venue            location where the event will be held
     * @param clubId           ID of the club organizing the event
     * @param userId           ID of the user creating the event
     * @param maxParticipants  maximum number of participants allowed
     * @param eventDate        date when the event will occur
     * @param startTime        start time of the event
     * @param endTime          end time of the event
     * @param ticketPrice      price of the event ticket
     * @param discountApplicable whether discount is available
     */
    public Event(String eventName, String description, String venue, int clubId, String userId, 
                 int maxParticipants, LocalDate eventDate, LocalTime startTime, LocalTime endTime, 
                 double ticketPrice, boolean discountApplicable) {
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
    }

    /**
     * Constructs a complete Event with all details.
     * 
     * This constructor is typically used when loading events from the database
     * where all fields including status information are already known.
     * 
     * @param eventId           unique identifier for the event
     * @param eventName         name of the event
     * @param description       detailed description of the event
     * @param venue             location where the event will be held
     * @param clubId            ID of the club organizing the event
     * @param userId            ID of the user who created the event
     * @param maxParticipants   maximum number of participants allowed
     * @param eventDate         date when the event will occur
     * @param startTime         start time of the event
     * @param endTime           end time of the event
     * @param ticketPrice       price of the event ticket
     * @param discountApplicable whether discount is available
     * @param approvalStatus    current approval status
     * @param completionStatus  current completion status
     * @param category          category of the event
     */
    public Event(int eventId, String eventName, String description, String venue, int clubId, 
                 String userId, int maxParticipants, LocalDate eventDate, LocalTime startTime, 
                 LocalTime endTime, double ticketPrice, boolean discountApplicable, 
                 String approvalStatus, String completionStatus, String category) {
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

    /**
     * Constructs an Event for reporting purposes.
     * 
     * This constructor is used for creating event reports and analytics
     * with essential information.
     * 
     * @param eventId           unique identifier for the event
     * @param eventName         name of the event
     * @param clubId            ID of the club organizing the event
     * @param venue             location where the event was held
     * @param ticketPrice       price of the event ticket
     * @param eventDate         date when the event occurred
     * @param startTime         start time of the event
     * @param endTime           end time of the event
     * @param totalParticipants total number of participants who attended
     */
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

    /**
     * Gets the unique identifier of the event.
     * 
     * @return the event ID
     */
    public int getEventId() { 
        return eventId; 
    }

    /**
     * Gets the name of the event.
     * 
     * @return the event name
     */
    public String getEventName() { 
        return eventName; 
    }

    /**
     * Gets the venue where the event will be held.
     * 
     * @return the venue
     */
    public String getVenue() { 
        return venue; 
    }

    /**
     * Gets the date when the event will occur.
     * 
     * @return the event date
     */
    public LocalDate getEventDate() { 
        return eventDate; 
    }

    /**
     * Gets the start time of the event.
     * 
     * @return the start time
     */
    public LocalTime getStartTime() { 
        return startTime; 
    }

    /**
     * Gets the end time of the event.
     * 
     * @return the end time
     */
    public LocalTime getEndTime() { 
        return endTime; 
    }

    /**
     * Gets the maximum number of participants allowed.
     * 
     * @return the maximum participants
     */
    public int getMaxParticipants() { 
        return maxParticipants; 
    }

    /**
     * Gets the ID of the club organizing the event.
     * 
     * @return the club ID
     */
    public int getClubId() { 
        return clubId; 
    }

    /**
     * Sets the ID of the club organizing the event.
     * 
     * @param clubId the club ID to set
     */
    public void setClubId(int clubId) { 
        this.clubId = clubId; 
    }

    /**
     * Sets the maximum number of participants allowed.
     * 
     * @param maxParticipants the maximum participants to set
     */
    public void setMaxParticipants(int maxParticipants) { 
        this.maxParticipants = maxParticipants; 
    }

    /**
     * Sets the unique identifier of the event.
     * 
     * @param eventId the event ID to set
     */
    public void setEventId(int eventId) { 
        this.eventId = eventId; 
    }

    /**
     * Sets the name of the event.
     * 
     * @param eventName the event name to set
     */
    public void setEventName(String eventName) { 
        this.eventName = eventName; 
    }

    /**
     * Sets the venue where the event will be held.
     * 
     * @param venue the venue to set
     */
    public void setVenue(String venue) { 
        this.venue = venue; 
    }

    /**
     * Sets the end time of the event.
     * 
     * @param endTime the end time to set
     */
    public void setEndTime(LocalTime endTime) { 
        this.endTime = endTime; 
    }

    /**
     * Sets the start time of the event.
     * 
     * @param startTime the start time to set
     */
    public void setStartTime(LocalTime startTime) { 
        this.startTime = startTime; 
    }

    /**
     * Sets the date when the event will occur.
     * 
     * @param eventDate the event date to set
     */
    public void setEventDate(LocalDate eventDate) { 
        this.eventDate = eventDate; 
    }

    /**
     * Gets the detailed description of the event.
     * 
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the detailed description of the event.
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the ID of the user who created the event.
     * 
     * @return the user ID of the event creator
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who created the event.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the ticket price for the event.
     * 
     * @return the ticket price
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the ticket price for the event.
     * 
     * @param ticketPrice the ticket price to set
     */
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Checks if discount is applicable for this event.
     * 
     * @return true if discount is applicable, false otherwise
     */
    public boolean isDiscountApplicable() {
        return discountApplicable;
    }

    /**
     * Sets whether discount is applicable for this event.
     * 
     * @param discountApplicable true if discount should be applicable
     */
    public void setDiscountApplicable(boolean discountApplicable) {
        this.discountApplicable = discountApplicable;
    }

    /**
     * Gets the current number of registered participants.
     * 
     * Note: This method has incorrect parameter naming in the original code.
     * It should be renamed to getRegisteredCount() for consistency.
     * 
     * @param registeredCount not used (parameter should be removed)
     * @return the current registered count
     */
    public static int getRegisteredCount(int registeredCount) {
        return registeredCount;
    }

    /**
     * Sets the current number of registered participants.
     * 
     * @param registeredCount the registered count to set
     */
    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }

    /**
     * Creates a unique event ID.
     * 
     * @return a unique event ID string
     * @deprecated This method should be replaced with database-generated IDs
     */
    public String createEventID(){
        return "E" + eventCount++;
    }

    /**
     * Gets the current approval status of the event.
     * 
     * @return the approval status (Pending, Approved, Rejected)
     */
    public String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * Sets the approval status of the event.
     * 
     * @param approvalStatus the approval status to set
     */
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    /**
     * Gets the current completion status of the event.
     * 
     * @return the completion status (Not Completed, Completed, Cancel)
     */
    public String getCompletionStatus() {
        return completionStatus;
    }

    /**
     * Sets the completion status of the event.
     * 
     * @param completionStatus the completion status to set
     */
    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    /**
     * Gets the category of the event.
     * 
     * @return the event category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the event.
     * 
     * @param category the event category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Checks if the event is currently approved and available for registration.
     * 
     * @return true if the event is approved, false otherwise
     */
    public boolean isApproved() {
        return "Approved".equals(this.approvalStatus);
    }
    
    /**
     * Checks if the event has been completed.
     * 
     * @return true if the event is completed, false otherwise
     */
    public boolean isCompleted() {
        return "Completed".equals(this.completionStatus);
    }
    
    /**
     * Checks if the event has available spots for registration.
     * 
     * @return true if spots are available, false if the event is full
     */
    public boolean hasAvailableSpots() {
        return this.registeredCount < this.maxParticipants;
    }

    /**
     * Returns a string representation of the event.
     * 
     * @return a string containing event date and category information
     */
    @Override
    public String toString() {
        return "Event{" +
                "eventDate=" + eventDate +
                ", category='" + category + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventId=" + eventId +
                '}';
    }
}
