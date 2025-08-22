package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

/**
 * Custom doubly linked list implementation for managing Event objects.
 * Supports insertion sorted by event date, navigation, and deletion at the current pointer.
 * Maintains size and current node references for traversal and manipulation.
 */
public class MyEventLL {
    int size = 0;          // Tracks the number of events in the list
    Node first = null;     // Reference to the first node in the list
    Node current = null;   // Pointer used for navigation within the list

    /**
     * Returns the number of events currently stored in the list.
     *
     * @return the size of the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Retrieves the Event at the specified index.
     * Throws IndexOutOfBoundsException if index is invalid.
     *
     * @param i the index of the event to retrieve
     * @return the Event at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Event get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        Node temp = first;
        for (int j = 0; j < i; j++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return true if there are no events, false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Inner class representing a node in the doubly linked list.
     * Each node holds an Event and references to previous and next nodes.
     */
    class Node {
        Node next, prev;  // References to adjacent nodes
        Event data;       // The Event stored in this node

        /**
         * Creates a new node containing the given Event.
         * Increments the size of the list upon creation.
         *
         * @param data the Event to store in the node
         */
        Node(Event data) {
            this.data = data;
            this.next = null;
            this.prev = null;
            size++;
        }
    }

    /**
     * Inserts an Event into the list maintaining ascending order by event date.
     * Updates current pointer to the first node after insertion.
     *
     * @param data the Event to insert
     */
    public void insert(Event data) {
        Node n = new Node(data);

        if (isEmpty()) {
            first = n;
            current = first;
        } else {
            if (first.data.getEventDate().isAfter(data.getEventDate())) {
                n.next = first;
                first.prev = n;
                first = n;
                current = first;
            } else {
                Node temp = first;
                while (temp.next != null && temp.next.data.getEventDate().isBefore(data.getEventDate())) {
                    temp = temp.next;
                }

                n.next = temp.next;
                n.prev = temp;

                if (temp.next != null) {
                    temp.next.prev = n;
                }
                temp.next = n;
            }
        }
    }

    /**
     * Deletes the Event at the current pointer.
     * Adjusts pointers to maintain list integrity.
     * Advances current pointer to the next node or resets to first if needed.
     *
     * @return the deleted Event, or null if list is empty
     */
    public Event deleteAtCurrent() {
        if (isEmpty() || current == null) {
            return null;
        }

        Event data = current.data;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            first = current.next; // If current is the first node
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        }

        if(current != first){
            current = current.next; // Move the current pointer forward only if it's not pointing to the new head
        }

        size--;
        return data;
    }

    /**
     * Returns the Event at the current pointer without modifying it.
     * Returns null if the list is empty.
     *
     * @return the current Event or null if empty
     */
    public Event viewCurrentEvent() {
        if (current == null) {
            return null;
        } else {
            return current.data;
        }
    }

    /**
     * Moves the current pointer to the next Event and returns it.
     * Returns null if at the end of the list or if current is null.
     *
     * @return the next Event, or null if none exists
     */
    public Event viewNextEvent() {
        if (current != null) {
            if (current.next != null) {
                current = current.next;
                return current.data;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Moves the current pointer to the previous Event and returns it.
     * Returns null if at the start of the list or if list is empty.
     *
     * @return the previous Event, or null if none exists
     */
    public Event viewPreviousEvent() {
        if (!isEmpty()) {
            if (current.prev != null) {
                current = current.prev;
                return current.data;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Resets the current pointer to the first node in the list.
     */
    public void resetPointer() {
        current = first;
    }

    /**
     * Adds an Event to the end of the list.
     * Updates current pointer to the first node after addition.
     *
     * @param data the Event to add at the end
     */
    public void addLast(Event data) {
        Node n = new Node(data);
        if (isEmpty()) {
            first = n;
            current = first;
        } else {
            Node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
            n.prev = temp;
        }
        size++;
    }
}
