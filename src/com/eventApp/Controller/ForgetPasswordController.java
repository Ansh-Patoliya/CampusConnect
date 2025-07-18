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

        if(userService.resetPassword(email,password,confirmPassword)){
            FXMLScreenLoader.openLoginPage(event);
        } else {
            // Handle the case where the password reset failed
            // You can show an error message or take appropriate action
            FXMLScreenLoader.showMessage("Password reset failed. Please try again.","Error","error");
        }
    }

    public boolean validateField(String email,String password,String confirmPassword){
        if(!ValidationUtils.checkEmail(email)){
            emailField.clear();
        }
        if(!ValidationUtils.checkPassword(password)){
            passwordField.clear();
            confirmPasswordField.clear();
        }
         if(!password.equals(confirmPassword)){
            passwordField.clear();
            confirmPasswordField.clear();
        }

        return true;
    }
}
