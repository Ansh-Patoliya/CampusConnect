package com.eventApp.DAO;

import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.ExceptionHandler.ValidationException;
import com.eventApp.Model.Club;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    public void createEvent(Event event) throws SQLException, ClassNotFoundException, ValidationException {

            String sql = "INSERT INTO events (club_id,event_name,description,event_date,ticket_price,created_by,discount_available,start_time,end_time,venue,max_participants,category) VALUES ( ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);


            preparedStatement.setInt(1, event.getClubId());
            preparedStatement.setString(2, event.getEventName());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setDate(4, Date.valueOf(event.getEventDate()));
            preparedStatement.setDouble(5, event.getTicketPrice());
            preparedStatement.setString(6, event.getUserId());
            preparedStatement.setBoolean(7, event.isDiscountApplicable());
            preparedStatement.setTime(8, Time.valueOf(event.getStartTime()));
            preparedStatement.setTime(9, Time.valueOf(event.getEndTime()));
            preparedStatement.setString(10, event.getVenue());
            preparedStatement.setInt(11, event.getMaxParticipants());
            preparedStatement.setString(12, event.getCategory());

            int r = preparedStatement.executeUpdate();
            if (r < 0) {
                throw new ValidationException("Event creation failed. Please try again.");
            }
    }

    public MyClubQueue getClubList(String clubStatus) {
        MyClubQueue clubList;
        int rowCount = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String fetchClubCount = "select count(*) from clubs where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchClubCount);
            preparedStatement.setString(1, clubStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
            clubList = new MyClubQueue(rowCount);
            String fetchClubRecord = "select * from clubs where status = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(fetchClubRecord);
            preparedStatement2.setString(1, clubStatus);
            resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()) {
                int clubId = resultSet.getInt("club_id");
                String clubName = resultSet.getString("club_name");
                String descriptions = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                clubList.enqueue(new Club(clubName, descriptions, category, founderId, status, memberCount, clubId));
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

    public int getClubIdBy(String clubName) throws SQLException, ClassNotFoundException {
        int clubId = -1; // Default value if club not found
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT club_id FROM clubs WHERE club_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, clubName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clubId = resultSet.getInt("club_id");
        }
        return clubId;
    }

    public Club getClubById(int clubId) {
        Club club = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM clubs WHERE club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String clubName = resultSet.getString("club_name");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                club = new Club(clubName, description, category, founderId, status, memberCount, clubId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return club;
    }


    public void checkClubNameExist(String clubName) throws ValidationException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select * from clubs where club_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clubName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new ValidationException("Club name already exists.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public MyClubQueue getAllClubList() {
        MyClubQueue clubList;
        int rowCount = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String fetchClubCount = "select count(*) from clubs";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchClubCount);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
            clubList = new MyClubQueue(rowCount);
            String fetchClubRecord = "select * from clubs order by created_at";
            PreparedStatement preparedStatement2 = connection.prepareStatement(fetchClubRecord);
            resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()) {
                int clubId = resultSet.getInt("club_id");
                String clubName = resultSet.getString("club_name");
                String descriptions = resultSet.getString("description");
                String category = resultSet.getString("category");
                String founderId = resultSet.getString("founder_Id");
                int memberCount = resultSet.getInt("member_count");
                String status = resultSet.getString("status");
                clubList.enqueue(new Club(clubName, descriptions, category, founderId, status, memberCount, clubId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clubList;
    }

    public List<String> getAllClubNames() throws SQLException, ClassNotFoundException {
        List<String> clubNames = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT club_name FROM clubs where status = 'Approved' order by club_name";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clubNames.add(resultSet.getString("club_name"));
        }
        return clubNames;
    }

}
