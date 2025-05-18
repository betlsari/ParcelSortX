package com.parcelsortx.core;

import com.parcelsortx.model.Parcel;

public class BinarySearchTree {
/* ECE */ 
	private BinarySearchTreeNode root;
	private int numOfNodes;
	
	private String cityWithHighestParcel;
	private int highestParcelLoadCount;
	
	public BinarySearchTree() {
        this.root = null;
        this.numOfNodes = 0;
        this.cityWithHighestParcel = null;
        this.highestParcelLoadCount = 0;
    }
	
	public void insertParcel(Parcel parcel) {
        if (parcel == null) {
            System.err.println("Hata: Eklenecek paket null olamaz.");
            return;
        }
        root = insertParcelRecursive(root, parcel);
    }
	private BinarySearchTreeNode insertParcelRecursive(BinarySearchTreeNode current, Parcel parcel) {
        if (current == null) {
        	numOfNodes++; // Yeni bir şehir düğümü oluşturuldu
            return new BinarySearchTreeNode(parcel.getDestinationCity(), parcel);
        }

        int compareResult = parcel.getDestinationCity().compareToIgnoreCase(current.cityName);

        if (compareResult < 0) {
            current.left = insertParcelRecursive(current.left, parcel);
        } else if (compareResult > 0) {
            current.right = insertParcelRecursive(current.right, parcel);
        } else {
            // Şehir düğümü zaten mevcut, paketi listeye ekle
            current.parcelList.add(parcel);
            // Paket eklendikten sonra en yüksek yüke sahip şehri kontrol et
            if (current.getParcelCount() > highestParcelLoadCount) {
                highestParcelLoadCount = current.getParcelCount();
                cityWithHighestParcel = current.cityName;
            }
        }
        return current;
    }
	
	 public Queue<Parcel> getCityParcels(String city) {
	        BSTNode node = findNode(root, city);
	        return (node != null) ? node.parcelList : null;
	    }

}
