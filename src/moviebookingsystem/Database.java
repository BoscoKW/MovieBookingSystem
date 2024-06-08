package moviebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

    private static final String URL = "jdbc:derby:MovieBookingDB;create=true;"; // URL of the DB host

    private static Database instance;
    private Connection conn;

    public Database() {
        establishConnection();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.conn;
    }

    // Establish connection
    private void establishConnection() {
        if (this.conn == null) {
            try {
                // Load the Derby driver
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                conn = DriverManager.getConnection(URL);
                System.out.println("Connected successfully to " + URL);
            } catch (SQLException ex) {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex.printStackTrace(); // Print stack trace for detailed error information
            } catch (ClassNotFoundException e) {
                System.out.println("Derby Embedded Driver not found. Make sure the derby.jar is included in the classpath.");
                e.printStackTrace();
            }
        }
    }

    public void updateDB(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for detailed error information
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException ex) {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex.printStackTrace(); // Print stack trace for detailed error information
            }
        }
    }

    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if ("XJ015".equals(ex.getSQLState())) {
                System.out.println("Derby shutdown successfully.");
            } else {
                System.out.println("Derby shutdown failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Database dbManager = Database.getInstance();
        System.out.println(dbManager.getConnection());

        // Ensure the database is shutdown properly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dbManager.shutdown();
        }));
    }
}
