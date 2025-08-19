package com.eventApp.Service;

import com.eventApp.DAO.ClubMemberDAO;
import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;

public class ClubMemberService {

    /**
     * Retrieves a ClubMember object corresponding to the given User.
     *
     * @param user The User object for which to find the related ClubMember
     * @return The ClubMember associated with the provided User
     */
    public ClubMember getClubMemberByUser(User user) {
        return ClubMemberDAO.getClubMember(user);
    }
}
