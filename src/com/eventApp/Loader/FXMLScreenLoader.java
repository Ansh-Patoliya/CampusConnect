package com.eventApp.Loader;

import com.eventApp.Controller.StudentController;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    public static void openForgotPassword(ActionEvent event) {
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

    public static void openEventRegistration(ActionEvent event) {
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

    public static void openAdminProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/AdminViewProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Profile");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openStudentViewEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/ViewEventsStudent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("View Event");
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

    public static void openEventApproval(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/EventApproval.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Event Approval");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message, String title, String type) {
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

    public static void openViewEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/ViewEvents.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("View Events");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openClubDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/ClubDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Club Dashboard");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openStudentProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/StudentViewProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Student Profile");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void openStudentDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLScreenLoader.class.getResource("/com/eventApp/FXML/StudentDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Student Dashboard");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
