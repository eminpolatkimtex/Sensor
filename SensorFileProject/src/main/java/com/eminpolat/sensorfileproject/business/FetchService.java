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

    // Birden fazla URL'yi tutmak için
    private final Map<Integer, String> urlMap = new ConcurrentHashMap<>();

    public FetchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // URL ekleme (id → url eşleştirme)
    public void setUrl(int id, String url) {
        urlMap.put(id, url);
    }

    public String getUrl(int id) {
        return urlMap.get(id);
    }

    // URL'den güvenli dosya adı üret
    private String getFileNameFromUrl(String url) {
        return url.replaceAll("https?://", "") // http:// veya https:// kaldır
                .replaceAll("[^a-zA-Z0-9.-]", "_") // geçersiz karakterleri "_" yap
                + ".txt";
    }

    // Tek bir URL'den veri çekip kaydet
    public void fetchAndSave(int id) {
        String url = urlMap.get(id);
        if (url == null) {
            System.out.println("ID " + id + " için URL bulunamadı!");
            return;
        }

        String fileName = getFileNameFromUrl(url);
        try (FileWriter writer = new FileWriter(fileName, true)) {
            String jsonData = restTemplate.getForObject(url, String.class);
            writer.write(jsonData + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Dosyaya yazılırken hata oluştu: " + fileName, e);
        }
    }

    // Tüm URL'lerden veri çek (Scheduled burayı çağıracak)
    public void fetchAll() {
        urlMap.keySet().forEach(this::fetchAndSave);
    }

    // Belirli bir dosyadan tüm veriyi oku
    public String getAllData(int id) {
        String url = urlMap.get(id);
        if (url == null) {
            return "ID " + id + " için URL bulunamadı!";
        }

        String fileName = getFileNameFromUrl(url);
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            return "Dosya okunamadı: " + e.getMessage();
        }
    }
}