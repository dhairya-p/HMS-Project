package entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Medicine> medicines;

    public Inventory() {
        this.medicines = new HashMap<>();
    }

    public void addMedicine(Medicine medicine) {
        medicines.put(medicine.getName(), medicine);
    }

    public Medicine getMedicine(String name) {
        return medicines.get(name);
    }

    public void updateStock(String name, int quantity) {
        if (medicines.containsKey(name)) {
            medicines.get(name).updateStock(quantity);
        }
    }

    public Map<String, Medicine> getAllMedicines() {
        return medicines;
    }

    @Override
    public String toString() {
        return medicines.toString();
    }
}
