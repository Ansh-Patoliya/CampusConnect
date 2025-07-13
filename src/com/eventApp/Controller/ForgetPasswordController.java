package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import javafx.event.ActionEvent;

public class ForgetPasswordController {

    public void openLoginPage(ActionEvent event) {
        FXMLScreenLoader.openLoginPage(event);
    }
}
