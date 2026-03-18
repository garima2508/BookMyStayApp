import java.util.*;
public class BookMyStayApp {
    // Reservation model
    static class Reservation {
        private String reservationId;
        private String roomType;
        private String roomId;
        private String guestName;
        private boolean active;

        public Reservation(String reservationId, String roomType, String roomId, String guestName) {
            this.reservationId = reservationId;
            this.roomType = roomType;
            this.roomId = roomId;
            this.guestName = guestName;
            this.active = true;
        }

        public String getReservationId() { return reservationId; }
        public String getRoomType() { return roomType; }
        public String getRoomId() { return roomId; }
        public String getGuestName() { return guestName; }
        public boolean isActive() { return active; }
        public void cancel() { this.active = false; }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room Type: " + roomType +
                    " | Room ID: " + roomId +
                    " | Status: " + (active ? "Active" : "Cancelled");
        }
    }

    // Centralized inventory
    private Map<String, Integer> inventory;
    // Booking history
    private Map<String, Reservation> bookingHistory;
    // Stack for rollback of released room IDs
    private Stack<String> rollbackStack;

    // Constructor initializes structures
    public BookMyStayApp() {
        inventory = new HashMap<>();
        bookingHistory = new HashMap<>();
        rollbackStack = new Stack<>();

        // Register room types with availability
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    // Confirm booking
    private void confirmBooking(String reservationId, String roomType, String guestName) {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking Failed: No availability for " + roomType + "\n");
            return;
        }

        // Generate unique room ID
        String roomId = UUID.randomUUID().toString();

        // Update inventory
        inventory.put(roomType, inventory.get(roomType) - 1);

        // Add to booking history
        Reservation res = new Reservation(reservationId, roomType, roomId, guestName);
        bookingHistory.put(reservationId, res);

        System.out.println("Booking Confirmed!");
        System.out.println(res + "\n");
    }

    // Cancel booking
    private void cancelBooking(String reservationId) {
        Reservation res = bookingHistory.get(reservationId);

        if (res == null) {
            System.out.println("Cancellation Failed: Reservation not found.\n");
            return;
        }
        if (!res.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.\n");
            return;
        }

        // Mark reservation as cancelled
        res.cancel();

        // Rollback: release room ID
        rollbackStack.push(res.getRoomId());

        // Restore inventory
        inventory.put(res.getRoomType(), inventory.get(res.getRoomType()) + 1);

        System.out.println("Cancellation Successful!");
        System.out.println("Reservation ID: " + res.getReservationId() +
                " | Room Type: " + res.getRoomType() +
                " | Released Room ID: " + rollbackStack.peek() +
                " | Inventory Restored: " + inventory.get(res.getRoomType()) + "\n");
    }

    // Display booking history
    private void displayBookingHistory() {
        System.out.println("=== Booking History ===");
        for (Reservation res : bookingHistory.values()) {
            System.out.println(res);
        }
        System.out.println();
    }

    // Display inventory
    private void displayInventory() {
        System.out.println("=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println();
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 10.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Confirm bookings
        app.confirmBooking("RES-4001", "Single Room", "Alice");
        app.confirmBooking("RES-4002", "Double Room", "Bob");
        app.confirmBooking("RES-4003", "Suite Room", "Charlie"); // fails

        // Cancel booking
        app.cancelBooking("RES-4001");
        app.cancelBooking("RES-4001"); // duplicate cancellation

        // Display final state
        app.displayBookingHistory();
        app.displayInventory();
    }
}
