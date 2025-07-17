package com.eventApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/EventRegistrationForm.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Campus Connect");
            primaryStage.setScene(new Scene(root, 1400, 800));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
