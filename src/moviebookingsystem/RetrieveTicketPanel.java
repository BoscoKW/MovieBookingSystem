package moviebookingsystem;

import javax.swing.*;
import java.awt.*;

public class RetrieveTicketPanel extends JPanel {

    private JTextField nameField;
    private JTextArea ticketInfoArea;

    public RetrieveTicketPanel(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Retrieve Your Ticket", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter your name:"));
        nameField = new JTextField(20);
        inputPanel.add(nameField);

        JButton retrieveButton = new JButton("Retrieve Ticket");
        retrieveButton.addActionListener(e -> {
            String name = nameField.getText();
            String ticketInfo = mainGUI.getDatabase().getTicketInfoByName(name);
            if (ticketInfo != null) {
                ticketInfoArea.setText(ticketInfo);
            } else {
                JOptionPane.showMessageDialog(this, "No ticket found for the given name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputPanel.add(retrieveButton);

        add(inputPanel, BorderLayout.CENTER);

        ticketInfoArea = new JTextArea(10, 30);
        ticketInfoArea.setEditable(false);
        add(new JScrollPane(ticketInfoArea), BorderLayout.SOUTH);
        
        JButton backButton = new JButton("Back"); 
        backButton.addActionListener(e -> mainGUI.showWelcomePage());
        inputPanel.add(backButton);

        add(inputPanel, BorderLayout.CENTER);
    }
}
