package com.parcelsortx.simulation;

import com.parcelsortx.config.Config;
import com.parcelsortx.core.*;
import com.parcelsortx.model.Parcel;
import com.parcelsortx.model.Parcel.Status;

import java.io.*;
import java.util.*;

public class SimulationEngine {
    private int currentTick;
    private int maxTicks;

    private ArrivalBuffer<Parcel> arrivalBuffer;
    private ReturnStack returnStack;
    private DestinationSorter destinationSorter;
    private ParcelTracker parcelTracker;
    private TerminalRotator terminalRotator;
    private Config config;

    private List<String> cities; // Yeni eklenen alan
    private int parcelPerTickMin; // Yeni alan
    private int parcelPerTickMax; // Yeni alan
    
    private Random random;
    private List<Parcel> allParcels;
    private BufferedWriter logWriter;

    // 1. INITIALIZE
    public void initialize() {
        try {
            // Konfigürasyon dosyasını oku
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
            this.parcelPerTickMin = config.getInt("PARCEL_PER_TICK_MIN"); // Değeri al ve atama yap
            this.parcelPerTickMax = config.getInt("PARCEL_PER_TICK_MAX"); // Değeri al ve atama yap

            // Log dosyasını aç
            logWriter = new BufferedWriter(new FileWriter("log.txt"));

            System.out.println("Simülasyon başlatıldı...");
            logWriter.write("Simülasyon başlatıldı...\n");

        } catch (IOException e) {
            System.err.println("Başlatma hatası: " + e.getMessage());
        }
    }

    // 2. RUN SIMULATION
    public void runSimulation() {
        for (currentTick = 1; currentTick <= maxTicks; currentTick++) {
            processTick();
        }

        generateFinalReport();
        closeLogger();
    }

    // 3. PROCESS TICK
    private void processTick() {
        logHeader();

        generateParcels();
        processQueue();
        dispatchParcels();

        if (currentTick % 3 == 0) {
            reprocessReturnedParcels();
        }

        if (currentTick % config.getInt("TERMINAL_ROTATION_INTERVAL") == 0) {
            rotateTerminal();
        }

        logTickSummary();
    }

    // Yardımcı log başlığı
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
                // Dispatched
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

    //betül 
    private void reprocessReturnedParcels() {}
    private void rotateTerminal() {}
    private void logTickSummary() {}
    private void generateFinalReport() {}
}
