package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Model.Club;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Service class to manage approval workflow for clubs.
 * It maintains a queue of clubs with status "Pending" and allows viewing,
 * approving, or rejecting clubs one by one.
 */
public class ClubApprovalService {

    private MyClubQueue queue;              // Queue holding clubs awaiting approval
    private ClubDAO clubDAO = new ClubDAO(); // DAO for club-related database operations

    /**
     * Constructor initializes the queue with all pending clubs.
     */
    public ClubApprovalService() {
        getAllPendingClubs();
    }

    /**
     * Returns the next club in the queue waiting for approval without removing it.
     *
     * @return the next pending Club object or null if none available
     */
    public Club viewNextPendingClub() {
        return queue.viewFirst();
    }

    /**
     * Approves the next pending club by updating its status in the database and
     * removing it from the queue.
     *
     * @return true if the club was successfully approved, false otherwise
     */
    public boolean approveNextClub() {
        Club nextClub = queue.dequeue();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE clubs SET status = 'Approved' WHERE club_id = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nextClub.getClubId());
            preparedStatement.setString(2, "Pending");

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rejects the next pending club by deleting it from the database using a stored procedure
     * and removing it from the queue.
     *
     * @return true if the club was successfully deleted, false otherwise
     */
    public boolean rejectNextClub() {
        Club nextClub = queue.dequeue();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "call deleteClub(?)";
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, nextClub.getClubId());

            int rowsUpdated = callableStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all clubs with status "Pending" from the database and loads them into the queue.
     * Only initializes the queue if it is not already initialized.
     */
    public void getAllPendingClubs() {
        if (queue == null) {
            queue = clubDAO.getClubList("Pending");
        }
    }
}
