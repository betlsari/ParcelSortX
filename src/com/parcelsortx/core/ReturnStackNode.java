package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class ReturnStackNode {
	Parcel parcel;
    ReturnStackNode next;
	
	public ReturnStackNode(Parcel parcel) {
		this.parcel = parcel;
		this.next = null;
	}
}
