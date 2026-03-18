import java.util.*;
public class BookMyStayApp {
    // Centralized inventory
    private Map<String, Integer> inventory;
    // Mapping room types to allocated room IDs
    private Map<String, Set<String>> allocatedRooms;
    // Queue for booking requests (FIFO)
    private Queue<String> bookingQueue;

    // Constructor initializes inventory and structures
    public BookMyStayApp() {
        inventory = new HashMap<>();
        allocatedRooms = new HashMap<>();
        bookingQueue = new LinkedList<>();

        // Register room types with availability
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);

        // Initialize allocated sets
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    // Add booking request to queue
    private void addBookingRequest(String roomType) {
        bookingQueue.offer(roomType);
    }

    // Process booking requests in FIFO order
    private void processBookings() {
        while (!bookingQueue.isEmpty()) {
            String roomType = bookingQueue.poll();
            confirmReservation(roomType);
        }
    }

    // Confirm reservation and allocate room
    private void confirmReservation(String roomType) {
        int availability = inventory.getOrDefault(roomType, 0);

        if (availability > 0) {
            // Generate unique room ID
            String roomId = UUID.randomUUID().toString();

            // Ensure uniqueness via Set
            allocatedRooms.get(roomType).add(roomId);

            // Update inventory immediately
            inventory.put(roomType, availability - 1);

            System.out.println("Reservation Confirmed!");
            System.out.println("Room Type: " + roomType);
            System.out.println("Allocated Room ID: " + roomId);
            System.out.println("Remaining Availability: " + inventory.get(roomType) + "\n");
        } else {
            System.out.println("Reservation Failed: No " + roomType + " available.\n");
        }
    }

    // Display current inventory and allocations
    private void displayStatus() {
        System.out.println("=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }

        System.out.println("\n=== Allocated Rooms ===");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Allocated IDs: " + entry.getValue());
        }
        System.out.println();
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 6.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Add booking requests
        app.addBookingRequest("Single Room");
        app.addBookingRequest("Double Room");
        app.addBookingRequest("Suite Room");
        app.addBookingRequest("Suite Room"); // Will fail due to no availability

        // Process requests
        app.processBookings();

        // Display final status
        app.displayStatus();
    }
}
