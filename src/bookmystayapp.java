import java.util.*;

/*
============================================================
CLASS – RoomInventory
Maintains room availability
============================================================
*/
class RoomInventory {

    private Map<String, Integer> rooms;

    public RoomInventory() {

        rooms = new HashMap<>();

        rooms.put("Single", 5);
        rooms.put("Double", 4);
        rooms.put("Suite", 2);
    }

    public void restoreRoom(String type) {

        rooms.put(type, rooms.get(type) + 1);
    }

    public int getAvailability(String type) {

        return rooms.getOrDefault(type, 0);
    }
}


/*
============================================================
CLASS – CancellationService
Handles booking cancellations
============================================================
*/
class CancellationService {

    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {

        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {

        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {

            System.out.println("Reservation not found.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        inventory.restoreRoom(roomType);

        releasedRoomIds.push(reservationId);

        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        while (!releasedRoomIds.isEmpty()) {

            System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
        }
    }
}


/*
============================================================
MAIN CLASS – UseCase10BookingCancellation
============================================================
*/
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        String reservationId = "Single-1";
        String roomType = "Single";

        cancellationService.registerBooking(reservationId, roomType);

        cancellationService.cancelBooking(reservationId, inventory);

        cancellationService.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: " +
                inventory.getAvailability("Single"));
    }
}