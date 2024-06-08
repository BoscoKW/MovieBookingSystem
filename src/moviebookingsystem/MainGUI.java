package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private JComboBox<String> cinemaComboBox;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private ShowtimePanel showtimePanel;
    private SeatPanel seatPanel;
    private PaymentPanel paymentPanel;
    private TicketPanel ticketPanel;
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

        // Create cinema selection panel
        System.out.println("Creating cinema selection panel...");
        JPanel cinemaSelectionPanel = createCinemaSelectionPanel();
        cardLayoutPanel.add(cinemaSelectionPanel, "CinemaSelectionPage");

        // Create Now Showing panel with the modified MoviePanel
        System.out.println("Creating Now Showing panel...");
        JPanel nowShowingPanel = createNowShowingPanel();
        cardLayoutPanel.add(nowShowingPanel, "NowShowingPage");

        // Create Showtime panel
        System.out.println("Creating Showtime panel...");
        showtimePanel = new ShowtimePanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(showtimePanel, "ShowtimePage");

        // Create Seats panel
        System.out.println("Creating Seats panel...");
        seatPanel = new SeatPanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(seatPanel, "SeatsPage");

        // Create Payment panel
        System.out.println("Creating Payment panel...");
        paymentPanel = new PaymentPanel(cardLayout, cardLayoutPanel, this);
        cardLayoutPanel.add(paymentPanel, "PaymentPage");

        // Create Ticket panel
        System.out.println("Creating Ticket panel...");
        ticketPanel = new TicketPanel();
        cardLayoutPanel.add(ticketPanel, "TicketPage");

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
        cinemaComboBox = new JComboBox<>(cinemaModel);
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
        cinemaSelectionPanel.setPreferredSize(new Dimension(500, 300)); // Adjust size to fit content

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

        nowShowingPanel.setPreferredSize(new Dimension(800, 400)); // Adjust size to fit content

        return nowShowingPanel;
    }

    // Method to get the list of cinemas (updated to use specified names)
    private List<String> getCinemas() {
        return Arrays.asList("Albany", "Manukau", "Mission Bay", "Newmarket",
                             "Queen Street", "St Lukes", "Sylvia Park", "Westgate");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }

    // Method to update the ticket panel with selected details
    public void updateTicketPanel() {
        ticketPanel.updateTicketInfo(selectedCinema, selectedMovie, selectedDate, selectedTime, "", selectedSeats);
    }
}
