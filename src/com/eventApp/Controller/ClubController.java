package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import javafx.event.ActionEvent;

public class ClubController {
    public void onLogin(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }

    public void handleEventCreate(ActionEvent event) {
        FXMLScreenLoader.openEventRegistration(event);
    }
}
