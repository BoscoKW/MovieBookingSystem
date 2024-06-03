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

        JButton checkBookingsButton = new JButton("Check Bookings");
        checkBookingsButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter your username:");
            if (username != null && !username.trim().isEmpty()) {
                checkBookings(username);
            }
        });

        JScrollPane scrollPane = new JScrollPane(bookingInfoTextArea);

        add(checkBookingsButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void checkBookings(String username) {
        bookingInfoTextArea.setText("");
        try {
            ResultSet rs = Database.getInstance().getBookings(username);
            boolean found = false;
            while (rs.next()) {
                found = true;
                bookingInfoTextArea.append("Seat: " + rs.getString("seat") + "\n");
            }
            if (!found) {
                bookingInfoTextArea.setText("No bookings for user " + username + ".");
            }
        } catch (SQLException e) {
            bookingInfoTextArea.setText("Error reading bookings: " + e.getMessage());
        }
    }
}
