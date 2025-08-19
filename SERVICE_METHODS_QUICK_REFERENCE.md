# Service Package Methods - Quick Reference

## AdminService (4 methods)
- exportClubData(String clubFilename)
- getAllClubs()
- getAdmin(User user)
- getClubMemberList(int clubId)

## ClubApprovalService (5 methods)
- ClubApprovalService()
- viewNextPendingClub()
- approveNextClub()
- rejectNextClub()
- getAllPendingClubs()

## ClubMemberService (1 method)
- getClubMemberByUser(User user)

## ClubService (12 methods)
- ClubService()
- ClubService(User user)
- addEvent(Event event)
- getClubByUser(User user)
- getClubMember(User user)
- getParticipant(int eventId)
- exportClubMembersToCSV(List<ClubMember> clubMemberList, String filePath)
- getAllEventNames(String userId)
- loadMyEventList(User user)
- viewCurrentEvent()
- viewNextEvent()
- cancelEvent()
- updateEvent(Event currentEvent)

## EventRegistrationService (1 method)
- registerForEvent(int eventId)

## EventService (8 methods)
- EventService()
- getAllPendingEvents() *(private)*
- viewNextEvent()
- approveEvent()
- rejectEvent()
- viewPreviousEvent()
- viewCurrentEvent()
- resetPointer()

## StudentService (8 methods)
- getStudentByUser(User user)
- sortEventList()
- loadStudentEvents()
- viewCurrentEvent()
- getNextEvent()
- getPreviousEvent()
- myEventListByDate(String userId)
- myEventListByPrice(String userId)

## UserService (6 methods)
- registerStudent(Student student, User user)
- registerClubMember(ClubMember clubMember, User user)
- checklogin(String emailInput, String passwordInput)
- resetPassword(String emailInput, String newPassword)
- registerClub(Club club, ClubMember clubMember, User user)
- getUserByEmail(String emailInput)

---
**Total: 45 methods across 8 service classes** *(including 1 private method)*