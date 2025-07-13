package com.eventApp.Loader;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLScreenLoader {
    public static void openLoginPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/Loginpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openSignupPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/registration.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openForgotPassword(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/ForgotPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null); // No header
        alert.setContentText(message);
        alert.showAndWait();
    }
}
