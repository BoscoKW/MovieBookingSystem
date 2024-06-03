package moviebookingsystem;

import javax.swing.*;
import java.awt.*;

public class TicketPanel extends JPanel {

    public TicketPanel(String cinema, String movie, String date, String time, String cinemaType, String seats) {
        setLayout(new BorderLayout());

        JLabel ticketLabel = new JLabel("Your Ticket", SwingConstants.CENTER);
        add(ticketLabel, BorderLayout.NORTH);

        JTextArea ticketInfo = new JTextArea();
        ticketInfo.setEditable(false);
        // Set the ticket information based on user input
        ticketInfo.setText("Ticket Information:\n\n" +
                "Movie: " + movie + "\n" +
                "Date: " + date + "\n" +
                "Time: " + time + "\n" +
                "Seats: " + seats + "\n" +
                "Cinema: " + cinema + "\n");
        
        add(ticketInfo, BorderLayout.CENTER);
    }
}
