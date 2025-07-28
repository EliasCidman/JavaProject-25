package swing;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.Properties;

import swing.DateLabelFormatter;

public class dlgSettings extends JDialog {

    private final JTextField urlField;
    private final JTextField fileNameField;
    //private final JSpinner fromDateSpinner;
    //private final JSpinner toDateSpinner;
    private final JDatePickerImpl fromDatePicker;
    private final JDatePickerImpl toDatePicker;
    private boolean confirmed = false;

    // Konstruktor der Dialogbox "Settings"
    public dlgSettings(JFrame parent, String prevUrl, String prevFileName, LocalDate prevFromDate, LocalDate prevToDate) {
        super(parent, "Settings", true);

        JLabel infoLabel = new JLabel("Einstellungen:");

        urlField = new JTextField(prevUrl,30);
        fileNameField = new JTextField(prevFileName, 20);


        // DatePicker-Modelle und Panels
        UtilDateModel fromModel = new UtilDateModel();
        fromModel.setDate(prevFromDate.getYear(), prevFromDate.getMonthValue() - 1, prevFromDate.getDayOfMonth());
        fromModel.setSelected(true);

        UtilDateModel toModel = new UtilDateModel();
        toModel.setDate(prevToDate.getYear(), prevToDate.getMonthValue() - 1, prevToDate.getDayOfMonth());
        toModel.setSelected(true);

        Properties prop = new Properties();
        prop.put("text.today", "Heute");
        prop.put("text.month", "Monat");
        prop.put("text.year", "Jahr");

        JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromModel, prop);
        fromDatePicker = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());

        JDatePanelImpl toDatePanel = new JDatePanelImpl(toModel, prop);
        toDatePicker = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.add(new JLabel("Kalender-ID:"));
        formPanel.add(urlField);
        formPanel.add(new JLabel("Dateiname:"));
        formPanel.add(fileNameField);
        formPanel.add(new JLabel("Zeitraum von:"));
        formPanel.add(fromDatePicker);
        formPanel.add(new JLabel("Zeitraum bis:"));
        formPanel.add(toDatePicker);

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

        setSize(600, 250);
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
        UtilDateModel model = (UtilDateModel) fromDatePicker.getModel();
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }

    public LocalDate getToDate() {
        UtilDateModel model = (UtilDateModel) toDatePicker.getModel();
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }
}
