package com.example.system_information_tool.services;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortInfoService {

    // Method to get all port information without filtering by state
    public List<Map<String, String>> getAllPortInfo() {
        // This method just calls getPortInfoByState with a null filter (no filtering)
        return getPortInfoByState(null);
    }

    // Method to get port information filtered by state
    public List<Map<String, String>> getPortInfoByState(String stateFilter) {
        List<Map<String, String>> portInfoList = new ArrayList<>();
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder builder;
            if (os.contains("win")) {
                builder = new ProcessBuilder("netstat", "-an");
            } else {
                builder = new ProcessBuilder("netstat", "-tuln");
            }

            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            boolean skipFirstLines = true;
            while ((line = reader.readLine()) != null) {
                System.out.println("Netstat Output Line: " + line);  // Debugging the output

                if (skipFirstLines && line.startsWith("Proto")) {
                    skipFirstLines = false;  // Skip the header
                    continue;
                }

                if (!line.trim().isEmpty()) {
                    Map<String, String> connectionInfo = parseNetstatOutput(line, os);
                    if (!connectionInfo.isEmpty()) {
                        // If no state filter, add all ports, otherwise add only the ones that match the state
                        if (stateFilter == null || connectionInfo.get("State").equalsIgnoreCase(stateFilter) || 
                            (stateFilter.equalsIgnoreCase("LISTEN") && connectionInfo.get("State").equalsIgnoreCase("LISTENING"))) {
                            portInfoList.add(connectionInfo);
                        }
                    }
                }
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portInfoList;
    }

    private Map<String, String> parseNetstatOutput(String line, String os) {
        Map<String, String> portInfo = new HashMap<>();
        String[] tokens = line.trim().split("\\s+");

        if (tokens.length >= 4) {
            String protocol = tokens[0];
            String localAddress = tokens[1];
            String foreignAddress = tokens[2];
            String state = tokens.length >= 5 ? tokens[3] : "N/A";

            // Adjust for Linux/macOS output formatting
            if (os.contains("nix") || os.contains("mac")) {
                if (tokens.length >= 6) {
                    state = tokens[5];  // In Linux, state is the 6th field
                }
            }

            portInfo.put("Protocol", protocol);
            portInfo.put("Local Address", localAddress);
            portInfo.put("Foreign Address", foreignAddress);
            portInfo.put("State", state);
        }
        return portInfo;
    }
}