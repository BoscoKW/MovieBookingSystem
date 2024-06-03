/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

    public static final String USER_NAME = "movie"; // your DB username
    public static final String PASSWORD = "movie"; // your DB password
    public static final String URL = "jdbc:derby:MovieBookings;create=true"; // url of the DB host

    private static Database instance;
    private static Connection conn;

    private Database() {
        establishConnection();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    public void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println(URL + " connected...");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // CRUD methods for customers and bookings
    public void registerCustomer(String username, String phoneNumber, String emailAddress) {
        String sql = "INSERT INTO customers(username, phoneNumber, emailAddress) VALUES('" + username + "', '" + phoneNumber + "', '" + emailAddress + "')";
        updateDB(sql);
    }

    public ResultSet getBookings(String username) {
        String sql = "SELECT * FROM bookings WHERE username = '" + username + "'";
        return queryDB(sql);
    }

    public void addBooking(String username, String seat) {
        String sql = "INSERT INTO bookings(username, seat) VALUES('" + username + "', '" + seat + "')";
        updateDB(sql);
    }
}
