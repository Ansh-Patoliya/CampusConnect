package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AdminController {

    @FXML
    private Label nameLabel;

    // Buttons mapped to your FXML fx:id
    @FXML private Button viewEventButton;
    @FXML private Button eventRequestsButton;
    @FXML private Button clubRequestsButton;
    @FXML private Button viewClubMemberButton;
    @FXML private Button exportClubListButton;
    @FXML private Button viewMyProfileButton;
    @FXML private Button backButton;

    public void initialize() {
        User user = CurrentUser.getCurrentUser();
        if (user != null) {
            nameLabel.setText(user.getName());
        }

        // Apply zoom effect to all buttons
        addZoomEffect(viewEventButton);
        addZoomEffect(eventRequestsButton);
        addZoomEffect(clubRequestsButton);
        addZoomEffect(viewClubMemberButton);
        addZoomEffect(exportClubListButton);
        addZoomEffect(viewMyProfileButton);
        addZoomEffect(backButton);
    }

    private void addZoomEffect(Button button) {
        if (button == null) return;

        button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
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

    public void handleViewProfile(ActionEvent event) {
        FXMLScreenLoader.openAdminProfile(event);
    }

    public void handleViewClubMembers(ActionEvent event) {
        FXMLScreenLoader.openClubMemberList(event);
    }

    public void handleExportClub(ActionEvent actionEvent) {
        FXMLScreenLoader.openExportClub(actionEvent);
    }
}
