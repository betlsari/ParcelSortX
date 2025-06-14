package com.parcelsortx.simulation;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.*;


import com.parcelsortx.config.Config;
import com.parcelsortx.core.*;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;



public class SimulationEngine {
 	private int currentTick;
    private int maxTicks;

    private ArrivalBuffer<Parcel> arrivalBuffer;
    private ReturnStack returnStack;
    private DestinationSorter destinationSorter;
    private ParcelTracker parcelTracker;
    private TerminalRotator terminalRotator;
    private Config config;

    private List<String> cities; 
    private int parcelPerTickMin;  
    private int parcelPerTickMax;  
    
    private Random random;
    private List<Parcel> allParcels;
    private BufferedWriter logWriter;

     public void initialize() {
        try {
             config = new Config("src/com/parcelsortx/config/config.txt");

            maxTicks = config.getInt("MAX_TICKS");
            int queueCapacity = config.getInt("QUEUE_CAPACITY");

            arrivalBuffer = new ArrivalBuffer<>(queueCapacity);
            returnStack = new ReturnStack();
            destinationSorter = new DestinationSorter();
            parcelTracker = new ParcelTracker();

            terminalRotator = new TerminalRotator();
            terminalRotator.initializeFromCityList(config.getCityList().toArray(new String[0]));

            this.cities = config.getCityList();
            random = new Random();
            allParcels = new ArrayList<>();
            this.parcelPerTickMin = config.getInt("PARCEL_PER_TICK_MIN");  
            this.parcelPerTickMax = config.getInt("PARCEL_PER_TICK_MAX");  

             logWriter = new BufferedWriter(new FileWriter("log.txt"));

            System.out.println("Simülasyon başlatıldı...");
            logWriter.write("Simülasyon başlatıldı...\n");

        } catch (IOException e) {
            System.err.println("Başlatma hatası: " + e.getMessage());
        }
    }

     public void runSimulation() {
        for (currentTick = 1; currentTick <= maxTicks; currentTick++) {
            processTick();
        }

        generateFinalReport();
        closeLogger();
    }

     private int getRemainingInQueue() {
		// TODO Auto-generated method stub
		return 0;
	}

	 private void processTick() {
        logHeader();

        generateParcels();
        processQueue();
        
        
        dispatchParcels();

        if (currentTick % 3 == 0) {
              reprocessReturnedParcels();
       
        }
        
        if (currentTick % config.getInt("TERMINAL_ROTATION_INTERVAL") == 0) {
            rotateTerminal(currentTick);
        }

        logTickSummary(currentTick, parcelTracker.getDispatchedParcelCount(), parcelTracker.getReturnedParcelCount());

    }

     private void logHeader() {
        try {
            logWriter.write("\n[Tick " + currentTick + "]\n");
            logWriter.flush();
        } catch (IOException e) {
            System.err.println("Log başlığı yazılamadı.");
        }
    }

    public void generateParcels() {
        int numParcelsToGenerate = random.nextInt(this.parcelPerTickMax - this.parcelPerTickMin + 1) + this.parcelPerTickMin;
        try {
            logWriter.write("tick " + currentTick + ": generating " + numParcelsToGenerate + " parcels.");
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("error writing to log in generateParcels: " + e.getMessage());
        }
        System.out.println("tick " + currentTick + ": generating " + numParcelsToGenerate + " parcels.");
        
        for (int i = 0; i < numParcelsToGenerate; i++) {
            String parcelID = "P" + currentTick + "-" + i + random.nextInt(1000);
            String destinationCity = cities.get(random.nextInt(cities.size()));
            int priority = random.nextInt(3) + 1; // 1 to 3
            String size = switch (random.nextInt(3)) { // 0, 1, 2
                case 0 -> "Small";
                case 1 -> "Medium";
                case 2 -> "Large";
                default -> "Unknown";
            };

            Parcel newParcel = new Parcel(parcelID, destinationCity, priority, size, currentTick, Status.InQueue);

            if (arrivalBuffer.isFull()) {
                try {
                    logWriter.write("warning: arrival buffer is full, discarding parcel " + newParcel.getParcelID() + " for " + newParcel.getDestinationCity() + ".");
                    logWriter.newLine();
                } catch (IOException e) {
                    System.err.println("error writing to log: " + e.getMessage());
                }
                System.out.println("warning: arrival buffer is full, discarding parcel " + newParcel.getParcelID() + " for " + newParcel.getDestinationCity() + ".");
            } else {
                arrivalBuffer.enqueue(newParcel);
                parcelTracker.insert(newParcel.getParcelID(), newParcel);
                parcelTracker.updateStatus(newParcel.getParcelID(), Status.InQueue);
                allParcels.add(newParcel);
                try {
                    logWriter.write("  generated parcel: " + newParcel.getParcelID() + " -> " + newParcel.getDestinationCity() + " (priority: " + newParcel.getPriority() + ", status: in queue)");
                    logWriter.newLine();
                } catch (IOException e) {
                    System.err.println("error writing to log (generated parcel): " + e.getMessage());
                }
                System.out.println("  generated parcel: " + newParcel.getParcelID() + " -> " + newParcel.getDestinationCity() + " (priority: " + newParcel.getPriority() + ", status: in queue)");
            }
        }
    }

    public void processQueue() {
        try {
            logWriter.write("tick " + currentTick + ": processing parcels from arrival buffer.");
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("error writing to log in processQueue: " + e.getMessage());
        }
        System.out.println("tick " + currentTick + ": processing parcels from arrival buffer.");
        while (!arrivalBuffer.isEmpty()) {
            Parcel parcel = arrivalBuffer.dequeue();
            if (parcel != null) {
                destinationSorter.insertParcel(parcel);
                parcelTracker.updateStatus(parcel.getParcelID(), Status.Sorted);
                try {
                    logWriter.write("  processed parcel: " + parcel.getParcelID() + " to " + parcel.getDestinationCity() + " (status: sorted)");
                    logWriter.newLine();
                } catch (IOException e) {
                    System.err.println("error writing to log (processed parcel): " + e.getMessage());
                }
                System.out.println("  processed parcel: " + parcel.getParcelID() + " to " + parcel.getDestinationCity() + " (status: sorted)");
            }
        }
    }

    public void dispatchParcels() {
    	
        String activeTerminalCity = terminalRotator.getActiveTerminal();
        if (activeTerminalCity != null) {
            activeTerminalCity = activeTerminalCity.trim().toLowerCase(); // Boşlukları temizle
        }
        try {
            logWriter.write("tick " + currentTick + ": attempting to dispatch parcels for active terminal: " + activeTerminalCity);
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("error writing to log in dispatchParcels: " + e.getMessage());
        }
        System.out.println("tick " + currentTick + ": attempting to dispatch parcels for active terminal: " + activeTerminalCity);

        if (activeTerminalCity == null) {
            try {
                logWriter.write("  no active terminal available for dispatch.");
                logWriter.newLine();
            } catch (IOException e) {
                System.err.println("error writing to log (no active terminal): " + e.getMessage());
            }
            System.out.println("  no active terminal available for dispatch.");
            return;
        }

        ArrivalBuffer<Parcel> parcelsForCity = destinationSorter.getCityParcels(activeTerminalCity);
       
        if (parcelsForCity == null || parcelsForCity.isEmpty()) {
            try {
                logWriter.write("  no parcels found for " + activeTerminalCity + " at this tick.");
                logWriter.newLine();
            } catch (IOException e) {
                System.err.println("error writing to log (no parcels for city): " + e.getMessage());
            }
            System.out.println("  no parcels found for " + activeTerminalCity + " at this tick.");
            return;
        }

        Parcel parcelToDispatch = parcelsForCity.peek();
        if (parcelToDispatch != null) {
            if (random.nextDouble() < config.getDouble("MISROUTING_RATE")) {
                parcelsForCity.dequeue();
                returnStack.push(parcelToDispatch);
                parcelTracker.updateStatus(parcelToDispatch.getParcelID(), Status.Returned);
                parcelTracker.incrementReturnCount(parcelToDispatch.getParcelID());
                try {
                    logWriter.write("  misrouted parcel: " + parcelToDispatch.getParcelID() + " -> " + parcelToDispatch.getDestinationCity() + " (status: returned, return count: " + parcelTracker.getReturnCount(parcelToDispatch.getParcelID()) + ")");
                    logWriter.newLine();
                } catch (IOException e) {
                    System.err.println("error writing to log (misrouted parcel): " + e.getMessage());
                }
                System.out.println("  misrouted parcel: " + parcelToDispatch.getParcelID() + " -> " + parcelToDispatch.getDestinationCity() + " (status: returned, return count: " + parcelTracker.getReturnCount(parcelToDispatch.getParcelID()) + ")");
            } else {
                 parcelsForCity.dequeue();
                parcelTracker.updateStatus(parcelToDispatch.getParcelID(), Status.Dispatched);
                parcelTracker.setDispatchTick(parcelToDispatch.getParcelID(), currentTick);
                try {
                    logWriter.write("  dispatched parcel: " + parcelToDispatch.getParcelID() + " to " + parcelToDispatch.getDestinationCity() + " (status: dispatched, dispatch tick: " + currentTick + ")");
                    logWriter.newLine();
                } catch (IOException e) {
                    System.err.println("error writing to log (dispatched parcel): " + e.getMessage());
                }
                System.out.println("  dispatched parcel: " + parcelToDispatch.getParcelID() + " to " + parcelToDispatch.getDestinationCity() + " (status: dispatched, dispatch tick: " + currentTick + ")");
            }
        }
    }
    

    public void reprocessReturnedParcels() {
    	int maxProcess = 3;
        int processed = 0;
        
        while (!returnStack.isEmpty() && processed < maxProcess) {
            Parcel parcel = returnStack.pop();
            destinationSorter.insertParcel(parcel);
            parcelTracker.updateStatus(parcel.getParcelID(), Status.Sorted);
            processed++;
        }
        System.out.println("[ReturnHandler] " + processed + " parcels reprocessed.");
    }
    public void rotateTerminal(int currentTick) {
    	int rotationInterval = config.getInt("TERMINAL_ROTATION_INTERVAL");

        if (rotationInterval > 0 && currentTick % rotationInterval == 0) {
            rotateAndLog();
        }
    }	
    private void rotateAndLog() {
        terminalRotator.advanceTerminal();
        String currentTerminal = terminalRotator.getActiveTerminal();
        System.out.println("[rotateTerminal] Terminal rotated. New terminal: " + currentTerminal);
    }
     
     
    
    public void logTickSummary(int currentTick, int dispatchedCount, int returnedCount) {
       
        int queueSize = arrivalBuffer.size();
        int stackSize = returnStack.size();
        String activeTerminal = terminalRotator.getActiveTerminal(); 

        
        if (activeTerminal == null) {
            activeTerminal = "N/A";
        }
        String summary = String.format(
            "[Tick %d] Queue: %d | ReturnStack: %d | Dispatched: %d | Returned: %d | Active Terminal: %s",
            currentTick, queueSize, stackSize, dispatchedCount, returnedCount, activeTerminal);

        
        System.out.println(summary);
        File logFile = new File("tick_log.txt");

        try {
           
            List<String> lines = new ArrayList<>();
            if (logFile.exists()) {
                lines = Files.readAllLines(logFile.toPath());
            }
           
            if (lines.size() >= 100) {
                lines = lines.subList(lines.size() - 99, lines.size());
            }

            lines.add(summary);
           
            Files.write(logFile.toPath(), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing to tick_log.txt:");
            e.printStackTrace();
        }
    }


    public void generateFinalReport() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            writer.println("Final Report");

            // Toplam işlenen kargolar
            if (parcelTracker != null) {
                writer.println("Total parcels processed: " + parcelTracker.getTotalParcels());
            } else {
                writer.println("Total parcels processed: N/A (parcelTracker is null)");
            }

            
            if (destinationSorter != null) {
                String busiestCity = destinationSorter.getCityWithHighestParcelLoad();
                if (busiestCity != null) {
                    writer.println("Busiest destination: " + busiestCity);
                } else {
                    writer.println("Busiest destination: no data");
                }
            } else {
                writer.println("Busiest destination: destinationSorter is null");
            }

            
            if (parcelTracker != null) {
                Parcel delayed = parcelTracker.getMostDelayedParcel();
                if (delayed != null) {
                    writer.println("Longest delayed parcel: " + delayed.getTrackingNumber()
                            + " Delay: " + delayed.getDelay() + " ticks");
                } else {
                    writer.println("Longest delayed parcel:no delayed parcels");
                }
            } else {
                writer.println("Longest delayed parcel:parcelTracker is null");
            }

        
            if (arrivalBuffer != null) {
                writer.println("Remaining in Queue: " + arrivalBuffer.size());
            } else {
                writer.println("Remaining in Queue:arrivalBuffer is null");
            }

            // ReturnStack'te kalan paketler
            if (returnStack != null) {
                writer.println("Remaining in ReturnStack: " + returnStack.size());
            } else {
                writer.println("Remaining in ReturnStack:returnStack is null");
            }

            // BST'de kalan paketler
            if (destinationSorter != null) {
                writer.println("Remaining in BST: " + destinationSorter.countAllParcels());
            } else {
                writer.println("Remaining in BST: N/A destinationSorter is null");
            }

            // Parcel Tracker kayıtları
            writer.println("\nParcel Tracker's Records");
            if (parcelTracker != null && parcelTracker.getAllParcelRecords() != null) {
                for (String record : parcelTracker.getAllParcelRecords()) {
                    if (record != null) {
                        writer.println(record);
                    }
                }
            } else {
                writer.println("No parcel records available.");
            }

            writer.println("End");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Final report generated. Log file closed.");
    }
    // Yardımcı dosya kapama
    private void closeLogger() {
        try {
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException e) {
            System.err.println("Log dosyası kapatılamadı.");
        }
    }

    
}


