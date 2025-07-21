package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentDashboardController {

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleViewProfile(ActionEvent event) {
        if (currentUser != null) {
            FXMLScreenLoader.openStudentProfile(currentUser);
        }
    }

}
