package com.eminpolat.sensorfileproject.controller;

import com.eminpolat.sensorfileproject.business.FetchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    private FetchService fetchService;

    public SensorController(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @GetMapping("/getAll")
    public String getAll() {
        return fetchService.getAllData();
    }
}
