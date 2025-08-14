package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentDashboardController {

    public Button eventHistoryButton;
    public Label nameLabel;

    public void initialize() {
        User currentUser = CurrentUser.getCurrentUser();
        nameLabel.setText(currentUser.getName());
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
