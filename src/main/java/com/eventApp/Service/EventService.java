package com.eventApp.Service;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.Event;

import java.sql.SQLException;

/**
 * Interface for event approval and navigation service operations.
 * Defines methods for managing event approval workflows and event navigation.
 */
public interface EventService {

    /**
     * Returns the next event in the list.
     *
     * @return The next pending Event.
     */
    Event viewNextEvent();

    /**
     * Approves the current event and removes it from the list.
     */
    void approveEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException;

    /**
     * Rejects the current event and removes it from the list.
     */
    void rejectEvent() throws SQLException, DatabaseExceptionHandler, ClassNotFoundException;

    /**
     * Returns the previous event in the list.
     *
     * @return The previous pending Event.
     */
    Event viewPreviousEvent();

    /**
     * Returns the current event in the list.
     *
     * @return The current pending Event.
     */
    Event viewCurrentEvent() throws NullPointerException;

    /**
     * Resets the internal pointer of the linked list to the starting position.
     */
    void resetPointer();
}
