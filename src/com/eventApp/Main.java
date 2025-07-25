package com.eventApp;

import com.eventApp.Service.StudentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println();
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/Loginpage.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Campus Connect");
            primaryStage.setScene(new Scene(root, 1400, 800));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
