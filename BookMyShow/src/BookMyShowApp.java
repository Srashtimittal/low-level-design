import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BookMyShowApp {

    private TheatreManager theatreManager;
    private BookingManager bookingManager;

    public static void main(String[] args) {
        BookMyShowApp app = new BookMyShowApp();
        app.initialize();
        app.userFlow();
    }

    private void initialize() {
        theatreManager = new TheatreManager();
        bookingManager = new BookingManager();

        // 1. Create Movies
        Movie baahubali = new Movie("BAAHUBALI");
        Movie avengers = new Movie("AVENGERS");

        // 2. Create Theatres -> Screens -> Seats
        Screen inoxScreen1 = new Screen(1, createSeats());
        Theatre inoxTheatreBangalore = new Theatre(
            "INOX",
            City.BANGALORE,
            List.of(inoxScreen1)
        );

        Screen pvrScreen1 = new Screen(1, createSeats());
        Theatre pvrTheatreDelhi = new Theatre(
            "PVR",
            City.DELHI,
            List.of(pvrScreen1)
        );

        theatreManager.addTheatre(inoxTheatreBangalore);
        theatreManager.addTheatre(pvrTheatreDelhi);

        // 3. Create Shows
        Show inoxMorningShow = new Show(
            baahubali, inoxScreen1, LocalDate.now(), LocalTime.of(8, 0)
        );
        Show inoxAfternoonShow = new Show(
            baahubali, inoxScreen1, LocalDate.now(), LocalTime.of(15, 0)
        );
        Show inoxEveningShow = new Show(
            avengers, inoxScreen1, LocalDate.now(), LocalTime.of(18, 0)
        );
        Show pvrMorningShowTomorrow = new Show(
            baahubali, pvrScreen1, LocalDate.now().plusDays(1), LocalTime.of(9, 0)
        );

        // Attach shows to screens
        inoxScreen1.addShow(inoxMorningShow);
        inoxScreen1.addShow(inoxAfternoonShow);
        inoxScreen1.addShow(inoxEveningShow);
        pvrScreen1.addShow(pvrMorningShowTomorrow);
    }

    private void userFlow() {
        User user = new User("U1", "Srashti");
        System.out.println("User logged in: " + user.getName());

        // 1. Select city
        City selectedCity = City.BANGALORE;
        System.out.println("Selected City: " + selectedCity);

        // 2. Show movies available in city for date
        LocalDate selectedDate = LocalDate.now();
        System.out.println("Selected Date: " + selectedDate);

        Set<Movie> movies = theatreManager.getMovies(selectedCity, selectedDate);
        System.out.println("Movies available:");
        movies.forEach(m -> System.out.println("  - " + m.getName()));

        // 3. Select movie
        Movie selectedMovie = movies.iterator().next();
        System.out.println("Selected Movie: " + selectedMovie.getName());

        // 4. Show theatres for movie + city + date
        List<Theatre> theatres = theatreManager.getTheatres(selectedCity, selectedMovie, selectedDate);
        System.out.println("Theatres available:");
        theatres.forEach(t -> System.out.println("  - " + t.getName()));

        // 5. Select theatre
        Theatre selectedTheatre = theatres.get(0);
        System.out.println("Selected Theatre: " + selectedTheatre.getName());

        // 6. Show running shows
        List<Show> shows = theatreManager.getShows(selectedMovie, selectedDate, selectedTheatre);
        System.out.println("Shows available:");
        shows.forEach(s -> System.out.println("  - " + s.getStartTime()));

        // 7. Select show
        Show selectedShow = shows.get(0);
        System.out.println("Selected Show Time: " + selectedShow.getStartTime());

        // 8. Select seats
        List<Integer> selectedSeats = List.of(1, 2, 3);
        System.out.println("Selected Seats: " + selectedSeats);

        // 9. Book
        Booking booking = bookingManager.createBooking(user, selectedShow, selectedSeats);
        System.out.println("BOOKING SUCCESSFUL");
        System.out.println("Booking ID: " + booking.getBookingId());
    }

    private List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            seats.add(new Seat(i, SeatCategory.SILVER));
        }
        return seats;
    }
}
