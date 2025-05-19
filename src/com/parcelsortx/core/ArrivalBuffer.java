package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class ArrivalBuffer {
	private static class Node{
		Parcel data;
		Node next;
		
		public Node(Parcel data){
			this.data=data;
			this.next=null;
		}
	}
	private Node head;
	private Node tail;
	private int size;
	private int QUEUE_CAPACITY;
	
	public ArrivalBuffer(int QUEUE_CAPACITY) {
		this.QUEUE_CAPACITY=QUEUE_CAPACITY;
		this.tail=null;
		this.size=0;
		this.head=null;
		
	}
	public boolean isFull() {
	    return size >= QUEUE_CAPACITY;
	}
	public boolean isEmpty() {
		return head==null;
		}
	
	public void enqueue(Parcel parcel) {
		if (isFull()) {
			System.out.println("Queue is full" + "Package: " + parcel.getParcelID() + parcel.getDestinationCity() + "not found.");
		return;
		}
		
		Node newNode= new Node(parcel);	
		
		if(isEmpty()) {
			head=newNode;
			tail=newNode;
		}else {
			tail.next=newNode;
			tail=newNode;
		}
		size++;
	}
			
		
	public Parcel dequeue() {
		if (isEmpty()) {
		System.out.println("Queue is empty");
		return null;
		}
		
		Parcel p= head.data; //bir elemanı çıkarırken bu elemanı bir değişkende tutuyoruz, en öndeki ilk çıkacağı için head verisini attık
		head=head.next;
		size--;
		
		if(head==null) {
			tail=null;
		}
		return p;
		
	}
	public Parcel peek() {
		if(isEmpty()) {
			return null;
		}
		return head.data;
	}
	public int size() {
        return size;
    }

	
}

