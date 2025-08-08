package com.eventApp.Controller;

import com.eventApp.DAO.EventDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ViewParticipantController {

    public TableColumn<Student, String> nameCol;
    public TableColumn<Student, String> userIdCol;
    public TableColumn<Student, String> deptCol;
    public TableColumn<Student, String> semCol;
    public TableView<Student> participantTable;
    public VBox tablePane;
    @FXML
    private ComboBox<String> eventComboBox;

    List<Student> participantList;
    List<String> eventNames;
    ClubService clubService = new ClubService();
    EventDAO eventDAO = new EventDAO();
    User currentUser= CurrentUser.getCurrentUser();
    @FXML
    public void initialize() {
        participantTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        loadEventNames();
        eventComboBox.getItems().addAll(eventNames);

        eventComboBox.setOnAction(event -> {
            String selectedEvent = (String) eventComboBox.getValue();
            loadParticipantList(eventDAO.getEventIdBy(selectedEvent));
            tablePane.setVisible(true);
            setupColumns();
        });
    }

    private void loadEventNames() {
        eventNames = clubService.getAllEventNames();
    }

    private void loadParticipantList(int eventId) {
        this.participantList = clubService.getParticipant(eventId);
    }

    private void setupColumns() {
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        userIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));
        deptCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDepartment()));
        semCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getSemester())));

        participantTable.getItems().setAll(participantList);
    }

    public void onBack(ActionEvent actionEvent) { FXMLScreenLoader.openClubDashboard(actionEvent); }

}
