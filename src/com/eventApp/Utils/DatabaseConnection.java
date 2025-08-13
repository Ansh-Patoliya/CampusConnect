package com.eventApp.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class for the CampusConnect system.
 * 
 * This utility class provides centralized database connection management
 * for the CampusConnect application. It handles PostgreSQL database
 * connections using JDBC and ensures consistent connection parameters
 * across the application.
 * 
 * Database Configuration:
 * - Database: PostgreSQL
 * - Default Host: localhost
 * - Default Port: 5432
 * - Database Name: CampusConnect
 * 
 * Security Note: In production environments, database credentials should
 * be externalized to environment variables or configuration files rather
 * than being hardcoded in the source code.
 * 
 * Usage Example:
 * <pre>
 * try (Connection conn = DatabaseConnection.getConnection()) {
 *     // Perform database operations
 * } catch (SQLException | ClassNotFoundException e) {
 *     // Handle connection errors
 * }
 * </pre>
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class DatabaseConnection {
    
    /** PostgreSQL JDBC driver class name */
    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    
    /** Database URL for PostgreSQL connection */
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/CampusConnect";
    
    /** Database username - should be externalized in production */
    private static final String DATABASE_USERNAME = "postgres";
    
    /** Database password - should be externalized in production */
    private static final String DATABASE_PASSWORD = "Ap1420@810";

    /**
     * Establishes and returns a connection to the CampusConnect database.
     * 
     * This method creates a new database connection each time it's called.
     * It loads the PostgreSQL JDBC driver and establishes a connection
     * using the configured database parameters.
     * 
     * Important: Callers are responsible for closing the connection to
     * prevent resource leaks. It's recommended to use try-with-resources
     * blocks when using this method.
     * 
     * @return Connection object representing the database connection
     * @throws SQLException if a database access error occurs or the connection cannot be established
     * @throws ClassNotFoundException if the PostgreSQL JDBC driver is not found in the classpath
     * 
     * @see Connection
     * @see DriverManager#getConnection(String, String, String)
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the PostgreSQL JDBC driver
        Class.forName(DRIVER_CLASS);
        
        // Establish and return the database connection
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }
    
    /**
     * Tests the database connection to verify connectivity.
     * 
     * This method can be used for health checks or during application
     * startup to ensure the database is accessible.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
