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
        this.queue=getAllPendingClubs();
    }

    public Club viewNextPendingClub() {
        // Returns next pending club
        return queue.peek();
    }

    public boolean approveNextClub(Club currentClub) {
        // Approve and remove club from queue
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = ? WHERE club_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setString(2, currentClub.getClubId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                queue.dequeue();
                return true;
            }
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean rejectNextClub(Club currentClub) {
        // Reject and remove club from queue
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE clubs SET status = ? WHERE club_id = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Rejected");
            preparedStatement.setString(2, currentClub.getClubId());
            preparedStatement.setString(3,"Pending");
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                queue.dequeue();
                return true;
            }
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public MyClubQueue getAllPendingClubs() {
        // Return pending clubs from DB

        return clubDAO.getClubList("Pending");

    }
}
