package com.eventApp.Service;
import com.eventApp.DAO.AdminDAO;
import com.eventApp.DAO.ClubDAO;
import com.eventApp.Model.Club;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClubApprovalService {

    private AdminDAO adminDAO;
    private MyClubQueue queue;
    private ClubDAO clubDAO;

    public Club viewNextPendingClub() {
        // Returns next pending club
        return queue.peek();
    }

    public boolean approveNextClub(String clubId) {
        // Approve and remove club from queue
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = 'approved' WHERE id = ? AND status = 'pending'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clubId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                queue.dequeue();
                return true;
            } else{
                return false;
            }
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public boolean rejectNextClub(String clubId) {
        // Reject and remove club from queue
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = 'rejected' WHERE id = ? AND status = 'pending'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clubId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                queue.dequeue();
                return true;
            } else{
                return false;
            }
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public MyClubQueue getAllPendingClubs() {
        // Return pending clubs from DB
        if (queue == null) {
            queue = adminDAO.getClubList("pending");
        }
        return queue;

    }
}
