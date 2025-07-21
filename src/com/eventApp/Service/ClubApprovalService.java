package com.eventApp.Service;
import com.eventApp.DAO.ClubDAO;
import com.eventApp.Model.Club;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClubApprovalService {

    private MyClubQueue queue;
    private ClubDAO clubDAO=new ClubDAO();

    public ClubApprovalService() {
        getAllPendingClubs();
    }

    public Club viewNextPendingClub() {
        // Returns next pending club
        return queue.peek();
    }

    public boolean approveNextClub() {
        // Approve and remove club from queue
        Club nextClub = viewNextPendingClub();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = 'Approved' WHERE club_id = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nextClub.getClubId());
            preparedStatement.setString(2,"Pending");
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                return true;
            } else{
                return false;
            }
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public boolean rejectNextClub() {
        // Reject and remove club from queue
        Club nextClub = viewNextPendingClub();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = 'Rejected' WHERE club_id = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nextClub.getClubId());
            preparedStatement.setString(2,"Pending");
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
            queue = clubDAO.getClubList("Pending");
        }
        return queue;
    }
}
