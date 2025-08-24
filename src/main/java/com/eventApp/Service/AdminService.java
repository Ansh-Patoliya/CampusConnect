package com.eventApp.Service;

import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Model.Admin;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for admin-related service operations.
 * Defines methods for managing admin functionality including club data export and retrieval.
 */
public interface AdminService {

    /**
     * Exports all club data into a CSV-like file with columns:
     * Club ID, Club Name, President Name, Description.
     *
     * @param clubFilename The path of the file to write club data to
     */
    void exportClubData(String clubFilename);

    /**
     * Retrieves all clubs as a queue.
     *
     * @return MyClubQueue containing all clubs
     */
    MyClubQueue getAllClubs();

    /**
     * Returns the Admin object corresponding to a User.
     *
     * @param user User object to get admin details for
     * @return Admin object related to the given user
     */
    Admin getAdmin(User user);

    /**
     * Fetches the list of ClubMember objects for a given club ID.
     *
     * @param clubId The ID of the club
     * @return List of ClubMember objects
     * @throws SQLException if a database access error occurs while retrieving the member list
     * @throws ClassNotFoundException if the database driver class cannot be found
     */
    List<ClubMember> getClubMemberList(int clubId) throws SQLException, ClassNotFoundException;
}
