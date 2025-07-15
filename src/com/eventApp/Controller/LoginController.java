package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField username;
    public Button signIn;
    public Hyperlink forgotPassword;
    public PasswordField password;
    public Button signUp;

    public void openSignupPage(ActionEvent actionEvent) {
        FXMLScreenLoader.openSignupPage(actionEvent);
    }

    public void handleSignIn(ActionEvent event) {
        String emailInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        UserService userService=new UserService();
        if (ValidationUtils.checkEmail(emailInput) && ValidationUtils.checkPassword(passwordInput)) {
            FXMLScreenLoader.showError("✅Login Successful");
            //code to move forward towards dashboard
            userService.checklogin(emailInput,passwordInput);
        }
        else{
            FXMLScreenLoader.showError("❌Invalid login id or password");
        }
    }

    public void openForgotPassword(ActionEvent actionEvent) {
        FXMLScreenLoader.openForgotPassword(actionEvent);
    }
}
