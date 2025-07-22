package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Admin;
import com.eventApp.Model.User;
import com.eventApp.Service.AdminService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminViewProfileController {
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;

    private User user;
    private AdminService adminService = new AdminService();

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
}
