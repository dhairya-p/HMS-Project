package controllers;

import entity.User;
import services.UserService;

import java.util.Scanner;

public class BaseController {
    protected UserService userService;

    public BaseController(UserService userService) {
        this.userService = userService;
    }

    public void viewProfile(User user) {
        System.out.println("User Profile:");
        System.out.println("ID: " + user.getHospitalID());
        System.out.println("Name: " + user.getName());
        System.out.println("Role: " + user.getRole());
        System.out.println("Login Status: " + (user.hasLoggedIn() ? "Logged In" : "First-Time Login"));
    }

    public void changePassword(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter old password: ");
        String oldPassword = scanner.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Enter new password again: ");
        String confirmPassword = scanner.nextLine();

        String result = userService.changePassword(oldPassword, newPassword, confirmPassword, user.getPasswordHash());
        if (result.equals("Password changed successfully.")) {
            user.setPasswordHash(userService.hashPassword(newPassword));
            System.out.println(result);
        } else {
            System.out.println("Error: " + result);
        }
    }
}
