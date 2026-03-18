import java.util.HashMap;
import java.util.Map;
public class BookMyStayApp {
    // Centralized inventory using HashMap
    private Map<String, Integer> inventory;
    // Room details (price per night)
    private Map<String, Double> roomDetails;

    // Constructor initializes inventory and room details
    public BookMyStayApp() {
        inventory = new HashMap<>();
        roomDetails = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: no suites available

        // Register room details
        roomDetails.put("Single Room", 50.0);
        roomDetails.put("Double Room", 90.0);
        roomDetails.put("Suite Room", 150.0);
    }

    // Read-only search method
    private void searchAvailableRooms() {
        System.out.println("=== Room Search Results ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String roomType = entry.getKey();
            int availability = entry.getValue();

            // Defensive check: only show rooms with availability > 0
            if (availability > 0) {
                double price = roomDetails.getOrDefault(roomType, 0.0);
                System.out.println("Room Type: " + roomType);
                System.out.println("Price per Night: $" + price);
                System.out.println("Available: " + availability + "\n");
            }
        }
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 4.1\n");

        // Initialize app
        BookMyStayApp app = new BookMyStayApp();

        // Guest initiates search
        System.out.println("Guest is searching for available rooms...\n");
        app.searchAvailableRooms();

        // Program terminates without modifying inventory
    }
}
