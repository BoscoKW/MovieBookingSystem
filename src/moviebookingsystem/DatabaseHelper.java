package moviebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:moviebooking.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                                          "username TEXT PRIMARY KEY, " +
                                          "phoneNumber TEXT, " +
                                          "emailAddress TEXT)";
            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                                         "username TEXT, " +
                                         "seat TEXT, " +
                                         "FOREIGN KEY(username) REFERENCES customers(username))";
            String createPaymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
                                         "cardNumber TEXT, " +
                                         "cardName TEXT, " +
                                         "cardExpiry TEXT, " +
                                         "cardCVV TEXT)";

            stmt.execute(createCustomersTable);
            stmt.execute(createBookingsTable);
            stmt.execute(createPaymentsTable);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}