package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubMemberService;
import com.eventApp.Service.impl.ClubMemberServiceImpl;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClubViewProfileController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label positionLabel;
    @FXML private Label clubIdLabel;

    private User user;
    private final ClubMemberService clubMemberService = new ClubMemberServiceImpl();

    @FXML
    public void initialize() {
        // Load current logged-in user
        user = CurrentUser.getCurrentUser();
        loadClubMemberProfile();
    }

    private void loadClubMemberProfile() {
        ClubMember clubMember = clubMemberService.getClubMemberByUser(user);
        if (clubMember != null) {
            nameLabel.setText(clubMember.getName());
            emailLabel.setText(clubMember.getEmail());
            positionLabel.setText(clubMember.getPosition());
            clubIdLabel.setText(String.valueOf(clubMember.getClubId()));
        } else {
            nameLabel.setText("N/A");
            emailLabel.setText("N/A");
            positionLabel.setText("N/A");
            clubIdLabel.setText("N/A");
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        // Navigate back to Club Dashboard
        FXMLScreenLoader.openClubDashboard(event);
    }
}
