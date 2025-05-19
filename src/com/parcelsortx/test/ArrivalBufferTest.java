package com.parcelsortx.test;

import com.parcelsortx.core.ArrivalBuffer;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class ArrivalBufferTest {
	public static void main(String[] args) {
	ArrivalBuffer buffer = new ArrivalBuffer(3); // Maksimum 3 paket alabilir

	Parcel p1 = new Parcel("P001", "İstanbul", 2, "Small", 1, Status.InQueue);
	Parcel p2 = new Parcel("P002", "Ankara", 1, "Large", 1, Status.Sorted);
	Parcel p3 = new Parcel("P003", "İzmir", 3, "Medium", 1, Status.Dispatched);
	Parcel p4 = new Parcel("P004", "Bursa", 2, "Small", 1, Status.Returned);


    buffer.enqueue(p1);
    buffer.enqueue(p2);
    buffer.enqueue(p3);
    buffer.enqueue(p4); // Bu eklenmeyecek, çünkü kapasite dolu

    System.out.println("Mevcut kuyruk boyutu: " + buffer.size());

    System.out.println("İlk paket: " + buffer.peek().getParcelID());
    Parcel removed = buffer.dequeue();
    System.out.println("Çıkarılan paket: " + removed.getParcelID());

    System.out.println("Yeni ilk paket: " + buffer.peek().getParcelID());
}
}
