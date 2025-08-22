package com.eventApp.Service;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import java.sql.SQLException;

/**
 * Interface to oversee event registration related service.
 * Defines method for event registration.
 */
public interface EventRegistrationService {
    /**
     * Registers the current user for an event by eventId.
     * It checks if the event has available slots and if the user
     * is not already registered for the event before proceeding.
     *
     * @param eventId The ID of the event to register for
     * @throws DatabaseExceptionHandler if max participant limit reached or user already registered
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    void registerForEvent(int eventId) throws DatabaseExceptionHandler, SQLException, ClassNotFoundException ;
}
