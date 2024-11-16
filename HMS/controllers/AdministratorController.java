package controllers;

import services.InventoryService;
import services.StaffService;
import services.UserService;
import entity.Staff;
import entity.User;

import java.util.Scanner;
public class AdministratorController extends BaseController {
    private StaffService staffService;
    private InventoryService inventoryService;

    public AdministratorController(UserService userService, StaffService staffService, InventoryService inventoryService) {
        super(userService);
        this.staffService = staffService;
        this.inventoryService = inventoryService;
    }

    public void displayAdministratorMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. Change My Password");
            System.out.println("3. View and Manage Hospital Staff");
            System.out.println("4. View Appointment Details");
            System.out.println("5. View and Manage Medication Inventory");
            System.out.println("6. Approve Replenishment Requests");
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
                    manageHospitalStaff();
                    break;
                case 4:
                    viewAppointmentDetails();
                    break;
                case 5:
                    manageMedicationInventory();
                    break;
                case 6:
                    approveReplenishmentRequests();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageHospitalStaff() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nManage Hospital Staff:");
            System.out.println("1. Add Staff Member");
            System.out.println("2. View All Staff Members");
            System.out.println("3. Update Staff Details");
            System.out.println("4. Remove Staff Member");
            System.out.println("5. Go Back");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStaffMember();
                    break;
                case 2:
                    staffService.displayAllStaff();
                    break;
                case 3:
                    updateStaffDetails();
                    break;
                case 4:
                    removeStaffMember();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStaffMember() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID: ");
        String hospitalID = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Role (DOCTOR, PHARMACIST, ADMINISTRATOR): ");
        String role = scanner.nextLine().toUpperCase();

        System.out.print("Enter Gender (MALE, FEMALE): ");
        String gender = scanner.nextLine().toUpperCase();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Staff newStaff = staffService.createStaff(hospitalID, name, role, gender, age);
            staffService.addStaff(newStaff);
            System.out.println("Staff member added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void updateStaffDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID to update: ");
        String hospitalID = scanner.nextLine();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter new age: ");
        int newAge = scanner.nextInt();

        staffService.updateStaffDetails(hospitalID, newName, newAge);
    }

    private void removeStaffMember() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID to remove: ");
        String hospitalID = scanner.nextLine();

        boolean success = staffService.removeStaff(hospitalID);
        if (success) {
            System.out.println("Staff member removed successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    private void viewAppointmentDetails() {
        System.out.println("Viewing appointment details is not implemented yet.");
        // Future implementation for appointment management
    }

    private void manageMedicationInventory() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nManage Medication Inventory:");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Stock");
            System.out.println("3. Go Back");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    inventoryService.displayInventory();
                    break;
                case 2:
                    updateStock();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateStock() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();

        System.out.print("Enter New Stock Quantity: ");
        int quantity = scanner.nextInt();

        inventoryService.updateStock(medicineName, quantity);
    }

    private void approveReplenishmentRequests() {
        System.out.println("\nApproving Replenishment Requests:");
        inventoryService.displayReplenishmentRequests();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Medicine Name to approve replenishment: ");
        String medicineName = scanner.nextLine();

        inventoryService.approveReplenishmentRequest(medicineName);
    }
}
