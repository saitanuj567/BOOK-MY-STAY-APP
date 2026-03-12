import java.util.LinkedList;
import java.util.Queue;

/*
============================================================
CLASS – Reservation
============================================================
Description:
This class represents a booking request made by a guest.
At this stage, a reservation only captures intent.
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
Description:
This class manages booking requests using FIFO Queue.
Requests are processed in the order received.
*/

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation processRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


/*
============================================================
MAIN CLASS – UseCase5BookingRequestQueue
============================================================
*/

public class bookmystayapp{

    public static void main(String[] args) {

        // Display header
        System.out.println("Booking Request Queue");

        // Create queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process queue (FIFO)
        while (bookingQueue.hasPendingRequests()) {

            Reservation r = bookingQueue.processRequest();

            System.out.println(
                    "Processing booking for Guest: "
                            + r.getGuestName()
                            + ", Room Type: "
                            + r.getRoomType()
            );
        }
    }
}