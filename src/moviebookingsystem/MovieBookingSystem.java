/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import moviebookingsystem.Booking;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mardiliza
 */
/**
 * Main class representing the Movie Booking System.
 */
public class MovieBookingSystem {

    /**
     * Main method where the execution of the program starts.
     */
    public static void main(String[] args) {

        // Initialize scanner object for user input
        Scanner scan = new Scanner(System.in);
        String selection = "";

        // Get map of movies
        Map<Integer, String> movieMap = movies();

        // Get map of cinemas
        Map<Integer, String> cinemaMap = getCinemas();

        // Get list of showtimes for movies
        List<Showtime> showtimes = showtimes(movieMap);

        // Display Main/Welcome Banner
        System.out.println("============ CINEMA ============\n");
        System.out.println("            WELCOME!");
        System.out.println("\nPress Enter to view NOW SHOWING");
        System.out.println("=================================");
        scan.nextLine();

        char continueBooking;

        // Display movies currently showing and available for booking
        for (Map.Entry<Integer, String> entry : movieMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }

        // Ask user if they want to proceed with booking
        System.out.println("\nProceed to book? (Y/N)\n");

        // Flag for continuing loop
        boolean continueLoop = true;

        while (continueLoop) {
            String input = scan.nextLine().toUpperCase();

            if (!input.isEmpty()) {
                continueBooking = input.charAt(0);

                if (continueBooking == 'Y' || continueBooking == 'y') {
                    selection = "Book";
                } else if (continueBooking == 'N' || continueBooking == 'n') {
                    selection = "Exit";
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N.'");
                }

                switch (selection) {
                    case "Book":

                        // Flag for proceeding with booking
                        boolean proceedWithBooking = false;

                        while (continueLoop) {
                            System.out.println("\nEnter the corresponding movie number to view the Showtimes\n");

                            int selectedNumber;
                            String selectedMovie = null;

                            if (scan.hasNextInt()) {
                                selectedNumber = scan.nextInt();
                                scan.nextLine();

                                selectedMovie = movieMap.get(selectedNumber);
                                if (selectedMovie != null) {
                                    System.out.println("\n========================  " + selectedMovie + "  SHOWTIMES ========================\n");
                                    showShowtimes(createShowtimes(selectedMovie));
                                } else {
                                    System.out.println("Invalid movie number. Please try again.");
                                    continue;
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a movie number.\n");
                                scan.nextLine();
                                continue;
                            }

                            // View movie information
                            System.out.println("\nMOVIE INFO\n");
                            viewMovieInformation(selectedMovie);

                            // Ask if user wants to view showtimes for another movie
                            System.out.println("\nView showtimes for another movie? (Y/N)\n");
                            char choice = scan.next().charAt(0);
                            scan.nextLine();

                            if (choice == 'N' || choice == 'n') {
                                //Ask if user want to continue with booking movie tickets
                                System.out.println("\nProceed with booking ticket(s)? (Y/N)\n");
                                char bookingChoice = scan.next().charAt(0);
                                scan.nextLine();

                                if (bookingChoice == 'Y' || bookingChoice == 'y') {
                                    while (continueLoop) {
                                        //Ask user to select preferred cinema if they want to proceed with booking tickets
                                        System.out.println("\nSelect your preferred Cinema\n");

                                        // Display different cinemas for user to select
                                        for (Map.Entry<Integer, String> entry : cinemaMap.entrySet()) {
                                            System.out.println(entry.getKey() + ". " + entry.getValue());
                                        }

                                        System.out.println("");

                                        if (scan.hasNextInt()) {
                                            selectedNumber = scan.nextInt();
                                            scan.nextLine();

                                            //Display user's cinema selection
                                            String selectedCinema = cinemaMap.get(selectedNumber);
                                            if (selectedCinema != null) {
                                                System.out.println("\nYou selected the " + selectedCinema + " cinema\n");
                                            } else {
                                                System.out.println("Invalid cinema number. Please try again.");
                                                continue;
                                            }
                                        } else {
                                            System.out.println("Invalid input. Please enter a cinema number.");
                                            scan.nextLine();
                                            continue;
                                        }

                                        // Ask user to select date and time
                                        while (true) {
                                            System.out.println("Select the date (DD/MM): \n");
                                            String date = scan.nextLine();

                                            if (date.matches("\\d{2}/\\d{2}")) {
                                                break;
                                            } else {
                                                System.out.println("\nInvalid date. Please check format and try again.\n");
                                            }
                                        }

                                        // Validate time input
                                        while (true) {
                                            System.out.println("\nSelect the time (HH:MMam/pm): \n");
                                            String time = scan.next();

                                            if (time.matches("\\d{2}:\\d{2}[ap]m")) {
                                                break;
                                            } else {
                                                System.out.println("\nInvalid time. Please check format and try again.\n");
                                            }
                                        }

                                        // Initialize seat arrangement
                                        int rows = 8;
                                        int columns = 10;
                                        Seat seat = new Seat(rows, columns);

                                        System.out.println("");

                                        // Display seating arrangement
                                        seat.displaySeatingArrangement();

                                        // Ask user to choose seat
                                        seat.chooseSeat();
                                        
                                        System.out.println("\nBooked seat in red: \n");
                                        seat.displaySeatingArrangement();
                                        
                                        // Proceed to payment
                                        System.out.println("\nPress Enter to proceed to Payment");
                                        scan.nextLine();
                                        scan.nextLine();

                                        // Get payment details
                                        String cardNumber;

                                        while (true) {
                                            System.out.println("\nCard number: xxxx xxxx xxxx xxxx");
                                            cardNumber = scan.nextLine();

                                            if (cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                                                break;
                                            } else {
                                                System.out.println("Invalid card number format. Please try again.");
                                            }
                                        }

                                        String cardName;

                                        System.out.println("\nName on card:");
                                        cardName = scan.nextLine();

                                        String cardExpiry;

                                        while (true) {
                                            System.out.println("\nExpiry Date (MM/YY):");
                                            cardExpiry = scan.nextLine();

                                            if (cardExpiry.matches("\\d{2}/\\d{2}")) {
                                                break;
                                            } else {
                                                System.out.println("\nInvalid expiry date. Please try again.");
                                            }
                                        }

                                        String cardCVV;

                                        while (true) {
                                            System.out.println("\nCVV (3 digits):");
                                            cardCVV = scan.nextLine();

                                            if (cardCVV.matches("\\d{3}")) {
                                                break;
                                            } else {
                                                System.out.println("\nInvalid CVV. Please try again.");
                                            }
                                        }

                                        //Confirm Payment
                                        System.out.println("\nEnter to submit payment");
                                        scan.nextLine();

                                        // Process payment
                                        System.out.println("\nProcessing Payment... Please do not exit the screen");

                                        try {
                                            Thread.sleep(2500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        // Display payment success message
                                        System.out.println("\nPayment Successful. Ticket Booked");

                                        boolean savePaymentInfo = false;
                                        boolean validInput = false;

                                        while (!validInput) {
                                            System.out.println("\nSave payment info for future transactions? (Y/N):");
                                            String saveOption = scan.nextLine();

                                            if (saveOption.equalsIgnoreCase("Y")) {
                                                savePaymentInfo = true;
                                                validInput = true; // Exit the loop since the user wants to save payment info
                                            } else if (saveOption.equalsIgnoreCase("N")) {
                                                validInput = true; // Exit the loop since the user doesn't want to save payment info
                                            } else {
                                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                                            }
                                        }

                                        if (savePaymentInfo) {
                                            writePaymentInfo(cardNumber, cardName, cardExpiry, cardCVV);
                                        }
                                        // Fetch booked tickets for the user
                                        String username = "";

                                        System.out.print("\nEnter your username to view booked tickets:");
                                        username = scan.nextLine();

                                        while (username.isEmpty()) {
                                            System.out.println("Username cannot be empty. Please try again.");
                                            System.out.print("\nEnter your username to view booked tickets:");
                                            username = scan.nextLine();
                                        }

                                        Booking booking = new Booking(username);

                                        try {
                                            Thread.sleep(1500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        // Display booked tickets
                                        booking.checkBookings();

                                        System.out.println("\nPlease enter your details to save your booking");

                                        System.out.println("\nUsername: ");
                                        String user = scan.nextLine();

                                        System.out.println("\nPhone Number: ");
                                        String phoneNo = scan.nextLine();

                                        System.out.println("\nEmail Address: ");
                                        String email = scan.nextLine();

                                        Customer customer = new Customer(user, phoneNo, email);
                                        customer.registerNewUser();

                                        System.out.println("\nBooking saved under " + user);

                                        continueLoop = false;
                                    }
                                }
                            }
                        }

                    case "Exit":
                        // Exit the program
                        System.out.println("\nExiting Program...\n");
                        continueLoop = false;
                        break;

                    default:
                        break;
                }
            }
        }
    }

// Method to initialize map of movies
    public static Map<Integer, String> movies() {
        Map<Integer, String> movies = new HashMap<>();
        movies.put(1, "Kung Fu Panda 4");
        movies.put(2, "Dune: Part 2");
        movies.put(3, "Godzilla x Kong: The New Empire");
        movies.put(4, "Exhuma");
        movies.put(5, "Monkey Man");
        movies.put(6, "Ghostbusters: Frozen Empire");
        movies.put(7, "Wonka");
        movies.put(8, "Immaculate");

        return movies;
    }

    // Method to initialize map of cinemas
    public static Map<Integer, String> getCinemas() {
        Map<Integer, String> cinemas = new HashMap<>();
        cinemas.put(1, "Queen Street");
        cinemas.put(2, "Newmarket");
        cinemas.put(3, "Sylvia Park");
        cinemas.put(4, "Albany");
        cinemas.put(5, "Manukau");
        cinemas.put(6, "Westgate");

        return cinemas;
    }

    // Method to get list of cinemas
    public static List<Cinema> getCinemas(Map<Integer, String> cinemaMap) {
        List<Cinema> cinemas = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : cinemaMap.entrySet()) {
            String cinemaLocation = entry.getValue();
            Cinema cinema = new Cinema(cinemaLocation);
            cinemas.add(cinema);
        }

        return cinemas;
    }

    // Method to display now showing movies
    public static void showNowShowing(List<String> movies) {
        System.out.println("          NOW SHOWING\n");
        for (String movie : movies) {
            System.out.println(movie);
        }
    }

    // Method to get list of showtimes for movies
    public static List<Showtime> showtimes(Map<Integer, String> movieMap) {
        List<Showtime> showtimes = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : movieMap.entrySet()) {
            List<Showtime> movieShowtimes = createShowtimes(entry.getValue());
            showtimes.addAll(movieShowtimes);
        }

        return showtimes;
    }

    // Method to display showtimes
    public static void showShowtimes(List<Showtime> showtimes) {
        for (Showtime showtime : showtimes) {
            System.out.println(showtime);
        }
    }

    // Method to get cinema type
    public static String getCinemaType() {
        String[] cinemaTypes = {"VMAX", "Original", "Boutique"};
        Random random = new Random();
        return cinemaTypes[random.nextInt(cinemaTypes.length)];
    }

    // Method to create showtimes for selected movie
    public static List<Showtime> createShowtimes(String selectedMovie) {
        List<Showtime> showtimes = new ArrayList<>();
        Random random = new Random();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            List<LocalTime> times = new ArrayList<>();
            // Generate times between 10 AM to 11:45 PM
            for (int i = 0; i < 3; i++) {
                int hour = random.nextInt(14) + 10; // Hours from 10 AM to 11 PM
                int minute = random.nextInt(4) * 15; // Minutes: 0, 15, 30, 45
                times.add(LocalTime.of(hour, minute));
            }
            Collections.sort(times);

            for (LocalTime time : times) {
                // Exclude times between 12 AM to 10 AM
                if (!(time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(10, 0)))) {
                    String cinemaType = getCinemaType();
                    showtimes.add(new Showtime(selectedMovie, date.format(dateFormatter), time.format(DateTimeFormatter.ofPattern("hh:mm a")), cinemaType));
                }
            }
        }
        return showtimes;
    }

    // Method to view movie information
    public static void viewMovieInformation(String selectedMovie) {
        Movie movie = findMovie(selectedMovie);
        if (movie != null) {
            System.out.println("" + movie.getMovieName());
            System.out.println("Genre: " + movie.getGenre());
            System.out.println("Running Time: " + movie.getRunningTime() + " minutes");
            System.out.println("Rating: " + movie.getRating());
        } else {
            System.out.println("Movie information not available.");
        }
    }

    // Method to find movie by name
    public static Movie findMovie(String selectedMovie) {
        List<Movie> movies = getMoviesList();
        for (Movie movie : movies) {
            if (movie.getMovieName().equalsIgnoreCase(selectedMovie)) {
                return movie;
            }
        }
        return null;
    }

    // Method to get list of movies
    public static List<Movie> getMoviesList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Kung Fu Panda 4", "PG", "Animation, Action, Adventure", 94));
        movies.add(new Movie("Dune: Part 2", "M", "Sci-Fi, Adventure", 166));
        movies.add(new Movie("Godzilla x Kong: The New Empire", "M", "Action, Sci-Fi, Thriller", 115));
        movies.add(new Movie("Exhuma", "M", "Horror, Thriller", 134));
        movies.add(new Movie("Monkey Man", "PG-13", "Action, Adventure, Drama", 121));
        movies.add(new Movie("Ghostbusters: Frozen Empire", "PG", "Comedy, Fantasy, Sci-Fi", 115));
        movies.add(new Movie("Wonka", "PG", "Adventure, Comedy, Family", 116));
        movies.add(new Movie("Immaculate", "R16", "Horror, Mystery, Thriller", 89));
        return movies;
    }

    // Method to write payment information to payment_info.txt
    private static void writePaymentInfo(String cardNumber, String cardName, String cardExpiry, String cardCVV) {
        String filePath = "./Resources/payment_info.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            writer.write("Card Number: " + cardNumber);
            writer.newLine();
            writer.write("Name on Card: " + cardName);
            writer.newLine();
            writer.write("Expiry Date: " + cardExpiry);
            writer.newLine();
            writer.write("CVV: " + cardCVV);
            writer.newLine();
            writer.write("--------------------------------");
            writer.newLine(); // Add an extra newline for separation between payments

            writer.close();

        } catch (IOException e) {
            System.err.println("Error writing payment information to file: " + e.getMessage());
        }
    }
}
