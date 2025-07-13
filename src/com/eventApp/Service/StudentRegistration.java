package com.eventApp.Service;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;

public class StudentRegistration {
    private StudentDAO studentDAO=new StudentDAO();
    public boolean registerStudent(Student student) {
        return studentDAO.registration(student);
    }

    public boolean checklogin(String emailInput, String passwordInput) { return studentDAO.checkLoginDetails(emailInput, passwordInput); }

    public boolean resetPassword(String emailInput, String newPassword, String confirmPassword) {
        return StudentDAO.resetPass(emailInput,newPassword,confirmPassword);
    }
}
