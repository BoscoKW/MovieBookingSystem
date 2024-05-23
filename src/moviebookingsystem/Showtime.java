/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

/**
 *
 * @author mardiliza
 */

/**
 * Main class representing the Movie Showtime
 */
public class Showtime {

    private String movieName;
    private String date;
    private String time;
    private String cinema;

    public Showtime(String movieName, String date, String time, String cinema) {
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.cinema = cinema;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCinema() {
        return cinema;
    }

    @Override
    public String toString() {
        return "Date: " + date + "            Time: " + time + "            Cinema: " + cinema + "";
    }
}
