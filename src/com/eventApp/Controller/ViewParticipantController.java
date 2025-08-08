package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;

public class ViewParticipantController {
    public TableColumn nameCol;
    public TableColumn userId;
    public TableColumn deptcol;
    public TableColumn semCol;
    public TableView<Student> participantTable;

    List<Student> participantList;
    ClubService clubService = new ClubService();
    User currentUser= CurrentUser.getCurrentUser();
    @FXML
    public void initialize() {
        loadParticipantList();
        //setupColumns();
    }

    private void loadParticipantList() {
        this.participantList = clubService.getParticipant(currentUser);
    }

    public void onBack(ActionEvent actionEvent) { FXMLScreenLoader.openStudentDashboard(actionEvent); }
}
