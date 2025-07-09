package com.eventApp.Model;

public class ClubMember extends User{
    private String position;
    private int clubId;

    public ClubMember(String userId, String name, String email, String password, String role, String position,int clubId) {
        super(userId, name, email, password, role);
        this.position = position;
        this.clubId=clubId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
