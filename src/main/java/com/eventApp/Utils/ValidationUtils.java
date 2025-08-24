package com.eventApp.Utils;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.ValidationException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Utility class providing static methods for validating various inputs used throughout the system.
 * Includes validations for names, emails, passwords, enrollment numbers, club names, descriptions, categories,
 * event times, and dates.
 */
public class ValidationUtils {

    /**
     * Validates that the given name contains only alphabetic letters and spaces.
     * Throws ValidationException if the name is null, empty, or contains invalid characters.
     *
     * @param name the input name string to validate
     * @throws ValidationException if the validation fails
     */
    public static void checkName(String name) throws ValidationException {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && (c != ' ')) {
                throw new ValidationException("Name can only contain letters.");
            }
        }
    }

    /**
     * Validates the given email string according to standard email format rules.
     * Also checks for duplicate email entries in the system via UserDAO.
     *
     * @param email the email string to validate
     * @throws ValidationException        if the email format is invalid or empty
     * @throws SQLException               if a database error occurs during duplication check
     * @throws ClassNotFoundException     if database driver is not found
     */
    public static void checkEmail(String email) throws ValidationException, SQLException, ClassNotFoundException {
        UserDAO.checkDuplicateEmail(email);

        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }

        // Ensure exactly one '@' character is present
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {
            throw new ValidationException("Email must contain exactly one '@' character.");
        }

        // '@' cannot be the first or last character
        if (email.indexOf('@') == 0 || email.lastIndexOf('@') == email.length() - 1) {
            throw new ValidationException("Email cannot start or end with '@'.");
        }

        // Domain must contain a '.' which is neither at start nor end of the domain
        int dotIndex = email.indexOf('.', email.indexOf('@'));
        if (dotIndex == -1 || dotIndex == email.length() - 1 || dotIndex == email.indexOf('@') + 1) {
            throw new ValidationException("Email domain must contain a dot (.) and cannot start or end with it.");
        }

        // Domain length after '.' must be at least 2 characters
        String domain = email.substring(email.indexOf('.') + 1);
        if (domain.length() < 2) {
            throw new ValidationException("Email domain must be at least 2 characters long.");
        }

        // Email should contain only valid characters: alphanumeric and . _ - @
        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') &&
                    c != '.' && c != '_' && c != '-' && c != '@') {
                throw new ValidationException("Email can only contain alphanumeric characters and special characters like . _ - @.");
            }
        }
    }

    /**
     * Validates password strength according to defined criteria:
     * minimum length of 8, at least one uppercase, one lowercase, one digit, and one special character.
     *
     * @param password the password string to validate
     * @throws ValidationException if password does not meet the criteria
     */
    public static void checkPassword(String password) throws ValidationException {
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

        if (!(hasDigit && hasLowerCase && hasUpperCase && hasSpecialChar)) {
            throw new ValidationException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
    }

    /**
     * Checks if the given password and confirmation password match exactly.
     *
     * @param password        original password
     * @param confirmPassword confirmation password to compare
     * @throws ValidationException if passwords do not match
     */
    public static void isMatchingPasswords(String password, String confirmPassword) throws ValidationException {
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match.");
        }
    }

    /**
     * Validates enrollment number to ensure it is not empty and contains only alphanumeric characters.
     * Also checks for duplicate enrollment number via UserDAO.
     *
     * @param enrollment enrollment number string to validate
     * @throws ValidationException        if invalid format or empty
     * @throws SQLException               if a database error occurs during duplication check
     * @throws ClassNotFoundException     if database driver is not found
     */
    public static void checkEnrollment(String enrollment) throws ValidationException, SQLException, ClassNotFoundException {
        UserDAO.checkDuplicateEnrollment(enrollment);

        if (enrollment == null || enrollment.isEmpty()) {
            throw new ValidationException("Enrollment number cannot be empty.");
        }

        // Only alphanumeric characters allowed
        for (int i = 0; i < enrollment.length(); i++) {
            char c = enrollment.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9')) {
                throw new ValidationException("Enrollment number can only contain alphanumeric characters.");
            }
        }
    }

    /**
     * Validates club name by checking if it already exists and ensuring it contains only letters and spaces.
     *
     * @param clubName the club name string to validate
     * @throws ValidationException if validation fails
     */
    public static void checkClubName(String clubName) throws ValidationException {
        ClubDAO clubDAO = new ClubDAO();
        clubDAO.checkClubNameExist(clubName);  // Check duplication
        checkName(clubName);                    // Check valid characters
    }

    /**
     * Validates that the given description is not null.
     *
     * @param description description string to validate
     * @throws ValidationException if null
     */
    public static void checkDescription(String description) throws ValidationException {
        if (description == null) {
            throw new ValidationException("Description cannot be empty.");
        }
    }

    /**
     * Validates that an event's start time is strictly before its end time.
     *
     * @param startTime event start time
     * @param endTime   event end time
     * @throws ValidationException if start time is null, end time is null, or start time is not before end time
     */
    public static void checkEventTime(LocalTime startTime, LocalTime endTime) throws ValidationException {
        if (startTime == null || endTime == null) {
            throw new ValidationException("Start time and end time cannot be null.");
        }

        if (!startTime.isBefore(endTime)) {
            throw new ValidationException("Start time must be earlier than end time.");
        }
    }

    /**
     * Validates that the given date is not null and not in the past.
     *
     * @param inputDate the date to validate
     * @throws ValidationException if date is null or before the current date
     */
    public static void dateValidator(LocalDate inputDate) throws ValidationException {
        if (inputDate == null) {
            throw new ValidationException("Date cannot be null.");
        }

        LocalDate currentDate = LocalDate.now();
        if (inputDate.isBefore(currentDate)) {
            throw new ValidationException("Date cannot be in the past.");
        }
    }
}
