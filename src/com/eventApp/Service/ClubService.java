package com.eventApp.Service;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for club related service operations.
 * Defines methods for CRUD operations, event navigation, data export, and more.
 */
public interface ClubService {
    /**
     * Creates a new event in the database.
     *
     * @param event The event object to be created
     * @throws ValidationException if the event data is invalid
     * @throws SQLException if a database access error occurs during event creation
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    void addEvent(Event event) throws ValidationException, SQLException, ClassNotFoundException ;

    /**
     * Retrieves the club associated with the given user.
     *
     * @param user The user whose club is to be retrieved
     * @return Club object associated with the user
     */
    Club getClubByUser(User user) ;

    /**
     * Retrieves and returns a sorted list of club members belonging to the same club as the given user.
     *
     * @param user The user whose club members are to be retrieved
     * @return Sorted list of ClubMember objects
     * @throws SQLException if a database access error occurs while fetching club members
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    List<ClubMember> getClubMember(User user) throws SQLException, ClassNotFoundException ;

    /**
     * Retrieves and returns a sorted list of students participating in a specific event.
     *
     * @param eventId The ID of the event
     * @return Sorted list of Student participants
     * @throws SQLException if a database access error occurs while fetching participants
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    List<Student> getParticipant(int eventId) throws SQLException, ClassNotFoundException ;

    /**
     * Exports a list of club members to a CSV file at the specified file path.
     * The CSV contains user ID, name, email, and position.
     *
     * @param clubMemberList List of club members to export
     * @param filePath Destination file path for the CSV
     * @throws IOException if an I/O error occurs during writing to the file
     */
    void exportClubMembersToCSV(List<ClubMember> clubMemberList, String filePath) throws IOException ;

    /**
     * Retrieves a sorted list of all event names for a given user.
     *
     * @param userId The user ID whose event names are requested
     * @return Sorted list of event names
     */
    List<String> getAllEventNames(String userId) ;

    /**
     * Loads the circular linked list of events for the club associated with the given user.
     *
     * @param user The user whose club events are loaded
     * @throws SQLException if a database access error occurs while loading events
     * @throws ClassNotFoundException if the JDBC driver class is not found
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     */
    void loadMyEventList(User user) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler ;

    /**
     * Returns the current event in the circular event list.
     *
     * @return The current Event object
     */
    Event viewCurrentEvent() ;

    /**
     * Moves to and returns the next event in the circular event list.
     *
     * @return The next Event object
     */
    Event viewNextEvent() ;

    /**
     * Cancels the current event by updating its status in the database.
     *
     * @throws SQLException if a database access error occurs during cancellation
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    void cancelEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException ;

    /**
     * Updates the details of the given event in the database.
     *
     * @param currentEvent The event object with updated information
     * @throws SQLException if a database access error occurs during update
     * @throws ClassNotFoundException if the JDBC driver class is not found
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     */
    void updateEvent(Event currentEvent) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler ;
}
