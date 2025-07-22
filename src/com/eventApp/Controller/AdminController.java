package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Admin;
import com.eventApp.Model.User;
import com.eventApp.Service.AdminService;
import com.eventApp.Service.StudentService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController {

    private User user;
    private AdminService adminService = new AdminService();
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        // You can perform any additional initialization here if needed
        user= CurrentUser.getCurrentUser() ;// Assuming User class has a method to get the current logged-in user
        loadAdminProfile();
    }

    private void loadAdminProfile() {
        Admin admin = adminService.getAdmin(user);
        if (admin != null) {
            nameLabel.setText(admin.getName());
            emailLabel.setText(admin.getEmail());
        }
    }

    public void onBack(ActionEvent event) {
        // Logic to go back to the previous screen, e.g., Student Dashboard
        // This could be implemented using a method from FXMLScreenLoader or similar
        FXMLScreenLoader.openAdminDashboard(event);
    }

    public void handleClubApproval(ActionEvent event) {
        FXMLScreenLoader.openClubApproval(event);
    }

    public void handleEventApproval(ActionEvent event) {
        FXMLScreenLoader.openEventApproval(event);
    }

    public void handleViewEvents(ActionEvent event) {
        FXMLScreenLoader.openViewEvents(event);
    }

    public void handleBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }
}
