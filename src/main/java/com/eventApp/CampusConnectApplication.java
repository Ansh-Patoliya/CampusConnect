package com.eventApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Main Spring Boot Application class for CampusConnect.
 * Replaces the JavaFX Main class and provides REST API functionality.
 */
@SpringBootApplication
public class CampusConnectApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CampusConnectApplication.class, args);
    }

    /**
     * Executes initialization tasks that were previously in the JavaFX main method.
     * This includes calling the event_complete() stored procedure.
     */
    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            // Note: For PostgreSQL, execute the stored procedure
            // For H2 demo, this procedure may not exist, so we'll skip it
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            if ("PostgreSQL".equals(databaseProductName)) {
                PreparedStatement preparedStatement = connection.prepareCall("call event_complete()");
                preparedStatement.executeUpdate();
                System.out.println("Application started successfully and event_complete() procedure executed.");
            } else {
                System.out.println("Application started successfully (demo mode with H2 database).");
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not execute event_complete() procedure: " + e.getMessage());
            System.out.println("Application started successfully in demo mode.");
        }
    }
}