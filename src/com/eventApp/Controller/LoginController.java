package com.eventApp.Controller;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.EventRegistrationDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.User;
import com.eventApp.Service.UserService;
import com.eventApp.Service.impl.UserServiceImpl;
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

    public void initialize() {
        // Neon glow effects
        addHoverGlow(username, "#9b59b6", "#654ea3");
        addHoverGlow(password, "#9b59b6", "#654ea3");
        addButtonHoverGlow(signIn, "#8e44ad", "#9b59b6");
        addButtonHoverGlow(signUp, "#ffffff", "#654ea3");
    }

    private void addHoverGlow(TextField field, String hoverColor, String focusColor) {
        String baseStyle = field.getStyle();

        // Hover effect
        field.setOnMouseEntered(e ->
                field.setStyle(baseStyle + " -fx-border-color: " + hoverColor +
                        "; -fx-effect: dropshadow(gaussian, " + hoverColor + ", 15, 0.6, 0, 0);"));

        field.setOnMouseExited(e ->
                field.setStyle(baseStyle));

        // Focus effect (when typing)
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { // focused
                field.setStyle(baseStyle + " -fx-border-color: " + focusColor +
                        "; -fx-effect: dropshadow(gaussian, " + focusColor + ", 18, 0.7, 0, 0);");
            } else {
                field.setStyle(baseStyle);
            }
        });
    }

    private void addButtonHoverGlow(Button btn, String hoverColor, String glowColor) {
        String baseStyle = btn.getStyle();

        btn.setOnMouseEntered(e ->
                btn.setStyle(baseStyle + " -fx-effect: dropshadow(gaussian, " + glowColor + ", 18, 0.7, 0, 0);"));

        btn.setOnMouseExited(e ->
                btn.setStyle(baseStyle));
    }

    // ---------------- Existing methods ----------------
    public void openSignupPage(ActionEvent actionEvent) {
        FXMLScreenLoader.openSignupPage(actionEvent);
    }

    public void handleSignIn(ActionEvent event) {
        String emailInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        UserService userService=new UserServiceImpl();

        if(userService.checklogin(emailInput,passwordInput)){
            User user = userService.getUserByEmail(emailInput);
            if (user != null) {
                new EventRegistrationDAO().getFinalTicketPrice();
                CurrentUser.setCurrentUser(user);
                if (user.getRole().equalsIgnoreCase("admin")) {
                    FXMLScreenLoader.showMessage("🔐 Welcome,"+ user.getName()+"! Login Successful.", "Login", "info");
                    FXMLScreenLoader.openAdminDashboard(event);
                } else if (user.getRole().equalsIgnoreCase("club_member")) {
                    if(new ClubMemberDAO().isPresidentOfApprovedClub(user.getUserId())){
                        FXMLScreenLoader.showMessage("🔐 Welcome,"+ user.getName()+"! Login Successful.", "Login", "info");
                        FXMLScreenLoader.openClubDashboard(event);
                    } else{
                        FXMLScreenLoader.showMessage("❌Your club is not approved yet", "login", "warning");
                    }
                } else if (user.getRole().equalsIgnoreCase("student")) {
                    FXMLScreenLoader.showMessage("🔐 Welcome,"+ user.getName()+"! Login Successful.", "Login", "info");
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
