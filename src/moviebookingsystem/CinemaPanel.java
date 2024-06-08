package moviebookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class CinemaPanel extends JPanel {
    
    private Database database;
    private Connection conn;
    private Statement statement;
    
    public CinemaPanel() {
        
        database = new Database();
        conn = database.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static final String[] cinemaData = {
        "Albany", "Manukau", "Mission Bay", "Newmarket",
        "Queen Street", "St Lukes", "Sylvia Park", "Westgate"
    };

    public static List<String> getCinemas() {
        return Arrays.asList(cinemaData);
    }

    public void addCinemasToDatabase() {
        Database database = Database.getInstance();
        for (String cinemaName : cinemaData) {
            String sql = "INSERT INTO cinemas (name) VALUES ('" + cinemaName + "')";
            database.updateDB(sql);
        }
    }
}
