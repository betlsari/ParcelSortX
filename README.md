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
- **DestinationSorter**: It uses Binary Search Tree (BST) to sort cargo by destination.**The DestinationSorterNode.java** class represents each node of the tree and contains the corresponding cargo list.    
- **PriorityHandler**: Manages priority cargo with a stack data structure. **ReturnStackNode.java** represents each cargo unit in the stack.  
- **HashTableTracker**: It uses a hash table to quickly locate and track cargo by its ID. It is implemented by the **ParcelTracker.java** class  
- **CircularDeliveryList**: Simulates delivery routes using a circular linked list structure. It is implemented by the **TerminalRotator.java** class.    
- **SimulationController**: Starts and controls the entire simulation and ensures coordination between components. It is implemented by the **SimulationEngine.java** class.  

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
├── src/  
│   └── com/  
│       └── parcelsortx/  
│           ├── config/            // Configuration files and utilities for the simulation  
│           │   ├── Config.java  
│           │   └── config.txt  
│           ├── core/              // Core logic for parcel handling, sorting, tracking, and routing  
│           │   ├── ArrivalBuffer.java    // Manages the initial intake and buffering of parcels.  
│           │   ├── DestinationSorter.java// Implements the Binary Search Tree (BST) logic for sorting parcels by destination.  
│           │   ├── DestinationSorterNode.java // Represents a node within the DestinationSorter's BST.  
│           │   ├── ParcelTracker.java    // Provides functionality to track parcels, likely using a hash table.  
│           │   ├── ReturnStack.java      // Implements a stack structure, possibly for handling priority or return parcels.  
│           │   ├── ReturnStackNode.java  // Represents an element within the ReturnStack.    
│           │   └── TerminalRotator.java  // Manages the final distribution or routing of parcels, possibly simulating a circular flow.  
│           ├── model/             // Data models for parcels and related entities  
│           │   └── Parcel.java          // Represents a cargo item with properties like ID, destination, and priority.  
│           ├── simulation/        // Simulation engine and related components  
│           │   └── SimulationEngine.java// The main controller that orchestrates the simulation flow and component interactions.  
│           └── test/              // Unit and integration tests for core components  
│               ├── ArrivalBufferTest.java// Tests for ArrivalBuffer functionality.  
│               ├── BSTTest.java          // Tests for DestinationSorter's BST implementation.  
│               └── StackTest.java        // Tests for ReturnStack's stack operations.  
│           ├── Main.java          // Main entry point for the application (if any - often used for simpler setups).  
│           └── ParcelSortXApp.java// The primary application class that sets up and runs the ParcelSortX system.  
├── module-info.java               // Java Platform Module System (JPMS) descriptor for module definitions.  
├── JRE System Library [jre]       // Java Runtime Environment dependencies.  
├── log.txt                        // Application-generated logs for runtime information.  
├── README.md                      // This README file providing project overview and instructions.  
├── report.txt                     // Generated reports from simulations or operations.  
└── tick_log.txt                   // Detailed logs, potentially for tick-by-tick simulation events.  

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

## 🎊 Örnek Çıktı (Output)  

### Main.java içeriği:  



## 📝License  

This project is for educational purposes only.  


## 👩‍💻 Authors  

- 😍 Ece Akın  
- 😎 Betül Sarı  
- 🌻 Zehra Sıla Özdizlekli  







