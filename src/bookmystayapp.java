import java.io.*;
import java.util.*;

/*
============================================================
CLASS – RoomInventory
Maintains room availability
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

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setRoomCount(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getRoomCount(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}


/*
============================================================
CLASS – FilePersistenceService
Handles saving and loading inventory from file
============================================================
*/
class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {

                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }


    public boolean loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {

                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    inventory.setRoomCount(roomType, count);
                }
            }

            return true;

        } catch (IOException e) {

            return false;
        }
    }
}


/*
============================================================
MAIN CLASS – UseCase12DataPersistenceRecovery
============================================================
*/
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        String filePath = "inventory.txt";

        boolean loaded = persistenceService.loadInventory(inventory, filePath);

        if (!loaded) {

            System.out.println("No valid inventory data found. Starting fresh.");
        }

        inventory.displayInventory();

        persistenceService.saveInventory(inventory, filePath);
    }
}