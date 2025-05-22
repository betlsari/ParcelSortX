package com.parcelsortx.test;

import java.util.Queue;

import com.parcelsortx.core.DestinationSorter;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class BSTTest {

	  public static void main(String[] args) {
	        DestinationSorter bst = new DestinationSorter();

	        Parcel p1 = new Parcel("P001", "istanbul", 2, "medium", 1, Status.Sorted);
	        Parcel p2 = new Parcel("P002", "Ankara", 1, "small", 2, Status.Sorted);
	        Parcel p3 = new Parcel("P003", "izmir", 3, "large", 3, Status.Sorted);
	        Parcel p4 = new Parcel("P004", "istanbul", 1, "small", 4, Status.Sorted);
	        Parcel p5 = new Parcel("P005", "Antalya", 2, "medium", 5, Status.Sorted);

	        bst.insertParcel(p1);
	        bst.insertParcel(p2);
	        bst.insertParcel(p3);
	        bst.insertParcel(p4);
	        bst.insertParcel(p5);

	        System.out.println("\n sehirler alfabetik sirayla:");
	        bst.inOrderTraversal();

	        System.out.println("\n 'istanbul'daki kargo sayisi: " + bst.countCityParcels("istanbul"));
	        System.out.println("'izmir'deki kargo sayisi: " + bst.countCityParcels("izmir"));
	        System.out.println(" 'Trabzon'daki kargo sayisi: " + bst.countCityParcels("Trabzon")); // 

	        // 🗑 5. Kargo sil (Parcel iD ile)
	        System.out.println("\n P004 iD'li kargo istanbul'dan siliniyor...");
	        boolean removed = bst.removeParcel("istanbul", "P004");
	        System.out.println("Silme basarili mi? " + removed);
	        System.out.println(" istanbul'daki guncel kargo sayisi: " + bst.countCityParcels("istanbul"));

	        // 📏 6. Agaç istatistikleri
	        System.out.println("\n BST Yuksekligi: " + bst.getHeight());
	        System.out.println(" Toplam sehir dugumu: " + bst.getNumberOfNodes());
	        System.out.println(" En yogun sehir: " + bst.getCityWithHighestParcelLoad() + 
	                           " (" + bst.getHighestParcelLoadCount() + " kargo)");
	    }
	}
