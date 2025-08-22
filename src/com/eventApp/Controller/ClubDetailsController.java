package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Service.impl.ClubServiceImpl;
import com.eventApp.Utils.CurrentUser;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ClubDetailsController {
    public Label clubNameLabel;
    public Label categoryLabel;
    public Label descriptionLabel;
    public Label founderNameLabel;
    public Label memberCountLabel;

    // ✅ Back button
    @FXML private Button backButton;

    private User user;
    private final ClubService clubService = new ClubServiceImpl();

    @FXML
    public void initialize() {
        user = CurrentUser.getCurrentUser();
        loadClubDetails();

        // ✅ Add animation to back button
        addButtonAnimation(backButton);
    }

    private void loadClubDetails() {
        Club club = clubService.getClubByUser(user);
        UserDAO userDAO = new UserDAO();
        if (club != null) {
            clubNameLabel.setText(club.getClubName());
            categoryLabel.setText(club.getCategory());
            descriptionLabel.setText(club.getDescriptions());
            founderNameLabel.setText(userDAO.getUserNameBy(user.getUserId()));
            memberCountLabel.setText(String.valueOf(club.getMemberCount()));
        }
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openClubDashboard(actionEvent);
    }

    // ✅ Animation logic for button
    private void addButtonAnimation(Button button) {
        if (button == null) return;

        // Hover in → scale up
        button.setOnMouseEntered(e -> animateScale(button, 1.0, 1.08));
        // Hover out → scale back
        button.setOnMouseExited(e -> animateScale(button, 1.08, 1.0));
        // Press → shrink
        button.setOnMousePressed(e -> animateScale(button, 1.08, 0.95));
        // Release → back to hover scale
        button.setOnMouseReleased(e -> animateScale(button, 0.95, 1.08));
    }

    private void animateScale(Button button, double from, double to) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }
}
