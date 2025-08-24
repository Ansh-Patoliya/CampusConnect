package com.eventApp.DAO;

import com.eventApp.DataStructures.MyEventLL;
import com.eventApp.Model.Admin;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Access Object for admin-related database operations.
 * Handles event retrieval and event approval status updates for admin workflows.
 */
public class AdminDAO {

    /**
     * Retrieves the admin details for a given user, if the user is an admin.
     *
     * @param user the User object containing user identification information
     * @return Admin - an Admin object populated with admin details, or null if not found or not an admin
     */
    public static Admin getAdmin(User user) {
        if (user == null || user.getUserId() == null) return null;

        String query = "SELECT user_id, name, email, password, role " +
                "FROM users " +
                "WHERE user_id = ? AND role = 'ADMIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // or use Logger
        }

        return null; // not found or not admin
    }

}
