package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import com.eventApp.Utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventRegistrationController {
    public RadioButton fixedRadio;
    public RadioButton variableRadio;
    public AnchorPane discountNote;
    public TextField eventNameField;
    public TextField descriptionField;
    public TextField capacityField;
    public DatePicker datePicker;
    public TextField venueField;
    public TextField ticketPriceField;
    @FXML
    private ComboBox<String> startTimeCombo;

    @FXML
    private ComboBox<String> endTimeCombo;

    @FXML
    public void initialize() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();

        LocalTime time = LocalTime.of(8, 0);
        while (time.isBefore(LocalTime.of(22, 0))) {
            timeOptions.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
            time = time.plusMinutes(30);
        }

        startTimeCombo.setItems(timeOptions);
        endTimeCombo.setItems(timeOptions);

        variableRadio.setOnAction(e-> {
            discountNote.setVisible(true);
        });
        fixedRadio.setOnAction(e-> {
            discountNote.setVisible(false);
        });
    }

    private ClubService clubService = new ClubService();
    public void handleEventRegistration(ActionEvent event) {
        String eventName = eventNameField.getText();
        String description = descriptionField.getText();
        String venue = venueField.getText();
        String maxParticipants= capacityField.getText();
        String ticketPrice = ticketPriceField.getText();
        LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate eventDate = datePicker.getValue();

        User currentUser = CurrentUser.getCurrentUser();
        String
                userId= currentUser.getUserId();
        String clubId= UserDAO.getClubIdByUserId(currentUser.getUserId());
        boolean discountApplicable = variableRadio.isSelected();

        if(validateFields(eventName, description, venue, maxParticipants, eventDate, startTime, endTime, ticketPrice)) {
            int maxParticipantsInt = Integer.parseInt(maxParticipants);
            double ticketPriceDouble = Double.parseDouble(ticketPrice);
            Event event1=new Event(eventName,description,venue,clubId,userId,maxParticipantsInt,eventDate,startTime,endTime,ticketPriceDouble, discountApplicable);
            if(clubService.addEvent(event1)){
                FXMLScreenLoader.showMessage("Event Created Successfully!", "Success", "info");
                openLoginPage(event);
            } else {
                FXMLScreenLoader.showMessage("Failed to create event. Please try again.", "Error", "error");
            }
        }
    }

    public boolean validateFields(String eventName, String description, String venue, String maxParticipants, LocalDate eventDate, LocalTime startTime, LocalTime endTime, String ticketPrice) {
        if(!ValidationUtils.checkName(eventName)){
            eventNameField.clear();
            return false;
        }
        if(!ValidationUtils.checkDescription(description)){
            descriptionField.clear();

            return false;
        }
        if(!ValidationUtils.checkName(venue)){
            venueField.clear();
            return false;
        }
        if(!ValidationUtils.checkNumber(maxParticipants)){
            capacityField.clear();
            return false;
        }
        if(!ValidationUtils.checkEventTime(startTime, endTime)){
            startTimeCombo.getSelectionModel().clearSelection();
            endTimeCombo.getSelectionModel().clearSelection();
            return false;
        }
        if(!ValidationUtils.dateValidator(eventDate)){
            datePicker.setValue(LocalDate.now());
            return false;
        }
        if(!ValidationUtils.checkNumber(ticketPrice)){
            ticketPriceField.clear();
            return false;
        }

        return true;
    }
    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }
}
