package entity;
import enums.UserRole;
// Interface for all users in the hospital
public interface User {
    String getHospitalID();
    String getName();
    UserRole getRole(); // Update the return type to UserRole
    String getPasswordHash();
    void setPasswordHash(String passwordHash);
    boolean hasLoggedIn();
    void setLoggedIn(boolean hasLoggedIn);
}

