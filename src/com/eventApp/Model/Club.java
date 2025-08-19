package com.eventApp.Model;

public class Club {
    // Club attributes
    private String clubName,descriptions,category,founderId,status;
    private int memberCount,maxMemberCount;
    private int clubId;

    /**
     * Constructor to create a new Club with basic details.
     * Initializes status as "Pending" by default.
     *
     * @param clubName       Name of the club
     * @param descriptions   Description of the club
     * @param category       Category or type of the club
     * @param founderId      ID of the club founder
     * @param maxMemberCount Maximum number of members allowed in the club
     */
    public Club(String clubName, String descriptions, String category, String founderId, int maxMemberCount) {
        this.clubName = clubName;
        this.descriptions = descriptions;
        this.category = category;
        this.founderId = founderId;
        this.maxMemberCount = maxMemberCount;
        this.status="Pending";
    }

    /**
     * Constructor to create a Club object with all attributes.
     *
     * @param clubName       Name of the club
     * @param descriptions   Description of the club
     * @param category       Category or type of the club
     * @param founderId      ID of the club founder
     * @param status         Status of the club (e.g., Approved, Pending)
     * @param memberCount    Current number of members
     * @param clubId         Unique club ID
     * @param maxMemberCount Maximum number of members allowed
     */
    public Club(String clubName, String descriptions, String category, String founderId, String status, int memberCount, int clubId, int maxMemberCount) {
        this.clubName = clubName;
        this.descriptions = descriptions;
        this.category = category;
        this.founderId = founderId;
        this.status = status;
        this.memberCount = memberCount;
        this.clubId = clubId;
    }

    // Getters and setters for different fields
    public int getMaxMemberCount() {
        return maxMemberCount;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFounderId() {
        return founderId;
    }

    public int getMemberCount() {
        return memberCount;
    }
}
