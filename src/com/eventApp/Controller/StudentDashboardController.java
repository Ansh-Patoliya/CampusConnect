package com.eventApp.Controller;

import com.eventApp.DAO.NotificationDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Notification;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;

public class StudentDashboardController {

    public Button eventHistoryButton;
    public Label nameLabel;

    NotificationDAO notificationDAO = new NotificationDAO();
    User currentUser;
    public void initialize() {
        currentUser=CurrentUser.getCurrentUser();
        nameLabel.setText(currentUser.getName());
        Platform.runLater(() -> {
            try {
                List<Notification> unreadNotifications = notificationDAO.getNotifications(currentUser.getUserId());
                showCancellationPopup(unreadNotifications);
            } catch (SQLException | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "Error fetching notifications", "error");
            }
        });

    }

    private void showCancellationPopup(List<Notification> notifications) {
        for (Notification n : notifications) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Cancelled");
            alert.setHeaderText(null);
            alert.setContentText(n.getMessage());
            alert.showAndWait();
            try {
                notificationDAO.markNotificationAsRead(currentUser.getUserId(),n.getId());
            } catch (SQLException | DatabaseExceptionHandler | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "Error marking notification as read", "error");
            }
        }
    }


    @FXML
    private void handleViewProfile(ActionEvent event) {
        FXMLScreenLoader.openStudentProfile(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleViewEventHistory(ActionEvent actionEvent) {
        FXMLScreenLoader.openViewEventHistory(actionEvent);
    }

    public void handleViewEvent(ActionEvent event) {
        FXMLScreenLoader.openStudentViewEvent(event);
    }

    public void handleMyParticipation(ActionEvent actionEvent) {
        FXMLScreenLoader.openMyParticipation(actionEvent);
    }
}
