import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MovieBookingSystemGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Map<Integer, String> movieMap;
    private Map<Integer, String> cinemaMap;
    private List<Showtime> showtimes;
    private JComboBox<String> cinemaComboBox;
    private JComboBox<String> movieComboBox;
    private JComboBox<String> showtimeComboBox;

    private Seat seat;

    public MovieBookingSystemGUI() {
        movieMap = movies();
        cinemaMap = getCinemas();
        showtimes = showtimes(movieMap);

        setTitle("Cinema Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        initializeWelcomePage();
        initializeMovieSelectionPage();
        initializeShowtimeSelectionPage();
        initializeCinemaSelectionPage();
        initializeSeatSelectionPage();
        initializePaymentPage();

        cardLayout.show(mainPanel, "WelcomePage");

        setVisible(true);
    }

    private void initializeWelcomePage() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Cinema Booking System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> cardLayout.show(mainPanel, "MovieSelectionPage"));
        welcomePanel.add(enterButton, BorderLayout.SOUTH);

        mainPanel.add(welcomePanel, "WelcomePage");
    }

    private void initializeMovieSelectionPage() {
        JPanel movieSelectionPanel = new JPanel();
        movieSelectionPanel.setLayout(new BorderLayout());

        JLabel movieLabel = new JLabel("Select a Movie", SwingConstants.CENTER);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 24));
        movieSelectionPanel.add(movieLabel, BorderLayout.NORTH);

        movieComboBox = new JComboBox<>(movieMap.values().toArray(new String[0]));
        movieSelectionPanel.add(movieComboBox, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "ShowtimeSelectionPage"));
        movieSelectionPanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(movieSelectionPanel, "MovieSelectionPage");
    }

    private void initializeShowtimeSelectionPage() {
        JPanel showtimeSelectionPanel = new JPanel();
        showtimeSelectionPanel.setLayout(new BorderLayout());

        JLabel showtimeLabel = new JLabel("Select a Showtime", SwingConstants.CENTER);
        showtimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        showtimeSelectionPanel.add(showtimeLabel, BorderLayout.NORTH);

        showtimeComboBox = new JComboBox<>();
        showtimeSelectionPanel.add(showtimeComboBox, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "CinemaSelectionPage"));
        showtimeSelectionPanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(showtimeSelectionPanel, "ShowtimeSelectionPage");
    }

    private void initializeCinemaSelectionPage() {
        JPanel cinemaSelectionPanel = new JPanel();
        cinemaSelectionPanel.setLayout(new BorderLayout());

        JLabel cinemaLabel = new JLabel("Select a Cinema", SwingConstants.CENTER);
        cinemaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cinemaSelectionPanel.add(cinemaLabel, BorderLayout.NORTH);

        cinemaComboBox = new JComboBox<>(cinemaMap.values().toArray(new String[0]));
        cinemaSelectionPanel.add(cinemaComboBox, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "SeatSelectionPage"));
        cinemaSelectionPanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(cinemaSelectionPanel, "CinemaSelectionPage");
    }

    private void initializeSeatSelectionPage() {
        JPanel seatSelectionPanel = new JPanel();
        seatSelectionPanel.setLayout(new BorderLayout());

        JLabel seatLabel = new JLabel("Select a Seat", SwingConstants.CENTER);
        seatLabel.setFont(new Font("Arial", Font.BOLD, 24));
        seatSelectionPanel.add(seatLabel, BorderLayout.NORTH);

        seat = new Seat(8, 10);
        seatSelectionPanel.add(seat.getSeatPanel(), BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "PaymentPage"));
        seatSelectionPanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(seatSelectionPanel, "SeatSelectionPage");
    }

    private void initializePaymentPage() {
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout(6, 2));

        JLabel cardNumberLabel = new JLabel("Card Number: ");
        JTextField cardNumberField = new JTextField();
        paymentPanel.add(cardNumberLabel);
        paymentPanel.add(cardNumberField);

        JLabel cardNameLabel = new JLabel("Name on Card: ");
        JTextField cardNameField = new JTextField();
        paymentPanel.add(cardNameLabel);
        paymentPanel.add(cardNameField);

        JLabel cardExpiryLabel = new JLabel("Expiry Date (MM/YY): ");
        JTextField cardExpiryField = new JTextField();
        paymentPanel.add(cardExpiryLabel);
        paymentPanel.add(cardExpiryField);

        JLabel cardCVVLabel = new JLabel("CVV: ");
        JTextField cardCVVField = new JTextField();
        paymentPanel.add(cardCVVLabel);
        paymentPanel.add(cardCVVField);

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
            // Validate and process payment
            if (validatePaymentDetails(cardNumberField.getText(), cardNameField.getText(), cardExpiryField.getText(), cardCVVField.getText())) {
                // Save payment information
                writePaymentInfo(cardNumberField.getText(), cardNameField.getText(), cardExpiryField.getText(), cardCVVField.getText());

                JOptionPane.showMessageDialog(this, "Payment Successful. Ticket Booked.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "WelcomePage");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Payment Details. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        paymentPanel.add(new JLabel());
        paymentPanel.add(payButton);

        mainPanel.add(paymentPanel, "PaymentPage");
    }

    private boolean validatePaymentDetails(String cardNumber, String cardName, String cardExpiry, String cardCVV) {
        return cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}") &&
                cardName.matches(".+") &&
                cardExpiry.matches("\\d{2}/\\d{2}") &&
                cardCVV.matches("\\d{3}");
    }

    private void writePaymentInfo(String cardNumber, String cardName, String cardExpiry, String cardCVV) {
        String filePath = "./Resources/payment_info.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            writer.write("Card Number: " + cardNumber);
            writer.newLine();
            writer.write("Name on Card: " + cardName);
            writer.newLine();
            writer.write("Expiry Date: " + cardExpiry);
            writer.newLine();
            writer.write("CVV: " + cardCVV);
            writer.newLine();
            writer.write("--------------------------------");
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing payment information to file: " + e.getMessage());
        }
    }

    public static Map<Integer, String> movies() {
        Map<Integer, String> movies = new HashMap<>();
        movies.put(1, "Kung Fu Panda 4");
        movies.put(2, "Dune: Part 2");
        movies.put(3, "Godzilla x Kong: The New Empire");
        movies.put(4, "Exhuma");
        movies.put(5, "Monkey Man");
        movies.put(6, "Ghostbusters: Frozen Empire");
        movies.put(7, "Wonka");
        movies.put(8, "Immaculate");

        return movies;
    }

    public static Map<Integer, String> getCinemas() {
        Map<Integer, String> cinemas = new HashMap<>();
        cinemas.put(1, "Queen Street");
        cinemas.put(2, "Newmarket");
        cinemas.put(3, "Sylvia Park");
        cinemas.put(4, "Albany");
        cinemas.put(5, "Manukau");
        cinemas.put(6, "Westgate");

        return cinemas;
    }

    public static List<Showtime> showtimes(Map<Integer, String> movieMap) {
        List<Showtime> showtimes = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : movieMap.entrySet()) {
                        List<Showtime> movieShowtimes = createShowtimes(entry.getValue());
            showtimes.addAll(movieShowtimes);
        }

        return showtimes;
    }

    public static List<Showtime> createShowtimes(String selectedMovie) {
        List<Showtime> showtimes = new ArrayList<>();
        Random random = new Random();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            List<LocalTime> times = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                int hour = random.nextInt(14) + 10;
                int minute = random.nextInt(4) * 15;
                times.add(LocalTime.of(hour, minute));
            }
            Collections.sort(times);

            for (LocalTime time : times) {
                if (!(time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(10, 0)))) {
                    String cinemaType = getCinemaType();
                    showtimes.add(new Showtime(selectedMovie, date.format(dateFormatter), time.format(DateTimeFormatter.ofPattern("hh:mm a")), cinemaType));
                }
            }
        }
        return showtimes;
    }

    public static String getCinemaType() {
        String[] cinemaTypes = {"VMAX", "Original", "Boutique"};
        Random random = new Random();
        return cinemaTypes[random.nextInt(cinemaTypes.length)];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MovieBookingSystemGUI();
            }
        });
    }
}

