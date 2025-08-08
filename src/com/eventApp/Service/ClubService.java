package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Model.Club;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClubService {
    public final ClubDAO clubDAO = new ClubDAO();
    public final ClubMemberDAO clubMemberDAO = new ClubMemberDAO();

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
}