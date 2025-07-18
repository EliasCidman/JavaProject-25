package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

public class dlgAbout extends JDialog {

    // Label für die Anzeige von Informationen
    private final JLabel infoLabel;

    // Konstruktor der Dialogbox "About"
    public dlgAbout(JFrame parent) {
        super(parent, "About", true);

        infoLabel = new JLabel(getHtml(16), SwingConstants.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(infoLabel, BorderLayout.CENTER);
        add(panel);

        setSize(400, 200);
        setLocationRelativeTo(parent);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.max(12, getHeight() / 15);
                infoLabel.setText(getHtml(size));
            }
        });
    }

    // Hilfsmethode, um HTML-Text zu generieren
    private String getHtml(int baseFontSize) {
        int h2Size = (int)(baseFontSize * 1.4);
        int pSize = (int)(baseFontSize * 0.9);

        return "<html><div>" +
                "<h2 style='font-size:" + h2Size + "px;'>Google Calendar Exporter in Java</h2>" +
                "<small style='font-size:" + baseFontSize + "px;'>DHBW 2025 Sommersemester</small><br>" +
                "<p style='font-size:" + pSize + "px;'>Entwickler: Elias Ciuman</p></div></html>";
    }
}
