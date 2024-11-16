package entity;

import java.util.HashMap;
import java.util.Map;

public class MedicalRecord {
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private ContactInfo contactInfo; // Replace email and contact number
    private Map<String, String> diagnoses; // Maps diagnosis to treatment

    public MedicalRecord(String patientID, String name, String dateOfBirth, String gender, String bloodType, ContactInfo contactInfo) {
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.diagnoses = new HashMap<>();
    }

    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Map<String, String> getDiagnoses() {
        return diagnoses;
    }

    public void addDiagnosis(String diagnosis, String treatment) {
        if (diagnoses.containsKey(diagnosis)) {
            System.out.println("Diagnosis already exists. Updating treatment.");
        }
        diagnoses.put(diagnosis, treatment);
    }

    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record.append(String.format("PatientID: %s\nName: %s\nDOB: %s\nGender: %s\nBlood Type: %s\nContact Info: %s\n",
                patientID, name, dateOfBirth, gender, bloodType, contactInfo));
        record.append("Diagnoses and Treatments:\n");
        diagnoses.forEach((diagnosis, treatment) -> record.append(String.format(" - %s: %s\n", diagnosis, treatment)));
        return record.toString();
    }
}
