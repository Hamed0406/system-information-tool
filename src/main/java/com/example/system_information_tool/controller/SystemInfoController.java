package com.example.system_information_tool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemInfoController {

    @GetMapping("/systeminfo")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();

        // Get Operating System Information
        systemInfo.put("Operating System", System.getProperty("os.name"));
        systemInfo.put("OS Version", System.getProperty("os.version"));
        systemInfo.put("OS Architecture", System.getProperty("os.arch"));

        // Get JVM Information
        systemInfo.put("Java Version", System.getProperty("java.version"));
        systemInfo.put("Java Vendor", System.getProperty("java.vendor"));

        // Get Runtime Information
        systemInfo.put("Available Processors", Runtime.getRuntime().availableProcessors());
        systemInfo.put("Free Memory (MB)", Runtime.getRuntime().freeMemory() / (1024 * 1024));
        systemInfo.put("Max Memory (MB)", Runtime.getRuntime().maxMemory() / (1024 * 1024));
        systemInfo.put("Total Memory (MB)", Runtime.getRuntime().totalMemory() / (1024 * 1024));

        // Get JVM Uptime
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        systemInfo.put("JVM Uptime (seconds)", uptime / 1000);

        return systemInfo;
    }

}