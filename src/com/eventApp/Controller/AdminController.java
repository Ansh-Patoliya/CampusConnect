package com.eventApp.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import com.eventApp.Model.Event;

import java.time.format.DateTimeFormatter;
import java.util.Queue;

public class AdminController {
    public MenuButton optionsButton;
    public MenuItem viewProfileItem;
    public MenuItem logoutItem;
    public Button viewEventButton;
    public Button viewClubButton;
    public Button viewClubMemberButton;
    public Button exportClubListButton;
    public Button backButton;

    @FXML
    private ListView<String> eventListView;

    public void initialize(){
        eventListView.setVisible(false);
    }

    public void handleViewEvent(ActionEvent actionEvent) {
        eventListView.setVisible(true);
        Queue<Event> eventQueue = null;// method call

        ObservableList<String> eventList = FXCollections.observableArrayList();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        //print EventListView in gui
        for (Event e : eventQueue) {
            String formattedEvent = e.getEventName() + " • " + dateFormatter.format(e.getEventDate());
            eventList.add(formattedEvent);
        }

        eventListView.setItems(eventList);
    }

}
