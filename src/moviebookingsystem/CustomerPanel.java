/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel extends JPanel {

    private JTextField usernameField;
    private JTextField phoneNumberField;
    private JTextField emailAddressField;

    public CustomerPanel() {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        add(phoneNumberField);

        add(new JLabel("Email Address:"));
        emailAddressField = new JTextField();
        add(emailAddressField);

    }
}
