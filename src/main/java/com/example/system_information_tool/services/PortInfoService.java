package com.example.system_information_tool.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortInfoService {

    // Method to get all port information without filtering by state
    public List<Map<String, String>> getAllPortInfo() {
        return getPortInfoByState(null);  // No filter, return all
    }

    // Method to get port information filtered by state
    public List<Map<String, String>> getPortInfoByState(String stateFilter) {
        List<Map<String, String>> portInfoList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("netstat.log"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Debugging the output
                System.out.println("Netstat Output Line: " + line);

                // Skip headers or empty lines
                if (line.trim().isEmpty() || line.startsWith("Proto")) {
                    continue;
                }

                Map<String, String> connectionInfo = parseNetstatOutput(line);
                if (!connectionInfo.isEmpty()) {
                    // If no state filter, add all ports, otherwise add only the ones that match the state
                    if (stateFilter == null || connectionInfo.get("State").equalsIgnoreCase(stateFilter)) {
                        portInfoList.add(connectionInfo);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portInfoList;
    }

    // Parsing the netstat log lines into a map
    private Map<String, String> parseNetstatOutput(String line) {
        Map<String, String> portInfo = new HashMap<>();
        String[] tokens = line.trim().split("\\s+");

        if (tokens.length >= 4) {
            String protocol = tokens[0];  // Protocol (TCP/UDP)
            String localAddress = tokens[1];  // Local address
            String foreignAddress = tokens[2];  // Foreign address

            String state = "N/A";
            // For TCP, the state will be the 4th or 5th token, for UDP no state is available
            if (protocol.startsWith("TCP")) {
                if (tokens.length >= 5) {
                    state = tokens[3];  // State for TCP
                }
            }

            // Add parsed details to map
            portInfo.put("Protocol", protocol);
            portInfo.put("Local Address", localAddress);
            portInfo.put("Foreign Address", foreignAddress);
            portInfo.put("State", state);  // Will be N/A for UDP

            // Debugging line to see parsed data
            System.out.println("Parsed Line: " + portInfo);
        }
        return portInfo;
    }

    // Method to run netstat and save the output to a log file
    public void saveNetstatToLog() {
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

            // Save netstat output to a log file
            try (PrintStream out = new PrintStream(new FileOutputStream("netstat.log"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.println(line);
                }
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
