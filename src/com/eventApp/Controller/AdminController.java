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
import com.eventApp.Model.Club;
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

    @FXML
    private ListView<String> clubListView;

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

    public void handleViewClub(ActionEvent actionEvent) {
        clubListView.setVisible(true);
        Queue<Club> clubQueue = null;// method call

        ObservableList<String> eventList = FXCollections.observableArrayList();

        //print clubListView in gui
        for (Club c : clubQueue) {
            String formattedEvent = c.getClubName();
            eventList.add(formattedEvent);
        }

        eventListView.setItems(eventList);
    }
}
