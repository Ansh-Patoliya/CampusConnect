package com.eventApp.DAO;

import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.Club;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for club-related database operations.
 * Handles CRUD operations for clubs, event creation for clubs, and club validation.
 */
public class ClubDAO {

    /**
     * Registers a new club in the system.
     * Creates the club with pending status for admin approval.
     *
     * @param club the Club object containing club information
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if club registration fails
     */
    public void registrationClub(Club club) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into clubs(club_name,category,description,founder_id,status,max_member) values(?,?,?,?,?,?)");
        preparedStatement.setString(1, club.getClubName());
        preparedStatement.setString(2, club.getCategory());
        preparedStatement.setString(3, club.getDescriptions());
        preparedStatement.setString(4, club.getFounderId());
        preparedStatement.setString(5, club.getStatus());
        preparedStatement.setInt(6, club.getMaxMemberCount());

        int insertClub = preparedStatement.executeUpdate();
        // Validate that the club was successfully created
        if (insertClub < 0) {
            throw new DatabaseExceptionHandler("Club registration failed. Please try again.");
        }
    }

    /**
     * Retrieves all clubs with a specific status from the database.
     * Uses a custom queue data structure for storage.
     *
     * @param clubStatus the status to filter clubs by (e.g., "Approved", "Pending")
     * @return MyClubQueue containing all clubs with the specified status
     */
    public MyClubQueue getClubList(String clubStatus) {
        MyClubQueue clubList;
        int rowCount = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            // First, get the count of clubs to initialize the queue with proper size
            String fetchClubCount = "select count(*) from clubs where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchClubCount);
            preparedStatement.setString(1, clubStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

            // Initialize queue with the determined size
            clubList = new MyClubQueue(rowCount);

            // Fetch all club records with the specified status
            String fetchClubRecord = "select * from clubs where status = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(fetchClubRecord);
            preparedStatement2.setString(1, clubStatus);
            resultSet = preparedStatement2.executeQuery();

            // Process each club record and add to the queue
            while (resultSet.next()) {
                int clubId = resultSet.getInt("club_id");
                String clubName = resultSet.getString("club_name");
                String descriptions = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                int memberLimit = resultSet.getInt("max_member");
                clubList.enqueue(new Club(clubName, descriptions, category, founderId, status, memberCount, clubId, memberLimit));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return clubList;
    }

    /**
     * Retrieves the club name for a given club ID.
     *
     * @param clubId the unique identifier of the club
     * @return String containing the club name, or null if not found
     */
    public String getClubNameBy(int clubId) {
        String clubName = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT club_name FROM clubs WHERE club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clubName = resultSet.getString("club_name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return clubName;
    }

    /**
     * Retrieves the club ID for a given club name.
     *
     * @param clubName the name of the club to search for
     * @return int club ID, or -1 if club not found
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public int getClubIdBy(String clubName) throws SQLException, ClassNotFoundException {
        int clubId = -1; // Default value if club not found
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT club_id FROM clubs WHERE club_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, clubName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clubId = resultSet.getInt("club_id");
        }
        return clubId;
    }


    /**
     * Retrieves complete club information for a given club ID.
     *
     * @param clubId the unique identifier of the club
     * @return Club object with all club details, or null if not found
     */
    public Club getClubById(int clubId) {
        Club club = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM clubs WHERE club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Extract all club fields from the result set
                String clubName = resultSet.getString("club_name");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                int memberLimit = resultSet.getInt("max_member");
                club = new Club(clubName, description, category, founderId, status, memberCount, clubId, memberLimit);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return club;
    }

    /**
     * Retrieves the club ID for a club that a specific user is a member of.
     * Used to determine which club a user belongs to for authorization purposes.
     *
     * @param userId the unique identifier of the user
     * @return int club ID of the club the user is a member of
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws DatabaseExceptionHandler if user is not a member of any club
     */
    public static int getClubIdByUserId(String userId) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        int clubId = 0;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select club_id from club_members where member_id=?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clubId = resultSet.getInt(1);
        } else {
            // Throw exception if user is not a club member
            throw new DatabaseExceptionHandler("Club not found.");
        }
        return clubId;
    }

    /**
     * Validates that a club name doesn't already exist in the database.
     * Throws an exception if the name is already taken.
     *
     * @param clubName the club name to validate for uniqueness
     * @throws ValidationException if the club name already exists
     */
    public void checkClubNameExist(String clubName) throws ValidationException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select * from clubs where club_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clubName);
            ResultSet resultSet = preparedStatement.executeQuery();
            // If any record is found, the club name already exists
            if (resultSet.next()) {
                throw new ValidationException("Club name already exists.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all clubs from the database regardless of status.
     * Results are ordered by creation date.
     *
     * @return MyClubQueue containing all clubs in the system
     */
    public MyClubQueue getAllClubList() {
        MyClubQueue clubList;
        int rowCount = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            // First, get the total count of clubs to initialize the queue
            String fetchClubCount = "select count(*) from clubs";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchClubCount);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

            // Initialize queue with the determined size
            clubList = new MyClubQueue(rowCount);

            // Fetch all club records ordered by creation date
            String fetchClubRecord = "select * from clubs order by created_at";
            PreparedStatement preparedStatement2 = connection.prepareStatement(fetchClubRecord);
            resultSet = preparedStatement2.executeQuery();

            // Process each club record and add to the queue
            while (resultSet.next()) {
                int clubId = resultSet.getInt("club_id");
                String clubName = resultSet.getString("club_name");
                String descriptions = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                int memberLimit = resultSet.getInt("max_member");
                clubList.enqueue(new Club(clubName, descriptions, category, founderId, status, memberCount, clubId, memberLimit));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow as unchecked exception for higher-level handling
            throw new RuntimeException(e);
        }
        return clubList;
    }

    /**
     * Retrieves a list of all approved club names, sorted alphabetically.
     * Used for dropdown menus and selection components.
     *
     * @return List<String> containing names of all approved clubs
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public List<String> getAllClubNames() throws SQLException, ClassNotFoundException {
        List<String> clubNames = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        // Only fetch approved clubs and sort them alphabetically
        String query = "SELECT club_name FROM clubs where status = 'Approved' order by club_name";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clubNames.add(resultSet.getString("club_name"));
        }
        return clubNames;
    }
}
