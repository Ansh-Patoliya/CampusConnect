package com.eventApp.DAO;

import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClubMemberDAO {
    public static ClubMember getClubMember(User user) {
        if (user == null || user.getUserId() == null) return null;

        String query = "SELECT cm.club_id, cm.position ,u.name, u.email, u.password, u.role FROM club_members cm JOIN users u ON cm.member_id = u.user_id WHERE cm.member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ClubMemberDAO: Found club member for user ID: " + user.getUserId());
                return new ClubMember(
                        user.getUserId(),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("position"),
                        rs.getInt("club_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
