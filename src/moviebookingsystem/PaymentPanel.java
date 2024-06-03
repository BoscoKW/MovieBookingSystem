package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {

    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;

    public PaymentPanel(CardLayout cardLayout, JPanel cardLayoutPanel) {
        this.cardLayout = cardLayout;
        this.cardLayoutPanel = cardLayoutPanel;

        setLayout(new BorderLayout());

        JLabel paymentLabel = new JLabel("Payment Portal", SwingConstants.CENTER);
        add(paymentLabel, BorderLayout.NORTH);

        JPanel paymentInfoPanel = new JPanel();
        paymentInfoPanel.setLayout(new GridLayout(3, 2));

        JLabel cardLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        JLabel expLabel = new JLabel("Expiration Date (MM/YY):");
        expiryField = new JTextField();
        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JTextField();

        paymentInfoPanel.add(cardLabel);
        paymentInfoPanel.add(cardNumberField);
        paymentInfoPanel.add(expLabel);
        paymentInfoPanel.add(expiryField);
        paymentInfoPanel.add(cvvLabel);
        paymentInfoPanel.add(cvvField);

        add(paymentInfoPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm Payment");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText();
                String expiry = expiryField.getText();
                String cvv = cvvField.getText();

                if (cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}") &&
                        expiry.matches("\\d{2}/\\d{2}") &&
                        cvv.matches("\\d{3}")) {
                    JOptionPane.showMessageDialog(PaymentPanel.this, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardLayoutPanel, "TicketPage");
                } else {
                    JOptionPane.showMessageDialog(PaymentPanel.this, "Invalid payment details. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(confirmButton, BorderLayout.SOUTH);
    }
}
