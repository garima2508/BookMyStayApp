import java.util.*;
public class BookMyStayApp {
    // Centralized inventory
    private Map<String, Integer> inventory;
    // Booking history
    private List<String> bookingHistory;

    // Constructor initializes inventory
    public BookMyStayApp() {
        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();

        // Register room types with availability
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Synchronized booking method
    public synchronized void processBooking(String reservationId, String roomType, String guestName) {
        int availability = inventory.getOrDefault(roomType, 0);

        if (availability > 0) {
            // Update inventory safely
            inventory.put(roomType, availability - 1);

            // Record booking
            String record = "Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room Type: " + roomType;
            bookingHistory.add(record);

            System.out.println("Booking Confirmed: " + record);
        } else {
            System.out.println("Booking Failed for " + guestName +
                    " (Reservation ID: " + reservationId +
                    ") - No " + roomType + " available.");
        }
    }

    // Display final inventory
    public void displayInventory() {
        System.out.println("\n=== Final Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Available: " + entry.getValue());
        }
    }

    // Display booking history
    public void displayBookingHistory() {
        System.out.println("\n=== Booking History ===");
        for (String record : bookingHistory) {
            System.out.println(record);
        }
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 11.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Simulate multiple guests booking concurrently
        Thread guest1 = new Thread(() -> app.processBooking("RES-5001", "Single Room", "Alice"));
        Thread guest2 = new Thread(() -> app.processBooking("RES-5002", "Single Room", "Bob"));
        Thread guest3 = new Thread(() -> app.processBooking("RES-5003", "Double Room", "Charlie"));
        Thread guest4 = new Thread(() -> app.processBooking("RES-5004", "Suite Room", "Diana"));
        Thread guest5 = new Thread(() -> app.processBooking("RES-5005", "Suite Room", "Eve")); // may fail

        // Start threads
        guest1.start();
        guest2.start();
        guest3.start();
        guest4.start();
        guest5.start();

        // Wait for all threads to finish
        try {
            guest1.join();
            guest2.join();
            guest3.join();
            guest4.join();
            guest5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final state
        app.displayBookingHistory();
        app.displayInventory();
    }
}
