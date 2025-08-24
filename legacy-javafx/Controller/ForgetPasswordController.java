package com.eventApp.Controller;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Service.UserService;
import com.eventApp.Service.impl.UserServiceImpl;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ForgetPasswordController {

    private final UserService userService = new UserServiceImpl();

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Button backBtn;
    @FXML
    private Button continueBtn;

    @FXML
    public void initialize() {
        // Neon glow effects for input fields
        addHoverGlow(emailField, "#9b59b6", "#654ea3");
        addHoverGlow(passwordField, "#9b59b6", "#654ea3");
        addHoverGlow(confirmPasswordField, "#9b59b6", "#654ea3");

        // Neon glow effects for buttons
        addButtonHoverGlow(backBtn, "#ffffff", "#9b59b6");
        addButtonHoverGlow(continueBtn, "#8e44ad", "#9b59b6");
    }

    // Glow for text fields
    private void addHoverGlow(TextField field, String hoverColor, String focusColor) {
        String baseStyle = field.getStyle();

        field.setOnMouseEntered(e ->
                field.setStyle(baseStyle + " -fx-border-color: " + hoverColor +
                        "; -fx-effect: dropshadow(gaussian, " + hoverColor + ", 15, 0.6, 0, 0);"));

        field.setOnMouseExited(e -> field.setStyle(baseStyle));

        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { // focused
                field.setStyle(baseStyle + " -fx-border-color: " + focusColor +
                        "; -fx-effect: dropshadow(gaussian, " + focusColor + ", 18, 0.7, 0, 0);");
            } else {
                field.setStyle(baseStyle);
            }
        });
    }

    // Glow for buttons
    private void addButtonHoverGlow(Button btn, String hoverColor, String glowColor) {
        String baseStyle = btn.getStyle();

        btn.setOnMouseEntered(e ->
                btn.setStyle(baseStyle + " -fx-effect: dropshadow(gaussian, " + glowColor + ", 18, 0.7, 0, 0);"));

        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    // Back button → go to loginpage
    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    // Continue button → reset password
    public void handleForgotPassword(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        try {
            ValidationUtils.checkPassword(password);
            ValidationUtils.isMatchingPasswords(password, confirmPassword);
            userService.resetPassword(email, password);

            FXMLScreenLoader.showMessage("Password reset successfully!", "Success", "info");
            FXMLScreenLoader.openLoginPage(event);

        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
            passwordField.clear();
            confirmPasswordField.clear();

        } catch (SQLException | DatabaseExceptionHandler | ClassNotFoundException e) {
            emailField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
        }
    }
}
