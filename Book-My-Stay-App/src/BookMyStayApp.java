public class BookMyStayApp {
    // Inner helper method to display room details
    private static void displayRoom(String roomType, int beds, double price, int availability) {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
        System.out.println("Available: " + availability + "\n");
    }

    /**
     * Application entry point.
     * Initializes room details and prints availability.
     *
     * @param args Command-line arguments (not used here).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 2.1\n");

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        System.out.println("=== Room Details & Availability ===");

        // Display Single Room
        displayRoom("Single Room", 1, 50.0, singleRoomAvailability);

        // Display Double Room
        displayRoom("Double Room", 2, 90.0, doubleRoomAvailability);

        // Display Suite Room
        displayRoom("Suite Room", 3, 150.0, suiteRoomAvailability);

        // Program terminates after displaying information
    }


}
