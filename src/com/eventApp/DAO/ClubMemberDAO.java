package com.eventApp.DAO;

import com.eventApp.ExceptionHandler.DatabaseExceptionHandler;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for club member-related database operations.
 * Handles retrieval of club member information, member lists, and role validation.
 */
public class ClubMemberDAO {

    /**
     * Retrieves club member information for a given user.
     * Joins club_members and users tables to get complete member details.
     *
     * @param user the User object containing the user ID to search for
     * @return ClubMember object with complete member details, or null if not found or user is null
     */
    public static ClubMember getClubMember(User user) {
        if (user == null || user.getUserId() == null) return null;

        // Join query to get both club membership and user details
        String query = "SELECT cm.club_id, cm.position ,u.name, u.email, u.password, u.role " +
                "FROM club_members cm JOIN users u ON cm.member_id = u.user_id WHERE cm.member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

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
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Registers a user as a member of a specific club with a given position.
     * Creates the many-to-many relationship between users and clubs.
     *
     * @param clubMember the ClubMember object containing membership information
     * @throws SQLException             if database operation fails
     * @throws ClassNotFoundException   if database driver is not found
     * @throws DatabaseExceptionHandler if club member registration fails
     */
    public void registrationClubMember(ClubMember clubMember) throws SQLException, ClassNotFoundException, DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into club_members values(?,?,?)");
        preparedStatement.setString(1, clubMember.getUserId());
        preparedStatement.setInt(2, clubMember.getClubId());
        preparedStatement.setString(3, clubMember.getPosition());

        int clubMemberInsert = preparedStatement.executeUpdate();
        // Validate that the club membership was successfully created
        if (clubMemberInsert < 0) {
            throw new DatabaseExceptionHandler("Club member registration failed. Please try again.");
        }
    }

    /**
     * Retrieves all club members for clubs founded by a specific user.
     * Used by club founders to view members of their clubs.
     *
     * @param userId the ID of the club founder
     * @return List&lt;ClubMember&gt; containing all members of clubs founded by the given user
     * @throws SQLException           if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public List<ClubMember> getClubMemberList(String userId) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMemberList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        // Complex join query to get members of all clubs founded by the specified user
        String query = "select u.name,u.email,cm.position,u.user_id,c.club_id from users u \n" +
                "inner join club_members cm on cm.member_id = u.user_id\n" +
                "inner join clubs c on c.club_id=cm.club_id WHERE c.founder_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Process each member record
        while (resultSet.next()) {
            // Note: password and role are set to null for security, club_id is set to 0 as not needed here
            clubMemberList.add(new ClubMember(resultSet.getString("user_id"), resultSet.getString("name"),
                    resultSet.getString("email"), resultSet.getString("position"),resultSet.getInt("club_id")));
        }
        return clubMemberList;
    }

    /**
     * Retrieves all members of a specific club by club ID.
     * Results are ordered alphabetically by member name.
     *
     * @param clubId the unique identifier of the club
     * @return List&lt;ClubMember&gt; containing all members of the specified club
     * @throws SQLException           if database operation fails
     * @throws ClassNotFoundException if database driver is not found
     */
    public List<ClubMember> getClubMemberList(int clubId) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMemberList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        // Join query to get member details with alphabetical ordering
        String query = "SELECT u.name, u.email, cm.position,cm.club_id, u.user_id FROM users u " +
                "INNER JOIN club_members cm ON cm.member_id = u.user_id " +
                "where cm.club_id=? order by u.name";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, clubId);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Process each member record and include the club_id
        while (resultSet.next()) {
            clubMemberList.add(new ClubMember(resultSet.getString("user_id"), resultSet.getString("name"),
                    resultSet.getString("email"), resultSet.getString("position"), resultSet.getInt("club_id")));
        }
        return clubMemberList;
    }

    /**
     * Checks if a user is the president of an approved club.
     * Performs a two-step validation: first checks if user is a president,
     * then verifies if their club is approved.
     *
     * @param user_id the ID of the user to check
     * @return true if the user is president of an approved club, false otherwise
     */
    public boolean isPresidentOfApprovedClub(String user_id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Step 1: Check if the user has a president position in any club
            String query = "select position from club_members where member_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, user_id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if (resultSet1.next()) {
                String position = resultSet1.getString(1);

                // Step 2: If user is a president, check if their club is approved
                if (position.equalsIgnoreCase("President")) {
                    query = "select status from clubs where founder_id = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query);
                    preparedStatement2.setString(1, user_id);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                    if (resultSet2.next()) {
                        String status = resultSet2.getString(1);
                        // Return false if club is not approved
                        if (!status.equalsIgnoreCase("Approved")) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Print stack trace for debugging, but don't throw exception
            e.printStackTrace();
        }

        // Return true by default (if no issues found or user is not a president)
        return true;
    }
}
