package com.eventApp.Controller;


import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.DAO.AdminDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class ViewEventsController {


    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> nameCol;
    @FXML
    private TableColumn<Event, String> statusCol;
    @FXML
    private TableColumn<Event, String> dateCol;
    @FXML
    private TableColumn<Event, String> creatorCol;
    @FXML
    private TableColumn<Event, String> completedCol;
    @FXML
    private Button refreshBtn;

    private MyEventLL eventList;

    private final AdminDAO adminDAO = new AdminDAO();

    @FXML
    public void initialize() {
        loadEventList();
        setupColumns();

        refreshBtn.setOnAction(e -> {
            loadEventList();
            showDataInTable();
        });

        showDataInTable();
    }

    private void loadEventList() {
        this.eventList = adminDAO.getEventList();
    }

    private void setupColumns() {
        nameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEventName()));

        statusCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApprovalStatus()));

        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        creatorCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));

        completedCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getCompletionStatus() ));

    }

    private void showDataInTable() {
        ObservableList<Event> observableEvents = FXCollections.observableArrayList();
        if (eventList != null && eventList.size() > 0) {
            for (int i = 0; i < eventList.size(); i++) {
                observableEvents.add(eventList.get(i));
            }
        }
        eventTable.setItems(observableEvents);
    }

    public void onBack(ActionEvent event) {
        FXMLScreenLoader.openAdminDashboard(event);
    }
}
