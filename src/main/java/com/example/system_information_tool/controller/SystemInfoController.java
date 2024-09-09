package com.example.system_information_tool.controller;

import com.example.system_information_tool.services.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemInfoController {

    private final CpuInfoService cpuInfoService;
    private final DiskInfoService diskInfoService;
    private final MemoryInfoService memoryInfoService;
    private final NetworkInfoService networkInfoService;
    private final OsInfoService osInfoService;

    public SystemInfoController(CpuInfoService cpuInfoService,
                                DiskInfoService diskInfoService,
                                MemoryInfoService memoryInfoService,
                                NetworkInfoService networkInfoService,
                                OsInfoService osInfoService) {
        this.cpuInfoService = cpuInfoService;
        this.diskInfoService = diskInfoService;
        this.memoryInfoService = memoryInfoService;
        this.networkInfoService = networkInfoService;
        this.osInfoService = osInfoService;
    }

    @GetMapping("/systeminfo")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        systemInfo.putAll(cpuInfoService.getCpuInfo());
        systemInfo.putAll(diskInfoService.getDiskInfo());
        systemInfo.putAll(memoryInfoService.getMemoryInfo());
        systemInfo.putAll(networkInfoService.getNetworkInfo());
        systemInfo.putAll(osInfoService.getOsInfo());

        return systemInfo;
    }
}