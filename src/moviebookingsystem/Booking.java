package moviebookingsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Booking {

    public String username;
    public boolean hasBookings;

    public Booking(String username) {
        this.username = username;
        this.hasBookings = false;
    }
    

    public void checkBookings() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Resources/bookings.txt"))) {
            boolean found = false;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].trim().equalsIgnoreCase(username.trim())) {
                    found = true;
                    if (parts.length == 1 || parts[1].isEmpty()) {
                        System.out.println("\nNo bookings for " + username + ".");
                    } else {
                        System.out.println("\nBookings for " + username + ":");
                        for (int i = 1; i < parts.length; i++) {
                            System.out.println("Seat: " + parts[i].trim());
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("No bookings for user " + username + ".");
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
    }
}