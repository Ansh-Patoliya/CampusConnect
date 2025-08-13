package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class ViewCreateEventsController {

    public Label eventName;
    public Label description;
    public Label eventDate;
    public Label startTime;
    public Label endTime;
    public Label venue;
    public Label clubName;
    public Label createdBy;
    public Label maxParticipate;
    public Label ticketPrice;
    public Label discountAvailabel;
    public Label categoryField;
    public Label ticketPrice1;
    public Label venue1;
    public Label eventDate1;
    public Label eventName1;
    public AnchorPane front;
    public AnchorPane back;
    public Label approvalStatusLabel;
    public Label completionStatusLabel;

    User currentUser = CurrentUser.getCurrentUser();
    ClubService clubService;
    UserDAO userDAO = new UserDAO();
    ClubDAO clubDAO = new ClubDAO();

    public void initialize() {
        try {
            front.setVisible(true);
            back.setVisible(false);
            clubService = new ClubService(currentUser);

            Event currentEvent = clubService.viewCurrentEvent();
            if (currentEvent != null) {
                setTextFront(currentEvent);
                setTextBack(currentEvent);
                setVisibleFront(true);
                setVisibleBack(false);
            } else {
                FXMLScreenLoader.showMessage("No more events to view.", "View Events", "info");
            }
        } catch (SQLException | DatabaseExceptionHandler | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Database Error", "error");
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
        approvalStatusLabel.setVisible(b);
        completionStatusLabel.setVisible(b);
    }

    private void setVisibleFront(boolean b) {
        eventName1.setVisible(b);
        eventDate1.setVisible(b);
        ticketPrice1.setVisible(b);
        venue1.setVisible(b);
    }

    private void setTextBack(Event currentEvent) {
        eventName.setText(currentEvent.getEventName());
        eventDate.setText(currentEvent.getEventDate().toString());
        ticketPrice.setText(String.valueOf(currentEvent.getTicketPrice()));
        venue.setText(currentEvent.getVenue());
        endTime.setText(currentEvent.getEndTime().toString());
        description.setText(currentEvent.getDescription());
        startTime.setText(currentEvent.getStartTime().toString());
        clubName.setText(clubDAO.getClubNameBy(currentEvent.getClubId()));
        createdBy.setText(userDAO.getUserNameBy(currentEvent.getUserId()));
        maxParticipate.setText(String.valueOf(currentEvent.getMaxParticipants()));
        discountAvailabel.setText(String.valueOf(currentEvent.isDiscountApplicable()));
        categoryField.setText(currentEvent.getCategory());
        approvalStatusLabel.setText(currentEvent.getApprovalStatus());
        completionStatusLabel.setText(currentEvent.getCompletionStatus());
    }

    private void setTextFront(Event currentEvent) {
        eventName1.setText(currentEvent.getEventName());
        eventDate1.setText(currentEvent.getEventDate().toString());
        ticketPrice1.setText(String.valueOf(currentEvent.getTicketPrice()));
        venue1.setText(currentEvent.getVenue());
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

    private void onClick() {
        front.setVisible(false);
        back.setVisible(true);
    }

    public void onNext(ActionEvent actionEvent) {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        Event nextEvent = clubService.viewNextEvent();
        if (nextEvent != null) {
            loadNextEvent(nextEvent);
        } else {
            FXMLScreenLoader.showMessage("No more events to approve.", "Event Approval", "info");
            onBack(actionEvent);
        }
    }

    private void loadNextEvent(Event nextEvent) {
        setTextFront(nextEvent);
        setTextBack(nextEvent);
    }

    public void onCanel(ActionEvent actionEvent) {
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openClubDashboard(actionEvent);
    }

    public void onUpdate(ActionEvent actionEvent) {
    }
}
