package services;

import entity.Appointment;
import entity.TimeSlot;
import entity.AppointmentOutcome;
import entity.Prescription;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentService {
    private Map<String, Appointment> appointments;
    private Map<String, AppointmentOutcome> appointmentOutcomes; // Manage outcomes
    private AvailabilityService availabilityService;

    public AppointmentService(AvailabilityService availabilityService) {
        this.appointments = new HashMap<>();
        this.appointmentOutcomes = new HashMap<>();
        this.availabilityService = availabilityService;
    }

    public boolean scheduleAppointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime, String reason) {
        if (appointments.values().stream().anyMatch(a -> a.getTimeSlot().getDateTime().equals(dateTime) && a.getDoctorID().equals(doctorID))) {
            System.out.println("Time slot already booked for this doctor.");
            return false;
        }
        Appointment appointment = new Appointment(appointmentID, patientID, doctorID, new TimeSlot(dateTime), "Confirmed");
        appointment.setReason(reason);
        appointments.put(appointmentID, appointment);
        System.out.println("Appointment scheduled successfully.");
        return true;
    }
    

    public void displayAvailableSlots() {
        System.out.println("\nAvailable Appointment Slots:");
        availabilityService.getAllAvailableSlots().forEach(slot -> {
            System.out.println("Doctor ID: " + slot.getDoctorID() + ", Date & Time: " + slot.getStartTime());
        });
    }
    

    

    public void displayAppointmentsForPatient(String patientID) {
        System.out.println("Appointments for Patient ID: " + patientID);
        appointments.values().stream()
                .filter(a -> a.getPatientID().equals(patientID))
                .forEach(System.out::println);
    }

    public void displayAppointmentsForDoctor(String doctorID) {
        System.out.println("\nUpcoming Appointments:");
        appointments.values().stream()
                .filter(a -> a.getDoctorID().equals(doctorID) && a.getTimeSlot().getDateTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(a -> a.getTimeSlot().getDateTime()))
                .forEach(System.out::println);
    }

    public Appointment getAppointment(String appointmentID) {
        return appointments.get(appointmentID);
    }

    public void recordAppointmentOutcome(String appointmentID, String doctorID, String patientID, String consultationNotes, List<Prescription> prescriptions, String servicesProvided) {
        AppointmentOutcome outcome = new AppointmentOutcome(appointmentID, doctorID, patientID, consultationNotes, prescriptions, servicesProvided);
        appointmentOutcomes.put(appointmentID, outcome);
        System.out.println("Appointment outcome recorded successfully.");
    }
    

    

    public List<Appointment> getPendingAppointmentsForDoctor(String doctorID) {
        return appointments.values().stream()
                .filter(a -> a.getDoctorID().equals(doctorID) && a.getStatus().equals("Pending"))
                .collect(Collectors.toList());
    }

    public void updateAppointmentStatus(String appointmentID, String status) {
        Appointment appointment = appointments.get(appointmentID);
        if (appointment != null) {
            appointment.setStatus(status);
        }
    }

    public List<String> getPatientsUnderDoctor(String doctorID) {
        return appointments.values().stream()
                .filter(a -> a.getDoctorID().equals(doctorID))
                .map(Appointment::getPatientID)
                .distinct()
                .collect(Collectors.toList());
    }


    public void displayPastAppointmentOutcomes(String patientID) {
        System.out.println("\nPast Appointment Outcomes for Patient ID: " + patientID);
        appointmentOutcomes.values().stream()
                .filter(outcome -> outcome.getPatientID().equals(patientID))
                .forEach(System.out::println);
    }

    public boolean rescheduleAppointment(String appointmentID, LocalDateTime newDateTime) {
        Appointment appointment = appointments.get(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }
        LocalDateTime oldDateTime = appointment.getTimeSlot().getDateTime();
        if (appointments.values().stream().anyMatch(a -> a.getTimeSlot().getDateTime().equals(newDateTime) && a.getDoctorID().equals(appointment.getDoctorID()))) {
            System.out.println("New time slot already booked for this doctor.");
            return false;
        }
        appointment.getTimeSlot().setDateTime(newDateTime);
        System.out.println("Appointment rescheduled successfully from " + oldDateTime + " to " + newDateTime);
        return true;
    }
    public boolean cancelAppointment(String appointmentID) {
        Appointment appointment = appointments.get(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }
        appointment.setStatus("Canceled"); // Mark as canceled
        System.out.println("Appointment canceled successfully.");
        return true;
    }
    

    
}
