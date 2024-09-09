package com.example.system_information_tool.services;

import org.springframework.stereotype.Service;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiskInfoService {

    public Map<String, Object> getDiskInfo() {
        Map<String, Object> diskInfo = new HashMap<>();
        File diskPartition = new File("/");  // Root partition

        // Disk space info
        long totalDiskSpace = diskPartition.getTotalSpace() / (1024 * 1024 * 1024);  // GB
        long freeDiskSpace = diskPartition.getFreeSpace() / (1024 * 1024 * 1024);  // GB
        long usableDiskSpace = diskPartition.getUsableSpace() / (1024 * 1024 * 1024);  // GB

        diskInfo.put("Total Disk Space (GB)", totalDiskSpace);
        diskInfo.put("Free Disk Space (GB)", freeDiskSpace);
        diskInfo.put("Usable Disk Space (GB)", usableDiskSpace);

        return diskInfo;
    }
}
