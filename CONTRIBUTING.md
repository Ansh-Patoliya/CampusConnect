# Contributing to CampusConnect 🤝

Thank you for your interest in contributing to CampusConnect! This guide will help you get started with contributing to our campus event management system.

## Table of Contents
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Project Structure](#project-structure)
- [Coding Standards](#coding-standards)
- [Contributing Workflow](#contributing-workflow)
- [Testing Guidelines](#testing-guidelines)
- [Documentation](#documentation)
- [Reporting Issues](#reporting-issues)

## Getting Started

### Prerequisites

Before contributing, ensure you have:
- Java 11 or higher
- JavaFX SDK configured
- PostgreSQL database
- IntelliJ IDEA (recommended) or any Java IDE
- Git for version control
- Basic understanding of JavaFX and MVC architecture

### First Time Contributors

1. **Fork the Repository**: Click the "Fork" button on GitHub
2. **Clone Your Fork**: 
   ```bash
   git clone https://github.com/YOUR_USERNAME/CampusConnect.git
   cd CampusConnect
   ```
3. **Set Up Development Environment**: Follow the [setup guide](README.md#installation--setup)
4. **Create a Feature Branch**: 
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Environment Configuration

1. **Database Setup**
   - Follow [DATABASE_SETUP.md](DATABASE_SETUP.md) for complete setup
   - Create a test database for development:
     ```sql
     CREATE DATABASE campusconnect_dev;
     ```

2. **IDE Configuration**
   - Import project into IntelliJ IDEA
   - Configure JavaFX library paths
   - Set up VM options for JavaFX modules

3. **Dependencies**
   - PostgreSQL JDBC driver
   - JavaFX Controls and FXML
   - Ensure all libraries are properly configured

### Running the Application

```bash
# From IDE: Run Main.java with VM options:
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml

# From command line:
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp . com.eventApp.Main
```

## Project Structure

Understanding the codebase structure:

```
src/com/eventApp/
├── Controller/          # JavaFX Controllers
│   ├── AdminController.java
│   ├── StudentController.java
│   └── ClubController.java
├── DAO/                 # Data Access Objects
│   ├── UserDAO.java
│   ├── EventDAO.java
│   └── ClubDAO.java
├── DataStructures/      # Custom Data Structures
│   ├── MyEventLL.java
│   ├── MyClubQueue.java
│   └── CircularLL.java
├── ExceptionHandler/    # Custom Exceptions
├── FXML/               # UI Layout Files
├── Model/              # Entity Classes
│   ├── User.java
│   ├── Event.java
│   └── Club.java
├── Service/            # Business Logic
├── Utils/              # Utility Classes
└── Main.java           # Application Entry Point
```

### Key Components

#### Models
- **User.java**: Base user entity with role-based functionality
- **Student.java**: Extended user with academic information
- **Event.java**: Event entity with comprehensive details
- **Club.java**: Club management and member tracking

#### Controllers
- Follow MVC pattern
- Handle UI interactions and business logic coordination
- Connect FXML views with backend services

#### DAOs (Data Access Objects)
- Database interaction layer
- CRUD operations for each entity
- Database connection management

#### Services
- Business logic implementation
- Validation and processing
- Coordination between controllers and DAOs

## Coding Standards

### Java Conventions

#### Naming Conventions
```java
// Classes: PascalCase
public class EventController { }

// Methods: camelCase
public void createEvent() { }

// Variables: camelCase
private String eventName;

// Constants: UPPER_SNAKE_CASE
private static final String DEFAULT_STATUS = "PENDING";
```

#### Code Organization
```java
public class ExampleClass {
    // 1. Static variables
    private static final String CONSTANT = "value";
    
    // 2. Instance variables
    private String instanceVariable;
    
    // 3. Constructors
    public ExampleClass() { }
    
    // 4. Public methods
    public void publicMethod() { }
    
    // 5. Private methods
    private void privateMethod() { }
    
    // 6. Getters and setters
    public String getInstanceVariable() { return instanceVariable; }
    public void setInstanceVariable(String value) { this.instanceVariable = value; }
}
```

### JavaFX Guidelines

#### Controller Structure
```java
@FXML
public class ExampleController implements Initializable {
    @FXML private Button submitButton;
    @FXML private TextField nameField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize UI components
    }
    
    @FXML
    private void handleSubmit(ActionEvent event) {
        // Handle button click
    }
}
```

#### FXML Best Practices
- Use meaningful `fx:id` attributes
- Organize UI elements logically
- Keep layouts responsive
- Use proper styling and themes

### Database Guidelines

#### DAO Pattern
```java
public class ExampleDAO {
    public void create(Example example) throws SQLException {
        String sql = "INSERT INTO examples (name, value) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, example.getName());
            stmt.setString(2, example.getValue());
            stmt.executeUpdate();
        }
    }
}
```

#### SQL Best Practices
- Use parameterized queries to prevent SQL injection
- Follow database naming conventions
- Implement proper error handling
- Use transactions for multi-step operations

## Contributing Workflow

### 1. Issue Selection
- Check existing issues for something to work on
- Comment on the issue to indicate you're working on it
- Create a new issue if needed

### 2. Development Process
```bash
# Create feature branch
git checkout -b feature/issue-description

# Make your changes
# ... code, test, commit ...

# Keep branch updated
git fetch origin
git rebase origin/main

# Push to your fork
git push origin feature/issue-description
```

### 3. Commit Guidelines
```bash
# Good commit messages:
git commit -m "Add event registration validation"
git commit -m "Fix club member count update trigger"
git commit -m "Update user profile UI layout"

# Include issue reference:
git commit -m "Fix login validation issue #123"
```

### 4. Pull Request Process
1. **Create Pull Request**: From your fork to main repository
2. **Describe Changes**: Use the PR template
3. **Review Process**: Address reviewer feedback
4. **Merge**: After approval, your PR will be merged

### Pull Request Template
```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
```

## Testing Guidelines

### Manual Testing
1. **Functionality Testing**
   - Test all affected features thoroughly
   - Verify edge cases and error conditions
   - Test with different user roles

2. **UI Testing**
   - Check responsive design
   - Verify all UI elements work correctly
   - Test navigation and user flow

3. **Database Testing**
   - Verify data integrity
   - Test database constraints
   - Check transaction handling

### Test Data
```sql
-- Create test users
INSERT INTO users VALUES ('test_student', 'Test Student', 'student@test.com', 'password', 'STUDENT');
INSERT INTO users VALUES ('test_club', 'Test Club Member', 'club@test.com', 'password', 'CLUB_MEMBER');
INSERT INTO users VALUES ('test_admin', 'Test Admin', 'admin@test.com', 'password', 'ADMIN');

-- Clean up after testing
DELETE FROM users WHERE user_id LIKE 'test_%';
```

## Documentation

### Code Documentation
```java
/**
 * Creates a new event for the specified club.
 * 
 * @param event The event to create
 * @param clubId The ID of the club creating the event
 * @return The created event with assigned ID
 * @throws ValidationException if event data is invalid
 * @throws DatabaseException if database operation fails
 */
public Event createEvent(Event event, int clubId) throws ValidationException, DatabaseException {
    // Implementation
}
```

### User Documentation
- Update relevant sections in USER_GUIDE.md
- Add screenshots for UI changes
- Include examples and use cases

### Technical Documentation
- Update architecture documentation for significant changes
- Document new APIs or interfaces
- Update database schema documentation

## Reporting Issues

### Bug Reports
Include the following information:
- **Environment**: OS, Java version, JavaFX version
- **Steps to Reproduce**: Detailed steps
- **Expected Behavior**: What should happen
- **Actual Behavior**: What actually happens
- **Screenshots**: If applicable
- **Logs**: Any relevant error logs

### Feature Requests
- **Description**: Clear description of the feature
- **Use Case**: Why this feature is needed
- **Acceptance Criteria**: How to validate the feature
- **Mockups**: UI mockups if applicable

### Issue Template
```markdown
## Environment
- OS: [Windows/macOS/Linux]
- Java Version: [e.g., Java 11]
- JavaFX Version: [e.g., JavaFX 11]

## Description
[Clear description of the issue]

## Steps to Reproduce
1. Step one
2. Step two
3. Step three

## Expected Behavior
[What you expected to happen]

## Actual Behavior
[What actually happened]

## Screenshots
[If applicable]

## Additional Context
[Any other relevant information]
```

## Code Review Guidelines

### For Contributors
- **Self-Review**: Review your own code before submitting
- **Clean Code**: Follow coding standards and best practices
- **Test Coverage**: Ensure adequate testing
- **Documentation**: Update relevant documentation

### For Reviewers
- **Be Constructive**: Provide helpful feedback
- **Check Standards**: Verify coding standards compliance
- **Test Functionality**: Verify changes work as expected
- **Security Review**: Check for security implications

## Getting Help

### Community Resources
- **GitHub Issues**: Ask questions or report problems
- **Documentation**: Check existing documentation first
- **Code Examples**: Look at existing code for patterns

### Development Support
- **Setup Issues**: Check the installation guide
- **Database Problems**: Review database setup documentation
- **JavaFX Issues**: Ensure proper JavaFX configuration

## Recognition

Contributors will be recognized in:
- **CONTRIBUTORS.md**: List of all contributors
- **Release Notes**: Major contributions highlighted
- **GitHub**: Contributor statistics and activity

---

**Thank you for contributing to CampusConnect!** Your efforts help make campus event management better for everyone. 🎉