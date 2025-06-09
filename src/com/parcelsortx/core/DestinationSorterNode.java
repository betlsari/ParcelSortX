package com.parcelsortx.core;
import java.util.LinkedList;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.core.ArrivalBuffer;


public class DestinationSorterNode {
	String cityName;
	ArrivalBuffer<Parcel> parcelList;
	DestinationSorterNode left;
	DestinationSorterNode right;
	
	 public DestinationSorterNode(String cityName, Parcel initialParcel) {
	      this.cityName = cityName;
	        this.parcelList = new ArrivalBuffer<>(15); // kapasiteyi yeterli bir sayıyla sabit ver
	        this.parcelList.enqueue(initialParcel);
	        this.left = null;
	        this.right = null;
	    }
	 public int getParcelCount() {
	        return parcelList.size();
	    }

	    public String getCityName() {
	        return cityName;
	    }

	    public ArrivalBuffer<Parcel> getParcelList() {
	        return parcelList;
	    }
}
