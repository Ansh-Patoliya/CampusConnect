package com.eventApp.DAO;

import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDAO {

    public static Student getStudent(User user) {
        Student student = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT s.*, u.name, u.email, u.password, u.role " +
                    "FROM students s JOIN users u ON s.user_id = u.user_id " +
                    "WHERE s.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String interestCSV = rs.getString("interest");
                List<String> interestList = null;
                if (interestCSV != null && !interestCSV.trim().isEmpty()) {
                    interestList = Arrays.stream(interestCSV.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                }

                student = new Student(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        interestList
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}
