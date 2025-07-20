package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

public class MyEventLL {
    class Node {
        Event data;
        Node next;
        Node prev;

        Node(Event data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    Node first = null;
    Node current = null;

    public void insert(Event data) {
        Node n = new Node(data);

        if (first == null) {
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

    public Event viewCurrentEvent() {
        if (current == null) {
            return null;
        } else {
            return current.data;
        }
    }

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

    public Event viewPreviousEvent() {
        if (current != null) {
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

    public void resetPointer() {
        current = first;
    }
}
