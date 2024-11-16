package services;

import entity.ContactInfo;
import entity.MedicalRecord;

import java.util.HashMap;
import java.util.Map;

public class MedicalRecordService {
    private Map<String, MedicalRecord> medicalRecords;

    public MedicalRecordService() {
        this.medicalRecords = new HashMap<>();
    }

    public void addMedicalRecord(String patientID, MedicalRecord record) {
        medicalRecords.put(patientID, record);
        System.out.println("Medical record added for patient ID: " + patientID);
    }

    public boolean updateDiagnosis(String patientID, String diagnosis, String treatment) {
        MedicalRecord record = medicalRecords.get(patientID);
        if (record == null) {
            return false; // Patient ID not found
        }
        record.addDiagnosis(diagnosis, treatment);
        return true; // Successfully updated
    }

    public void displayMedicalRecord(String patientID) {
        MedicalRecord record = medicalRecords.get(patientID);
        if (record == null) {
            System.out.println("No medical record found for patient ID: " + patientID);
            return;
        }
        System.out.println("Medical Record for Patient ID: " + patientID);
        System.out.println(record);
    }

    public String getPatientName(String patientID) {
        MedicalRecord record = medicalRecords.get(patientID);
        return (record != null) ? record.getName() : "Unknown Patient";
    }

    public boolean patientExists(String patientID) {
        return medicalRecords.containsKey(patientID);
    }

    public boolean updateContactInformation(String patientID, String email, String contactNumber) {
        MedicalRecord record = medicalRecords.get(patientID);
        if (record == null) {
            return false; // Medical record not found
        }
        ContactInfo contactInfo = record.getContactInfo();
        contactInfo.setEmail(email);
        contactInfo.setPhoneNumber(contactNumber);
        record.setContactInfo(contactInfo);
        return true;
    }
}
