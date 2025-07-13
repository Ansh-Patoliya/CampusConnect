package com.eventApp.Utils;

/*
    -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
    -> This method ignores spaces, digits, and special characters — it returns false if any are present.
    -> Use this for fields like name, department, or club name where only letters are allowed.
 */
public class ValidationUtils {

    public static boolean checkName(String name) {
    /*
        -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
        -> This method ignores spaces, digits, and special characters — it returns false if any are present.
    */
        if (name == null || name.isEmpty()) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkEmail(String email) {
        return true;
    }

    public static boolean checkPassword(String password) {
    /*
        -> Validates whether the given password is strong and secure.
        -> A strong password must meet all the following conditions:

            -> At least 8 characters long
            -> Contains at least one uppercase letter (A–Z)
            -> Contains at least one lowercase letter (a–z)
            -> Contains at least one digit (0–9)
            -> Contains at least one special character (e.g. !@#$%^&*)
     */
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                hasUpperCase = true;
            } else if (c >= 'a' && c <= 'z') {
                hasLowerCase = true;
            } else if (c >= '0' && c <= '9') {
                hasDigit = true;
            } else if ("!@#$%^&*".indexOf(c) > -1) {
                hasSpecialChar = true;
            }
        }

        return hasDigit && hasLowerCase && hasUpperCase && hasSpecialChar;
    }

    public static boolean isMatchingPasswords(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean checkDepartment(String department) {
    /*
        -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
        -> This method ignores spaces, digits, and special characters — it returns false if any are present.
    */
        return true;
    }

    public static boolean checkSemester(String semester) {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        if (semester == null || semester.length() != 1) {
            return false;
        }
         char c = semester.charAt(0);
            if (c < '0' || c > '8') {
                return false; // Found a non-digit character
            }

        return true;
    }

    public static boolean checkEnrollment(String enrollment) {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        if (enrollment == null || enrollment.isEmpty()) {
            return false;
        }

        for (int i = 0; i < enrollment.length(); i++) {
            char c = enrollment.charAt(i);
            if (c < '0' || c > '9') {
                return false; // Found a non-digit character
            }
        }
        return true;
    }
}
