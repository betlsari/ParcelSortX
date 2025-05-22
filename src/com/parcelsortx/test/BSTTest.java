package com.parcelsortx.test;

import java.util.Queue;

import com.parcelsortx.core.BinarySearchTree;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

public class BSTTest {

	  public static void main(String[] args) {
	        BinarySearchTree bst = new BinarySearchTree();

	        // 📦 1. Parcel nesneleri oluştur
	        Parcel p1 = new Parcel("P001", "Istanbul", 2, "medium", 1, Status.Sorted);
	        Parcel p2 = new Parcel("P002", "Ankara", 1, "small", 2, Status.Sorted);
	        Parcel p3 = new Parcel("P003", "Izmir", 3, "large", 3, Status.Sorted);
	        Parcel p4 = new Parcel("P004", "Istanbul", 1, "small", 4, Status.Sorted);
	        Parcel p5 = new Parcel("P005", "Antalya", 2, "medium", 5, Status.Sorted);

	        // 🌳 2. Ağaç yapısına ekle
	        bst.insertParcel(p1);
	        bst.insertParcel(p2);
	        bst.insertParcel(p3);
	        bst.insertParcel(p4); // Aynı şehre ikinci kargo
	        bst.insertParcel(p5);

	        // 🔍 3. In-order traversal (şehirleri alfabetik sırayla yazdır)
	        System.out.println("\n🗂 Şehirler alfabetik sırayla:");
	        bst.inOrderTraversal();

	        // 📊 4. Belirli şehirlerdeki kargo sayısını kontrol et
	        System.out.println("\n📦 'Istanbul'daki kargo sayısı: " + bst.countCityParcels("Istanbul"));
	        System.out.println("📦 'Izmir'deki kargo sayısı: " + bst.countCityParcels("Izmir"));
	        System.out.println("📦 'Trabzon'daki kargo sayısı: " + bst.countCityParcels("Trabzon")); // olmayan şehir

	        // 🗑 5. Kargo sil (Parcel ID ile)
	        System.out.println("\n🗑 P004 ID'li kargo Istanbul'dan siliniyor...");
	        boolean removed = bst.removeParcel("Istanbul", "P004");
	        System.out.println("✅ Silme başarılı mı? " + removed);
	        System.out.println("📦 Istanbul'daki güncel kargo sayısı: " + bst.countCityParcels("Istanbul"));

	        // 📏 6. Ağaç istatistikleri
	        System.out.println("\n🌲 BST Yüksekliği: " + bst.getHeight());
	        System.out.println("🌐 Toplam şehir düğümü: " + bst.getNumberOfNodes());
	        System.out.println("🏙 En yoğun şehir: " + bst.getCityWithHighestParcelLoad() + 
	                           " (" + bst.getHighestParcelLoadCount() + " kargo)");
	    }
	}
