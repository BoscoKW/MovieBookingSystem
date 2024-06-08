package moviebookingsystem;

import javax.swing.*;
import java.awt.*;

public class TicketPanel extends JPanel {

    private JLabel ticketLabel;
    private JTextArea ticketInfo;

    public TicketPanel() {
        setLayout(new BorderLayout());

        ticketLabel = new JLabel("Your Ticket", SwingConstants.CENTER);
        ticketLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(ticketLabel, BorderLayout.NORTH);

        ticketInfo = new JTextArea();
        ticketInfo.setEditable(false);
        ticketInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        ticketInfo.setMargin(new Insets(10, 10, 10, 10));
        ticketInfo.setBackground(new Color(240, 240, 240));
        ticketInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(ticketInfo, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Page");
        backButton.addActionListener(e -> {
            // Logic to navigate back to the main page
            // Assuming mainPanel is the cardLayoutPanel in MainGUI
            ((CardLayout) getParent().getLayout()).show(getParent(), "CinemaSelectionPage");
        });

        add(backButton, BorderLayout.SOUTH);
    }

    public void updateTicketInfo(String cinema, String movie, String date, String time, String cinemaType, String seats) {
        ticketInfo.setText(String.format(
            "Ticket Information:\n\n" +
            "Cinema: %s\n" +
            "Movie: %s\n" +
            "Date: %s\n" +
            "Time: %s\n" +
            "Seats: %s\n",
            cinema, movie, date, time, seats
        ));
    }
}
