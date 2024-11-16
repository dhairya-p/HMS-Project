package entity;

import java.util.List;

public class AppointmentOutcome {
    private String appointmentID;
    private String doctorID;
    private String patientID;
    private String consultationNotes;
    private List<Prescription> prescriptions;
    private String servicesProvided;

    public AppointmentOutcome(String appointmentID, String doctorID, String patientID, String consultationNotes, List<Prescription> prescriptions, String servicesProvided) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.consultationNotes = consultationNotes;
        this.prescriptions = prescriptions;
        this.servicesProvided = servicesProvided;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getServicesProvided() {
        return servicesProvided;
    }

    @Override
    public String toString() {
        return "AppointmentOutcome {" +
                "appointmentID='" + appointmentID + '\'' +
                ", doctorID='" + doctorID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", consultationNotes='" + consultationNotes + '\'' +
                ", prescriptions=" + prescriptions +
                ", servicesProvided='" + servicesProvided + '\'' +
                '}';
    }
}
