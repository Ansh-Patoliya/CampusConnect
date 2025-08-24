package com.eventApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.ArrayList;

/**
 * JPA Entity representing a student user in the Event App.
 * Extends the base User class by adding academic details and interests.
 * 
 * This is the Spring Boot/JPA version of the original Student class.
 * Changes from original:
 * - Added JPA annotations for database mapping
 * - Added validation annotations
 * - Added proper handling of interests list with ElementCollection
 * - Added default constructor for JPA
 */
@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

    @NotBlank(message = "Department is required")
    @Column(name = "department", nullable = false, length = 100)
    private String department;        // Academic department of the student
    
    @Positive(message = "Semester must be a positive number")
    @Min(value = 1, message = "Semester must be at least 1")
    @Column(name = "semester", nullable = false)
    private int semester;             // Current semester number the student is enrolled in
    
    @ElementCollection
    @CollectionTable(name = "student_interests", 
                    joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "interest")
    private List<String> interest = new ArrayList<>();    // List of student's interests or areas of focus

    /**
     * Default constructor required by JPA
     */
    public Student() {
        super();
    }

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
    public Student(String userId, String name, String email, String password, String role, 
                   String department, int semester, List<String> interest) {
        super(userId, name, email, password, role);  // Initialize User superclass constructor
        this.department = department;
        this.semester = semester;
        this.interest = interest != null ? new ArrayList<>(interest) : new ArrayList<>();
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
     * Sets the academic department of the student.
     *
     * @param department Academic department to set
     */
    public void setDepartment(String department) {
        this.department = department;
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
     * Sets the current semester number.
     *
     * @param semester Semester number to set
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Retrieves the list of interests associated with the student.
     *
     * @return list of interest strings
     */
    public List<String> getInterest() {
        return interest;
    }

    /**
     * Sets the list of interests for the student.
     *
     * @param interest List of interests to set
     */
    public void setInterest(List<String> interest) {
        this.interest = interest != null ? new ArrayList<>(interest) : new ArrayList<>();
    }

    /**
     * Adds an interest to the student's interest list.
     *
     * @param newInterest Interest to add
     */
    public void addInterest(String newInterest) {
        if (this.interest == null) {
            this.interest = new ArrayList<>();
        }
        if (!this.interest.contains(newInterest)) {
            this.interest.add(newInterest);
        }
    }

    /**
     * Removes an interest from the student's interest list.
     *
     * @param interestToRemove Interest to remove
     */
    public void removeInterest(String interestToRemove) {
        if (this.interest != null) {
            this.interest.remove(interestToRemove);
        }
    }
}