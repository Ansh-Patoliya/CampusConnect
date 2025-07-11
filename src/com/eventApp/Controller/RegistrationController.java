package com.eventApp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class RegistrationController {

    @FXML
    private RadioButton studentRadio;

    @FXML
    private RadioButton clubRadio;

    @FXML
    private AnchorPane StudentField;

    @FXML
    private AnchorPane ClubField;

    @FXML
    public void initialize() {
        // Listener for Student radio
        studentRadio.setOnAction(e -> {
            if (studentRadio.isSelected()) {
                StudentField.setVisible(true);
                ClubField.setVisible(false);
            }
        });

        // Listener for Club Member radio
        clubRadio.setOnAction(e -> {
            if (clubRadio.isSelected()) {
                ClubField.setVisible(true);
                StudentField.setVisible(false);
            }
        });
    }


}
