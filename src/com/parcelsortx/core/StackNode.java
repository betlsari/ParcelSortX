package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class StackNode {
	Parcel parcel;
    StackNode next;
	
	public StackNode(Parcel parcel) {
		this.parcel = parcel;
		this.next = null;
	}
}
