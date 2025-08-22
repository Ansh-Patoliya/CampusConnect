package com.eventApp.Service;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import java.sql.SQLException;

/**
 * Interface to manage user related service operations.
 * Defines methods to register, validate login, reset password and register club for users
 */
public interface UserService {

    /**
     * Registers a new student along with associated user data.
     *
     * @param student the Student object containing student details
     * @param user the User object containing general user details
     * @throws DatabaseExceptionHandler if database constraints or errors occur
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    void registerStudent(Student student, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException ;

    /**
     * Registers a new club member along with associated user data after checking club member limit.
     *
     * @param clubMember the ClubMember object containing club member details
     * @param user the User object containing general user details
     * @throws DatabaseExceptionHandler if club member limit is exceeded or other database errors occur
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    void registerClubMember(ClubMember clubMember, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException ;

    /**
     * Checks login credentials against stored user data.
     *
     * @param emailInput the email input from user
     * @param passwordInput the password input from user
     * @return true if credentials are valid, false otherwise
     */
    boolean checklogin(String emailInput, String passwordInput) ;

    /**
     * Resets the password for a user with the given email.
     *
     * @param emailInput the email of the user
     * @param newPassword the new password to set
     * @throws SQLException if a database access error occurs
     * @throws DatabaseExceptionHandler if database constraints or errors occur
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    void resetPassword(String emailInput, String newPassword) throws SQLException, DatabaseExceptionHandler, ClassNotFoundException ;

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
    void registerClub(Club club, ClubMember clubMember, User user) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException ;

    /**
     * Retrieves a User object by their email.
     *
     * @param emailInput the email of the user
     * @return the User object if found, null otherwise
     */
    User getUserByEmail(String emailInput) ;
}
