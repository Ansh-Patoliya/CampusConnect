package com.eventApp.DAO;

import com.eventApp.DataStructures.MyClubQueue;
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
     * Creates a new event in the database for a specific club.
     *
     * @param event the Event object containing all event details to be inserted
     * @throws SQLException if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     * @throws ValidationException if event creation fails at database level
     */
    public void createEvent(Event event) throws SQLException, ClassNotFoundException, ValidationException {
        // SQL query to insert new event with all required fields
        String sql = "INSERT INTO events (club_id,event_name,description,event_date,ticket_price,created_by,discount_available,start_time,end_time,venue,max_participants,category) VALUES ( ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        // Set all event parameters in the prepared statement
        preparedStatement.setInt(1, event.getClubId());
        preparedStatement.setString(2, event.getEventName());
        preparedStatement.setString(3, event.getDescription());
        preparedStatement.setDate(4, Date.valueOf(event.getEventDate()));
        preparedStatement.setDouble(5, event.getTicketPrice());
        preparedStatement.setString(6, event.getUserId());
        preparedStatement.setBoolean(7, event.isDiscountApplicable());
        preparedStatement.setTime(8, Time.valueOf(event.getStartTime()));
        preparedStatement.setTime(9, Time.valueOf(event.getEndTime()));
        preparedStatement.setString(10, event.getVenue());
        preparedStatement.setInt(11, event.getMaxParticipants());
        preparedStatement.setString(12, event.getCategory());

        int r = preparedStatement.executeUpdate();
        // Validate that the event was successfully inserted
        if (r < 0) {
            throw new ValidationException("Event creation failed. Please try again.");
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
