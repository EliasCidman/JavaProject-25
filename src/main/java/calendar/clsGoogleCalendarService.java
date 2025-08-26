package calendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.client.util.DateTime;
import com.google.api.client.auth.oauth2.Credential;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class clsGoogleCalendarService {
    // --------------------------------------
    // Konstanten für die Google Calendar API
    // --------------------------------------

    // Name der Anwendung, die in der Google Cloud Console registriert ist
    private static final String APPLICATION_NAME = "JavaProject Ciuman Elias";

    // JSON Factory für die Verarbeitung von JSON-Daten
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    // Verzeichnis, in dem die OAuth 2.0 Tokens gespeichert werden
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    // Liste der benötigten Berechtigungen (Scopes) für den Zugriff auf den Google Kalender
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/calendar.readonly");

    // Pfad zur Datei mit den Anmeldeinformationen (credentials.json)
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";


    // ------------------------------------------------------------------------
    // Methode zum Abrufen der Anmeldeinformationen für die Google Calendar API
    // ------------------------------------------------------------------------
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        // Laden der Client-Secrets aus der JSON-Datei
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));

        // Aufbau des OAuth 2.0 Flows mit den Client-Secrets und den benötigten Berechtigungen
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        // Lokaler Server für die Authentifizierung
        com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver receiver =
                new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver.Builder().setPort(8888).build();

        // Startet den Autorisierungsprozess und wartet auf die Rückgabe des Tokens
        return new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // -----------------------------------------
    // Methode zum Abrufen des Calendar Service
    // -----------------------------------------
    public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    // -------------------------------------------
    // Holt alle Events aus dem Google Kalender
    // -------------------------------------------
    public List<Map<String, String>> getEvents(String calenderId, String startDate, String endDate) throws Exception {
        Calendar service = getCalendarService();
        // Zeitbereich für die Abfrage der Events
        DateTime timeMin = new DateTime(startDate + "T00:00:00Z");
        DateTime timeMax = new DateTime(endDate + "T23:59:59Z");

        // Holt Events aus angegeben Kalender mit den angegebenen Zeitparamtern
        Events events = service.events().list(calenderId)
                .setTimeMin(timeMin)
                .setTimeMax(timeMax)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        // Speichert Events in einer Liste von Maps
        List<Map<String, String>> eventList = new ArrayList<>();
        for (Event event : events.getItems()) {
            Map<String, String> map = new HashMap<>();
            map.put("summary", event.getSummary());
            // Unterscheide zwischen ganzen Tagen und Terminen mit Uhrzeit
            map.put("start", event.getStart().getDateTime() != null ? event.getStart().getDateTime().toStringRfc3339().replace("T", " ").replace("Z", " ") : event.getStart().getDate().toStringRfc3339().replace("T", " ").replace("Z", " "));
            map.put("end", event.getEnd().getDateTime() != null ? event.getEnd().getDateTime().toStringRfc3339().replace("T", " ").replace("Z", " ") : event.getEnd().getDate().toStringRfc3339().replace("T", " ").replace("Z", " "));
            eventList.add(map);
        }

        return eventList;
    }
}