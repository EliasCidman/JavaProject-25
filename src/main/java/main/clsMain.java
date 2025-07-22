package main;

import calendar.*;
import swing.MainFrame;

import javax.swing.*;
import java.util.*;

public class clsMain  {
    public static void main(String[] args) throws Exception {

        // Startet die GUI in einem Event-Dispatching-Thread
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));

        // Überprüfen, ob die erforderlichen Argumente übergeben wurden
        if (args.length < 3) {
            System.out.println("Aufruf: java main.clsMain <Startdatum> <Enddatum> <Dateiname>");
            return;
        }

        // Argumente aus der Kommandozeile lesen
        String startDate = args[0];
        String endDate = args[1];
        String fileName = args[2];

        // Google Calendar Service initialisieren
        clsGoogleCalendarService calendarService = new clsGoogleCalendarService();
        List<Map<String, String>> events = calendarService.getEvents("primary", startDate, endDate);

        // iCalendar Exporter initialisieren
        clsCalendarExporter calendarExporter = new clsCalendarExporter();
        calendarExporter.export(events, fileName);

        // Ausgabe für den User für den Abschluss des Exports
        System.out.println("Export abgeschlossen: " + fileName);
    }
}