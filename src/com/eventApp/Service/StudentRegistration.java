package com.eventApp.Service;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.Model.Student;

public class StudentRegistration {
    private StudentDAO studentDAO=new StudentDAO();
    public boolean registerStudent(Student student) {
        return studentDAO.registration(student);
    }
}
