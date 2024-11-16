package entity;

import enums.Gender;
import enums.UserRole;
import utils.PasswordUtils;

public class Patient implements User {
    private String hospitalID;
    private String name;
    private Gender gender;
    private String passwordHash;
    private boolean hasLoggedIn;


    public Patient(String hospitalID, String name, Gender gender) {
        this(hospitalID, name, gender, "password"); // Default to "password"
    }
    // New constructor
    public Patient(String hospitalID, String name, Gender gender, String password) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.gender = gender;
        this.passwordHash = PasswordUtils.hashPassword(password);
        this.hasLoggedIn = false;
    }

    @Override
    public String getHospitalID() {
        return hospitalID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UserRole getRole() {
        return UserRole.PATIENT;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean hasLoggedIn() {
        return hasLoggedIn;
    }

    @Override
    public void setLoggedIn(boolean hasLoggedIn) {
        this.hasLoggedIn = hasLoggedIn;
    }

    @Override
    public String toString() {
        return String.format("Patient[ID=%s, Name=%s, Gender=%s, LoggedIn=%b]",
                hospitalID, name, gender, hasLoggedIn);
    }

    public Gender getGender() {
        return gender;
    }
}
