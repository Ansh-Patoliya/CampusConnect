package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Service.ClubApprovalService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubApprovalController {

    @FXML
    private Label clubName;

    @FXML
    private Label description;

    @FXML
    private Label createdBy;

    @FXML
    private Label createdAt;


    private final ClubApprovalService clubApprovalService = new ClubApprovalService();

    private Club currentClub;

    @FXML
    public void initialize() {
        Platform.runLater(this::loadNextClub);
    }

    private void loadNextClub() {
        currentClub = clubApprovalService.viewNextPendingClub();

        if (currentClub != null) {
            clubName.setText(currentClub.getClubName());
            description.setText(currentClub.getDescriptions());
            createdBy.setText(currentClub.getFounderId());
            createdAt.setText(currentClub.getCategory());

            clubName.setVisible(true);
            description.setVisible(true);
            createdBy.setVisible(true);
            createdAt.setVisible(true);
        } else {
            FXMLScreenLoader.showMessage("No more clubs to approve.", "Club Approval","info");
            clearLabels();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/AdminDashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) clubName.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onApprove() {
        if (clubApprovalService.approveNextClub()) {
            FXMLScreenLoader.showMessage("Club approved successfully.", "Club Approval", "info");
        } else {
            FXMLScreenLoader.showMessage("Error approving club.", "Club Approval", "error");
        }
        loadNextClub();
    }

    @FXML
    private void onReject() {
        if (clubApprovalService.rejectNextClub()) {
            FXMLScreenLoader.showMessage("Club rejected successfully.", "Club Approval", "info");
        } else {
            FXMLScreenLoader.showMessage("Error rejecting club.", "Club Approval", "error");
        }
        loadNextClub();
    }

    @FXML
    private void onBack(ActionEvent event) {
        // You can implement navigation back to dashboard here
        FXMLScreenLoader.openAdminDashboard(event); // Assuming this method opens the dashboard or previous screen
    }

    private void clearLabels() {
        clubName.setText("");
        description.setText("");
        createdBy.setText("");
        createdAt.setText("");

        clubName.setVisible(false);
        description.setVisible(false);
        createdBy.setVisible(false);
        createdAt.setVisible(false);
    }

}