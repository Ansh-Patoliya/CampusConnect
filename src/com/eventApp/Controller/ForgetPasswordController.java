package com.eventApp.Controller;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ForgetPasswordController {

    private final UserService userService = new UserService();
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public TextField emailField;

    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleForgotPassword(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        try {
            ValidationUtils.checkPassword(password);
            userService.resetPassword(email, password, confirmPassword);
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
