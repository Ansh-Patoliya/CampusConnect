package com.eventApp.Controller;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class ViewMemberController {
    public TableColumn<ClubMember,String> nameCol;
    public TableColumn<ClubMember,String> emailCol;
    public TableColumn<ClubMember,String> positionCol;
    public TableColumn<ClubMember,String> userIdCol;
    public TableView<ClubMember> memberTable;

    List<ClubMember> clubMemberList;
    ClubService clubService = new ClubService();
    User currentUser= CurrentUser.getCurrentUser();
    @FXML
    public void initialize() {
        loadMemberList();
        setupColumns();
    }


    private void setupColumns() {
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        positionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPosition()));
        userIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));

        memberTable.getItems().setAll(clubMemberList);
    }

    private void loadMemberList() {
        try {
            this.clubMemberList = clubService.getClubMember(currentUser);
        } catch (SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
        }
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openClubDashboard(actionEvent);
    }
}
