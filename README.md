# ParcelSortX
**ParcelSortX** is a "Smart Package Sorting and Routing Simulation" using classical data structures.
This project is a logistics simulation involving packet reception, sorting, routing, and delivery using basic data structures such as queues, stacks, binary search trees, circularly linked, hash tables, and lists. The goal is to test these data structures in a real-world context.
---
## :zap: Project Overview

ParcelSortX simulates the package's progress cycle from the moment it is picked up to its delivery. The system includes:

-Intelligent packet logging and tracking
-Using Binary Search Tree for dynamic route-based sorting
-Stack structure for priority management
-Using Hash Table for fast packet discovery

## 🧠 System Architecture

The system is modular and composed of the following main components:

- **ParcelManager**: Manages the creation and basic operations of cargoes.
- **DestinationSorter**: It uses Binary Search Tree (BST) to sort cargo by destination.
- **PriorityHandler**: Manages priority cargo with a stack data structure.
- **HashTableTracker**: It uses a hash table to quickly locate and track cargo by its ID.
- **CircularDeliveryList**: Simulates delivery routes using a circular linked list structure.
- **SimulationController**: Starts and controls the entire simulation and ensures coordination between components.

## :package: Data Structures Used

Data Structures:
-Queue : Receiving and processing packets in order
-Stack: Management of priority packages
-Binary Search Tree: Sorting and grouping of packages by destination
-Hash Table: Fast and effective finding of package IDs
-Circular Linked List: Management of delivery vehicle routes

## :round_pushpin: How It Works

-**Acceptance of a Packet:** Users enter the package information (ID, sender, receiver, destination, priority) into the system.  
-**Ordering:** Packets are placed in the appropriate nodes in the binary search tree according to their destinations.  
-**Tracking:** Packets are recorded in a hash table for quick access.  
-**Delivery Cycle:** Delivery routes are modeled as a continuous cycle with a circular linked list.  
-**Priority Packets:** Priority packets are placed ahead of the standard queue using a stack structure.  

## 🗂️ Project Structure

ParcelSortX/  
│  
├── src/  
│ ├── Main.java  
│ ├── Parcel.java  
│ ├── ParcelManager.java  
│ ├── DestinationSorter.java  
│ ├── PriorityHandler.java  
│ ├── HashTableTracker.java  
│ ├── CircularDeliveryList.java  
│ └── SimulationController.java  
│  
├── README.md  
└── report/  
└── ParcelSortX_Requirements.pdf  

## 🚀 Installation and Operation  

### ✨ Requirements  

- 📌 Java Development Kit (JDK) 17 or later  
- 📌 A Java IDE (IntelliJ IDEA, Eclipse, etc.) or terminal
  
### 🚀 Operating Steps

1️⃣ Place all `.java` files in the `com.escaping.maze` package structure.  
2️⃣ Compile from the command line:  
```bash  
javac parcelsortx/**/*.java  
```  
3️⃣ Run the program:  

```bash  
java parcelsortx.SimulationController  
```  

---  
## 📝License  

This project is for educational purposes only.  


## 👩‍💻 Authors  

- 😍 Ece Akın  
- 😎 Betül Sarı  
- 🌻 Zehra Sıla Özdizlekli  







