package swing;

import javax.swing.*;

public class MainFrame extends JFrame {

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

        // -----------------------------
        // Aktionen für die Menüeinträge
        // -----------------------------

        // ActionEvent e: Objekt, das Aktion bei Klick auf Menüpunkt beschreibt

        // About
        aboutItem.addActionListener(e -> {
            dlgAbout aboutDialog = new dlgAbout(this);
            aboutDialog.setVisible(true);
        });


        // -------------------
        // Inhalt des Fensters
        // -------------------

        // Tabelle für die Anzeige der Kalenderereignisse
        // Spaltenüberschriften
        String[] columns = {"Ereignis", "von", "bis"};
        // Erstelle eine Tabelle mit den Spaltenüberschriften
        JTable table = new JTable(new javax.swing.table.DefaultTableModel(columns, 0));
        // Füge einige Beispielzeilen hinzu (diese sollten durch echte Daten ersetzt werden)
        add(new JScrollPane(table));

        setLocationRelativeTo(null); // Zentriert das Fenster
    }

}
