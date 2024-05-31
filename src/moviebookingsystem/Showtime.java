package moviebookingsystem;

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
        return "Date: " + date + " Time: " + time + " Cinema: " + cinema;
    }
}
