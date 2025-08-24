package com.eventApp.repository;

import com.eventApp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Student entity.
 * 
 * This replaces the original StudentDAO class and provides:
 * - Automatic CRUD operations through JpaRepository
 * - Custom query methods using Spring Data JPA conventions
 * - Type-safe database operations
 * - Automatic transaction management
 * 
 * Changes from original StudentDAO:
 * - No need for manual JDBC connection management
 * - Automatic implementation of basic CRUD operations
 * - Query methods are automatically implemented by Spring Data
 * - Exception handling is managed by Spring
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    /**
     * Find student by email address.
     * Replaces the database query from original DAO.
     *
     * @param email Email address to search for
     * @return Optional containing the student if found
     */
    Optional<Student> findByEmail(String email);
    
    /**
     * Find students by department.
     *
     * @param department Department name to search for
     * @return List of students in the specified department
     */
    List<Student> findByDepartment(String department);
    
    /**
     * Find students by semester.
     *
     * @param semester Semester number to search for
     * @return List of students in the specified semester
     */
    List<Student> findBySemester(int semester);
    
    /**
     * Find students by department and semester.
     *
     * @param department Department name
     * @param semester Semester number
     * @return List of students matching both criteria
     */
    List<Student> findByDepartmentAndSemester(String department, int semester);
    
    /**
     * Check if a student exists by user ID.
     * Replaces the existence check from original DAO.
     *
     * @param userId User ID to check
     * @return true if student exists, false otherwise
     */
    boolean existsByUserId(String userId);
    
    /**
     * Custom query to find students with specific interests.
     * This replaces the complex interest matching logic from the original DAO.
     *
     * @param interests List of interests to match
     * @return List of students having any of the specified interests
     */
    @Query("SELECT DISTINCT s FROM Student s JOIN s.interest i WHERE i IN :interests")
    List<Student> findStudentsWithInterests(@Param("interests") List<String> interests);
    
    /**
     * Custom query to get interest list for a specific student.
     * This replaces the getInterestList method from original StudentDAO.
     *
     * @param studentId Student ID
     * @return List of interests for the student
     */
    @Query("SELECT s.interest FROM Student s WHERE s.userId = :studentId")
    List<String> findInterestsByStudentId(@Param("studentId") String studentId);
    
    /**
     * Count students by department.
     *
     * @param department Department name
     * @return Number of students in the department
     */
    long countByDepartment(String department);
    
    /**
     * Find students by name containing a keyword (case-insensitive).
     *
     * @param nameKeyword Keyword to search in names
     * @return List of students with names containing the keyword
     */
    List<Student> findByNameContainingIgnoreCase(String nameKeyword);
}