package com.eventApp.DAO;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AdminDAO {

    public MyEventLL getEventList(){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "select * from events";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");

                String clubId = resultSet.getString("club_id");
                String userId = resultSet.getString("created_by");

                int maxParticipants = resultSet.getInt("max_participants");

                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();

                double ticketPrice = resultSet.getDouble("ticket_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");

                String approvalStatus = resultSet.getString("approval_status");
                String completionStatus = resultSet.getString("completion_status");


                eventList.insert(new Event(eventId,eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime , ticketPrice, discountApplicable,approvalStatus,completionStatus));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }

    //bring list of pending event, make method in DAO return type list
    public MyEventLL getEventList(String statusOfEvent){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "select * from events where approval_status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,statusOfEvent.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int eventId=resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");

                String clubId = resultSet.getString("club_id");
                String userId = resultSet.getString("created_by");

                int maxParticipants = resultSet.getInt("max_participants");

                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();

                double ticketPrice = resultSet.getDouble("ticket_price");
                boolean discountApplicable = resultSet.getBoolean("discount_available");

                String approvalStatus = resultSet.getString("approval_status");
                String completionStatus = resultSet.getString("completion_status");

                eventList.insert(new Event(eventId,eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                        startTime, endTime , ticketPrice, discountApplicable,approvalStatus,completionStatus));

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }

    public boolean approvalStatusUpdate(String approvalStatus,int eventId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE events SET approval_status = ? WHERE event_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, approvalStatus.toUpperCase());
            preparedStatement.setInt(2, eventId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
