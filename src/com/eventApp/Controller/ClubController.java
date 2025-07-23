package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClubController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label clubIdLabel;


    public void setClubMember(ClubMember clubMember) {
        if (clubMember != null) {
            nameLabel.setText(clubMember.getName());
            emailLabel.setText(clubMember.getEmail());
            positionLabel.setText(clubMember.getPosition());
            clubIdLabel.setText(String.valueOf(clubMember.getClubId()));
        }
    }

    @FXML
    private void viewProfile(ActionEvent event) {
        FXMLScreenLoader.openClubMemberProfile(event);

    }


    public void onLogin(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleEventCreate(ActionEvent event) {
        FXMLScreenLoader.openEventRegistration(event);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void onBack1(ActionEvent event) {
        FXMLScreenLoader.openClubDashboard(event);
    }

    public void handleViewEvents(ActionEvent event) {
    }

    public void handleEventApproval(ActionEvent event) {
    }

    public void handleClubApproval(ActionEvent event) {
    }
}
