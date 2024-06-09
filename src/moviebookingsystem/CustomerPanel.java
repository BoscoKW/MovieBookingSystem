package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel extends JPanel {

    private JTextField usernameField;
    private JTextField phoneNumberField;
    private JTextField emailAddressField;
    private JButton createAccountButton;

    public CustomerPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel emailAddressLabel = new JLabel("Email Address:");
        gbc.gridx = 0;
        gbc.gridy = 1; // Changed to 1
        add(emailAddressLabel, gbc);

        emailAddressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1; // Changed to 1
        add(emailAddressField, gbc);
        
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 2; // Changed to 2
        add(phoneNumberLabel, gbc);

        phoneNumberField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2; // Changed to 2
        add(phoneNumberField, gbc);

        createAccountButton = new JButton("Create Account");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createAccountButton, gbc);

        setPreferredSize(new Dimension(400, 300));

        // Add action listener for the create account button
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                // Navigate to the next page with the username
                showNextPage(username);
            }
        });
    }

    // Method to show the next page after creating an account
    private void showNextPage(String username) {
        JPanel nextPage = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel(username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nextPage.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        JButton myTicketButton = new JButton("My Tickets");
        myTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the TicketPanel
                navigateToTicketPanel();
            }
        });
        buttonPanel.add(myTicketButton);

        nextPage.add(buttonPanel, BorderLayout.CENTER);

        getParent().add(nextPage, "NextPage");
        ((CardLayout) getParent().getLayout()).show(getParent(), "NextPage");
    }

    // Method to navigate to the TicketPanel
    private void navigateToTicketPanel() {
        MainGUI mainGui = (MainGUI) SwingUtilities.getWindowAncestor(this);
        mainGui.showTicketPanel();
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getEmailAddressField() {
        return emailAddressField;
    }
    
    public JTextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
        
    }
}
