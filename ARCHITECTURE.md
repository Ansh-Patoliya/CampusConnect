# Architecture Guide 🏗️

Technical overview of CampusConnect's system architecture, design patterns, and implementation details.

## Table of Contents
- [System Overview](#system-overview)
- [Architecture Patterns](#architecture-patterns)
- [Component Architecture](#component-architecture)
- [Data Layer](#data-layer)
- [Business Logic](#business-logic)
- [Presentation Layer](#presentation-layer)
- [Security Architecture](#security-architecture)
- [Performance Considerations](#performance-considerations)

## System Overview

CampusConnect is a desktop JavaFX application built using the Model-View-Controller (MVC) architectural pattern. The system provides a comprehensive platform for campus event management with role-based access control.

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Student   │  │ Club Member │  │    Admin    │        │
│  │     UI      │  │     UI      │  │     UI      │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│         │                 │                 │              │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              JavaFX Controllers                         │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                   Business Logic Layer                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Service   │  │ Validation  │  │   Utils     │        │
│  │   Classes   │  │   Layer     │  │  Classes    │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     Data Access Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │     DAO     │  │   Models    │  │ Exception   │        │
│  │   Classes   │  │   Classes   │  │  Handlers   │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     Database Layer                          │
│              PostgreSQL with Custom Functions               │
└─────────────────────────────────────────────────────────────┘
```

### Technology Stack

| Layer | Technology | Purpose |
|-------|------------|---------|
| **Presentation** | JavaFX + FXML | Desktop UI framework |
| **Controllers** | JavaFX Controllers | UI event handling and coordination |
| **Business Logic** | Java Services | Core application logic |
| **Data Access** | DAO Pattern + JDBC | Database abstraction |
| **Database** | PostgreSQL | Data persistence and integrity |
| **Build** | IntelliJ IDEA | Development environment |

## Architecture Patterns

### 1. Model-View-Controller (MVC)

**Implementation**: Strict separation between UI, business logic, and data

```java
// Model: Data representation
public class Event {
    private int eventId;
    private String eventName;
    // ... other properties
}

// View: FXML files
<!-- StudentDashboard.fxml -->
<BorderPane xmlns="http://javafx.com/javafx/11.0.1">
    <!-- UI elements -->
</BorderPane>

// Controller: UI logic coordination
@FXML
public class StudentDashboardController implements Initializable {
    @FXML private TableView<Event> eventsTable;
    private EventService eventService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEvents();
    }
}
```

### 2. Data Access Object (DAO) Pattern

**Purpose**: Encapsulate database access logic

```java
public interface EventDAO {
    void createEvent(Event event) throws SQLException;
    Event getEventById(int eventId) throws SQLException;
    List<Event> getAllEvents() throws SQLException;
    void updateEvent(Event event) throws SQLException;
    void deleteEvent(int eventId) throws SQLException;
}

public class EventDAOImpl implements EventDAO {
    private DatabaseConnection dbConnection;
    
    @Override
    public void createEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events (event_name, description, ...) VALUES (?, ?, ...)";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            // Implementation
        }
    }
}
```

### 3. Service Layer Pattern

**Purpose**: Centralize business logic and orchestrate operations

```java
public class EventService {
    private EventDAO eventDAO;
    private ClubDAO clubDAO;
    private ValidationUtils validator;
    
    public Event createEvent(Event event, String userId) throws ValidationException {
        // 1. Validate input
        validator.validateEvent(event);
        
        // 2. Check permissions
        if (!clubDAO.isClubMember(userId, event.getClubId())) {
            throw new SecurityException("Unauthorized");
        }
        
        // 3. Create event
        return eventDAO.createEvent(event);
    }
}
```

### 4. Factory Pattern

**Usage**: Object creation and initialization

```java
public class DAOFactory {
    public static EventDAO createEventDAO() {
        return new EventDAOImpl(DatabaseConnection.getInstance());
    }
    
    public static UserDAO createUserDAO() {
        return new UserDAOImpl(DatabaseConnection.getInstance());
    }
}
```

### 5. Singleton Pattern

**Usage**: Database connection management

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        initializeConnection();
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
```

## Component Architecture

### Core Components

#### 1. Authentication & Authorization
```java
public class AuthenticationService {
    public User authenticate(String userId, String password) {
        // Verify credentials
        // Return user with role information
    }
    
    public boolean hasPermission(String userId, String action, String resource) {
        // Role-based permission checking
    }
}
```

#### 2. Event Management
```java
// Event lifecycle management
public class EventManagementService {
    public Event createEvent(Event event);           // Club members
    public void approveEvent(int eventId);          // Admins
    public void registerForEvent(String userId, int eventId); // Students
    public void completeEvent(int eventId);         // System/Admins
}
```

#### 3. Club Management
```java
// Club operations and membership
public class ClubManagementService {
    public Club createClub(Club club);              // Club founders
    public void approveClub(int clubId);           // Admins
    public void joinClub(String userId, int clubId); // Students
    public void manageMembers(int clubId, List<String> members); // Club admins
}
```

#### 4. User Management
```java
// User account operations
public class UserManagementService {
    public User registerUser(User user);
    public void updateProfile(String userId, User updatedInfo);
    public List<Event> getUserEvents(String userId);
    public List<Club> getUserClubs(String userId);
}
```

## Data Layer

### Database Schema Design

#### Core Entities
```sql
-- Users: Base entity for all system users
users (user_id, name, email, password, role, created_at)

-- Students: Extended user information
students (student_id→users.user_id, department, semester, interests)

-- Clubs: Organization entities
clubs (club_id, club_name, category, description, founder_id→users.user_id, status, member_count)

-- Events: Activity entities
events (event_id, club_id→clubs.club_id, event_name, description, event_date, venue, max_participants)

-- Relationships
club_members (member_id→users.user_id, club_id→clubs.club_id, position)
event_registration (user_id→users.user_id, event_id→events.event_id, registration_date)
```

#### Custom Database Functions
```sql
-- Data retrieval functions
CREATE FUNCTION returnUserData(uid VARCHAR) RETURNS users;
CREATE FUNCTION returnClubData(cid INT) RETURNS clubs;
CREATE FUNCTION returnEventData(eid INT) RETURNS events;

-- Validation functions
CREATE FUNCTION checkcount(event_id INT) RETURNS BOOLEAN;
CREATE FUNCTION checkAlreadyRegistered(u_id VARCHAR, e_id INT) RETURNS BOOLEAN;
```

#### Triggers for Data Integrity
```sql
-- Automatic count updates
CREATE TRIGGER trg_after_insert_event_reg
AFTER INSERT ON event_registration
FOR EACH ROW EXECUTE FUNCTION update_event_reg_count();

-- Member count management
CREATE TRIGGER trg_after_member_join
AFTER INSERT ON club_members
FOR EACH ROW EXECUTE FUNCTION update_member_count();
```

### Custom Data Structures

#### Event Linked List
```java
public class MyEventLL {
    private EventNode head;
    
    public void addEvent(Event event) {
        // Custom linked list implementation for event management
    }
    
    public Event findEvent(int eventId) {
        // Efficient event search
    }
}
```

#### Club Queue
```java
public class MyClubQueue {
    private ClubNode front, rear;
    
    public void enqueue(Club club) {
        // Queue implementation for club approval workflow
    }
    
    public Club dequeue() {
        // Process club approvals in order
    }
}
```

## Business Logic

### Service Layer Architecture

#### 1. Domain Services
```java
// Student-specific operations
public class StudentService {
    public List<Event> getAvailableEvents(String studentId);
    public boolean registerForEvent(String studentId, int eventId);
    public List<Club> getRecommendedClubs(String studentId);
}

// Club-specific operations  
public class ClubService {
    public Event createEvent(Event event, String clubMemberId);
    public List<User> getClubMembers(int clubId);
    public void updateClubInfo(int clubId, Club updatedInfo);
}

// Admin-specific operations
public class AdminService {
    public void approveClub(int clubId, String adminId);
    public void approveEvent(int eventId, String adminId);
    public List<Club> getPendingClubs();
}
```

#### 2. Validation Layer
```java
public class ValidationUtils {
    public static void validateEvent(Event event) throws ValidationException {
        if (event.getEventName() == null || event.getEventName().trim().isEmpty()) {
            throw new ValidationException("Event name is required");
        }
        
        if (event.getEventDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Event date cannot be in the past");
        }
        
        if (event.getMaxParticipants() <= 0) {
            throw new ValidationException("Max participants must be positive");
        }
    }
    
    public static void validateUser(User user) throws ValidationException {
        // Email validation, password strength, etc.
    }
}
```

#### 3. Exception Handling
```java
// Custom exception hierarchy
public class CampusConnectException extends Exception {
    protected String errorCode;
    protected String userMessage;
}

public class ValidationException extends CampusConnectException {
    public ValidationException(String message) {
        super(message);
        this.userMessage = message;
    }
}

public class DatabaseException extends CampusConnectException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
        this.userMessage = "Database operation failed. Please try again.";
    }
}
```

## Presentation Layer

### JavaFX Architecture

#### 1. FXML-based UI Design
```xml
<!-- Modular FXML structure -->
<?xml version="1.0" encoding="UTF-8"?>
<BorderPane xmlns="http://javafx.com/javafx/11.0.1" 
            xmlns:fx="http://javafx.com/fxml/1" 
            fx:controller="com.eventApp.Controller.StudentDashboardController">
    
    <top>
        <fx:include source="NavigationBar.fxml"/>
    </top>
    
    <center>
        <TabPane>
            <Tab text="Events">
                <fx:include source="EventsTab.fxml"/>
            </Tab>
            <Tab text="Clubs">
                <fx:include source="ClubsTab.fxml"/>
            </Tab>
        </TabPane>
    </center>
</BorderPane>
```

#### 2. Controller Hierarchy
```java
// Base controller with common functionality
public abstract class BaseController implements Initializable {
    protected User currentUser;
    protected AlertUtils alertUtils;
    
    protected void showSuccessMessage(String message) {
        alertUtils.showSuccess(message);
    }
    
    protected void showErrorMessage(String message) {
        alertUtils.showError(message);
    }
}

// Specialized controllers
public class StudentDashboardController extends BaseController {
    private StudentService studentService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStudentData();
    }
}
```

#### 3. Navigation Management
```java
public class NavigationManager {
    private static final Map<String, String> FXML_PATHS = Map.of(
        "LOGIN", "/com/eventApp/FXML/Loginpage.fxml",
        "STUDENT_DASHBOARD", "/com/eventApp/FXML/StudentDashboard.fxml",
        "ADMIN_DASHBOARD", "/com/eventApp/FXML/AdminDashboard.fxml"
    );
    
    public static void navigateTo(String screenName, Stage stage) {
        // Load and display the specified screen
    }
}
```

## Security Architecture

### 1. Authentication
```java
public class AuthenticationManager {
    public AuthenticationResult authenticate(String userId, String password) {
        // 1. Hash password
        String hashedPassword = PasswordUtils.hash(password);
        
        // 2. Verify against database
        User user = userDAO.getUserByCredentials(userId, hashedPassword);
        
        // 3. Create session
        if (user != null) {
            CurrentUser.setCurrentUser(user);
            return AuthenticationResult.success(user);
        }
        
        return AuthenticationResult.failure("Invalid credentials");
    }
}
```

### 2. Authorization
```java
public class AuthorizationManager {
    public boolean canCreateEvent(String userId) {
        User user = CurrentUser.getCurrentUser();
        return user.getRole().equals("CLUB_MEMBER") || user.getRole().equals("ADMIN");
    }
    
    public boolean canApproveClub(String userId) {
        User user = CurrentUser.getCurrentUser();
        return user.getRole().equals("ADMIN");
    }
}
```

### 3. Session Management
```java
public class CurrentUser {
    private static User currentUser;
    private static final ThreadLocal<User> userSession = new ThreadLocal<>();
    
    public static void setCurrentUser(User user) {
        userSession.set(user);
        currentUser = user;
    }
    
    public static User getCurrentUser() {
        return userSession.get();
    }
    
    public static void clearSession() {
        userSession.remove();
        currentUser = null;
    }
}
```

## Performance Considerations

### 1. Database Optimization
```sql
-- Indexes for common queries
CREATE INDEX idx_events_date ON events(event_date);
CREATE INDEX idx_events_club ON events(club_id);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_registration_user ON event_registration(user_id);
```

### 2. Connection Management
```java
public class ConnectionPool {
    private static final int MAX_CONNECTIONS = 10;
    private Queue<Connection> availableConnections;
    
    public Connection getConnection() {
        // Return pooled connection
    }
    
    public void returnConnection(Connection connection) {
        // Return connection to pool
    }
}
```

### 3. Caching Strategy
```java
public class CacheManager {
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static final long CACHE_EXPIRY = 300000; // 5 minutes
    
    public static <T> T getCached(String key, Class<T> type) {
        CacheEntry entry = (CacheEntry) cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return type.cast(entry.getValue());
        }
        return null;
    }
}
```

### 4. Lazy Loading
```java
public class EventService {
    public List<Event> getEvents(int page, int size) {
        // Implement pagination to avoid loading all events
        return eventDAO.getEvents(page * size, size);
    }
    
    public Event getEventWithDetails(int eventId) {
        // Load event details only when needed
        Event event = eventDAO.getEvent(eventId);
        event.setRegistrations(eventDAO.getEventRegistrations(eventId));
        return event;
    }
}
```

## Deployment Architecture

### Local Development
```
┌─────────────────┐    ┌─────────────────┐
│   JavaFX App    │    │   PostgreSQL    │
│   (localhost)   │◄──►│   (localhost)   │
│   Port: N/A     │    │   Port: 5432    │
└─────────────────┘    └─────────────────┘
```

### Production Considerations
- Database connection pooling
- Proper error logging and monitoring
- Database backup and recovery procedures
- Security hardening (encrypted connections, secure credential storage)

## Future Enhancements

### Scalability Improvements
1. **Microservices**: Split into separate services (User, Event, Club services)
2. **Web Interface**: Add REST API for web client support
3. **Real-time Updates**: WebSocket integration for live notifications
4. **Distributed Database**: Sharding for large user bases

### Architecture Evolution
1. **Event-Driven Architecture**: Implement event sourcing for audit trails
2. **CQRS Pattern**: Separate read and write operations
3. **Message Queues**: Asynchronous processing for heavy operations
4. **Cloud Deployment**: Container-based deployment with Kubernetes

---

This architecture provides a solid foundation for campus event management while maintaining flexibility for future enhancements and scalability requirements.