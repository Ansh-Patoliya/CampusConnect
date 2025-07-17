package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Model.Event;

public class ClubService {
    public final ClubDAO clubDAO = new ClubDAO();

    public boolean addEvent(Event event) {
        return clubDAO.createEvent(event);
    }
}