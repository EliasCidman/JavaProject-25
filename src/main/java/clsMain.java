import java.util.*;

public class clsMain  {
    public static void main(String[] args) throws Exception {

        // Überprüfen, ob die erforderlichen Argumente übergeben wurden
        if (args.length < 3) {
            System.out.println("Aufruf: java clsMain <Startdatum> <Enddatum> <Dateiname>");
            return;
        }

        // Argumente aus der Kommandozeile lesen
        String startDate = args[0];
        String endDate = args[1];
        String fileName = args[2];

        // Google Calendar Service initialisieren
        clsGoogleCalenderService calendarService = new clsGoogleCalenderService();
        List<Map<String, String>> events = calendarService.getEvents("primary", startDate, endDate);

        // iCalendar Exporter initialisieren
        clsICalenderExporter calendarExporter = new clsICalenderExporter();
        calendarExporter.export(events, fileName);

        // Ausgabe für den User für den Abschluss des Exports
        System.out.println("Export abgeschlossen: " + fileName);
    }
}