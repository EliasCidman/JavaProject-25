import java.util.*;
import java.io.*;

public class clsICalenderExporter {
    // Exportiert die Events als iCalendar-Datei
    public void export(List<Map<String, String>> events, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // iCalendar Header
            writer.write("BEGIN:VCALENDAR\n");
            writer.write("VERSION:2.0\n");

            for (Map<String, String> event: events) {
                // iCalendar Event
                writer.write("BEGIN:VEVENT\n");
                writer.write("SUMMARY:" + event.get("summary") + "\n");
                writer.write("DTSTART:" + event.get("start").replaceAll("[-:]", "").replace("T", "T").replace("Z", "Z") + "\n");
                writer.write("DTEND:" + event.get("end").replaceAll("[-:]", "").replace("T", "T").replace("Z", "Z") + "\n");
                writer.write("END:VEVENT\n");
            }
            writer.write("END:VCALENDAR\n");
        }
    }
}
