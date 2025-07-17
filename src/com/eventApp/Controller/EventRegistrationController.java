package com.eventApp.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventRegistrationController {
    public RadioButton fixedRadio;
    public RadioButton variableRadio;
    public AnchorPane discountNote;
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

    public void handleEventRegistration(ActionEvent event) {

    }

    public void openLoginPage(ActionEvent event) {
    }
}
