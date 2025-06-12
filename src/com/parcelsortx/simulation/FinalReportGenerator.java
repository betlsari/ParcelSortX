package com.parcelsortx.simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.parcelsortx.model.Parcel;

public class FinalReportGenerator {
    private int totalTicks;
    private int totalParcelsGenerated;
    private int totalDispatchedParcels;
    private int totalReturnedParcels;
    private int remainingInQueue;
    private int remainingInReturnStack;
    private int remainingInBST;
    private Map<String, Integer> parcelsPerCity;
    private String busiestDestination;
    private double averageProcessingTime;
    private String longestDelayedParcelID;
    private int longestDelayTicks;
    private int parcelsReturnedMoreThanOnce;
    private int maxQueueSize;
    private int maxStackSize;
    private int finalBSTHeight;
    private double hashTableLoadFactor;
    private String[] parcelTrackerRecords;

    // Constructor: Simülasyondan gelen verileri parametre olarak al
    public FinalReportGenerator(int totalTicks, int totalParcelsGenerated,
                                int totalDispatchedParcels, int totalReturnedParcels,
                                int remainingInQueue, int remainingInReturnStack, int remainingInBST,
                                Map<String, Integer> parcelsPerCity, String busiestDestination,
                                double averageProcessingTime, String longestDelayedParcelID,
                                int longestDelayTicks, int parcelsReturnedMoreThanOnce,
                                int maxQueueSize, int maxStackSize, int finalBSTHeight,
                                double hashTableLoadFactor, String[] parcelTrackerRecords) {
        this.totalTicks = totalTicks;
        this.totalParcelsGenerated = totalParcelsGenerated;
        this.totalDispatchedParcels = totalDispatchedParcels;
        this.totalReturnedParcels = totalReturnedParcels;
        this.remainingInQueue = remainingInQueue;
        this.remainingInReturnStack = remainingInReturnStack;
        this.remainingInBST = remainingInBST;
        this.parcelsPerCity = parcelsPerCity;
        this.busiestDestination = busiestDestination;
        this.averageProcessingTime = averageProcessingTime;
        this.longestDelayedParcelID = longestDelayedParcelID;
        this.longestDelayTicks = longestDelayTicks;
        this.parcelsReturnedMoreThanOnce = parcelsReturnedMoreThanOnce;
        this.maxQueueSize = maxQueueSize;
        this.maxStackSize = maxStackSize;
        this.finalBSTHeight = finalBSTHeight;
        this.hashTableLoadFactor = hashTableLoadFactor;
        this.parcelTrackerRecords = parcelTrackerRecords;
    }

    public FinalReportGenerator(int totalTicks2, int totalParcelsGenerated2, int dispatchedCount, int returnedCount,
			int remainingInQueue2, int remainingInQueue3, Map<String, Integer> parcelsPerCity2, String busiestCity,
			long l, String longestDelayedParcelID2, int longestDelayTicks2, int returnedMoreThanOnce, int maxQueueSize2,
			int maxStackSize2, int bstHeight, double hashTableLoadFactor2, List<Parcel> parcelTrackerRecords2) {
		// TODO Auto-generated constructor stub
	}

	public void generateReport(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"));

            writer.write("Final Simulation Report\n");
            writer.write("Author: Betül\n");
            writer.write("Date: " + dateStr + "\n\n");

            writer.write("1. Simulation Overview\n");
            writer.write(String.format("- Total Ticks Executed: %d\n", totalTicks));
            writer.write(String.format("- Number of Parcels Generated: %d\n\n", totalParcelsGenerated));

            writer.write("2. Parcel Statistics\n");
            writer.write(String.format("- Total Dispatched Parcels: %d\n", totalDispatchedParcels));
            writer.write(String.format("- Total Returned Parcels: %d\n", totalReturnedParcels));
            writer.write(String.format("- Parcels Remaining in Queue: %d\n", remainingInQueue));
            writer.write(String.format("- Parcels Remaining in ReturnStack: %d\n", remainingInReturnStack));
            writer.write(String.format("- Parcels Remaining in BST: %d\n\n", remainingInBST));

            writer.write("3. Destination Metrics\n");
            writer.write("- Parcels per City:\n");
            for (Map.Entry<String, Integer> entry : parcelsPerCity.entrySet()) {
                writer.write(String.format("   - %-10s : %d\n", entry.getKey(), entry.getValue()));
            }
            writer.write(String.format("- Most Frequently Targeted Destination: %s\n\n", busiestDestination));

            writer.write("4. Timing and Delay Metrics\n");
            writer.write(String.format("- Average Parcel Processing Time: %.2f ticks\n", averageProcessingTime));
            writer.write(String.format("- Parcel with Longest Delay: ID %s, Delay: %d ticks\n", longestDelayedParcelID, longestDelayTicks));
            writer.write(String.format("- Number of Parcels Returned More Than Once: %d\n\n", parcelsReturnedMoreThanOnce));

            writer.write("5. Data Structure Statistics\n");
            writer.write(String.format("- Maximum Queue Size Observed: %d\n", maxQueueSize));
            writer.write(String.format("- Maximum Stack Size Observed: %d\n", maxStackSize));
            writer.write(String.format("- Final Height of BST: %d\n", finalBSTHeight));
            writer.write(String.format("- Hash Table Load Factor: %.2f\n\n", hashTableLoadFactor));

            writer.write("Appendix: Parcel Tracker Records\n");
            for (String record : parcelTrackerRecords) {
                writer.write("- " + record + "\n");
            }

            System.out.println("Report generated successfully at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing the report file.");
        }
    }
}
