package com.eminpolat.sensorfileproject.business;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FetchService {
    private final RestTemplate restTemplate;

    // Birden fazla cihazı tutmak için
    private final Map<Integer, String> deviceMap = new ConcurrentHashMap<>();

    public FetchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Cihaz ekleme (id → deviceName eşleştirme)
    public void setDevice(int id, String deviceName) {
        deviceMap.put(id, deviceName);
    }

    public String getDevice(int id) {
        return deviceMap.get(id);
    }

    // Cihaz adından URL oluştur
    private String getUrlFromDevice(String deviceName) {
        return "http://" + deviceName + ".local/getCycles";
    }

    // URL'den güvenli dosya adı üret
    private String getFileNameFromDevice(String deviceName) {
        return deviceName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".txt";
    }

    // Tek bir cihazdan veri çekip kaydet
    public void fetchAndSave(int id) {
        String deviceName = deviceMap.get(id);
        if (deviceName == null) {
            System.out.println("ID " + id + " için cihaz bulunamadı!");
            return;
        }

        String url = getUrlFromDevice(deviceName);
        String fileName = getFileNameFromDevice(deviceName);

        try (FileWriter writer = new FileWriter(fileName, true)) {
            String jsonData = restTemplate.getForObject(url, String.class);
            writer.write(jsonData + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Dosyaya yazılırken hata oluştu: " + fileName, e);
        }
    }

    // Tüm cihazlardan veri çek (Scheduled burayı çağıracak)
    public void fetchAll() {
        deviceMap.keySet().forEach(this::fetchAndSave);
    }

    // Belirli bir cihaz dosyasından tüm veriyi oku
    public String getAllData(int id) {
        String deviceName = deviceMap.get(id);
        if (deviceName == null) {
            return "ID " + id + " için cihaz bulunamadı!";
        }

        String fileName = getFileNameFromDevice(deviceName);
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            return "Dosya okunamadı: " + e.getMessage();
        }
    }
}