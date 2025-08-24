package com.eventApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Main Application for CampusConnect
 * 
 * This replaces the JavaFX Main.java and provides:
 * - Web-based interface instead of desktop JavaFX
 * - Spring Boot auto-configuration
 * - Dependency injection
 * - Spring Data JPA for database operations
 * - Spring Security for authentication
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eventApp.repository")
@EntityScan(basePackages = "com.eventApp.model")
public class CampusConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusConnectApplication.class, args);
    }
}