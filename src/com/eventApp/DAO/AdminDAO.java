package com.eventApp.DAO;

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

    //bring list of pending event, make method in DAO return type list
    ArrayList<Event> getEventList(String statusOfEvent){
        ArrayList<Event> eventList = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "select * from events where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,statusOfEvent);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
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

                eventList.add(new Event(eventName, description, venue, clubId, userId, maxParticipants, eventDate,
                startTime, endTime , ticketPrice, discountApplicable));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }
}
