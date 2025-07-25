package com.eventApp.DAO;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {
    public static int getClubId(String clubName) {
        int clubId = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select club_id from clubs where club_name=?");
            preparedStatement.setString(1, clubName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            clubId = resultSet.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubId;
    }

    public static int getClubIdByUserId(String userId) {
        int clubId = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select club_id from club_members where user_id=?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clubId = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubId;
    }

    public boolean registrationUser(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(user_id,name,email,password,role) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().toUpperCase());

            int userInsert = preparedStatement.executeUpdate();
            if (userInsert > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrationStudent(Student student) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into students values(?,?,?,?)");
            preparedStatement.setString(1, student.getUserId());
            preparedStatement.setString(2, student.getDepartment());
            preparedStatement.setInt(3, student.getSemester());
            preparedStatement.setString(4, String.join(",", student.getInterest()));

            int studentInsert = preparedStatement.executeUpdate();
            if (studentInsert > 0) {
                System.out.println("registration complete");
                return true;
            } else {
                System.out.println("registration fail..");
            }
        } catch (Exception e) {
        }

        return false;
    }

    public boolean checkLoginDetails(String emailInput, String passwordInput) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select email,password from users where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, emailInput);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String storedPassword = rs.getString("password");
                if (Objects.equals(passwordInput, storedPassword)) {
                    System.out.println("Login verified");
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean resetPass(String emailInput, String newPassword, String confirmPassword) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, emailInput);

            int r = preparedStatement.executeUpdate();
            if (r > 0) {
                System.out.println("Password updated successfully.");
                return true;
            } else {
                FXMLScreenLoader.showMessage("Password update failed. Please try again.", "Error", "error");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean registrationClubMember(ClubMember clubMember) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into club_members values(?,?,?)");
            preparedStatement.setString(1, clubMember.getUserId());
            preparedStatement.setInt(2, clubMember.getClubId());
            preparedStatement.setString(3, clubMember.getPosition());

            int clubMemberInsert = preparedStatement.executeUpdate();
            if (clubMemberInsert > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrationClub(Club club) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into clubs(club_name,category,description,founder_id,status) values(?,?,?,?,?)");
            preparedStatement.setString(1, club.getClubName());
            preparedStatement.setString(2, club.getCategory());
            preparedStatement.setString(3, club.getDescriptions());
            preparedStatement.setString(4, club.getFounderId());
            preparedStatement.setString(5, club.getStatus());

            int insertClub = preparedStatement.executeUpdate();
            if (insertClub > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllClubsName() {
        List<String> clubNames = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT club_name FROM clubs");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String clubName = rs.getString(1);
                clubNames.add(clubName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubNames;
    }

    public User getUserByemail(String emailInput) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, emailInput);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                user = new User(userId, name, emailInput, password, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getUserNameBy(String userId) {
        String userName = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM users WHERE user_id = ?");
            preparedStatement.setString(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userName = rs.getString("name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userName;
    }
}
