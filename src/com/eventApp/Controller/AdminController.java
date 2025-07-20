package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import com.eventApp.Model.Event;
import com.eventApp.Model.Club;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

public class AdminController {

    public void handleClubApproval(ActionEvent event) {
        FXMLScreenLoader.openClubApproval(event);
    }
}
