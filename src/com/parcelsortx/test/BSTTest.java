package com.parcelsortx.test;

import java.util.Queue;

import com.parcelsortx.core.BinarySearchTree;
import com.parcelsortx.model.Parcel;

public class BSTTest {

	public static void main(String[] args) {
		BinarySearchTree sorter = new BinarySearchTree();

        // Örnek Parcel nesneleri oluşturma
        Parcel p1 = new Parcel("P001", "Ankara", 2, "medium", 10, Parcel.Status.Sorted);
        Parcel p2 = new Parcel("P002", "Izmir", 3, "large", 12, Parcel.Status.Sorted);
        Parcel p3 = new Parcel("P003", "Ankara", 1, "small", 15, Parcel.Status.Sorted);
        Parcel p4 = new Parcel("P004", "Istanbul", 2, "medium", 18, Parcel.Status.Sorted);
        Parcel p5 = new Parcel("P005", "Izmir", 2, "small", 20, Parcel.Status.Sorted);
        Parcel p6 = new Parcel("P006", "Ankara", 3, "large", 22, Parcel.Status.Sorted);
        Parcel p7 = new Parcel("P007", "Bursa", 1, "small", 25, Parcel.Status.Sorted);
        Parcel p8 = new Parcel("P008", "Istanbul", 3, "large", 28, Parcel.Status.Sorted);

        // Paketleri ekleme
        sorter.insertParcel(p1);
        sorter.insertParcel(p2);
        sorter.insertParcel(p3);
        sorter.insertParcel(p4);
        sorter.insertParcel(p5);
        sorter.insertParcel(p6);
        sorter.insertParcel(p7);
        sorter.insertParcel(p8);

        System.out.println("BST Yüksekliği: " + sorter.getHeight());
        System.out.println("Toplam Şehir Düğümü Sayısı: " + sorter.getNumberOfNodes());

        // In-Order Traversal
        sorter.inOrderTraversal();

        // Belirli bir şehre ait paketleri sorgulama
        Queue<Parcel> ankaraParcels = sorter.getCityParcels("Ankara");
        if (ankaraParcels != null) {
            System.out.println("\nAnkara'ya giden paketler (" + ankaraParcels.size() + " adet):");
            for (Parcel p : ankaraParcels) {
                System.out.println("- " + p.getParcelID() + " (" + p.getPriority() + ")");
            }
        }

        // Belirli bir şehirdeki paket sayısını sorgulama
        System.out.println("\nIzmir'deki paket sayısı: " + sorter.countCityParcels("Izmir"));
        System.out.println("Antalya'daki paket sayısı: " + sorter.countCityParcels("Antalya"));

        // Paket kaldırma
        System.out.println("\nP003 (Ankara) paketi kaldırılıyor: " + sorter.removeParcel("Ankara", "P003"));
        System.out.println("Ankara'daki yeni paket sayısı: " + sorter.countCityParcels("Ankara"));
        System.out.println("P009 (Bilinmeyen) paketi kaldırılıyor: " + sorter.removeParcel("Bursa", "P009")); // Olmayan bir paket

        System.out.println("\nGüncel BST Yüksekliği: " + sorter.getHeight());
        System.out.println("Güncel Toplam Şehir Düğümü Sayısı: " + sorter.getNumberOfNodes()); // Düğüm sayısı değişmez çünkü sadece paketi sildik, düğümü değil.

        // En yüksek paket yüküne sahip şehir
        System.out.println("\nEn Yüksek Paket Yüküne Sahip Şehir: " + sorter.getCityWithHighestParcelLoad() + 
                           " (" + sorter.getHighestParcelLoadCount() + " paket)");

        // Tekrar in-order traversal
        sorter.inOrderTraversal();
    }

	}
