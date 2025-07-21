package com.eventApp.Service;


import com.eventApp.DAO.UserDAO;
import com.eventApp.Model.Club;
import com.eventApp.Utils.DatabaseConnection;

import com.eventApp.DAO.AdminDAO;
import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminService {

    UserDAO userDAO = new UserDAO();

    public void exportClubData(String clubDetailsFile, Club club) {
        try (BufferedWriter clubFile = new BufferedWriter(new FileWriter(clubDetailsFile, true));
             Connection connection = DatabaseConnection.getConnection()) {
//            callable procedure to fetchClubPresident here
//            String fetchClubPresident="{call fetchClubPresident(?)}";
//            CallableStatement callableStatement = connection.prepareCall(fetchClubPresident);
//            callableStatement.setString(1,club.getClubId());
//            String presidentName = callableStatement.execute();
//            clubFile.write(presidentName);
            String formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", "Club ID", "Club Name", "President Name", "Description");
            clubFile.write(formattedLine);
            clubFile.newLine();
            formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", club.getClubId(), club.getClubName(), userDAO.getUserNameBy(club.getFounderId()),
                    club.getDescriptions());
            clubFile.write(formattedLine);
            clubFile.newLine();
            clubFile.flush();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
