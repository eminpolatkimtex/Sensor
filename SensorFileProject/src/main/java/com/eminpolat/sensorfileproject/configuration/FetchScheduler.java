package com.eminpolat.sensorfileproject.configuration;

import com.eminpolat.sensorfileproject.business.FetchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchScheduler {
    private final FetchService fetchService;

    public FetchScheduler(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @Scheduled(fixedRate = 30000) // her 30 saniyede bir
    public void fetchDataPeriodically() {
        fetchService.fetchAll(); // bütün URL’lerden veri çek
    }
}