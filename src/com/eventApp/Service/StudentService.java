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

    public Student getStudentByUser(User user) {
        return StudentDAO.getStudent(user);
    }

    EventDAO eventDAO= new EventDAO();
    StudentDAO studentDAO= new StudentDAO();
    public MyEventLL sortEventList(){
        List<Event> eventList=eventDAO.getEventList("Pending");
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

//    public void loadStudentEvents(String studentId)           // loads list and sets index = 0
//    public Event viewCurrentEvent()                           // returns event at currentIndex
//    public Event getNextEvent()                               // increments index, returns next
//    public Event getPreviousEvent()                           // decrements index, returns previous
//    public boolean registerStudentToEvent(String studentId, String eventId)  // registration logic
}
