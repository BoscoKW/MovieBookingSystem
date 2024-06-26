package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class ShowtimePanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private String selectedMovie;
    private MainGUI mainGUI;

    public ShowtimePanel(CardLayout cardLayout, JPanel cardLayoutPanel, MainGUI mainGUI) {
        this.cardLayout = cardLayout;
        this.cardLayoutPanel = cardLayoutPanel;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("SHOWTIMES", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel datesPanel = new JPanel();
        datesPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Flow layout with left alignment

        mainPanel.add(datesPanel, BorderLayout.NORTH);

        JPanel timesPanel = new JPanel();
        timesPanel.setLayout(new BoxLayout(timesPanel, BoxLayout.Y_AXIS));

        mainPanel.add(new JScrollPane(timesPanel), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardLayoutPanel, "NowShowingPage"));
        add(backButton, BorderLayout.SOUTH);
        
        setPreferredSize(new Dimension(600, 400)); // Adjust size to fit content
    }

    public void setSelectedMovie(String selectedMovie) {
        this.selectedMovie = selectedMovie;
        updateShowtimes(selectedMovie);
    }

    private void updateShowtimes(String selectedMovie) {

        List<Showtime> showtimes = createShowtimes(selectedMovie);

        JPanel mainPanel = (JPanel) getComponent(1);
        JPanel datesPanel = (JPanel) mainPanel.getComponent(0);
        JPanel timesPanel = (JPanel) ((JScrollPane) mainPanel.getComponent(1)).getViewport().getView();

        datesPanel.removeAll();
        timesPanel.removeAll();

        if (showtimes.isEmpty()) {
            JLabel noShowtimesLabel = new JLabel("No showtimes available.", SwingConstants.CENTER);
            timesPanel.add(noShowtimesLabel);
        } else {
            Map<String, List<Showtime>> showtimesByDate = groupShowtimesByDate(showtimes);

            // Add date buttons to the dates panel
            for (String date : showtimesByDate.keySet()) {
                JButton dateButton = new JButton(formatDate(date));
                dateButton.addActionListener(e -> {
                    timesPanel.removeAll();
                    List<Showtime> times = showtimesByDate.get(date);
                    for (Showtime showtime : times) {
                        JButton timeButton = new JButton(showtime.getTime() + " " + showtime.getCinemaType());
                        timeButton.setFont(new Font("Arial", Font.PLAIN, 14));
                        timeButton.addActionListener(evt -> navigateToSeatsPage(selectedMovie, showtime.getDate(), showtime.getTime(), showtime.getCinemaType()));
                        timesPanel.add(timeButton);
                    }
                    timesPanel.revalidate();
                    timesPanel.repaint();
                });
                datesPanel.add(dateButton);
            }
        }

        revalidate(); // Refresh panel
        repaint();
    }

    private String formatDate(String date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateObj = LocalDate.parse(date);
        if (dateObj.equals(currentDate)) {
            return "Today";
        } else if (dateObj.equals(currentDate.plusDays(1))) {
            return "Tomorrow";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM");
            return dateObj.format(formatter);
        }
    }

    private Map<String, List<Showtime>> groupShowtimesByDate(List<Showtime> showtimes) {
        Map<String, List<Showtime>> showtimesByDate = new LinkedHashMap<>();
        for (Showtime showtime : showtimes) {
            String date = showtime.getDate();
            showtimesByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(showtime);
        }
        return showtimesByDate;
    }

    private List<Showtime> createShowtimes(String selectedMovie) {
        List<Showtime> showtimes = new ArrayList<>();
        Random random = new Random();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String[] types = {"Standard", "IMAX", "3D", "Boutique"};

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            List<LocalTime> times = new ArrayList<>();
            int numTimes = random.nextInt(5) + 2; // Generate 2 to 6 times
            for (int i = 0; i < numTimes; i++) {
                int hour = random.nextInt(14) + 10; // Hours from 10 AM to 11 PM
                int minute = random.nextInt(4) * 15; // Minutes: 0, 15, 30, 45
                times.add(LocalTime.of(hour, minute));
            }
            Collections.sort(times);

            for (LocalTime time : times) {
                if (!(time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(10, 0)))) {
                    String cinemaType = types[random.nextInt(types.length)]; // Generate cinema type directly
                    showtimes.add(new Showtime(selectedMovie, date.format(dateFormatter), time.format(DateTimeFormatter.ofPattern("hh:mm a")), cinemaType));
                }
            }
        }
        return showtimes;
    }

    // Method to navigate to the seats page
    private void navigateToSeatsPage(String selectedMovie, String date, String time, String cinema) {
        mainGUI.selectedMovie = selectedMovie;
        mainGUI.selectedDate = date;
        mainGUI.selectedTime = time;
        mainGUI.selectedSeats = cinema;
        cardLayout.show(cardLayoutPanel, "SeatsPage");
    }
}
