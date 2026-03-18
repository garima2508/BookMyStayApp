import java.util.*;
public class BookMyStayApp {
    // Custom exception for invalid bookings
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Centralized inventory
    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public BookMyStayApp() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // No suites available
    }

    // Validate booking input
    private void validateBooking(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
    }

    // Process booking request
    private void processBooking(String reservationId, String roomType, String guestName) {
        try {
            // Validate input
            validateBooking(roomType);

            // Update inventory safely
            inventory.put(roomType, inventory.get(roomType) - 1);

            // Confirm reservation
            System.out.println("Reservation Confirmed!");
            System.out.println("Reservation ID: " + reservationId);
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Remaining Availability: " + inventory.get(roomType) + "\n");

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Reservation Failed: " + e.getMessage() + "\n");
        }
    }

    // Display current inventory
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
        System.out.println("Version: 9.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Valid booking
        app.processBooking("RES-3001", "Single Room", "Alice");

        // Invalid room type
        app.processBooking("RES-3002", "Penthouse", "Bob");

        // No availability
        app.processBooking("RES-3003", "Suite Room", "Charlie");

        // Display final inventory
        app.displayInventory();
    }
}
