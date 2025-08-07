package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubMemberService;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClubDetailsController {
    public Label clubNameLabel;
    public Label categoryLabel;
    public Label descriptionLabel;
    public Label founderNameLabel;
    public Label memberCountLabel;

    private User user;
    private ClubService clubService = new ClubService();

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        // You can perform any additional initialization here if needed
        user= CurrentUser.getCurrentUser() ;// Assuming User class has a method to get the current logged-in user
        loadClubDetails();
    }

    private void loadClubDetails() {
        Club club= clubService.getClubByUser(user);
        UserDAO userDAO = new UserDAO();
        if (club != null) {
            clubNameLabel.setText(club.getClubName());
            categoryLabel.setText(club.getCategory());
            descriptionLabel.setText(club.getDescriptions());
            founderNameLabel.setText(userDAO.getUserNameBy(user.getUserId()));
            memberCountLabel.setText(String.valueOf(club.getMemberCount()));
        }
    }
    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openClubDashboard(actionEvent);
    }
}
