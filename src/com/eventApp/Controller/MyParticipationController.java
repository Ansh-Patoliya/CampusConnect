package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Service.StudentService;
import com.eventApp.Utils.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class MyParticipationController {

    public TableView<Event> eventTable;
    public TableColumn<Event, String> nameCol;
    public TableColumn<Event, String> dateCol;
    public TableColumn<Event, String> creatorCol;
    public TableColumn<Event, String> priceCol;

    ClubDAO clubDAO = new ClubDAO();
    StudentService studentService = new StudentService();
    List<Event> eventList;
    User currentUser = CurrentUser.getCurrentUser();
    public void initialize() {
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setupColumns();
        showDataInTable();
    }

    private void setupColumns() {
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEventName()));
        dateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEventDate().toString()));
        creatorCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(clubDAO.getClubNameBy(cellData.getValue().getClubId())));
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getTicketPrice())));
    }

    private void showDataInTable() {
        eventList= studentService.myEventListByDate(currentUser.getUserId());
        ObservableList<Event> observableList = FXCollections.observableArrayList(eventList);
        eventTable.setItems(observableList);
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openStudentDashboard(actionEvent);
    }
}
