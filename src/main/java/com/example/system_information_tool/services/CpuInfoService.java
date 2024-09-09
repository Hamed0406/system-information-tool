package com.example.system_information_tool.services;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class CpuInfoService {

    public Map<String, Object> getCpuInfo() {
        Map<String, Object> cpuInfo = new HashMap<>();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // System CPU load
        double systemCpuLoad = osBean.getCpuLoad() * 100;
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        cpuInfo.put("System CPU Load (%)", systemCpuLoad);
        cpuInfo.put("Process CPU Load (%)", processCpuLoad);

        return cpuInfo;
    }
}
