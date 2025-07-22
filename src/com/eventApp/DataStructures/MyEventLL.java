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

    public boolean isEmpty() {
        return first==null;
    }

    class Node {
        Node next, prev;
        Event data;

        Node(Event data) {
            this.data = data;
            this.next = null;
            this.prev = null;
            size++;
        }
    }

    Node first = null;
    Node current = null;

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

    public Event deleteAtCurrent() {
        if (isEmpty()) {
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

        if (current == first) {
            current = first; // Reset current to first node
        } else {
            current = current.prev; // Move current to previous node
        }

        size--;
        return data;
    }
    public Event viewCurrentEvent() {
        if (isEmpty()) {
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

    public void resetPointer() {
        current = first;
    }

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
