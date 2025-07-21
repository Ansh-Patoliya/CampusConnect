package com.eventApp.Service;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;

public class StudentService {

    public Student getStudentByUser(User user) {
        return StudentDAO.getStudent(user);
    }
}
