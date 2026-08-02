import java.util.*;

public class BookingManager {

    private final Map<UUID, Booking> bookings = new HashMap<>();

    public Booking createBooking(User user, Show show, List<Integer> seats) {
        if (!show.lockSeats(seats)) {
            throw new RuntimeException("Seat unavailable");
        }

        // Simulated payment - can plug in real PaymentManager here
        Payment payment = new Payment(PaymentStatus.SUCCESS);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            show.confirmSeats(seats);
            Booking booking = new Booking(user, show, seats, payment);
            bookings.put(booking.getBookingId(), booking);
            return booking;
        } else {
            show.releaseSeats(seats);
            throw new RuntimeException("Payment failed");
        }
    }

    public Booking getBooking(UUID bookingId) {
        return bookings.get(bookingId);
    }

    public List<Booking> getBookingsForUser(User user) {
        return bookings.values().stream()
            .filter(b -> b.getUser().equals(user))
            .toList();
    }
}
