package main;

import controllers.AdministratorController;
import controllers.DoctorController;
import controllers.PatientController;
import entity.*;
import services.*;
import enums.Gender;
import dataimport.DataImportService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        UserService userService = new UserService();
        StaffService staffService = new StaffService();
        InventoryService inventoryService = new InventoryService();
        MedicalRecordService medicalRecordService = new MedicalRecordService();
        AvailabilityService availabilityService = new AvailabilityService();
        AppointmentService appointmentService = new AppointmentService(availabilityService);

        // Initialize controllers
        AdministratorController adminController = new AdministratorController(userService, staffService, inventoryService);
        DoctorController doctorController = new DoctorController(userService, medicalRecordService, appointmentService, availabilityService);
        PatientController patientController = new PatientController(userService, medicalRecordService, appointmentService, availabilityService);

        // Initialize DataImportService with StaffService dependency
        DataImportService dataImportService = new DataImportService(staffService);

        try {
            System.out.println("\n=== Importing Patients ===");
            dataImportService.importPatients("data/Patient_List.csv").forEach(patient -> {
                System.out.println(patient);
                medicalRecordService.addMedicalRecord(patient.getHospitalID(), new MedicalRecord(
                        patient.getHospitalID(),
                        patient.getName(),
                        "1980-01-01", // Placeholder for DOB
                        patient.getGender().toString(),
                        "A+", // Placeholder for blood type
                        new ContactInfo("1234567890", "placeholder@example.com")
                ));
            });

            System.out.println("\n=== Importing Staff ===");
            dataImportService.importStaff("data/Staff_List.csv").forEach(staff -> {
                System.out.println(staff);
                staffService.addStaff(staff);
            });

            System.out.println("\n=== Importing Medicines ===");
            dataImportService.importMedicines("data/Medicine_List.csv").forEach(medicine -> {
                System.out.println(medicine);
                inventoryService.addMedicine(medicine);
            });
        } catch (Exception e) {
            System.out.println("Error importing data: " + e.getMessage());
        }


        // Test Doctor Menu
        System.out.println("\n=== Testing Doctor Menu ===");
        User doctorUser = new Doctor("D001", "John Smith", Gender.MALE, 45);
        System.out.println("Logged in as Doctor: " + doctorUser.getName());
        doctorController.displayDoctorMenu(doctorUser);

        // Display availability slots for the doctor
        System.out.println("\n=== Displaying Doctor Availability Slots ===");
        availabilityService.displayAvailability("D001");

        // Test Patient Menu
        System.out.println("\n=== Testing Patient Menu ===");
        User patientUser = new Patient("P1001", "Alice Brown", Gender.FEMALE, "password");
        System.out.println("Logged in as Patient: " + patientUser.getName());
        patientController.displayPatientMenu(patientUser);

    }
}
