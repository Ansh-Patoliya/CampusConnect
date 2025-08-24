package com.eventApp.controller;

import com.eventApp.model.Student;
import com.eventApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Spring Boot Web Controller for Student operations.
 * 
 * This replaces the JavaFX StudentController and provides:
 * - Web-based interface using Thymeleaf templates
 * - RESTful endpoints for student operations
 * - Proper HTTP request/response handling
 * - Form validation and error handling
 * 
 * Changes from original JavaFX StudentController:
 * - Replaced JavaFX event handlers with HTTP request mappings
 * - Replaced FXML UI elements with Thymeleaf model attributes
 * - Added proper web-based navigation and form handling
 * - Uses Spring's dependency injection instead of manual instantiation
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Display student dashboard.
     * Replaces the JavaFX initialize() method.
     */
    @GetMapping("/dashboard")
    public String studentDashboard(Model model) {
        // TODO: Get current user from Spring Security context
        // Student student = studentService.getStudentByUser(getCurrentUser());
        
        // For now, create a placeholder
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Student Dashboard");
        
        return "student/dashboard";
    }

    /**
     * Display student profile.
     * Replaces the JavaFX loadStudentProfile() method.
     */
    @GetMapping("/profile")
    public String studentProfile(@RequestParam(required = false) String userId, Model model) {
        if (userId == null) {
            // TODO: Get current user from Spring Security context
            // userId = getCurrentUser().getUserId();
            userId = "placeholder"; // For demo purposes
        }
        
        Student student = studentService.getStudentByUserId(userId);
        if (student == null) {
            model.addAttribute("error", "Student not found");
            return "error/404";
        }
        
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Student Profile");
        
        return "student/profile";
    }

    /**
     * Display form to edit student profile.
     */
    @GetMapping("/profile/edit")
    public String editProfileForm(@RequestParam(required = false) String userId, Model model) {
        if (userId == null) {
            // TODO: Get current user from Spring Security context
            userId = "placeholder"; // For demo purposes
        }
        
        Student student = studentService.getStudentByUserId(userId);
        if (student == null) {
            model.addAttribute("error", "Student not found");
            return "error/404";
        }
        
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Profile");
        
        return "student/edit-profile";
    }

    /**
     * Handle student profile update.
     */
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("student") Student student,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/edit-profile";
        }
        
        try {
            studentService.updateStudent(student);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        
        return "redirect:/students/profile?userId=" + student.getUserId();
    }

    /**
     * Display list of all students (admin/staff view).
     */
    @GetMapping("/list")
    public String listStudents(@RequestParam(required = false) String department,
                             @RequestParam(required = false) Integer semester,
                             Model model) {
        List<Student> students;
        
        if (department != null && !department.trim().isEmpty()) {
            students = studentService.getStudentsByDepartment(department);
            model.addAttribute("filterType", "Department: " + department);
        } else if (semester != null && semester > 0) {
            students = studentService.getStudentsBySemester(semester);
            model.addAttribute("filterType", "Semester: " + semester);
        } else {
            students = studentService.getAllStudents();
            model.addAttribute("filterType", "All Students");
        }
        
        model.addAttribute("students", students);
        model.addAttribute("pageTitle", "Students List");
        
        return "student/list";
    }

    /**
     * Display student registration form.
     */
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Student Registration");
        
        return "student/register";
    }

    /**
     * Handle student registration.
     */
    @PostMapping("/register")
    public String registerStudent(@Valid @ModelAttribute("student") Student student,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/register";
        }
        
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student registered successfully!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "student/register";
        }
    }

    /**
     * Delete student (admin operation).
     */
    @PostMapping("/delete/{userId}")
    public String deleteStudent(@PathVariable String userId,
                              RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(userId);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete student: " + e.getMessage());
        }
        
        return "redirect:/students/list";
    }

    /**
     * AJAX endpoint to get student details.
     */
    @GetMapping("/api/{userId}")
    @ResponseBody
    public Student getStudentDetails(@PathVariable String userId) {
        return studentService.getStudentByUserId(userId);
    }

    /**
     * AJAX endpoint to get students by department.
     */
    @GetMapping("/api/department/{department}")
    @ResponseBody
    public List<Student> getStudentsByDepartment(@PathVariable String department) {
        return studentService.getStudentsByDepartment(department);
    }

    // TODO: Implement when Spring Security is set up
    /*
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    */
}