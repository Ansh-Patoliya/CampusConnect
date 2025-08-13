package com.eventApp.Service;

import com.eventApp.DAO.EventDAO;
import com.eventApp.DAO.StudentDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;

import java.util.Comparator;
import java.util.List;

/**
 * Service class for student-related operations in the CampusConnect system.
 * 
 * This service provides business logic for student users, including event discovery,
 * personalized event recommendations, and student profile management. It implements
 * intelligent event sorting based on student interests and manages the interaction
 * between students and the event system.
 * 
 * Key Features:
 * - Personalized event recommendations based on student interests
 * - Intelligent event sorting by interest relevance and date
 * - Student profile retrieval and management
 * - Custom data structure integration for efficient event browsing
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class StudentService {

    /** Custom linked list data structure for managing student events */
    MyEventLL myEventLL;

    /** Data access object for event operations */
    EventDAO eventDAO = new EventDAO();
    
    /** Data access object for student operations */
    StudentDAO studentDAO = new StudentDAO();

    /**
     * Retrieves student information for a given user.
     * 
     * This method fetches the extended student information (department, semester, interests)
     * for a user with the STUDENT role.
     * 
     * @param user the user object for whom to retrieve student information
     * @return Student object containing academic and personal information
     */
    public Student getStudentByUser(User user) {
        return StudentDAO.getStudent(user);
    }

    /**
     * Creates a personalized and sorted event list for the current student.
     * 
     * This method implements intelligent event sorting based on:
     * 1. Student's interest relevance (events matching interests appear first)
     * 2. Event date (earlier events within same interest level appear first)
     * 
     * The algorithm:
     * - Retrieves all approved events
     * - Gets current student's interest list
     * - Sorts events by interest relevance (matched interests get lower index values)
     * - Secondary sort by event date for events with same interest relevance
     * - Returns events in a custom linked list data structure
     * 
     * @return MyEventLL containing sorted events personalized for the current student
     */
    public MyEventLL sortEventList() {
        // Get all approved events
        List<Event> eventList = eventDAO.getEventList("Approved");
        
        // Get current user's interests
        User currentUser = CurrentUser.getCurrentUser();
        List<String> interestList = studentDAO.getInterestList(currentUser.getUserId());
        
        // Sort events by interest relevance and date
        eventList.sort(Comparator.comparingInt((Event e) -> {
            int index = interestList.indexOf(e.getCategory());
            // Events not matching interests get maximum value (appear last)
            return index == -1 ? Integer.MAX_VALUE : index;
        }).thenComparing(Event::getEventDate));

        // Convert to custom linked list data structure
        MyEventLL myEventLL = new MyEventLL();
        for (Event event : eventList) {
            myEventLL.addLast(event);
        }
        return myEventLL;
    }

    /**
     * Loads and prepares personalized event list for the current student.
     * 
     * This method initializes the student's event browsing experience by
     * loading and sorting events based on their preferences. It should be
     * called when a student logs in or when refreshing their event feed.
     */
    public void loadStudentEvents() {
        myEventLL = sortEventList();
    }

    /**
     * Retrieves the current event being viewed by the student.
     * 
     * This method is part of the event browsing workflow, allowing students
     * to navigate through their personalized event list. It returns the
     * event currently at the head of the linked list.
     * 
     * @return Event object representing the current event, or null if no events available
     */
    public Event viewCurrentEvent() {
        if (myEventLL.isEmpty()) {
            return null; // No events available
        }
        return myEventLL.viewCurrentEvent();
    }

    /**
     * Registers a student for a specific event.
     * 
     * TODO: This method needs to be implemented to handle:
     * - Event capacity validation
     * - Duplicate registration prevention
     * - Payment processing (if required)
     * - Registration confirmation
     * 
     * @param studentId the ID of the student registering
     * @param eventId   the ID of the event to register for
     * @return true if registration successful, false otherwise
     */
    // public boolean registerStudentToEvent(String studentId, String eventId)

    /**
     * Checks if the student's event list is empty.
     * 
     * @return true if no events are available for the student, false otherwise
     */
    public boolean hasEvents() {
        return myEventLL != null && !myEventLL.isEmpty();
    }

    /**
     * Gets the total number of events available to the student.
     * 
     * @return number of events in the student's personalized list
     */
    public int getEventCount() {
        if (myEventLL == null) {
            return 0;
        }
        return myEventLL.size();
    }
}

    public Event getNextEvent() {
        if (myEventLL.isEmpty()) {
            return null; // or throw an exception
        }
        return myEventLL.viewNextEvent();
    }

    public Event getPreviousEvent() {
        if (myEventLL.isEmpty()) {
            return null; // or throw an exception
        }
        return myEventLL.viewPreviousEvent();
    }

    public  List<Event> myEventListByDate(String userId){
        List<Event> myEvents = eventDAO.getMyEventList(userId);
        myEvents.sort(Comparator.comparing(Event::getEventDate));
        return myEvents;
    }

    public  List<Event> myEventListByPrice(String userId){
        List<Event> myEvents = eventDAO.getMyEventList(userId);
        myEvents.sort(Comparator.comparing(Event::getTicketPrice));
        return myEvents;
    }
}
