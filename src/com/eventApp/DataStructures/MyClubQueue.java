package com.eventApp.DataStructures;

import com.eventApp.Model.Club;

import java.util.List;

    /**
     * A fixed-size custom queue implementation for storing Club objects.
     * Supports basic queue operations such as enqueue, dequeue, and peek,
     * along with utility methods to check emptiness and retrieve all clubs.
     *
     * Designed specifically for use in the Event App to manage Club-related workflows.
     */
public class MyClubQueue {
    int rear, front, size;
    Club clubQueue[];

        /**
         * Initializes the queue with a fixed size.
         * Sets front and rear pointers to indicate an empty queue.
         *
         * @param size the maximum capacity of the queue
         */
    public MyClubQueue(int size) {
        this.front = -1;
        this.rear = -1;
        this.size = size;
        clubQueue = new Club[size];
    }

        /**
         * Adds a Club to the rear of the queue if there is space.
         * Updates front pointer if queue was previously empty.
         * Does nothing if the queue is already full.
         *
         * @param club the Club object to enqueue
         */
    public void enqueue(Club club) {
        if (rear == size - 1) {
            return;// Queue is full; cannot enqueue
        }
        if (isEmpty()) {
            front = 0;// First element enqueued
        }
        rear++;
        clubQueue[rear] = club;
    }

        /**
         * Removes and returns the Club at the front of the queue.
         * Adjusts pointers accordingly to maintain queue state.
         * Returns null if the queue is empty.
         *
         * @return the dequeued Club object, or null if empty
         */
    public Club dequeue() {
        if (isEmpty()) {
            return null;// Queue is empty; nothing to dequeue
        }
        Club temp = clubQueue[front];
        if (front == rear) { // Queue becomes empty after removal
            front = -1;
            rear = -1;
        } else {
            front++;
        }
        return temp;
    }

        /**
         * Returns the Club at the front of the queue without removing it.
         * Returns null if the queue is empty.
         *
         * @return the Club object at the front, or null if empty
         */
    public Club viewFirst() {
        if (isEmpty()) {
            return null;
        }
        return clubQueue[front];
    }

        /**
         * Checks if the queue is empty.
         *
         * @return true if the queue contains no Clubs, false otherwise
         */
    public boolean isEmpty() {
        return front == -1;
    }

        /**
         * Retrieves and removes all Clubs currently in the queue.
         * Empties the queue in the process.
         *
         * @return a List containing all Clubs previously enqueued
         */
    public List<Club> getAllClubs() {
        List<Club> clubs = new java.util.ArrayList<>();
        while (!isEmpty()) {
            clubs.add(dequeue());
        }
        return clubs;
    }
}
