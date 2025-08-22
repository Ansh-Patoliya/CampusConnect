package com.eventApp.Service;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import java.util.List;

/**
 * Interface to manage student related service operations
 * Defines methods to retrieve student data, navigate event lists,
 * and access registered or sorted events.
 */
public interface StudentService {

    /**
     * Retrieves a Student object corresponding to the given User.
     *
     * @param user The User object to look up
     * @return The matching Student object
     */
    Student getStudentByUser(User user) ;

    /**
     * Sorts the list of approved and not completed events for the current user.
     * The sorting prioritizes events matching user's interests first,
     * then sorts events by their date.
     *
     * @return A linked list of sorted events (MyEventLL)
     */
    MyEventLL sortEventList() ;

    /**
     * Loads the student's event list by sorting events and storing in myEventLL.
     */
    void loadStudentEvents() ;

    /**
     * Retrieves the current event from the student's event list.
     *
     * @return The current Event or null if the list is empty
     */
    Event viewCurrentEvent() ;

    /**
     * Retrieves the next event from the student's event list.
     *
     * @return The next Event or null if the list is empty
     */
    Event getNextEvent() ;

    /**
     * Retrieves the previous event from the student's event list.
     *
     * @return The previous Event or null if the list is empty
     */
    Event getPreviousEvent() ;

    /**
     * Returns the list of events the student is registered for, sorted by event date.
     *
     * @param userId The student's user ID
     * @return List of Events sorted by date
     */
    List<Event> myEventListByDate(String userId) ;
}
