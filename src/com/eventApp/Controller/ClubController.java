package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClubController {

    @FXML
    private void viewProfile(ActionEvent event) {
        FXMLScreenLoader.openClubMemberProfile(event);

    }

    public void handleEventCreate(ActionEvent event) {
        FXMLScreenLoader.openEventRegistration(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openClubDashboard(event);
    }

    public void handleViewEvents(ActionEvent event) {
    }


    public void handleViewClubMembers(ActionEvent event) {
    }

    public void handleViewParticipate(ActionEvent event) {
    }

    public void handleViewClubDetails(ActionEvent event) {
    }
}
