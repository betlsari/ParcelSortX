package com.parcelsortx.core;

import java.util.LinkedList;

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
	
	/*
	 * insertParcelRecursive
	 * null ise yenii bstnODE oluştur
	 * numOfNodes++ 
	 * compareToIgnore : büyük küçük harf duyarsız olarak karşılaştır
	 * paketin şehri currenttan alfabetik olarak önce geliyosa sol ağaca
	 * sonra geliyosa sağ ağaca çağrı yaparr
	 * current ile aynıysa da current düğümünün parcellistine ekler
	 * eğer bu düğümdeki paket sayısı mevcut en yüksekten fazla 
	 * ise citywithhighest... ve highest... değerlerini günceller
	 * her rekürsif çağrıdan sonra current düğümünü döndürür
	 *  */ 
	
	
	private BinarySearchTreeNode insertParcelRecursive(BinarySearchTreeNode current, Parcel parcel) {
        if (current == null) {
        	numOfNodes++; // Yeni bir şehir düğümü oluşturuldu
            return new BinarySearchTreeNode(parcel.getDestinationCity(), parcel);
        }
        // iki stringi alfabetik olarak karşılaştırır
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
	/* 
	 * getcityparcels
	 * findNode ile ilgili şehir düğümünü bulur 
	 * düğüm bulunursa parcellist döndürür
	 * aksi takdirde null döner
	 * 
	 * findnode
	 * eğer current null veya aranan şehir = current , currentı döndürür
	 * aranan şehir currentten önce ise sol alt ağaca
	 * sonra geliyosa sağ alt ağaca bakar
	 * 
	 *  */ 
	
	
	 public java.util.Queue<Parcel> getCityParcels(String city) {
		 BinarySearchTreeNode node = findNode(root, city);
	        return (node != null) ? node.parcelList : null;
	    }
	 
	 private BinarySearchTreeNode findNode(BinarySearchTreeNode current, String city) {
	        if (current == null) {
	            return null;
	        }
	        int compareResult = city.compareToIgnoreCase(current.cityName);

	        if (compareResult == 0) {
	            return current;
	        }
	        return (compareResult < 0) ? findNode(current.left, city) : findNode(current.right, city);
	    }
	 
	 	/*
	 	 * inordertraversal
	 	 *  tüm şehirleri alfabetik sıraya göre gezer
	 	 *  her şehrin paket sayısını konsola yazdırır
	 	 *  
	 	 *  recursive
	 	 *  node null ise geri döner
	 	 *  ilk sol alt ağaç sonra sağ alt ağaç gezilir
	 	 *  
	 	 *  */ 
	    public void inOrderTraversal() {
	        System.out.println("Cities in alphabetical order:");
	        inOrderRecursive(root);
	    }

	    private void inOrderRecursive(BinarySearchTreeNode node) {
	        if (node != null) {
	            inOrderRecursive(node.left);
	            System.out.println("- " + node.cityName + ": " + node.parcelList.size() + " parcel(s)");
	            inOrderRecursive(node.right);
	        }
	    }
	    
	    /* 
	     * removeparcel
	     * 
	     * önce findNode ile hedef şehir düğümü bulunur
	     * düğüm bulunursa düğümün parcellisti üzerinde removeıf metodu ile parcelıd eşleşen
	     * paketi kaldırır işlem başarılı ise true değilse false
	     * düğüm değil paket kaldırılır
	     * 
	     * */ 
	    
	    
	    
	    public boolean removeParcel(String city, String parcelID) {
	    	BinarySearchTreeNode node = findNode(root, city);
	        if (node != null) {
	            // LinkedList'ten belirli bir öğeyi kaldırmak biraz daha karmaşık.
	            // Önce paketi bulup sonra listeden çıkarmamız gerekiyor.
	            return node.parcelList.removeIf(p -> p.getParcelID().equals(parcelID));
	        }
	        return false;
	    }
	    
	    
	    public int countCityParcels(String city) {
	    	BinarySearchTreeNode node = findNode(root, city);
	        return (node != null) ? node.getParcelCount() : 0;
	    }
	    
	    public int getHeight() {
	        return calculateHeight(root);
	    }

	    private int calculateHeight(BinarySearchTreeNode node) {
	        if (node == null) {
	            return -1; // Boş ağaç için yükseklik -1
	        }
	        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
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
