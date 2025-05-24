package com.parcelsortx.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Config {
    private Map<String, String> configMap;

    public Config(String filename) {
        configMap = new HashMap<>();
        loadConfigFile(filename);
    }

    private void loadConfigFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Yorum satırlarını ve boş satırları atla
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    configMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Config dosyası okunamadı: " + e.getMessage());
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(configMap.get(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(configMap.get(key));
    }

    public String getString(String key) {
        return configMap.get(key);
    }

    public List<String> getCityList() {
        String cities = configMap.get("CITY_LIST");
        if (cities == null || cities.isEmpty()) return new ArrayList<>();
        return Arrays.asList(cities.split(","));
    }

    public boolean containsKey(String key) {
        return configMap.containsKey(key);
    }

    public void printAll() {
        System.out.println("Config ayarları:");
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
