package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

/**
 * Controller for the Admin dashboard screen.
 *
 * Responsibilities:
 * - Initialize the dashboard header with the current admin's name.
 * - Handle navigation actions triggered from the admin dashboard buttons.
 *
 * Contracts/Assumptions:
 * - CurrentUser holds a non-null User after successful login; otherwise initialization may fail.
 * - nameLabel is injected from AdminDashboard.fxml.
 */
public class AdminController {

    // Injected by FXML: shows the logged-in admin's display name on the dashboard header.
    public Label nameLabel;

    /**
     * JavaFX lifecycle hook. Called automatically after FXML fields are injected.
     * Populates the admin name in the header.
     */
    public void initialize() {
        User user = CurrentUser.getCurrentUser();
        // Note: If user is null, this will throw a NullPointerException. Ensure login sets CurrentUser.
        nameLabel.setText(user.getName());
    }

    /**
     * Navigate to the Club Approval view where pending clubs can be approved/rejected.
     * @param event button click event
     */
    public void handleClubApproval(ActionEvent event) {
        FXMLScreenLoader.openClubApproval(event);
    }

    /**
     * Navigate to the Event Approval view for reviewing and approving pending events.
     * @param event button click event
     */
    public void handleEventApproval(ActionEvent event) {
        FXMLScreenLoader.openEventApproval(event);
    }

    /**
     * Navigate to the View Events screen to browse all events.
     * @param event button click event
     */
    public void handleViewEvents(ActionEvent event) {
        FXMLScreenLoader.openViewEvents(event);
    }

    /**
     * Navigate back to the Login page.
     * Note: This does not clear session state; ensure logout logic runs elsewhere if required.
     * @param event button click event
     */
    public void handleBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    /**
     * Navigate to the Admin Profile view.
     * @param event button click event
     */
    public void handleViewProfile(ActionEvent event) {
        FXMLScreenLoader.openAdminProfile(event);
    }

    /**
     * Navigate to the Club Members listing screen.
     * @param event button click event
     */
    public void handleViewClubMembers(ActionEvent event) {
        FXMLScreenLoader.openClubMemberList(event);
    }

    /**
     * Open the Export Club screen to export club details.
     * @param actionEvent button click event
     */
    public void handleExportClub(ActionEvent actionEvent) {
        FXMLScreenLoader.openExportClub(actionEvent);
    }
}
