package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Service.IEventService;
import com.eventApp.Service.impl.EventServiceImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EventApprovalController {
    private final IEventService eventService = new EventServiceImpl();
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
    public Label categoryField;
    public AnchorPane front;
    public Label eventName1;
    public Label eventDate1;
    public Label venue1;
    public Label ticketPrice1;
    public AnchorPane back;
    private Event currentEvent;
    private ClubDAO clubDAO = new ClubDAO();
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        eventService.resetPointer();
        Platform.runLater(this::loadCurrentEvent);
    }

    private void loadCurrentEvent() {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        currentEvent = eventService.viewCurrentEvent();
        if (currentEvent != null) {
            setTextFront(currentEvent);
            setTextBack(currentEvent);
            setVisible(true);
        } else {
            // Handle case when there are no more events to approve
            FXMLScreenLoader.showMessage("No more events to approve.", "Club Approval", "info");
            clearLabels();
            openAdminDashboard();
        }
    }

    private void setTextFront(Event event) {
        eventName1.setText(event.getEventName());
        eventDate1.setText(event.getEventDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        ticketPrice1.setText(String.valueOf(event.getTicketPrice()));
        venue1.setText(event.getVenue());
    }

    private void setTextBack(Event event) {
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
        categoryField.setText(event.getCategory());
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
            loadCurrentEvent();
            FXMLScreenLoader.showMessage("Event approved successfully.", "Event Approval", "info");
        } else {
            FXMLScreenLoader.showMessage("Failed to approve the event. Please try again.", "Event Approval", "error");
        }
    }

    public void onReject(ActionEvent event) {
        boolean isRejected = eventService.rejectEvent();
        if (isRejected) {
            FXMLScreenLoader.showMessage("Event rejected successfully.", "Event Approval", "success");
            loadCurrentEvent();
        } else {
            FXMLScreenLoader.showMessage("Failed to reject the event. Please try again.", "Event Approval", "error");
        }
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openAdminDashboard(event); // Assuming this method opens the dashboard or previous screen
    }

    public void loadNextOrPreviousEvent(Event event) {
        if (event != null) {
            setTextFront(event);
            setTextBack(event);
            setVisible(true);
        } else {
            FXMLScreenLoader.showMessage("No event data available.", "Event Approval", "warning");
        }
    }

    public void onNext(ActionEvent event) {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        Event nextEvent = eventService.viewNextEvent();
        if (nextEvent != null) {
            loadNextOrPreviousEvent(nextEvent);
        } else {
            FXMLScreenLoader.showMessage("No more events to approve.", "Event Approval", "info");
            openAdminDashboard();
        }
    }


    public void onPrevious(ActionEvent event) {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        Event previousEvent = eventService.viewPreviousEvent();
        if (event != null) {
            loadNextOrPreviousEvent(previousEvent);
        } else {
            FXMLScreenLoader.showMessage("No previous events to view.", "Event Approval", "info");
            openAdminDashboard();
        }
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
        categoryField.setVisible(visible);
    }
    public void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventApp/FXML/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) clubName.getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(new Scene(root, 1400, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickFront(MouseEvent mouseEvent) {
        if (front.isVisible()) {
            onClick();
            setVisibleFront(false);
            setVisibleBack(true);
        } else {
            front.setVisible(true);
            back.setVisible(false);
        }
    }

    private void setVisibleBack(boolean b) {
        eventName.setVisible(b);
        eventDate.setVisible(b);
        ticketPrice.setVisible(b);
        venue.setVisible(b);
        endTime.setVisible(b);
        description.setVisible(b);
        startTime.setVisible(b);
        clubName.setVisible(b);
        createdBy.setVisible(b);
        maxParticipate.setVisible(b);
        discountAvailabel.setVisible(b);
        categoryField.setVisible(b);
    }

    private void setVisibleFront(boolean b) {
        eventName1.setVisible(b);
        eventDate1.setVisible(b);
        ticketPrice1.setVisible(b);
        venue1.setVisible(b);
    }

    private void onClick() {
        front.setVisible(false);
        back.setVisible(true);
    }
}
