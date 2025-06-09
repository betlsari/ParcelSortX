package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class ReturnStack {
	/* ECE */
	/*
	 * Bu yığın, yanlış yönlendirilen veya teslim 
	 * edilemeyen kargoları geçici olarak
	 * depolamak için kullanılacak.
	 */

	/* Add color-coded log entries for returned parcels for easier debugging. */ 
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[31m";
	private static final String YELLOW = "\u001B[33m";
	private static final String CYAN = "\u001B[36m";
	private static final String GREEN = "\u001B[32m";
	
	
	private ReturnStackNode top;
	private int size;

	public ReturnStack() {
		this.top = null;
		this.size = 0;
	}

	public void push(Parcel parcel) {
		ReturnStackNode newNode = new ReturnStackNode(parcel);
		newNode.next = top;
		top = newNode;
		size++;

		System.out.println( RED + " Stack pushed :  "
		+ parcel.getParcelID() +" " +  "to" + " " 
				+parcel.getDestinationCity() + RESET);

	}

	public Parcel pop() {
		if (top == null) {
			System.out.println(YELLOW + "empty stack" + RESET);
			return null;
		}
		Parcel poppedParcel = top.parcel;
		top = top.next;
		size--;

		System.out.println(CYAN + "Popped parcel : "+ " " +
		poppedParcel.getParcelID() + " "+ "size : " + size + RESET);
		return poppedParcel;
	}

	public Parcel peek() {
		if (top == null) {
			return null;
		}
		return top.parcel;
	}

	public int size() {
		return size;
	}

	public void showAllParcels() {
		if (top == null) {
			System.out.println(YELLOW + " Stack is empty!" + RESET);
			return;
		}
		System.out.println(GREEN + "stack size is : " + size + RESET);

		ReturnStackNode current = top;
		int position = 1;

		while (current != null) {
			Parcel pc = current.parcel;
			System.out.println(RED +  position + "." + pc.getParcelID() +" "+ "to" + " " + pc.getDestinationCity() +" "+ "priority: "
					+ pc.getPriority() +  " "+ "arrived at tick" + " "+  pc.getArrivalTrick() + RESET);
			current = current.next;
			position++;

		}
		
	}
	public boolean isEmpty() {
	    return top == null;
	}


	static {
	    if (System.getProperty("os.name").toLowerCase().contains("win")) {
	        try {
	            new ProcessBuilder("cmd", "/c", "color").inheritIO().start().waitFor();
	        } catch (Exception e) {
	            // Renkler desteklenmiyorsa normal devam et
	        }
	    }
	}

	
}
