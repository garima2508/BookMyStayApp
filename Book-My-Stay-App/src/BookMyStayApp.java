/**
 * BookMyStayApp.java
 *
 * This class demonstrates data persistence and recovery for the Hotel Booking
 * Management System. Booking history and inventory state are serialized to a file
 * and restored during system startup.
 *
 * @author YourName
 * @version 12.1
 */

import java.io.*;
import java.util.*;

public class BookMyStayApp implements Serializable {

    private static final long serialVersionUID = 1L;

    // Reservation model
    static class Reservation implements Serializable {
        private static final long serialVersionUID = 1L;
        private String reservationId;
        private String roomType;
        private String guestName;

        public Reservation(String reservationId, String roomType, String guestName) {
            this.reservationId = reservationId;
            this.roomType = roomType;
            this.guestName = guestName;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room Type: " + roomType;
        }
    }

    // Booking history and inventory
    private List<Reservation> bookingHistory;
    private Map<String, Integer> inventory;

    // File for persistence
    private static final String DATA_FILE = "bookingData.ser";

    // Constructor initializes structures
    public BookMyStayApp() {
        bookingHistory = new ArrayList<>();
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    // Confirm booking
    public void confirmBooking(String reservationId, String roomType, String guestName) {
        int availability = inventory.getOrDefault(roomType, 0);
        if (availability > 0) {
            inventory.put(roomType, availability - 1);
            Reservation res = new Reservation(reservationId, roomType, guestName);
            bookingHistory.add(res);
            System.out.println("Booking Confirmed: " + res);
        } else {
            System.out.println("Booking Failed: No " + roomType + " available.");
        }
    }

    // Display booking history
    public void displayBookingHistory() {
        System.out.println("\n=== Booking History ===");
        if (bookingHistory.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation res : bookingHistory) {
                System.out.println(res);
            }
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Available: " + entry.getValue());
        }
    }

    // Save state to file
    public void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static BookMyStayApp loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            System.out.println("System state loaded successfully.\n");
            return (BookMyStayApp) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.\n");
            return new BookMyStayApp();
        }
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 12.1\n");

        // Load previous state if available
        BookMyStayApp app = BookMyStayApp.loadState();

        // Simulate bookings
        app.confirmBooking("RES-6001", "Single Room", "Alice");
        app.confirmBooking("RES-6002", "Double Room", "Bob");
        app.confirmBooking("RES-6003", "Suite Room", "Charlie"); // fails

        // Display state
        app.displayBookingHistory();
        app.displayInventory();

        // Save state before shutdown
        app.saveState();
    }
}