package com.eminpolat.sensorfileproject.controller;

import com.eminpolat.sensorfileproject.business.FetchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    private final FetchService fetchService;

    public SensorController(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    // Cihaz ekle → /api/sensor/setDevice?id=1&deviceName=eva2025
    @GetMapping("/setDevice")
    public String setDevice(@RequestParam int id, @RequestParam String deviceName) {
        fetchService.setDevice(id, deviceName);
        return "Cihaz " + id + " için ayarlandı: " + deviceName;
    }

    // Veri oku → /api/sensor/getAll/1
    @GetMapping("/getAll/{id}")
    public String getAll(@PathVariable int id) {
        return fetchService.getAllData(id);
    }
}