package com.eventApp.Controller;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Service.UserService;
import com.eventApp.Utils.CurrentUser;
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

            if(userService.checklogin(emailInput,passwordInput)){
                User user = userService.getUserByEmail(emailInput);
                if (user != null) {
                    CurrentUser.setCurrentUser(user);
                    FXMLScreenLoader.showMessage("✅Login successful", "login","info");
                    if (user.getRole().equalsIgnoreCase("admin")) {
                        FXMLScreenLoader.openAdminDashboard(event);
                    } else if (user.getRole().equalsIgnoreCase("club_member")) {
                        if(new ClubMemberDAO().isPresidentOfApprovedClub(user.getUserId())){
                            FXMLScreenLoader.openClubDashboard(event);
                        } else{
                            FXMLScreenLoader.showMessage("❌Your club is not approved yet", "login", "warning");
                        }

                    } else if (user.getRole().equalsIgnoreCase("student")) {
                        FXMLScreenLoader.openStudentDashboard(event);
                    }
                } else {
                    FXMLScreenLoader.showMessage("❌User not found", "login", "error");
                }
            }
            else{
                FXMLScreenLoader.showMessage("❌Invalid email or password", "login", "error");
            }

    }

    public void openForgotPassword(ActionEvent actionEvent) {
        FXMLScreenLoader.openForgotPassword(actionEvent);
    }
}
