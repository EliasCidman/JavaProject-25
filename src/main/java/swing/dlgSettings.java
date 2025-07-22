package swing;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class dlgSettings extends JDialog {

    private final JLabel infoLabel;
    private final JTextField urlField;
    private final JTextField fileNameField;
    private final JSpinner fromDateSpinner;
    private final JSpinner toDateSpinner;
    private boolean confirmed = false;

    // Konstruktor der Dialogbox "Settings"
    public dlgSettings(JFrame parent, String prevUrl, String prevFileName, LocalDate prevFromDate, LocalDate prevToDate) {
        super(parent, "Settings", true);

        infoLabel = new JLabel("Einstellungen:");

        urlField = new JTextField(prevUrl,30);
        fileNameField = new JTextField(prevFileName, 20);

        fromDateSpinner = new JSpinner(new SpinnerDateModel());
        toDateSpinner = new JSpinner(new SpinnerDateModel());
        fromDateSpinner.setEditor(new JSpinner.DateEditor(fromDateSpinner, "yyyy-MM-dd"));
        toDateSpinner.setEditor(new JSpinner.DateEditor(toDateSpinner, "yyyy-MM-dd"));

        // Vorbelegung der Datumsfelder
        fromDateSpinner.setValue(Date.from(prevFromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        toDateSpinner.setValue(Date.from(prevToDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.add(new JLabel("Kalender-URL:"));
        formPanel.add(urlField);
        formPanel.add(new JLabel("Dateiname:"));
        formPanel.add(fileNameField);
        formPanel.add(new JLabel("Zeitraum von:"));
        formPanel.add(fromDateSpinner);
        formPanel.add(new JLabel("Zeitraum bis:"));
        formPanel.add(toDateSpinner);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(400, 200);
        setLocationRelativeTo(parent);
    }

    // Getters
    public boolean isConfirmed() {
        return confirmed;
    }

    public String getUrl() {
        return urlField.getText();
    }

    public String getFileName() {
        return fileNameField.getText();
    }

    public LocalDate getFromDate() {
        java.util.Date date = (java.util.Date) fromDateSpinner.getValue();
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getToDate() {
        java.util.Date date = (java.util.Date) toDateSpinner.getValue();
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }
}
