package com.parcelsortx.model;
public class Parcel{
	public enum Status{
		InQueue,
		Sorted,
		Dispatched,
		Returned
	}
	
	private String parcelID; //must be unique
	private String destinationCity; //"Istanbul"
	private int priority; // 1=low,2=medium,3=high
	private String size; //"small","medium","large"
	private int arrivalTrick;
	private Status status;
	
	
	
	public Parcel(String parcelID,String destinationCity,int priority, String size, int arrivalTrick, Status status) {
		this.parcelID=parcelID;
		this.destinationCity=destinationCity;
		this.priority=priority;
		this.size=size;
		this.arrivalTrick=arrivalTrick;
		this.status=status;
	}
	
	//getter ve setter kullanımı - private değişkenlere erişmek için getter ve stter kullanıyoruz. Public yapıp kullanmayabilirdik ama güvenli olmaz.
	public String getParcelID(){ 
		return parcelID;
	}
	public void setParcelID(String parcelID) {
		this.parcelID=parcelID;
	}
	
	public String getDestinationCity() {
		return destinationCity;		
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity=destinationCity;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		switch(priority) {
		case 1,2,3 -> this.priority=priority;
		
		default -> throw new IllegalArgumentException( //parametre hatası olduğunu belirtmek için hazır sınıf
				
				"geçersiz! Lütfen 1(low),2(medium) veya 3(high) girin"
				);
		}	
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		switch(size) {
		case "small","medium","large" -> this.size=size;
		default -> throw new IllegalArgumentException( // throw kelimesi istisna(exception) sağlar, sarı yazan sınıf ise exception sınıfı, metoda yanlış veya geçersiz bir parametre verildiğinde kullanılıyor.
				"Geçersiz size! Lütfen 'small','medium' veya 'large' seçin!");
		}
	}
	
		
	public int getArrivalTrick() {
		return arrivalTrick;
	}
	public void setArrivalTrick(int arrivalTrick) {
		this.arrivalTrick=arrivalTrick;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status=status;
	}
	
	
	@Override //metodun üst sınıftaki bir metodu yeniden yazdığını belirtir
	public String toString() {
		return "Parcel: {" + "parcelID: " + parcelID + "\n" +
	           "Destination City: " + destinationCity+ "\n" + 
	           "Priority: " + priority +"\n"+
	           "Size: " + size +  "\n"+ 
	           "Arrival Trick: " + arrivalTrick+"\n" + 
	           "Status: " + status + "}";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}