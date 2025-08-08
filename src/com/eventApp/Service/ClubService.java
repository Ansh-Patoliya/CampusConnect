package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.DAO.EventRegistrationDAO;
import com.eventApp.Model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClubService {
    public final ClubDAO clubDAO = new ClubDAO();
    public final ClubMemberDAO clubMemberDAO = new ClubMemberDAO();
    public final EventRegistrationDAO eventRegistrationDAO = new EventRegistrationDAO();

    public boolean addEvent(Event event) {
        return clubDAO.createEvent(event);
    }

    public Club getClubByUser(User user) {
        int clubId = ClubMemberDAO.getClubMember(user).getClubId();
        Club club = clubDAO.getClubById(clubId);
        if (club == null) {
            return null; // No club found for the user
        }
        return club;
    }

    public List<ClubMember> getClubMember(User user) throws SQLException, ClassNotFoundException {
        List<ClubMember> clubMembers = clubMemberDAO.getClubMemberList(user.getUserId());
        clubMembers.sort(Comparator.comparing(ClubMember::getName));
        return clubMembers;
    }

    public List<Student> getParticipant(User user){
        List<Student> participants = eventRegistrationDAO.getParticipantList(user.getUserId());
        participants.sort(Comparator.comparing(Student::getName));
        return participants;
    }


    public void exportClubsToCSV(List<ClubMember> clubMemberList, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(String.format("%-10s | %-32s | %-32s | %-32s", "User ID", "Name", "Email", "Position"));
        writer.newLine();
        for (ClubMember clubMember : clubMemberList) {
            String formattedLine = String.format("%-10s | %-32s | %-32s | %-32s",
                    clubMember.getUserId(),
                    clubMember.getName(),
                    clubMember.getEmail(),
                    clubMember.getPosition());
            writer.write(formattedLine);
            writer.newLine();
        }
        writer.flush();
        writer.close();
        }
}