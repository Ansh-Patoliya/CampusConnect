package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Model.Event;

public class ClubService {
    public final ClubDAO clubDAO = new ClubDAO();

    public static boolean addEvent(Event event) throws Exception {
        return ClubDAO.createEvent(event);
    }
}