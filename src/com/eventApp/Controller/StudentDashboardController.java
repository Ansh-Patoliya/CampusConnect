package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentDashboardController {

    @FXML
    private void handleViewProfile(ActionEvent event) {
            FXMLScreenLoader.openStudentProfile(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }
}
