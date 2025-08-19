package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class EventRegistrationController {
    private final ClubService clubService = new ClubService();
    public RadioButton fixedRadio;
    public RadioButton variableRadio;
    public AnchorPane discountNote;
    public TextField eventNameField;
    public TextField descriptionField;
    public TextField capacityField;
    public DatePicker datePicker;
    public TextField venueField;
    public TextField ticketPriceField;
    public ComboBox categoryField;
    @FXML
    private ComboBox<String> startTimeCombo;
    @FXML
    private ComboBox<String> endTimeCombo;

    @FXML
    public void initialize() {
        List<String> categories = Arrays.asList(
                // Technical
                "Coding",
                "Robotics",
                "AI/ML",
                "Cybersecurity",
                "Web & App Development",
                "Electronics",
                "Open Source",

                // Cultural
                "Dance",
                "Music",
                "Drama/Theatre",
                "Literature",
                "Fine Arts",
                "Photography",
                "Film & Media",

                // Academic / Leadership
                "Entrepreneurship",
                "Finance & Business",
                "Debate",
                "Public Speaking",
                "Management",
                "Innovation",
                "Science & Research"
        );

        categoryField.getItems().addAll(categories);
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

    ClubDAO clubDAO = new ClubDAO();
    public void handleEventRegistration(ActionEvent event) {
        try {
            String eventName = eventNameField.getText();
            String description = descriptionField.getText();
            String venue = venueField.getText();
            String category = (String) categoryField.getValue();
            if (category == null || category.isEmpty()) {
                FXMLScreenLoader.showMessage("Please select a category.", "Category Error", "error");
                return;
            }
            int maxParticipants = Integer.parseInt(capacityField.getText());
            if( maxParticipants <= 0) {
                FXMLScreenLoader.showMessage("Maximum participants must be greater than zero.", "Capacity Error", "error");
                return;
            }
            double ticketPrice = Double.parseDouble(ticketPriceField.getText());
            if (ticketPrice < 0) {
                FXMLScreenLoader.showMessage("Ticket price cannot be negative.", "Ticket Price Error", "error");
                return;
            }
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate eventDate = datePicker.getValue();

            User currentUser = CurrentUser.getCurrentUser();
            String userId = currentUser.getUserId();
            int clubId = clubDAO.getClubIdByUserId(currentUser.getUserId());
            boolean discountApplicable = variableRadio.isSelected();

            if (validateFields(eventName, description, venue, eventDate, startTime, endTime)) {
                Event event1 = new Event(eventName, description, venue, clubId, userId, maxParticipants, eventDate, startTime, endTime, ticketPrice, discountApplicable, category);
                clubService.addEvent(event1);
                FXMLScreenLoader.showMessage("Event Created Successfully!", "Success", "info");
                handleBack(event);
            }
        } catch (NumberFormatException e) {
            FXMLScreenLoader.showMessage("Please enter valid numeric values for capacity and ticket price.", "Input Error", "error");
        } catch (ValidationException | SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Validation Error", "error");
        } catch (DatabaseExceptionHandler e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Database Error", "error");
        }
    }

    public boolean validateFields(String eventName, String description, String venue, LocalDate eventDate, LocalTime startTime, LocalTime endTime) {
        try {
            ValidationUtils.checkName(eventName);
        } catch (ValidationException e) {
            eventNameField.clear();
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Name", "error");
            return false;
        }

        try {
            ValidationUtils.checkDescription(description);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Description", "error");
            descriptionField.clear();
            return false;
        }

        try {
            ValidationUtils.checkName(venue);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Venue", "error");
            venueField.clear();
            return false;
        }

        try {
            ValidationUtils.checkEventTime(startTime, endTime);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Time", "error");
            startTimeCombo.getSelectionModel().clearSelection();
            endTimeCombo.getSelectionModel().clearSelection();
            return false;
        }

        try {
            ValidationUtils.dateValidator(eventDate);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Event Date", "error");
            datePicker.setValue(LocalDate.now());
            return false;
        }
        return true;
    }

    public void handleBack(ActionEvent event) {
        FXMLScreenLoader.openClubDashboard(event);
    }
}
