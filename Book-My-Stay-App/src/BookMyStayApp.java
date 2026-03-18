import java.util.HashMap;
import java.util.Map;
public class BookMyStayApp {
    // Centralized inventory using HashMap
    private Map<String, Integer> inventory;

    // Constructor initializes the inventory
    public BookMyStayApp() {
        inventory = new HashMap<>();
    }

    // Register a room type with its availability
    private void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    // Retrieve availability for a given room type
    private int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (e.g., after booking or cancellation)
    private void updateAvailability(String roomType, int newAvailability) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newAvailability);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display the current inventory state
    private void displayInventory() {
        System.out.println("=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println();
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 3.1\n");

        // Initialize inventory system
        BookMyStayApp app = new BookMyStayApp();

        // Register room types with availability
        app.addRoomType("Single Room", 5);
        app.addRoomType("Double Room", 3);
        app.addRoomType("Suite Room", 2);

        // Display initial inventory
        app.displayInventory();

        // Example update: one Single Room booked
        System.out.println("Booking a Single Room...");
        app.updateAvailability("Single Room", app.getAvailability("Single Room") - 1);

        // Display updated inventory
        app.displayInventory();
    }

}
