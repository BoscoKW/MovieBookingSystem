/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class Booking {

    private String username;
    private Scanner scanner;
    private Map<Integer, String> movieMap;
    private Map<Integer, String> cinemaMap;

    public Booking(String username, Scanner scanner, Map<Integer, String> movieMap, Map<Integer, String> cinemaMap) {
        this.username = username;
        this.scanner = scanner;
        this.movieMap = movieMap;
        this.cinemaMap = cinemaMap;
    }

    // Method to save ticket information
    public static void saveTicketInfo(String username, String movie, String cinema, String date, String time, String seat) {
        String ticketInfo = username + ":" + movie + ":" + cinema + ":" + date + ":" + time + ":" + seat;

        try ( PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("./Resources/bookings.txt", true)))) {
            printWriter.println(ticketInfo);
        } catch (IOException e) {
            System.err.println("Error saving ticket information: " + e.getMessage());
        }
    }

    // Method to retrieve ticket information
    public static void retrieveTicketInfo() {
        System.out.println("\nTicket\n");
        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader("./Resources/bookings.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line); // Print each line of the bookings.txt file
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
    }

    // Method to check bookings
    public void checkBookings() {
        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader("./Resources/bookings.txt"))) {
            String line;
            boolean found = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 6 && parts[0].trim().equalsIgnoreCase(username.trim())) {
                    found = true;
                    System.out.println("\nBooking for " + username + ":");
                    System.out.println("Movie: " + parts[1].trim());
                    System.out.println("Cinema: " + parts[2].trim());
                    System.out.println("Date: " + parts[3].trim());
                    System.out.println("Time: " + parts[4].trim());
                    System.out.println("Seat: " + parts[5].trim());
                }
            }
            if (!found) {
                System.out.println("\nNo bookings found for " + username + ".\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
    }
}







