package com.eventApp.Service;

import com.eventApp.DAO.ClubDAO;
import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Model.Club;
import com.eventApp.Model.Event;
import com.eventApp.Model.User;

public class ClubService {
    public final ClubDAO clubDAO = new ClubDAO();

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
}