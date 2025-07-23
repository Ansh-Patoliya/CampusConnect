package com.eventApp.Controller;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StudentDashboardController {

    StudentDAO studentDAO;
    public Button eventHistoryButton;

    @FXML
    private void handleViewProfile(ActionEvent event) {
            FXMLScreenLoader.openStudentProfile(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleViewEventHistory(ActionEvent actionEvent) {
    }

    public void handleViewEvent(ActionEvent event) {
        FXMLScreenLoader.openStudentViewEvent(event);
    }
}
