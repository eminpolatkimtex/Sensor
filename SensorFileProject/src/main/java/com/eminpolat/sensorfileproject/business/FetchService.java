package com.eminpolat.sensorfileproject.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FetchService {
    private final RestTemplate restTemplate;

    @Value("${app.file.name}")
    private String fileName;

    @Value("${app.fetch.url}")
    private String url;

    public FetchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fetchAndSave()
    {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            String jsonData = restTemplate.getForObject(url, String.class);
            writer.write(jsonData + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Dosyaya yazılırken hata oluştu", e);
        }
    }

    public String getAllData() {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            return "Dosya okunamadı: " + e.getMessage();
        }
    }

}
