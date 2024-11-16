package controllers;

import entity.Availability;
import entity.User;
import services.AppointmentService;
import services.AvailabilityService;
import services.MedicalRecordService;
import services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class PatientController extends BaseController {
    private MedicalRecordService medicalRecordService;
    private AppointmentService appointmentService;
    private AvailabilityService availabilityService;

    // Constructor
    public PatientController(UserService userService, MedicalRecordService medicalRecordService, AppointmentService appointmentService, AvailabilityService availabilityService) {
        super(userService);
        this.medicalRecordService = medicalRecordService;
        this.appointmentService = appointmentService;
        this.availabilityService = availabilityService;
    }

    // Main menu for the patient
    public void displayPatientMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. Change My Password");
            System.out.println("3. View Medical Record");
            System.out.println("4. Update Personal Information");
            System.out.println("5. View Available Appointment Slots");
            System.out.println("6. Schedule Appointment");
            System.out.println("7. Reschedule Appointment");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. View Scheduled Appointments");
            System.out.println("10. View Past Appointment Outcome Records");
            System.out.println("11. Logout");
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
                    medicalRecordService.displayMedicalRecord(user.getHospitalID());
                    break;
                case 4:
                    updatePersonalInformation(user.getHospitalID());
                    break;
                case 5:
                    appointmentService.displayAvailableSlots();
                    break;
                case 6:
                    scheduleAppointment(user.getHospitalID());
                    break;
                case 7:
                    rescheduleAppointment(user.getHospitalID());
                    break;
                case 8:
                    cancelAppointment(user.getHospitalID());
                    break;
                case 9:
                    appointmentService.displayAppointmentsForPatient(user.getHospitalID());
                    break;
                case 10:
                    viewPastAppointmentOutcomes(user.getHospitalID());
                    break;
                case 11:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updatePersonalInformation(String patientID) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new email address: ");
        String email = scanner.nextLine();

        System.out.print("Enter new contact number: ");
        String contactNumber = scanner.nextLine();

        boolean updated = medicalRecordService.updateContactInformation(patientID, email, contactNumber);
        if (updated) {
            System.out.println("Personal information updated successfully.");
        } else {
            System.out.println("Failed to update personal information.");
        }
    }

    // Method to reschedule appointments
    private void rescheduleAppointment(String patientID) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Appointment ID to reschedule: ");
        String appointmentID = scanner.nextLine();

        System.out.print("Enter new appointment date and time (YYYY-MM-DDTHH:MM): ");
        String newDateTime = scanner.nextLine();

        boolean success = appointmentService.rescheduleAppointment(appointmentID, LocalDateTime.parse(newDateTime));
        if (success) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule appointment. The new time slot may be unavailable.");
        }
    }

    // Method to cancel appointments
    private void cancelAppointment(String patientID) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Appointment ID to cancel: ");
        String appointmentID = scanner.nextLine();

        boolean success = appointmentService.cancelAppointment(appointmentID);
        if (success) {
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Failed to cancel appointment. Appointment not found.");
        }
    }

    // View past appointment outcomes
    private void viewPastAppointmentOutcomes(String patientID) {
        appointmentService.displayPastAppointmentOutcomes(patientID);
    }

    // Method to schedule an appointment
    private void scheduleAppointment(String patientID) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        // Display all available slots for the selected date
        System.out.println("Available slots for " + date + ":");
        List<Availability> availableSlots = availabilityService.getAvailableSlotsForDate(date);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for the selected date. Please try another date.");
            return;
        }

        // Show all available slots
        for (int i = 0; i < availableSlots.size(); i++) {
            Availability slot = availableSlots.get(i);
            System.out.println((i + 1) + ". Doctor ID: " + slot.getDoctorID() +
                    ", Start Time: " + slot.getStartTime() +
                    ", End Time: " + slot.getEndTime());
        }

        // Ask the patient to select a time slot
        System.out.print("Select a slot (1-" + availableSlots.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > availableSlots.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        // Get the selected slot
        Availability selectedSlot = availableSlots.get(choice - 1);
        LocalDateTime startTime = selectedSlot.getStartTime();

        // Ask for the reason for the appointment
        System.out.print("Enter reason for appointment: ");
        String reason = scanner.nextLine();

        // Generate a unique appointment ID
        String appointmentID = "A" + System.currentTimeMillis();

        // Schedule the appointment
        if (appointmentService.scheduleAppointment(appointmentID, patientID, selectedSlot.getDoctorID(), startTime, reason)) {
            System.out.println("Your appointment has been booked successfully.");
            System.out.println("Appointment Details:");
            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Date: " + startTime.toLocalDate());
            System.out.println("Time: " + startTime.toLocalTime());
            System.out.println("Doctor: " + selectedSlot.getDoctorID());
            System.out.println("Reason: " + reason);
            System.out.println("Status: Confirmed");
        } else {
            System.out.println("Failed to book appointment. Please try again.");
        }
    }
}
