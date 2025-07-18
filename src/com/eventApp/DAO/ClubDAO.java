package com.eventApp.DAO;

import com.eventApp.Model.Event;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

public class ClubDAO {
    public boolean createEvent(Event event) {
        try {
            String sql = "INSERT INTO events (event_id,club_id,event_name,description,event_date,ticket_price,created_by,discount_available,start_time,end_time,venue,max_participants) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, event.getEventId());
            preparedStatement.setString(2, event.getClubId());
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
}
