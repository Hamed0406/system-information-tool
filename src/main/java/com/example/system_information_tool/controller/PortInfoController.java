package com.example.system_information_tool.controller;



import com.example.system_information_tool.services.PortInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PortInfoController {

    private final PortInfoService portInfoService;

    public PortInfoController(PortInfoService portInfoService) {
        this.portInfoService = portInfoService;
    }

  /* */  // Endpoint to get all port information
    @GetMapping("/portinfo")
    public List<Map<String, String>> getAllPortInfo() {
        return portInfoService.getAllPortInfo();
    
    }
    // Endpoint to get only LISTEN ports
    @GetMapping("/portinfo/listen")
    public List<Map<String, String>> getListenPortInfo() {
        return portInfoService.getPortInfoByState("LISTEN");
    }

    // Endpoint to get only ESTABLISHED connections
    @GetMapping("/portinfo/established")
    public List<Map<String, String>> getEstablishedPortInfo() {
        return portInfoService.getPortInfoByState("ESTABLISHED");
    }

    // Add more endpoints for other states (CLOSE_WAIT, TIME_WAIT, etc.)
    @GetMapping("/portinfo/close_wait")
    public List<Map<String, String>> getCloseWaitPortInfo() {
        return portInfoService.getPortInfoByState("CLOSE_WAIT");
    }

    @GetMapping("/portinfo/time_wait")
    public List<Map<String, String>> getTimeWaitPortInfo() {
        return portInfoService.getPortInfoByState("TIME_WAIT");
    }

    // Add more states as needed
}