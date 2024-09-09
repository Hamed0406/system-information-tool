package com.example.system_information_tool.services;

import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@Service
public class OsInfoService {

    public Map<String, Object> getOsInfo() {
        Map<String, Object> osInfo = new HashMap<>();
        osInfo.put("OS Name", System.getProperty("os.name"));
        osInfo.put("OS Version", System.getProperty("os.version"));
        osInfo.put("OS Architecture", System.getProperty("os.arch"));

        // JVM uptime
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        osInfo.put("JVM Uptime (seconds)", uptime);

        return osInfo;
    }
}
