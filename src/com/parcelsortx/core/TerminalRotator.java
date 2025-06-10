package com.parcelsortx.core;
import java.util.Locale; // Locale sınıfını import etmeyi unutmayın

import com.parcelsortx.model.Parcel;

public class TerminalRotator {
	private static class Node {
		String cityName;
		Node next;

		Node(String cityName) {
			this.cityName = cityName;
		}
	}

	private Node current; 

	public void initializeFromCityList(String[] cities) {
		if (cities == null || cities.length == 0)
			return;

		 Node head = new Node(cities[0].trim().toLowerCase(new Locale("tr", "TR")));
	        Node prev = head;
		for (int i = 1; i < cities.length; i++) {
			Node newNode = new Node(cities[i].trim().toLowerCase(new Locale("tr", "TR"))); 
            prev.next = newNode;
            prev = newNode;
		}
		prev.next = head; // Circular
		current = head; // Başlangıç noktası
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
		if (current == null)
			return;

		Node temp = current;
		do {
			System.out.print(temp.cityName + " -> ");
			temp = temp.next;
		} while (temp != current);
		System.out.println("(back to " + current.cityName + ")");
	}

}
