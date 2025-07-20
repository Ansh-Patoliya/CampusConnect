package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Service.EventService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EventApprovalController {
    private final EventService eventService = new EventService();
    public Label startTime;
    public Label eventDate;
    public Label description;
    public Label eventName;
    public Label endTime;
    public Label venue;
    public Label clubName;
    public Label createdBy;
    public Label maxParticipate;
    public Label ticketPrice;
    public Label discountAvailabel;
    private Event currentEvent;
    private ClubDAO clubDAO = new ClubDAO();
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        eventService.resetPointer();
        Platform.runLater(this::loadCurrentEvent);
    }

    private void loadCurrentEvent() {
        currentEvent = eventService.viewCurrentEvent();
        if (currentEvent != null) {
            setText(currentEvent);
            setVisible(true);
        } else {
            // Handle case when there are no more events to approve
            FXMLScreenLoader.showMessage("No more events to approve.", "Club Approval", "info");
            clearLabels();
            openAdminDashboard();
        }
    }

    private void clearLabels() {
        eventName.setText("");
        description.setText("");
        startTime.setText("");
        endTime.setText("");
        eventDate.setText("");
        venue.setText("");
        clubName.setText("");
        createdBy.setText("");
        maxParticipate.setText("");
        ticketPrice.setText("");
        discountAvailabel.setText("");

        setVisible(false);
    }

    public void onApprove(ActionEvent event) {
        boolean isApproved = eventService.approveEvent();
        if (isApproved) {
            FXMLScreenLoader.showMessage("Event approved successfully.", "Event Approval", "success");
            Event nextEvent = eventService.viewNextEvent();
            if (nextEvent != null) {
                loadNextOrPreviousEvent(nextEvent);
            } else {
                clearLabels();
                FXMLScreenLoader.showMessage("No more events to approve.", "Event Approval", "info");
                openAdminDashboard();
            }
        } else {
            FXMLScreenLoader.showMessage("Failed to approve the event. Please try again.", "Event Approval", "error");
        }
    }

    public void onReject(ActionEvent event) {
        boolean isRejected = eventService.rejectEvent();
        if (isRejected) {
            FXMLScreenLoader.showMessage("Event rejected successfully.", "Event Approval", "success");
            Event nextEvent = eventService.viewNextEvent();
            if (nextEvent != null) {
                loadNextOrPreviousEvent(nextEvent);
            } else {
                clearLabels();
                FXMLScreenLoader.showMessage("No more events to approve.", "Event Approval", "info");
                openAdminDashboard();
            }
        } else {
            FXMLScreenLoader.showMessage("Failed to reject the event. Please try again.", "Event Approval", "error");
        }
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openAdminDashboard(event); // Assuming this method opens the dashboard or previous screen
    }

    public void loadNextOrPreviousEvent(Event event) {
        currentEvent = event;
        setText(currentEvent);
        setVisible(true);
    }

    public void onNext(ActionEvent event) {
        Event nextEvent = eventService.viewNextEvent();
        if (nextEvent != null) {
            loadNextOrPreviousEvent(nextEvent);
        } else {
            FXMLScreenLoader.showMessage("No more events to approve.", "Event Approval", "info");
            openAdminDashboard();
        }
    }


    public void onPrevious(ActionEvent event) {
        Event previousEvent = eventService.viewPreviousEvent();
        if (event != null) {
            loadNextOrPreviousEvent(previousEvent);
        } else {
            FXMLScreenLoader.showMessage("No previous events to view.", "Event Approval", "info");
            openAdminDashboard();
        }
    }

    void setText(Event event){
        eventName.setText(event.getEventName());
        description.setText(event.getDescription());
        startTime.setText(event.getStartTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        endTime.setText(event.getEndTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        eventDate.setText(event.getEventDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        venue.setText(event.getVenue());
        clubName.setText(clubDAO.getClubNameBy(event.getClubId()));
        createdBy.setText(userDAO.getUserNameBy(event.getUserId()));
        maxParticipate.setText(String.valueOf(event.getMaxParticipants()));
        ticketPrice.setText(String.valueOf(event.getTicketPrice()));
        discountAvailabel.setText(String.valueOf(event.isDiscountApplicable()));
    }

    void setVisible(boolean visible) {
        eventName.setVisible(visible);
        description.setVisible(visible);
        startTime.setVisible(visible);
        endTime.setVisible(visible);
        eventDate.setVisible(visible);
        venue.setVisible(visible);
        clubName.setVisible(visible);
        createdBy.setVisible(visible);
        maxParticipate.setVisible(visible);
        ticketPrice.setVisible(visible);
        discountAvailabel.setVisible(visible);
    }
    public void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) clubName.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
