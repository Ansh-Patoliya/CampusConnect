package com.eventApp.Controller;

import com.eventApp.DAO.UserDAO;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Service.AdminService;
import com.eventApp.Service.impl.AdminServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ViewClubsController {
    public Button export;
    public TableView<Club> clubTable;
    public TableColumn<Club,String> idCol;
    public TableColumn<Club,String> clubNameCol;
    public TableColumn<Club,String> presidentNameCol;
    public TableColumn<Club,String> descriptionCol;

    List<Club> clubList;
    private final AdminService adminService = new AdminServiceImpl();
    UserDAO userDAO = new UserDAO();


    @FXML
    public void initialize() {
        clubTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadClubList();
        setupColumns();
    }


    private void setupColumns() {
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(""+cellData.getValue().getClubId()));
        clubNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClubName()));
        presidentNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(userDAO.getUserNameBy(cellData.getValue().getFounderId())));
        descriptionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescriptions()));

        clubTable.getItems().setAll(clubList);
    }

    private void loadClubList() {
        this.clubList= adminService.getAllClubs().getAllClubs();
    }

    public void onBack(ActionEvent actionEvent) {
        FXMLScreenLoader.openAdminDashboard(actionEvent);
    }

    @FXML
    public void onExport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) export.getScene().getWindow(); // exportButton will be @FXML injected

            if (clubList.isEmpty()) {
                throw new ValidationException("Club list is empty. Cannot export.");
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Club List");
            fileChooser.setInitialFileName("clubs_export.csv");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                adminService.exportClubData( file.getAbsolutePath());
                FXMLScreenLoader.showMessage("Club list exported successfully to " + file.getAbsolutePath(), "Success", "info");
            }
        } catch (ValidationException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
            return;
        }
    }


}
