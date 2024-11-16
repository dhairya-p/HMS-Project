package entity;

import enums.Gender;
import enums.UserRole;
import utils.PasswordUtils;

public abstract class Staff implements User {
    private String hospitalID;
    private String name;
    private UserRole role;
    private Gender gender;
    private int age;
    private String passwordHash;
    private boolean hasLoggedIn;

    public Staff(String hospitalID, String name, UserRole role, Gender gender, int age) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.passwordHash = PasswordUtils.hashPassword("password");
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        return String.format("Staff[ID=%s, Name=%s, Role=%s, Gender=%s, Age=%d]",
                hospitalID, name, role, gender, age);
    }
}

