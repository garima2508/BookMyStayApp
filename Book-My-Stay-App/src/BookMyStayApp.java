import java.util.*;
public class BookMyStayApp {
    // Reservation model
    static class Reservation {
        private String reservationId;
        private String roomType;
        private String guestName;

        public Reservation(String reservationId, String roomType, String guestName) {
            this.reservationId = reservationId;
            this.roomType = roomType;
            this.guestName = guestName;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room Type: " + roomType;
        }
    }

    // Booking history stored in insertion order
    private List<Reservation> bookingHistory;

    // Constructor initializes booking history
    public BookMyStayApp() {
        bookingHistory = new ArrayList<>();
    }

    // Add confirmed reservation to history
    private void addReservation(Reservation reservation) {
        bookingHistory.add(reservation);
    }

    // Display all reservations in history
    private void displayBookingHistory() {
        System.out.println("=== Booking History ===");
        if (bookingHistory.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation res : bookingHistory) {
                System.out.println(res);
            }
        }
        System.out.println();
    }

    // Generate a simple report: count by room type
    private void generateReport() {
        System.out.println("=== Booking Report ===");
        Map<String, Integer> report = new HashMap<>();

        for (Reservation res : bookingHistory) {
            report.put(res.getRoomType(), report.getOrDefault(res.getRoomType(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Total Bookings: " + entry.getValue());
        }
        System.out.println();
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 8.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Simulate confirmed reservations
        app.addReservation(new Reservation("RES-2001", "Single Room", "Alice"));
        app.addReservation(new Reservation("RES-2002", "Double Room", "Bob"));
        app.addReservation(new Reservation("RES-2003", "Single Room", "Charlie"));

        // Admin views booking history
        app.displayBookingHistory();

        // Admin generates report
        app.generateReport();
    }
}
