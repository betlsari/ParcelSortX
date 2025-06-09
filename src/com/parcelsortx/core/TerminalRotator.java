package com.parcelsortx.core;

public class TerminalRotator {
    private static class Node {
        String cityName;
        Node next;

        Node(String cityName) {
            this.cityName = cityName;
        }
    }

    private Node current; // currentActiveTerminal

    public void initializeFromCityList(String[] cities) {
        if (cities == null || cities.length == 0) return;

        Node head = new Node(cities[0]);
        Node prev = head;

        for (int i = 1; i < cities.length; i++) {
            Node newNode = new Node(cities[i]);
            prev.next = newNode;
            prev = newNode;
        }
        prev.next = head; // Circular
        current = head;   // Başlangıç noktası
    }

    public void advanceTerminal() {
        if (current != null) {
            current = current.next;
        }
    }

    public String getActiveTerminal() {
        return current != null ? current.cityName : null;
    }

    public void printTerminalOrder() {
        if (current == null) return;

        Node temp = current;
        do {
            System.out.print(temp.cityName + " -> ");
            temp = temp.next;
        } while (temp != current);
        System.out.println("(back to " + current.cityName + ")");
    }

	public String getCurrentTerminal() {
		
		return null;
	}
}
