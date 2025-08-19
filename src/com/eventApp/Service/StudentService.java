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

public class StudentService {

    // Linked list of events tailored for the student
    MyEventLL myEventLL;

    /**
     * Retrieves a Student object corresponding to the given User.
     *
     * @param user The User object to look up
     * @return The matching Student object
     */
    public Student getStudentByUser(User user) {
        return StudentDAO.getStudent(user);
    }

    EventDAO eventDAO = new EventDAO();
    StudentDAO studentDAO = new StudentDAO();

    /**
     * Sorts the list of approved and not completed events for the current user.
     * The sorting prioritizes events matching user's interests first,
     * then sorts events by their date.
     *
     * @return A linked list of sorted events (MyEventLL)
     */
    public MyEventLL sortEventList() {
        // Fetch approved, not completed events
        List<Event> eventList = eventDAO.getEventList("Approved", "Not Completed");
        // Get current logged-in user
        User currentUser = CurrentUser.getCurrentUser();
        // Fetch interest categories for the user
        List<String> interestList = studentDAO.getInterestList(currentUser.getUserId());

        // Sort events: first by interest priority, then by event date
        eventList.sort(Comparator.comparingInt((Event e) -> {
            int index = interestList.indexOf(e.getCategory());
            return index == -1 ? Integer.MAX_VALUE : index; // events not in interests go to the end
        }).thenComparing(Event::getEventDate));

        // Populate linked list with sorted events
        MyEventLL myEventLL = new MyEventLL();
        for (Event event : eventList) {
            myEventLL.addLast(event);
        }
        return myEventLL;
    }

    /**
     * Loads the student's event list by sorting events and storing in myEventLL.
     */
    public void loadStudentEvents() {
        myEventLL = sortEventList();
    }

    /**
     * Retrieves the current event from the student's event list.
     *
     * @return The current Event or null if the list is empty
     */
    public Event viewCurrentEvent() {
        if (myEventLL.isEmpty()) {
            return null;
        }
        return myEventLL.viewCurrentEvent();
    }

    /**
     * Retrieves the next event from the student's event list.
     *
     * @return The next Event or null if the list is empty
     */
    public Event getNextEvent() {
        if (myEventLL.isEmpty()) {
            return null;
        }
        return myEventLL.viewNextEvent();
    }

    /**
     * Retrieves the previous event from the student's event list.
     *
     * @return The previous Event or null if the list is empty
     */
    public Event getPreviousEvent() {
        if (myEventLL.isEmpty()) {
            return null;
        }
        return myEventLL.viewPreviousEvent();
    }

    /**
     * Returns the list of events the student is registered for, sorted by event date.
     *
     * @param userId The student's user ID
     * @return List of Events sorted by date
     */
    public List<Event> myEventListByDate(String userId) {
        List<Event> myEvents = eventDAO.getMyEventList(userId);
        myEvents.sort(Comparator.comparing(Event::getEventDate));
        return myEvents;
    }

}
