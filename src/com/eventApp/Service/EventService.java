package com.eventApp.Service;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DAO.EventDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;

public class EventService {
    private MyEventLL LL;

    AdminDAO adminDAO = new AdminDAO();
    EventDAO eventDAO = new EventDAO();
    public EventService() {
        getAllPendingEvents();
    }

    private void getAllPendingEvents() {
        if (LL == null) {
            LL = eventDAO.getEventList("Pending");
        }
    }

    public Event viewNextEvent() {
        return LL.viewNextEvent();
    }

    public boolean approveEvent() {
        return eventDAO.approvalStatusUpdate("Approved", LL.deleteAtCurrent().getEventId());
    }
    public boolean rejectEvent() {
        return eventDAO.approvalStatusUpdate("Rejected", LL.deleteAtCurrent().getEventId());
    }

    public Event viewPreviousEvent() {
        return LL.viewPreviousEvent();
    }

    public Event viewCurrentEvent() {
        return LL.viewCurrentEvent();
    }

    public void resetPointer() {
        LL.resetPointer();
    }
}
