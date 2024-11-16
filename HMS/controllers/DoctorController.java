package controllers;

import services.*;
import entity.Appointment;
import entity.Prescription;
import entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DoctorController extends BaseController {
    private MedicalRecordService medicalRecordService;
    private AppointmentService appointmentService;
    private AvailabilityService availabilityService;

    public DoctorController(UserService userService, MedicalRecordService medicalRecordService, AppointmentService appointmentService, AvailabilityService availabilityService) {
        super(userService);
        this.medicalRecordService = medicalRecordService;
        this.appointmentService = appointmentService;
        this.availabilityService = availabilityService;
    }

    public void displayDoctorMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. Change My Password");
            System.out.println("3. View Patient Medical Records");
            System.out.println("4. Update Patient Medical Records");
            System.out.println("5. Manage Appointment Requests");
            System.out.println("6. Manage Availability");
            System.out.println("7. View Personal Schedule");
            System.out.println("8. Record Appointment Outcome");
            System.out.println("9. Logout");

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
                    viewPatientRecords(user);
                    break;
                case 4:
                    updatePatientRecords(user);
                    break;
                case 5:
                    manageAppointmentRequests(user.getHospitalID());
                    break;
                case 6:
                    manageAvailability(user.getHospitalID());
                    break;
                case 7:
                    appointmentService.displayAppointmentsForDoctor(user.getHospitalID());
                    break;
                case 8:
                    recordAppointmentOutcome(user.getHospitalID());
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewPatientRecords(User user) {
        List<String> patientIDs = appointmentService.getPatientsUnderDoctor(user.getHospitalID());

        if (patientIDs.isEmpty()) {
            System.out.println("No patients under your care with medical records.");
            return;
        }

        System.out.println("Patients under your care:");
        for (String patientID : patientIDs) {
            System.out.println(" - " + patientID + ": " + medicalRecordService.getPatientName(patientID));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID to view medical record: ");
        String patientID = scanner.nextLine();

        medicalRecordService.displayMedicalRecord(patientID);
    }

    private void updatePatientRecords(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        if (!medicalRecordService.patientExists(patientID)) {
            System.out.println("No medical record found for this patient.");
            return;
        }

        System.out.print("Enter New Diagnosis: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Enter Treatment Plan for this Diagnosis: ");
        String treatment = scanner.nextLine();

        boolean success = medicalRecordService.updateDiagnosis(patientID, diagnosis, treatment);
        if (success) {
            System.out.println("New diagnosis and treatment added to the patient's medical record.");
        } else {
            System.out.println("Failed to update medical record.");
        }
    }

    private void manageAvailability(String doctorID) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select your availability for the next 7 days.");
        for (int i = 1; i <= 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            System.out.println(i + ". " + date);
        }

        System.out.print("Select a day (1-7): ");
        int day = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        LocalDate selectedDate = LocalDate.now().plusDays(day);

        System.out.print("Enter start time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter end time (HH:mm): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());

        availabilityService.addAvailability(doctorID, LocalDateTime.of(selectedDate, startTime), LocalDateTime.of(selectedDate, endTime));
        System.out.println("Availability added for " + selectedDate);
    }

    private void manageAppointmentRequests(String doctorID) {
        List<Appointment> pendingAppointments = appointmentService.getPendingAppointmentsForDoctor(doctorID);
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }

        System.out.println("Pending Appointment Requests:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + pendingAppointments.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select an appointment to respond to (1-" + pendingAppointments.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Appointment selectedAppointment = pendingAppointments.get(choice - 1);

        System.out.print("Accept or Decline this appointment? (A/D): ");
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("A")) {
            appointmentService.updateAppointmentStatus(selectedAppointment.getAppointmentID(), "Confirmed");
            System.out.println("Appointment confirmed.");
        } else if (response.equals("D")) {
            appointmentService.updateAppointmentStatus(selectedAppointment.getAppointmentID(), "Cancelled");
            System.out.println("Appointment cancelled.");
        } else {
            System.out.println("Invalid response. Appointment status unchanged.");
        }
    }

    private void recordAppointmentOutcome(String doctorID) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();
    
        System.out.print("Enter Consultation Notes: ");
        String consultationNotes = scanner.nextLine();
    
        System.out.println("Enter Prescriptions (format: MedicineName-Quantity-Status; separate multiple entries with commas): ");
        String prescriptionInput = scanner.nextLine();
        List<Prescription> prescriptions = Arrays.stream(prescriptionInput.split(","))
                .map(entry -> {
                    String[] parts = entry.split("-");
                    return Prescription.fromString(parts[0].trim(), Integer.parseInt(parts[1].trim()), parts[2].trim());
                })
                .collect(Collectors.toList());
    
        System.out.print("Enter Services Provided: ");
        String servicesProvided = scanner.nextLine();
    
        Appointment appointment = appointmentService.getAppointment(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(doctorID)) {
            appointmentService.recordAppointmentOutcome(appointmentID, doctorID, appointment.getPatientID(), consultationNotes, prescriptions, servicesProvided);
        } else {
            System.out.println("Appointment not found or does not belong to you.");
        }
    }
    

    
    
}
