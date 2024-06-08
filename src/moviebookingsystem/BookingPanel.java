package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingPanel extends JPanel {

    private JTextArea bookingInfoTextArea;

    public BookingPanel() {
        setLayout(new BorderLayout());

        bookingInfoTextArea = new JTextArea();
        bookingInfoTextArea.setEditable(false);
    }
}
