package com.eventApp;

import com.eventApp.Utils.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {

    /**
     * The main entry point of the application.
     * Before launching the JavaFX UI, it executes two stored procedures:
     * 1. event_complete() - presumably to update event completion statuses.
     * 2. apply_ticket_price_rules() - presumably to apply ticket pricing logic.
     *
     * @param args Command line arguments
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If the JDBC driver class is not found
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Establish connection to the database
        Connection connection = DatabaseConnection.getConnection();

        // Prepare and execute stored procedure to update event completion statuses
        PreparedStatement preparedStatement1 = connection.prepareCall("call event_complete()");
        preparedStatement1.executeUpdate();

        // Prepare and execute stored procedure to apply ticket pricing rules
        PreparedStatement preparedStatement2 = connection.prepareCall("call apply_ticket_price_rules()");
        preparedStatement2.executeUpdate();

        // Launch the JavaFX application (calls start method)
        launch(args);
    }

    /**
     * This method is called after launch() and is responsible for
     * initializing and displaying the primary stage of the JavaFX application.
     *
     * @param primaryStage The main window (stage) for the application
     */
    @Override
    public void start(Stage primaryStage){
        try {
            // Load the Login page layout from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/Loginpage.fxml"));
            Parent root = loader.load();

            // Set the title of the application window
            primaryStage.setTitle("Campus Connect");

            // Set the scene with the loaded layout and fixed size
            primaryStage.setScene(new Scene(root, 1400, 800));

            // Show the application window
            primaryStage.show();
        } catch (Exception e) {
            // Print stack trace if any exception occurs during UI loading/display
            e.printStackTrace();
        }
    }
}
