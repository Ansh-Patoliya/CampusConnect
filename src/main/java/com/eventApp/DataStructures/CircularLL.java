package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

/**
 * Custom Circular Linked List implementation for managing Event objects.
 * Events are automatically sorted by date and approval status priority.
 * Provides circular navigation through events for enhanced user experience.
 */
public class CircularLL {
    private Node head = null;  // Reference to the first node in the circular list
    private Node current;      // Pointer for circular navigation through events

    /**
     * Checks if the circular linked list is empty.
     *
     * @return true if the list contains no events, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Adds an event to the circular linked list in sorted order.
     * Events are sorted first by date (ascending), then by approval status priority.
     * Maintains the circular structure where the last node points back to head.
     *
     * @param data the Event object to add to the list
     */
    public void add(Event data) {
        Node n = new Node(data);

        // Case 1: Empty list - make new node the head and point to itself
        if (head == null) {
            head = n;
            n.next = head;  // Circular: single node points to itself
            return;
        }

        // Case 2: New event should be the new head (comes before current head)
        if (comesBefore(data, head.data)) {
            // Find the last node to update its next pointer
            Node last = head;
            while (last.next != head) {
                last = last.next;
            }
            // Insert at beginning and update circular references
            last.next = n;
            n.next = head;
            head = n;
            return;
        }

        // Case 3: Insert in middle or at end while maintaining sorted order
        Node temp = head;
        while (temp.next != head && comesBefore(temp.next.data, data)) {
            temp = temp.next;
        }
        // Insert new node after temp
        n.next = temp.next;
        temp.next = n;
    }

    /**
     * Determines if event 'a' should come before event 'b' in the sorted order.
     * Primary sort: by event date (earlier dates first)
     * Secondary sort: by approval status priority (approved before pending before rejected)
     *
     * @param a the first event to compare
     * @param b the second event to compare
     * @return true if event 'a' should come before event 'b', false otherwise
     */
    private boolean comesBefore(Event a, Event b) {
        // Primary sort criterion: event date
        if (a.getEventDate().isBefore(b.getEventDate())) {
            return true;
        }

        // Secondary sort criterion: if dates are equal, sort by approval status priority
        if (a.getEventDate().isEqual(b.getEventDate())) {
            int pa = getPriority(a.getApprovalStatus());
            int pb = getPriority(b.getApprovalStatus());
            return pa < pb;  // Lower priority number means higher precedence
        }
        return false;
    }

    /**
     * Assigns priority values to approval statuses for sorting purposes.
     * Lower numbers indicate higher priority in the sort order.
     *
     * @param status the approval status string to evaluate
     * @return int priority value (0=highest priority, 3=lowest priority)
     */
    private int getPriority(String status) {
        if (status.equalsIgnoreCase("Approved")) return 0;   // Highest priority
        if (status.equalsIgnoreCase("Pending")) return 1;    // Medium priority
        if (status.equalsIgnoreCase("Rejected")) return 2;   // Lower priority
        return 3;  // Default/unknown status - lowest priority
    }

    /**
     * Returns the event at the current position without advancing the pointer.
     * Used to view the current event in the circular navigation.
     *
     * @return Event object at current position, or null if list is empty
     */
    public Event viewCurrentEvent() {
        if (current == null) {
            return null; // No events in the list
        }
        return current.data;
    }

    /**
     * Advances to the next event in the circular list and returns it.
     * Provides circular navigation - after the last event, moves to the first event.
     *
     * @return Event object at the new current position, or null if list is empty
     */
    public Event viewNextEvent() {
        if (current == null) {
            return null; // No events in the list
        }
        current = current.next;  // Move to next event (circular navigation)
        return current.data;
    }

    /**
     * Inner class representing a node in the circular linked list.
     * Each node contains an Event object and a reference to the next node.
     */
    class Node {
        Event data;    // The event stored in this node
        Node next;     // Reference to the next node in the circular list

        /**
         * Creates a new node with the given event data.
         * Initializes current pointer to head for navigation purposes.
         *
         * @param data the Event object to store in this node
         */
        Node(Event data) {
            this.data = data;
            this.next = null;
            current = head;  // Reset current pointer to head when new node is created
        }
    }
}
