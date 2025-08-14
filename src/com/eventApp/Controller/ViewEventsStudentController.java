package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Service.EventRegistrationService;
import com.eventApp.Service.StudentService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class ViewEventsStudentController {
    public Label venue;
    public Label eventDate;
    public Label eventName;
    public Label ticketPrice;
    public Label eventName1;
    public Label venue1;
    public Label ticketPrice1;
    public Label eventDate1;
    public Label endTime;
    public Label description;
    public Label startTime;
    public Label clubName;
    public Label createdBy;
    public Label maxParticipate;
    public Label discountAvailabel;
    public AnchorPane front;
    public AnchorPane back;
    public Label categoryField;

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openStudentDashboard(event);
    }

    public void initialize() {
        front.setVisible(true);
        back.setVisible(false);
        studentService.loadStudentEvents();
        Event currentEvent = studentService.viewCurrentEvent();
        if (currentEvent != null) {
            setTextFront(currentEvent);
            setTextBack(currentEvent);
            setVisibleFront(true);
            setVisibleBack(false);
        } else {
            FXMLScreenLoader.showMessage("No more events to view.", "View Events", "info");
            onBack(null);
        }
    }

    private void setVisibleFront(boolean b) {
        eventName1.setVisible(b);
        eventDate1.setVisible(b);
        ticketPrice1.setVisible(b);
        venue1.setVisible(b);
    }

    ClubDAO clubDAO = new ClubDAO();
    UserDAO userDAO = new UserDAO();

    private void setTextFront(Event event) {
        eventName1.setText(event.getEventName());
        eventDate1.setText(event.getEventDate().toString());
        ticketPrice1.setText(String.valueOf(event.getTicketPrice()));
        venue1.setText(event.getVenue());
    }
    private void setTextBack(Event event) {
        eventName.setText(event.getEventName());
        description.setText(event.getDescription());
        categoryField.setText(event.getCategory());
        startTime.setText(event.getStartTime().toString());
        endTime.setText(event.getEndTime().toString());
        eventDate.setText(event.getEventDate().toString());
        venue.setText(event.getVenue());
        clubName.setText(clubDAO.getClubNameBy(event.getClubId()));
        createdBy.setText(userDAO.getUserNameBy(event.getUserId()));
        maxParticipate.setText(String.valueOf(event.getMaxParticipants()));
        ticketPrice.setText(String.valueOf(event.getTicketPrice()));
        discountAvailabel.setText(String.valueOf(event.isDiscountApplicable()));
    }

    void setVisibleBack(boolean visible) {

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


    void onClick(){
        front.setVisible(false);
        back.setVisible(true);
    }

    StudentService studentService = new StudentService();

    public void onNext(ActionEvent event) {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        Event nextEvent = studentService.getNextEvent();
        if (nextEvent != null) {
            loadNextOrPreviousEvent(nextEvent);
        } else {
            FXMLScreenLoader.showMessage("No more events to view.", "Event Approval", "info");
            onBack(event);
        }
    }

    private void loadNextOrPreviousEvent(Event nextEvent) {

        setTextFront(nextEvent);
        setTextBack(nextEvent);
    }

    public void onPrevious(ActionEvent event) {
        front.setVisible(true);
        back.setVisible(false);
        setVisibleFront(true);
        setVisibleBack(false);
        Event previousEvent = studentService.getPreviousEvent();
        if (previousEvent != null) {
            loadNextOrPreviousEvent(previousEvent);
        } else {
            FXMLScreenLoader.showMessage("No previous events to view.", "Event Approval", "info");
            onBack(event);
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

    public void onRegistration(ActionEvent event) {
        EventRegistrationService eventRegistrationService = new EventRegistrationService();
        int eventId = studentService.viewCurrentEvent().getEventId();
        try {
            eventRegistrationService.registerForEvent(eventId);
            onNext(event);
            FXMLScreenLoader.showMessage("Registration successful for event: " + studentService.viewCurrentEvent().getEventName(), "Registration Success", "info");
        } catch (DatabaseExceptionHandler | SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Registration Error", "error");
        }

    }
}
