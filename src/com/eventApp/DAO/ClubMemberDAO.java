package com.eventApp.DAO;

import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<ClubMember> getClubMemberList(String userId) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMemberList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String query = "select u.name,u.email,cm.position,u.user_id from users u \n" +
                "inner join club_members cm on cm.member_id = u.user_id\n" +
                "inner join clubs c on c.club_id=cm.club_id WHERE c.founder_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clubMemberList.add(new ClubMember(userId, resultSet.getString("name"),
                    resultSet.getString("email"), null, null,
                    resultSet.getString("position"), 0));
        }
        return clubMemberList;
    }

    public List<ClubMember> getClubMemberList(int clubId) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMemberList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT u.name, u.email, cm.position,cm.club_id, u.user_id FROM users u " +
                "INNER JOIN club_members cm ON cm.member_id = u.user_id " +
                "where cm.club_id=? order by u.name";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, clubId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clubMemberList.add(new ClubMember(resultSet.getString("user_id"), resultSet.getString("name"),
                    resultSet.getString("email"), null, null,
                    resultSet.getString("position"), resultSet.getInt("club_id")));
        }
        return clubMemberList;
    }

}
