package util;

import java.io.*;
import java.util.Properties;

public class clsSettingsManager {
    private static final String SETTINGS_FILE = "settings.properties";
    private final Properties properties = new Properties();

    // Konstruktor lädt die Einstellungen beim Erstellen der Instanz
    public clsSettingsManager() {
        loadSettings();
    }

    // Lädt die Einstellungen aus der Datei
    public void loadSettings() {
        try (FileInputStream in = new FileInputStream(SETTINGS_FILE)) {
            properties.load(in);
        } catch (IOException ignored) {/*Datei könnte nicht existieren, was in Ordnung ist*/}
    }

    // Speichert die aktuellen Einstellungen in die Datei
    public void saveSettings() {
        try (FileOutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(out, "Programmeinstellungen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter und Setter für die Einstellungen
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
