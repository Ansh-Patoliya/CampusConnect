package com.eventApp.Service;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;

public class EventService {
    private MyEventLL LL;
    private MyEventLL eventList = new MyEventLL();

    public EventService() {
        getAllPendingEvents();
    }

    private MyEventLL getAllPendingEvents() {
        if (LL == null) {
            LL = AdminDAO.getEventList("Pending");
        }
        return LL;
    }

    public Event viewNextEvent() {
        return LL.viewNextEvent();
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
