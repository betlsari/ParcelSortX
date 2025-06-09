package com.parcelsortx.core;

import java.util.ArrayList;
import java.util.List;

import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class ParcelTracker {
	  private int dispatchedParcelCount;     // Toplam sevk edilen koli sayısı
	    private int returnedParcelCount;       // Toplam iade edilen koli sayısı

    private static class Entry {
        String key; // parcelID
        Parcel value;
        int returnCount;
        int dispatchTick;
        Entry next;

        Entry(String key, Parcel value) {
            this.key = key;
            this.value = value;
            this.returnCount = 0;
            this.dispatchTick = -1;
        }
    }

    private static final int TABLE_SIZE = 50;
    private Entry[] table;

    public ParcelTracker() {
        table = new Entry[TABLE_SIZE];
        this.dispatchedParcelCount = 0;
        this.returnedParcelCount = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % TABLE_SIZE;
    }

    public void insert(String parcelID, Parcel parcel) {
        if (exists(parcelID)) return;
        int index = hash(parcelID);
        Entry newEntry = new Entry(parcelID, parcel);
        newEntry.next = table[index];
        table[index] = newEntry;
    }

    public void updateStatus(String parcelID, Status dispatched) {
    	 Entry e = find(parcelID);
         if (e != null) {
             Status oldStatus = e.value.getStatus(); // Mevcut durumu al

             // Durum değişikliği Dispatch'e ise sayacı artır
             if (dispatched == Status.Dispatched && oldStatus != Status.Dispatched) {
                 dispatchedParcelCount++;
             }
             // Durum değişikliği Returned'a ise sayacı artır
             else if (dispatched == Status.Returned && oldStatus != Status.Returned) {
                 returnedParcelCount++;
             }
             
             e.value.setStatus(dispatched); // Durumu güncelle
    }}

    public Parcel get(String parcelID) {
        Entry e = find(parcelID);
        return (e != null) ? e.value : null;
    }

    public boolean exists(String parcelID) {
        return find(parcelID) != null;
    }

    public void incrementReturnCount(String parcelID) {
        Entry e = find(parcelID);
        if (e != null) {
            e.returnCount++;
        }
    }

    public void setDispatchTick(String parcelID, int tick) {
        Entry e = find(parcelID);
        if (e != null) {
            e.dispatchTick = tick;
        }
    }

    private Entry find(String parcelID) {
        int index = hash(parcelID);
        Entry current = table[index];
        while (current != null) {
            if (current.key.equals(parcelID)) return current;
            current = current.next;
        }
        return null;
    }

    public void printTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Entry e = table[i];
            if (e != null) {
                System.out.print("Bucket " + i + ": ");
                while (e != null) {
                    System.out.print("[" + e.key + " - " + e.value.getStatus() + "] -> ");
                    e = e.next;
                }
                System.out.println("null");
            }
        }
    }
    public int getReturnCount(String parcelID) {
        Entry e = find(parcelID);
        return (e != null) ? e.returnCount : 0;
    }
    //betül salağı  lalal

    public Parcel getMostDelayedParcel() {
        Parcel mostDelayed = null;
        int maxDelay = -1;

        for (int i = 0; i < TABLE_SIZE; i++) {
            Entry e = table[i];
            while (e != null) {
                int delay = e.dispatchTick - e.value.getArrivalTrick();
                if (e.dispatchTick > 0 && delay > maxDelay) {
                    maxDelay = delay;
                    mostDelayed = e.value;
                }
                e = e.next;
            }
        }

        return mostDelayed;
    }


	public String[] getAllParcelRecords() {
	    List<String> records = new ArrayList<>();

	    for (int i = 0; i < TABLE_SIZE; i++) {
	        Entry e = table[i];
	        while (e != null) {
	            String record = "ParcelID: " + e.key +
	                            ", Status: " + e.value.getStatus() +
	                            ", Return Count: " + e.returnCount +
	                            ", Dispatch Tick: " + e.dispatchTick;
	            records.add(record);
	            e = e.next;
	        }
	    }

	    return records.toArray(new String[0]);
	}


	public int getTotalParcels() {
	    int count = 0;
	    for (int i = 0; i < TABLE_SIZE; i++) {
	        Entry e = table[i];
	        while (e != null) {
	            count++;
	            e = e.next;
	        }
	    }
	    return count;
	}


	  public int getDispatchedParcelCount() {
	        return dispatchedParcelCount;
	    }

	    // Toplam iade edilen koli sayısını döndürür
	    public int getReturnedParcelCount() {
	        return returnedParcelCount;
	    }
}
