package calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class clsGoogleCalendarUtils {
    // Überprüft, ob eine Kalender-ID existiert
    public static boolean calendarIdExists(Calendar calendarService, String calendarId) {
        try {
            // Versucht, den Kalender mit der angegebenen ID abzurufen
            com.google.api.services.calendar.model.Calendar calendar =
                    calendarService.calendars().get(calendarId).execute();
            return calendar != null;
        } catch (GoogleJsonResponseException e) {
            // Wenn der Fehlercode 404 ist, existiert der Kalender nicht
            if (e.getStatusCode() == 404) {
                return false; // Kalender existiert nicht
            }
            throw new RuntimeException("Fehler beim Kalender-Check", e);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Kalender-Check", e);
        }
    }
}