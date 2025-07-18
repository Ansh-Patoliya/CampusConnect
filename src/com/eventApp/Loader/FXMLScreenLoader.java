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

    public static void openEventRegistration(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/EventRegistrationForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openAdminDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openClubApproval(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/ClubApproval.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Club Approval");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message, String title,String type) {
        Alert alert;
        if (type.equalsIgnoreCase("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (type.equalsIgnoreCase("info")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(title);
        alert.setHeaderText(null); // No header
        alert.setContentText(message);
        alert.showAndWait();
    }
}
