package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

public class MyEventLL {
    class Node {
        Node next, prev;
        Event data;

        public Node(Event data) {
            this.next = null;
            this.prev = null;
            this.data = data;
        }
    }

    Node first = null;

    void insert(Event data) {
        Node n = new Node(data);
        if (first == null) {
            first = n;
            return;
        }
        if (first.next == null || first.next.data.getEventDate().isAfter(data.getEventDate())) {
            if (first.data.getEventDate().isAfter(data.getEventDate())) {
                n.next = first;
                first.prev = n;
                first = n;
                return;
            } else {
                n.prev = first;
                first.next = n;
                return;
            }
        }
        Node temp = first;
        while (temp.next != null && temp.next.data.getEventDate().isBefore(data.getEventDate())) {
            temp = temp.next;
        }
        n.next = temp.next;
        n.prev = temp;
        if (temp.next != null)
            temp.next.prev = n;
        temp.next = n;
    }
}
