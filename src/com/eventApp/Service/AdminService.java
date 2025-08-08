package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.UserDAO;
import com.eventApp.DataStructures.MyClubQueue;
import com.eventApp.Model.*;

import com.eventApp.DAO.AdminDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class AdminService {

    UserDAO userDAO = new UserDAO();
    ClubDAO clubDAO = new ClubDAO();

    public void exportClubData(Club club) {
        String clubDetailsFile = "D:\\" + club.getClubName() + "Data.txt";
        try (BufferedWriter clubFile = new BufferedWriter(new FileWriter(clubDetailsFile))) {
            String formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", "Club ID", "Club Name", "President Name", "Description");
            clubFile.write(formattedLine);
            clubFile.newLine();
            formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", club.getClubId(), club.getClubName(), userDAO.getUserNameBy(club.getFounderId()),
                    club.getDescriptions());
            clubFile.write(formattedLine);
            clubFile.newLine();
            clubFile.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllClubData(){
        MyClubQueue allClubs = clubDAO.getAllClubList();
        String clubFilename = "D:\\ClubList.txt";
        try (BufferedWriter clubFile = new BufferedWriter(new FileWriter(clubFilename))) {
            String formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", "Club ID", "Club Name", "President Name", "Description");
            clubFile.write(formattedLine);
            clubFile.newLine();
            while (!allClubs.isEmpty()){
                Club currentClub = allClubs.dequeue();
                formattedLine = String.format("%-10s | %-32s | %-32s | %-256s", currentClub.getClubId(),currentClub.getClubName(),
                        userDAO.getUserNameBy(currentClub.getFounderId()), currentClub.getDescriptions());
                clubFile.write(formattedLine);
                clubFile.newLine();
                clubFile.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Admin getAdmin(User user) {
        return AdminDAO.getAdmin(user);
    }
}
