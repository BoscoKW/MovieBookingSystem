/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

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
    private JFrame frame;
    private JPanel seatPanel;
    private JLabel[][] seatLabels;

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

    public void displaySeatingArrangement(JFrame parentFrame) {
        frame = new JFrame("Seat Selection");
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
                    seatLabels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            selectSeat(finalI, finalJ, parentFrame);
                        }
                    });
                }
                seatPanel.add(seatLabels[i][j]);
                current = current.next;
            }
            rowChar++;
        }

        frame.add(seatPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(parentFrame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void selectSeat(int row, int column, JFrame parentFrame) {
        String seatChoice = Character.toString((char) ('A' + row)) + (column + 1);
        System.out.println("Seat Selected: " + seatChoice);

        JTextField usernameField = new JTextField();
        Object[] message = {
            "Please enter your username: ", usernameField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Book Seat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                seatLabels[row][column].setText("[X]");
                seatLabels[row][column].setBackground(Color.RED);
                seatLabels[row][column].removeMouseListener(seatLabels[row][column].getMouseListeners()[0]);
                updateBookings(username, seatChoice);
                JOptionPane.showMessageDialog(frame, "Seat successfully booked by " + username + ": " + seatChoice);
            } else {
                JOptionPane.showMessageDialog(frame, "Username cannot be empty.");
            }
        }
    }

    private void updateBookings(String username, String seat) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources/bookings.txt", true))) {
            writer.write(username + "," + seat + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while updating bookings.");
            e.printStackTrace();
        }
    }
}
