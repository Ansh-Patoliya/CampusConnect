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
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareCall("call event_complete()");
        preparedStatement.executeUpdate();
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
