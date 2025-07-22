package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.StudentService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class StudentController {

    public TextArea interestArea;
    private User user;
    private StudentService studentService = new StudentService();

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label departmentLabel;
    @FXML private Label semesterLabel;

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        // You can perform any additional initialization here if needed
        user= CurrentUser.getCurrentUser() ;// Assuming User class has a method to get the current logged-in user
        loadStudentProfile();
    }

    private void loadStudentProfile() {
        if (user != null && "student".equalsIgnoreCase(user.getRole())) {
            Student student = studentService.getStudentByUser(user);
            if (student != null) {
                nameLabel.setText(student.getName());
                emailLabel.setText(student.getEmail());
                departmentLabel.setText(student.getDepartment());
                semesterLabel.setText(String.valueOf(student.getSemester()));
                List<String> interests = student.getInterest();
                interestArea.setText(interests != null ? String.join(", ", interests) : "");
            }
        }
    }

    public void onBack(ActionEvent event) {
        // Logic to go back to the previous screen, e.g., Student Dashboard
        // This could be implemented using a method from FXMLScreenLoader or similar
        FXMLScreenLoader.openStudentDashboard(event);
    }
}
