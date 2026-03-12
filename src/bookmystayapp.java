import java.util.*;

/*
============================================================
CLASS – Reservation
============================================================
Represents a booking request.
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
CLASS – RoomInventory
============================================================
Maintains available room counts.
*/

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean hasAvailableRoom(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}


/*
============================================================
CLASS – RoomAllocationService
============================================================
Allocates rooms and prevents duplicates.
*/

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (!inventory.hasAvailableRoom(type)) {
            System.out.println("No rooms available for " + type);
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

        inventory.reduceRoom(type);

        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId);
    }

    private String generateRoomId(String roomType) {

        Set<String> rooms = assignedRoomsByType.getOrDefault(roomType, new HashSet<>());

        int number = rooms.size() + 1;

        return roomType + "-" + number;
    }
}


/*
============================================================
MAIN CLASS – UseCase6RoomAllocation
============================================================
Use Case 6: Reservation Confirmation & Room Allocation
*/

public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Single");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        service.allocateRoom(r1, inventory);
        service.allocateRoom(r2, inventory);
        service.allocateRoom(r3, inventory);
    }
}