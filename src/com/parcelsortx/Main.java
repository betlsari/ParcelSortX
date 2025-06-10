package com.parcelsortx;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.parcelsortx.simulation.SimulationEngine;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {
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

	}

}
