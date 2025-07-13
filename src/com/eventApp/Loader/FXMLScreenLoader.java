package com.eventApp.Loader;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLScreenLoader {
    public static void openLoginPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/LoginPAge.fxml"));
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
}
