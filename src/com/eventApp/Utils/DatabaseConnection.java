package com.eventApp.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections in the CampusConnect system.
 * Provides a centralized method to obtain PostgreSQL database connections
 * with configured credentials and connection parameters.
 */
public class DatabaseConnection {
    /**
     * Establishes and returns a connection to the PostgreSQL database.
     * Uses hardcoded credentials for the CampusConnect database.
     * 
     * @return Connection object to the CampusConnect database
     * @throws SQLException if database connection fails
     * @throws ClassNotFoundException if PostgreSQL driver is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/CampusConnect",
                    "postgres", "Ap1420@810");
    }
}
