package com.parcelsortx.model;

public class Parcel {
    public enum Status {
        IN_QUEUE,
        SORTED,
        DISPATCHED,
        RETURNED
    }

    private String parcelID;
    private String destinationCity;
    private int priority; // 1 = low, 2 = medium, 3 = high
    private String size; // "Small", "Medium", "Large"
    private int arrivalTick;
    private Status status;

    // Constructor
    public Parcel(String parcelID, String destinationCity, int priority, String size, int arrivalTick, Status status) {
        this.parcelID = parcelID;
        this.destinationCity = destinationCity;
        this.priority = priority;
        this.size = size;
        this.arrivalTick = arrivalTick;
        this.status = status;
    }

    // Getters and Setters
    public String getParcelID() {
        return parcelID;
    }

    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 1 || priority > 3) {
            throw new IllegalArgumentException("Priority must be 1 (low), 2 (medium), or 3 (high).");
        }
        this.priority = priority;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (!size.equalsIgnoreCase("Small") && !size.equalsIgnoreCase("Medium") && !size.equalsIgnoreCase("Large")) {
            throw new IllegalArgumentException("Size must be 'Small', 'Medium', or 'Large'.");
        }
        this.size = size;
    }

    public int getArrivalTick() {
        return arrivalTick;
    }

    public void setArrivalTick(int arrivalTick) {
        this.arrivalTick = arrivalTick;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ToString method for debugging or logging
    @Override
    public String toString() {
        return "Parcel{" +
                "parcelID='" + parcelID + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", priority=" + priority +
                ", size='" + size + '\'' +
                ", arrivalTick=" + arrivalTick +
                ", status=" + status +
                '}';
    }
}
