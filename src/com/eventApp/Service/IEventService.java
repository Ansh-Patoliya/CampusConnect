package com.eventApp.Service;

import com.eventApp.Model.Event;

/**
 * Interface for event approval and navigation service operations.
 * Defines methods for managing event approval workflows and event navigation.
 */
public interface IEventService {

    /**
     * Returns the next event in the list.
     *
     * @return The next pending Event.
     */
    Event viewNextEvent();

    /**
     * Approves the current event and removes it from the list.
     *
     * @return true if the event was successfully approved; false otherwise.
     */
    boolean approveEvent();

    /**
     * Rejects the current event and removes it from the list.
     *
     * @return true if the event was successfully rejected; false otherwise.
     */
    boolean rejectEvent();

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
    Event viewCurrentEvent();

    /**
     * Resets the internal pointer of the linked list to the starting position.
     */
    void resetPointer();
}
