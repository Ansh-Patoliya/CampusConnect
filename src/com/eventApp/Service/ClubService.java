package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.EventDAO;
import com.eventApp.DAO.EventRegistrationDAO;
import com.eventApp.DataStructures.CircularLL;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service class managing operations related to clubs, events, and club members.
 * Provides methods for CRUD operations, event navigation, data export, and more.
 */
public class ClubService {

    public final ClubDAO clubDAO = new ClubDAO();                   // DAO for club-related database operations
    public final ClubMemberDAO clubMemberDAO = new ClubMemberDAO(); // DAO for club member data access
    public final EventRegistrationDAO eventRegistrationDAO = new EventRegistrationDAO(); // DAO for event registrations
    public final EventDAO eventDAO = new EventDAO();                 // DAO for event data access

    CircularLL circularLL;  // Circular linked list to navigate through club events

    /**
     * Default constructor.
     */
    public ClubService() {
        // Default constructor
    }

    /**
     * Overloaded constructor that loads the event list for the given user.
     *
     * @param user The user whose club events should be loaded
     * @throws SQLException if a database access error occurs while loading events
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public ClubService(User user) throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
        loadMyEventList(user);
    }

    /**
     * Creates a new event in the database.
     *
     * @param event The event object to be created
     * @throws ValidationException if the event data is invalid
     * @throws SQLException if a database access error occurs during event creation
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void addEvent(Event event) throws ValidationException, SQLException, ClassNotFoundException {
        eventDAO.createEvent(event);
    }

    /**
     * Retrieves the club associated with the given user.
     *
     * @param user The user whose club is to be retrieved
     * @return Club object associated with the user
     */
    public Club getClubByUser(User user) {
        int clubId = ClubMemberDAO.getClubMember(user).getClubId();
        return clubDAO.getClubById(clubId);
    }

    /**
     * Retrieves and returns a sorted list of club members belonging to the same club as the given user.
     *
     * @param user The user whose club members are to be retrieved
     * @return Sorted list of ClubMember objects
     * @throws SQLException if a database access error occurs while fetching club members
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<ClubMember> getClubMember(User user) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMembers = clubMemberDAO.getClubMemberList(user.getUserId());
        clubMembers.sort(Comparator.comparing(ClubMember::getName));
        return clubMembers;
    }

    /**
     * Retrieves and returns a sorted list of students participating in a specific event.
     *
     * @param eventId The ID of the event
     * @return Sorted list of Student participants
     * @throws SQLException if a database access error occurs while fetching participants
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<Student> getParticipant(int eventId) throws SQLException, ClassNotFoundException {
        List<Student> participants = eventRegistrationDAO.getParticipantList(eventId);
        participants.sort(Comparator.comparing(Student::getName));
        return participants;
    }

    /**
     * Exports a list of club members to a CSV file at the specified file path.
     * The CSV contains user ID, name, email, and position.
     *
     * @param clubMemberList List of club members to export
     * @param filePath Destination file path for the CSV
     * @throws IOException if an I/O error occurs during writing to the file
     */
    public void exportClubMembersToCSV(List<ClubMember> clubMemberList, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(String.format("%s , %s , %s , %s", "User ID", "Name", "Email", "Position"));
        writer.newLine();

        for (ClubMember clubMember : clubMemberList) {
            String formattedLine = String.format("%s , %s , %s , %s",
                    clubMember.getUserId(),
                    clubMember.getName(),
                    clubMember.getEmail(),
                    clubMember.getPosition());
            writer.write(formattedLine);
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

    /**
     * Retrieves a sorted list of all event names for a given user.
     *
     * @param userId The user ID whose event names are requested
     * @return Sorted list of event names
     */
    public List<String> getAllEventNames(String userId) {
        List<String> eventNameList = eventDAO.getEventNames(userId);
        Collections.sort(eventNameList);
        return eventNameList;
    }

    /**
     * Loads the circular linked list of events for the club associated with the given user.
     *
     * @param user The user whose club events are loaded
     * @throws SQLException if a database access error occurs while loading events
     * @throws ClassNotFoundException if the JDBC driver class is not found
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     */
    public void loadMyEventList(User user) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        int clubId = ClubMemberDAO.getClubMember(user).getClubId();
        this.circularLL = eventDAO.getEventListByClubId(clubId);
    }

    /**
     * Returns the current event in the circular event list.
     *
     * @return The current Event object
     */
    public Event viewCurrentEvent() {
        return circularLL.viewCurrentEvent();
    }

    /**
     * Moves to and returns the next event in the circular event list.
     *
     * @return The next Event object
     */
    public Event viewNextEvent() {
        return circularLL.viewNextEvent();
    }

    /**
     * Cancels the current event by updating its status in the database.
     *
     * @throws SQLException if a database access error occurs during cancellation
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void cancelEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException {
        eventDAO.cancelEvent(viewCurrentEvent().getEventId());
    }

    /**
     * Updates the details of the given event in the database.
     *
     * @param currentEvent The event object with updated information
     * @throws SQLException if a database access error occurs during update
     * @throws ClassNotFoundException if the JDBC driver class is not found
     * @throws DatabaseExceptionHandler if there is a custom database related exception
     */
    public void updateEvent(Event currentEvent) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        eventDAO.updateEvent(currentEvent);
    }
}
