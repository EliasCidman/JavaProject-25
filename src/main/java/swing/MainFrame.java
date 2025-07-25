package swing;

import calendar.clsGoogleCalendarService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // Variablen initialisieren für die Einstellungen
    private String calenderUrl = "";
    private String fileName = "";
    private LocalDate fromDate = LocalDate.now();
    private LocalDate toDate = LocalDate.now().plusDays(7);

    public MainFrame() {
        // Setzt den Titel des Fensters
        setTitle("Google Calendar Exporter");
        // Setzt die Standardoperationsoptionen, um das Fenster zu schließen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setzt die Größe des Fensters
        setSize(800, 600);


        //------------
        // Menüleiste
        //------------
        JMenuBar menuBar = new JMenuBar();

        // File Menü
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");

        // Füge einen ActionListener hinzu, um die Aktion beim Klicken auf "Save" zu definieren
        saveItem.addActionListener(e -> {
        try {
           // Kalenderdaten abrufen
           clsGoogleCalendarService calendarService = new clsGoogleCalendarService();
           List<Map<String, String>> events = calendarService.getEvents(
                   calenderUrl, fromDate.toString(), toDate.toString()
           );

           // Pfad zum Download-Ordner
            String downloadPath = System.getProperty("user.home") + "\\Downloads\\";
            String exportFileName = fileName.endsWith(".ics") ? fileName: fileName + ".ics";
            String fullFileName = downloadPath + exportFileName;

           // Export
           calendar.clsCalendarExporter exporter = new calendar.clsCalendarExporter();
           exporter.export(events, fullFileName);

           JOptionPane.showMessageDialog(this, "Datei erfolgreich gespeichert:\n" + fullFileName);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Exportieren der Kalenderdaten:\n" + ex.getMessage());
        }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // Application Menü
        JMenu appMenu = new JMenu("Application");
        JMenuItem settingsItem = new JMenuItem("Settings");
        appMenu.add(settingsItem);

        // Help Menü
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        // Füge die Menüs zur Menüleiste hinzu
        menuBar.add(fileMenu);
        menuBar.add(appMenu);
        menuBar.add(helpMenu);

        // Setzt die Menüleiste des Fensters
        setJMenuBar(menuBar);


        // -------------------
        // Inhalt des Fensters
        // -------------------

        // Tabelle für die Anzeige der Kalenderereignisse
        // Spaltenüberschriften
        String[] columns = {"Ereignis", "von", "bis"};
        tableModel = new javax.swing.table.DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        setLocationRelativeTo(null); // Zentriert das Fenster


        // -----------------------------
        // Aktionen für die Menüeinträge
        // -----------------------------

        // ActionEvent e: Objekt, das Aktion bei Klick auf Menüpunkt beschreibt

        // ####
        // File
        // ####

        // File -> Exit
        exitItem.addActionListener(e -> System.exit(0));


        // ###########
        // Application
        // ###########

        // Application -> Settings
        settingsItem.addActionListener(e -> {
            dlgSettings settingsDialog = new dlgSettings(this, calenderUrl, fileName, fromDate, toDate);
            settingsDialog.setVisible(true);

            if (settingsDialog.isConfirmed()) {
                // Speichere Einstellungen, wenn "OK" geklickt wurde
                calenderUrl = settingsDialog.getUrl();
                fileName = settingsDialog.getFileName();
                fromDate = settingsDialog.getFromDate();
                toDate = settingsDialog.getToDate();

                // Events abrufen und Tabelle aktualisieren
                try {
                    clsGoogleCalendarService calendarService = new clsGoogleCalendarService();
                    List<Map<String, String>> events = calendarService.getEvents(
                            calenderUrl,
                            fromDate.toString(),
                            toDate.toString()
                    );

                    // Tabelle leeren
                    tableModel.setRowCount(0);

                    for (Map<String, String> event : events) {
                        tableModel.addRow(new Object[]{
                                event.get("summary"),
                                event.get("start"),
                                event.get("end")
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,  "Fehler beim Laden der Kalenderdaten:\n" + ex.getMessage());
                }

            }
        });


        // ####
        // Help
        // ####

        // Help -> About
        aboutItem.addActionListener(e -> {
            dlgAbout aboutDialog = new dlgAbout(this);
            aboutDialog.setVisible(true);
        });
    }

}
