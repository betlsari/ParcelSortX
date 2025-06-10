package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class ArrivalBuffer<T extends Parcel> { 
	private static class Node<T> {
		T data;
		Node<T> next;

		public Node(T data) {
			this.data = data;
			this.next = null;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int QUEUE_CAPACITY;

	public ArrivalBuffer(int QUEUE_CAPACITY) {
		this.QUEUE_CAPACITY = QUEUE_CAPACITY;
		this.tail = null;
		this.size = 0;
		this.head = null;

	}

	public boolean isFull() {
		return size >= QUEUE_CAPACITY;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void enqueue(T parcel) {
		if (isFull()) {
			System.out.println("Queue is full. Package: " + parcel.getParcelID() + " " + parcel.getDestinationCity()
					+ " not found.");
			return;
		}

		Node<T> newNode = new Node<>(parcel);

		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		size++;
	}

	public T dequeue() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
			return null;
		}

		T p = head.data; // bir elemanı çıkarırken bu elemanı bir değişkende tutuyoruz, en öndeki ilk
							// çıkacağı için head verisini attık
		head = head.next;
		size--;

		if (head == null) {
			tail = null;
		}
		return p;

	}

	public T peek() {
		if (isEmpty()) {
			return null;
		}
		return head.data;
	}

	public int size() {
		return size;
	}

	public boolean removeByID(String parcelID) {
		if (isEmpty())
			return false;

		Node<T> dummy = new Node<>(null);
		dummy.next = head;
		Node<T> prev = dummy;
		Node<T> current = head;

		while (current != null) {
			if (((Parcel) current.data).getParcelID().equals(parcelID)) {
				prev.next = current.next;
				if (current == tail) {
					tail = prev == dummy ? null : prev;
				}
				head = dummy.next;
				size--;
				return true;
			}
			prev = current;
			current = current.next;
		}
		return false;
	}

}
