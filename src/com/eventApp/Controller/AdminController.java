package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Admin;
import com.eventApp.Service.AdminService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController {

    private String currentUserId;  // Store just userId (better than entire object)
    private final AdminService adminService = new AdminService();

    public void setUserId(String userId) {
        this.currentUserId = userId;
    }

    public void handleViewProfile(ActionEvent event) {
        if (currentUserId == null) return;

        // Fetch fresh admin data from DB
        Admin admin = adminService.getAdmin(currentUserId);
        if (admin == null) {
            System.out.println("Admin not found");
            return;
        }

        Label nameLabel = new Label("Name: " + admin.getName());
        Label emailLabel = new Label("Email: " + admin.getEmail());
        Label roleLabel = new Label("Role: " + admin.getRole());

        nameLabel.setStyle("-fx-font-size: 18;");
        emailLabel.setStyle("-fx-font-size: 18;");
        roleLabel.setStyle("-fx-font-size: 18;");

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> ((Stage) closeBtn.getScene().getWindow()).close());
        closeBtn.setStyle("-fx-font-size: 16; -fx-background-color: #6a3093; -fx-text-fill: white;");

        VBox root = new VBox(20, nameLabel, emailLabel, roleLabel, closeBtn);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        Stage profileStage = new Stage();
        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.setTitle("Admin Profile");
        profileStage.setScene(new Scene(root, 400, 300));
        profileStage.showAndWait();
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
