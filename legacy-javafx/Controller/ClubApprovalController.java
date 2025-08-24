package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Service.ClubApprovalService;
import com.eventApp.Service.impl.ClubApprovalServiceImpl;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ClubApprovalController {

    public Label category;
    @FXML private Label clubName;
    @FXML private Label description;
    @FXML private Label createdBy;

    // ✅ Buttons
    @FXML private Button approveButton;
    @FXML private Button rejectButton;
    @FXML private Button backButton;

    private final ClubApprovalService clubApprovalService = new ClubApprovalServiceImpl();
    private Club currentClub;

    UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // ✅ Apply animation to buttons
        addButtonAnimation(approveButton);
        addButtonAnimation(rejectButton);
        addButtonAnimation(backButton);

        Platform.runLater(this::loadNextClub);
    }

    private void loadNextClub() {
        currentClub = clubApprovalService.viewNextPendingClub();

        if (currentClub != null) {
            clubName.setText(currentClub.getClubName());
            description.setText(currentClub.getDescriptions());
            createdBy.setText(userDAO.getUserNameBy(currentClub.getFounderId()));
            category.setText(currentClub.getCategory());

            clubName.setVisible(true);
            description.setVisible(true);
            createdBy.setVisible(true);
            category.setVisible(true);
        } else {
            FXMLScreenLoader.showMessage("No more clubs to approve.", "Club Approval", "info");
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
        if (!clubApprovalService.rejectNextClub()) {
            FXMLScreenLoader.showMessage("Club rejected successfully.", "Club Approval", "info");
        } else {
            FXMLScreenLoader.showMessage("Error rejecting club.", "Club Approval", "error");
        }
        loadNextClub();
    }

    @FXML
    private void onBack(ActionEvent event) {
        FXMLScreenLoader.openAdminDashboard(event);
    }

    private void clearLabels() {
        clubName.setText("");
        description.setText("");
        createdBy.setText("");
        category.setText("");

        clubName.setVisible(false);
        description.setVisible(false);
        createdBy.setVisible(false);
        category.setVisible(false);
    }

    // ✅ Animation logic for buttons
    private void addButtonAnimation(Button button) {
        if (button == null) return;

        // Hover in -> scale up
        button.setOnMouseEntered(e -> animateScale(button, 1.0, 1.08));
        // Hover out -> scale back
        button.setOnMouseExited(e -> animateScale(button, 1.08, 1.0));
        // Press -> shrink
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
}
