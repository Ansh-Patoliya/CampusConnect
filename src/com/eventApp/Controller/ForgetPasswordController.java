package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ForgetPasswordController {

    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public TextField emailField;

    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    private final UserService userService=new UserService();
    public void handleForgotPassword(ActionEvent event) {
        String email=emailField.getText();
        String password=passwordField.getText();
        String confirmPassword=confirmPasswordField.getText();

        if(validateField(email,password,confirmPassword)){
            userService.resetPassword(email,password,confirmPassword);
        }
    }

    public boolean validateField(String email,String password,String confirmPassword){
        if(!ValidationUtils.checkEmail(email)){
            emailField.clear();
            FXMLScreenLoader.showError("Please enter a valid email.");
        }
        if(!ValidationUtils.checkPassword(password)){
            passwordField.clear();
            confirmPasswordField.clear();
            FXMLScreenLoader.showError("Please enter a valid password.");
        }
         if(!password.equals(confirmPassword)){
            passwordField.clear();
            confirmPasswordField.clear();
            FXMLScreenLoader.showError("Passwords do not match.");
        }

        return true;
    }
}
