package entity;

import enums.PrescriptionStatus;

public class Prescription {
    private String medicineName;
    private int quantity;
    private PrescriptionStatus status;

    public Prescription(String medicineName, int quantity, PrescriptionStatus status) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.status = status;
    }

    // Factory method to create a Prescription from String inputs
    public static Prescription fromString(String medicineName, int quantity, String status) {
        PrescriptionStatus prescriptionStatus = PrescriptionStatus.valueOf(status.toUpperCase());
        return new Prescription(medicineName, quantity, prescriptionStatus);
    }

    
    @Override
    public String toString() {
        return "Prescription {" +
                "medicineName='" + medicineName + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }
}
