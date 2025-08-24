package com.eventApp.Controller;

import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Service.impl.ClubServiceImpl;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class ViewMemberController {
    public TableColumn<ClubMember, String> nameCol;
    public TableColumn<ClubMember, String> emailCol;
    public TableColumn<ClubMember, String> positionCol;
    public TableColumn<ClubMember, String> userIdCol;
    public TableView<ClubMember> memberTable;
    public Button export;

    List<ClubMember> clubMemberList;
    ClubService clubService = new ClubServiceImpl();
    User currentUser = CurrentUser.getCurrentUser();

    @FXML
    public void initialize() {
        memberTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
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

    @FXML
    public void onExport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) export.getScene().getWindow(); // exportButton will be @FXML injected

            if (clubMemberList.isEmpty()) {
                throw new ValidationException("Club member list is empty. Cannot export.");
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Club List");
            fileChooser.setInitialFileName("clubs_export.csv");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                clubService.exportClubMembersToCSV(clubMemberList, file.getAbsolutePath());
                FXMLScreenLoader.showMessage("Club members exported successfully to " + file.getAbsolutePath(), "Success", "info");
            }
        } catch (ValidationException | IOException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
            return;
        }
    }

}
