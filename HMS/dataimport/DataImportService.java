package dataimport;

import entity.*;
import enums.Gender;
import enums.UserRole;
import utils.PasswordUtils;
import services.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataImportService {
    private StaffService staffService;

    public DataImportService(StaffService staffService) {
        this.staffService = staffService;
    }

    public List<Patient> importPatients(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Flag to skip the header row
    
            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip the header
                    isHeader = false;
                    continue;
                }
    
                String[] fields = line.split(",");
                if (fields.length < 6) {
                    System.out.println("Invalid data row: " + line);
                    continue;
                }
    
                Gender gender;
                try {
                    gender = Gender.valueOf(fields[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid gender value: " + fields[3]);
                    continue;
                }
    
                Patient patient = new Patient(fields[0].trim(), fields[1].trim(), gender);
                patients.add(patient);
            }
        }
        return patients;
    }
    

    public List<Staff> importStaff(String filePath) throws IOException {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Flag to skip the header row

            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip the header
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 5) {
                    System.out.println("Invalid data row: " + line);
                    continue;
                }

                Gender gender;
                try {
                    gender = Gender.valueOf(fields[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid gender value: " + fields[3]);
                    continue;
                }

                String role = fields[2].trim().toUpperCase();
                Staff staff = staffService.createStaff(fields[0].trim(), fields[1].trim(), role, gender.name(), Integer.parseInt(fields[4].trim()));
                staffList.add(staff);
            }
        }
        return staffList;
    }
    

    public List<Medicine> importMedicines(String filePath) throws IOException {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Flag to skip the header row
    
            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip the header
                    isHeader = false;
                    continue;
                }
    
                String[] fields = line.split(",");
                if (fields.length < 3) {
                    System.out.println("Invalid data row: " + line);
                    continue;
                }
    
                String name = fields[0].trim();
                int initialStock = Integer.parseInt(fields[1].trim());
                int lowStockAlert = Integer.parseInt(fields[2].trim());
                medicines.add(new Medicine(name, initialStock, lowStockAlert));
            }
        }
        return medicines;
    }
}
