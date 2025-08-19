package com.eventApp.Service;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DAO.EventDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;

/**
 * Service class to manage event approval workflows and event navigation.
 */
public class EventService {
    private MyEventLL LL;

    EventDAO eventDAO = new EventDAO();

    /**
     * Constructs an EventService instance and loads all pending events.
     */
    public EventService() {
        getAllPendingEvents();
    }

    /**
     * Loads all pending events into the linked list if not already loaded.
     */
    private void getAllPendingEvents() {
        if (LL == null) {
            LL = eventDAO.getEventList("Pending");
        }
    }

    /**
     * Returns the next event in the list.
     *
     * @return The next pending Event.
     */
    public Event viewNextEvent() {
        return LL.viewNextEvent();
    }

    /**
     * Approves the current event and removes it from the list.
     *
     * @return true if the event was successfully approved; false otherwise.
     */
    public boolean approveEvent() {
        return eventDAO.approvalStatusUpdate("Approved", LL.deleteAtCurrent().getEventId());
    }

    /**
     * Rejects the current event and removes it from the list.
     *
     * @return true if the event was successfully rejected; false otherwise.
     */
    public boolean rejectEvent() {
        return eventDAO.approvalStatusUpdate("Rejected", LL.deleteAtCurrent().getEventId());
    }

    /**
     * Returns the previous event in the list.
     *
     * @return The previous pending Event.
     */
    public Event viewPreviousEvent() {
        return LL.viewPreviousEvent();
    }

    /**
     * Returns the current event in the list.
     *
     * @return The current pending Event.
     */
    public Event viewCurrentEvent() {
        return LL.viewCurrentEvent();
    }

    /**
     * Resets the internal pointer of the linked list to the starting position.
     */
    public void resetPointer() {
        LL.resetPointer();
    }
}
