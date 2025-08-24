package com.eventApp.Controller;

import com.eventApp.Model.Event;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * REST Controller for event operations.
 * Replaces JavaFX event management functionality with REST API endpoints.
 */
@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {

    /**
     * Get all events endpoint.
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        // TODO: Implement actual event retrieval using EventService
        // For now, return sample events to test the endpoint
        List<Event> events = new ArrayList<>();
        events.add(new Event(
            "Sample Tech Conference", 
            "A conference about latest technologies", 
            "Main Auditorium", 
            1, 
            "user1", 
            100,
            LocalDate.now().plusDays(30), 
            LocalTime.of(9, 0), 
            LocalTime.of(17, 0), 
            50.0,
            true, 
            "Technology"
        ));
        
        return ResponseEntity.ok(events);
    }

    /**
     * Get event by ID endpoint.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable int eventId) {
        // TODO: Implement actual event retrieval by ID using EventService
        Event event = new Event(
            "Sample Event", 
            "Event description", 
            "Venue", 
            1, 
            "user1", 
            50,
            LocalDate.now().plusDays(15), 
            LocalTime.of(10, 0), 
            LocalTime.of(12, 0), 
            25.0,
            false, 
            "General"
        );
        event.setEventId(eventId);
        
        return ResponseEntity.ok(event);
    }

    /**
     * Create new event endpoint.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEvent(@RequestBody EventCreateRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        // TODO: Implement actual event creation using EventService
        
        response.put("success", true);
        response.put("message", "Event created successfully");
        response.put("eventId", 123); // Placeholder
        
        return ResponseEntity.ok(response);
    }

    /**
     * Update event endpoint.
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> updateEvent(
            @PathVariable int eventId, 
            @RequestBody EventUpdateRequest request) {
        Map<String, String> response = new HashMap<>();
        
        // TODO: Implement actual event update using EventService
        
        response.put("message", "Event updated successfully");
        response.put("status", "success");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Register for event endpoint.
     */
    @PostMapping("/{eventId}/register")
    public ResponseEntity<Map<String, String>> registerForEvent(
            @PathVariable int eventId, 
            @RequestBody Map<String, String> registrationData) {
        Map<String, String> response = new HashMap<>();
        
        // TODO: Implement actual event registration using EventService
        
        response.put("message", "Successfully registered for event");
        response.put("status", "success");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get events by club endpoint.
     */
    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Event>> getEventsByClub(@PathVariable int clubId) {
        // TODO: Implement actual event retrieval by club using EventService
        List<Event> events = new ArrayList<>();
        
        return ResponseEntity.ok(events);
    }

    /**
     * Data Transfer Object for event creation requests.
     */
    public static class EventCreateRequest {
        private String eventName;
        private String description;
        private String venue;
        private int clubId;
        private String userId;
        private int maxParticipants;
        private String eventDate; // Will be parsed to LocalDate
        private String startTime; // Will be parsed to LocalTime
        private String endTime; // Will be parsed to LocalTime
        private double ticketPrice;
        private boolean discountApplicable;
        private String category;

        // Getters and setters
        public String getEventName() { return eventName; }
        public void setEventName(String eventName) { this.eventName = eventName; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getVenue() { return venue; }
        public void setVenue(String venue) { this.venue = venue; }
        public int getClubId() { return clubId; }
        public void setClubId(int clubId) { this.clubId = clubId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public int getMaxParticipants() { return maxParticipants; }
        public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
        public String getEventDate() { return eventDate; }
        public void setEventDate(String eventDate) { this.eventDate = eventDate; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
        public double getTicketPrice() { return ticketPrice; }
        public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }
        public boolean isDiscountApplicable() { return discountApplicable; }
        public void setDiscountApplicable(boolean discountApplicable) { this.discountApplicable = discountApplicable; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }

    /**
     * Data Transfer Object for event update requests.
     */
    public static class EventUpdateRequest {
        private String eventName;
        private String description;
        private String venue;
        private int maxParticipants;
        private String eventDate;
        private String startTime;
        private String endTime;
        private double ticketPrice;
        private boolean discountApplicable;
        private String category;

        // Getters and setters (similar to EventCreateRequest)
        public String getEventName() { return eventName; }
        public void setEventName(String eventName) { this.eventName = eventName; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getVenue() { return venue; }
        public void setVenue(String venue) { this.venue = venue; }
        public int getMaxParticipants() { return maxParticipants; }
        public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
        public String getEventDate() { return eventDate; }
        public void setEventDate(String eventDate) { this.eventDate = eventDate; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
        public double getTicketPrice() { return ticketPrice; }
        public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }
        public boolean isDiscountApplicable() { return discountApplicable; }
        public void setDiscountApplicable(boolean discountApplicable) { this.discountApplicable = discountApplicable; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}