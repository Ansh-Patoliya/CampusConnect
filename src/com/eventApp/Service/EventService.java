package com.eventApp.Service;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;

public class EventService {
    private MyEventLL LL;

    AdminDAO adminDAO = new AdminDAO();
    public EventService() {
        getAllPendingEvents();
    }

    private void getAllPendingEvents() {
        if (LL == null) {
            LL = adminDAO.getEventList("Pending");
        }
    }

    public Event viewNextEvent() {
        return LL.viewNextEvent();
    }

    public boolean approveEvent() {
        return adminDAO.approvalStatusUpdate("Approved", LL.deleteAtCurrent().getEventId());
    }
    public boolean rejectEvent() {
        return adminDAO.approvalStatusUpdate("Rejected", LL.deleteAtCurrent().getEventId());
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
