package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private JComboBox<String> cinemaComboBox;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private ShowtimePanel showtimePanel;
    private SeatPanel seatPanel;
    private PaymentPanel paymentPanel;
    private TicketPanel ticketPanel;

    public MainGUI() {
        setTitle("Movie Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        cardLayoutPanel = new JPanel(cardLayout);
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(cardLayoutPanel, gbc);

        // Create cinema selection panel
        JPanel cinemaSelectionPanel = createCinemaSelectionPanel();
        cardLayoutPanel.add(cinemaSelectionPanel, "CinemaSelectionPage");

        // Create Now Showing panel with the modified MoviePanel
        JPanel nowShowingPanel = createNowShowingPanel();
        cardLayoutPanel.add(nowShowingPanel, "NowShowingPage");

        // Create Showtime panel
        showtimePanel = new ShowtimePanel(cardLayout, cardLayoutPanel);
        cardLayoutPanel.add(showtimePanel, "ShowtimePage");

        // Create Seats panel
        seatPanel = new SeatPanel(cardLayout, cardLayoutPanel);
        cardLayoutPanel.add(seatPanel, "SeatsPage"); // Add SeatsPanel to cardLayoutPanel

        // Create Payment panel
        paymentPanel = new PaymentPanel(cardLayout, cardLayoutPanel); // Pass cardLayout and cardLayoutPanel to PaymentPanel
        cardLayoutPanel.add(paymentPanel, "PaymentPage");

        // Create Ticket panel
        ticketPanel = new TicketPanel("", "", "", "", "", "");
        cardLayoutPanel.add(ticketPanel, "TicketPage");

        // Add main panel to frame
        add(mainPanel);
    }

    private JPanel createCinemaSelectionPanel() {
        JPanel cinemaSelectionPanel = new JPanel(new BorderLayout());
        JLabel cinemasLabel = new JLabel("CINEMAS", SwingConstants.CENTER);
        cinemaSelectionPanel.add(cinemasLabel, BorderLayout.NORTH);

        JPanel selectCinemaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Map<Integer, String> cinemas = CinemaPanel.getCinemas();
        String[] cinemaNames = new String[cinemas.size() + 1];
        cinemaNames[0] = "Select Cinema";
        int index = 1;
        for (String cinemaName : cinemas.values()) {
            cinemaNames[index++] = cinemaName;
        }
        cinemaComboBox = new JComboBox<>(cinemaNames);
        cinemaComboBox.setPreferredSize(new Dimension(300, 40));
        selectCinemaPanel.add(cinemaComboBox);

        JButton setCinemaButton = new JButton("Set Cinema");
        setCinemaButton.setPreferredSize(new Dimension(150, 40));
        setCinemaButton.addActionListener(e -> {
            String selectedCinema = (String) cinemaComboBox.getSelectedItem();
            if (!selectedCinema.equals("Select Cinema")) {
                cardLayout.show(cardLayoutPanel, "NowShowingPage");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a cinema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        selectCinemaPanel.add(setCinemaButton);

        cinemaSelectionPanel.add(selectCinemaPanel, BorderLayout.CENTER);
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

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(backButton, BorderLayout.WEST);

        JButton setMovieButton = new JButton("Select Movie");
        setMovieButton.setPreferredSize(new Dimension(150, 40));
        setMovieButton.addActionListener(e -> {
            String selectedMovie = (String) moviePanel.getSelectedMovie();
            if (selectedMovie != null) {
                // Set the selected movie in the ShowtimePanel
                showtimePanel.setSelectedMovie(selectedMovie);

                // Proceed to the showtime page
                cardLayout.show(cardLayoutPanel, "ShowtimePage");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a movie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(setMovieButton, BorderLayout.CENTER);

        nowShowingPanel.add(buttonPanel, BorderLayout.SOUTH);

        return nowShowingPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
