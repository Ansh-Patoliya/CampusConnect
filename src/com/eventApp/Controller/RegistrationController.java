package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Student;
import com.eventApp.Service.UserService;
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

    private final UserService userService=new UserService();
    public void handleRegistration(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (studentRadio.isSelected()) {
            String department = departmentField.getText();
            String semester = semesterField.getText();
            String enrollmentNo = enrollmentField.getText();
            List<String> interest = interestListView.getSelectionModel().getSelectedItems();
            if (interest == null || interest.isEmpty()) {
                showError("❌ Please select at least one interest.");
                return;
            }
            if(validateStudentFields(name,email,password,confirmPassword,department,semester,enrollmentNo)){
                int sem=Integer.parseInt(semester);
                Student student=new Student(enrollmentNo,name,email,password,"student".toUpperCase(),department,sem,interest);
                boolean success=userService.registerStudent(student);
                if(success) {
                    FXMLScreenLoader.openLoginPage(event);
                }
                else{
                    showError("❌ Registration failed. Please try again.");
                }
            }
            else{

            }
        } else if (clubRadio.isSelected()) {
            // Handle club registration logic here
            System.out.println("Club registration logic goes here.");
        }
        else{
            showError("Please select a role (Student or Club Member) to register.");
        }

    }

    public boolean validateFields(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (!ValidationUtils.checkName(name))
        {
            nameField.clear();
            showError("Name must contain only letters.");
            isValid = false;
        }

        if (!ValidationUtils.checkEmail(email)) {
            emailField.clear();
            showError("Please enter a valid email.");
            isValid = false;
        }

        if (!ValidationUtils.checkPassword(password)) {
            passwordField.clear();
            showError("Password must be strong (min 8 chars, mix of A-Z, a-z, 0-9, special char).");
            isValid = false;
        }

        if (!ValidationUtils.isMatchingPasswords(password, confirmPassword)) {
            passwordField.clear();
            confirmPasswordField.clear();
            showError("Password and Confirm Password do not match.");
            isValid = false;
        }

        return isValid;
    }

    public boolean validateStudentFields(String name, String email, String password, String confirmPassword, String department, String semester, String enrollmentNo) {
        if (!validateFields(name, email, password, confirmPassword)) {
            return false; // General validations failed
        }

        if (!ValidationUtils.checkDepartment(department)) {
            departmentField.clear();
            showError("❌ Department cannot be empty or invalid.");
            return false;
        }

        if (!ValidationUtils.checkSemester(semester)) {
            semesterField.clear();
            showError("❌ Semester must be a number between 1 and 8.");
            return false;
        }

        if (!ValidationUtils.checkEnrollment(enrollmentNo)) {
            enrollmentField.clear();
            showError("❌ Enrollment number must contain only digits.");
            return false;
        }
        return true;
    }

}
