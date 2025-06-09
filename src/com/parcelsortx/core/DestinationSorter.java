package com.parcelsortx.core;

import java.util.LinkedList;
import java.util.Locale; // Locale sınıfını import et

import com.parcelsortx.model.Parcel;

public class DestinationSorter {
	/* ECE */
	private DestinationSorterNode root;
	private int numOfNodes;

	private String cityWithHighestParcel;
	private int highestParcelLoadCount;

	public DestinationSorter() {
		this.root = null;
		this.numOfNodes = 0;
		this.cityWithHighestParcel = null;
		this.highestParcelLoadCount = 0;
	}

	public void insertParcel(Parcel parcel) {
		if (parcel == null) {
			System.err.println("Eklenecek paket null olamaz");
			return;
		}
		// Paket hedef şehrini Türkçe locale'ine göre normalleştir
		root = insertParcelRecursive(root, parcel.getDestinationCity().trim().toLowerCase(new Locale("tr", "TR")), parcel);
	}

	/*
	 * insertParcelRecursive null ise yenii bstnODE oluştur numOfNodes++
	 * compareToIgnore : büyük küçük harf duyarsız olarak karşılaştır paketin şehri
	 * currenttan alfabetik olarak önce geliyosa sol ağaca sonra geliyosa sağ ağaca
	 * çağrı yaparr current ile aynıysa da current düğümünün parcellistine ekler
	 * eğer bu düğümdeki paket sayısı mevcut en yüksekten fazla ise
	 * citywithhighest... ve highest... değerlerini günceller her rekürsif çağrıdan
	 * sonra current düğümünü döndürür
	 */

	private DestinationSorterNode insertParcelRecursive(DestinationSorterNode current, String normalizedCityName,
			Parcel parcel) {
		if (current == null) {
			numOfNodes++;
			// Yeni düğüm oluştururken de normalize edilmiş şehir adını kullan
			return new DestinationSorterNode(normalizedCityName, parcel);
		}

		// Normalize edilmiş şehir adı ile mevcut düğümün şehir adını karşılaştır
		int compareResult = normalizedCityName.compareTo(current.cityName);

		if (compareResult < 0) {
			current.left = insertParcelRecursive(current.left, normalizedCityName, parcel);
		} else if (compareResult > 0) {
			current.right = insertParcelRecursive(current.right, normalizedCityName, parcel);
		} else {
			// Şehir zaten mevcutsa, paketi o düğümün listesine ekle
			current.parcelList.enqueue(parcel);
			// En yoğun şehri güncelle (eğer bu düğümdeki koli sayısı en yüksekse)
			if (current.getParcelCount() > highestParcelLoadCount) {
				highestParcelLoadCount = current.getParcelCount();
				cityWithHighestParcel = current.cityName; // Bu zaten normalize edilmiş hali
			}
		}
		return current;
	}
	/*
	 * getcityparcels findNode ile ilgili şehir düğümünü bulur düğüm bulunursa
	 * parcellist döndürür aksi takdirde null döner
	 * 
	 * findnode eğer current null veya aranan şehir = current , currentı döndürür
	 * aranan şehir currentten önce ise sol alt ağaca sonra geliyosa sağ alt ağaca
	 * bakar
	 * 
	 */

	public ArrivalBuffer<Parcel> getCityParcels(String city) {
		// Gelen şehir adını Türkçe locale'ine göre normalleştir ve findNode'a gönder
		String normalizedCity = city.trim().toLowerCase(new Locale("tr", "TR"));
		DestinationSorterNode node = findNode(root, normalizedCity);
		if (node == null) {
			// Normalleştirilmiş şehir adı bulunamadığında bu mesajı göster
			System.out.println("Şehir bulunamadı: " + city); // Hatanın daha iyi anlaşılması için orijinal şehri göster
			return null;
		}
		return node.getParcelList();
	}

	private DestinationSorterNode findNode(DestinationSorterNode current, String normalizedCity) {
		while (current != null) {
			// Normalize edilmiş şehir adını mevcut düğümün şehir adıyla karşılaştır
			int compare = normalizedCity.compareTo(current.cityName);

			if (compare == 0) {
				return current;
			} else if (compare < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;
	}

	/*
	 * inordertraversal tüm şehirleri alfabetik sıraya göre gezer her şehrin paket
	 * sayısını konsola yazdırır
	 * 
	 * recursive node null ise geri döner ilk sol alt ağaç sonra sağ alt ağaç
	 * gezilir
	 * 
	 */
	public void inOrderTraversal() {
		System.out.println("alfabetik sirada sehirler:");
		inOrderRecursive(root);
	}

	private void inOrderRecursive(DestinationSorterNode node) {
		if (node != null) {
			inOrderRecursive(node.left);
			System.out.println("- " + node.cityName + ": " + node.parcelList.size() + " parcel(s)");
			inOrderRecursive(node.right);
		}
	}

	/*
	 * removeparcel
	 * 
	 * önce findNode ile hedef şehir düğümü bulunur düğüm bulunursa düğümün
	 * parcellisti üzerinde removeıf metodu ile parcelıd eşleşen paketi kaldırır
	 * işlem başarılı ise true değilse false düğüm değil paket kaldırılır
	 * 
	 */

	public boolean removeParcel(String city, String parcelID) {
		// Gelen şehir adını Türkçe locale'ine göre normalleştir ve findNode'a gönder
		String normalizedCity = city.trim().toLowerCase(new Locale("tr", "TR"));
		DestinationSorterNode node = findNode(root, normalizedCity);
		if (node != null) {
			return node.parcelList.removeByID(parcelID);
		}
		return false;
	}

	public int countCityParcels(String city) {
		DestinationSorterNode current = root;
		// Gelen şehir adını Türkçe locale'ine göre normalleştir
		String normalizedCity = city.trim().toLowerCase(new Locale("tr", "TR"));

		while (current != null) {
			// Normalize edilmiş şehir adını mevcut düğümün şehir adıyla karşılaştır
			int cmp = normalizedCity.compareTo(current.cityName);

			if (cmp == 0) {
				return current.getParcelCount();
			} else if (cmp < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}

		System.out.println("Sehir bulunamadi: " + city); // Hatanın daha iyi anlaşılması için orijinal şehri göster
		return 0;
	}

	public int getHeight() {
		return calculateHeight(root);
	}

	private int calculateHeight(DestinationSorterNode node) {
		if (node == null) {
			return -1; // boş ağaç veya yaprak sonrası
		}

		int leftHeight = calculateHeight(node.left);
		int rightHeight = calculateHeight(node.right);

		int currentHeight = 1 + Math.max(leftHeight, rightHeight);
		return currentHeight;
	}

	public int getNumberOfNodes() {
		return numOfNodes;
	}

	public String getCityWithHighestParcelLoad() {
		return cityWithHighestParcel;
	}

	public int getHighestParcelLoadCount() {
		return highestParcelLoadCount;
	}

	

	public int countAllParcels() {
		return countAllParcelsRecursive(root);
	}

	private int countAllParcelsRecursive(DestinationSorterNode node) {
		if (node == null) {
			return 0;
		}
		// Mevcut düğümdeki koli sayısı + sol ağaçtaki koli sayısı + sağ ağaçtaki koli sayısı
		return node.getParcelCount() + countAllParcelsRecursive(node.left) + countAllParcelsRecursive(node.right);
	}

}