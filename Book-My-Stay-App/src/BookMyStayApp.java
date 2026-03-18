import java.util.*;
public class BookMyStayApp {
    // Map reservation IDs to selected services
    private Map<String, List<String>> reservationServices;
    // Map service names to their costs
    private Map<String, Double> serviceCatalog;

    // Constructor initializes structures
    public BookMyStayApp() {
        reservationServices = new HashMap<>();
        serviceCatalog = new HashMap<>();

        // Register available services
        serviceCatalog.put("Breakfast", 15.0);
        serviceCatalog.put("Airport Pickup", 30.0);
        serviceCatalog.put("Spa Access", 50.0);
        serviceCatalog.put("Extra Bed", 20.0);
    }

    // Attach services to a reservation
    private void addServicesToReservation(String reservationId, List<String> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);
    }

    // Calculate total additional cost for a reservation
    private double calculateAdditionalCost(String reservationId) {
        List<String> services = reservationServices.getOrDefault(reservationId, Collections.emptyList());
        double total = 0.0;
        for (String service : services) {
            total += serviceCatalog.getOrDefault(service, 0.0);
        }
        return total;
    }

    // Display reservation services and costs
    private void displayReservationDetails(String reservationId) {
        System.out.println("=== Reservation Details ===");
        System.out.println("Reservation ID: " + reservationId);

        List<String> services = reservationServices.getOrDefault(reservationId, Collections.emptyList());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            System.out.println("Selected Services: " + services);
            System.out.println("Additional Cost: $" + calculateAdditionalCost(reservationId));
        }
        System.out.println();
    }

    // Application entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 7.1\n");

        BookMyStayApp app = new BookMyStayApp();

        // Example reservations
        String reservation1 = "RES-1001";
        String reservation2 = "RES-1002";

        // Guest selects services
        app.addServicesToReservation(reservation1, Arrays.asList("Breakfast", "Spa Access"));
        app.addServicesToReservation(reservation2, Arrays.asList("Airport Pickup", "Extra Bed"));

        // Display reservation details
        app.displayReservationDetails(reservation1);
        app.displayReservationDetails(reservation2);
    }
}
