# Project Structure

This document provides an overview of the CampusConnect project structure and organization.

## Directory Structure

```
CampusConnect/
├── src/
│   ├── com/eventApp/
│   │   ├── Controller/          # JavaFX Controllers
│   │   │   ├── AdminController.java
│   │   │   ├── LoginController.java
│   │   │   ├── RegistrationController.java
│   │   │   └── StudentController.java
│   │   ├── DAO/                 # Data Access Objects
│   │   │   ├── ClubDAO.java
│   │   │   ├── EventDAO.java
│   │   │   ├── NotificationDAO.java
│   │   │   └── UserDAO.java
│   │   ├── DataStructures/      # Custom Data Structures
│   │   │   ├── CircularLL.java
│   │   │   └── MyClubQueue.java
│   │   ├── ExceptionHandler/    # Custom Exceptions
│   │   │   ├── DatabaseExceptionHandler.java
│   │   │   └── ValidationException.java
│   │   ├── FXML/               # JavaFX FXML Files
│   │   │   ├── AdminDashboard.fxml
│   │   │   ├── ClubDashboard.fxml
│   │   │   ├── Loginpage.fxml
│   │   │   └── registration.fxml
│   │   ├── Loader/             # Screen Loading Utilities
│   │   │   └── FXMLScreenLoader.java
│   │   ├── Model/              # Entity Classes
│   │   │   ├── Admin.java
│   │   │   ├── Club.java
│   │   │   ├── Event.java
│   │   │   ├── Student.java
│   │   │   └── User.java
│   │   ├── Service/            # Business Logic Layer
│   │   │   ├── impl/
│   │   │   │   ├── AdminServiceImpl.java
│   │   │   │   ├── ClubServiceImpl.java
│   │   │   │   └── StudentServiceImpl.java
│   │   │   ├── AdminService.java
│   │   │   ├── ClubService.java
│   │   │   └── StudentService.java
│   │   ├── Utils/              # Utility Classes
│   │   │   ├── CurrentUser.java
│   │   │   ├── DatabaseConnection.java
│   │   │   └── ValidationUtils.java
│   │   └── Main.java           # Application Entry Point
│   └── Queary/                 # Database Schema
│       └── database_schema.sql
├── .gitignore                  # Git ignore rules
├── CampusConnect.iml          # IntelliJ IDEA module file
├── CHANGELOG.md               # Version history
├── CONTRIBUTING.md            # Contribution guidelines
├── LICENSE                    # MIT License
└── README.md                  # Project documentation
```

## Architecture Layers

### 1. Presentation Layer (FXML + Controllers)
- **FXML Files**: Define the UI layout and structure
- **Controllers**: Handle user interactions and coordinate with services
- **Loader**: Manages screen navigation and loading

### 2. Service Layer
- **Interfaces**: Define business logic contracts
- **Implementations**: Implement business rules and coordinate with DAOs
- **Purpose**: Encapsulate business logic and provide transaction management

### 3. Data Access Layer (DAO)
- **DAO Classes**: Handle database operations
- **Connection Management**: Database connection utilities
- **Purpose**: Abstract database operations and provide data persistence

### 4. Model Layer
- **Entity Classes**: Represent core business objects
- **Data Structures**: Custom implementations for specific needs
- **Purpose**: Define data structure and business entities

### 5. Utility Layer
- **Validation**: Input validation and business rule checking
- **Helpers**: Common utilities and helper functions
- **Exceptions**: Custom exception handling

## Key Design Patterns

### 1. Model-View-Controller (MVC)
- **Model**: Entity classes and data structures
- **View**: FXML files and UI components
- **Controller**: Controller classes handling user interactions

### 2. Data Access Object (DAO)
- Abstracts database operations
- Provides clean separation between business logic and data access
- Enables easier testing and maintenance

### 3. Service Layer Pattern
- Encapsulates business logic
- Provides transaction boundaries
- Coordinates between controllers and DAOs

### 4. Dependency Injection
- Services are injected into controllers
- DAOs are injected into services
- Promotes loose coupling and testability

## File Naming Conventions

### Controllers
- Format: `[Entity]Controller.java`
- Examples: `LoginController.java`, `StudentController.java`

### Services
- Interface: `[Entity]Service.java`
- Implementation: `[Entity]ServiceImpl.java`
- Examples: `ClubService.java`, `ClubServiceImpl.java`

### DAOs
- Format: `[Entity]DAO.java`
- Examples: `UserDAO.java`, `EventDAO.java`

### Models
- Format: `[Entity].java`
- Examples: `User.java`, `Event.java`

### FXML
- Format: `[ScreenName].fxml`
- Examples: `Loginpage.fxml`, `AdminDashboard.fxml`

## Database Integration

### Connection Management
- `DatabaseConnection.java`: Manages PostgreSQL connections
- Connection pooling for performance
- Proper resource cleanup

### Data Access
- Each entity has a corresponding DAO
- Prepared statements for security
- Transaction management

### Schema Management
- Database schema defined in `src/Queary/database_schema.sql`
- Includes tables, triggers, functions, and stored procedures
- Supports complex business logic at database level

## Configuration

### Database Configuration
- Connection parameters in `DatabaseConnection.java`
- Environment-specific configurations
- Connection pooling settings

### JavaFX Configuration
- Module path configuration for JavaFX
- FXML resource loading
- Scene and stage management

## Testing Strategy

### Unit Testing
- Service layer testing with mocked dependencies
- DAO testing with test database
- Validation utility testing

### Integration Testing
- End-to-end workflow testing
- Database integration testing
- UI component testing

## Build and Deployment

### Development Environment
- IntelliJ IDEA project configuration
- JavaFX module setup
- Database connection configuration

### Dependencies
- JavaFX Controls and FXML
- PostgreSQL JDBC driver
- Custom data structures

This structure promotes maintainability, testability, and scalability while following established Java development patterns and best practices.