package com.eventApp.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

/**
 * REST Controller for authentication operations.
 * Replaces JavaFX login functionality with REST API endpoints.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    /**
     * Health check endpoint to verify the API is working.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "CampusConnect API is running");
        return ResponseEntity.ok(response);
    }

    /**
     * Login endpoint to authenticate users.
     * Replaces the JavaFX login functionality.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        // TODO: Implement actual authentication logic using UserService
        // For now, return a simple response to test the endpoint
        
        response.put("success", true);
        response.put("message", "Login endpoint working");
        response.put("user", Map.of(
            "email", loginRequest.getEmail(),
            "role", "student" // Placeholder
        ));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Registration endpoint for new users.
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationRequest registrationRequest) {
        Map<String, String> response = new HashMap<>();
        
        // TODO: Implement actual registration logic using UserService
        
        response.put("message", "Registration endpoint working");
        response.put("status", "success");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Data Transfer Object for login requests.
     */
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    /**
     * Data Transfer Object for registration requests.
     */
    public static class RegistrationRequest {
        private String name;
        private String email;
        private String password;
        private String role;
        private String department;
        private Integer semester;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public Integer getSemester() { return semester; }
        public void setSemester(Integer semester) { this.semester = semester; }
    }
}