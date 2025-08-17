package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

public class CircularLL {
    private Node head = null;
    private Node current;

    public boolean isEmpty() {
        return head == null;
    }

    // insert sorted by date
    public void add(Event data) {
        Node n = new Node(data);

        if (head == null) {
            head = n;
            n.next = head;
            return;
        }
        if (comesBefore(data, head.data)) {
            Node last = head;
            while (last.next != head) {
                last = last.next;
            }
            last.next = n;
            n.next = head;
            head = n;
            return;
        }
        Node temp = head;
        while (temp.next != head && comesBefore(temp.next.data, data)) {
            temp = temp.next;
        }
        n.next = temp.next;
        temp.next = n;
    }

    private boolean comesBefore(Event a, Event b) {
        if (a.getEventDate().isBefore(b.getEventDate())) {
            return true;
        }
        if (a.getEventDate().isEqual(b.getEventDate())) {
            int pa = getPriority(a.getApprovalStatus());
            int pb = getPriority(b.getApprovalStatus());
            return pa < pb;
        }
        return false;
    }

    private int getPriority(String status) {
        if (status.equalsIgnoreCase("Approved")) return 0;
        if (status.equalsIgnoreCase("Pending")) return 1;
        if (status.equalsIgnoreCase("Rejected")) return 2;
        return 3;
    }

    public Event viewCurrentEvent() {
        if (current == null) {
            return null; // No events in the list
        }
        return current.data;
    }

    public Event viewNextEvent() {
        if (current == null) {
            return null; // No events in the list
        }
        current = current.next;
        return current.data;
    }

    class Node {
        Event data;
        Node next;

        Node(Event data) {
            this.data = data;
            this.next = null;
            current = head;
        }
    }
}
