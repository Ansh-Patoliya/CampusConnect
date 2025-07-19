package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Service.ClubApprovalService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        loadNextClub();
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
    private void onBack() {
        // You can implement navigation back to dashboard here
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