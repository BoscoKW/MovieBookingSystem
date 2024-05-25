/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.util.*;
import java.io.*;

/**
 *
 * @author mardiliza
 */
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

    public void displaySeatingArrangement() {
        System.out.println("============== Cinema Screen ==============");
        System.out.print("   ");
        for (int j = 1; j <= columns; j++) {
            System.out.print(String.format(" %-3s", j));
        }
        System.out.println();
        char rowChar = 'A';
        Node current = head;
        for (int i = 0; i < rows; i++) {
            System.out.print(rowChar + "  ");
            for (int j = 0; j < columns; j++) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
            rowChar++;
        }
    }

    public void chooseSeat() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nSelect your seat (e.g. A3): ");
            String seatChoice = scanner.nextLine().toUpperCase();

            // Check if the seat choice is in the correct format
            if (seatChoice.length() != 2 || !Character.isLetter(seatChoice.charAt(0)) || !Character.isDigit(seatChoice.charAt(1))) {
                System.out.println("Invalid seat format. Please enter a valid seat (e.g., A3).");
                continue;
            }

            char rowChoice = seatChoice.charAt(0);
            int columnChoice = Character.getNumericValue(seatChoice.charAt(1));

            // Check if the row and column choices are within valid range
            boolean isValidRowChoice = rowChoice >= 'A' && rowChoice < ('A' + rows);
            boolean isValidColumnChoice = columnChoice >= 1 && columnChoice <= columns;

            if (!isValidRowChoice || !isValidColumnChoice) {
                System.out.println("Invalid seat choice. Please enter a valid seat within the range.");
                continue;
            }

            // Calculate the index of the selected seat in the linked list
            int index = (rowChoice - 'A') * columns + columnChoice - 1;

            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            if (current.data.equals("[X]")) {
                System.out.println("\nSeat chosen. Please choose another one.");
                continue;
            } else {
                current.data = "[\u001B[31mX\u001B[0m]"; // red X
                System.out.println("\nPlease enter your username: ");
                String username = scanner.nextLine();

                String seat = Character.toString(rowChoice) + columnChoice; // Convert row and column to seat format
                updateBookings(username, seat); // Update bookings with seat format

                System.out.print("\nSeat successfully booked by " + username + ": " + seat);
                break;
            }
        }
    }

    private void updateBookings(String username, String seat) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("Resources/bookings.txt", true))) {
            writer.write(username + "," + seat + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while updating bookings.");
            e.printStackTrace();
        }
    }
}
