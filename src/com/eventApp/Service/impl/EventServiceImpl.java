package com.eventApp.Service.impl;

import com.eventApp.DAO.EventDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Event;
import com.eventApp.Service.EventService;

import java.sql.SQLException;

public class EventServiceImpl implements EventService {
    private MyEventLL LL;

    EventDAO eventDAO = new EventDAO();

    /**
     * Constructs an EventService instance and loads all pending events.
     */
    public EventServiceImpl() {
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
     */
    public void approveEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
         eventDAO.approvalStatusUpdate("Approved", LL.deleteAtCurrent().getEventId());
    }

    /**
     * Rejects the current event and removes it from the list.
     */
    public void rejectEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
         eventDAO.approvalStatusUpdate("Rejected", LL.deleteAtCurrent().getEventId());
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
        Event event=LL.viewCurrentEvent();
        if (event==null){
            throw new NullPointerException("No current event available.");
        }
         return event;
    }

    /**
     * Resets the internal pointer of the linked list to the starting position.
     */
    public void resetPointer() {
        LL.resetPointer();
    }
}
