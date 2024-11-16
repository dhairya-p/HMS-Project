package entity;
import enums.Gender;
import enums.UserRole;
// Pharmacist Entity
import utils.PasswordUtils;

public class Pharmacist extends Staff {
    private String passwordHash;
    private boolean hasLoggedIn;

    public Pharmacist(String hospitalID, String name, Gender gender, int age) {
        super(hospitalID, name, UserRole.PHARMACIST, gender, age);
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
        return String.format("Pharmacist[ID=%s, Name=%s, Gender=%s, Age=%d, LoggedIn=%b]",
                getHospitalID(), getName(), getGender(), getAge(), hasLoggedIn);
    }
}
