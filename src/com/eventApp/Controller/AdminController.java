package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Admin;
import com.eventApp.Model.User;
import com.eventApp.Service.AdminService;
import com.eventApp.Service.StudentService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for the Admin Dashboard in the CampusConnect system.
 * 
 * This controller manages the main administrative interface and handles navigation
 * to various admin-specific functions. Administrators have the highest level of
 * system access and can approve clubs and events, manage users, and oversee
 * the entire platform.
 * 
 * Admin Capabilities:
 * - Club approval and management
 * - Event approval and oversight
 * - User and member management
 * - System data export and reporting
 * - Profile management
 * 
 * This controller acts as the central hub for all administrative operations,
 * delegating specific tasks to specialized controllers and services.
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
public class AdminController {

    /**
     * Handles navigation to the Club Approval interface.
     * 
     * This method opens the club approval screen where administrators can
     * review pending club creation requests, examine club details, and
     * make approval decisions. The approval process ensures that clubs
     * meet the platform's standards and guidelines.
     * 
     * @param event the ActionEvent triggered by clicking the club approval button
     */
    public void handleClubApproval(ActionEvent event) {
        FXMLScreenLoader.openClubApproval(event);
    }

    /**
     * Handles navigation to the Event Approval interface.
     * 
     * This method opens the event approval screen where administrators can
     * review pending event submissions from clubs, verify event details,
     * and approve or reject events based on platform policies.
     * 
     * @param event the ActionEvent triggered by clicking the event approval button
     */
    public void handleEventApproval(ActionEvent event) {
        FXMLScreenLoader.openEventApproval(event);
    }

    /**
     * Handles navigation to the View Events interface.
     * 
     * This method opens a comprehensive view of all events in the system,
     * allowing administrators to monitor event activity, track registrations,
     * and manage event-related issues.
     * 
     * @param event the ActionEvent triggered by clicking the view events button
     */
    public void handleViewEvents(ActionEvent event) {
        FXMLScreenLoader.openViewEvents(event);
    }

    /**
     * Handles logout and navigation back to the login page.
     * 
     * This method logs out the current administrator and returns to the
     * main login interface. It should clear any session data and ensure
     * secure logout procedures.
     * 
     * @param event the ActionEvent triggered by clicking the logout/back button
     */
    public void handleBack(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    /**
     * Handles navigation to the Admin Profile interface.
     * 
     * This method opens the administrator's profile management screen
     * where they can view and update their personal information,
     * change passwords, and manage account settings.
     * 
     * @param event the ActionEvent triggered by clicking the view profile button
     */
    public void handleViewProfile(ActionEvent event) {
        FXMLScreenLoader.openAdminProfile(event);
    }

    /**
     * Handles navigation to the Club Members List interface.
     * 
     * This method opens a comprehensive view of all club members in the system,
     * allowing administrators to monitor club membership, resolve membership
     * issues, and manage user roles within clubs.
     * 
     * @param event the ActionEvent triggered by clicking the view club members button
     */
    public void handleViewClubMembers(ActionEvent event) {
        FXMLScreenLoader.openClubMemberList(event);
    }

    /**
     * Handles navigation to the Export Club Data interface.
     * 
     * This method opens the club data export functionality, allowing
     * administrators to generate reports and export club information
     * for analysis, backup, or external use.
     * 
     * @param actionEvent the ActionEvent triggered by clicking the export club button
     */
    public void handleExportClub(ActionEvent actionEvent) {
        FXMLScreenLoader.openExportClub(actionEvent);
    }
}
