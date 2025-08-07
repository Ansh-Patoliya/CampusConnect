package com.eventApp.Service;

import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;

public class UserService {
    private final UserDAO userDAO= new UserDAO();
    public boolean registerStudent(Student student,User user) throws DatabaseExceptionHandler {
        return userDAO.registrationUser(user)&&userDAO.registrationStudent(student);
    }

    public boolean registerClubMember(ClubMember clubMember,User user) throws DatabaseExceptionHandler {
        return userDAO.registrationUser(user)&&userDAO.registrationClubMember(clubMember);
    }

    public boolean checklogin(String emailInput, String passwordInput) { return userDAO.checkLoginDetails(emailInput, passwordInput); }

    public boolean resetPassword(String emailInput, String newPassword, String confirmPassword) {
        return userDAO.resetPass(emailInput,newPassword,confirmPassword);
    }

    public boolean registerClub(Club club, ClubMember clubMember, User user) throws DatabaseExceptionHandler {
        return userDAO.registrationUser(user)&&userDAO.registrationClub(club) && userDAO.registrationClubMember(clubMember);
    }

    public User getUserByEmail(String emailInput) {
        return userDAO.getUserByemail(emailInput);
    }
}
