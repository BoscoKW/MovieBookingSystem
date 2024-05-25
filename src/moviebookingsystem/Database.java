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

    public static final String USER_NAME = "movie"; //your DB username
    public static final String PASSWORD = "movie"; //your DB password
    public static final String URL = "jdbc:derby:MovieBookings;create=true";  //url of the DB host

    public static Connection conn;

    public Database() {
        establishConnection();
    }

    public static void main(String[] args) {
        Database database = new Database();
        System.out.println(database.getConnection());
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void establishConnection() {
        try{
            conn=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println(URL + " connected...");
        }
        catch(SQLException ex){
            System.err.println("SQLException: " +ex.getMessage());
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

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}