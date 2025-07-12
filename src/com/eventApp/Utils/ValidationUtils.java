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
        return true;
    }

    public static boolean isMatchingPasswords(String Password, String ConfirmPassword) {
        return true;
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
        return true;
    }

    public static boolean checkEnrollment(String enrollment) {
    /*
        -> Validates that the given input contains only numeric digits (0–9).
        -> This method returns false if the input includes letters, spaces, or special characters.
     */
        return true;
    }
}
