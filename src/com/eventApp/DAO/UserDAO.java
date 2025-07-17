package com.eventApp.DAO;

import com.eventApp.Loader.FXMLScreenLoader;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Student;
import com.eventApp.Model.User;
import com.eventApp.Utils.DatabaseConnection;
import com.eventApp.Utils.ValidationUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {
    public boolean registrationUser(User user){
        try {
            Connection connection=DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(user_id,name,email,password,role) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1,user.getUserId());
            preparedStatement.setString(2,user.getName());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5, user.getRole().toUpperCase());

            int userInsert=preparedStatement.executeUpdate();
            if(userInsert>0){
                return true;
            }
            else {
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

            User user=new User(student.getUserId(),student.getName(), student.getEmail(), student.getPassword(), student.getRole());
            boolean userInsert=registrationUser(user);

            PreparedStatement preparedStatement= connection.prepareStatement("insert into students values(?,?,?,?)");
            preparedStatement.setString(1,student.getUserId());
            preparedStatement.setString(2, student.getDepartment());
            preparedStatement.setInt(3,student.getSemester());
            preparedStatement.setString(4,String.join(",",student.getInterest()));

            int studentInsert=preparedStatement.executeUpdate();
            if( userInsert && studentInsert>0){
                System.out.println("registration complete");
                return true;
            }
            else{
                System.out.println("registration fail..");
            }
        }
        catch (Exception e) {
        }

        return false;
    }

    public boolean checkLoginDetails(String emailInput, String passwordInput){
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "select email,password from users where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, emailInput);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String storedPassword = rs.getString("password");
                if(Objects.equals(passwordInput,storedPassword)){
                    System.out.println("Login verified");
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean resetPass(String emailInput, String newPassword, String confirmPassword) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            if (!ValidationUtils.checkEmail(emailInput)) {
                FXMLScreenLoader.showError("Please enter a valid email.");
                return false;
            }


            if (!newPassword.equals(confirmPassword)) {
                FXMLScreenLoader.showError("Password and Confirm Password do not match.");
                return false;
            }

            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, emailInput);

            int r = preparedStatement.executeUpdate();
            if(r>0) {
                System.out.println("Password updated successfully.");
                return true;
            }
            else {
                FXMLScreenLoader.showError("Password update failed.");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    
    public static String getClubId(String clubName){
        String clubId = null;
        try {
            Connection connection=DatabaseConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select club_id from clubs where club_name=?");
            preparedStatement.setString(1,clubName);
            ResultSet resultSet=preparedStatement.executeQuery();
            clubId=resultSet.getString(1);
//            while (resultSet.next()){
//                clubId=resultSet.getString(1);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubId;
    }

    public boolean registrationClubMember(ClubMember clubMember) {
        try{
            Connection connection=DatabaseConnection.getConnection();

            PreparedStatement preparedStatement=connection.prepareStatement("insert into club_members values(?,?,?)");
            preparedStatement.setString(1,clubMember.getUserId());
            preparedStatement.setString(2,clubMember.getClubId());
            preparedStatement.setString(3,clubMember.getPosition());

            int clubMemberInsert=preparedStatement.executeUpdate();
            if(clubMemberInsert>0){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrationClub(Club club) {
        try{
            Connection connection=DatabaseConnection.getConnection();

            PreparedStatement preparedStatement=connection.prepareStatement("insert into clubs(club_id,club_name,category,description,founder_id,status) values(?,?,?,?,?,?)");
            preparedStatement.setString(1,club.getClubId());
            preparedStatement.setString(2,club.getClubName());
            preparedStatement.setString(3,club.getCategory());
            preparedStatement.setString(4,club.getDescriptions());
            preparedStatement.setString(5,club.getFounderId());
            preparedStatement.setString(6,club.getStatus());

            int insertClub=preparedStatement.executeUpdate();
            if(insertClub>0){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllClubsName(){
        List<String> clubNames = new ArrayList<>();
        try{
            Connection connection= DatabaseConnection.getConnection();
            PreparedStatement ps= connection.prepareStatement("SELECT club_name FROM clubs");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String clubName = rs.getString(1);
                clubNames.add(clubName);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clubNames;
    }

    public static String getClubIdByUserId(String userId) {
        String clubId = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select club_id from club_members where user_id=?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clubId = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubId;
    }
}
