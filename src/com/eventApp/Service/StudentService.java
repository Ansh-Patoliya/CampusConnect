package com.eventApp.Service;

import com.eventApp.DAO.EventDAO;
import com.eventApp.DAO.StudentDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class StudentService {

    MyEventLL myEventLL ;

    public Student getStudentByUser(User user) {
        return StudentDAO.getStudent(user);
    }

    EventDAO eventDAO= new EventDAO();
    StudentDAO studentDAO= new StudentDAO();
    public MyEventLL sortEventList(){
        List<Event> eventList=eventDAO.getEventList("Approved");
        User currentUser=CurrentUser.getCurrentUser();
        List<String > interestList = studentDAO.getInterestList(currentUser.getUserId());
        eventList.sort(Comparator.comparingInt((Event e) -> {
            int index = interestList.indexOf(e.getCategory());
            return index == -1 ? Integer.MAX_VALUE : index;
        }).thenComparing(Event::getEventDate));

        MyEventLL myEventLL = new MyEventLL();
        for (Event event : eventList) {
            myEventLL.addLast(event);
        }
        return myEventLL;
    }
    //    public boolean registerStudentToEvent(String studentId, String eventId)  // registration logic

    public void loadStudentEvents() {
        myEventLL=sortEventList();
    }

    public Event viewCurrentEvent() {
        if (myEventLL.isEmpty()) {
            return null; // or throw an exception
        }
        return myEventLL.viewCurrentEvent();
    }

    public Event getNextEvent() {
        if (myEventLL.isEmpty()) {
            return null; // or throw an exception
        }
        return myEventLL.viewNextEvent();
    }

    public Event getPreviousEvent() {
        if (myEventLL.isEmpty()) {
            return null; // or throw an exception
        }
        return myEventLL.viewPreviousEvent();
    }
}
