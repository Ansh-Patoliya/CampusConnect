package com.eventApp.Controller;

import com.eventApp.DAO.EventDAO;
import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Service.ClubService;
import com.eventApp.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * Controller class for viewing event participants in the CampusConnect application.
 * <p>
 * This class manages the UI and logic for displaying a list of participants for selected events.
 * It interacts with the ClubService and EventDAO to fetch event and participant data from the database.
 * The controller is responsible for:
 * <ul>
 *   <li>Initializing the event selection ComboBox with available event names.</li>
 *   <li>Loading and displaying participants for the selected event in a TableView.</li>
 *   <li>Handling navigation actions, such as returning to the club dashboard.</li>
 * </ul>
 * <p>
 * Fields:
 * <ul>
 *   <li>TableColumn fields for displaying student details (name, user ID, department, semester).</li>
 *   <li>TableView for showing the list of participants.</li>
 *   <li>ComboBox for selecting events.</li>
 *   <li>Service and DAO objects for business logic and database access.</li>
 *   <li>Current user information for context-sensitive operations.</li>
 * </ul>
 * <p>
 * Methods:
 * <ul>
 *   <li>initialize(): Sets up the ComboBox and TableView, and loads event names.</li>
 *   <li>loadEventNames(): Retrieves all event names from the service.</li>
 *   <li>loadParticipantList(int eventId): Loads participants for a given event.</li>
 *   <li>setupColumns(): Configures TableView columns for participant display.</li>
 *   <li>onBack(ActionEvent): Handles navigation back to the club dashboard.</li>
 * </ul>
 *
 * This controller is typically associated with the ViewParticipants.fxml UI layout.
 */

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
