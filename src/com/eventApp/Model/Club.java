package com.eventApp.Model;

public class Club {
    private String clubName,descriptions,category,founderId,status;
    private int memberCount;
    private int clubId;
    static int clubCount=1;

    public Club(String clubName, String descriptions, String category, String founderId, int memberCount) {
        this.clubName = clubName;
        this.descriptions = descriptions;
        this.category = category;
        this.founderId = founderId;
        this.memberCount = memberCount;
        this.status="Pending";
    }

    public Club(String clubName, String descriptions, String category, String founderId, String status, int memberCount, int clubId) {
        this.clubName = clubName;
        this.descriptions = descriptions;
        this.category = category;
        this.founderId = founderId;
        this.status = status;
        this.memberCount = memberCount;
        this.clubId = clubId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFounderId() {
        return founderId;
    }

    public void setFounderId(String founderId) {
        this.founderId = founderId;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

   public String createClubID(){
         return "C" +clubCount++;
   }
}
