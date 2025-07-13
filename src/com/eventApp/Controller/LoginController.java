package com.eventApp.Controller;

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
        openSignupPage(actionEvent);
    }

    public void handleSignIn(ActionEvent event) {
        String emailInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        if (ValidationUtils.checkEmail(emailInput) && ValidationUtils.checkPassword(passwordInput)) {
            //code to move forward towards dashboard
        }
        else{
            System.out.println("Invalid login id or password");
        }
    }
}
