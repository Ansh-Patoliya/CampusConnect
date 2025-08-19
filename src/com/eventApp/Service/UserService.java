package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.StudentDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final ClubMemberDAO clubMemberDAO = new ClubMemberDAO();

    public void registerStudent(Student student, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        userDAO.registrationUser(user);
        studentDAO.registrationStudent(student);
    }

    public void registerClubMember(ClubMember clubMember, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT checkclubcount(?)");
        preparedStatement.setInt(1, clubMember.getClubId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getBoolean(1)) {
            userDAO.registrationUser(user);
            clubMemberDAO.registrationClubMember(clubMember);
        } else
            throw new DatabaseExceptionHandler("club member limit exceeded.");
    }

    public boolean checklogin(String emailInput, String passwordInput) {
        return userDAO.checkLoginDetails(emailInput, passwordInput);
    }

    public void resetPassword(String emailInput, String newPassword) throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
        userDAO.resetPass(emailInput, newPassword);
    }

    ClubDAO clubDAO = new ClubDAO();
    public void registerClub(Club club, ClubMember clubMember, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        userDAO.registrationUser(user);
        clubDAO.registrationClub(club);
        int clubId=clubDAO.getClubIdBy(club.getClubName());
        clubMember.setClubId(clubId);
        clubMemberDAO.registrationClubMember(clubMember);
    }

    public User getUserByEmail(String emailInput) {
        return userDAO.getUserByemail(emailInput);
    }
}
