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

public class clsGoogleCalenderService {
    private static final String APPLICATION_NAME = "JavaProject Ciuman Elias";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/calendar.readonly");
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver receiver =
                new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver.Builder().setPort(8888).build();

        return new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private Calendar getCalenderService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Map<String, String>> getEvents(String calenderId, String startDate, String endDate) throws Exception {
        Calendar service = getCalenderService();
        DateTime timeMin = new DateTime(startDate + "T00:00:00Z");
        DateTime timeMax = new DateTime(endDate + "T23:59:59Z");

        Events events = service.events().list(calenderId)
                .setTimeMin(timeMin)
                .setTimeMax(timeMax)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        List<Map<String, String>> eventList = new ArrayList<>();
        for (Event event : events.getItems()) {
            Map<String, String> map = new HashMap<>();
            map.put("summary", event.getSummary());
            map.put("start", event.getStart().getDateTime() != null ? event.getStart().getDateTime().toStringRfc3339() : event.getStart().getDate().toStringRfc3339());
            map.put("end", event.getEnd().getDateTime() != null ? event.getEnd().getDateTime().toStringRfc3339() : event.getEnd().getDate().toStringRfc3339());
            eventList.add(map);
        }

        return eventList;
    }
}