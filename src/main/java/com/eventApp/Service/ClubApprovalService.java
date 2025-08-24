package com.eventApp.Service;

import com.eventApp.Model.Club;

/**
 * Interface for club approval service operations.
 * Defines methods for managing club approval workflow including viewing, approving, and rejecting clubs.
 */
public interface ClubApprovalService {

    /**
     * Returns the next club in the queue waiting for approval without removing it.
     *
     * @return the next pending Club object or null if none available
     */
    Club viewNextPendingClub();

    /**
     * Approves the next pending club by updating its status in the database and
     * removing it from the queue.
     *
     * @return true if the club was successfully approved, false otherwise
     */
    boolean approveNextClub();

    /**
     * Rejects the next pending club by deleting it from the database using a stored procedure
     * and removing it from the queue.
     *
     * @return true if the club was successfully deleted, false otherwise
     */
    boolean rejectNextClub();

    /**
     * Retrieves all clubs with status "Pending" from the database and loads them into the queue.
     */
    void getAllPendingClubs();
}
