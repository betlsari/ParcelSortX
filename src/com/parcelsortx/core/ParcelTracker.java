package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class ParcelTracker {
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

    public void updateStatus(String parcelID, Status newStatus) {
        Entry e = find(parcelID);
        if (e != null) {
            e.value.setStatus(newStatus);
        }
    }

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
}
