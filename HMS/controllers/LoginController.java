package controllers;

import entity.User;
import repositories.PatientRepository;
import repositories.StaffRepository;
import services.UserService;

public class LoginController {
    private PatientRepository patientRepository;
    private StaffRepository staffRepository;
    private UserService userService;

    public LoginController(PatientRepository patientRepo, StaffRepository staffRepo, UserService userService) {
        this.patientRepository = patientRepo;
        this.staffRepository = staffRepo;
        this.userService = userService;
    }

    public void login(String hospitalID, String password) {
        User user = patientRepository.getPatient(hospitalID);
        if (user == null) {
            user = staffRepository.getStaff(hospitalID);
        }

        if (user == null || !userService.verifyPassword(password, user.getPasswordHash())) {
            System.out.println("Invalid credentials.");
            return;
        }

        if (!user.hasLoggedIn()) {
            System.out.println("First-time login detected. Please change your password.");
            // Simulated input flow for password change
            String newPassword = "newPassword123"; // Simulate input
            String confirmPassword = "newPassword123"; // Simulate input
            String result = userService.changePassword(password, newPassword, confirmPassword, user.getPasswordHash());
            if (result.equals("Password changed successfully.")) {
                user.setPasswordHash(userService.hashPassword(newPassword));
                user.setLoggedIn(true);
            } else {
                System.out.println(result);
                return;
            }
        }

        System.out.println("Welcome, " + user.getName() + "!");
    }
}
