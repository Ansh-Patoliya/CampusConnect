package com.eventApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Spring Boot Web Controller for authentication operations.
 * 
 * This replaces the JavaFX LoginController and provides:
 * - Web-based login form
 * - User authentication (placeholder implementation)
 * - Session management
 * - Redirect to appropriate dashboard based on role
 */
@Controller
public class LoginController {

    /**
     * Display the login page.
     * Replaces the JavaFX login screen.
     */
    @GetMapping({"/", "/login"})
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Login - CampusConnect");
        return "auth/login";
    }

    /**
     * Handle login form submission.
     * Replaces the JavaFX handleSignIn method.
     */
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                            @RequestParam String password,
                            RedirectAttributes redirectAttributes) {
        
        // TODO: Implement actual authentication with Spring Security
        // For now, using placeholder logic
        
        if (email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email is required");
            return "redirect:/login";
        }
        
        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password is required");
            return "redirect:/login";
        }
        
        // Placeholder authentication logic
        if ("admin@campus.edu".equals(email) && "admin123".equals(password)) {
            redirectAttributes.addFlashAttribute("success", "Welcome Admin! Login successful.");
            return "redirect:/admin/dashboard";
        } else if ("student@campus.edu".equals(email) && "student123".equals(password)) {
            redirectAttributes.addFlashAttribute("success", "Welcome Student! Login successful.");
            return "redirect:/students/dashboard";
        } else if ("club@campus.edu".equals(email) && "club123".equals(password)) {
            redirectAttributes.addFlashAttribute("success", "Welcome Club Member! Login successful.");
            return "redirect:/clubs/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }

    /**
     * Display the registration page.
     */
    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("pageTitle", "Register - CampusConnect");
        return "auth/register";
    }

    /**
     * Handle logout.
     */
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        // TODO: Implement with Spring Security
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/login";
    }

    /**
     * Display forgot password page.
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {
        model.addAttribute("pageTitle", "Forgot Password - CampusConnect");
        return "auth/forgot-password";
    }
}