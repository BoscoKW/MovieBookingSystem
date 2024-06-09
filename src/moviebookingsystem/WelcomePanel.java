package moviebookingsystem;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Movie Booking System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JButton proceedToBookButton = new JButton("Proceed to Book");
        proceedToBookButton.setPreferredSize(new Dimension(150, 50));
        proceedToBookButton.addActionListener(e -> mainGUI.showBookingPage());

        JButton checkTicketButton = new JButton("Check Your Ticket");
        checkTicketButton.setPreferredSize(new Dimension(150, 50));
        checkTicketButton.addActionListener(e -> mainGUI.showRetrieveTicketPage());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(proceedToBookButton);
        buttonPanel.add(checkTicketButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
