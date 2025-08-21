package com.eventApp.Service;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Model.*;
import com.eventApp.DAO.AdminDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class for administrative operations in the CampusConnect system.
 * Handles data export, reporting, and administrative management tasks.
 * Provides functionality to export club and event data for administrators.
 */
public class AdminService {

    // DAO for user-related database operations
    UserDAO userDAO = new UserDAO();

    // DAO for club-related database operations
    ClubDAO clubDAO = new ClubDAO();

    /**
     * Exports all club data into a CSV-like file with columns:
     * Club ID, Club Name, President Name, Description.
     *
     * @param clubFilename The path of the file to write club data to
     */
    public void exportClubData(String clubFilename){
        // Retrieve all clubs in a queue data structure
        MyClubQueue allClubs = clubDAO.getAllClubList();

        try (BufferedWriter clubFile = new BufferedWriter(new FileWriter(clubFilename))) {
            // Write header line
            String formattedLine = String.format("%s , %s , %s , %s", "Club ID", "Club Name", "President Name", "Description");
            clubFile.write(formattedLine);
            clubFile.newLine();

            // Iterate through all clubs until the queue is empty
            while (!allClubs.isEmpty()){
                Club currentClub = allClubs.dequeue();

                // Format club data: club id, name, founder (president) name, and description
                formattedLine = String.format("%s , %s , %s , %s",
                        currentClub.getClubId(),
                        currentClub.getClubName(),
                        userDAO.getUserNameBy(currentClub.getFounderId()),
                        currentClub.getDescriptions());

                // Write the formatted club data to file and flush buffer
                clubFile.write(formattedLine);
                clubFile.newLine();
                clubFile.flush();
            }
        } catch (Exception e) {
            // Wrap and throw runtime exception on failure
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all clubs as a queue.
     *
     * @return MyClubQueue containing all clubs
     */
    public MyClubQueue getAllClubs(){
        return clubDAO.getAllClubList();
    }

    /**
     * Returns the Admin object corresponding to a User.
     *
     * @param user User object to get admin details for
     * @return Admin object related to the given user
     */
    public Admin getAdmin(User user) {
        return AdminDAO.getAdmin(user);
    }

    /**
     * Fetches the list of ClubMember objects for a given club ID.
     *
     * @param clubId The ID of the club
     * @return List of ClubMember objects
     * @throws SQLException if a database access error occurs while retrieving the member list
     * @throws ClassNotFoundException if the database driver class cannot be found
     */
    public List<ClubMember> getClubMemberList(int clubId) throws SQLException, ClassNotFoundException {
        return new ClubMemberDAO().getClubMemberList(clubId);
    }
}
