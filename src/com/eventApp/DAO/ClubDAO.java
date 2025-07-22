package com.eventApp.DAO;

import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Model.Club;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.*;

public class ClubDAO {
    public boolean createEvent(Event event) {
        try {
            String sql = "INSERT INTO events (event_id,club_id,event_name,description,event_date,ticket_price,created_by,discount_available,start_time,end_time,venue,max_participants) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, event.getEventId());
            preparedStatement.setInt(2, event.getClubId());
            preparedStatement.setString(3, event.getEventName());
            preparedStatement.setString(4, event.getDescription());
            preparedStatement.setDate(5, Date.valueOf(event.getEventDate()));
            preparedStatement.setDouble(6, event.getTicketPrice());
            preparedStatement.setString(7, event.getUserId());
            preparedStatement.setBoolean(8, event.isDiscountApplicable());
            preparedStatement.setTime(9, Time.valueOf(event.getStartTime()));
            preparedStatement.setTime(10, Time.valueOf(event.getEndTime()));
            preparedStatement.setString(11, event.getVenue());
            preparedStatement.setInt(12, event.getMaxParticipants());

            int r = preparedStatement.executeUpdate();

            if (r > 0) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public MyClubQueue getClubList(String clubStatus){
        MyClubQueue clubList=null;
        int rowCount=0;
        try(Connection connection = DatabaseConnection.getConnection()){
            String fetchClubCount = "select count(*) from clubs where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchClubCount);
            preparedStatement.setString(1,clubStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                rowCount = resultSet.getInt(1);
            }
            clubList = new MyClubQueue(rowCount);
            String fetchClubRecord = "select * from clubs where status = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(fetchClubRecord);
            preparedStatement2.setString(1,clubStatus);
            resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()){
                int clubId = resultSet.getInt("club_id");
                String clubName=resultSet.getString("club_name");
                String descriptions= resultSet.getString("description");
                String category= resultSet.getString("category");
                String founderId=resultSet.getString("founder_Id");
                int memberCount=resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                clubList.enqueue(new Club(clubName,descriptions,category,founderId,status,memberCount,clubId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clubList;
    }

    public String getClubNameBy(int clubId) {
        String clubName = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT club_name FROM clubs WHERE club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clubName = resultSet.getString("club_name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clubName;
    }
}
