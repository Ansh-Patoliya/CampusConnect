package com.eventApp.Service;



import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.StudentDAO;
import com.eventApp.DAO.ClubDAO;


import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.StudentDAO;

import com.eventApp.DAO.UserDAO;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Model.*;

import com.eventApp.DAO.AdminDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

    UserDAO userDAO = new UserDAO();
    ClubDAO clubDAO = new ClubDAO();

    public void exportClubData(String clubFilename){
        MyClubQueue allClubs = clubDAO.getAllClubList();
        try (BufferedWriter clubFile = new BufferedWriter(new FileWriter(clubFilename))) {
            String formattedLine = String.format("%s , %s , %s , %s", "Club ID", "Club Name", "President Name", "Description");
            clubFile.write(formattedLine);
            clubFile.newLine();
            while (!allClubs.isEmpty()){
                Club currentClub = allClubs.dequeue();
                formattedLine = String.format("%s , %s , %s , %s", currentClub.getClubId(),currentClub.getClubName(),
                        userDAO.getUserNameBy(currentClub.getFounderId()), currentClub.getDescriptions());
                clubFile.write(formattedLine);
                clubFile.newLine();
                clubFile.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MyClubQueue getAllClubs(){
        return clubDAO.getAllClubList();
    }

    public Admin getAdmin(User user) {
        return AdminDAO.getAdmin(user);
    }

    public List<ClubMember> getClubMemberList(int clubId) throws SQLException, ClassNotFoundException {
        return new ClubMemberDAO().getClubMemberList(clubId);
    }
}
