package com.eminpolat.sensorfileproject.configuration;

import com.eminpolat.sensorfileproject.business.FetchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchScheduler {
    private FetchService fetchService;

    public FetchScheduler(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @Scheduled(fixedRate = 30000) // dakikada 2 defa
    public void fetchDataPeriodically() {
        fetchService.fetchAndSave();
    }
}