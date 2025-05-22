package com.parcelsortx.core;
import java.util.LinkedList;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.core.ArrivalBuffer;


public class BinarySearchTreeNode {
	String cityName;
	ArrivalBuffer<Parcel> parcelList;
	BinarySearchTreeNode left;
	BinarySearchTreeNode right;
	
	 public BinarySearchTreeNode(String cityName, Parcel initialParcel) {
	        this.cityName = cityName;
	        this.parcelList = new LinkedList<>(); // LinkedList, Queue arayüzünü uygular.
	        this.parcelList.add(initialParcel);
	        this.left = null;
	        this.right = null;
	    }
	 public int getParcelCount() {
	        return parcelList.size();
	    }

	    public String getCityName() {
	        return cityName;
	    }

	    public Queue<Parcel> getParcelList() {
	        return parcelList;
	    }
}
