package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class RegistrationController {

    public TextField nameField;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public ToggleGroup RollGroup;
    public TextField departmentField;
    public TextField semesterField;
    public TextField enrollmentField;
    public ComboBox selectClubField;
    public TextField clubNameField;
    public TextArea descriptionField;
    public ComboBox categoryField;
    public ListView interestListView;
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

        interestListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleRegistration(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (studentRadio.isSelected()) {
            // Handle student registration logic here
            System.out.println("Student registration logic goes here.");
            String department = departmentField.getText();
            String semester = semesterField.getText();
            String enrollmentNo = enrollmentField.getText();
            List<String> interest = interestListView.getSelectionModel().getSelectedItems();
            if(validateStudentFields(name,email,password,confirmPassword,department,semester,enrollmentNo)){
                System.out.println("successfully");
            }
        } else if (clubRadio.isSelected()) {
            // Handle club registration logic here
            System.out.println("Club registration logic goes here.");
        } else {
            System.out.println("Please select a registration type.");
        }
        FXMLScreenLoader.openLoginPage(event);
    }

    public boolean validateFields(String name, String email, String password, String confirmPassword) {
        return ValidationUtils.checkName(name) && ValidationUtils.checkEmail(email) && ValidationUtils.checkPassword(password) && ValidationUtils.isMatchingPasswords(password, confirmPassword);
    }

    public boolean validateStudentFields(String name, String email, String password, String confirmPassword, String department, String semester, String enrollmentNo) {
        return validateFields(name, email, password, confirmPassword) && ValidationUtils.checkDepartment(department) && ValidationUtils.checkSemester(semester) && ValidationUtils.checkEnrollment(enrollmentNo);
    }
}
