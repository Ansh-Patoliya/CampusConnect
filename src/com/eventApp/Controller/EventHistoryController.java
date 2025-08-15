package com.eventApp.Controller;

import com.eventApp.DAO.StudentDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Utils.CurrentUser;
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
    private MyEventLL eventList;

    public void initialize(){
        eventHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
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

//        creatorCol.setCellValueFactory(cellData ->
//                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));
        creatorCol.setCellValueFactory(cellData -> {
            String userId = cellData.getValue().getUserId();
            System.out.println(userId);
            String username = new UserDAO().getUserNameBy(userId);
            System.out.println(username);
            return new javafx.beans.property.SimpleStringProperty(username);
        });

        categoryCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));
    }

    private void loadEventList() {
        User user = CurrentUser.getCurrentUser();
        this.eventList = studentDAO.viewEventsHistory(user.getUserId());
    }

    private void showDataInTable() {
        ObservableList<Event> observableEvents = FXCollections.observableArrayList();
        if (eventList != null && !eventList.isEmpty()) {
            for (int i = 0; i < eventList.size(); i++) {
                observableEvents.add(eventList.get(i));
            }
        }
        eventHistoryTable.setItems(observableEvents);
    }

    public void onBack(ActionEvent actionEvent) { FXMLScreenLoader.openStudentDashboard(actionEvent);}
}
