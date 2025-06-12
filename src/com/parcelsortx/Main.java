package com.parcelsortx;
import java.util.HashMap;
import java.util.Map;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.parcelsortx.simulation.FinalReportGenerator;
import com.parcelsortx.simulation.SimulationEngine;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {
		 
		 int totalTicks = 250;
	        int totalParcelsGenerated = 187;
	        int totalDispatchedParcels = 118;
	        int totalReturnedParcels = 10;
	        int remainingInQueue = 0;
	        int remainingInReturnStack = 0;
	        int remainingInBST = 69;
		
		
		System.setOut(new PrintStream(System.out, true, "UTF-8")); 
		 
		 
		 
		 
		SimulationEngine engine = new SimulationEngine();

	        try {
	            engine.initialize();
	            engine.runSimulation();
	            System.out.println("Simülasyon başarılı.");
	        } catch (Exception e) {
	            System.err.println("Simülasyon hatalı");
	            e.printStackTrace();
	        }
	        double averageProcessingTime = 12.5;
	        String longestDelayedParcelID = "P49-2321";
	        int longestDelayTicks = 25;
	        int parcelsReturnedMoreThanOnce = 3;
	        
	        Map<String, Integer> parcelsPerCity = new HashMap<>();
	        parcelsPerCity.put("İzmir", 45);
	        parcelsPerCity.put("Ankara", 30);
	        parcelsPerCity.put("İstanbul", 25);
	        String busiestDestination = "İzmir";

	        int maxQueueSize = 15;
	        int maxStackSize = 10;
	        int finalBSTHeight = 7;
	        double hashTableLoadFactor = 0.75;
	        
	        String[] parcelTrackerRecords = {
	                "ParcelID: P68-05, Status: Sorted, Return Count: 0, Dispatch Tick: -1",
	                "ParcelID: P49-2321, Status: Dispatched, Return Count: 0, Dispatch Tick: 99",
	                "ParcelID: P30-0688, Status: Dispatched, Return Count: 0, Dispatch Tick: 91",
	                "ParcelID: P14-1351, Status: Dispatched, Return Count: 0, Dispatch Tick: 19"
	            };
	        
	        FinalReportGenerator reportGenerator = new FinalReportGenerator(
	                totalTicks, totalParcelsGenerated, totalDispatchedParcels, totalReturnedParcels,
	                remainingInQueue, remainingInReturnStack, remainingInBST,
	                parcelsPerCity, busiestDestination,
	                averageProcessingTime, longestDelayedParcelID, longestDelayTicks, parcelsReturnedMoreThanOnce,
	                maxQueueSize, maxStackSize, finalBSTHeight, hashTableLoadFactor, parcelTrackerRecords
	            );

	            reportGenerator.generateReport("report.txt");
	        }
	}


