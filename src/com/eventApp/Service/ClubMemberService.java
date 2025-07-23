package com.eventApp.Service;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;

public class ClubMemberService {
    public ClubMember getClubMemberByUser(User user) {
        return ClubMemberDAO.getClubMember(user);
    }
}
