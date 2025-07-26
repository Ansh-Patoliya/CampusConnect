package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

public class RegistrationController {

    private final UserService userService = new UserService();
    public TextField nameField;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public ToggleGroup RollGroup;
    public ComboBox departmentField;
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
    RadioButton joinExistingRadio;

    @FXML
    RadioButton createNewRadio;
    @FXML
    private RadioButton studentRadio;
    @FXML
    private RadioButton clubRadio;
    @FXML
    private AnchorPane StudentField;
    @FXML
    private AnchorPane ClubField;
    private UserDAO userDAO = new UserDAO();

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


        categoryField.getItems().addAll("Tech", "Cultural", "Sports", "Literature");
        categoryField.setValue("Tech"); // Optional default

        interestListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadExistingClubs();

        List<String> department = Arrays.asList(
                // Engineering & Technology
                "Computer Engineering",
                "Information Technology",
                "Electronics & Communication Engineering",
                "Electrical Engineering",
                "Mechanical Engineering",
                "Civil Engineering",
                "Chemical Engineering",
                "Biomedical Engineering",
                "Mechatronics Engineering",
                "Automobile Engineering",

                // Science
                "Physics",
                "Chemistry",
                "Mathematics",
                "Biotechnology",
                "Microbiology",
                "Environmental Science",

                // Commerce & Management
                "Accounting",
                "Finance",
                "Marketing",
                "Business Administration",
                "Economics",
                "Human Resource Management",

                // Arts & Humanities
                "English Literature",
                "History",
                "Political Science",
                "Psychology",
                "Sociology",
                "Philosophy",

                // Law & Education
                "Law",
                "Education",
                "Library Science",

                // Medical & Health Sciences
                "Nursing",
                "Pharmacy",
                "Physiotherapy",
                "Public Health",
                "Dentistry",

                // Others
                "Hotel Management",
                "Journalism and Mass Communication",
                "Design",
                "Fashion Technology",
                "Animation",
                "Fine Arts"
        );

        List<String> clubCategories = Arrays.asList(
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


        departmentField.getItems().addAll(department);
        categoryField.getItems().addAll(clubCategories);

    }

    private void loadExistingClubs() {
        List<String> clubs = userDAO.getAllClubsName();
        if (clubs != null && !clubs.isEmpty()) {
            selectClubField.getItems().addAll(clubs);
        }
    }

    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }


    public void handleRegistration(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String enrollmentNo = enrollmentField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (studentRadio.isSelected()) {
            if (departmentField.getValue() == null) {
                FXMLScreenLoader.showMessage("❌ Please select a department.", "department", "error");
                return;
            }
            String department = departmentField.getValue().toString();
            String semester = semesterField.getText();
            List<String> interest = interestListView.getSelectionModel().getSelectedItems();
            if (interest == null || interest.isEmpty()) {
                FXMLScreenLoader.showMessage("❌ Please select at least one interest.", "title", "error");
                return;
            }
            if (validateStudentFields(name, email, password, confirmPassword, department, semester, enrollmentNo)) {
                int sem = Integer.parseInt(semester);
                User user = new User(enrollmentNo, name, email, password, "student".toUpperCase());
                Student student = new Student(enrollmentNo, name, email, password, "student".toUpperCase(), department, sem, interest);
                boolean success = userService.registerStudent(student, user);
                if (success) {
                    FXMLScreenLoader.openLoginPage(event);
                    FXMLScreenLoader.showMessage("✅ Registration successful! You can now log in.", "registration", "info");
                    return;
                }
                FXMLScreenLoader.showMessage("❌ Registration failed. Please try again.", "registration", "error");
            }
        } else if (clubRadio.isSelected()) {

            if (joinExistingRadio.isSelected()) {
                // Handle joining existing club logic here
                String selectClub = (String) selectClubField.getValue();
                if (!(selectClub == null || selectClub.isEmpty())) {
                    int clubId = UserDAO.getClubId(selectClub);
                    User user = new User(enrollmentNo, name, email, password, "club_member".toUpperCase());
                    ClubMember clubMember = new ClubMember(enrollmentNo, name, email, password, "club_member".toUpperCase(), "Member", clubId);
                    boolean success = userService.registerClubMember(clubMember, user);
                    if (success) {
                        FXMLScreenLoader.openLoginPage(event);
                        FXMLScreenLoader.showMessage("✅ Successfully joined the club! You can now log in.", "registration", "info");
                    } else {
                        FXMLScreenLoader.showMessage("❌ Registration failed. Please try again.", "registration", "error");
                    }
                } else {
                    FXMLScreenLoader.showMessage("❌ Please select a club to join.", "registration", "error");
                }
            } else if (createNewRadio.isSelected()) {
                // Handle club registration logic here
                String clubName = clubNameField.getText().trim();
                String description = descriptionField.getText().trim();
                String category = (String) categoryField.getValue();
                String maxMember = maxMemberField.getText();
                int maxMembers = 0;
                if (ValidationUtils.maxMembers(maxMember)) {
                    maxMembers = Integer.parseInt(maxMember);
                }

                if (validateClubFields(name, email, password, confirmPassword, clubName, description, category, enrollmentNo)) {
                    User user = new User(enrollmentNo, name, email, password, "club_member".toUpperCase());
                    Club club = new Club(clubName, description, category, enrollmentNo, maxMembers);
                    ClubMember clubMember = new ClubMember(enrollmentNo, name, email, password, "club_member".toUpperCase(), "President", club.getClubId());
                    boolean success = userService.registerClub(club, clubMember, user);
                    if (success) {
                        FXMLScreenLoader.openLoginPage(event);
                        FXMLScreenLoader.showMessage("✅ Club registration successful! You can now log in.", "registration", "info");
                    } else {
                        FXMLScreenLoader.showMessage("❌ Registration failed. Please try again.", "registration", "error");
                    }
                }
            }
            else{
                FXMLScreenLoader.showMessage("❌ Please select a club registration option.", "registration", "error");
            }

        } else {
            FXMLScreenLoader.showMessage("❌ Please select a registration option.", "registration", "error");
        }
    }

    public boolean validateFields(String name, String email, String enrollmentNo, String password, String confirmPassword) {
        boolean isValid = true;

        if (!ValidationUtils.checkName(name)) {
            nameField.clear();
            isValid = false;
        }

        if (!ValidationUtils.checkEmail(email)) {
            emailField.clear();
            isValid = false;
        }

        if (!ValidationUtils.checkEnrollment(enrollmentNo)) {
            enrollmentField.clear();
            isValid = false;
        }

        if (!ValidationUtils.checkPassword(password)) {
            passwordField.clear();
            FXMLScreenLoader.showMessage("❌ Password must be at least 8 characters long, contain uppercase and lowercase letters, a number, and a special character.", "password", "error");
            isValid = false;
        }

        if (!ValidationUtils.isMatchingPasswords(password, confirmPassword)) {
            passwordField.clear();
            confirmPasswordField.clear();
            FXMLScreenLoader.showMessage("Password and Confirm Password do not match.", "password", "error");
            isValid = false;
        }

        return isValid;
    }

    public boolean validateStudentFields(String name, String email, String password, String confirmPassword, String department, String semester, String enrollmentNo) {
        if (!validateFields(name, email, enrollmentNo, password, confirmPassword)) {
            return false; // General validations failed
        }

        if (!ValidationUtils.checkSemester(semester)) {
            semesterField.clear();
            return false;
        }

        return true;
    }

    public boolean validateClubFields(String name, String email, String password, String confirmPassword, String clubName, String description, String category, String enrollmentNo) {
        if (!validateFields(name, email, enrollmentNo, password, confirmPassword)) {
            return false; // General validations failed
        }

        if (!ValidationUtils.checkClubName(clubName)) {
            clubNameField.clear();
            return false;
        }
        if (!ValidationUtils.checkDescription(description)) {
            descriptionField.clear();
            FXMLScreenLoader.showMessage("❌ Description cannot be empty.", "description", "error");
            return false;
        }

        if (!ValidationUtils.checkCategory(category)) {
            FXMLScreenLoader.showMessage("❌ Please select a valid category.", "category", "error");
            return false;
        }
        return true;
    }
}
