package com.parcelsortx.test;

import com.parcelsortx.core.Stack;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class StackTest {
	public static void main(String[] args) {
		Stack returnStack = new Stack();

		Parcel highPriorityParcel  = new Parcel("P001", "Istanbul", 2, "medium", 10, Status.Returned);
		Parcel mediumPriorityParcel  = new Parcel("P002", "Ankara", 3, "large", 11, Status.Returned);
		Parcel lowPriorityParcel  = new Parcel("P003", "Izmir", 1, "small", 12, Status.Returned);
		   testPushOperations(returnStack, highPriorityParcel, mediumPriorityParcel, lowPriorityParcel);
	        testPopOperations(returnStack);
	        testEmptyStackOperations(returnStack);
	    }
	    
	    public static void testPushOperations(Stack stack, Parcel... parcels) {
	        System.out.println("\n=== Push İşlemleri Testi ===");
	        for (Parcel p : parcels) {
	            stack.push(p);
	        }
	        stack.showAllParcels();
	    }
	    
	    public static void testPopOperations(Stack stack) {
	        System.out.println("\n=== Pop İşlemleri Testi ===");
	        while (stack.size() > 0) {
	            stack.pop();
	        }
	    }
	    
	    public static void testEmptyStackOperations(Stack stack) {
	        System.out.println("\n=== Boş Stack Testleri ===");
	        stack.pop(); // Boş stack'ten pop
	        stack.peek(); // Boş stack'te peek
	        stack.showAllParcels(); // Boş stack'i gösterme
	    }

}
