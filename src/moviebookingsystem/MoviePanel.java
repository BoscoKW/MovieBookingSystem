/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MoviePanel extends JPanel {
    
    private JComboBox<String> movieComboBox;
    private JLabel movieDetailsLabel;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private Database database;
    private Connection conn;
    private Statement statement;
    
    public MoviePanel() {
        database = new Database();
        conn = database.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static final String[][] movieData = {
            {"Furiosa: A Mad Max Saga", "Action/Adventure", "120 minutes", "PG-13",
                    "In a post-apocalyptic wasteland, Furiosa, a hardened warrior, "
                            + "joins forces with Max, a drifter, to take on a ruthless warlord and his army."},
            {"The Garfield Movie", "Animation/Comedy", "90 minutes", "PG",
                    "Garfield, the lazy, lasagna-loving cat, must save his owner Jon "
                            + "from an evil television celebrity who kidnaps him."},
            {"Kingdom of the Planet of the Apes", "Sci-Fi/Action", "135 minutes", "PG-13",
                    "After a virus wipes out most of humanity, Caesar, the leader of "
                            + "the genetically evolved apes, struggles to maintain peace "
                            + "with the remaining humans."},
            {"IF", "Drama/Thriller", "110 minutes", "R",
                    "In a world where reality is uncertain, a man named IF navigates "
                            + "through a series of mind-bending challenges to find the truth."},
            {"Freud's Last Session", "Drama", "100 minutes", "PG",
                    "In a fictional meeting, renowned psychoanalyst Sigmund Freud "
                            + "engages in a philosophical debate with the author C.S. Lewis "
                            + "about love, God, and the meaning of life."},
            {"Unsung Hero", "Biography/Drama", "115 minutes", "PG",
                    "Based on a true story, Unsung Hero follows the journey of a "
                            + "dedicated teacher who transforms the lives of his students "
                            + "despite facing numerous challenges."},
            {"The Fall Guy", "Action/Thriller", "105 minutes", "R",
                    "A former stuntman turned bounty hunter finds himself caught "
                            + "in a deadly game of cat and mouse when he becomes the target "
                            + "of a ruthless criminal organization."},
            {"Challengers", "Sports/Drama", "125 minutes", "PG-13",
                    "In this inspiring sports drama, a group of underdog athletes "
                            + "band together to overcome personal obstacles and compete "
                            + "for glory in a high-stakes tournament."}
    };

    public MoviePanel(CardLayout cardLayout, JPanel cardLayoutPanel) {
        this.cardLayout = cardLayout;
        this.cardLayoutPanel = cardLayoutPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        // Panel to hold movie information and buttons
        JPanel movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel, BoxLayout.Y_AXIS));
        movieDetailsLabel = new JLabel();
        movieDetailsPanel.add(movieDetailsLabel);
        movieDetailsPanel.add(Box.createVerticalGlue()); // Add spacing between components

        // Add movie details panel to the main panel
        add(movieDetailsPanel, BorderLayout.CENTER);

        // Populate movie names in the combo box
        String[] movieNames = new String[movieData.length];
        for (int i = 0; i < movieData.length; i++) {
            movieNames[i] = movieData[i][0];
        }
        movieComboBox = new JComboBox<>(movieNames);
        movieComboBox.setPreferredSize(new Dimension(200, 30)); // Adjust width and height as needed

        // Add combo box to the main panel
        add(movieComboBox, BorderLayout.NORTH);

        // Add action listener to show movie details when an item is selected
        movieComboBox.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selectedMovie = (String) source.getSelectedItem();
            if (selectedMovie != null) {
                for (String[] movie : movieData) {
                    if (movie[0].equals(selectedMovie)) {
                        showMovieInformation(movie);
                        break;
                    }
                }
            }
        });
    }

    private void showMovieInformation(String[] movie) {
        StringBuilder movieDetails = new StringBuilder();
        movieDetails.append("<html>");
        movieDetails.append("<b>Genre:</b> ").append(movie[1]).append("<br>");
        movieDetails.append("<b>Running Time:</b> ").append(movie[2]).append("<br>");
        movieDetails.append("<b>Rating:</b> ").append(movie[3]).append("<br>");
        movieDetails.append("<b>Synopsis:</b> ").append(movie[4]);
        movieDetails.append("</html>");
        movieDetailsLabel.setText(movieDetails.toString());
    }

    // Inside MoviePanel class
    public String getSelectedMovie() {
        return (String) movieComboBox.getSelectedItem();
    }

    public void addMoviesToDatabase() {
        Database database = Database.getInstance();
        for (String[] movie : movieData) {
            String title = movie[0];
            String genre = movie[1];
            String duration = movie[2];
            String rating = movie[3];
            String synopsis = movie[4];

            String sql = "INSERT INTO movies (title, genre, duration, rating, synopsis) VALUES (" +
                    "'" + title + "', " +
                    "'" + genre + "', " +
                    "'" + duration + "', " +
                    "'" + rating + "', " +
                    "'" + synopsis + "'" +
                    ")";

            database.updateDB(sql);
        }
    }
}
