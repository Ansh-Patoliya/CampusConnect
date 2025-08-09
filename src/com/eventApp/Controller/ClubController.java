package com.eventApp.Controller;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ClubController {

    public AnchorPane viewClubDetailsPane;

    public void initialize() {
        // Initialization logic can go here if needed
        User user= CurrentUser.getCurrentUser();
        ClubMember clubMember = ClubMemberDAO.getClubMember(user);
        if(clubMember.getPosition().equalsIgnoreCase("President")) {
            viewClubDetailsPane.setVisible(true);
        } else {
            viewClubDetailsPane.setVisible(false);
        }
    }

    @FXML
    private void viewProfile(ActionEvent event) {
        FXMLScreenLoader.openClubMemberProfile(event);
    }

    public void handleEventCreate(ActionEvent event) {
        FXMLScreenLoader.openEventRegistration(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleViewEvents(ActionEvent event) {
        FXMLScreenLoader.openViewCreateEvents(event);
    }


    public void handleViewClubMembers(ActionEvent event) {
        FXMLScreenLoader.openViewMember(event);
    }

    public void handleViewParticipate(ActionEvent event) {
        FXMLScreenLoader.openViewParticipant(event);
    }

    public void handleViewClubDetails(ActionEvent event) {
        FXMLScreenLoader.openClubDetails(event);
    }
}
