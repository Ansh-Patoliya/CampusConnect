package com.eventApp.DataStructures;

import com.eventApp.Model.Club;

public class ClubApprovalQueue {
    int rear, front, size;
    Club clubQueue[];

    public ClubApprovalQueue(int size) {
        this.front = -1;
        this.rear = -1;
        this.size = size;
        clubQueue = new Club[size];
    }

    void enqueue(Club club) {
        if (rear == size - 1) {
            return;
        }
        if (isEmpty()) {
            front = 0;
        }
        rear++;
        clubQueue[rear] = club;
    }

    public Club dequeue() {
        if (isEmpty()) {
            return null;
        }
        Club temp = clubQueue[front];
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front++;
        }
        return temp;
    }

    Club peek() {
        if (isEmpty()) {
            return null;
        }
        return clubQueue[front];
    }

    public boolean isEmpty() {
        return front == -1;
    }
}
