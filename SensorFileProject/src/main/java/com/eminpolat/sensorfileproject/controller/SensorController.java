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

    // URL ekle → /api/sensor/setUrl?id=1&url=https://...
    @GetMapping("/setUrl")
    public String setUrl(@RequestParam int id, @RequestParam String url) {
        fetchService.setUrl(id, url);
        return "URL " + id + " için ayarlandı: " + url;
    }

    // Veri oku → /api/sensor/getAll/1
    @GetMapping("/getAll/{id}")
    public String getAll(@PathVariable int id) {
        return fetchService.getAllData(id);
    }
}