package com.eventApp.Service;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Model.Student;

public class UserService {
    private final UserDAO userDAO= new UserDAO();
    public boolean registerStudent(Student student) {
        return userDAO.registrationStudent(student);
    }

    public boolean checklogin(String emailInput, String passwordInput) { return userDAO.checkLoginDetails(emailInput, passwordInput); }
}
