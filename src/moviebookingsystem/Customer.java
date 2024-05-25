/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Customer {

    private String username;
    private String phoneNumber;
    private String emailAddress;

    public Customer(String username, String phoneNumber, String emailAddress) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public void registerNewUser() {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("./Resources/customers.txt", true)))) {
            printWriter.println("Username: " + username + "\nPhone Number: " + phoneNumber + "\nEmail Address: " + emailAddress);
            printWriter.println("-------------------------------------------");
            System.out.println("\nUser registered successfully!");
        } catch (IOException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }
}