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

            random = new Random();
            allParcels = new ArrayList<>();

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

    // Placeholder metotlar (boş, sonra doldurulacak)
    private void generateParcels() {}
    private void processQueue() {}
    private void dispatchParcels() {}
    private void reprocessReturnedParcels() {}
    private void rotateTerminal() {}
    private void logTickSummary() {}
    private void generateFinalReport() {}
}
