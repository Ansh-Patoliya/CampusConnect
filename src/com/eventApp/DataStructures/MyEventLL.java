package com.eventApp.DataStructures;

import com.eventApp.Model.Event;

public class MyEventLL {
    public int size() {
        return size;
    }

    int size = 0;

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

    class Node {
        Node next, prev;
        Event data;

        public Node(Event data) {
            this.next = null;
            this.prev = null;
            this.data = data;
            size++;
        }
    }

    Node first = null;

    public void insert(Event data) {
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
