package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private ShowtimePanel showtimePanel;
    private SeatPanel seatPanel;
    private PaymentPanel paymentPanel;
    private TicketPanel ticketPanel;
    private WelcomePanel welcomePanel;
    private RetrieveTicketPanel retrieveTicketPanel;
    private CustomerPanel customerPanel;
    private Database database;

    public String selectedCinema;
    public String selectedMovie;
    public String selectedDate;
    public String selectedTime;
    public String selectedSeats = "";

    public MainGUI() {
        setTitle("Movie Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establish database connection
        System.out.println("Establishing database connection...");
        database = Database.getInstance();

        // Add shutdown hook to close the database properly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            database.shutdown();
        }));

        // Create main panel with CardLayout
        System.out.println("Initializing CardLayout...");
        cardLayout = new CardLayout();
        cardLayoutPanel = new JPanel(cardLayout);
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(cardLayoutPanel, gbc);

        // Create and add panels to the card layout
        welcomePanel = new WelcomePanel(this);
        cardLayoutPanel.add(welcomePanel, "WelcomePage");

        JPanel cinemaSelectionPanel = createCinemaSelectionPanel();
        cardLayoutPanel.add(cinemaSelectionPanel, "CinemaSelectionPage");

        JPanel nowShowingPanel = createNowShowingPanel();
        cardLayoutPanel.add(nowShowingPanel, "NowShowingPage");

        showtimePanel = new ShowtimePanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(showtimePanel, "ShowtimePage");

        seatPanel = new SeatPanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(seatPanel, "SeatsPage");

        paymentPanel = new PaymentPanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(paymentPanel, "PaymentPage");

        ticketPanel = new TicketPanel(this);
        cardLayoutPanel.add(ticketPanel, "TicketPage");
        
        retrieveTicketPanel = new RetrieveTicketPanel(this);
        cardLayoutPanel.add(retrieveTicketPanel, "RetrieveTicketPage");

        // Add CustomerPanel
        customerPanel = new CustomerPanel();
        cardLayoutPanel.add(customerPanel, "CustomerPanel");

        // Show welcome page initially
        cardLayout.show(cardLayoutPanel, "WelcomePage");

        // Set default size for the frame
        setSize(800, 600);

        // Add main panel to frame
        add(mainPanel);
        System.out.println("Initialization complete.");
    }

    private JPanel createCinemaSelectionPanel() {
        JPanel cinemaSelectionPanel = new JPanel(new BorderLayout());
        JLabel cinemasLabel = new JLabel("CINEMAS", SwingConstants.CENTER);
        cinemasLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cinemaSelectionPanel.add(cinemasLabel, BorderLayout.NORTH);

        JPanel selectCinemaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        List<String> cinemas = getCinemas();
        DefaultComboBoxModel<String> cinemaModel = new DefaultComboBoxModel<>();
        cinemaModel.addElement("Select Cinema");
        for (String cinema : cinemas) {
            cinemaModel.addElement(cinema);
        }
        JComboBox<String> cinemaComboBox = new JComboBox<>(cinemaModel);
        cinemaComboBox.setPreferredSize(new Dimension(300, 40));

        selectCinemaPanel.add(cinemaComboBox);

        JButton setCinemaButton = new JButton("Set Cinema");
        setCinemaButton.setPreferredSize(new Dimension(150, 40));
        setCinemaButton.addActionListener(e -> {
            selectedCinema = (String) cinemaComboBox.getSelectedItem();
            if (!selectedCinema.equals("Select Cinema")) {
                cardLayout.show(cardLayoutPanel, "NowShowingPage");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a cinema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        selectCinemaPanel.add(setCinemaButton);

        cinemaSelectionPanel.add(selectCinemaPanel, BorderLayout.CENTER);
        cinemaSelectionPanel.setPreferredSize(new Dimension(400, 200)); // Adjust size to fit content

        return cinemaSelectionPanel;
    }

    private JPanel createNowShowingPanel() {
        JPanel nowShowingPanel = new JPanel(new BorderLayout());
        nowShowingPanel.add(new JLabel("NOW SHOWING", SwingConstants.CENTER), BorderLayout.NORTH);
        MoviePanel moviePanel = new MoviePanel(cardLayout, cardLayoutPanel);
        nowShowingPanel.add(moviePanel, BorderLayout.CENTER);

        // Create button panel for back and set movie buttons
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardLayoutPanel, "CinemaSelectionPage"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        JButton setMovieButton = new JButton("Select Movie");
        setMovieButton.setPreferredSize(new Dimension(150, 40));
        setMovieButton.addActionListener(e -> {
            selectedMovie = moviePanel.getSelectedMovie();
            if (selectedMovie != null) {
                showtimePanel.setSelectedMovie(selectedMovie);
                cardLayout.show(cardLayoutPanel, "ShowtimePage");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a movie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(setMovieButton);
        nowShowingPanel.add(buttonPanel, BorderLayout.SOUTH);

        nowShowingPanel.setPreferredSize(new Dimension(600, 300)); // Adjust size to fit content

        return nowShowingPanel;
    }

    // Method to get the list of cinemas (updated to use specified names)
    private List<String> getCinemas() {
        return Arrays.asList("Albany", "Manukau", "Mission Bay", "Newmarket",
                "Queen Street", "St Lukes", "Sylvia Park", "Westgate");
    }

    public Database getDatabase() {
        return database;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }

    public void showWelcomePage() {
        cardLayout.show(cardLayoutPanel, "WelcomePage");
    }

    public void showBookingPage() {
        cardLayout.show(cardLayoutPanel, "CinemaSelectionPage");
    }

    public void showCustomerPanel() {
        cardLayout.show(cardLayoutPanel, "CustomerPanel");
    }

    public void updateTicketPanel() {
        ticketPanel.updateTicketInfo(selectedCinema, selectedMovie, selectedDate, selectedTime, "", selectedSeats);
    }

    public void showTicketPanel() {
        cardLayout.show(cardLayoutPanel, "TicketPage");
    }
    
     public void showRetrieveTicketPage() {
        cardLayout.show(cardLayoutPanel, "RetrieveTicketPage");
    }
}
