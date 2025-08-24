package com.eventApp.service;

import com.eventApp.model.Event;
import com.eventApp.model.Student;
import com.eventApp.model.User;
import java.util.List;

/**
 * Interface for student related service operations.
 * Defines methods to retrieve student data, navigate event lists,
 * and access registered or sorted events.
 * 
 * This is the Spring Boot version of the original StudentService interface.
 * Changes from original:
 * - Removed MyEventLL dependency (will be replaced with List<Event>)
 * - Added additional methods for better Spring Boot integration
 * - Maintained the same core functionality
 */
public interface StudentService {

    /**
     * Retrieves a Student object corresponding to the given User.
     *
     * @param user The User object to look up
     * @return The matching Student object
     */
    Student getStudentByUser(User user);

    /**
     * Retrieves a Student object by user ID.
     *
     * @param userId The user ID to look up
     * @return The matching Student object or null if not found
     */
    Student getStudentByUserId(String userId);

    /**
     * Sorts the list of approved and not completed events for the current user.
     * The sorting prioritizes events matching user's interests first,
     * then sorts events by their date.
     *
     * @return A list of sorted events
     */
    List<Event> sortEventList();

    /**
     * Sorts events for a specific student based on their interests.
     *
     * @param studentId The student ID
     * @return A list of sorted events for the student
     */
    List<Event> sortEventListForStudent(String studentId);

    /**
     * Loads the student's event list by sorting events and storing in session.
     */
    void loadStudentEvents();

    /**
     * Retrieves the current event from the student's event list.
     *
     * @return The current Event or null if the list is empty
     */
    Event viewCurrentEvent();

    /**
     * Retrieves the next event from the student's event list.
     *
     * @return The next Event or null if the list is empty
     */
    Event getNextEvent();

    /**
     * Retrieves the previous event from the student's event list.
     *
     * @return The previous Event or null if the list is empty
     */
    Event getPreviousEvent();

    /**
     * Returns the list of events the student is registered for, sorted by event date.
     *
     * @param userId The student's user ID
     * @return List of Events sorted by date
     */
    List<Event> myEventListByDate(String userId);

    /**
     * Saves a new student to the database.
     *
     * @param student The student to save
     * @return The saved student
     */
    Student saveStudent(Student student);

    /**
     * Updates an existing student in the database.
     *
     * @param student The student to update
     * @return The updated student
     */
    Student updateStudent(Student student);

    /**
     * Deletes a student from the database.
     *
     * @param userId The user ID of the student to delete
     */
    void deleteStudent(String userId);

    /**
     * Gets all students in a specific department.
     *
     * @param department The department name
     * @return List of students in the department
     */
    List<Student> getStudentsByDepartment(String department);

    /**
     * Gets all students in a specific semester.
     *
     * @param semester The semester number
     * @return List of students in the semester
     */
    List<Student> getStudentsBySemester(int semester);

    /**
     * Gets all students.
     *
     * @return List of all students
     */
    List<Student> getAllStudents();
}