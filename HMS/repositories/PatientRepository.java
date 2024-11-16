package repositories;

import entity.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientRepository {
    private Map<String, Patient> patients = new HashMap<>();

    public void addPatient(Patient patient) {
        patients.put(patient.getHospitalID(), patient);
    }

    public Patient getPatient(String hospitalID) {
        return patients.get(hospitalID);
    }

    public boolean patientExists(String hospitalID) {
        return patients.containsKey(hospitalID);
    }

    public void updatePassword(String hospitalID, String newPasswordHash) {
        Patient patient = getPatient(hospitalID);
        if (patient != null) {
            patient.setPasswordHash(newPasswordHash);
        }
    }

    public void updateLoginStatus(String hospitalID, boolean hasLoggedIn) {
        Patient patient = getPatient(hospitalID);
        if (patient != null) {
            patient.setLoggedIn(hasLoggedIn);
        }
    }

    public Map<String, Patient> getAllPatients() {
        return patients;
    }
}
