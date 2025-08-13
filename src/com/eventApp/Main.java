package com.eventApp;

import com.eventApp.Service.StudentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the CampusConnect application.
 * 
 * CampusConnect is a comprehensive campus event management system that provides
 * a platform for students, club members, and administrators to organize and
 * participate in campus activities.
 * 
 * Key Features:
 * - Student event registration and club membership
 * - Club event creation and management
 * - Administrative approval workflows
 * - Payment processing for events
 * - Role-based access control
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class Main extends Application {
    
    /** Default window width for the application */
    private static final int WINDOW_WIDTH = 1400;
    
    /** Default window height for the application */
    private static final int WINDOW_HEIGHT = 800;
    
    /** Application title displayed in the window title bar */
    private static final String APP_TITLE = "Campus Connect";
    
    /**
     * Main method - entry point for the JavaFX application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Starting CampusConnect Application...");
        launch(args);
    }

    /**
     * Starts the JavaFX application by loading the login screen.
     * 
     * This method is called by the JavaFX framework and initializes the primary
     * stage with the login interface. It loads the FXML file for the login page,
     * sets up the scene, and displays the main application window.
     * 
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set
     * @throws Exception if the FXML file cannot be loaded or other initialization errors occur
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the login page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/Loginpage.fxml"));
            Parent root = loader.load();
            
            // Configure the primary stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            primaryStage.setResizable(true);
            primaryStage.show();
            
            System.out.println("CampusConnect Application started successfully.");
            
        } catch (Exception e) {
            System.err.println("Failed to start CampusConnect Application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
