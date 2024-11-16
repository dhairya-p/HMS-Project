package services;


import entity.*;
import enums.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enums.Gender;

public class StaffService {
    private Map<String, Staff> staffMembers;

    public StaffService() {
        this.staffMembers = new HashMap<>();
    }

    public void addStaff(Staff staff) {
        staffMembers.put(staff.getHospitalID(), staff);
    }

    public Staff getStaffById(String hospitalID) {
        return staffMembers.get(hospitalID);
    }

    public boolean removeStaff(String hospitalID) {
        if (staffMembers.containsKey(hospitalID)) {
            staffMembers.remove(hospitalID);
            return true;
        }
        return false;
    }

    public List<Staff> getStaffByRole(UserRole role) {
        return staffMembers.values().stream()
                .filter(staff -> staff.getRole() == role)
                .collect(Collectors.toList());
    }

    public void displayAllStaff() {
        if (staffMembers.isEmpty()) {
            System.out.println("No staff members available.");
            return;
        }
        System.out.println("Staff Members:");
        staffMembers.values().forEach(System.out::println);
    }

    public void updateStaffDetails(String hospitalID, String newName, int newAge) {
        Staff existingStaff = getStaffById(hospitalID);
        if (existingStaff != null) {
            existingStaff.setName(newName);
            existingStaff.setAge(newAge);
            System.out.println("Staff details updated successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    public Staff createStaff(String hospitalID, String name, String role, String gender, int age) {
        UserRole userRole;
        Gender staffGender;

        try {
            userRole = UserRole.valueOf(role.toUpperCase());
            staffGender = Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role or gender. Role must be one of [DOCTOR, PHARMACIST, ADMINISTRATOR] and gender must be [MALE, FEMALE].");
        }

        switch (userRole) {
            case DOCTOR:
                return new Doctor(hospitalID, name, staffGender, age);
            case PHARMACIST:
                return new Pharmacist(hospitalID, name, staffGender, age);
            case ADMINISTRATOR:
                return new Administrator(hospitalID, name, staffGender, age);
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}

