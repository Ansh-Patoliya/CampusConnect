package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Admin;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Service.AdminService;
import com.eventApp.Service.ClubMemberService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClubViewProfileController {
    public Label positionLabel;
    public Label clubIdLabel;
    @FXML
    private Label nameLabel;
    @FXML private Label emailLabel;

    private User user;
    private ClubMemberService clubMemberService = new ClubMemberService();

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        // You can perform any additional initialization here if needed
        user= CurrentUser.getCurrentUser() ;// Assuming User class has a method to get the current logged-in user
        loadClubMemberProfile();
    }

    private void loadClubMemberProfile() {
        System.out.println("Loading club member profile for user: " + user.getUserId());
        ClubMember clubMember = clubMemberService.getClubMemberByUser(user);
        if (clubMember != null) {
            nameLabel.setText(clubMember.getName());
            emailLabel.setText(clubMember.getEmail());
            positionLabel.setText(clubMember.getPosition());
            clubIdLabel.setText(String.valueOf(clubMember.getClubId()));
        }
    }

    public void onBack(ActionEvent event) {
        // Logic to go back to the previous screen, e.g., Student Dashboard
        // This could be implemented using a method from FXMLScreenLoader or similar
        FXMLScreenLoader.openStudentDashboard(event);
    }
}
