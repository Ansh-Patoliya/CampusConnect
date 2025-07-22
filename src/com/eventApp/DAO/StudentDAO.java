package com.eventApp.DAO;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Event;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDAO {

    public static Student getStudent(User user) {
        if (user == null || user.getUserId() == null) return null;

        String query = "SELECT s.student_id, s.department, s.semester, s.interests, " +
                "u.name, u.email, u.password, u.role " +
                "FROM students s JOIN users u ON s.student_id = u.user_id " +
                "WHERE s.student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String interestCSV = rs.getString("interests");
                    List<String> interestList = (interestCSV == null || interestCSV.trim().isEmpty())
                            ? null
                            : Arrays.stream(interestCSV.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    return new Student(
                            rs.getString("student_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("department"),
                            rs.getInt("semester"),
                            interestList
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // or use Logger
        }
        return null;
    }

    public MyEventLL viewEventsHistory(){
        MyEventLL eventList = new MyEventLL();
        try(Connection connection = DatabaseConnection.getConnection()){
            String fetchEventsQuery = "select * from event_history";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(fetchEventsQuery);
            while (resultSet.next()){
                int eventId=resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String clubId = resultSet.getString("club_id");
                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                String venue = resultSet.getString("venue");
                int totalParticipants = resultSet.getInt("total_participants");
                double ticketPrice = resultSet.getDouble("ticket_price");

                eventList.insert(new Event(eventId, eventName, clubId, venue, ticketPrice, eventDate, startTime, endTime, totalParticipants ));

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }
}
