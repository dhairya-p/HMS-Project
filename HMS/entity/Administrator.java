package entity;

import enums.Gender;
import enums.UserRole;
import utils.PasswordUtils;

public class Administrator extends Staff {
    private String passwordHash;
    private boolean hasLoggedIn;

    public Administrator(String hospitalID, String name, Gender gender, int age) {
        super(hospitalID, name, UserRole.ADMINISTRATOR, gender, age);
        this.passwordHash = PasswordUtils.hashPassword("password");
        this.hasLoggedIn = false;
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
        return String.format("Administrator[ID=%s, Name=%s, Gender=%s, Age=%d, LoggedIn=%b]",
                getHospitalID(), getName(), getGender(), getAge(), hasLoggedIn);
    }
}
