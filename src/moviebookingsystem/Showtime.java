/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;
/**
 *
 * @author mardiliza
 */
import java.time.LocalTime;

public class Showtime {
    private String movieName;
    private String date;
    private String time;
    private String cinemaType;

    public Showtime(String movieName, String date, String time, String cinemaType) {
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.cinemaType = cinemaType;
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

    public String getCinemaType() {
        return cinemaType;
    }
}
