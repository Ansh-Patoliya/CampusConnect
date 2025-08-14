package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class AdminController {

    public Label nameLabel;

    public void initialize() {
        User user = CurrentUser.getCurrentUser();
        nameLabel.setText(user.getName());
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
