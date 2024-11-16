package repositories;

import entity.Staff;
import enums.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StaffRepository {
    private Map<String, Staff> staffMembers = new HashMap<>();

    public void addStaff(Staff staff) {
        staffMembers.put(staff.getHospitalID(), staff);
    }

    public Staff getStaff(String hospitalID) {
        return staffMembers.get(hospitalID);
    }

    public boolean staffExists(String hospitalID) {
        return staffMembers.containsKey(hospitalID);
    }

    public List<Staff> getStaffByRole(UserRole role) {
        return staffMembers.values().stream()
                .filter(staff -> staff.getRole() == role)
                .collect(Collectors.toList());
    }

    public void updatePassword(String hospitalID, String newPasswordHash) {
        Staff staff = getStaff(hospitalID);
        if (staff != null) {
            staff.setPasswordHash(newPasswordHash);
        }
    }

    public void updateLoginStatus(String hospitalID, boolean hasLoggedIn) {
        Staff staff = getStaff(hospitalID);
        if (staff != null) {
            staff.setLoggedIn(hasLoggedIn);
        }
    }

    public Map<String, Staff> getAllStaff() {
        return staffMembers;
    }
}
