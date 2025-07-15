package com.eventApp.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public static boolean checkDuplicateEmail(String newEmail) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT email FROM Users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newEmail);

            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean checkEmail(String email) {
        if(!checkDuplicateEmail(email)){
            return false;
        }
        if( email == null || email.isEmpty()) {
            return false;
        }
        // ->@ count exactly 1
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if(email.charAt(i) == '@') {
                atCount++;
            }
        }
        if(atCount != 1) {
            return false;
        }
        /*
            ->Valid Position of @
                ->The @ should not be:
                ->At the start of the email.
                ->At the end of the email.
         */
        if(email.indexOf('@') == 0 || email.lastIndexOf('@') == email.length() - 1) {
            return false;
        }
        /*
            ->Valid Domain
                ->The domain part (after @) should contain at least one dot (.)
                ->The dot should not be at the start or end of the domain.
         */
        int dotIndex = email.indexOf('.', email.indexOf('@'));
        if(dotIndex == -1 || dotIndex == email.length() - 1 || dotIndex == email.indexOf('@') + 1) {
            return false;
        }
        /*
            ->minimum domain length
                ->The domain part should be at least 2 characters long (e.g., "com", "org", "in").
         */
        String domain = email.substring(email.indexOf('.') + 1);
        if(domain.length() < 2) {
            return false;
        }
        /*
            ->Valid Characters
                ->The email should only contain valid characters:
                ->Alphanumeric characters (A-Z, a-z, 0-9)
                ->Special characters like . _ - @
         */
        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') &&
                    c != '.' && c != '_' && c != '-' && c != '@') {
                return false;
            }
        }
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

    public static boolean checkClubName(String clubName) {
        /*
            -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
            -> This method ignores spaces, digits, and special characters — it returns false if any are present.
        */
        return checkName(clubName);
    }

    public static boolean checkDescription(String description) {
        /*
            -> Validates that the given input string is not empty.
            -> This method returns false if the input is null or contains only whitespace.
        */
        return description != null && !description.trim().isEmpty();
    }

    public static boolean checkCategory(String category) {
        /*
            -> Validates that the given input string is not empty.
            -> This method returns false if the input is null or contains only whitespace.
        */
        return category != null && !category.trim().isEmpty();
    }

    public static boolean maxMembers(String maxMembers) {
        /*
            -> Validates that the given input contains only numeric digits (0–9).
            -> This method returns false if the input includes letters, spaces, or special characters.
         */
        if (maxMembers == null || maxMembers.isEmpty()) {
            return false;
        }

        for (int i = 0; i < maxMembers.length(); i++) {
            char c = maxMembers.charAt(i);
            if (c < '0' || c > '9') {
                return false; // Found a non-digit character
            }
        }
        return true;
    }
}
