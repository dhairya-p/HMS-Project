package controllers;

import services.AppointmentService;
import services.InventoryService;
import services.UserService;
import entity.User;

import java.util.Scanner;

public class PharmacistController extends BaseController {
    private InventoryService inventoryService;
    private AppointmentService appointmentService;

    public PharmacistController(UserService userService, InventoryService inventoryService, AppointmentService appointmentService) {
        super(userService);
        this.inventoryService = inventoryService;
        this.appointmentService = appointmentService;
    }

    public void displayPharmacistMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. Change My Password");
            System.out.println("3. View Appointment Outcome Record");
            System.out.println("4. Update Prescription Status");
            System.out.println("5. View Medication Inventory");
            System.out.println("6. Submit Replenishment Request");
            System.out.println("7. Logout");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProfile(user);
                    break;
                case 2:
                    changePassword(user);
                    break;
                case 3:
                    viewAppointmentOutcomes();
                    break;
                case 4:
                    updatePrescriptionStatus();
                    break;
                case 5:
                    inventoryService.displayInventory();
                    break;
                case 6:
                    submitReplenishmentRequest();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAppointmentOutcomes() {
        System.out.println("Viewing appointment outcome records...");
        // Implementation for displaying outcome records
    }

    private void updatePrescriptionStatus() {
        System.out.println("Updating prescription status...");
        // Implementation for updating prescriptions
    }

    private void submitReplenishmentRequest() {
        System.out.println("Submitting replenishment request...");
        // Implementation for replenishment
    }
}
