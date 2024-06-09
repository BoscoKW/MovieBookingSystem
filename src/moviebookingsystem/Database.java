package moviebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

    private static final String URL = "jdbc:derby:MovieBookingDB;create=true;";
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc"; 

    private static Database instance;
    private Connection conn;

    public Database() {
        establishConnection();
        createTables();
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

    private void createTables() {
        String createTicketsTable = "CREATE TABLE tickets (" +
                "name VARCHAR(255)," +
                "cinema VARCHAR(255)," +
                "movie VARCHAR(255)," +
                "date VARCHAR(255)," +
                "time VARCHAR(255)," +
                "seats VARCHAR(255)" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTicketsTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("X0Y32")) {
                System.out.println("Table already exists.");
            } else {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex.printStackTrace();
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

    public void storeTicketInfo(String name, String cinema, String movie, String date, String time, String seats) {
        String sql = String.format("INSERT INTO tickets (name, cinema, movie, date, time, seats) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                name, cinema, movie, date, time, seats);
        updateDB(sql);
    }

    public String getTicketInfoByName(String name) {
        String sql = "SELECT * FROM tickets WHERE name='" + name + "'";
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return String.format(
                        "Ticket Information:\n\n" +
                        "Name: %s\n" +
                        "Cinema: %s\n" +
                        "Movie: %s\n" +
                        "Date: %s\n" +
                        "Time: %s\n" +
                        "Seats: %s\n",
                        rs.getString("name"),
                        rs.getString("cinema"),
                        rs.getString("movie"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("seats")
                );
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for detailed error information
        }
        return null;
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
