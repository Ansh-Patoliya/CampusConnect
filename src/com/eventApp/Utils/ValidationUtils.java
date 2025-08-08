package com.eventApp.Utils;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;

/*
    -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
    -> This method ignores spaces, digits, and special characters — it returns false if any are present.
    -> Use this for fields like name, department, or club name where only letters are allowed.
 */
public class ValidationUtils {

    public static void checkName(String name) throws ValidationException {
    /*
        -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
        -> This method ignores spaces, digits, and special characters — it returns false if any are present.
    */
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                throw new ValidationException("Name can only contain letters.");
            }
        }
    }

    public static void checkEmail(String email) throws ValidationException {
        UserDAO.checkDuplicateEmail(email);

        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }
        // ->@ count exactly 1
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {
            throw new ValidationException("Email must contain exactly one '@' character.");
        }
        /*
            ->Valid Position of @
                ->The @ should not be:
                ->At the start of the email.
                ->At the end of the email.
         */
        if (email.indexOf('@') == 0 || email.lastIndexOf('@') == email.length() - 1) {
            throw new ValidationException("Email cannot start or end with '@'.");
        }
        /*
            ->Valid Domain
                ->The domain part (after @) should contain at least one dot (.)
                ->The dot should not be at the start or end of the domain.
         */
        int dotIndex = email.indexOf('.', email.indexOf('@'));
        if (dotIndex == -1 || dotIndex == email.length() - 1 || dotIndex == email.indexOf('@') + 1) {
            throw new ValidationException("Email domain must contain a dot (.) and cannot start or end with it.");
        }
        /*
            ->minimum domain length
                ->The domain part should be at least 2 characters long (e.g., "com", "org", "in").
         */
        String domain = email.substring(email.indexOf('.') + 1);
        if (domain.length() < 2) {
            throw new ValidationException("Email domain must be at least 2 characters long.");
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
                throw new ValidationException("Email can only contain alphanumeric characters and special characters like . _ - @.");
            }
        }
    }

    public static void checkPassword(String password) throws ValidationException {
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
            throw new ValidationException("Password must be at least 8 characters long.");
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

        if (!(hasDigit && hasLowerCase && hasUpperCase && hasSpecialChar))
            throw new ValidationException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
    }

    public static void isMatchingPasswords(String password, String confirmPassword) throws ValidationException {
        if (!password.equals(confirmPassword))
            throw new ValidationException("Passwords do not match.");
    }


    public static void checkEnrollment(String enrollment) throws ValidationException {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        UserDAO.checkDuplicateEnrollment(enrollment);

        if (enrollment == null || enrollment.isEmpty())
            throw new ValidationException("Enrollment number cannot be empty.");

        for (int i = 0; i < enrollment.length(); i++) {
            char c = enrollment.charAt(i);
            if (c < '0' || c > '9')
                throw new ValidationException("Enrollment number must contain only digits (0-9).");
        }
    }

    public static void checkClubName(String clubName) throws ValidationException {
        /*
            -> Validates that the given input string contains only alphabetic letters (A-Z, a-z).
            -> This method ignores spaces, digits, and special characters — it returns false if any are present.
        */
        ClubDAO clubDAO = new ClubDAO();
        clubDAO.checkClubNameExist(clubName);
        checkName(clubName);
    }

    public static void checkDescription(String description) throws ValidationException {
        /*
            -> Validates that the given input string is not empty.
            -> This method returns false if the input is null or contains only whitespace.
        */
        if (description == null) {
            throw new ValidationException("Description cannot be empty.");
        }
    }

    public static void checkCategory(String category) throws ValidationException {
        /*
            -> Validates that the given input string is not empty.
            -> This method returns false if the input is null or contains only whitespace.
        */
        if (category == null) {
            throw new ValidationException("Category cannot be empty.");
        }
    }

    public static void checkEventTime(LocalTime startTime, LocalTime endTime) throws ValidationException {
    /*
         Validates that the start time of an event is earlier than the end time.
        -> Returns true if startTime is before endTime.
        -> Returns false otherwise (including if they are equal).
    */
        if (startTime == null || endTime == null)
            throw new ValidationException("Start time and end time cannot be null.");

        if (!startTime.isBefore(endTime))
            throw new ValidationException("Start time must be earlier than end time.");
    }

    public static void dateValidator(LocalDate inputDate) throws ValidationException {
        /**
         * Checks if the given date is in the future or present.
         * Returns true if the input has not passed yet.
         */
        if (inputDate == null)
            throw new ValidationException("Date cannot be null.");

        LocalDate currentDate = LocalDate.now();
        if (!inputDate.isBefore(currentDate))
            throw new ValidationException("Date must be in the future or present.");
    }
}
