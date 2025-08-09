package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MyParticipationController {

    public TableView<Event> eventTable;
    public TableColumn<Event, String> nameCol;
    public TableColumn<Event, String> dateCol;
    public TableColumn<Event, String> creatorCol;
    public TableColumn<Event, String> priceCol;
    private Button refreshBtn;
    private Event myEventList;

   //loadEventList

    private void showDataInTable() {
        ObservableList<Event> observableEvents = FXCollections.observableArrayList();
       //enter data in list
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openStudentDashboard(actionEvent);
    }
}
