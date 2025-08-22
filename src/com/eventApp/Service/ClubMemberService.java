package com.eventApp.Service;

import com.eventApp.Model.ClubMember;
import com.eventApp.Model.User;

/**
 * Interface for club member related service operations.
 * Defines a method to get user object.
 */
public interface ClubMemberService {

    /**
     * Retrieves a ClubMember object corresponding to the given User.
     *
     * @param user The User object for which to find the related ClubMember
     * @return The ClubMember associated with the provided User
     */
    ClubMember getClubMemberByUser(User user);
}
