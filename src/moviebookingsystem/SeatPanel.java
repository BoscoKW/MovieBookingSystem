package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SeatPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private String selectedMovie;
    private String selectedDate;
    private String selectedTime;
    private String selectedCinema;
    private JButton[][] seatButtons;
    private Set<String> selectedSeats;
    private JLabel selectedSeatLabel; 

    public SeatPanel(CardLayout cardLayout, JPanel cardLayoutPanel) {
        this.cardLayout = cardLayout;
        this.cardLayoutPanel = cardLayoutPanel;
        this.selectedSeats = new HashSet<>();

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("FRONT OF CINEMA", SwingConstants.CENTER); // Add label for "FRONT OF CINEMA"
        add(titleLabel, BorderLayout.NORTH);

        JPanel seatPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int numRows = 8; // Define the number of rows
        int numCols = 8; // Define the number of columns
        seatButtons = new JButton[numRows][numCols]; // Adjust rows and columns as needed

        Random random = new Random();

        // Add row labels with alphabets
        String[] rowLabels = new String[numRows];
        for (int i = 0; i < numRows; i++) {
            rowLabels[i] = Character.toString((char) ('A' + i));
            JLabel rowLabel = new JLabel(rowLabels[i], SwingConstants.CENTER);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.insets = new Insets(5, 5, 5, 5);
            seatPanel.add(rowLabel, gbc);
        }

        // Add column labels with numbers
        String[] columnLabels = new String[numCols];
        for (int i = 0; i < numCols; i++) {
            columnLabels[i] = Integer.toString(i + 1);
            JLabel columnLabel = new JLabel(columnLabels[i], SwingConstants.CENTER);
            gbc.gridx = i + 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            seatPanel.add(columnLabel, gbc);
        }

        for (int i = 0; i < numRows; i++) { // Adjust rows as needed
            for (int j = 0; j < numCols; j++) { // Adjust columns as needed
                JButton seatButton = new JButton();
                seatButton.setPreferredSize(new Dimension(30, 30)); // Set preferred size for square buttons
                seatButton.setOpaque(true); // Ensure button is opaque for background color to be visible
                seatButton.setBorderPainted(false); // Remove border for cleaner look
                seatButton.setBackground(random.nextBoolean() ? Color.RED : Color.LIGHT_GRAY); // Randomly set seat color
                seatButton.addActionListener(new SeatButtonListener(seatButton)); // Add action listener
                seatButtons[i][j] = seatButton;

                gbc.gridx = j + 1;
                gbc.gridy = i + 1;
                gbc.insets = new Insets(5, 5, 5, 5); // Add insets for gap between buttons
                seatPanel.add(seatButton, gbc);
            }
        }

        add(new JScrollPane(seatPanel), BorderLayout.CENTER);

        // Create label to display selected seat
        selectedSeatLabel = new JLabel("", SwingConstants.CENTER);
        add(selectedSeatLabel, BorderLayout.SOUTH);

        // Create button panel for back and proceed to payment
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardLayoutPanel, "ShowtimePage"));

        JButton proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the PaymentPage
                cardLayout.show(cardLayoutPanel, "PaymentPage");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);
        buttonPanel.add(proceedButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Listener class for seat buttons
    private class SeatButtonListener implements ActionListener {

        private JButton button;

        public SeatButtonListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (button.getBackground() == Color.LIGHT_GRAY) {
                button.setBackground(Color.GREEN); // Set background color to green when selected
                selectedSeats.add(getSeatKey(button)); // Add the selected seat to the set
            } else if (button.getBackground() == Color.GREEN) {
                button.setBackground(Color.LIGHT_GRAY); // Set background color back to grey when deselected
                selectedSeats.remove(getSeatKey(button)); // Remove the deselected seat from the set
            } else {
                // Seat is occupied (red), show a pop-up message
                JOptionPane.showMessageDialog(SeatPanel.this, "Seat is unavailable. Please select another seat.", "Seat Unavailable", JOptionPane.WARNING_MESSAGE);
            }

            updateSelectedSeatLabel(); // Update the selected seat label
        }

        // Helper method to get the key representing the seat position
        private String getSeatKey(JButton button) {
            // Example: "A1", "B2", etc.
            int row = -1, col = -1;
            outerLoop:
            for (int i = 0; i < seatButtons.length; i++) {
                for (int j = 0; j < seatButtons[i].length; j++) {
                    if (seatButtons[i][j] == button) {
                        row = i;
                        col = j;
                        break outerLoop;
                    }
                }
            }
            return Character.toString((char) ('A' + row)) + (col + 1);
        }
    }

    // Method to update the selected seat label
    private void updateSelectedSeatLabel() {
        if (!selectedSeats.isEmpty()) {
            StringBuilder selectedSeatsText = new StringBuilder("Selected Seats: ");
            for (String seat : selectedSeats) {
                selectedSeatsText.append(seat).append(", ");
            }
            selectedSeatsText.delete(selectedSeatsText.length() - 2, selectedSeatsText.length()); // Remove the last comma and space
            selectedSeatLabel.setText(selectedSeatsText.toString());
        } else {
            selectedSeatLabel.setText(""); // Clear the label if no seat is selected
        }
    }
}
