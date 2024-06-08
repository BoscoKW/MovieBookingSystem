package moviebookingsystem;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {

    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private MainGUI mainGUI;

    public PaymentPanel(CardLayout cardLayout, JPanel cardLayoutPanel, MainGUI mainGUI) {
        this.cardLayout = cardLayout;
        this.cardLayoutPanel = cardLayoutPanel;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());

        JLabel paymentLabel = new JLabel("Payment Portal", SwingConstants.CENTER);
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(paymentLabel, BorderLayout.NORTH);

        JPanel paymentInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel cardLabel = new JLabel("Card number (xxxx xxxx xxxx xxxx):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        paymentInfoPanel.add(cardLabel, gbc);

        cardNumberField = new JTextField(19);
        cardNumberField.setPreferredSize(new Dimension(150, 25));
        ((AbstractDocument) cardNumberField.getDocument()).setDocumentFilter(new CardNumberDocumentFilter());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        paymentInfoPanel.add(cardNumberField, gbc);

        JLabel expLabel = new JLabel("Expiry (MM/YY):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        paymentInfoPanel.add(expLabel, gbc);

        expiryField = new JTextField(5);
        expiryField.setPreferredSize(new Dimension(50, 25));
        ((AbstractDocument) expiryField.getDocument()).setDocumentFilter(new ExpiryDocumentFilter());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        paymentInfoPanel.add(expiryField, gbc);

        JLabel cvvLabel = new JLabel("CVV (xxx):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        paymentInfoPanel.add(cvvLabel, gbc);

        cvvField = new JTextField(3);
        cvvField.setPreferredSize(new Dimension(30, 25));
        ((AbstractDocument) cvvField.getDocument()).setDocumentFilter(new CVVDocumentFilter());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        paymentInfoPanel.add(cvvField, gbc);

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
                    mainGUI.updateTicketPanel(); // Update the ticket panel with the selected details
                    cardLayout.show(cardLayoutPanel, "TicketPage");
                } else {
                    JOptionPane.showMessageDialog(PaymentPanel.this, "Invalid payment details. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(600, 300)); // Adjust size to fit content
    }

    private static class CardNumberDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(string);
            if (fb.getDocument().getLength() + string.length() > 19) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i)) && sb.charAt(i) != ' ') {
                    return;
                }
            }
            super.insertString(fb, offset, sb.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(text);
            if (fb.getDocument().getLength() - length + text.length() > 19) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i)) && sb.charAt(i) != ' ') {
                    return;
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }

    private static class ExpiryDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(string);
            if (fb.getDocument().getLength() + string.length() > 5) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i)) && sb.charAt(i) != '/') {
                    return;
                }
            }
            super.insertString(fb, offset, sb.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(text);
            if (fb.getDocument().getLength() - length + text.length() > 5) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i)) && sb.charAt(i) != '/') {
                    return;
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }

    private static class CVVDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(string);
            if (fb.getDocument().getLength() + string.length() > 3) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i))) {
                    return;
                }
            }
            super.insertString(fb, offset, sb.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(text);
            if (fb.getDocument().getLength() - length + text.length() > 3) {
                return;
            }
            for (int i = 0; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i))) {
                    return;
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }
}
