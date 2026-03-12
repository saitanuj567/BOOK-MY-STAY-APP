import java.util.*;

/*
============================================================
CLASS – Service
============================================================
Represents an add-on service.
*/

class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}


/*
============================================================
CLASS – AddOnServiceManager
============================================================
Use Case 7: Add-On Service Selection
Manages optional services for reservations.
*/

class AddOnServiceManager {

    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {

        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = servicesByReservation.get(reservationId);

        if (services == null) {
            return 0;
        }

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}


/*
============================================================
MAIN CLASS – UseCase7AddOnServiceSelection
============================================================
*/

public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        Service spa = new Service("Spa", 1000);
        Service breakfast = new Service("Breakfast", 500);

        manager.addService(reservationId, spa);
        manager.addService(reservationId, breakfast);

        double total = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + total);
    }
}