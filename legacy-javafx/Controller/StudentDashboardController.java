package com.eventApp.Controller;

import com.eventApp.DAO.NotificationDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Notification;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.List;

public class StudentDashboardController {

    public Button eventHistoryButton;
    public Button viewEventButton;
    public Button myParticipationButton;
    public Button viewMyProfileButton;
    public Button backButton;

    public Label nameLabel;

    NotificationDAO notificationDAO = new NotificationDAO();
    User currentUser;

    public void initialize() {
        currentUser = CurrentUser.getCurrentUser();
        nameLabel.setText(currentUser.getName());

        // ✅ Apply animation to all dashboard buttons
        addButtonAnimation(viewEventButton);
        addButtonAnimation(eventHistoryButton);
        addButtonAnimation(myParticipationButton);
        addButtonAnimation(viewMyProfileButton);
        addButtonAnimation(backButton);

        // ✅ Notifications
        Platform.runLater(() -> {
            try {
                List<Notification> unreadNotifications = notificationDAO.getNotifications(currentUser.getUserId());
                showCancellationPopup(unreadNotifications);
            } catch (SQLException | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "Error fetching notifications", "error");
            }
        });
    }

    /**
     * Adds hover + click scale animations to a button
     */
    private void addButtonAnimation(Button button) {
        // Hover in -> scale up
        button.setOnMouseEntered(e -> animateScale(button, 1.0, 1.08));
        // Hover out -> scale back
        button.setOnMouseExited(e -> animateScale(button, 1.08, 1.0));
        // Press -> small shrink (click feedback)
        button.setOnMousePressed(e -> animateScale(button, 1.08, 0.95));
        // Release -> back to hover scale
        button.setOnMouseReleased(e -> animateScale(button, 0.95, 1.08));
    }

    private void animateScale(Button button, double from, double to) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }

    private void showCancellationPopup(List<Notification> notifications) {
        for (Notification n : notifications) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Cancelled");
            alert.setHeaderText(null);
            alert.setContentText(n.getMessage());
            alert.showAndWait();
            try {
                notificationDAO.markNotificationAsRead(currentUser.getUserId(), n.getId());
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
