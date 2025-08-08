package com.eventApp.Controller;

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
import java.util.List;

public class ViewParticipantController {
    public TableColumn<Student, String> nameCol;
    public TableColumn<Student, String> userIdCol;
    public TableColumn<Student, String> deptCol;
    public TableColumn<Student, String> semCol;
    public TableView<Student> participantTable;
    @FXML
    private ComboBox<String> eventComboBox;

    List<Student> participantList;
    ClubService clubService = new ClubService();
    User currentUser= CurrentUser.getCurrentUser();
    @FXML
    public void initialize() {
        loadParticipantList();
        setupColumns();
    }

    private void loadEventNames() {
        List<String> eventNames = clubService.getAllEventNames(currentUser);
        eventComboBox.setItems(FXCollections.observableArrayList(eventNames));
    }

    private void loadParticipantList() {
        this.participantList = clubService.getParticipant(currentUser);
    }

    private void setupColumns() {
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        userIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));
        deptCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDepartment()));
        semCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getSemester())));

        participantTable.getItems().setAll(participantList);
    }

    public void onBack(ActionEvent actionEvent) { FXMLScreenLoader.openStudentDashboard(actionEvent); }

    public void onEventSelected(ActionEvent actionEvent) {
    }
}
