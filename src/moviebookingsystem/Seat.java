package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Node {

    String data;
    Node prev, next;

    public Node(String data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class Seat {

    private Node head;
    private int rows;
    private int columns;
    private JPanel seatPanel;
    private JLabel[][] seatLabels;
    private static final String DB_URL = "jdbc:sqlite:moviebooking.db";

    public Seat(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        generateSeats();
        generateOccupiedSeats();
    }

    private void generateSeats() {
        head = new Node("[ ]");
        Node current = head;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 1; j++) {
                Node newNode = new Node("[ ]");
                current.next = newNode;
                newNode.prev = current;
                current = newNode;
            }
            Node newRow = new Node("[ ]");
            current.next = newRow;
            newRow.prev = current;
            current = newRow;
        }
    }

    private void generateOccupiedSeats() {
        Random random = new Random();
        Set<Integer> occupiedSeats = new HashSet<>();
        int totalSeats = rows * columns;
        while (occupiedSeats.size() < Math.min(random.nextInt(6) + 5, totalSeats)) {
            int seatNumber = random.nextInt(totalSeats);
            occupiedSeats.add(seatNumber);
        }

        Node current = head;
        int seatNumber = 0;
        while (current != null) {
            if (occupiedSeats.contains(seatNumber)) {
                current.data = "[X]";
            }
            current = current.next;
            seatNumber++;
        }
    }

    public JPanel getSeatPanel() {
        seatPanel = new JPanel(new GridLayout(rows + 1, columns + 1));
        seatLabels = new JLabel[rows][columns];

        seatPanel.add(new JLabel("")); // Empty corner
        for (int j = 1; j <= columns; j++) {
            seatPanel.add(new JLabel(String.format(" %d ", j), SwingConstants.CENTER));
        }

        char rowChar = 'A';
        Node current = head;
        for (int i = 0; i < rows; i++) {
            seatPanel.add(new JLabel(String.valueOf(rowChar), SwingConstants.CENTER));
            for (int j = 0; j < columns; j++) {
                seatLabels[i][j] = new JLabel(current.data, SwingConstants.CENTER);
                seatLabels[i][j].setOpaque(true);
                if (current.data.equals("[X]")) {
                    seatLabels[i][j].setBackground(Color.RED);
                } else {
                    seatLabels[i][j].setBackground(Color.GREEN);
                    int finalI = i;
                    int finalJ = j;
                    seatLabels[i][j].addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            selectSeat(finalI, finalJ);
                        }
                    });
                }
                seatPanel.add(seatLabels[i][j]);
                current = current.next;
            }
            rowChar++;
        }

        return seatPanel;
    }

    private void selectSeat(int row, int column) {
        String seatChoice = Character.toString((char) ('A' + row)) + (column + 1);
        System.out.println("Seat Selected: " + seatChoice);

        JTextField usernameField = new JTextField();
        Object[] message = {
            "Please enter your username: ", usernameField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Book Seat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                seatLabels[row][column].setText("[X]");
                seatLabels[row][column].setBackground(Color.RED);
                seatLabels[row][column].removeMouseListener(seatLabels[row][column].getMouseListeners()[0]);
                updateBookings(username, seatChoice);
                JOptionPane.showMessageDialog(null, "Seat successfully booked by " + username + ": " + seatChoice);
            } else {
                JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            }
        }
    }

    private void updateBookings(String username, String seat) {
        String query = "INSERT INTO bookings (username, seat) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, seat);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating bookings: " + e.getMessage());
        }
    }
}
