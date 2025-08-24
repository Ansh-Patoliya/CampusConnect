package com.eventApp.Controller;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.Loader.FXMLScreenLoader;

import com.eventApp.Model.ClubMember;
import com.eventApp.Service.AdminService;
import com.eventApp.Service.impl.AdminServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ViewClubMemberController {

    public TableColumn<ClubMember,String> nameCol;
    public TableColumn<ClubMember,String> emailCol;
    public TableColumn<ClubMember,String> positionCol;
    public TableColumn<ClubMember,String> userIdCol;
    public TableView<ClubMember> memberTable;
    public TableColumn<ClubMember,String> clubNameCol;
    public ComboBox selectMenu;
    public AnchorPane selectClubPane;
    public VBox tablePane;

    List<ClubMember> clubMemberList;
    private final AdminService adminService = new AdminServiceImpl();
    ClubDAO clubDAO = new ClubDAO();
    @FXML
    public void initialize() {
        memberTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            selectMenu.getItems().addAll(clubDAO.getAllClubNames());
        } catch (SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
        }

        selectMenu.setOnAction(event -> {
            String selectedClub = (String) selectMenu.getValue();
            try {
                loadMemberList(clubDAO.getClubIdBy(selectedClub));
                tablePane.setVisible(true);
            } catch (SQLException | ClassNotFoundException e) {
                FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
            }
            setupColumns();
        });
    }


    private void setupColumns() {
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        positionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPosition()));
        userIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserId()));
        clubNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(clubDAO.getClubNameBy(cellData.getValue().getClubId())));

        memberTable.getItems().setAll(clubMemberList);
    }

    private void loadMemberList(int clubId) {
        try {
            this.clubMemberList = adminService.getClubMemberList(clubId);
        } catch (SQLException | ClassNotFoundException e) {
            FXMLScreenLoader.showMessage(e.getMessage(), "Error", "error");
        }
    }


    public void onBack(ActionEvent event){
        FXMLScreenLoader.openAdminDashboard(event);
    }
}
