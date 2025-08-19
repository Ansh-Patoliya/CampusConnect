# All Method Names in DAO Package

This document lists all public method names in the `com.eventApp.DAO` package.

## AdminDAO.java
1. `approvalStatusUpdate(String approvalStatus, int eventId)`
2. `getAdmin(User user)` *(static)*
3. `getEventList()` *(overloaded - 2 versions)*
4. `getEventList(String statusOfEvent)`

## ClubDAO.java
1. `checkClubNameExist(String clubName)`
2. `createEvent(Event event)`
3. `getAllClubList()`
4. `getAllClubNames()`
5. `getClubById(int clubId)`
6. `getClubIdBy(String clubName)`
7. `getClubList(String clubStatus)`
8. `getClubNameBy(int clubId)`

## ClubMemberDAO.java
1. `getClubMember(User user)` *(static)*
2. `getClubMemberList(String userId)` *(overloaded - 2 versions)*
3. `getClubMemberList(int clubId)`
4. `isPresidentOfApprovedClub(String user_id)`

## EventDAO.java
1. `cancelEvent(int eventId)`
2. `getEventIdBy(String eventName)`
3. `getEventList(String approvalStatus, String completionStatus)`
4. `getEventListByClubId(int clubId)`
5. `getEventNames(String userId)`
6. `getMyEventList(String userId)`
7. `updateEvent(Event currentEvent)`

## EventRegistrationDAO.java
1. `getParticipantList(int eventId)`
2. `registerForEvent(int eventId, String userId)`

## NotificationDAO.java
1. `createNotification(String userId, String message)`
2. `getNotifications(String userId)`
3. `markNotificationAsRead(String userId, int notificationId)`

## StudentDAO.java
1. `getInterestList(String studentId)`
2. `getStudent(User user)` *(static)*
3. `viewEventsHistory(String userId)`

## UserDAO.java
1. `checkDuplicateEmail(String newEmail)` *(static)*
2. `checkDuplicateEnrollment(String enrollment)` *(static)*
3. `checkLoginDetails(String emailInput, String passwordInput)`
4. `getAllClubsName()`
5. `getClubId(String clubName)` *(static)*
6. `getClubIdByUserId(String userId)` *(static)*
7. `getUserByemail(String emailInput)`
8. `getUserNameBy(String userId)`
9. `registrationClub(Club club)`
10. `registrationClubMember(ClubMember clubMember)`
11. `registrationStudent(Student student)`
12. `registrationUser(User user)`
13. `resetPass(String emailInput, String newPassword)`

---

## Summary
- **Total DAO Classes**: 8
- **Total Methods**: 44 (including overloaded methods)
- **Unique Method Names**: 37
- **Static Methods**: 5

### Method Categories:
- **CRUD Operations**: Create, Read, Update operations for various entities
- **Authentication & Authorization**: Login validation, role checking
- **Data Retrieval**: Various getter methods for different data types
- **Registration**: User, student, club, and club member registration
- **Validation**: Duplicate checking, existence validation
- **Event Management**: Event creation, cancellation, updates, and history
- **Notification Management**: Create, read, and update notifications