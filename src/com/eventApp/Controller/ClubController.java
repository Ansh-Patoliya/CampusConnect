package com.eventApp.Controller;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ClubController {

    @FXML private AnchorPane viewClubDetailsPane;
    @FXML private Label nameLabel;

    // ✅ Match exact fx:id from FXML
    @FXML private Button createEventButton;
    @FXML private Button viewMyEventsButton;
    @FXML private Button viewClubDetailsButton;
    @FXML private Button myParticipationButton;
    @FXML private Button viewMyProfileButton;
    @FXML private Button viewClubMemberButton;
    @FXML private Button backButton;

    public void initialize() {
        User user = CurrentUser.getCurrentUser();
        if (user != null) {
            nameLabel.setText(user.getName());
            ClubMember clubMember = ClubMemberDAO.getClubMember(user);

            // Show "View Members" only if President
            if (clubMember != null && "President".equalsIgnoreCase(clubMember.getPosition())) {
                viewClubDetailsPane.setVisible(true);
            } else {
                viewClubDetailsPane.setVisible(false);
            }
        }

        // ✅ Apply animations to all buttons
        addButtonAnimation(createEventButton);
        addButtonAnimation(viewMyEventsButton);
        addButtonAnimation(viewClubDetailsButton);
        addButtonAnimation(myParticipationButton);
        addButtonAnimation(viewMyProfileButton);
        addButtonAnimation(viewClubMemberButton);
        addButtonAnimation(backButton);
    }

    /**
     * Adds hover + click scale animations to a button
     */
    private void addButtonAnimation(Button button) {
        if (button == null) return;

        button.setOnMouseEntered(e -> animateScale(button, 1.0, 1.08));
        button.setOnMouseExited(e -> animateScale(button, 1.08, 1.0));
        button.setOnMousePressed(e -> animateScale(button, 1.08, 0.95));
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

    // ✅ Handlers
    @FXML
    private void viewProfile(ActionEvent event) {
        FXMLScreenLoader.openClubMemberProfile(event);
    }

    @FXML
    public void handleEventCreate(ActionEvent event) {
        FXMLScreenLoader.openEventRegistration(event);
    }

    @FXML
    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    @FXML
    public void handleViewEvents(ActionEvent event) {
        FXMLScreenLoader.openViewCreateEvents(event);
    }

    @FXML
    public void handleViewClubMembers(ActionEvent event) {
        FXMLScreenLoader.openViewMember(event);
    }

    @FXML
    public void handleViewParticipate(ActionEvent event) {
        FXMLScreenLoader.openViewParticipant(event);
    }

    @FXML
    public void handleViewClubDetails(ActionEvent event) {
        FXMLScreenLoader.openClubDetails(event);
    }
}
