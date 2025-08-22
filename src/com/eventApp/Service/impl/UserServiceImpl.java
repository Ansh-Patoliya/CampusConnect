package com.eventApp.Service.impl;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.StudentDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class to manage user-related operations such as registration, login, and password reset.
 */
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final ClubMemberDAO clubMemberDAO = new ClubMemberDAO();
    private final ClubDAO clubDAO = new ClubDAO();

    /**
     * Registers a new student along with associated user data.
     *
     * @param student the Student object containing student details
     * @param user the User object containing general user details
     * @throws DatabaseExceptionHandler if database constraints or errors occur
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    public void registerStudent(Student student, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        userDAO.registrationUser(user);
        studentDAO.registrationStudent(student);
    }

    /**
     * Registers a new club member along with associated user data after checking club member limit.
     *
     * @param clubMember the ClubMember object containing club member details
     * @param user the User object containing general user details
     * @throws DatabaseExceptionHandler if club member limit is exceeded or other database errors occur
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
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

    /**
     * Checks login credentials against stored user data.
     *
     * @param emailInput the email input from user
     * @param passwordInput the password input from user
     * @return true if credentials are valid, false otherwise
     */
    public boolean checklogin(String emailInput, String passwordInput) {
        return userDAO.checkLoginDetails(emailInput, passwordInput);
    }

    /**
     * Resets the password for a user with the given email.
     *
     * @param emailInput the email of the user
     * @param newPassword the new password to set
     * @throws SQLException if a database access error occurs
     * @throws DatabaseExceptionHandler if database constraints or errors occur
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    public void resetPassword(String emailInput, String newPassword) throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
        userDAO.resetPass(emailInput, newPassword);
    }

    /**
     * Registers a new club along with associated club member and user data.
     *
     * @param club the Club object containing club details
     * @param clubMember the ClubMember object containing member details
     * @param user the User object containing general user details
     * @throws DatabaseExceptionHandler if database constraints or errors occur
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    public void registerClub(Club club, ClubMember clubMember, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException {
        userDAO.registrationUser(user);
        clubDAO.registrationClub(club);
        int clubId = clubDAO.getClubIdBy(club.getClubName());
        clubMember.setClubId(clubId);
        clubMemberDAO.registrationClubMember(clubMember);
    }

    /**
     * Retrieves a User object by their email.
     *
     * @param emailInput the email of the user
     * @return the User object if found, null otherwise
     */
    public User getUserByEmail(String emailInput) {
        return userDAO.getUserByemail(emailInput);
    }
}
