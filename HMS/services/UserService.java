package services;
import utils.PasswordUtils;

public class UserService {

    public boolean verifyPassword(String password, String storedHash) {
        return PasswordUtils.hashPassword(password).equals(storedHash);
    }

    public String changePassword(String oldPassword, String newPassword, String confirmPassword, String storedHash) {
        if (!verifyPassword(oldPassword, storedHash)) {
            return "Old password is incorrect.";
        }

        if (newPassword.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "New password entries do not match.";
        }

        if (PasswordUtils.hashPassword(newPassword).equals(storedHash)) {
            return "New password cannot be the same as the old password.";
        }

        return "Password changed successfully.";
    }

    public String hashPassword(String password) {
        return PasswordUtils.hashPassword(password);
    }
}
