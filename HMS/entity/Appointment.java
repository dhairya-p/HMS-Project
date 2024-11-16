package entity;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private TimeSlot timeSlot;
    private String status; // e.g., Scheduled, Confirmed, Cancelled
    private String reason; // Reason for the appointment

    public Appointment(String appointmentID, String patientID, String doctorID, TimeSlot timeSlot, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
        this.status = status;
        this.reason = null; // Initialize as null
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Appointment[ID=%s, PatientID=%s, DoctorID=%s, TimeSlot=%s, Status=%s, Reason=%s]",
                appointmentID, patientID, doctorID, timeSlot, status, reason);
    }
}
