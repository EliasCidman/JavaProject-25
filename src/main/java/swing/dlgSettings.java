package swing;

import javax.swing.*;
import java.awt.*;

public class dlgSettings extends JDialog {

    private final JLabel infoLabel;

    // Konstruktor der Dialogbox "Settings"
    public dlgSettings(JFrame parent) {
        super(parent, "Settings", true);

        infoLabel = new JLabel("Einstellungen werden hier hinzugefügt");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(infoLabel, BorderLayout.CENTER);
        add(panel);

        setSize(400, 200);
        setLocationRelativeTo(parent);
    }
}
