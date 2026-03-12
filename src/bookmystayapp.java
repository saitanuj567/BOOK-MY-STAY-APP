import java.util.*;

/*
============================================================
CLASS – Reservation
============================================================
*/
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/*
============================================================
CLASS – BookingRequestQueue
============================================================
*/
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}


/*
============================================================
CLASS – RoomInventory
============================================================
*/
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public boolean hasAvailableRoom(String type) {

        return inventory.getOrDefault(type, 0) > 0;
    }

    public void allocateRoom(String type) {

        inventory.put(type, inventory.get(type) - 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}


/*
============================================================
CLASS – RoomAllocationService
============================================================
*/
class RoomAllocationService {

    private Map<String, Integer> counters = new HashMap<>();

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (!inventory.hasAvailableRoom(type)) {
            System.out.println("No rooms available for " + type);
            return;
        }

        inventory.allocateRoom(type);

        int number = counters.getOrDefault(type, 0) + 1;
        counters.put(type, number);

        String roomId = type + "-" + number;

        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId
        );
    }
}


/*
============================================================
CLASS – ConcurrentBookingProcessor
============================================================
*/
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (!bookingQueue.hasPendingRequests())
                    break;

                reservation = bookingQueue.getRequest();
            }

            synchronized (inventory) {

                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}


/*
============================================================
MAIN CLASS – UseCase11ConcurrentBookingSimulation
============================================================
*/
public class  {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        t1.start();
        t2.start();

        try {

            t1.join();
            t2.join();

        } catch (InterruptedException e) {

            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {

            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}