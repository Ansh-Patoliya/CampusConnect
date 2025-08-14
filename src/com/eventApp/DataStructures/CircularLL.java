package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

public class CircularLL {
    public boolean isEmpty() {
        return head == null;
    }

    class Node{
        Event data;
        Node next;
        Node(Event data) {
            this.data = data;
            this.next = null;
            current=head;
        }
    }
    private Node head=null;
    private Node current;

    // insert sorted by date
    public void add(Event data){
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
            return a.getApprovalStatus().compareTo(b.getApprovalStatus()) < 0;
        }
        return false;
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
}
