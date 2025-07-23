package com.eventApp.Controller;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;

public class EventHistoryController {

    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public TableView<Event> eventHistoryTable;
    public TableColumn<Event, String> nameCol;
    public TableColumn<Event, String> dateCol;
    public TableColumn<Event, String> creatorCol;
    public TableColumn<Event, String> categoryCol;
    public Button refreshBtn;
    private MyEventLL eventList;

    public void initialize(){
        loadEventList();
        setupColumns();
        showDataInTable();
    }

    private void setupColumns() {
        nameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEventName()));

        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        creatorCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));
    }

    private void loadEventList() {
        this.eventList = studentDAO.viewEventsHistory();
    }

    private void showDataInTable() {
        ObservableList<Event> observableEvents = FXCollections.observableArrayList();
        if (eventList != null && eventList.size() > 0) {
            for (int i = 0; i < eventList.size(); i++) {
                observableEvents.add(eventList.get(i));
            }
        }
        eventHistoryTable.setItems(observableEvents);
    }

    public void onBack(ActionEvent actionEvent) { FXMLScreenLoader.openStudentDashboard(actionEvent);}
}
