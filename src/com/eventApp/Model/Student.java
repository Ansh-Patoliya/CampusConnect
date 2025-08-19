package com.eventApp.Model;

import java.util.List;

/**
 * Represents a student user in the Event App.
 * Extends the base User class by adding academic details and interests.
 */
public class Student extends User {

    private String department;        // Academic department of the student
    private int semester;             // Current semester number the student is enrolled in
    private List<String> interest;    // List of student's interests or areas of focus

    /**
     * Full constructor for Student including user credentials and academic details.
     *
     * @param userId     Unique identifier for the user
     * @param name       Name of the student
     * @param email      Email address of the student
     * @param password   Password for login authentication
     * @param role       Role assigned to the user (e.g., "Student")
     * @param department Academic department the student belongs to
     * @param semester   Current semester number
     * @param interest   List of interests or specializations
     */
    public Student(String userId, String name, String email, String password, String role, String department, int semester, List<String> interest) {
        super(userId, name, email, password, role);  // Initialize User superclass constructor
        this.department = department;
        this.semester = semester;
        this.interest = interest;
    }

    /**
     * Retrieves the academic department of the student.
     *
     * @return department string
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Retrieves the current semester number the student is enrolled in.
     *
     * @return semester number
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Retrieves the list of interests associated with the student.
     *
     * @return list of interest strings
     */
    public List<String> getInterest() {
        return interest;
    }

}
