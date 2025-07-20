package com.eventApp.Service;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;

import java.util.List;
import java.util.Queue;

public class AdminService {
    MyEventLL eventList;

    AdminDAO adminDAO = new AdminDAO();

    public void getEventList() {
        this.eventList= adminDAO.getEventList();
    }
}
