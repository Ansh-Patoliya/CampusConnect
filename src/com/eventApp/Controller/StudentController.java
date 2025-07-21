package com.eventApp.Controller;

import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class StudentController {

    private User user;
    private StudentService studentService = new StudentService();

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label departmentLabel;
    @FXML private Label semesterLabel;
    @FXML private Label interestLabel;

    public void setUser(User user) {
        this.user = user;
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
                interestLabel.setText(interests != null ? String.join(", ", interests) : "");
            }
        }
    }
}
