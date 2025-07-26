package com.eventApp.Utils;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Loader.FXMLScreenLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

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
            FXMLScreenLoader.showMessage("Name cannot be empty.", "name", "error");
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                FXMLScreenLoader.showMessage("Name can only contain letters.", "name", "error");
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
        FXMLScreenLoader.showMessage("Email already exists.", "email", "error");
        return false;
    }

    public static boolean checkEmail(String email) {
        if (!checkDuplicateEmail(email)) {
            return false;
        }
        if (email == null || email.isEmpty()) {
            FXMLScreenLoader.showMessage("Email cannot be empty.", "email", "error");
            return false;
        }
        // ->@ count exactly 1
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {
            FXMLScreenLoader.showMessage("Email must contain exactly one '@' character.", "email", "error");
            return false;
        }
        /*
            ->Valid Position of @
                ->The @ should not be:
                ->At the start of the email.
                ->At the end of the email.
         */
        if (email.indexOf('@') == 0 || email.lastIndexOf('@') == email.length() - 1) {
            FXMLScreenLoader.showMessage("Email cannot start or end with '@'.", "email", "error");
            return false;
        }
        /*
            ->Valid Domain
                ->The domain part (after @) should contain at least one dot (.)
                ->The dot should not be at the start or end of the domain.
         */
        int dotIndex = email.indexOf('.', email.indexOf('@'));
        if (dotIndex == -1 || dotIndex == email.length() - 1 || dotIndex == email.indexOf('@') + 1) {
            FXMLScreenLoader.showMessage("Email domain must contain a dot (.) and cannot start or end with it.", "email", "error");
            return false;
        }
        /*
            ->minimum domain length
                ->The domain part should be at least 2 characters long (e.g., "com", "org", "in").
         */
        String domain = email.substring(email.indexOf('.') + 1);
        if (domain.length() < 2) {
            FXMLScreenLoader.showMessage("Email domain must be at least 2 characters long.", "email", "error");
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
                FXMLScreenLoader.showMessage("Email can only contain alphanumeric characters and special characters like . _ - @.", "email", "error");
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
            FXMLScreenLoader.showMessage("Password must be at least 8 characters long.", "password", "error");
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


    public static boolean checkSemester(String semester) {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        if (semester == null || semester.length() != 1) {
            FXMLScreenLoader.showMessage("Semester must be a single digit (0-8).", "semester", "error");
            return false;
        }
        char c = semester.charAt(0);
        if (c < '0' || c > '8') {
            FXMLScreenLoader.showMessage("Semester must be a single digit (0-8).", "semester", "error");
            return false; // Found a non-digit character
        }

        return true;
    }

    public static boolean checkDuplicateEnrollment(String enrollment) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT user_id FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, enrollment);

            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FXMLScreenLoader.showMessage("Enrollment number already exists.", "enrollment", "error");
        return false;
    }
    public static boolean checkEnrollment(String enrollment) {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        if (!checkDuplicateEnrollment(enrollment)) {
            return false;
        }
        if (enrollment == null || enrollment.isEmpty()) {
            FXMLScreenLoader.showMessage("Enrollment number cannot be empty.", "enrollment", "error");
            return false;
        }

        for (int i = 0; i < enrollment.length(); i++) {
            char c = enrollment.charAt(i);
            if (c < '0' || c > '9') {
                FXMLScreenLoader.showMessage("Enrollment number must contain only digits (0-9).", "enrollment", "error");
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
        ClubDAO clubDAO=new ClubDAO();
        if(clubDAO.checkClubNameExist(clubName))
            return checkName(clubName) ;
        FXMLScreenLoader.showMessage("Club name already exists.", "clubName", "error");
        return false;
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
            FXMLScreenLoader.showMessage("Maximum members cannot be empty.", "maxMembers", "error");
            return false;
        }

        for (int i = 0; i < maxMembers.length(); i++) {
            char c = maxMembers.charAt(i);
            if (c < '0' || c > '9') {
                FXMLScreenLoader.showMessage("Maximum members must contain only digits (0-9).", "maxMembers", "error");
                return false; // Found a non-digit character
            }
        }
        return true;
    }

    public static boolean checkNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            FXMLScreenLoader.showMessage("Input must be a valid number.", "number", "error");
            return false;
        }
    }

    public static boolean checkEventTime(LocalTime startTime, LocalTime endTime) {
    /*
         Validates that the start time of an event is earlier than the end time.
        -> Returns true if startTime is before endTime.
        -> Returns false otherwise (including if they are equal).
    */
        if (startTime == null || endTime == null) {
            FXMLScreenLoader.showMessage("Start time and end time cannot be null.", "time", "error");
            return false;
        }
        return startTime.isBefore(endTime);
    }

    public static boolean dateValidator(LocalDate inputDate) {
        /**
         * Checks if the given date is in the future or present.
         * Returns true if the input has not passed yet.
         */
        if (inputDate == null) {
            FXMLScreenLoader.showMessage("Date cannot be null.", "date", "error");
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        return !inputDate.isBefore(currentDate);  // Valid if now or in future
    }
}
