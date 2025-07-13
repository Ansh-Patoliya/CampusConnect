package com.eventApp.DAO;

import com.eventApp.Model.Student;
import com.eventApp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {
    public boolean
    registration(Student student) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(user_id,name,email,password,role) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1,student.getUserId());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setString(3,student.getEmail());
            preparedStatement.setString(4,student.getPassword());
            preparedStatement.setString(5,"student".toUpperCase());

            int userInsert=preparedStatement.executeUpdate();

            preparedStatement= connection.prepareStatement("insert into students values(?,?,?,?)");
            preparedStatement.setString(1,student.getUserId());
            preparedStatement.setString(2, student.getDepartment());
            preparedStatement.setInt(3,student.getSemester());
            preparedStatement.setString(4,String.join(",",student.getInterest()));

            int studentInsert=preparedStatement.executeUpdate();
            if(userInsert>0 && studentInsert>0){
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
}
