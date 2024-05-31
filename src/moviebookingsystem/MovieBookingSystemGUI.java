package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MovieBookingSystemGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<Integer, String> movieMap;
    private Map<Integer, String> cinemaMap;
    private List<Showtime> showtimes;
    private JList<String> movieList;
    private JList<String> showtimeList;
    private JList<String> cinemaList;
    private Seat seat;
    private static final String DB_URL = "jdbc:sqlite:moviebooking.db";

    public MovieBookingSystemGUI() {
        movieMap = movies();
        cinemaMap = getCinemas();
        showtimes = showtimes(movieMap);

        setTitle("Cinema Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        DatabaseHelper.initializeDatabase();
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
        welcomePanel.setLayout(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to Cinema Booking System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension(200, 40));
        enterButton.addActionListener(e -> cardLayout.show(mainPanel, "MovieSelectionPage"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enterButton);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(welcomePanel, "WelcomePage");
    }

    private void initializeMovieSelectionPage() {
        JPanel movieSelectionPanel = new JPanel(new BorderLayout(10, 10));

        JLabel movieLabel = new JLabel("Select a Movie", SwingConstants.CENTER);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 24));
        movieSelectionPanel.add(movieLabel, BorderLayout.NORTH);

        movieList = new JList<>(movieMap.values().toArray(new String[0]));
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(movieList);
        movieSelectionPanel.add(scrollPane, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(200, 40));
        nextButton.addActionListener(e -> {
            updateShowtimes();
            cardLayout.show(mainPanel, "ShowtimeSelectionPage");
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        movieSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(movieSelectionPanel, "MovieSelectionPage");
    }

    private void initializeShowtimeSelectionPage() {
        JPanel showtimeSelectionPanel = new JPanel(new BorderLayout(10, 10));

        JLabel showtimeLabel = new JLabel("Select a Showtime", SwingConstants.CENTER);
        showtimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        showtimeSelectionPanel.add(showtimeLabel, BorderLayout.NORTH);

        showtimeList = new JList<>();
        showtimeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(showtimeList);
        showtimeSelectionPanel.add(scrollPane, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(200, 40));
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "CinemaSelectionPage"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        showtimeSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(showtimeSelectionPanel, "ShowtimeSelectionPage");
    }

    private void initializeCinemaSelectionPage() {
        JPanel cinemaSelectionPanel = new JPanel(new BorderLayout(10, 10));

        JLabel cinemaLabel = new JLabel("Select a Cinema", SwingConstants.CENTER);
        cinemaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cinemaSelectionPanel.add(cinemaLabel, BorderLayout.NORTH);

        cinemaList = new JList<>(cinemaMap.values().toArray(new String[0]));
        cinemaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(cinemaList);
        cinemaSelectionPanel.add(scrollPane, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(200, 40));
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "SeatSelectionPage"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        cinemaSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(cinemaSelectionPanel, "CinemaSelectionPage");
    }

    private void initializeSeatSelectionPage() {
        JPanel seatSelectionPanel = new JPanel(new BorderLayout(10, 10));

        JLabel seatLabel = new JLabel("Select a Seat", SwingConstants.CENTER);
        seatLabel.setFont(new Font("Arial", Font.BOLD, 24));
        seatSelectionPanel.add(seatLabel, BorderLayout.NORTH);

        seat = new Seat(8, 10);
        seatSelectionPanel.add(seat.getSeatPanel(), BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(200, 40));
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "PaymentPage"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        seatSelectionPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(seatSelectionPanel, "SeatSelectionPage");
    }

    private void initializePaymentPage() {
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel cardNumberLabel = new JLabel("Card Number: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(cardNumberLabel, gbc);

        JTextField cardNumberField = new JTextField();
        cardNumberField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        paymentPanel.add(cardNumberField, gbc);

        JLabel cardNameLabel = new JLabel("Name on Card: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentPanel.add(cardNameLabel, gbc);

        JTextField cardNameField = new JTextField();
        cardNameField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        paymentPanel.add(cardNameField, gbc);

        JLabel cardExpiryLabel = new JLabel("Expiry Date (MM/YY): ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentPanel.add(cardExpiryLabel, gbc);

        JTextField cardExpiryField = new JTextField();
        cardExpiryField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        paymentPanel.add(cardExpiryField, gbc);

        JLabel cardCVVLabel = new JLabel("CVV: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        paymentPanel.add(cardCVVLabel, gbc);

        JTextField cardCVVField = new JTextField();
        cardCVVField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        paymentPanel.add(cardCVVField, gbc);

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(200, 40));
        payButton.addActionListener(e -> {
            // Validate and process payment
            if (validatePaymentDetails(cardNumberField.getText(), cardNameField.getText(), cardExpiryField.getText(), cardCVVField.getText())) {
                try {
                    // Save payment information
                    writePaymentInfo(cardNumberField.getText(), cardNameField.getText(), cardExpiryField.getText(), cardCVVField.getText());

                    JOptionPane.showMessageDialog(this, "Payment Successful. Ticket Booked.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "WelcomePage");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error processing payment. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Payment Details. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        paymentPanel.add(payButton, gbc);

        mainPanel.add(paymentPanel, "PaymentPage");
    }

    private boolean validatePaymentDetails(String cardNumber, String cardName, String cardExpiry, String cardCVV) {
        return cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}") &&
                cardName.matches(".+") &&
                cardExpiry.matches("\\d{2}/\\d{2}") &&
                cardCVV.matches("\\d{3}");
    }

    private void writePaymentInfo(String cardNumber, String cardName, String cardExpiry, String cardCVV) throws SQLException {
        String query = "INSERT INTO payments (cardNumber, cardName, cardExpiry, cardCVV) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, cardName);
            pstmt.setString(3, cardExpiry);
            pstmt.setString(4, cardCVV);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error writing payment information to database: " + e.getMessage());
        }
    }

    private void updateShowtimes() {
        String selectedMovie = movieList.getSelectedValue();
        List<Showtime> selectedShowtimes = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            if (showtime.getMovieName().equals(selectedMovie)) {
                selectedShowtimes.add(showtime);
            }
        }

        String[] showtimeStrings = selectedShowtimes.stream()
            .map(Showtime::toString)
            .toArray(String[]::new);

        showtimeList.setListData(showtimeStrings);
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        SwingUtilities.invokeLater(MovieBookingSystemGUI::new);
    }
}
