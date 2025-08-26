package calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class clsGoogleCalendarUtils {
    public static boolean calendarIdExists(Calendar calendarService, String calendarId) {
        try {
            com.google.api.services.calendar.model.Calendar calendar =
                    calendarService.calendars().get(calendarId).execute();
            return calendar != null;
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 404) {
                return false; // Kalender existiert nicht
            }
            throw new RuntimeException("Fehler beim Kalender-Check", e);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Kalender-Check", e);
        }
    }
}