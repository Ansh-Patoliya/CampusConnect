package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
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
    public TextField clubNameField;
    public TextArea descriptionField;
    public ComboBox selectClubField;
    public ComboBox categoryField;
    public ListView interestListView;
    public AnchorPane CreateNewPane;
    public AnchorPane JoinExistingPane;
    public ToggleGroup clubOptionGroup;
    public TextField maxMemberField;
    @FXML
    private RadioButton studentRadio;
    @FXML
    RadioButton joinExistingRadio;
    
    @FXML
    RadioButton createNewRadio;

    @FXML
    private RadioButton clubRadio;

    @FXML
    private AnchorPane StudentField;

    @FXML
    private AnchorPane ClubField;

    @FXML
    public void initialize() {
        clubRadio.setOnAction(e -> {
            ClubField.setVisible(true);
            StudentField.setVisible(false);
        });

        studentRadio.setOnAction(e -> {
            StudentField.setVisible(true);
            ClubField.setVisible(false);
        });

        joinExistingRadio.setOnAction(e -> {
            JoinExistingPane.setVisible(true);
            CreateNewPane.setVisible(false);
        });

        createNewRadio.setOnAction(e -> {
            CreateNewPane.setVisible(true);
            JoinExistingPane.setVisible(false);
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
        String enrollmentNo = enrollmentField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (studentRadio.isSelected()) {
            String department = departmentField.getText();
            String semester = semesterField.getText();
            List<String> interest = interestListView.getSelectionModel().getSelectedItems();
            if (interest == null || interest.isEmpty()) {
                FXMLScreenLoader.showError("❌ Please select at least one interest.");
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
                    FXMLScreenLoader.showError("❌ Registration failed. Please try again.");
                }
            }
            else{

            }
        } else if (clubRadio.isSelected()) {
            // Handle club registration logic here
            String clubName=clubNameField.getText().trim();
            String description=descriptionField.getText().trim();
            String selectClub=(String)selectClubField.getValue();
            String category=(String)categoryField.getValue();

            if(validateClubFields(name,email,password,confirmPassword,clubName,description,category,enrollmentNo)){
                Club club = new Club(clubName, description, selectClub, category,30);
                ClubMember clubMember = new ClubMember(enrollmentNo, name, email, password, "club_member".toUpperCase(),"head",club.getClubId());
            }
        }
        else{
            FXMLScreenLoader.showError("Please select a role (Student or Club Member) to register.");
        }

    }

    public boolean validateFields(String name, String email,String enrollmentNo, String password, String confirmPassword) {
        boolean isValid = true;

        if (!ValidationUtils.checkName(name))
        {
            nameField.clear();
            FXMLScreenLoader.showError("Name must contain only letters.");
            isValid = false;
        }

        if (!ValidationUtils.checkEmail(email)) {
            emailField.clear();
            FXMLScreenLoader.showError("Please enter a valid email.");
            isValid = false;
        }

        if(ValidationUtils.checkEnrollment(enrollmentNo)){
            enrollmentField.clear();
            FXMLScreenLoader.showError("Enrollment number must contain only digits.");
            isValid = false;
        }

        if (!ValidationUtils.checkPassword(password)) {
            passwordField.clear();
            FXMLScreenLoader.showError("Password must be strong (min 8 chars, mix of A-Z, a-z, 0-9, special char).");
            isValid = false;
        }

        if (!ValidationUtils.isMatchingPasswords(password, confirmPassword)) {
            passwordField.clear();
            confirmPasswordField.clear();
            FXMLScreenLoader.showError("Password and Confirm Password do not match.");
            isValid = false;
        }

        return isValid;
    }

    public boolean validateStudentFields(String name, String email, String password, String confirmPassword, String department, String semester, String enrollmentNo) {
        if (!validateFields(name, email,enrollmentNo, password, confirmPassword)) {
            return false; // General validations failed
        }

        if (!ValidationUtils.checkDepartment(department)) {
            departmentField.clear();
            FXMLScreenLoader.showError("❌ Department cannot be empty or invalid.");
            return false;
        }

        if (!ValidationUtils.checkSemester(semester)) {
            semesterField.clear();
            FXMLScreenLoader.showError("❌ Semester must be a number between 1 and 8.");
            return false;
        }

        return true;
    }

    public boolean validateClubFields(String name, String email, String password, String confirmPassword, String clubName, String description,String category,String enrollmentNo){
        if (!validateFields(name, email,enrollmentNo, password, confirmPassword)) {
            return false; // General validations failed
        }

        if(ValidationUtils.checkClubName(clubName)){
            clubNameField.clear();
            FXMLScreenLoader.showError("❌ Club name must contain only letters.");
            return false;
        }
        if (ValidationUtils.checkDescription(description)) {
            descriptionField.clear();
            FXMLScreenLoader.showError("❌ Description cannot be empty.");
            return false;
        }

        if(!ValidationUtils.checkCategory(category)) {
            FXMLScreenLoader.showError("❌ Please select a valid category.");
            return false;
        }
        return true;
    }

}
