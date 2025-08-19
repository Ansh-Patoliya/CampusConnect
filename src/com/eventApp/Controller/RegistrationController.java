package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
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

import java.sql.SQLException;
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
        List<String> clubs = null;
        try {
            clubs = clubDAO.getAllClubNames();
            if (clubs != null && !clubs.isEmpty()) {
                selectClubField.getItems().addAll(clubs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "registration", "error");
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
            handleStudentRegistration(event, enrollmentNo, name, email, password, confirmPassword);
        } else if (clubRadio.isSelected()) {
            if (joinExistingRadio.isSelected()) {
                handleJoinExistingClub(event, enrollmentNo, name, email, password);
            } else if (createNewRadio.isSelected()) {
                handleCreateNewClub(event, enrollmentNo, name, email, password, confirmPassword);
            } else {
                FXMLScreenLoader.showMessage("❌ Please select a club registration option.", "registration", "error");
            }
        } else {
            FXMLScreenLoader.showMessage("❌ Please select a registration option.", "registration", "error");
        }
    }

    private void handleCreateNewClub(ActionEvent event, String enrollmentNo, String name, String email, String password, String confirmPassword) {
        // Handle club registration logic here
        String clubName = clubNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String category = (String) categoryField.getValue();
        int maxMember = Integer.parseInt(maxMemberField.getText());

        if(maxMember <= 0) {
            throw new NumberFormatException("❌ Maximum members must be greater than 0.");
        }

        if (validateClubFields(name, email, password, confirmPassword, clubName, description, category, enrollmentNo)) {
            User user = new User(enrollmentNo, name, email, password, "CLUB_MEMBER");
            Club club = new Club(clubName, description, category, enrollmentNo, maxMember);
            ClubMember clubMember = new ClubMember(enrollmentNo, name, email, password, "CLUB_MEMBER", "President", club.getClubId());
            try {
                userService.registerClub(club, clubMember, user);

                FXMLScreenLoader.openLoginPage(event);
                FXMLScreenLoader.showMessage("✅ Club registration successful! You can now log in.", "registration", "info");

            } catch (DatabaseExceptionHandler | SQLException | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "registration", "error");
            }
            catch (NumberFormatException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "maxMember", "error");
                maxMemberField.clear();
            }
        }
    }

    private void handleStudentRegistration(ActionEvent event, String enrollmentNo, String name, String email, String password, String confirmPassword) {
        try {
            if (departmentField.getValue() == null) {
                throw new ValidationException("❌ Please select a department.");
            }
            String department = departmentField.getValue().toString();
            int semester = Integer.parseInt(semesterField.getText());

            List<String> interest = interestListView.getSelectionModel().getSelectedItems();
            if (interest == null || interest.isEmpty()) {
                throw new ValidationException("❌ Please select at least one interest.");
            }

            if (validateStudentFields(name, email, password, confirmPassword, department, enrollmentNo)) {
                User user = new User(enrollmentNo, name, email, password, "student".toUpperCase());
                Student student = new Student(enrollmentNo, name, email, password, "student".toUpperCase(), department, semester, interest);

                userService.registerStudent(student, user);
                FXMLScreenLoader.openLoginPage(event);
                FXMLScreenLoader.showMessage("✅ Registration successful! You can now log in.", "registration", "info");
            }
        } catch (DatabaseExceptionHandler | ValidationException | SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "registration", "error");
        } catch (NumberFormatException e) {
            FXMLScreenLoader.showMessage("❌ Please enter a valid semester number.", "semester", "error");
            semesterField.clear();
        }
    }

    ClubDAO clubDAO = new ClubDAO();
    private void handleJoinExistingClub(ActionEvent event, String enrollmentNo, String name, String email, String password) {
        // Handle joining existing club logic here
        String selectClub = (String) selectClubField.getValue();
        if (!(selectClub == null || selectClub.isEmpty())) {
            try {
                int clubId = clubDAO.getClubIdBy(selectClub);
                User user = new User(enrollmentNo, name, email, password, "club_member".toUpperCase());
                ClubMember clubMember = new ClubMember(enrollmentNo, name, email, password, "club_member".toUpperCase(), "Member", clubId);

                userService.registerClubMember(clubMember, user);
                FXMLScreenLoader.openLoginPage(event);
                FXMLScreenLoader.showMessage("✅ Successfully joined the club! You can now log in.", "registration", "info");

            } catch (DatabaseExceptionHandler | SQLException | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "registration", "error");
            }
        } else {
            FXMLScreenLoader.showMessage("❌ Please select a club to join.", "registration", "error");
        }
    }

    public boolean validateFields(String name, String email, String enrollmentNo, String password, String confirmPassword) {
        try {
            ValidationUtils.checkName(name);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "name", "error");
            nameField.clear();
            return false;
        }

        try {
            ValidationUtils.checkEmail(email);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "email", "error");
            emailField.clear();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "email", "error");
        }

        try {
            ValidationUtils.checkEnrollment(enrollmentNo);
        } catch (ValidationException | SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "enrollment", "error");
            enrollmentField.clear();
            return false;
        }

        try {
            ValidationUtils.checkPassword(password);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "password", "error");
            passwordField.clear();
            return false;
        }

        try {
            ValidationUtils.isMatchingPasswords(password, confirmPassword);
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "confirmPassword", "error");
            confirmPasswordField.clear();
            return false;
        }

        return true;
    }

    public boolean validateStudentFields(String name, String email, String password, String confirmPassword, String department, String enrollmentNo) {
        return validateFields(name, email, enrollmentNo, password, confirmPassword); // General validations failed
    }

    public boolean validateClubFields(String name, String email, String password, String confirmPassword, String clubName, String description, String category, String enrollmentNo) {
        if (!validateFields(name, email, enrollmentNo, password, confirmPassword)) {
            return false; // General validations failed
        }

        try {
            ValidationUtils.checkDescription(description);
        } catch (ValidationException e) {
            descriptionField.clear();
            FXMLScreenLoader.showMessage(e.getMessage(), "description", "error");
            return false;
        }

        try {
            ValidationUtils.checkClubName(clubName);
        } catch (ValidationException e) {
            clubNameField.clear();
            FXMLScreenLoader.showMessage(e.getMessage(), "clubName", "error");
            return false;
        }
        return true;
    }
}
