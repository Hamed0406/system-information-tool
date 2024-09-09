package com.example.system_information_tool.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemoryInfoService {

    public Map<String, Object> getMemoryInfo() {
        Map<String, Object> memoryInfo = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();

        // Memory info
        long freeMemory = runtime.freeMemory() / (1024 * 1024);  // in MB
        long totalMemory = runtime.totalMemory() / (1024 * 1024);  // in MB
        long maxMemory = runtime.maxMemory() / (1024 * 1024);  // in MB

        memoryInfo.put("Free Memory (MB)", freeMemory);
        memoryInfo.put("Total Memory (MB)", totalMemory);
        memoryInfo.put("Max Memory (MB)", maxMemory);

        return memoryInfo;
    }
}
