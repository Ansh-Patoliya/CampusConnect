package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.ValidationException;
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
    private final ClubService clubService = new ClubService();

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

        variableRadio.setOnAction(_ -> discountNote.setVisible(true));
        fixedRadio.setOnAction(_ -> discountNote.setVisible(false));
    }

    public void handleEventRegistration(ActionEvent event) {
        try {
            String eventName = eventNameField.getText();
            String description = descriptionField.getText();
            String venue = venueField.getText();
            int maxParticipants = Integer.parseInt(capacityField.getText());
            double ticketPrice = Double.parseDouble(ticketPriceField.getText());
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate eventDate = datePicker.getValue();

            User currentUser = CurrentUser.getCurrentUser();
            String userId = currentUser.getUserId();
            int clubId = UserDAO.getClubIdByUserId(currentUser.getUserId());
            boolean discountApplicable = variableRadio.isSelected();

            if (validateFields(eventName, description, venue, eventDate, startTime, endTime)) {
                Event event1 = new Event(eventName, description, venue, clubId, userId, maxParticipants, eventDate, startTime, endTime, ticketPrice, discountApplicable);
                if (clubService.addEvent(event1)) {
                    FXMLScreenLoader.showMessage("Event Created Successfully!", "Success", "info");
                    handleBack(event);
                } else {
                    FXMLScreenLoader.showMessage("Failed to create event. Please try again.", "Error", "error");
                }
            }
        } catch (NumberFormatException e) {
            FXMLScreenLoader.showMessage("Please enter valid numeric values for capacity and ticket price.", "Input Error", "error");
        }
    }

    public boolean validateFields(String eventName, String description, String venue, LocalDate eventDate, LocalTime startTime, LocalTime endTime) {
        try {
            ValidationUtils.checkName(eventName);
        } catch (ValidationException e) {
            eventNameField.clear();
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Name", "error");
        }

        try {
            ValidationUtils.checkDescription(description);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Description", "error");
            descriptionField.clear();
        }

        try {
            ValidationUtils.checkName(venue);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Venue", "error");
            venueField.clear();
        }

        try {
            ValidationUtils.checkEventTime(startTime, endTime);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Time", "error");
            startTimeCombo.getSelectionModel().clearSelection();
            endTimeCombo.getSelectionModel().clearSelection();
        }

        try {
            ValidationUtils.dateValidator(eventDate);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Date", "error");
            datePicker.setValue(LocalDate.now());
        }
        return true;
    }

    public void handleBack(ActionEvent event) {
        FXMLScreenLoader.openClubDashboard(event);
    }
}
