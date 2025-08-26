package util;

import java.io.*;
import java.util.Properties;

public class clsSettingsManager {
    private static final String SETTINGS_FILE = "settings.properties";
    private final Properties properties = new Properties();

    public clsSettingsManager() {
        loadSettings();
    }

    public void loadSettings() {
        try (FileInputStream in = new FileInputStream(SETTINGS_FILE)) {
            properties.load(in);
        } catch (IOException ignored) {/*Datei könnte nicht existieren, was in Ordnung ist*/}
    }

    public void saveSettings() {
        try (FileOutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(out, "Programmeinstellungen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
