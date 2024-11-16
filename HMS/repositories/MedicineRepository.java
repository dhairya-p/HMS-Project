package repositories;

import entity.Medicine;

import java.util.HashMap;
import java.util.Map;

public class MedicineRepository {
    private Map<String, Medicine> medicines = new HashMap<>();

    public void addMedicine(Medicine medicine) {
        medicines.put(medicine.getName(), medicine);
    }

    public Medicine getMedicine(String name) {
        return medicines.get(name);
    }

    public boolean medicineExists(String name) {
        return medicines.containsKey(name);
    }

    public void updateStock(String name, int quantity) {
        Medicine medicine = getMedicine(name);
        if (medicine != null) {
            medicine.updateStock(quantity);
        }
    }

    public Map<String, Medicine> getAllMedicines() {
        return medicines;
    }
}
