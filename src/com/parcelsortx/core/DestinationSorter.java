package com.parcelsortx.core;

import java.util.LinkedList;

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
		root = insertParcelRecursive(root, parcel);
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

	private DestinationSorterNode insertParcelRecursive(DestinationSorterNode current, Parcel parcel) {
		if (current == null) {
			numOfNodes++; 
			return new DestinationSorterNode(parcel.getDestinationCity(), parcel);
		}
		int compareResult = parcel.getDestinationCity().compareToIgnoreCase(current.cityName);

		if (compareResult < 0) {
			current.left = insertParcelRecursive(current.left, parcel);
		} else if (compareResult > 0) {
			current.right = insertParcelRecursive(current.right, parcel);
		} else {
			current.parcelList.enqueue(parcel);
			if (current.getParcelCount() > highestParcelLoadCount) {
				highestParcelLoadCount = current.getParcelCount();
				cityWithHighestParcel = current.cityName;
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
		DestinationSorterNode node = findNode(root, city);
		if (node==null) {
			System.out.println("şehir bulunamadı" + city);
			return null;
		}
		return node.getParcelList();
	}

	private DestinationSorterNode findNode(DestinationSorterNode current, String city) {
		while (current != null) {
			int compare = city.compareToIgnoreCase(current.cityName);

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
		DestinationSorterNode node = findNode(root, city);
		if (node != null) {
			return node.parcelList.removeByID(parcelID);
		}
		return false;
	}

	public int countCityParcels(String city) {
	    DestinationSorterNode current = root;
	    
	    while (current != null) {
	        int cmp = city.compareToIgnoreCase(current.cityName);
	        
	        if (cmp == 0) {
	            return current.getParcelCount();
	        } else if (cmp < 0) {
	            current = current.left;
	        } else {
	            current = current.right;
	        }
	    }

	    System.out.println("Sehir bulunamadi: " + city);
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

}
