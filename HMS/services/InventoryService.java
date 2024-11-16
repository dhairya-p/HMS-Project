package services;

import entity.Medicine;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<String, Medicine> inventory;
    private Map<String, Integer> replenishmentRequests;

    public InventoryService() {
        this.inventory = new HashMap<>();
        this.replenishmentRequests = new HashMap<>();
    }

    /**
     * Adds a medicine to the inventory.
     *
     * @param medicine The medicine object
     */
    public void addMedicine(Medicine medicine) {
        inventory.put(medicine.getName(), medicine);
    }

    /**
     * Displays the current inventory.
     */
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.");
            return;
        }
        System.out.println("Current Inventory:");
        inventory.values().forEach(System.out::println);
    }

    /**
     * Updates the stock for a specific medicine.
     *
     * @param medicineName The name of the medicine
     * @param quantity The new stock quantity
     */
    public void updateStock(String medicineName, int quantity) {
        Medicine medicine = inventory.get(medicineName);
        if (medicine == null) {
            System.out.println("Medicine not found in inventory.");
            return;
        }
        medicine.updateStock(quantity);
        System.out.println("Stock updated successfully for " + medicineName);
    }

    /**
     * Submits a replenishment request for a medicine.
     *
     * @param medicineName The name of the medicine
     * @param quantity The quantity to replenish
     */
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        if (!inventory.containsKey(medicineName)) {
            System.out.println("Medicine not found in inventory.");
            return;
        }
        replenishmentRequests.put(medicineName, quantity);
        System.out.println("Replenishment request submitted for " + medicineName);
    }

    /**
     * Approves a replenishment request and updates inventory.
     *
     * @param medicineName The name of the medicine
     */
    public void approveReplenishmentRequest(String medicineName) {
        if (!replenishmentRequests.containsKey(medicineName)) {
            System.out.println("No pending request for " + medicineName);
            return;
        }
        int quantity = replenishmentRequests.get(medicineName);
        updateStock(medicineName, quantity);
        replenishmentRequests.remove(medicineName);
        System.out.println("Replenishment request approved for " + medicineName);
    }

    /**
     * Displays pending replenishment requests.
     */
    public void displayReplenishmentRequests() {
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }
        System.out.println("Pending Replenishment Requests:");
        replenishmentRequests.forEach((medicineName, quantity) -> 
            System.out.println("Medicine: " + medicineName + ", Quantity: " + quantity)
        );
    }
}
