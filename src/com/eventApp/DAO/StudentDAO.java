package com.eventApp.DAO;

import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDAO {

    public static Student getStudent(User user) {
        if (user == null || user.getUserId() == null) return null;

        String query = "SELECT s.student_id, s.department, s.semester, s.interests, " +
                "u.name, u.email, u.password, u.role " +
                "FROM students s JOIN users u ON s.student_id = u.user_id " +
                "WHERE s.student_id" +
                " = ?";

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

    public List<String> getInterestList(String studentId) {
        List<String> interestList = new ArrayList<>();
        String query = "SELECT interests FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String interest = rs.getString("interests");
                    interestList.add(interest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // or use Logger
        }
        return interestList;
    }

}
