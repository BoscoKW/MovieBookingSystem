package moviebookingsystem;

public class Movie {

    private String movieName;
    private String rating;
    private String genre;
    private int runningTime;

    public Movie(String movieName, String rating, String genre, int runningTime) {
        this.movieName = movieName;
        this.rating = rating;
        this.genre = genre;
        this.runningTime = runningTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void viewMovieInformation() {
        System.out.println(movieName);
        System.out.println("Genre: " + genre);
        System.out.println("Running Time: " + runningTime + " minutes");
        System.out.println("Rating: " + rating);
    }
}
