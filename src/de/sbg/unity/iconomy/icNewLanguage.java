package de.sbg.unity.iconomy;

import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class icNewLanguage {

    private HashMap<String, HashMap<String, String>> savedText;
    private final DatabaseFormat dataFormat;
    private final icConsole Console;

    public icNewLanguage(iConomy plugin, icConsole Console) {
        savedText = new HashMap<>();
        dataFormat = new DatabaseFormat();
        this.Console = Console;
    }

    private String getSave(String text, String lang) {
        if (savedText.containsKey(text)) {
            if (savedText.get(text).containsKey(lang)) {
                return savedText.get(text).get(lang);
            }
        }
        return "";
    }

    public void saveAllAsData() {
        final String FILEPATH = "translate.dat";
        
        try (FileOutputStream fos = new FileOutputStream(FILEPATH)) {
            byte[] b = dataFormat.toBlob(savedText);
            fos.write(b);
            Console.sendErr("Transalte", "Datei erfolgreich geschrieben: " + FILEPATH);
        } catch (IOException e) {
            Console.sendErr("Transalte", e.getMessage());
        }
    }

    public void loadAllFromData() {
        final String FILEPATH = "translate.dat";
        File file = new File(FILEPATH);
        if (file.exists()) {
            try {
                byte[] dataRead = Files.readAllBytes(Path.of(FILEPATH));
                Console.sendInfo("Translate","Datei erfolgreich gelesen: " + FILEPATH);
                setSavedText((HashMap<String, HashMap<String, String>>)dataFormat.toObject(dataRead));
            } catch (IOException | ClassNotFoundException e) {
                Console.sendErr("Transalte", e.getMessage());
            }
        }
    }

    public void setSavedText(HashMap<String, HashMap<String, String>> savedText) {
        this.savedText = savedText;
    }
    
    

    /**
     * Übersetzt einen Text von einer Quellsprache in eine Zielsprache mit
     * LibreTranslate.
     *
     * @param text Der zu übersetzende Text.
     * @param sourceLang Die Sprache des Originaltexts (z. B. "de" für Deutsch).
     * @param targetLang Die Sprache, in die übersetzt werden soll (z. B. "en"
     * für Englisch).
     * @return Der übersetzte Text.
     */
    public String translate(String text, String sourceLang, String targetLang) {
        String serverUrl = "http://89.58.19.92:8080"; // URL des Übersetzungsservers
        if (!isServerRunning(serverUrl)) {
            System.err.println("Übersetzungsserver ist nicht erreichbar.");
            return text;
        }

        if (!sourceLang.equals(targetLang)) {
            String save = getSave(text, targetLang);
            if (!save.isBlank()) {
                return save;
            }
            try {
                // URL des lokalen LibreTranslate-Servers
                String url = serverUrl + "/translate";

                // Anfrage-Body erstellen
                String requestBody = "q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                        + "&source=" + sourceLang
                        + "&target=" + targetLang;

                // HTTP-Anfrage erstellen
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                // HTTP-Client senden
                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Übersetzung aus der Antwort extrahieren
                String responseBody = response.body();
                if (responseBody.contains("\"translatedText\":")) {
                    String result = responseBody.split("\"translatedText\":\"")[1].split("\"")[0];
                    
                    if (savedText.containsKey(text)) {
                        savedText.get(text).put(targetLang, result);
                    } else {
                        savedText.put(text, new HashMap<>()).put(targetLang, result);
                    }
                    return result;
                } else {
                    throw new Exception("Fehler bei der Übersetzung: " + responseBody);
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return text;
    }
    
    

    /**
     * Prüft, ob der Übersetzungsserver läuft.
     *
     * @param serverUrl Die URL des Servers.
     * @return true, wenn der Server läuft, false sonst.
     */
    private boolean isServerRunning(String serverUrl) {
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 Sekunden Timeout
            connection.connect();
            return connection.getResponseCode() == 200; // Status 200 = OK
        } catch (IOException e) {
            return false;
        }
    }

}
