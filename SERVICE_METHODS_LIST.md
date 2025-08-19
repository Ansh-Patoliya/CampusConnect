# Service Package Methods List

This document provides a comprehensive list of all methods available in the service package (`com.eventApp.Service`). The service layer contains business logic and acts as an intermediary between the controllers and data access objects (DAOs).

## Table of Contents
1. [AdminService](#adminservice)
2. [ClubApprovalService](#clubapprovalservice)
3. [ClubMemberService](#clubmemberservice)
4. [ClubService](#clubservice)
5. [EventRegistrationService](#eventregistrationservice)
6. [EventService](#eventservice)
7. [StudentService](#studentservice)
8. [UserService](#userservice)

---

## AdminService

**Package:** `com.eventApp.Service.AdminService`

**Description:** Provides administrative functionality for managing clubs and users.

**Dependencies:** `UserDAO`, `ClubDAO`, `AdminDAO`, `ClubMemberDAO`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `exportClubData` | `void` | `String clubFilename` | `RuntimeException` | Exports all club data to a CSV file |
| `getAllClubs` | `MyClubQueue` | None | None | Retrieves all clubs as a queue data structure |
| `getAdmin` | `Admin` | `User user` | None | Gets admin details for a given user |
| `getClubMemberList` | `List<ClubMember>` | `int clubId` | `SQLException`, `ClassNotFoundException` | Retrieves list of club members for a specific club |

---

## ClubApprovalService

**Package:** `com.eventApp.Service.ClubApprovalService`

**Description:** Handles club approval workflow for pending club registrations.

**Dependencies:** `ClubDAO`, `DatabaseConnection`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `ClubApprovalService` | Constructor | None | None | Initializes the service and loads pending clubs |
| `viewNextPendingClub` | `Club` | None | None | Returns the next pending club in the queue |
| `approveNextClub` | `boolean` | None | `RuntimeException` | Approves the next pending club and updates database |
| `rejectNextClub` | `boolean` | None | `RuntimeException` | Rejects the next pending club and removes from database |
| `getAllPendingClubs` | `void` | None | None | Loads all pending clubs into the queue |

---

## ClubMemberService

**Package:** `com.eventApp.Service.ClubMemberService`

**Description:** Provides functionality related to club member operations.

**Dependencies:** `ClubMemberDAO`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `getClubMemberByUser` | `ClubMember` | `User user` | None | Retrieves club member information for a given user |

---

## ClubService

**Package:** `com.eventApp.Service.ClubService`

**Description:** Core service for club-related operations including event management and member management.

**Dependencies:** `ClubDAO`, `ClubMemberDAO`, `EventRegistrationDAO`, `EventDAO`, `CircularLL`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `ClubService` | Constructor | None | None | Default constructor |
| `ClubService` | Constructor | `User user` | `SQLException`, `DatabaseExceptionHandler`, `ClassNotFoundException` | Parameterized constructor that loads user's events |
| `addEvent` | `void` | `Event event` | `ValidationException`, `SQLException`, `ClassNotFoundException` | Creates a new event |
| `getClubByUser` | `Club` | `User user` | None | Retrieves club information for a given user |
| `getClubMember` | `List<ClubMember>` | `User user` | `SQLException`, `ClassNotFoundException` | Gets sorted list of club members for user's club |
| `getParticipant` | `List<Student>` | `int eventId` | `SQLException`, `ClassNotFoundException` | Gets sorted list of event participants |
| `exportClubMembersToCSV` | `void` | `List<ClubMember> clubMemberList`, `String filePath` | `IOException` | Exports club members to CSV file |
| `getAllEventNames` | `List<String>` | `String userId` | None | Gets sorted list of all event names for a user |
| `loadMyEventList` | `void` | `User user` | `SQLException`, `ClassNotFoundException`, `DatabaseExceptionHandler` | Loads events for user's club into circular linked list |
| `viewCurrentEvent` | `Event` | None | None | Views the current event in the circular list |
| `viewNextEvent` | `Event` | None | None | Moves to and views the next event in the circular list |
| `cancelEvent` | `void` | None | `SQLException`, `DatabaseExceptionHandler`, `ClassNotFoundException` | Cancels the current event |
| `updateEvent` | `void` | `Event currentEvent` | `SQLException`, `ClassNotFoundException`, `DatabaseExceptionHandler` | Updates an existing event |

---

## EventRegistrationService

**Package:** `com.eventApp.Service.EventRegistrationService`

**Description:** Handles student registration for events with validation.

**Dependencies:** `EventRegistrationDAO`, `CurrentUser`, `DatabaseConnection`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `registerForEvent` | `void` | `int eventId` | `DatabaseExceptionHandler`, `SQLException`, `ClassNotFoundException` | Registers current user for an event with capacity and duplicate checks |

---

## EventService

**Package:** `com.eventApp.Service.EventService`

**Description:** Manages event approval workflow for administrators.

**Dependencies:** `AdminDAO`, `EventDAO`, `MyEventLL`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `EventService` | Constructor | None | None | Initializes the service and loads pending events |
| `getAllPendingEvents` | `void` | None | None | **(Private)** Loads all pending events into the linked list |
| `viewNextEvent` | `Event` | None | None | Views the next pending event |
| `approveEvent` | `boolean` | None | None | Approves the current event and removes from pending list |
| `rejectEvent` | `boolean` | None | None | Rejects the current event and removes from pending list |
| `viewPreviousEvent` | `Event` | None | None | Views the previous event in the list |
| `viewCurrentEvent` | `Event` | None | None | Views the current event in the list |
| `resetPointer` | `void` | None | None | Resets the pointer to the beginning of the event list |

---

## StudentService

**Package:** `com.eventApp.Service.StudentService`

**Description:** Provides student-specific functionality including event browsing and management.

**Dependencies:** `EventDAO`, `StudentDAO`, `MyEventLL`, `CurrentUser`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `getStudentByUser` | `Student` | `User user` | None | Retrieves student information for a given user |
| `sortEventList` | `MyEventLL` | None | None | Sorts events based on student interests and date |
| `loadStudentEvents` | `void` | None | None | Loads and sorts events for the current student |
| `viewCurrentEvent` | `Event` | None | None | Views the current event in the student's event list |
| `getNextEvent` | `Event` | None | None | Gets the next event in the student's event list |
| `getPreviousEvent` | `Event` | None | None | Gets the previous event in the student's event list |
| `myEventListByDate` | `List<Event>` | `String userId` | None | Gets user's registered events sorted by date |
| `myEventListByPrice` | `List<Event>` | `String userId` | None | Gets user's registered events sorted by ticket price |

---

## UserService

**Package:** `com.eventApp.Service.UserService`

**Description:** Handles user registration, authentication, and account management.

**Dependencies:** `UserDAO`, `StudentDAO`, `ClubMemberDAO`, `ClubDAO`, `DatabaseConnection`

### Methods:

| Method | Return Type | Parameters | Exceptions | Description |
|--------|-------------|------------|------------|-------------|
| `registerStudent` | `void` | `Student student`, `User user` | `DatabaseExceptionHandler`, `SQLException`, `ClassNotFoundException` | Registers a new student user |
| `registerClubMember` | `void` | `ClubMember clubMember`, `User user` | `DatabaseExceptionHandler`, `SQLException`, `ClassNotFoundException` | Registers a new club member with capacity validation |
| `checklogin` | `boolean` | `String emailInput`, `String passwordInput` | None | Validates user login credentials |
| `resetPassword` | `void` | `String emailInput`, `String newPassword` | `SQLException`, `DatabaseExceptionHandler`, `ClassNotFoundException` | Resets user password |
| `registerClub` | `void` | `Club club`, `ClubMember clubMember`, `User user` | `DatabaseExceptionHandler`, `SQLException`, `ClassNotFoundException` | Registers a new club with founder as club member |
| `getUserByEmail` | `User` | `String emailInput` | None | Retrieves user information by email address |

---

## Summary

The service package contains **8 service classes** with a total of **45 methods** (including constructors and 1 private method). These services provide a comprehensive business logic layer for:

- **User Management**: Registration, authentication, password management
- **Club Operations**: Club creation, approval, member management, data export
- **Event Management**: Event creation, approval, registration, browsing
- **Administrative Functions**: Data export, approval workflows
- **Student Features**: Event browsing with personalized sorting, registration tracking

Each service follows the separation of concerns principle by handling specific business domains while leveraging appropriate DAOs for data access.